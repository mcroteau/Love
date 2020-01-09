package ioc

import grails.converters.*
import grails.plugin.springsecurity.annotation.Secured
import grails.util.Environment

import com.stripe.Stripe
import com.stripe.model.Customer
import com.stripe.model.Product
import com.stripe.model.Plan
import com.stripe.model.Subscription
import com.stripe.model.Charge

import xyz.ioc.common.ApplicationConstants
import xyz.ioc.DonationPlan
import xyz.ioc.DonationSubscription

import xyz.ioc.Account 
import xyz.ioc.Donation


class DonateController {
	
	def commonUtilities
	def emailService
	def stripeService
	def applicationService
	def springSecurityService


	@Secured([ApplicationConstants.PERMIT_ALL])
	def index(){
		def account = commonUtilities.getAuthenticatedAccount()
		def plans = DonationPlan.list()
		if(!params.onetimeChecked && !params.recurringChecked) params.recurringChecked = "checked"
		[ account: account, plans: plans, params: params ]
	}


	@Secured([ApplicationConstants.PERMIT_ALL])
	def monthly(){
		def account = commonUtilities.getAuthenticatedAccount()
		def plan = DonationPlan.get(params.id)
		if(!plan){
			flash.message = "Monthly donation plan cannot be found."
			redirect(action:"index")
			return
		}
		[ account: account, plan: plan ]
	}


	@Secured([ApplicationConstants.PERMIT_ALL])
	def pay(){
		println "params : ${params}"
		if(!params.amount){
			flash.message = "Please specify an amount."
			redirect(action:"index", params: params)
			return
		}

		if(!params.amount.isNumber()){
			flash.message = "Please enter a valid number for amount."
			redirect(action: "index", params: params)
			return
		}

		if(!params.token){
			flash.message = "Will you contact support for us? We forgot something. You were not charged. Thank you!"
			redirect(action:"index", params: params)
			return
		}

		def account = Account.findByUsername(params.email)

		if(!account){
			account = createAccount(params)
		}

		def apiKey

		if(Environment.current == Environment.DEVELOPMENT) apiKey = applicationService.getStripeDevSecret()
		if(Environment.current == Environment.PRODUCTION) apiKey = applicationService.getStripeSecret()
	
		Stripe.apiKey = apiKey

		def token = params.token
		def amount = new BigDecimal(params.amount) * 100 as Integer

		def chargeParams = [
		    'amount': amount, 
		    'currency': "usd", 
		    'source': token, 
		    'description': "Donation : Account Id: ${account.id}, ${account.username}"
		]
    	
		def charge = Charge.create(chargeParams)
		
		/**
		charge.properties.each { 
			println "$it.key -> $it.value" 
		}
		**/

		if(charge){

			def donation = new Donation()
			donation.stripeId = charge.id 
			donation.amount = amount
			donation.account = account 
			donation.save(flush:true)
			
		}else{
			flash.message = "Would you be willing to contact support? Thank you!"
			redirect(action: "index", params: params)
			return
		}

		redirect(action:"thank_you", id: account.id)
		return
	}


	def createAccount(params){
		if(!params.name){
			flash.message = "Name cannot be blank."
			redirect(action:"index", params: params)
			return				
		}

		if(!params.email){
			flash.message = "Email cannot be blank."
			redirect(action:"index", params: params)
			return				
		}

		if(!commonUtilities.validEmail(params.email)){
			flash.message = "Email must be a valid email address."
			redirect(action:"index", params: params)
			return
		}

		if(!params.password || params.password.size() < 7){
			flash.message = "Password must be 7 characters in length"
			redirect(action: "index", params : params)
			return
		}

		def account = new Account()
		account.name = params.name 
		account.username = params.username 
		account.location = params.location

		def password = springSecurityService.encodePassword(params.password)
   		account.password = password

		
		if(!account.save(flush:true)){
			println "didnt save..."
			flash.message = "You may already have an account with us. Please signin or contact us to continue."
			redirect(controller:"login", action:"signin")
			return
		}

		account.createAccountRoles(false)
		account.createAccountPermission()
		account.save(flush:true)


		return account 
	}


