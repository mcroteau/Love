<% def designService = grailsApplication.classLoader.loadClass('xyz.ioc.DesignService').newInstance()%>

<% def applicationService = grailsApplication.classLoader.loadClass('xyz.ioc.ApplicationService').newInstance()%>

${raw(designService.header("My Account"))}

	<div id="account-review-container">
	
	
		<h1>My Account</h1>


		<g:if test="${flash.message}">
			<div class="alert alert-info" role="status">${flash.message}</div>
		</g:if>
	

		<g:hasErrors bean="${accountInstance}">
			<ul class="errors" role="alert" class="notify">
				<g:eachError bean="${accountInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
		</g:hasErrors>
		

		<g:if test="${accountInstance.donationSubscription}">
			<p class="lead">My Monthly Contribution : <span class="badge badge-secondary">$${applicationService.formatPrice(accountInstance.donationSubscription.donationPlan.amount/100)}</span>&nbsp;&nbsp;<g:link uri="/donate/confirm/${accountInstance.donationSubscription.donationPlan.id}" style="font-size:.9em;">Cancel</g:link></p>

		</g:if>



		<g:if test="${!accountInstance.donationSubscription}">
			<p class="lead"><g:link uri="/donate" style="margin-top:39px">Enroll in Monthly Donation</g:link></p>
		</g:if>



		<g:form action="update_guest" method="post" class="form-horizontal" autocomplete="false">
		
			<g:hiddenField name="id" value="${accountInstance?.id}" />

			<div class="form-group">
				<label for="name">Name</label>
			    <input type="text" class="form-control" name="name" value="${accountInstance.name}">
			</div>

			<div class="form-group">
				<label for="email">Email Address</label>
			    <input type="text" class="form-control" name="email" value="${accountInstance.username}" disabled>
			</div>

			<div class="form-group">
				<label for="email">Phone</label>
			    <input type="text" class="form-control" name="phone" value="${accountInstance.phone}">
			</div>

			<div class="form-group">
				<label for="location">Address</label>
			    <textarea class="form-control" name="location">${accountInstance.location}</textarea>
			</div>

			<div class="form-group">
				<label for="password">Password</label>
			    <input type="password" class="form-control" name="password" value="">
				<span class="information secondary">Leave blank to keep password the same</span>
			</div>


			
			<div class="buttons-container" style="margin-top:49px;text-align:right">
				<input type="submit" class="btn btn-primary" value="Update Account" />
			</div>


			<br class="clear"/>
			
		</g:form>
	
	</div>


${raw(designService.footer())}
