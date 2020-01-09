<%@ page import="xyz.ioc.Account" %>
<%@ page import="xyz.ioc.State" %>
<% def applicationService = grailsApplication.classLoader.loadClass('xyz.ioc.ApplicationService').newInstance()%>


<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="administrator">
		<title>Edit Donor</title>
	</head>
	<body>


	<div class="form-outer-container">
	
	
		<div class="form-container">
		

			<g:if test="${flash.message}">
				<div class="notify" role="status">${flash.message}</div>
			</g:if>
		
		
			<h2 class="left-float">Edit Account</h2>
			<g:link uri="/accounts" params="[admin:false]" class="button yella small right-float">Back to Accounts</g:link>
				
			<br class="clear"/>
			
			
			
			<g:hasErrors bean="${accountInstance}">
				<ul class="errors" role="alert">
					<g:eachError bean="${accountInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
			</g:hasErrors>
			
			
			
			
			<g:form method="post" class="form-horizontal" >
			
				<g:hiddenField name="id" value="${accountInstance?.id}" />
				

				<div class="form-row">
					<span class="form-label full">Username</span>
					<span class="input-container">
						${accountInstance.username}
					</span>
					<br class="clear"/>
				</div>


				<div class="form-row">
					<span class="form-label full">Name</span>
					<span class="input-container">
						<g:textField class="form-control fourhundred"  name="name" value="${accountInstance?.name}"/>
					</span>
					<br class="clear"/>
				</div>


				<div class="form-row">
					<span class="form-label full">Location</span>
					<span class="input-container">
						<textarea class="form-control threefifty" style="height:150px; font-family:Roboto-Regular; font-size:19px;" name="location">${accountInstance?.location}</textarea>
					</span>
					<br class="clear"/>
				</div>


				<div class="form-row">
					<span class="form-label full">Phone</span>
					<span class="input-container">
						<g:textField class="fourhundred form-control"  name="phone" value="${accountInstance?.phone}"/>
					</span>
					<br class="clear"/>
				</div>

				

				<div class="form-row">
					<span class="form-label full">Is Adminstrator</span>
					<span class="input-container">
						<g:checkBox name="admin" value="${accountInstance.hasAdminRole}" checked="${accountInstance.hasAdminRole}"/>
						<span class="information">All Accounts are customers by default.</span>		</span>
					<br class="clear"/>
				</div>	
				
				

				<div class="buttons-container">
				
					<g:link action="change_password" id="${accountInstance.id}" style="margin-right:5px;">Change Password</g:link>
					
					<g:if test="${accountInstance.username != 'admin'}">
						<g:actionSubmit class="button" action="delete" value="Delete" formnovalidate="" onclick="return confirm('Are you sure?');" />
					</g:if>
					
					<g:actionSubmit class="button retro" action="update" value="Update" />
					
		
				</div>
				
			</g:form>
	
		</div>
	
	</div>	
	
	
	<script type="text/javascript" src="${resource(dir:'js/country_states.js')}"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
		})
	</script>
	
	</body>
</html>
