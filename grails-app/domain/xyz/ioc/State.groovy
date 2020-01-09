package xyz.ioc

class State {
	
	String name
	
	Country country
	static belongsTo = [ Country ]
	
	static mapping = {
	    sort "name"
	}
		
    static constraints = {
		name(unique:true)
    }
}
