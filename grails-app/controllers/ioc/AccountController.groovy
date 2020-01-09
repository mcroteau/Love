package ioc

import static org.springframework.http.HttpStatus.OK

import grails.plugin.springsecurity.annotation.Secured
import groovy.text.SimpleTemplateEngine

import xyz.ioc.common.ApplicationConstants
import xyz.ioc.Account
import xyz.ioc.Role
import xyz.ioc.AccountRole
import xyz.ioc.Permission
import xyz.ioc.Country


import java.util.regex.Matcher
import java.util.regex.Pattern

import grails.util.Environment


class AccountController {

    static allowedMethods = [ save: "POST", update: "POST", delete: "POST", reset_password: "POST"]
	
	
	def emailService
	def phoneService
	def applicationService
	def springSecurityService
	def commonUtilities
	
    String csvMimeType
    String encoding

	@Secured([ApplicationConstants.ROLE_ADMIN])
	def index(){
    	def max = 10
		def offset = params?.offset ? params.offset : 0
		def sort = params?.sort ? params.sort : "id"
		def order = params?.order ? params.order : "asc"
		
		def accountInstanceList = []
		def accountInstanceTotal = 0

		def hasAdminRole = (params?.admin && params?.admin == "true") ? true : false
		
		if(params.query){
			
			def accountCriteria = Account.createCriteria()
			def countCriteria = Account.createCriteria()

			accountInstanceTotal = countCriteria.count(){
				and{
					or {
						ilike("username", "%${params.query}%")
						ilike("name", "%${params.query}%")
					}
					eq("hasAdminRole", hasAdminRole)
				}
			}
			
			
			accountInstanceList = accountCriteria.list(max: max, offset: offset, sort: sort, order: order){
				and{
					or {
						ilike("username", "%${params.query}%")
						ilike("name", "%${params.query}%")
					}
					eq("hasAdminRole", hasAdminRole)
				}
			}
		
		}else{
			accountInstanceList = Account.findAllByHasAdminRole(hasAdminRole, [max: max, offset: offset, sort: sort, order: order])
			accountInstanceTotal = Account.countByHasAdminRole(hasAdminRole)
		}
		
		[ accountInstanceList: accountInstanceList, accountInstanceTotal: accountInstanceTotal, admin: hasAdminRole, query : params.query ]
	}
	
	
	
	@Secured([ApplicationConstants.ROLE_ADMIN])
	def create(){
		if(params.admin == "true")request.admin = "true"
		
    	[accountInstance: new Account(params), countries: Country.list()]
	}
	
	
	@Secured([ApplicationConstants.ROLE_ADMIN])
	def show(Long id){
   		def accountInstance = Account.get(id)
   		if (!accountInstance) {
   		    flash.message = "Account not found"
   		    redirect(action: "index")
   		    return
   		}  		
   		[accountInstance: accountInstance, countries: Country.list()]	
	}

	
	
	@Secured([ApplicationConstants.ROLE_ADMIN])
	def save(){
		def accountInstance = new Account(params)
		
   		def password = springSecurityService.encodePassword(params.password)
		accountInstance.password = password

		def includeAdminRole = false
		if(params.admin == "true" ||
				params.admin == "on"){
			includeAdminRole = true
		}
		
		accountInstance.hasAdminRole = includeAdminRole
		
		if(accountInstance.validate()){
			accountInstance.save(flush:true)
			
			accountInstance.createAccountRoles(includeAdminRole)
			accountInstance.createAccountPermission()

	       	flash.message = "Account successfully created..."
	       	redirect(action: "index")
			
		}else{
			def message = "Something went wrong while saving account.<br/>"
			message = message + "Please make sure username is a valid email address and is not already in use.<br/>"
			
			flash.message = message
			
		    accountInstance.errors.allErrors.each {
		        println it
		    }
			redirect(action: 'create', accountInstance: accountInstance, params: params)
		}
	}
	
