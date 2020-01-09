package xyz.ioc

class Donation {

	int amount
	String stripeId

	Date dateCreated
	Date lastUpdated

	static belongsTo = [ account: Account ]

	static constraints = {
		amount(nullable:false)
	}
	
}