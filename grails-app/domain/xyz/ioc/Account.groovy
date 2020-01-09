package xyz.ioc

import xyz.ioc.AccountRole
import xyz.ioc.Permission
import xyz.ioc.Role

import xyz.ioc.common.ApplicationConstants

class Account {

	String username
    String password
	
	String name	
	String phone
	
	String resetUuid
	
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	boolean hasAdminRole

	String location
	
	Date dateCreated
	Date lastUpdated

	String stripeId
	boolean donor
	DonationSubscription donationSubscription

	static hasMany = [ permissions: Permission ]
	
	static mapping = {
		location type : "text"
	}
	

	Set<Role> getAuthorities() {
		AccountRole.findAllByAccount(this)*.role
	}

	def createAccountPermission(){
		createPermission(ApplicationConstants.ACCOUNT_PERMISSION + this.id)
	}

	def createAdminAccountRole(){
		def adminRole = Role.findByAuthority(ApplicationConstants.ROLE_ADMIN)
		createAccountRole(adminRole)
	}

	def createAccountRoles(includeAdminRole){
		this.hasAdminRole = false
		this.save(flush:true)

		def role = Role.findByAuthority(ApplicationConstants.ROLE_CUSTOMER)
		createAccountRole(role)
	
		if(includeAdminRole){
			def adminRole = Role.findByAuthority(ApplicationConstants.ROLE_ADMIN)
			createAccountRole(adminRole)
			this.hasAdminRole = true
		}

		this.save(flush:true)
	}

	def createAccountRole(role){
		if(!this)throw new Exception("Account didn't save correctly")
		def accountRole = new AccountRole()
		accountRole.account = this
		accountRole.role = role
		accountRole.save(flush:true)	
	}

	def createPermission(permissionString){
		def permission = new Permission()
		permission.account = this
		permission.permission = permissionString
		permission.save(flush:true)

		this.addToPermissions(permission)
		this.save(flush:true)
	}


	def getNameEmail(){
		if(this.name){
			return this.name
		}else{
			return this.email
		}
	}
	
	static constraints = {
        username(email:true, nullable:false, blank:false, unique: true)
		password(minSize: 5, nullable:false, blank:false, column: '`password`')
		name(blank:true, nullable:true)
		phone(nullable:true)
		hasAdminRole(nullabe:true)
		resetUuid(nullable:true)
		enabled(nullable:true, default:true)
		accountExpired(nullable:true)
		accountLocked(nullable:true)
		passwordExpired(nullable:true)
		location(nullable:true)
		stripeId(nullable:true)
		donor(nullable:true)
		donationSubscription(nullable:true)
    }
	
}
