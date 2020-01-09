<% def designService = grailsApplication.classLoader.loadClass('xyz.ioc.DesignService').newInstance()%>

${raw(designService.header("Thank you for your Donation!"))}
	
	<h1>Thank you!</h1>
	
	<g:if test="${flash.message}">
		<p class="notify" role="status">${flash.message}</p>
	</g:if>


	<p class="lead">Thank you for your donation! If a monthly contributor you can opt out at any time by loging in and accessing your account.</p>


	<g:link uri="/account/review" style="margin-top:39px">&larr;&nbsp;My Account</g:link>


${raw(designService.footer())}
