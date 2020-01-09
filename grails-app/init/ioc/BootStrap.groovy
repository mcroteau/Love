package ioc

import groovy.time.TimeCategory
import grails.util.Environment

import xyz.ioc.common.ApplicationConstants			
			
import xyz.ioc.CountryStateHelper
import xyz.ioc.Role
import xyz.ioc.Account
import xyz.ioc.Country		
import xyz.ioc.State	

import xyz.ioc.Page
import xyz.ioc.Design


class BootStrap {

	def customerRole
	def administratorRole
	
	def stripeService
	def commonUtilities
	def grailsApplication
	def springSecurityService

    
	def init = { servletContext ->

		println "***********************************************"
		println "*******        愛 Love Bootstrap        *******"
		println "***********************************************"
		createRoles()
		createAdministrator()
		createDesign()
		createPages()

		if(Environment.current == Environment.DEVELOPMENT) {
			//-> create dev
		}
		
    }
	

	def createRoles(){
		if(Role.count() == 0){
			administratorRole = new Role(authority : ApplicationConstants.ROLE_ADMIN).save(flush:true)
			customerRole = new Role(authority : ApplicationConstants.ROLE_CUSTOMER).save(flush:true)
		}else{
			administratorRole = Role.findByAuthority(ApplicationConstants.ROLE_ADMIN)
			customerRole = Role.findByAuthority(ApplicationConstants.ROLE_CUSTOMER)
		}
		
		println 'Roles : ' + Role.count()
	
	}
	
	
	def createAdministrator(){
		if(Account.count() == 0){
			def password = springSecurityService.encodePassword("password")
			def adminAccount = new Account(username : "croteau.mike+admin@gmail.com", password : password, name : 'Administrator')
			adminAccount.hasAdminRole = true
			adminAccount.save(flush:true)

			adminAccount.createAccountRoles(true)
			adminAccount.createAccountPermission()
		}		

		println "Accounts : " + Account.count()
	}



	def createMockAccounts(){
		def admin = Account.findByUsername("croteau.mike+admin@gmail.com")
		def password = springSecurityService.encodePassword("password")
		(0..9).each(){
			def account = new Account()
			account.username = "croteau.mike+${it}@gmail.com"
			account.password = password
			account.name = commonUtilities.randomString(7)
			account.location = commonUtilities.randomString(10) + " " + commonUtilities.randomString(2)
			account.save(flush:true)

			account.hasAdminRole = false
			account.save(flush:true)

			account.createAccountRoles(false)
			account.createAccountPermission()
			
		}

		println "Accounts : ${Account.count()}"
	}


	def createDesign(){
		
		File cssFile = grailsApplication.mainContext.getResource("css/app.css").file
		String css = cssFile.text

		def existingLayout = Design.findByName(ApplicationConstants.INITIAL_DESIGN)
		if(!existingLayout){	
			File designFile = grailsApplication.mainContext.getResource("templates/web/design.html").file
			String designContent = designFile.text

			def design = new Design()
			design.content = designContent
			design.name = ApplicationConstants.INITIAL_DESIGN
			design.css = css
			design.defaultDesign = true
			
			design.save(flush:true)
		}
	}

	
	def createPages(){
		def design = Design.findByDefaultDesign(true)
		createHomepage(design)
		createAboutUs(design)
		createContactUs(design)
		println "Pages : ${Page.count()}"
	}

	
	def createHomepage(design){
		def homepage = Page.findByTitle("Home")
		if(!homepage){
			def home = new Page()
			home.title = "Home"
			home.content = "Home page content."
			home.design = design
			home.save(flush:true)
		}
	}
	
	
	def createAboutUs(design){
		def aboutUs = Page.findByTitle("About Us")
		if(!aboutUs){
			def page = new Page()
			page.title = "About Us"
			page.content = "<p>About us.</p>"
			page.design = design
			page.save(flush:true)
		}
	}
	
	def createContactUs(design){
		def contactUs = Page.findByTitle("Contact Us")
		if(!contactUs){
			def page = new Page()
			page.title = "Contact Us"
			page.content = "Contact us."
			page.design = design
			page.save(flush:true)
		}
	}


	def createCountries(){
		if(Country.count() == 0){
			CountryStateHelper countryStateHelper = new CountryStateHelper()
			countryStateHelper.countryStates.each(){ countryData ->
				createCountryAndStates(countryData)
			}
		}
		println "Countries : ${Country.count()}"
		println "States : ${State.count()}"
	}
	
	
	def createCountryAndStates(countryData){
		def country = new Country()
		country.name = countryData.name
		country.save(flush:true)
		
		countryData.states.each(){ stateData ->
			def state = new State()
			state.country = country
			state.name = stateData
			state.save(flush:true)
		}
	}


	def q(){
	}
	
    def destroy = {
    }
}
