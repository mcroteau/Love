package ioc

import java.io.FileOutputStream;
import java.io.FileInputStream

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

import xyz.ioc.common.ApplicationConstants
import grails.plugin.springsecurity.annotation.Secured


class SettingsController {
	
	MessageSource messageSource

	private final String SETTINGS_FILE = "settings.properties"

	private final String SITE_CURRENCY = "site.currency"
	private final String SITE_COUNTRY_CODE = "site.country.code"
	
	private final String SITE_NAME = "site.name"
	private final String SITE_PHONE = "site.phone"
	private final String SITE_EMAIL = "site.email"
	
	private final String MAIL_ADMIN_EMAIL_ADDRESS = "mail.smtp.adminEmail"
	private final String MAIL_SUPPORT_EMAIL_ADDRESS = "mail.smtp.supportEmail"
	private final String MAIL_USERNAME = "mail.smtp.username"
	private final String MAIL_PASSWORD = "mail.smtp.password"
	private final String MAIL_HOST = "mail.smtp.host"
	private final String MAIL_PORT = "mail.smtp.port"
	private final String MAIL_STARTTLS = "mail.smtp.starttls.enabled"
	private final String MAIL_AUTH = "mail.smtp.auth"

	private final String STRIPE_ENABLED_KEY = "stripe.enabled"
	private final String STRIPE_DEVELOPMENT_API_KEY = "stripe.development.api.key"
	private final String STRIPE_DEVELOPMENT_PUBLISHABLE_KEY = "stripe.development.publishable.key"
	private final String STRIPE_PRODUCTION_API_KEY = "stripe.production.api.key"
	private final String STRIPE_PRODUCTION_PUBLISHABLE_KEY = "stripe.production.publishable.key"
	
	private final String BRAINTREE_ENABLED = "braintree.enabled"
	private final String BRAINTREE_MERCHANT_ID = "braintree.merchantId"
	private final String BRAINTREE_PUBLIC_KEY = "braintree.publicKey"
	private final String BRAINTREE_PRIVATE_KEY = "braintree.privateKey"
	
	def applicationService
	
	@Secured([ApplicationConstants.ROLE_ADMIN])
	def index(){
		Properties prop = new Properties();
		try{
		
			File propertiesFile = grailsApplication.mainContext.getResource("settings/${SETTINGS_FILE}").file
			FileInputStream inputStream = new FileInputStream(propertiesFile)
			prop.load(inputStream);
			
			def settings = [:]
			settings["siteName"] = prop.getProperty(SITE_NAME);
			settings["sitePhone"] = prop.getProperty(SITE_PHONE);
			settings["siteEmail"] = prop.getProperty(SITE_EMAIL);

			[ settings : settings ]
			
		} catch (IOException e){
		    log.debug"Exception occured while reading properties file :"+e
		} //TODO:add excpetion handler for IOException
	}
	
	
	
