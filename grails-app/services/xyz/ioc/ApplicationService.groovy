package xyz.ioc

import java.math.BigDecimal
import java.text.NumberFormat
import groovy.text.SimpleTemplateEngine
import org.springframework.web.context.request.RequestContextHolder

import grails.web.servlet.mvc.GrailsParameterMap 
import grails.util.Holders


class ApplicationService {
    
	def grailsApplication
	def springSecurityService
	
	
	static scope = "singleton"
	static transactional = true
	
	def properties


	def init(){
		setProperties()
	}
	
	
	def getContextName(){
		setProperties()
		String contextName = properties.getProperty("app.context");
		//println "++++++++ Company Name : ${contextName} +++++++++++"	
		return contextName
	}


	def getSiteName(){
		if(!properties){
			setProperties()
		}
		String siteName = properties.getProperty("site.name");
		return siteName ? siteName : ""	
	}

	
	def getSitePhone(){
		if(!properties){
			setProperties()
		}
		String sitePhone = properties.getProperty("site.phone");
		//println "++++++++ Company Phone : ${companyPhone} +++++++++++"	
		return sitePhone ? sitePhone : ""	
	}
	
	
    def getGoogleAnalyticsCode() {
		if(!properties)setProperties()
		String googleAnalytics = properties.getProperty("google.analytics");	
		return googleAnalytics ? googleAnalytics : ""
    }

	
	/******** MAIL SETTINGS ********/
	
	def getAdminEmailAddress(){
		if(!properties)setProperties()
		String adminEmail = properties.getProperty("mail.smtp.adminEmail");
		//println "++++++++ adminEmail : ${adminEmail} +++++++++++"	
		return adminEmail
	}
	
	def getSupportEmailAddress(){
		if(!properties)setProperties()
		String supportEmail = properties.getProperty("mail.smtp.supportEmail");
		//println "++++++++ supportEmail : ${supportEmail} +++++++++++"	
		return supportEmail
	}
		
	def getMailUsername(){
		if(!properties)setProperties()
		String username = properties.getProperty("mail.smtp.username");
		//println "++++++++ apiKey : ${username} +++++++++++"	
		return username
	}
	
	def getMailPassword(){
		if(!properties)setProperties()
		String password = properties.getProperty("mail.smtp.password");
		//println "++++++++ password : ${password} +++++++++++"	
		return password
	}
		
	def getMailAuth(){
		if(!properties)setProperties()
		String auth = properties.getProperty("mail.smtp.auth");
		//println "++++++++ auth : ${auth} +++++++++++"	
		return auth
	}
	
	def getMailStartTlsEnabled(){
		if(!properties)setProperties()
		String startTlsEnabled = properties.getProperty("mail.smtp.starttls.enabled");
		//println "++++++++ startTlsEnabled : ${startTlsEnabled} +++++++++++"	
		return startTlsEnabled
	}
	
	def getMailHost(){
		if(!properties)setProperties()
		String host = properties.getProperty("mail.smtp.host");
		//println "++++++++ host : ${host} +++++++++++"	
		return host	
	}
	
	def getMailPort(){
		if(!properties)setProperties()
		String port = properties.getProperty("mail.smtp.port");
		//println "++++++++ port : ${port} +++++++++++"	
		return port		
	}
	
	
	def getStripePublishable(){
		if(!properties)setProperties()
		String secret = properties.getProperty("stripe.publishable.key");
		//println "++++++++ secret : ${secret} +++++++++++"	
		return secret		
	}


	def getStripeDevPublishable(){
		if(!properties)setProperties()
		String secret = properties.getProperty("stripe.development.publishable.key");
		println "++++++++ secret : ${secret} +++++++++++"	
		return secret		
	}	

	
	def getStripeSecret(){
		if(!properties)setProperties()
		String secret = properties.getProperty("stripe.secret.key");
		//println "++++++++ secret : ${secret} +++++++++++"	
		return secret		
	}

	def getStripeDevSecret(){
		if(!properties)setProperties()
		String secret = properties.getProperty("stripe.development.api.key");
		//println "++++++++ secret : ${secret} +++++++++++"	
		return secret		
	}
		
	def getBraintreeEnabled(){
		if(!properties)setProperties()
		String enabled = properties.getProperty("braintree.enabled");
		//println "++++++++ braintree enabled : ${enabled} +++++++++++"	
		return enabled		
	}

	def getMultiCurrencyEnabled(){
		if(!properties)setProperties()
		String multiCurrencyEnabled = properties.getProperty("multi.currency.enabled");
		//println "++++++++ multiCurrencyEnabled : ${multiCurrencyEnabled} +++++++++++"	
		return multiCurrencyEnabled
	}

	def formatPrice(price){
		if(price){
 	   		BigDecimal numberBigDecimal = new BigDecimal(price);
			numberBigDecimal  = numberBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
			
			return numberBigDecimal
		}else{
			return new BigDecimal(0)
		}
	}
	
	def formatNumber(number){
		if(number){
			return NumberFormat.getNumberInstance(Locale.US).format(number)
		}
		else{
			return 0
		}
	}

	
	def formatTotal(price){
		if(price){
 	   		BigDecimal numberBigDecimal = new BigDecimal(price);
			numberBigDecimal  = numberBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
			
			return numberBigDecimal
		}else{
			return new BigDecimal(0)
		}
	}
	
	
	def formatAmount(price){
		if(price){
 	   		BigDecimal numberBigDecimal = new BigDecimal(price);
			numberBigDecimal  = numberBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
			
			return numberBigDecimal
		}else{
			return new BigDecimal(0)
		}
	}
	
	
	def hasAdministrationRole(){
		if(!springSecurityService){
			springSecurityService = grailsApplication.classLoader.loadClass("grails.plugin.springsecurity.SpringSecurityService").newInstance()
		}
		def principal = springSecurityService.principal
		def authorities = principal.authorities
		def admininstrator = false
		authorities.each() { role ->
			if(role.authority == "ROLE_ADMIN")admininstrator = true
		}	
		return admininstrator
	}
	
	
	def setProperties(){
		properties = new Properties();
		try{
			if(!grailsApplication){
				grailsApplication = Holders.grailsApplication
			}
			
			java.io.File propertiesFile = grailsApplication.mainContext.getResource("settings/settings.properties").file
			
			FileInputStream inputStream = new FileInputStream(propertiesFile)
			if(inputStream){
				properties.load(inputStream);
			}
		} catch (IOException e){
		    log.debug"Exception occured while reading properties file :"+ e
		}
	}
}
