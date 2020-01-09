package ioc

class BaseController {

	def hasPermission(p, account){

		def permission = account.permissions.find {
			it.permission == p
		}

		println ">> permission ${permission}"

		if(permission) {
			return true 
		}
		else{
			return false
		}
	}

}