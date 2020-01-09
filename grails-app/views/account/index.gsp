<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="administrator"/>
		<title><g:message code="accounts"/></title>
	</head>
	<body>

		<div id="list-account" class="content scaffold-list" role="main">
		

			<br class="clear"/>
			
			<g:if test="${!admin}">
				<h1 class="left-float">${accountInstanceTotal} Donor Accounts</h1>
			</g:if>
			<g:else>
				<h1 class="left-float">${accountInstanceTotal} Admin(s)</h1>
			</g:else>

			<g:if test="${admin}">
				<g:form action="create">
					<input type="hidden" name="admin" value="true"/>
					<g:submitButton class="button yella pull-right small" name="create" value="Create New Admin User"/>
				</g:form>
			</g:if>
			<g:else>
				<g:form action="create">
					<input type="hidden" name="admin" value="false"/>
					<g:submitButton class="button yella pull-right small" name="create" value="New Account"/>
				</g:form>
			</g:else>

			<br class="clear"/>


			<g:if test="${flash.message}">
				<div class="notify" role="status">${flash.message}</div>
			</g:if>
			
						
			<ul class="navigation">

				<g:if test="${admin}">
			  	  	<li class="active"><g:link uri="/account?admin=true" class="">Admin Accounts</g:link></li>
			  		<li class="inactive"><g:link uri="/account?admin=false" class="">Donor Accounts</g:link></li>
				</g:if>
				<g:else>
			  	  	<li class="inactive"><g:link uri="/account?admin=true" class="">Admin Accounts</g:link></li>
			  		<li class="active"><g:link uri="/account?admin=false" class="">Donor Accounts</g:link></li>
				</g:else>
			</ul>
			
			<br class="clear"/>
			<br class="clear"/>

			<g:if test="${accountInstanceList}">
			
			
				<table class="table">
					<thead>
						<tr>
							<!-- TODO: make sortable, may require refactoring Account hasMany to include hasMany roles/authorities -->
							<g:sortableColumn property="id" title="Id" params="${[admin:admin]}"/>
							
							<g:sortableColumn property="name" title="Name" params="${[admin:admin]}"/>

							<g:sortableColumn property="location" title="Location" params="${[admin:admin]}"/>
							
							<th></th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${accountInstanceList}" status="i" var="accountInstance">
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
			
							<td>
								<g:link action="edit" id="${accountInstance.id}">${accountInstance.id}</g:link>
							</td>
							
							<td>${accountInstance.name}</td>
						
							<td>${accountInstance.location}</td>

							<td>
								<g:link action="edit" params="[id: accountInstance.id]" class="edit-${accountInstance.id}" elementId="edit-${accountInstance.id}">Edit</g:link>
							</td>
						
						</tr>
					</g:each>
					</tbody>
				</table>
				
				<div class="button-group">
					<g:paginate total="${accountInstanceTotal}" 
						 	params="${[query : params.query ]}"/>
				</div>

				<g:link uri="/account/export" class="href-dotted right-float">Export</g:link>

				<br class="clear"/>
				
			</g:if>
			<g:else>
				<br/>
				<p style="color:#333;padding:0px 40px;">No Accounts found...</p>
			</g:else>
		</div>
		
	</body>
</html>
