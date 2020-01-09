<% def applicationService = grailsApplication.classLoader.loadClass('xyz.ioc.ApplicationService').newInstance()%>




<form action="/${applicationService.getContextName()}/donate/${action}" method="post" id="payment-form" name="payment-form">

	<g:if test="${!monthly}">
		<input type="hidden" name="onetimeChecked" value="checked"/>
		<input type="hidden" name="recurringChecked" value=""/>
		<div class="form-group">
			<label for="name">Amount</label>
		    <input type="text" class="form-control" placeholder="5.00" name="amount" value="${params.amount}">
		</div>
	</g:if>


	<g:if test="${monthly}">
		<input type="hidden" name="id" value="${plan.id}" />
	</g:if>

	<input type="hidden" name="token" id="token" value="" />


	<g:if test="${account}">

		<input type="hidden" name="email" value="${account.username}" />

		<div class="form-group">
			<label for="name">Name</label>
		    <input type="text" class="form-control" placeholder="Pat Sommers" name="name" value="${account.name}" disabled>
		</div>

		<div class="form-group">
			<label for="email">Email Address</label>
		    <input type="text" class="form-control" placeholder="support@goioc.xyz" name="email" value="${account.username}" disabled>
		</div>

		<div class="form-group">
			<label for="location">Address</label>
		    <textarea class="form-control" placeholder="" name="location" disabled>${account.location}</textarea>
		</div>

	</g:if>
	<g:else>
		<div class="form-group">
			<label for="name">Name</label>
		    <input type="text" class="form-control" placeholder="Pat Sommers" name="name" value="${params.name}">
		</div>

		<div class="form-group">
			<label for="email">Email Address</label>
		    <input type="text" class="form-control" placeholder="support@goioc.xyz" name="email" value="${params.email}">
		</div>

		<div class="form-group">
			<label for="location">Address</label>
		    <textarea class="form-control" placeholder="" name="location">${params.location}</textarea>
		</div>


		<div class="form-group">
			<label for="password">Password</label>
		    <input type="password" class="form-control" placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;" name="password" value="">
		</div>

	</g:else>

</form>

<span class="payment-information">Secure Payment Information</span>
<div id="credit-card-information"></div>

<p id="processing" class="notify" style="display:none"></p>

<br class="clear"/>

<a href="javascript:" class="btn btn-primary btn-lg" id="submit-button" style="float:right; margin-top:3px;">Make Donation</a>

<br class="clear"/>




	
<g:if env="development">
	<g:set var="publishableKey" value="${applicationService.getStripeDevPublishable()}"/>
</g:if>

<g:if env="production">
	<g:set var="publishableKey" value="${applicationService.getStripePublishable()}"/>
</g:if>

<script src="https://js.stripe.com/v3/"></script>
<script type="text/javascript">
	$(document).ready(function(){

		<g:if test="${!monthly}">
			var $onetime = $("#one-time"),
				$recurring = $("#recurring")

			var $checkboxes = $(".form-check-input"),
				$oneTimeContainer = $("#one-time-container"),
				$recurringContainer = $("#recurring-container")

			$checkboxes.click(function(event){
				var option = $(event.target).attr("data-option")
				console.log(option)
				if(option == "one-time"){
					hideShowContainer($recurringContainer, $oneTimeContainer)
				}else{
					hideShowContainer($oneTimeContainer, $recurringContainer)
				}
			})

			function hideShowContainer($hide, $show){
				$hide.hide()
				$show.show()
			}

			<g:if test="${params.onetimeChecked}">
				$onetime.click()
			</g:if>
			<g:else>
				$recurring.click()
			</g:else>
		</g:if>

		var stripe = {},
    		elements = {},
    		card = {};

		var $form = $("#payment-form")
			$creditCardInfo = $("#credit-card-information")
			$processing = $("#processing"),
			$submitBtn = $("#submit-button"),
			$tokenInput = $("#token")

		var processingHtml = "Processing, please wait..."
		stripe = Stripe("${publishableKey}");
		elements = stripe.elements()
		card = elements.create('card', {})
		card.mount('#credit-card-information')
		card.addEventListener('change', function(event) {
	  		var displayError = document.getElementById('card-errors');
	  		if (event.error) {
	    		$processing.html(event.error.message)
				$processing.show()
	  		} else {
	  			$processing.hide()
				$processing.html(processingHtml)
	  		}
		});

		$submitBtn.click(function(event){
			event.preventDefault()
			$processing.show()
			stripe.createToken(card).then(function(result) {
				$tokenInput.val(result.token.id)
				$form.submit();
			});
		})

	})
</script>
