package ioc

import org.springframework.dao.DataIntegrityViolationException

import xyz.ioc.Account
import xyz.ioc.Page
import xyz.ioc.Design
import xyz.ioc.ImportFile

import grails.plugin.springsecurity.annotation.Secured

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder


@Mixin(BaseController)
class PageController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def springSecurityService
    MessageSource messageSource
	

 	@Secured(['permitAll'])
	def ref(String title){
		println "t ${params?.title} -> ${title}"
		def t = java.net.URLDecoder.decode(params.title, "UTF-8");
		def pageInstance = Page.findByTitle(t)
		if(!pageInstance){
			flash.message = messageSource.getMessage("page.not.found", null, LocaleContextHolder.locale)
    	    redirect(action: "home")
		}

		[pageInstance : pageInstance]
	}



 	@Secured(['permitAll'])
	def view(Long id){   
    	def pageInstance = Page.get(id)
    	if (!pageInstance) {
			flash.message = messageSource.getMessage("page.not.found", null, LocaleContextHolder.locale)
    	    redirect(action: "home")
    	    return
    	} 

    	[pageInstance: pageInstance]
	}
	
	
	//TODO:if deleted in database
 	@Secured(['permitAll'])
	def home(){
		println params
		if(springSecurityService.isLoggedIn() && !flash.redirected){
			flash.redirected = true
			redirect(controller:"donations", action: "index")
			return
		}

		def pageInstance = Page.findByTitle("Home")
		if(!pageInstance){
			pageInstance = new Page(title: "Home", content: "Home page content.", design: Design.findByDefaultDesign(true))
			pageInstance.save(flush:true)
		}

		[ pageInstance: pageInstance ]
	}
	
	



 	@Secured(['ROLE_ADMIN'])
    def create() {
        [ pageInstance: new Page(params),  pages: Page.list(), designs: Design.list() ]
    }
	
	

 	@Secured(['ROLE_ADMIN'])
	def index() {
        redirect(action: "list", params: params)
    }

	
 	@Secured(['ROLE_ADMIN'])
	def list(Integer max) {
		println ">>> list"
    	params.max = Math.min(max ?: 10, 100)
    	[pageInstanceList: Page.list(params), pageInstanceTotal: Page.count()]
	}
	

	
 	@Secured(['ROLE_ADMIN'])
    def show(Long id) {
		def pageInstance = Page.get(params.id)
		if(!pageInstance){
			flash.message = messageSource.getMessage("page.not.found", null, LocaleContextHolder.locale)
    	    redirect(action:"list")
		}
		[ pageInstance: pageInstance, designs: Design.list() ]
    }
	
	

 	@Secured(['ROLE_ADMIN'])
    def edit(Long id) {
		def pageInstance = Page.get(params.id)
		if(!pageInstance){
			flash.message = messageSource.getMessage("page.not.found", null, LocaleContextHolder.locale)
    	    redirect(action:"list")
		}
        	
        [ pageInstance: pageInstance, pages: Page.list(), files: ImportFile.list(), designs: Design.list() ]	
    }



 	@Secured(['ROLE_ADMIN'])
    def save() {	
    	def pageInstance = new Page(params)
    	if (!pageInstance.save(flush: true)) {
			def designs = Design.list()
			flash.message = messageSource.getMessage("something.went.wrong.message", null, LocaleContextHolder.locale)
    	    render(view: "create", model: [pageInstance: pageInstance, designs: designs])
    	    return
    	}
    	
    	flash.message = messageSource.getMessage("successfully.saved", null, LocaleContextHolder.locale)
    	redirect(action: "edit", id: pageInstance.id)
	}
	
	
	

 	@Secured(['ROLE_ADMIN'])
    def update(Long id, Long version) {
		def pageInstance = Page.get(params.id)
		if(!pageInstance){
			flash.message = messageSource.getMessage("page.not.found", null, LocaleContextHolder.locale)
    	    redirect(action:"list")
    	    return
		}

    	pageInstance.properties = params
    	
    	if (!pageInstance.save(flush: true)) {
    		println "something went wrong"
			flash.message = messageSource.getMessage("something.went.wrong", null, LocaleContextHolder.locale)
    	    render(view: "edit", model: [pageInstance: pageInstance])
    	    return
    	}
    	
    	flash.message = messageSource.getMessage("successfully.updated", null, LocaleContextHolder.locale)
    	redirect(action: "edit", id: pageInstance.id)
		
    }
	
	
	
 	@Secured(['ROLE_ADMIN'])
    def delete(Long id) {
		def pageInstance = Page.get(params.id)
		if(!pageInstance){
			flash.message = messageSource.getMessage("page.not.found", null, LocaleContextHolder.locale)
    	    redirect(action:"list")
		}

	    pageInstance.delete(flush: true)

	    flash.message = messageSource.getMessage("successfully.deleted", null, LocaleContextHolder.locale)
	    redirect(action: "list")
    }
}
