package ioc

import grails.plugin.springsecurity.annotation.Secured

import xyz.ioc.common.ApplicationConstants
import xyz.ioc.Account
import xyz.ioc.Donation
import xyz.ioc.DonationSubscription

class DonationsController {
	
	def commonUtilities

	@Secured([ ApplicationConstants.ROLE_ADMIN ])
	def index(){
		def count = Donation.count()
		def donations = Donation.list()
		def amount = donations.sum(){ it.amount } 
		amount = amount ? amount : 0

		def contributors = DonationSubscription.list()
		def monthly = contributors.sum(){ it.donationPlan.amount }
		monthly = monthly ? monthly : 0

		[ count: count, amount: amount, monthly: monthly ]
	}

}