	@Secured([ApplicationConstants.ROLE_ADMIN])
	def edit(Long id){
    	def accountInstance = Account.get(id)
    	if (!accountInstance) {
    	    flash.message = "Account not found"
    	    redirect(action: "index")
    	    return
    	}   	

		def admin = false
		if(accountInstance.hasAdminRole)admin = true
		
    	[accountInstance: accountInstance, admin : admin, countries: Country.list()]
	}
	
	
	@Secured([ApplicationConstants.ROLE_ADMIN])
	def update(Long id){
		def accountInstance = Account.get(id)
		
   		if (!accountInstance) {
   		    flash.message = "Account not found"
   		    redirect(action: "index")
   		    return
   		}

		
		accountInstance.properties = params
		def adminRole = Role.findByAuthority(ApplicationConstants.ROLE_ADMIN)
		

		if(params.admin == "true" ||
				params.admin == "on"){
			accountInstance.createAccountRole(adminRole)
			accountInstance.hasAdminRole = true
		}else{
			def accountRole = AccountRole.findByRoleAndAccount(adminRole, accountInstance)
			if(accountRole){
				accountRole.delete(flush:true)
				accountInstance.hasAdminRole = false
			}
		}

   		if (!accountInstance.save(flush: true)) {
   			flash.message = "Something went wrong when updating account, please try again..."
   		    render(view: "edit", model: [accountInstance: accountInstance])
   		    return
   		}
   		
   		flash.message = "Account successfully updated"
   		redirect(action: "edit", id: accountInstance.id)
	}
	
	
	
	@Secured([ApplicationConstants.ROLE_ADMIN])
	def delete(Long id){
		def accountInstance = Account.get(id)
    	if (!accountInstance) {
    	    flash.message = "Account not found..."
    	    redirect(action: "index")
    	    return
    	}
		
		try {
			
			def accountRoles = AccountRole.findAllByAccount(accountInstance)
			accountRoles.each(){
				it.delete(flush:true)
			}

			def permissions = Permission.findAllByAccount(accountInstance)
			permissions.each(){
				it.delete(flush:true)
			}
            accountInstance.delete(flush: true)
            flash.message = "Successfully deleted the account...."
            redirect(action: "index")
        
        } catch (Exception e) {
			e.printStackTrace()
            flash.message = "Something went wrong while trying to delete."
            redirect(action: "edit", id: id)
        }
	}
	
	
	
	@Secured([ApplicationConstants.PERMIT_ALL])
	def forgot(){}



	@Secured([ApplicationConstants.PERMIT_ALL])	
	def send_forgot(){

		if(params.username){

			def accountInstance = Account.findByUsername(params.username)
			if(accountInstance){
				def resetUUID = UUID.randomUUID()
				accountInstance.resetUUID = resetUUID
				accountInstance.save(flush:true)
				
				def url = request.getRequestURL()
				
				def split = url.toString().split("/${applicationService.getContextName()}/")
				def httpSection = split[0]
				def resetUrl = "${httpSection}/${applicationService.getContextName()}/abcrAccount/confirm_reset?"
				def params = "username=${accountInstance.username}&uuid=${resetUUID}"
				resetUrl+= params
				
				//http://localhost:9463/abcr/abcrAccount/confirm_reset?username=admin@mail.com&uuid=e4cd4247-7a92-4e5c-9c38-0de54021e3fc
				println "reset url " + resetUrl
				
				sendResetPasswordEmail(accountInstance, resetUrl)
			}else{
				flash.message = "Account not found with following username : ${params.username}"
				redirect(action: "forgot")
			}
		}else{
			flash.message = "Please enter an email to continue the reset password process"
			redirect(action: "forgot")
		}
	
	}
	

