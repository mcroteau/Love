package ioc

import grails.plugin.springsecurity.annotation.Secured

import xyz.ioc.ImportFile
import xyz.ioc.common.ApplicationConstants

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

import grails.io.IOUtils
import java.io.FileOutputStream
import java.io.FileInputStream


class ImportController {
	
	def commonUtilities
    MessageSource messageSource

 	@Secured(['ROLE_ADMIN'])
	def index(Integer max){
		params.max = Math.min(max ?: 10, 100)
		[uploadInstanceList : ImportFile.list(params), uploadInstanceTotal: ImportFile.count()]
	}
	
 	@Secured(['ROLE_ADMIN'])
	def remove(Long id){
		def upload = ImportFile.get(id)
		if(upload){
			
			if(commonUtilities.deleteFile(upload.name)){
				upload.delete(flush:true)
				flash.message = messageSource.getMessage("successfully.deleted", null, LocaleContextHolder.locale)
				redirect(action: "index")
				return
			}
			else{
				flash.message = "Will you contact support? Something went wrong."
				redirect(action: "index")
				return
			}
		}else{
			flash.message = messageSource.getMessage("upload.not.found", null, LocaleContextHolder.locale)
			redirect(action: "index")
			return
		}
	}
	
	
	
 	@Secured(['ROLE_ADMIN'])
	def upload(){
		
		def file = request.getFile('file')
		
		if(file.getOriginalFilename()){
			println "file ${file} ${file.getOriginalFilename()}"
			def originalFileName = file.getOriginalFilename()
			
			//String[] tokens = originalFileName.split("\\.(?=[^\\.]+\\\$)");
			def r = commonUtilities.randomString(7)
			def extension = commonUtilities.getExtension(originalFileName)
		
			def fileName = "${r}.${extension}"
			
			def path = commonUtilities.getApplicationFilesPath()
			def filePath = "${path}${fileName}"
			
			InputStream is = file.getInputStream()
			OutputStream os = new FileOutputStream(filePath)
		
			try {
			    IOUtils.copy(is, os);
			} finally {
			    IOUtils.closeQuietly(os);
			    IOUtils.closeQuietly(is);
			}
			
			def upload = new ImportFile()
			upload.name = fileName
			upload.uri = "files/${fileName}"	
			upload.save(flush:true)
			
			flash.message = messageSource.getMessage("successfully.imported", null, LocaleContextHolder.locale)
			redirect(action:'index')
		}else{
			flash.message = messageSource.getMessage("specify.file.error.message", null, LocaleContextHolder.locale)
			redirect(action:'index')
		}

	}


}