	@Secured([ApplicationConstants.ROLE_ADMIN])
	def save(){
		String siteName = params.siteName
		String sitePhone = params.sitePhone
		String siteEmail = params.siteEmail
		
		Properties prop = new Properties();
		
		File propertiesFile = grailsApplication.mainContext.getResource("settings/${SETTINGS_FILE}").file
		FileInputStream inputStream = new FileInputStream(propertiesFile)
		prop.load(inputStream);
		
		try{
		    
			prop.setProperty(SITE_NAME, siteName);
			prop.setProperty(SITE_PHONE, sitePhone);
			prop.setProperty(SITE_EMAIL, siteEmail);
			
			def absolutePath = grailsApplication.mainContext.servletContext.getRealPath('settings')
			
			absolutePath = absolutePath.endsWith("/") ? absolutePath : absolutePath + "/"
			println "abs ${SETTINGS_FILE}"
			def filePath = absolutePath + SETTINGS_FILE
			
		    prop.store(new FileOutputStream(filePath), null);
			applicationService.setProperties()
			
			flash.message = "Successfully saved settings..."
			redirect(action : 'index')
			
		} catch (IOException e){
		    log.debug"exception occured while saving properties file :"+e
			flash.message = "Something went wrong... "
			redirect(action : 'index')
			return
		}
			
	}
	
	
	@Secured([ApplicationConstants.ROLE_ADMIN])
	def email_settings(){			
		Properties prop = new Properties();
		try{
		
			File propertiesFile = grailsApplication.mainContext.getResource("settings/${SETTINGS_FILE}").file
			FileInputStream inputStream = new FileInputStream(propertiesFile)
			
			prop.load(inputStream);
			
			def email_settings = [:]
			email_settings["adminEmail"] = prop.getProperty(MAIL_ADMIN_EMAIL_ADDRESS)
			email_settings["supportEmail"] = prop.getProperty(MAIL_SUPPORT_EMAIL_ADDRESS)
			email_settings["username"] = prop.getProperty(MAIL_USERNAME)
			email_settings["password"] = prop.getProperty(MAIL_PASSWORD)
			email_settings["host"] = prop.getProperty(MAIL_HOST)
			email_settings["port"] = prop.getProperty(MAIL_PORT)
			
			def startTls = prop.getProperty(MAIL_STARTTLS)
			def auth = prop.getProperty(MAIL_AUTH)
			
			if(startTls == "true")email_settings["startTls"] = prop.getProperty(MAIL_STARTTLS)
			if(auth == "true")email_settings["auth"] = prop.getProperty(MAIL_AUTH)
			
			if(email_settings["startTls"])email_settings["startTls"] = "checked"
			if(email_settings["auth"])email_settings["auth"] = "checked"
			
			[ email_settings : email_settings ]
			
		} catch (IOException e){
		    log.debug"Exception occured while reading properties file :"+e
		}
	}
	
	
	
