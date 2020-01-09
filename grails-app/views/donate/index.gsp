<% def applicationService = grailsApplication.classLoader.loadClass('xyz.ioc.ApplicationService').newInstance()%>
<% def designService = grailsApplication.classLoader.loadClass('xyz.ioc.DesignService').newInstance()%>

${raw(designService.header("Donation Options"))}

	<div id="donation-plan-container" class="">

		<h1 class="black yella">Donation Options</h1>
		
		<g:if test="${flash.message}">
			<p class="alert alert-info" role="status">${raw(flash.message)}</p>
		</g:if>


		<p class="lead">Below are your donation options. <br/>You can either make a single one time donation or a monthly donation.</p>

		<style type="text/css">
			.form-check-inline{
				float:left;
				margin-right:20px;
			}
			#credit-card-information{
				background:#fff;
				padding:23px; 
				border:solid 1px #ccc
			}
			.payment-information{
				margin:17px auto 10px auto;
				display:inline-block
			}

			.btn-light{
				border:solid 1px #ccc !important;
			}

			.clear{
				clear:both;
			}
		</style>

		<div class="inline">
			<div class="form-check form-check-inline">
				<input class="form-check-input" type="radio" name="inlineRadioOptions" id="recurring" data-option="recurring" ${params.recurringChecked}>
				<label class="form-check-label" for="recurring">Monthly Donation</label>
			</div>
			<div class="form-check form-check-inline">
				<input class="form-check-input" type="radio" name="inlineRadioOptions" id="one-time" data-option="one-time" ${params.onetimeChecked}>
				<label class="form-check-label" for="one-time" >One time donation</label>
			</div>
			<br class="clear"/>
			<br class="clear"/>
		</div>


		<div id="recurring-container">
			<h2>Monthly Donation</h2>

			<table class="table">
				<tr>	
					<th>Description</th>
					<th>Amount</th>
					<th></th>
				</tr>
				<g:each in="${plans}" var="plan">
					<tr>
						<td>${plan.nickname}</td>
						<td>$${applicationService.formatPrice(plan.amount/100)}</td>
						<td><g:link uri="/donate/monthly/${plan.id}" class="btn btn-light">Select</g:link></td>
				</g:each>
			</table>
		</div>

		<div id="one-time-container" style="display:none;">
			<h2>One time donation</h2>

			<g:render template="donation_form" model="['monthly': false, 'action': 'pay']"/>
		</div>
	
	</div>


	<div style="margin-top:100px;"/>
	

	<g:if test="${account}">
		<g:link uri="/account/review" style="margin-top:39px">&larr;&nbsp;My Account</g:link>
	</g:if>



${raw(designService.footer())}