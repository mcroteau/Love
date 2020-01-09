package xyz.ioc

class ImportFile {
	
	String uri
	String name
	
	Date dateCreated
	Date lastUpdated

    static constraints = {
    	uri(nullable:false)
    	name(nullable:false)
    }

}