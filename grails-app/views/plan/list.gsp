<% def applicationService = grailsApplication.classLoader.loadClass('xyz.ioc.ApplicationService').newInstance()%>

<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="administrator">
		<title>Donation Plans</title>
	</head>
	<body>

		<div id="list-account" class="content scaffold-list" role="main">
		
			<h1 class="left-float">Donation Plans</h1>

			<g:link action="create" class="button yella small right-float" style="float:right;">Create Plan</g:link>

			<br class="clear"/>

			<p>Click <g:link uri="/donate/" target="_blank">Here</g:link> to see the donation page as a visitor will see it. The page will use the <g:link uri="/designs" class="href-dotted">Default Design</g:link>. </p>

			<g:if test="${flash.message}">
				<div class="notify" role="status">${flash.message}</div>
			</g:if>
			

			<g:if test="${plans}">
			
				<table class="table">
					<thead>
						<tr>

							<g:sortableColumn property="id" title="Id"/>
							
							<g:sortableColumn property="nickname" title="Nickname"/>

							<g:sortableColumn property="frequency" title="Interval"/>
							
							<g:sortableColumn property="amount" title="Amount"/>

							<th></th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${plans}" status="i" var="plan">

						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
			
							<td>
								<g:link action="pre" id="${plan.id}">${plan.id}</g:link>
							</td>
							
							<td>${plan.nickname}</td>
						
							<td>${plan.frequency}</td>
						
							<td>$${applicationService.formatPrice(plan.amount/100)}</td>
						
							<td>
								<g:form action="remove" id="${plan.id}">
	                            	<g:actionSubmit action="remove" value="Delete" onclick="return confirm('Are you sure you want to delete Plan?');" class="button beauty-light small" />
	                            </g:form>
							</td>

						</tr>
					</g:each>
					</tbody>
				</table>
				
				<div class="btn-group">
					<g:paginate total="${plans}"/>
				</div>
			</g:if>
			<g:else>
				<br class="clear"/>
				<p class="alert alert-info">No plans created yet... <g:link uri="/plan/create">Create Plan</g:link></p>
			</g:else>
		</div>
	</body>
</html>
