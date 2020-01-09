package ioc

import grails.plugin.springsecurity.annotation.Secured
import xyz.ioc.common.ApplicationConstants


class LoginController {

	@Secured([ApplicationConstants.PERMIT_ALL])
	def signin(){ }

}