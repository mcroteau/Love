package xyz.ioc

import grails.util.Environment

import xyz.ioc.common.ApplicationConstants

import com.stripe.Stripe
import com.stripe.model.Customer
import com.stripe.model.Product
import com.stripe.model.Plan
import com.stripe.model.Subscription
import com.stripe.model.Charge

import xyz.ioc.Account
import xyz.ioc.DonationPlan
import xyz.ioc.DonationSubscription


class StripeService {
	
	def applicationService


	def save(nickname, interval, amount){
	
		Stripe.apiKey = getApiKey()

		String currency = "usd"
		println "*** amount : ${amount} : ${Environment.current} : ${getApiKey()}"


		Map<String, Object> p = new HashMap<String, Object>()
		p.put("name", "${applicationService.getSiteName()} ${nickname}")
		p.put("type", "service")

		def stripeProduct = com.stripe.model.Product.create(p)
		println stripeProduct


		Map<String, Object> params = new HashMap<String, Object>()
		params.put("product", stripeProduct.id);
		params.put("nickname", nickname);
		params.put("interval", interval);
		params.put("currency", currency);
		params.put("amount", amount);
		def stripePlan = com.stripe.model.Plan.create(params);


		def donationProduct = new DonationProduct()
		donationProduct.stripeId = stripeProduct.id
		donationProduct.name = nickname
		donationProduct.type = "service"
		donationProduct.save(flush:true)

		donationProduct.errors.allErrors.each { it ->
			println it
		}

		def donationPlan = new DonationPlan()
		donationPlan.stripeId = stripePlan.id
		donationPlan.donationProduct = donationProduct
		donationPlan.nickname = nickname
		donationPlan.frequency = interval
		donationPlan.currency = currency
		donationPlan.amount = amount

		donationPlan.errors.allErrors.each { it ->
			println it
		}

		donationPlan.save(flush:true)

		return donationPlan
	}


	def remove(){
		def plans = DonationPlan.list()

		plans.each { plan ->

			def product = plan.product 

			try{
				def pl = com.stripe.model.Plan.retrieve(plan.stripeId);
				pl.delete();
			}catch(Exception e){
				
			}
			try{
				def p = com.stripe.model.Product.retrieve(product.stripeId)
				p.delete()
			}catch(Exception e){

			}

			def accounts = Account.findAllByDonationPlan(plan)
			accounts.each{ account ->
				account.donationPlan = null
				account.save(flush:true)
			}

			def subscriptions = DonationSubscription.findAllByDonationPlan(plan)
			subscriptions.each { subscription ->
				cancel(subscription.stripeId)
				subscription.delete(flush:true)
			}

			plan.delete(flush:true)
			
		}
	}


	def cancel(subscriptionStripeId){
		Stripe.apiKey = getApiKey()
		try{
			def sb = com.stripe.model.Subscription.retrieve(subscriptionStripeId);
			sb.cancel(null);
		}catch(Exception e){
			e.printStackTrace()
		}

	}


	def getApiKey(){
		def apiKey

			println "\n\n\n\n${Environment.current}\n\n\n\n"
		if(Environment.current == Environment.DEVELOPMENT)apiKey = applicationService.getStripeDevSecret()
		if(Environment.current == Environment.PRODUCTION) apiKey = applicationService.getStripeSecret()

		return apiKey
	}
}