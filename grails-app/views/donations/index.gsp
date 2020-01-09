<% def applicationService = grailsApplication.classLoader.loadClass('xyz.ioc.ApplicationService').newInstance()%>

<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="administrator"/>

</head>
<body>
	
	<g:if test="${flash.message}">
		<p class="notify">${flash.message}</p>
	</g:if>


	<h1>$${applicationService.formatPrice(amount/100)} individual donations</h1>

	<h1>$${applicationService.formatPrice(monthly/100)} monthly</h1>

</body>
</html>
