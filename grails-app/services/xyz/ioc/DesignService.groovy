package xyz.ioc

import org.apache.shiro.SecurityUtils
import grails.util.Holders

class DesignService {

	def header
	def footer
	def wrapper

	def grailsApplication
	def applicationService


	DesignService(){
		if(!grailsApplication){
			grailsApplication = Holders.grailsApplication
		}
		if(!applicationService){
		    applicationService = grailsApplication.classLoader.loadClass("xyz.ioc.ApplicationService").newInstance()
		}
	}


	def render(pageInstance){
		setPageHeader(pageInstance)
		setPageFooter(pageInstance)
		def content = pageInstance?.content
		println ">> content >> ${pageInstance.content}" 
		return header + content + footer
	}


	def header(title){
		setHeader(title)
		return header
	}

	
	def footer(){
		setFooter()
		return footer
	}


	def setHeader(title){
		def design = Design.findByDefaultDesign(true)
		refreshBaseLayoutWrapper(design)

		def titleFull = applicationService.getSiteName() + " : ${title}" 
		header = header.replace("{{TITLE}}", titleFull)
	
		header = header.replace("{{CSS}}", design.css ? design.css : "")

		return header
	}


	def setFooter(){
		def design = Design.findByDefaultDesign(true)
		refreshBaseLayoutWrapper(design)

		footer = footer.replace("{{JAVASCRIPT}}", design.javascript ? design.javascript : "")
		return footer
	}



	def setPageHeader(pageInstance){
		refreshBaseLayoutWrapper(pageInstance.design)

		def titleFull = applicationService.getSiteName() + " : " + pageInstance.title
		header = header.replace("{{TITLE}}", titleFull)

		header = header.replace("{{CSS}}", pageInstance?.design?.css ? pageInstance?.design?.css : "")

		return header
	}

	
	def setPageFooter(pageInstance){
		footer = footer.replace("{{JAVASCRIPT}}", pageInstance?.design?.javascript ? pageInstance?.design?.javascript : "")
		return footer
	}


	def refreshBaseLayoutWrapper(design){
		if(!grailsApplication){
			grailsApplication = Holders.grailsApplication
		}
		File designFile = grailsApplication.mainContext.getResource("templates/web/wrapper.html").file
		wrapper = designFile.text
		wrapper = wrapper.replace("{{LAYOUT}}", design.content)
		String[] split = wrapper.split("\\{\\{CONTENT\\}\\}");
		header = renderHtmlTags(split[0])
		footer = renderHtmlTags(split[1])
	}

	
	def renderHtmlTags(section){
		section = section.replace("{{ACCOUNT}}", getAccount())
		section = section.replace("{{GREETING}}", getGreeting())
		section = section.replace("{{LOGIN}}", getSignin())
		section = section.replace("{{LOGOUT}}", getLogout())
		section = section.replace("{{REGISTER}}", getRegister())
		section = section.replace("{{CONTEXT_NAME}}", applicationService.getContextName())
		
		if(section.contains("{{GOOGLE_ANALYTICS}}")) section = section.replace("{{GOOGLE_ANALYTICS}}", applicationService.getGoogleAnalyticsCode())
		
		return section
	}


	
	def getAccount(){
		def subject = SecurityUtils.getSubject()
		if(subject.isAuthenticated()){
			return "<a href=\"/${applicationService.getContextName()}/account/customer_profile\" id=\"my-account\">My Account</a>"
		}else{
			return ""
		}
	}


	def getGreeting(){
		def subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()){
			return "<span id=\"greeting\">Welcome back <a href=\"/${applicationService.getContextName()}/account/customer_profile\">${subject.principal}</a></span>"
		}else{
			return "<span></span>"
		}
	}
	

	def getSignin(){
		def subject = SecurityUtils.getSubject()
		if(!subject.isAuthenticated()){
			return "<a href=\"/${applicationService.getContextName()}/auth/customer_login\" id=\"login\">Login</a>"
		}else{
			return ""
		}
	}
	
	def getLogout(){
		def subject = SecurityUtils.getSubject()
		if(subject.isAuthenticated()){
			return "<a href=\"/${applicationService.getContextName()}/logout\" id=\"logout\">Logout</a>"
		}else{
			return ""
		}
		
	}
	
	def getRegister(){
		def subject = SecurityUtils.getSubject();
		if(!subject.isAuthenticated()){
			return "<a href=\"/${applicationService.getContextName()}/account/customer_registration\" id=\"register\">Register</a>"
		}else{
			return "<span></span>"
		}
	}
	

}