	@Secured([ApplicationConstants.ROLE_ADMIN])
	def save_email_settings(){
    	
		String adminEmail = params.adminEmail
		String supportEmail = params.supportEmail
		String username = params.username
		String password = params.password
		String host = params.host
		String port = params.port
		String startTls = params.startTls
		String auth = params.auth
		
		if(startTls == "on")startTls = true
		if(auth == "on")auth = true
		
		if(!startTls)startTls = false
		if(!auth)auth = false
		
		
		Properties prop = new Properties();
		
		File propertiesFile = grailsApplication.mainContext.getResource("settings/${SETTINGS_FILE}").file
		FileInputStream inputStream = new FileInputStream(propertiesFile)
		
		prop.load(inputStream);
		
		try{
			
			prop.setProperty(MAIL_ADMIN_EMAIL_ADDRESS, adminEmail)
			prop.setProperty(MAIL_SUPPORT_EMAIL_ADDRESS, supportEmail)
			prop.setProperty(MAIL_USERNAME, username);
			prop.setProperty(MAIL_PASSWORD, password);
			prop.setProperty(MAIL_HOST, host);
			prop.setProperty(MAIL_PORT, port);
			prop.setProperty(MAIL_STARTTLS, startTls)
			prop.setProperty(MAIL_AUTH, auth)
			
			def absolutePath = grailsApplication.mainContext.servletContext.getRealPath('settings')
			absolutePath = absolutePath.endsWith("/") ? absolutePath : absolutePath + "/"
			def filePath = absolutePath + SETTINGS_FILE
			
		    prop.store(new FileOutputStream(filePath), null);
			
			applicationService.setProperties()
			
			flash.message = "Successfully saved email settings..."
			redirect(action : 'email_settings')
			
		} catch (IOException e){
		    log.debug"exception occured while saving properties file :"+e
			flash.message = "Something went wrong... "
			redirect(action : 'email_settings')
			return
		}			
	}
	
	
	
	
 	@Secured(['ROLE_ADMIN'])
	def payment_settings(){
		Properties prop = new Properties();
		try{
	
			File propertiesFile = grailsApplication.mainContext.getResource("settings/${SETTINGS_FILE}").file
			FileInputStream inputStream = new FileInputStream(propertiesFile)
			prop.load(inputStream);
			
			def payment_settings = [:]
			
			payment_settings["storeCurrency"] = prop.getProperty(SITE_CURRENCY);
			payment_settings["storeCountryCode"] = prop.getProperty(SITE_COUNTRY_CODE);
			payment_settings["stripeDevApiKey"] = prop.getProperty(STRIPE_DEVELOPMENT_API_KEY)
			payment_settings["stripeDevPublishableKey"] = prop.getProperty(STRIPE_DEVELOPMENT_PUBLISHABLE_KEY)
			payment_settings["stripeProdApiKey"] = prop.getProperty(STRIPE_PRODUCTION_API_KEY)
			payment_settings["stripeProdPublishableKey"] = prop.getProperty(STRIPE_PRODUCTION_PUBLISHABLE_KEY)
			
			
			payment_settings["braintreeMerchantId"] = prop.getProperty(BRAINTREE_MERCHANT_ID)
			payment_settings["braintreePublicKey"] = prop.getProperty(BRAINTREE_PUBLIC_KEY)
			payment_settings["braintreePrivateKey"] = prop.getProperty(BRAINTREE_PRIVATE_KEY)
			
			
			[ payment_settings : payment_settings ]
			
		} catch (IOException e){
		    log.debug"Exception occured while reading properties file :" + e
			flash.message = messageSource.getMessage("something.went.wrong.message", null, LocaleContextHolder.locale)
		}
	}
	
	
	
	
 	@Secured(['ROLE_ADMIN'])
	def save_payment_settings(){

		//String enabled = params.enabled
		println params.storeCurrency
		String storeCurrency = params.storeCurrency
		String storeCountryCode = params.storeCountryCode
		String stripeDevApiKey = params.stripeDevApiKey
		String stripeDevPublishableKey = params.stripeDevPublishableKey
		String stripeProdApiKey = params.stripeProdApiKey
		String stripeProdPublishableKey = params.stripeProdPublishableKey
		
		String braintreeMerchantId = params.braintreeMerchantId
		String braintreePublicKey = params.braintreePublicKey
		String braintreePrivateKey = params.braintreePrivateKey
		
		
		//if(enabled == "on")enabled = true
		//if(!enabled)enabled = false
		
		
		Properties prop = new Properties();
		File propertiesFile = grailsApplication.mainContext.getResource("settings/${SETTINGS_FILE}").file
		FileInputStream inputStream = new FileInputStream(propertiesFile)
		
		prop.load(inputStream);
		
		try{
			
			//prop.setProperty(STRIPE_ENABLED_KEY, enabled);
			if(storeCurrency)prop.setProperty(STORE_CURRENCY, storeCurrency);
			if(storeCountryCode)prop.setProperty(STORE_COUNTRY_CODE, storeCountryCode);
			prop.setProperty(STRIPE_DEVELOPMENT_API_KEY, stripeDevApiKey);
			prop.setProperty(STRIPE_DEVELOPMENT_PUBLISHABLE_KEY, stripeDevPublishableKey);
			prop.setProperty(STRIPE_PRODUCTION_API_KEY, stripeProdApiKey);
			prop.setProperty(STRIPE_PRODUCTION_PUBLISHABLE_KEY, stripeProdPublishableKey);

			prop.setProperty(BRAINTREE_MERCHANT_ID, braintreeMerchantId);
			prop.setProperty(BRAINTREE_PUBLIC_KEY, braintreePublicKey);
			prop.setProperty(BRAINTREE_PRIVATE_KEY, braintreePrivateKey);
			
			
			def absolutePath = grailsApplication.mainContext.servletContext.getRealPath('settings')
			absolutePath = absolutePath.endsWith("/") ? absolutePath : absolutePath + "/"
			def filePath = absolutePath + SETTINGS_FILE
			
		    prop.store(new FileOutputStream(filePath), null);

			applicationService.setProperties()
			
			flash.message = messageSource.getMessage("successfully.saved", null, LocaleContextHolder.locale)
			redirect(action : 'payment_settings')
			
		} catch (IOException e){
		    log.debug"exception occured while saving properties file :"+e
			flash.message = messageSource.getMessage("something.went.wrong.message", null, LocaleContextHolder.locale)
			redirect(action : 'payment_settings')
			return
		}
	}
	
	
	
}