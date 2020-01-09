		
<h1>Reset Password</h1>

<g:if test="${flash.message}">
	<div class="notify">${flash.message}</div>		
</g:if>


<p>Reseting password for <strong>${accountInstance.username}</strong></p>
<g:form action="reset_password" method="post" >
	<div class="form-group">
		<label for="password">New Password</label>
		<input type="password" name="password" id="password" value="" class="form-control" style="width:250px;">
	</div>
	<div class="form-group">
		<label for="username">Confirm Password</label>
		<input type="password" name="confirmPassword" id="confirmPassword" value="" class="form-control" style="width:250px;" >
	</div>
	<input type="hidden" value="${accountInstance.username}" name="username"/>
	
	<input type="submit" value="Reset Password" class="button retro"/>
</g:form>
