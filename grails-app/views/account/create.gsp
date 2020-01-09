<%@ page import="xyz.ioc.Account" %>
<%@ page import="xyz.ioc.State" %>
<% def applicationService = grailsApplication.classLoader.loadClass('xyz.ioc.ApplicationService').newInstance()%>

<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="administrator">
	<title>Create Account</title>	
</head>
	
<body>
<div class="form-outer-container">
	
	<div class="form-container">

		<h1 class="left-float">
			<g:if test="${admin == 'true'}">
				Create Administrator			
			</g:if>
			<g:else>
				Create Account
			</g:else>
		</h1>
			
		<g:link uri="/accounts" params="[admin:false]" class="button yella small right-float">Back to Accounts</g:link>



		<br class="clear"/>
		
		
		<div class="messages">
		
			<g:if test="${flash.message}">
				<div class="notify" role="status">${raw(flash.message)}</div>
			</g:if>
				
			<g:hasErrors bean="${accountInstance}">
				<div class="alert alert-danger">
					<ul>
						<g:eachError bean="${accountInstance}" var="error">
							<li><g:message error="${error}"/></li>
						</g:eachError>
					</ul>
				</div>
			</g:hasErrors>
			
		</div>
		
	
		
		<g:form action="save" class="form-horizontal" role="form" method="post">



			<div class="form-row">
				<span class="form-label full">Username
					<p class="information secondary">Username must be a valid email address</p>
				</span>
				
				<span class="input-container">
					<g:textField type="text" name="username" required="" value="${accountInstance?.username}" class="form-control fourhundred"/>
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
				<span class="form-label full">Phone</span>
				<span class="input-container">
					<g:textField class="threefifty form-control"  name="phone" value="${accountInstance?.phone}"/>
				</span>
				<br class="clear"/>
			</div>


			<div class="form-row">
				<span class="form-label full">Password
					<p class="information secondary">Password must be at least 7 characters long</p>
				</span>
				<span class="input-container">
					<g:textField type="text" name="password" required="" value="${accountInstance?.password}" class="form-control threefifty"/>
				</span>
				<br class="clear"/>
			</div>
			
		

			

			<div class="form-row">
				<span class="form-label full">Is Adminstrator
					<p class="information">All Accounts are Salesman by default</p>
				</span>
				<span class="input-container">
					<g:checkBox name="admin" value="${accountInstance.hasAdminRole}" checked="${admin}"/>
				</span>
				<br class="clear"/>
			</div>	
			
			
			<div class="buttons-container">	
				<g:submitButton name="create" class="button retro" value="Create Account" />		
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
