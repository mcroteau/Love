package xyz.ioc

class DonationSubscription {

	String stripeId
	
	Account account
	DonationPlan donationPlan

	static constraints = {
		stripeId(nullable:true)
	}
}