	@Secured([ApplicationConstants.PERMIT_ALL])
	def confirm_reset(){
		def accountInstance = Account.findByUsernameAndResetUUID(params.username, params.uuid)
		
		if(!accountInstance){
			flash.message = "Something went wrong, please try again."
			redirect(action: 'forgot')
		}	

		[accountInstance: accountInstance]	
	}
	
	
	@Secured([ApplicationConstants.PERMIT_ALL])
	def reset_password(){
		def username = params.username
		def newPassword = params.password
		def confirmPassword = params.confirmPassword
		
		def accountInstance = Account.findByUsername(username)
		if(accountInstance && newPassword && confirmPassword){	
		
			if(confirmPassword == newPassword){
			
				if(newPassword.length() >= 5){
					
					def password = springSecurityService.encodePassword(newPassword)
					accountInstance.password = password
					
					if(accountInstance.save(flush:true)){
				
						//def authToken = new UsernamePasswordToken(username, newPassword as String)					
						flash.message = "Successfully reset password... Login with new credentials"
						redirect(controller : "login", action : "auth", params : [ username : username ])
						return

					}else{
						flash.message = "We were unable to reset your password, please try again."
						redirect(action:'confirm_reset', params : [username : username, uuid : accountInstance.resetUUID ])
					}
				}else{
					flash.message = "Passwords must be at least 5 characters in length. Please try again"
					redirect(action: 'confirm_reset', params : [uuid : accountInstance.resetUUID, username : username])
				}

			}else{
				flash.message = "Passwords must match. Please try again"
				redirect(action: 'confirm_reset', params : [uuid : accountInstance.resetUUID, username : username])
				
			}
		}else{
			flash.message = "Password cannot be blank, please try again."
			redirect(action: 'confirm_reset', params : [uuid : accountInstance.resetUUID, username : username])
		}
	}
	
	

	@Secured([ApplicationConstants.ROLE_ADMIN])
	def change_password(Long id){
		def accountInstance = Account.get(id)
		if(!accountInstance){
			flash.message = "Account not found with id : " + id + ". Please try again"
			redirect(action:"index")
			return
		}
		[accountInstance: accountInstance]
	}
	
	@Secured([ApplicationConstants.ROLE_ADMIN])
	def update_password(){
		def accountInstance = Account.get(params.id)
		if(!accountInstance){
			flash.message = "Account not found with id : " + params.id + ". Please try again"
			redirect(action:"index")
			return
		}
		def password = params.password
		if(password.length() < 5){
			flash.message = "Password needs to be 5 characters in length at the least."
			redirect(action:"index")
			return
		}
		
		accountInstance.password = springSecurityService.encodePassword(password)
		accountInstance.save(flush:true)
		
		flash.message = "Password successfully updated..."
		redirect(action:"edit", id : accountInstance.id)
	}
	



	@Secured([ApplicationConstants.ROLE_ADMIN, ApplicationConstants.ROLE_CUSTOMER])
	def account(){
		def accountInstance = commonUtilities.getAuthenticatedAccount()
		[accountInstance : accountInstance]
	}	


	@Secured([ApplicationConstants.ROLE_ADMIN, ApplicationConstants.ROLE_CUSTOMER])
	def update_account(){
		def accountInstance = commonUtilities.getAuthenticatedAccount()

		accountInstance.properties = params
		accountInstance.phone = params.phone

		println "phone : ${accountInstance.name} : ${params.name}"

		def password = params.password
		accountInstance.name = params.name
		
		if(password){
			accountInstance.password = springSecurityService.encodePassword(password)
		}

		accountInstance.save(flush:true)

		println "phone : ${accountInstance.phone}"

		render(view:"account", model: [accountInstance: accountInstance])
	}	

	
	def sendResetPasswordEmail(accountInstance, resetUrl){
		try { 
		
			def fromAddress = applicationService.getSupportEmailAddress()
			//def toAddress = accountInstance.username
			//TODO:change
			def toAddress = "croteau.mike@gmail.com"
			def subject = "${applicationService.getCompanyName()}: Reset password"

			
			File templateFile = grailsAttributes.getApplicationContext().getResource(  "/templates/email/password_reset.html").getFile();

			def binding = [ "companyName" : applicationService.getCompanyName(),
				 			"supportEmail" : applicationService.getSupportEmailAddress(),
							"resetUrl": resetUrl ]
			def engine = new SimpleTemplateEngine()
			def template = engine.createTemplate(templateFile).make(binding)
			def bodyString = template.toString()
			
			
			emailService.send(toAddress, fromAddress, subject, bodyString)
			
		}catch(Exception e){
			e.printStackTrace()
		}
	}


