package xyz.ioc

class Role {
	
	String authority

	Role(String authority) {
		this()
		this.authority = authority
	}

	static constraints = {
		authority(blank: false, unique: true)
	}

}
