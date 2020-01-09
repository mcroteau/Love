<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="administrator">
		<title><g:message code="designs"/></title>
		
		<script type="text/javascript" src="${resource(dir:'js/allow-tab.js')}"></script>
		
		<style type="text/css">
			#css-textarea,
			#design-textarea{
				height:350px; 
				width:100%;
				font-size:12px;
				background:#f8f8f8;
				font-family: Monaco,"MonacoRegular",monospace;
			}
		</style>
		
	</head>
	<body>

		<g:if test="${flash.message}">
			<div class="notify" style="margin-top:10px;">${flash.message}</div>
		</g:if>
		
		<g:if test="${flash.error}">
			<div class="alert alert-danger" style="margin-top:10px;">${flash.error}</div>
		</g:if>
		
		
		<div class="form-group" style="margin-top:30px">
			<g:link class="button yella small pull-right" controller="design" action="create"><g:message code="new.design"/></g:link>
			
			<g:link uri="/pages" style="display:inline-block;margin-right:10px" class="button beauty-light small right-float"><g:message code="pages"/></g:link>
		</div>
		
		<h1><g:message code="designs"/></h1>

		<!--
		<p class="instructions"><g:link controller="design" action="edit_wrapper"><g:message code="edit.html.wrapper.note"/></g:link></p>  
		-->	
		
		<p><g:message code="each.page.note"/>.</p>
		
			
		<g:if test="${designs?.size() > 0}">
			<table class="table">
				<thead>
					<tr>
						<g:sortableColumn property="name" title="${message(code:'name')}" />
						<g:sortableColumn property="defaultDesign" title="${message(code:'default')}" />
						<th></th>
					</tr>
				</thead>
				<g:each in="${designs}" var="designInstance">
					<tr>
						<td>${designInstance.name}</td>
						<td>
							<g:if test="${designInstance.defaultDesign}">
								<g:message code="default"/>
							</g:if>
						</td>
						<td>
							<g:link controller="design" action="edit" params="[id: designInstance?.id]" class="${designInstance.name}-edit"><g:message code="edit"/></g:link>
						</td>
					</tr>
				</g:each>
			</table>

		</g:if>	
		<g:else>
			<div class="notify" style="margin-top:10px;"><strong><g:message code="how.did.note"/>?</strong>
			<br/><br/>
			<g:message code="you.must.have.note"/>. <g:link controller="design" action="create" class="button retro"><g:message code="new.design"/></g:link>
			</div>
		</g:else>
	
		
	</body>
</html>
