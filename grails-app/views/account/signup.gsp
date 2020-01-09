<% def applicationService = grailsApplication.classLoader.loadClass('xyz.ioc.ApplicationService').newInstance()%>


<!DOCTYPE html>
<html>
	<head>
		<title>Sign Up</title>
		<meta name="layout" content="guest">
	</head>
	<body>
		
<style type="text/css">

	input:-webkit-autofill {
		background:#f8f8f8;
	}

	label{
		font-size:19px !important;
	}


	#graph:hover{
		opacity:1.0 !important;
	}
</style>
	
	<div id="signin-container" >
		

		<g:if test="${flash.message}">
			<div class="notify">${flash.message}</div>
		</g:if>


		<h1 style="float:left;">Sign Up </h1>


		<br class="clear"/>

		<p style="margin-top:12px;font-size:19px;" class="light">Sign up for a free account to start logging your workouts!</p>

		<div style="margin-bottom:30px;display:block;"></div>


		<div id="signup-form">

			<form action="/pro/account/register" method="POST" id="signupForm">
			
				<div class="form-group">
				  	<label for="name" class="nofloat">Name</label>
				  	<input type="text" name="name" class="form-control nofloat" id="name" placeholder="Ghandi" value="" style="width:83%">
				</div>

				<div class="form-group">
				  	<label for="username" class="nofloat">Email</label>
				  	<input type="text" name="username" class="form-control nofloat" id="username" value="" placeholder="name@email.com">
				</div>
			
				<div class="form-group">
				  	<label for="password" class="nofloat">Password</label>
				  	<input type="password" name="password" class="form-control nofloat" id="password" placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;">
				</div>
			
				<div class="form-group">
				  	<label for="password" class="nofloat">Confirm Password</label>
				  	<input type="password" name="passwordConfirm" class="form-control nofloat" id="password" placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;">
				</div>



				<button type="submit" class="button retro pull-right" id="login" style="margin:23px 0px 92px 0px;">Register!</button>
				
				<br class="clear"/>

				
			</form>

		</div>

	</div>
			
	</body>
</html>

