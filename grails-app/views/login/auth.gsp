<% def applicationService = grailsApplication.classLoader.loadClass('xyz.ioc.ApplicationService').newInstance()%>

<!DOCTYPE html>
<html>
<head>
	<title>Signin</title>
	<meta name="layout" content="guest"/>

	<style type="text/css">
		#login-container{
			margin-left:20px;
		}
		.form-group{
			margin:30px auto;
		}
	</style>
</head>
<body>
	
	
	<div id="login-container">

		<g:if test="${flash.message}">
			<div class="notify">${flash.message}</div>
		</g:if>
			
		<g:if test="${applicationService.getSiteName()}">	
			<h1>${applicationService.getSiteName()}</h1>	
		</g:if>


		<form action="/${applicationService.getContextName()}/login/authenticate" method="post" autocomplete="off">

			<div class="form-group">
			  	<label for="username">Username</label>
			  	<input type="text" name="username" class="form-control fourhundred" id="username" value="${username}">
			</div>

			<div class="form-group">
			  	<label for="password">Password</label>
			  	<input type="password" name="password" class="form-control fourhundred" id="password" placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;">
			</div>
			
			<div class="form-group">
				<input type="checkbox" class="chk" name="remember-me" id="remember_me" />
				<span for="remember_me">Remember me</span>
			</div>
			
			<div class="form-group">
			  	<g:link controller="account" action="forgot" style="font-size:17px">Forgot password?</g:link>
			</div>
			
			<div class="form-group">
			  	<g:link controller="account" action="register" style="font-size:17px">Register</g:link>
			</div>

			<button type="submit" class="button retro pull-right" id="login">Admin Signin</button>
			
			<br class="clear"/>
			
		</form>
			
		<br class="clear"/>
	
	</div>

</body>
</html>