	@Secured([ApplicationConstants.PERMIT_ALL])
	def pay_monthly(){
		def account = Account.findByUsername(params.email)

		if(!account){
			account = createAccount(params)
		}

		if(account.donationSubscription){
			flash.message = "You must cancel your previous monthly donation in order to continue."
			redirect(controller:"account", action: "review")
			return
		}

		def apiKey

		if(Environment.current == Environment.DEVELOPMENT) apiKey = applicationService.getStripeDevSecret()
		if(Environment.current == Environment.PRODUCTION) apiKey = applicationService.getStripeSecret()
	
		Stripe.apiKey = apiKey

		def donationPlan = DonationPlan.get(params.id)

		if(!donationPlan){
			flash.message = 'Will you contact support? There was an issue.'
			redirect(action: "pay")
			return
		}

		println "token : " + params.token

		Map<String, Object> customerMap = new HashMap<String, Object>()
		customerMap.put("email", account.username)
		customerMap.put("source", params.token)
		def stripeCustomer = com.stripe.model.Customer.create(customerMap)
		
		account.stripeId = stripeCustomer.id
   		account.save(flush:true)


   		def stripeSubscription = getStripeSubscription(donationPlan, stripeCustomer)
   		def donationSubscription = getDonationSubscription(account, donationPlan, stripeSubscription)

		account.donationSubscription = donationSubscription
		account.save(flush:true)
			
		request.account = account 
		flash.message = "\$${applicationService.formatPrice(donationPlan.amount/100)} Payment receieved! Thank you for your support."
		render(view: "thank_you")
	}


	@Secured([ApplicationConstants.PERMIT_ALL])
	def thank_you(){
		def account = commonUtilities.getAuthenticatedAccount()
		[ account : account ]
	}



	@Secured([ApplicationConstants.ROLE_ADMIN, ApplicationConstants.ROLE_CUSTOMER])
	def confirm(){
		def account = commonUtilities.getAuthenticatedAccount()
		def plan = DonationPlan.get(params.id)
		if(!plan){
			flash.message = "Will you contact support? We cannot find account information. Thank you. Apologies for the inconvenience."
			redirect(controller: "account", action: "review")
			return
		}
		[ account : account, plan: plan ]
	}



	@Secured([ApplicationConstants.ROLE_ADMIN, ApplicationConstants.ROLE_CUSTOMER])
	def cancel(){
		def account = Account.get(params.id)
		if(!account){
			flash.message = "Unable to find account"
			redirect(action:"index")
			return
		}

		if(!account.donationSubscription){
			flash.message = "You are not currently enrolled in a donation plan."
			redirect(controller: "account", action:"review")
			return
		}
		
		def subscription = account.donationSubscription.stripeId
		stripeService.cancel(subscription)

		def donationSubscription = DonationSubscription.get(account.donationSubscription.id)
		println donationSubscription

		account.donationSubscription = null
		account.save(flush:true)

		donationSubscription.delete(flush:true)



		flash.message = "Successfully opted out of monthly contribution. Thank you."
		redirect(controller: "account", action: "review")

	}

	def getStripeSubscription(donationPlan, stripeCustomer){
		Map<String, Object> item = new HashMap<String, Object>()
		item.put("plan", donationPlan.stripeId)
		
		Map<String, Object> items = new HashMap<String, Object>()
		items.put("0", item)

		Map<String, Object> params = new HashMap<String, Object>()
		params.put("customer", stripeCustomer.id)
		params.put("items", items)
		
		com.stripe.model.Subscription stripeSubscription = com.stripe.model.Subscription.create(params);
		return stripeSubscription
	}


	def getDonationSubscription(account, donationPlan, stripeSubscription){
		def subscription = new DonationSubscription()
		subscription.stripeId = stripeSubscription.id
		subscription.account = account 		
		subscription.donationPlan = donationPlan 
		subscription.save(flush:true)
		return subscription
	}


}