<% def applicationService = grailsApplication.classLoader.loadClass('xyz.ioc.ApplicationService').newInstance()%>


<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="administrator">
		<title>Create Plan</title>
	</head>
	<body>


	<div id="Create Plan">
	

		<g:if test="${flash.message}">
			<div class="notify" role="status">${flash.message}</div>
		</g:if>
	
	
		<h1 class="left-float">Create Plan</h1>

		<g:link uri="/plans" class="button yella small right-float">Back to List</g:link>

		<br class="clear"/>

		<p><span class="href-dotted">Plans are charged monthly.</span> <br/>Love utilizes <a href="http://stripe.com" target="_blank">Stripe</a> for payment processing. <g:link uri="/settings/payment_settings">Configure Love</g:link>.</p>
		
		<g:hasErrors bean="${planInstance}">
			<ul class="errors" role="alert" class="alert alert-info">
				<g:eachError bean="${planInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
		</g:hasErrors>
		
		
		
		<div >
			<g:form action="save" method="post" class="form-horizontal" autocomplete="off">
		
				<div class="form-row">
					<span class="form-label nofloat">Description</span>
					<span class="input-container">
						<g:textField class="form-control threehundred"  name="name" value="${planInstance?.name}" placeholder="\$5 month"/>
					</span>
					<br class="clear"/>
				</div>

				<!--
				<div class="form-row">
					<span class="form-label nofloat">Description</span>
					<span class="input-container">
						<g:textField class="form-control threefifty" name="description" value="${planInstance?.description}"/>
					</span>
					<br class="clear"/>
				</div>
				-->

				<div class="form-row">
					<span class="form-label nofloat">Amount</span>
					<span class="tiny">Enter amount with decimal without currency sign.</span><br/><br/>
					<input type="text" class="onefifty form-control" name="amount" value="" autocomplete="false" placeholder="$" /><br/>
				</div>


				
				
				<div class="buttons-container" style="margin-top:42px;">
					<input type="submit" class="button retro" value="Create Plan" />			
				</div>



				<br class="clear"/>
				
			</g:form>
		</div>

	</div>
	
	
	</body>
</html>
