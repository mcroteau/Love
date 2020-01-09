
<%@ page import="xyz.ioc.Page" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="xyz.ioc.ApplicationService" %>
<% def applicationService = grailsApplication.classLoader.loadClass('xyz.ioc.ApplicationService').newInstance()
%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="administrator">
		<title><g:message code="pages"/></title>
	</head>
	<body>
	
		<div id="list-page" class="content scaffold-list" role="main">

			<h1 class="black"><g:message code="pages"/>

			<g:link uri="/designs" class="button beauty-light small pull-right"  style="display:inline-block !important;margin-left:10px;"><g:message code="designs"/></g:link>

			<g:link uri="/page/create" class="button yella small pull-right" style="display:inline-block;margin-left:10px;"><g:message code="new.page"/></g:link>
			

			</h1>
		
			<br class="clear"/>
			
			<g:if test="${flash.message}">
				<div class="notify" role="status">${flash.message}</div>
			</g:if>
			
			
			<br style="clear:both">
			
			<table class="table">
				<thead>
					<tr>
						<g:sortableColumn property="title" title="${message(code: 'title', default: 'Title')}" />
						
						<th><g:message code="url"/></th>
						
						<th><g:message code="design"/></th>
						
						<th></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${pageInstanceList}" status="i" var="pageInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
				
						<td>${pageInstance.title}</td>
					
						<!--
						<td>/${applicationService.getContextName()}/page/ref/${pageInstance.title} &nbsp;&nbsp;
						<a href="/${applicationService.getContextName()}/page/ref/${URLEncoder.encode("${pageInstance.title}", "UTF-8")}" class="information" target="_blank"><g:message code="test"/></a></td>
						-->

						<td><g:link uri="/page/view/${pageInstance.id}" target="_blank">/page/view/${pageInstance.id}</g:link></td>

						<td><g:link controller="design" action="edit" id="${pageInstance.design.id}">${pageInstance.design.name}</g:link></td>
					
						<td><g:link action="edit" id="${pageInstance.id}" class=""><g:message code="edit"/></g:link></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${pageInstanceTotal}" />
			</div>
		</div>



		<g:link uri="/import" class="button small pull-left"><g:message code="import.file"/></g:link>

	</body>
</html>
