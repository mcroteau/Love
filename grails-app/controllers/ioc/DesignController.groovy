package ioc

import grails.plugin.springsecurity.annotation.Secured

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

import xyz.ioc.Design
import xyz.ioc.Page


class DesignController {

	def applicationService

	MessageSource messageSource

 	@Secured(['ROLE_ADMIN'])	
	def how(){}


 	@Secured(['ROLE_ADMIN'])
	def tags(){}
		
	
 	@Secured(['ROLE_ADMIN'])	
    def index() {	
		def designs = Design.list()
		[ designs: designs ]
	}
	
	
 	@Secured(['ROLE_ADMIN'])	
	def create(){
		[ pages: Page.list() ]
	}
	
	
 	@Secured(['ROLE_ADMIN'])	
	def save(){

		def design = new Design(params)
		println "content" + design.content
		println "css" + design.css
		println "javascript" + design.javascript
		
		
		if(!design.name){
			flash.message = messageSource.getMessage("design.name.empty.message", null, LocaleContextHolder.locale)
	        render(view: "create", model: [ design: design ])
	        return
		}

		
		def existingDesign = Design.findByName(design.name)
		if(existingDesign){
			println "existing..."
			flash.message = messageSource.getMessage("design.name.unique.message", null, LocaleContextHolder.locale)
	        render(view: "create", model: [design: design])
	        return
		}
		
		
		if(design.content && !design.content.contains("{{CONTENT}}")){
			flash.message = messageSource.getMessage("design.content.tag.message", null, LocaleContextHolder.locale)
	        render(view: "create", model: [design: design])
	        return
		}
		

		def existingDefaultDesigns = Design.findAllByDefaultDesign(true)
		
		if(design.defaultDesign){
			if(existingDefaultDesigns){
				existingDefaultDesigns.each { it ->
					it.defaultDesign = false
					it.save(flush:true)
				}
			}
		}else{
			if(!existingDefaultDesigns){
				design.defaultDesign = true
			}
		}
		
		if(!design.save(flush: true)) {
			flash.message = messageSource.getMessage("design.content.tag.message", null, LocaleContextHolder.locale)
	        render(view: "create", model: [design: design ])
	        return
	    }
		
		redirect(action:"edit", id: design.id)
	}


	
 	@Secured(['ROLE_ADMIN'])
	def edit(Long id){
		def design = Design.get(id)
		if(!design){
			flash.message = messageSource.getMessage("design.not.found", null, LocaleContextHolder.locale)
			redirect(action:"index")
		}
		[ designInstance: design, pages: Page.list()  ]
	}
	
	
	
 	@Secured(['ROLE_ADMIN'])
    def update(Long id) {
		def design = Design.get(id)
		if(!design){
			flash.message = messageSource.getMessage("design.not.found", null, LocaleContextHolder.locale)
			redirect(action:"index")
			return
		}

    	design.properties = params
		
		if(!design.name){
			flash.message = messageSource.getMessage("design.empty.message", null, LocaleContextHolder.locale)
	        redirect(action: "edit", id: design.id)
	        return
		}
		
		
		if(design.content && !design.content.contains("{{CONTENT}}")){
			flash.message = messageSource.getMessage("design.content.tag.message", null, LocaleContextHolder.locale)
	        redirect(action: "edit", id: design.id)
	        return
		}

		def existingDefaultDesigns = Design.findAllByDefaultDesign(true)
		if(!design.defaultDesign){
			if(!existingDefaultDesigns)design.defaultDesign = true
		}else{
			existingDefaultDesigns.each{ it ->
				if(design != it){
					it.defaultDesign = false
					it.save(flush:true)
				}
			}
		}
		
		println "here update uno..."
		
		if(!design.save(flush: true)) {
			flash.message = messageSource.getMessage("design.content.tag.message", null, LocaleContextHolder.locale)
	        redirect(action: "edit", id: design.id)
	        return
	    }
		
		println "here update..."
		flash.message = messageSource.getMessage("successfully.updated", null, LocaleContextHolder.locale)
		redirect(action:"edit", id: design.id)
		
	}
	
	
	
 	@Secured(['ROLE_ADMIN'])
	def delete(Long id){
		def design = Design.get(id)
		if(!design){
			flash.message = messageSource.getMessage("design.not.found", null, LocaleContextHolder.locale)
			redirect(action: "index")
			return
		}
		if(design.defaultDesign){
			flash.message = messageSource.getMessage("design.default.message", null, LocaleContextHolder.locale)
			redirect(action: "index")
			return
		}
		
		try{
		
			design.delete(flush:true)
			flash.message = messageSource.getMessage("successfully.deleted", null, LocaleContextHolder.locale)
			redirect(action: "index")
			
		}catch(Exception e){
			e.printStackTrace()
			flash.message = messageSource.getMessage("something.wrong.design.products.catalogs.assigned", request.locale)
			redirect(action: "edit", id: id)
		}
		
	}
	
	
 	@Secured(['ROLE_ADMIN'])	
	def edit_wrapper(){
		File designFile = grailsApplication.mainContext.getResource("templates/web/wrapper.html").file
		def designWrapper = designFile.text
		
		[designWrapper: designWrapper]
	}
	
	
 	@Secured(['ROLE_ADMIN'])	
	def update_wrapper(){
		File designFile = grailsApplication.mainContext.getResource("templates/web/wrapper.html").file
		FileWriter fw = new FileWriter(designFile.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		def html = params.designWrapper
		bw.write(html)
		bw.close()
		flash.message = messageSource.getMessage("successfully.updated", request.locale)
		redirect(action:"edit_wrapper")
	}
	

 	@Secured(['ROLE_ADMIN'])
	def restore_wrapper(){		
		File backupdesignFile = grailsApplication.mainContext.getResource("templates/web/wrapper.backup").file
		
		File designFile = grailsApplication.mainContext.getResource("templates/web/wrapper.html").file
		FileWriter fw = new FileWriter(designFile.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(backupdesignFile.text)
		bw.close()
		flash.message = messageSource.getMessage("successfully.updated", request.locale)
		redirect(action:"edit_wrapper")
	}
	
	
	
 	@Secured(['ROLE_ADMIN'])	
	def apply_pages(){
		def design = Design.get(params.id)
		if(!design){
			flash.message = messageSource.getMessage("something.went.wrong", null, LocaleContextHolder.locale)
			redirect(action:"list")
		}
		def pages = Page.list()
		pages.each(){ page ->
			page.design = design
			page.save(flush:true)
		}

		def existingDefaultDesigns = Design.findAllByDefaultDesign(true)
		if(existingDefaultDesigns){
			existingDefaultDesigns.each{ it ->
				if(design != it){
					it.defaultDesign = false
					it.save(flush:true)
				}
			}
		}

		design.defaultDesign = true
		design.save(flush:true)
		
		flash.message = messageSource.getMessage("successfully.applied.pages", null, LocaleContextHolder.locale)	
		redirect(action:"edit", id: params.id)
	}

	
}
