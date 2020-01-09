<%@ page import="xyz.ioc.Page" %>
<%@ page import="xyz.ioc.Design %>
<%@ page import="xyz.ioc.ApplicationService" %>
<% def applicationService = grailsApplication.classLoader.loadClass('xyz.ioc.ApplicationService').newInstance()
%>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="administrator">
	<title><g:message code="edit.page"/></title>

	<script type="text/javascript" src="${resource(dir:'js/allow-tab.js')}"></script>
</head>
<body>
	



	
	<div class="form-outer-container">
	
	
		<div class="form-container">
		
			<h1><g:message code="edit.page"/>
				<g:link controller="page" action="create" class="button yella small pull-right"><g:message code="new.page"/></g:link>

				<g:link controller="page" action="list" class="button beauty-light small pull-right" style="margin-right:10px;"><g:message code="back.to.list"/></g:link>
				<br class="clear"/>
			</h1>
		
			<br class="clear"/>
		
		
			<div class="messages">
		
				<g:if test="${flash.message}">
					<div class="notify" role="status">${flash.message}</div>
				</g:if>
		
				<g:if test="${flash.error}">
					<div class="alert alert-danger" role="status">${flash.error}</div>
				</g:if>
				
				<g:hasErrors bean="${pageInstance}">
					<div class="alert alert-danger">
						<ul>
							<g:eachError bean="${pageInstance}" var="error">
								<li><g:message error="${error}"/></li>
							</g:eachError>
						</ul>
					</div>
				</g:hasErrors>
			
			</div>
		
		
			<g:form action="update" id="${pageInstance.id}">
				
				<g:hiddenField name="id" value="${pageInstance?.id}" />
				<g:hiddenField name="version" value="${pageInstance?.version}" />
				

				<div class="form-row">
					<span class="form-label full secondary"><g:message code="url"/></span>
					<span class="input-container">
						<span class="information">
							/${applicationService.getContextName()}/page/ref/${pageInstance.title} &nbsp;&nbsp;
							<a href="/${applicationService.getContextName()}/page/ref/${URLEncoder.encode("${pageInstance.title}", "UTF-8")}" target="_blank"><g:message code="test"/></a>
						</span>
						<br/>

						<span class="information">
							/${applicationService.getContextName()}/page/view/${pageInstance.id} &nbsp;&nbsp;
							<a href="/${applicationService.getContextName()}/page/view/${pageInstance.id}" target="_blank"><g:message code="test"/></a>
						</span>
					</span>
					<br class="clear"/>
				</div>
				
				
				<div class="form-row">
					<span class="form-label full secondary"><g:message code="title"/> 
						<span class="information secondary block"><g:message code="title.unique.message"/></span>
					</span>
					<span class="input-container">
						<input name="title" type="text" class="form-control threefifty" value="${pageInstance?.title}"/>
					</span>
					<br class="clear"/>
				</div>
				
				
						  
			
				<div class="form-row">
					<span class="form-label full secondary"><g:message code="content"/> 
					</span>
					<span class="input-container">
						<span class="information secondary block"><g:message code="content.editor.message"/></span>
					</span>
					<br class="clear"/>
				</div>
			
			
				<div class="form-row">
					<g:textArea class="form-control ckeditor" name="content" id="content" cols="40" rows="35" maxlength="65535" value="${pageInstance?.content}"/>
					<br class="clear"/>
				</div>
			
			
					  

	 			<div class="form-row">
	 				<span class="form-label full secondary"><g:message code="layout"/></span>
	 				<span class="input-container">
						<g:select name="design.id"
								from="${designs}"
								value="${pageInstance?.design?.id}"
								optionKey="id" 
								optionValue="name" 
								class="form-control"
								style="width:275px;"/>
	 				</span>
	 				<br class="clear"/>
	 			</div> 
			
			
			
				<div class="buttons-container">

					<g:if test="${pageInstance.title != "Home"}">
						<g:actionSubmit class="button" action="delete" value="Delete" formnovalidate="" onclick="return confirm('Are you sure?');" />
					</g:if>
				
					<g:actionSubmit class="button retro" action="update" value="${message(code:'update')}" />

					
				</div>
				
			</g:form>
			
		</div>
	</div>
	

<script type="text/javascript">
$(document).ready(function(){
	$("#content").allowTabChar();
});
</script>	

</body>
</html>
