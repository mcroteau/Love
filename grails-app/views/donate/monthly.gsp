<% def applicationService = grailsApplication.classLoader.loadClass('xyz.ioc.ApplicationService').newInstance()%>
<% def designService = grailsApplication.classLoader.loadClass('xyz.ioc.DesignService').newInstance()%>

${raw(designService.header("Monthly Donation"))}

	<div id="donation-plan-container" class="">

		<h1 class="black yella">Monthly</h1>
		
		<g:if test="${flash.message}">
			<p class="notify" role="status">${raw(flash.message)}</p>
		</g:if>

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
			.clear{
				clear:both;
			}
		</style>

		<p class="lead">Donation Amount : <span class="badge badge-secondary" style="font-size:0.9em;">$${applicationService.formatPrice(plan.amount/100)}</span></p>

		<g:render template="donation_form" bean="${account}" model="['monthly': true, 'action': 'pay_monthly']"/>
	
	</div>


	<g:if test="${account}">
		<g:link uri="/account/review" style="margin-top:39px">&larr;&nbsp;My Account</g:link>
	</g:if>


${raw(designService.footer())}