<% def applicationService = grailsApplication.classLoader.loadClass("xyz.ioc.ApplicationService").newInstance()%>


<h1>Password Reset</h1>

<g:if test="${flash.message}">
	<div class="notify">${flash.message}</div>		
</g:if>


<g:form action="send_forgot" method="post" >
	
	<div class="form-group">
		<em class="highlight">Enter Username </em><br/>
		<span class="small">An email will be sent to this address with instructions on how to continue reset process</span>
		<input type="email" value="" id="username" name="username" class="form-control" style="width:250px;"/>
	</div>
	
	<input type="submit" value="Start Reset Process" class="button retro"/>
	
</g:form>

<br class="clear"/>


