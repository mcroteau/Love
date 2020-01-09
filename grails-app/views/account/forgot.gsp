<% def designService = grailsApplication.classLoader.loadClass("xyz.ioc.DesignService").newInstance()%>

${raw(designService.header("Reset Password"))}

<h1>Reset Password</h1>

<g:if test="${flash.message}">
	<div class="alert alert-info">${flash.message}</div>		
</g:if>


<g:form action="send_forgot" method="post" >
	
	<div class="form-group">
		<em class="highlight">Username </em><br/>
		<span class="small">An email will be sent to this address to reset password</span>
		<input type="email" value="" id="username" name="username" class="form-control" style="width:250px;"/>
	</div>
	
	<input type="submit" value="Reset Password" class="btn btn-primary"/>
	
</g:form>

<br class="clear"/>

${raw(designService.footer())}

