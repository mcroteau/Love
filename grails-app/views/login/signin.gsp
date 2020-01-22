<% def designService = grailsApplication.classLoader.loadClass('xyz.ioc.DesignService').newInstance()%>
<% def applicationService = grailsApplication.classLoader.loadClass('xyz.ioc.ApplicationService').newInstance()%>

${raw(designService.header("Signin"))}

	<h1>${applicationService.getSiteName()}</h1>

	<g:if test="${flash.message}">
		<div class="alert alert-info">${flash.message}</div>
	</g:if>
		

	<form action="/${applicationService.getContextName()}/login/authenticate" method="post" autocomplete="off">

		<div class="form-group">
		  	<label for="username">Email Address</label>
		  	<input type="text" name="username" class="form-control" id="username" value="${username}">
		</div>

		<div class="form-group">
		  	<label for="password">Password</label>
		  	<input type="password" name="password" class="form-control" id="password" placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;">
		</div>
		
		<div class="form-group">
			<input type="checkbox" class="chk" name="remember-me" id="remember_me" />
			<label for="remember_me">Remember me</label>
		</div>
		
		<div class="form-group">
		  	<g:link controller="account" action="forgot" style="font-size:17px">Forgot password?</g:link>
		</div>
		
		<div class="form-group">
		  	<g:link controller="account" action="register">Register</g:link>
		</div>

		<button type="submit" class="btn btn-primary pull-right">Signin</button>
		
		<br class="clear"/>
		
	</form>
		
	<br class="clear"/>


${raw(designService.footer())}