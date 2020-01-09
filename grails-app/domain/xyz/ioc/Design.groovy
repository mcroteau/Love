package xyz.ioc

class Design {

	String name
	String content
	String css
	String javascript
	boolean defaultDesign
	
	Date dateCreated
	Date lastUpdated
	
	static mapping = {
		content type: "text"
		css type : "text"
		javascript type : "text"
	}
	
	static constraints = {
		name(nullable:false, unique:true)
		content(blank:false, nullable:false, size:0..65535)
		css(blank:true, nullable:true, size:0..65535)
		javascript(blank:true, nullable:true, size:0..65535)
		defaultDesign(nullable:false, default:false)
    }
	
	public String toString(){
		return this.content
	}
}
