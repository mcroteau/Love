package xyz.ioc

class Country {
	
	String name
	
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [states : State]
	
    static constraints = {
		name(unique:true)
    }
}