	@Secured([ApplicationConstants.PERMIT_ALL])
	def review(){
		def accountInstance = commonUtilities.getAuthenticatedAccount()
		if(!accountInstance){
			flash.message = flash.message + " You must sign in to continue."
			redirect(controller: "login", action: "signin")
			return
		}
		[accountInstance: accountInstance ]
	}	


	@Secured([ApplicationConstants.ROLE_ADMIN, ApplicationConstants.ROLE_CUSTOMER])
	def update_guest(){
		def account = commonUtilities.getAuthenticatedAccount()


		if(params.phone.contains(" ")){
			flash.message = "Phone cannot contain spaces"
			redirect(action: "review")
			return
		}


		if(commonUtilities.containsSpecialCharacters(params.phone)){
			flash.message = "Phone cannot contain special characters or spaces, just numbers. Thank you."
			redirect(action: "review")
			return
		}

		// if(params.phone){
		// 	def valid = phoneService.validate(params.phone, account)

		// 	if(!valid){
		// 		flash.message = "Phone entered is not valid, please enter a valid phone number"
		// 		redirect(action: "review")
		// 		return
		// 	}
		// }

		account.properties = params
		account.phone = params.phone


		def password = params.password
		account.name = params.name
		
		if(password){
			account.password = springSecurityService.encodePassword(password)
		}

		account.save(flush:true)

		flash.message = "Successfully updated account information"
		redirect(action:"review")
	}	


	@Secured([ApplicationConstants.ROLE_ADMIN, ApplicationConstants.ROLE_CUSTOMER])
	def member_search(){
		if(!params.q){
			flash.message = "Search must be at least 3 characters in length"
			redirect(controller:"dashboard", action:"index")
		}

		def accountCriteria = Account.createCriteria()
		
		def accountInstanceList = accountCriteria.list(){
			and{
				or {
					ilike("name", "%${params.q}%")
				}
			}
		}

		[ accountInstanceList : accountInstanceList, q: params.q ]

	}
		

	@Secured([ApplicationConstants.ROLE_ADMIN, ApplicationConstants.ROLE_CUSTOMER])
	def search(){
		def account = commonUtilities.getAuthenticatedAccount()
		if(!account.subscriber){
			flash.message = "All access for only \$3.29"
			redirect(controller: "zula", action: "pay")
			return
		}
		def accountCriteria = Account.createCriteria()
		def accountInstanceList = accountCriteria.list(){
			and{
				or {
					ilike("name", "%${params?.q}%")
				}
			}
		}

		[ accountInstanceList : accountInstanceList, q: params.q ]
	}
		

	@Secured([ApplicationConstants.PERMIT_ALL])
	def signup(){
		//phoneService.support("${applicationService.getSiteName()} ${request.remoteHost}")
		[ params: params ]
	}


