<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="administrator">
	<title>Update Password</title>
</head>
<body>

	
	<div class="form-outer-container">
	
	
		<div class="form-container">
			
			
			<g:if test="${flash.message}">
				<div class="notify" role="status">${flash.message}</div>
			</g:if>

			
			<g:form method="post">
			
				<h2 class="left-float">Password</h2>
					<g:link controller="account" action="edit" id="${accountInstance?.id}" class="button yella small pull-right">Back to Account</g:link>

				
				<br class="clear"/>
				
				
				<div class="form-row">
					<span class="form-label full">Username</span>
					<span class="input-container">${accountInstance.username}
					</span>
					<br class="clear"/>
				</div>
				

				
				<div class="form-row">
					<span class="form-label full">New Password</span>		
					<span class="input-container">
						<input type="password" class="form-control fourhundred"  name="password"value=""/>	
					</span>
					<br class="clear"/>
				</div>


				<div class="buttons-container">
					<g:hiddenField name="id" value="${accountInstance?.id}" />
					<g:actionSubmit class="button retro" action="update_password" value="Update Password" />		
				</div>
				
			</g:form>
			
		</div>
		
	</div>
	
</body>
</html>
