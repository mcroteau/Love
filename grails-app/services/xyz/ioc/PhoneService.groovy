package xyz.ioc

import com.plivo.api.PlivoClient
import com.plivo.api.Plivo

import xyz.ioc.common.ApplicationConstants


class PhoneService {

	private static final String PLIVO_PHONE = ""
	private static final String NOTIFY_PHONE = ""

	def validate(phone, account){
		try{
			
			Plivo.init("", "");
	    	def message = com.plivo.api.models.message.Message.creator(
	    				PLIVO_PHONE, Collections.singletonList("+1" + phone), "∑ Sigma : Setup complete ${account.name}")
	                    .create();
	        println message
    	
    		message.properties.each { println "$it.key -> $it.value" }

    	}catch(Exception e){
    		e.printStackTrace()
    		return false
    	}

        return true
	}


	def send(phones, notification){
		try{
			
			Plivo.init("", "");
	    	def message = com.plivo.api.models.message.Message.creator(
	    				PLIVO_PHONE, Collections.singletonList(phones), notification)
	                    .create();
    		println "message sent."
    	}catch(Exception e){
    		e.printStackTrace()
    		return false
    	}
	}


	def support(notification){
		try{
			
			Plivo.init("", "");
	    	def message = com.plivo.api.models.message.Message.creator(
	    				PLIVO_PHONE, Collections.singletonList(NOTIFY_PHONE), notification)
	                    .create();
    		println "message sent."
    	}catch(Exception e){
    		e.printStackTrace()
    		return false
    	}
	}

}