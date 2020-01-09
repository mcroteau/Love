package xyz.ioc

class DonationPlan {

	int amount

	String stripeId
	String nickname
	String description
	String frequency
	String currency
	
	DonationProduct donationProduct 

	static constraints = {
        amount(nullable:false)
		stripeId(nullable:false)
        nickname(nullable:false, unique:true)
        description(nullable:true)
        frequency(nullable:false)
        currency(nullable:false)
    }
}

