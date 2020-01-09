<%@ page import="xyz.ioc.Page" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="administrator">
		<title><g:message code="create.page"/></title>

		<script type="text/javascript" src="${resource(dir:'js/allow-tab.js')}"></script>
	</head>
	<body>
		
		

		<div class="form-outer-container">
		
		
			<div class="form-container">
			
				<h1 class="bold"><g:message code="create.page"/>
					<g:link controller="page" action="list" class="button yella small pull-right"><g:message code="back.to.list"/></g:link>
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
				
				
				
			
				<g:form action="save" >
					<div class="form-row">
						<label><g:message code="title"/> 
							<span class="tiny block"><g:message code="title.unique.message"/></span>
						</label>
						<br/>
						<span class="input-container">
							<input name="title" type="text" class="form-control threefifty" value="${pageInstance?.title}"/>
						</span>
						<br class="clear"/>
					</div>
					
					<br/>
				
					<div class="form-row">
						<label><g:message code="content"/>
							<span class="tiny block"><g:message code="content.editor.message"/></span>
						</label>
						<br/>
						<g:textArea class="form-control ckeditor" name="content" id="content" rows="35" maxlength="65535" value="${pageInstance?.content}"/>
						<br class="clear"/>
					</div>


		 			<div class="form-row">
		 				
		 				<span class="form-label full secondary"><g:message code="design"/></span>

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
						<g:submitButton name="create" class="button retro" value="${message(code:'save.page')}" />
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