	@Secured([ApplicationConstants.PERMIT_ALL])
	def register(){

		def accountInstance = new Account(params)


		if(!params.password || params.password.size() < 7){
			flash.message = "Password must be 9 characters in length"
			redirect(action: "signup", params : params)
			return
		}


		if(!params.username){
			flash.message = "Please enter an email address to continue..."
			redirect(action: "signup", params : params)
			return
		}


		if(params.username.contains(" ")){
			flash.message = "Your username contains spaces, no spaces are allowed, sorry."
			redirect(action: "signup", params : params)
			return
		}
		
		
		if(params.password && params.passwordConfirm){
			
			if(params.password == params.passwordConfirm){

				if(params.password.length() >= 7){
				
					params.ipAddress = request.getRemoteHost()
					accountInstance.properties = params
					accountInstance.subscriber = false
					
					def existingAccountUsername = Account.findByUsername(params.username)
					if(existingAccountUsername){
						flash.message = "An account with us with the following username " + params.username + " already exists... "
						redirect(controller: "login", action: "auth")
						return
					}
					
					def password = springSecurityService.encodePassword(params.password)
			   		accountInstance.password = password
		
					if(accountInstance.save(flush:true)){
					
						accountInstance.hasAdminRole = false//TODO:used for easy searching in admin
						accountInstance.createAccountRoles(false)
						accountInstance.createAccountPermission()

						springSecurityService.reauthenticate(accountInstance.username)

						sendAdminEmail(accountInstance)
						//phoneService.support("${applicationService.getSiteName()}: Registration ${accountInstance.username}")
						sendThankYouEmail(accountInstance)

						flash.message = "You have successfully registered... "
						redirect(controller : 'login', action: 'auth', params : [ accountInstance: accountInstance, username : params.username, password : params.password, new_account : true])
		
					}else{
						flash.message = "There was a problem with your registration, please try again or contact the administrator"
						render(view: "signup", model: [accountInstance: accountInstance])
						return
					}
					
				
				}else{
					flash.message = "Password must be at least 7 characters long.  Please try again"
					render(view: "signup", model: [accountInstance: accountInstance])
				}
	
			}else{
				//passwords don't match
				flash.message = "Passwords don't match.  Please try again"
				render(view: "signup", model: [accountInstance: accountInstance])
			}
		}else{
			flash.message = "Passwords cannot be blank"
			render(view: "signup", model: [accountInstance: accountInstance])
		}
	
	}


	def sendAdminEmail(Account accountInstance){
		try { 
			
			def fromAddress = applicationService.getSupportEmailAddress()
			if(fromAddress){

				def customerSubject = "Registration."
			
				File templateFile = grailsAttributes.getApplicationContext().getResource(  "/templates/email/registration_notification.html").getFile();
	    	
				def binding = [ "companyName" : "∑ Sigma",
					 			"accountInstance" : accountInstance ]
				def engine = new SimpleTemplateEngine()
				def template = engine.createTemplate(templateFile).make(binding)
				def bodyString = template.toString()
				
				def thread = Thread.start {
					emailService.send(applicationService.getAdminEmailAddress(), fromAddress, customerSubject, bodyString)
				}
				
			}

		}catch(Exception e){
			e.printStackTrace()
		}
	}



	@Secured(['ROLE_ADMIN'])
    def export(){
        def accountsCsvArray = []
        def accounts = Account.list()

        accounts.eachWithIndex { account, index ->
        	def accountLine = ""
        	accountLine+= account?.name + ","
        	accountLine+= account?.username + ","
        	
        	def location = account?.location ? account.location.replaceAll(",", "") : ""
        	location = location ? location.replaceAll("[\r\n]+", " ") : ""

			accountLine+= location + ","
			accountLine+= account?.phone
			accountsCsvArray.add(accountLine)
        }

        def filename = "donors.csv"
        def outs = response.outputStream
        response.status = OK.value()
        response.contentType = "${csvMimeType};charset=${encoding}";
        response.setHeader "Content-disposition", "attachment; filename=${filename}"
 
        accountsCsvArray.each { String line ->
        	println line
            outs << "${line}\n"
        }
 
        outs.flush()
        outs.close()
    } 

	def sendThankYouEmail(Account accountInstance){
		try { 
			def customerToAddress = accountInstance.username
			def customerSubject = "Thank you for registering"
			
			File templateFile = grailsAttributes.getApplicationContext().getResource(  "/templates/email/registration.html").getFile();

			def binding = [ "companyName" : "∑ Sigma",
				 			"supportEmail" : applicationService.getSupportEmailAddress()]
			def engine = new SimpleTemplateEngine()
			def template = engine.createTemplate(templateFile).make(binding)
			def bodyString = template.toString()
			
			emailService.send(customerToAddress, customerSubject, bodyString)		
		}catch(Exception e){
			e.printStackTrace()
		}
	}



}