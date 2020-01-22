package ioc

import grails.plugin.springsecurity.annotation.Secured
import xyz.ioc.common.ApplicationConstants


class LoginController {

	@Secured([ApplicationConstants.PERMIT_ALL])
	def signin(){
		if(params.login_error){
			flash.message = "Username or password is incorrect. Please try again."
			println "flash.message ${flash.message}"
		}
	}


	@Secured([ApplicationConstants.PERMIT_ALL])
	def auth(){ 
		if(params.login_error){
			redirect(action:"signin", params : params)
		}
	}
}