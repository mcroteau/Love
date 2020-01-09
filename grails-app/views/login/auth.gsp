<% def applicationService = grailsApplication.classLoader.loadClass('xyz.ioc.ApplicationService').newInstance()%>

<!DOCTYPE html>
<html>
<head>
	<title>Signin</title>
	<meta name="layout" content="guest"/>
</head>
<body>
	
	
	<g:if test="${flash.message}">
		<div class="notify">${flash.message}</div>
	</g:if>
		

	<form action="/${applicationService.getContextName()}/login/authenticate" method="post" autocomplete="off">

		<div class="form-group">
		  	<label for="username">Username</label>
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
		  	<g:link controller="account" action="register" style="font-size:17px">Register</g:link>
		</div>

		<button type="submit" class="button yella pull-right" id="login">Signin</button>
		
		<br class="clear"/>
		
	</form>
		
	<br class="clear"/>

	</body>
</html>

