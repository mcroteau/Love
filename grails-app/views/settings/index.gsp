<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="administrator">
	<title>Settings</title>

	<style type="text/css">

	</style>
	
	<link rel="stylesheet" href="${resource(dir:'css', file:'admin.css')}" />
	
</head>
<body>

	<h2>Site Settings</h2>
	
	<g:if test="${flash.message}">
		<div class="notify" role="status">${flash.message}</div>
	</g:if>
	
	
	<ul class="nav nav-tabs left" style="margin-bottom:30px !important;">
		<li class="active"><g:link uri="/settings/index" class="btn btn-default">General Settings</g:link></li>

		<li class="inactive"><g:link uri="/settings/email_settings" class="btn btn-default">Email Settings</g:link></li>
		
		<li class="inactive"><g:link uri="/settings/payment_settings" class="btn btn-default">Payment Settings</g:link></li>

	</ul>
	
	<br class="clear"/>
	<br class="clear"/>
	
	
	
	<g:form action="save" class="form-horizontal" method="post">
		
			
		<div class="form-row">
			<span class="form-label twohundred">Site Name
				<br/>
				<span class="information secondary">Important, required for page titles etc.</span>
			</span>
			<span class="input-container">
				<input type="text" class="form-control threehundred" name="siteName" value="${settings?.siteName}" style="width:370px;"/>
			</span>
			<br class="clear"/>
		</div>
		
			
		<div class="form-row">
			<span class="form-label twohundred">Site Phone
				<br/>
				<span class="information secondary">Used when creating shipping labels</span>
			</span>
			<span class="input-container">
				<input type="text" class="form-control" name="sitePhone" value="${settings?.sitePhone}" style="width:273px;"/>
			</span>
			<br class="clear"/>
		</div>
		
			
		<div class="form-row">
			<span class="form-label twohundred">Site Email
				<br/>
				<span class="information secondary"></span>
			</span>
			<span class="input-container">
				<input type="text" class="form-control" name="siteEmail" value="${settings?.siteEmail}" style="width:273px;"/>
			</span>
			<br class="clear"/>
		</div>

		
		
		<div class="buttons-container">
			<g:link uri="/accounts" class="button white">Cancel</g:link>
			<g:submitButton value="Save Settings" name="submit" class="button retro"/>
		</div>
		
		
	</g:form>
	
	
	<br class="clear"/>
</body>
</html>