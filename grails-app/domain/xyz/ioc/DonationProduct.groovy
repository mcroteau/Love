package xyz.ioc

class DonationProduct {
	
	String stripeId
	
	String name
	String type

	static constraints = {
		stripeId(nullable:false)
        name(nullable:false)
        type(nullable:false)
    }

}