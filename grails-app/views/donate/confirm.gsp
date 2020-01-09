<% def designService = grailsApplication.classLoader.loadClass('xyz.ioc.DesignService').newInstance()%>

<% def applicationService = grailsApplication.classLoader.loadClass('xyz.ioc.ApplicationService').newInstance()%>

${raw(designService.header("Confirm Cancel"))}

	<div id="confirm-cancellation-container">
	

		<g:if test="${flash.message}">
			<div class="alert alert-info" role="status">${flash.message}</div>
		</g:if>
	
	
		<h1>Confirm Cancellation</h1>

		<div id="confirm-form-container">

			<g:form action="cancel" method="post" class="form-horizontal" autocomplete="off">
						
				<input type="hidden" name="id" value="${account.id}"/>

				<p class="lead">Confirm cancellation of $${new BigDecimal(plan.amount)/100} a month contribution </p>
				
				<div class="buttons-container" style="margin-top:42px;text-align:right">
					<input type="submit" class="btn btn-danger" value="Cancel Donation" />
				</div>

				<br class="clear"/>
				
			</g:form>

		</div>

	</div>
	
	
	<g:link uri="/account/review" style="margin-top:39px">&larr;&nbsp;My Account</g:link>

	</body>
</html>
