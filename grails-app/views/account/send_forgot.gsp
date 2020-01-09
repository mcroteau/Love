<% def designService = grailsApplication.classLoader.loadClass("xyz.ioc.DesignService").newInstance()%>

${raw(designService.header("Reset Password"))}
	
<h1>Email Confirmation Sent</h1>

<g:if test="${flash.message}">
	<div class="notify">${flash.message}</div>		
</g:if>

<p>Successfully sent reset password email confirmation. Please check the email address entered for instructions on how to continue the password reset process.
</p>

${raw(designService.footer())}
	