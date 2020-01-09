package xyz.ioc.common

import grails.io.IOUtils

import java.io.FileOutputStream
import org.apache.commons.validator.routines.EmailValidator
import java.io.FileInputStream
import java.util.regex.Matcher
import java.util.regex.Pattern
import org.apache.commons.io.FilenameUtils

import grails.util.Holders

import xyz.ioc.Account


class CommonUtilities {
	
	def grailsApplication
	def springSecurityService
	
	
	CommonUtilities(){
		if(!grailsApplication){
			grailsApplication = Holders.grailsApplication
		}
		if(!springSecurityService){
		    springSecurityService = grailsApplication.classLoader.loadClass("grails.plugin.springsecurity.SpringSecurityService").newInstance()
		}
	}
	

	def percent(total, amount){
		println amount + ": " + total
		if(total != 0 && amount != 0){
			return Math.round((total/amount) * 100)
		}
		return 0
	}

	
	def getAuthenticatedAccount(){
		def username = springSecurityService.principal.username
		def account = Account.findByUsername(username)
		return account
	}
	
	def randomNumber(min, max){
		def random = new Random()
		def n = random.nextInt(max)
		if( n + min > max){
			return max
		}else{
			return n + min
		}
	}

	def generateRandomNumber(min, max){
		def random = new Random()
		def n = random.nextInt(max)
		if( n + min > max){
			return max
		}else{
			return n + min
		}
	}

	public randomString(int n){
		def alphabet = (('a'..'z')+('A'..'Z')+('0'..'9')).join()
		new Random().with {
		  	(1..n).collect { alphabet[ nextInt( alphabet.length() ) ] }.join()
		}
	}

	public generateRandomString(int n){
		def alphabet = (('a'..'z')+('A'..'Z')+('0'..'9')).join()
		new Random().with {
		  	(1..n).collect { alphabet[ nextInt( alphabet.length() ) ] }.join()
		}
	}

	
	def generateFileName(file){
		def fullFileName = file.getOriginalFilename()
		println "fullFileName : ${fullFileName}"
		
		String[] nameSplit = fullFileName.toString().split("\\.")
		def fileName = generateRandomString(9)
			
		println "extension : ${nameSplit[nameSplit.length - 1]}"
		def extension = nameSplit[nameSplit.length - 1]
	
		def newFileName = "${fileName}.${extension}"
		
		println "generateFileName ${newFileName}"
		return newFileName
	}


	def getApplicationFilesPath(){
		try {
			def applicationPath = grailsApplication.mainContext.servletContext.getRealPath('files')
			applicationPath = applicationPath.endsWith("/") ? applicationPath : applicationPath + "/"
			return applicationPath
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}


	def deleteFile(String fileName){
		if(fileName != "") {

			String applicationPath = getApplicationFilesPath();
			String filePath = applicationPath + fileName;
			System.out.println("file path : " + filePath);

			File file = new File(filePath);
			file.delete();

			return true;
		}
		else{
			return false;
		}
	}


	def getExtension(filename) {
    	return FilenameUtils.getExtension(filename);
	}


	def nullToBlankCheck(value){
		if(value == null)return ""
		return value.toString()
	}



	def containsSpecialCharacters(String str) {
		Pattern p = Pattern.compile("[^A-Za-z0-9]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		boolean b = m.find();

		if (b){
			return true
		}
		
		return false
	}


	def validEmail(String str){
		EmailValidator validator = EmailValidator.getInstance();
		return validator.isValid(str);
	}

	def String convertSeconds(int totalSeconds) {

	    final int MINUTES_IN_AN_HOUR = 60;
	    final int SECONDS_IN_A_MINUTE = 60;

	    int seconds = totalSeconds % SECONDS_IN_A_MINUTE;
	    int totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE;
	    int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
	    int hours = totalMinutes / MINUTES_IN_AN_HOUR;

	    return hours + ":" + minutes + ":" + seconds;
	}

}