package xyz.ioc

class Page {
	
	String title
	String content
	Design design
	
	Date dateCreated
	Date lastUpdated
	
	static mapping = {
		content type: "text"
	}
	
    static constraints = {
		title(nullable:false, unique:true)
		content(size:0..65535)
    }

}