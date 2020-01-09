package ioc

import grails.plugin.springsecurity.annotation.Secured
import grails.util.Environment

import xyz.ioc.common.ApplicationConstants
import grails.converters.*
import com.stripe.Stripe
import com.stripe.model.Customer
import com.stripe.model.Product
import com.stripe.model.Plan
import com.stripe.model.Subscription
import com.stripe.model.Charge

import xyz.ioc.Account
import xyz.ioc.DonationProduct
import xyz.ioc.DonationPlan
import xyz.ioc.DonationSubscription


class PlanController {

	def commonUtilities
	def applicationService
	def stripeService

	@Secured([ApplicationConstants.ROLE_ADMIN])
	def index(){
		redirect(action: "list")
	}


	@Secured([ApplicationConstants.ROLE_ADMIN])
	def create(){}


	@Secured([ApplicationConstants.ROLE_ADMIN])
	def list(){
		def plans = DonationPlan.list()
		[ plans : plans ]
	}


	@Secured([ApplicationConstants.ROLE_ADMIN])
	def save(){

		if(!params.name || !params.amount){
			flash.message = "Name or amount cannot be blank."
			redirect(action: "create")
			return
		}

		if(!params.amount.isNumber()){
			flash.message = "Please enter a number for amount."
			redirect(action: "create")
			return
		}

		def amount = new BigDecimal(params.amount) * 100 as Integer
		println "amount: ${amount}"

		def plan = stripeService.save(params.name, "month", amount)
		redirect(action: "list")

	}


	@Secured([ApplicationConstants.ROLE_ADMIN])
	def remove(){
		def plan = DonationPlan.get(params.id)

		if(!plan){
			flash.message = "Unable to find plan."
			redirect(action: "list")
			return
		}

		def product = plan.donationProduct 

		try{
			def pl = com.stripe.model.Plan.retrieve(plan.stripeId);
			pl.delete();
		}catch(Exception e){
			e.printStacktrace()
		}
		try{
			def p = com.stripe.model.Product.retrieve(product.stripeId)
			p.delete()
		}catch(Exception e){
			e.printStacktrace()
		}


		def subscriptions = DonationSubscription.findAllByDonationPlan(plan)
		subscriptions.each { subscription ->
			stripeService.cancel(subscription.stripeId)
			def account = Account.findByDonationSubscription(subscription)
			account.donationSubscription = null
			account.save(flush:true)
			subscription.delete(flush:true)
		}

		plan.delete(flush:true)

		flash.message = "Successfully deleted plan."
		redirect(action: "list")

	}


	@Secured([ApplicationConstants.ROLE_ADMIN])
	def bootstrap(){
		/**
		5,10,25,50,100,200,300,500,1000,2000
		**/

		def one = [ "description" : "\$5 a month", "interval": "month", "amount": 500  ]		
		def two = [ "description" : "\$25 a month", "interval": "month", "amount": 2500  ]
		def three = [ "description" : "\$50 a month", "interval": "month", "amount": 5000  ]
		def four = [ "description" : "\$100 a month", "interval": "month", "amount": 10000  ]
		def five = [ "description" : "\$150 a month", "interval": "month", "amount": 15000  ]
		

		def plans = []

		plans.add(one)
		plans.add(two)
		plans.add(three)
		plans.add(four)
		plans.add(five)

		plans.each { it ->
			def plan = stripeService.save(it.description, it.interval, it.amount)
			println "plan : ${plan}"
		}

	}


	@Secured([ApplicationConstants.ROLE_ADMIN])
	def remove_all(){
		stripeService.remove()
	}


}