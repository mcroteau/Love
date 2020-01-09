<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="administrator">
		<title><g:message code="edit.design"/></title>
		
		<script type="text/javascript" src="${resource(dir:'js/allow-tab.js')}"></script>
		
	</head>
	<body>

		<g:if test="${flash.message}">
			<div class="notify" style="margin-top:10px;">${flash.message}</div>
		</g:if>
		
		<g:if test="${flash.error}">
			<div class="notify" style="margin-top:10px;">${flash.error}</div>
		</g:if>

		
		<g:if test="${pages}">
			<div id="pages-list-container" class="shadow-lg">
				<p class="black" style="font-size:32px;">Pages</p>
				<div id="pages-list-inner-container">
					<table class="table" style="border:none">
						<g:each in="${pages}" var="page">
							<tr class="page-container">
								<td>${page.title}</td>
								<td><g:link uri="/page/view/${page.id}">/page/view/${page.id}</g:link></td>
							</tr>
						</g:each>
					</table>
				</div>
			</div>
		</g:if>


		
		<g:form controller="design" action="update" id="${designInstance?.id}">
			
			<div class="form-group" style="margin-top:30px">
				
				<g:submitButton class="button retro small pull-right" name="save" value="${message(code:'update.design')}" />

				<g:link class="button beauty-light small pull-right" controller="design" action="index" style="display:inline-block;margin-right:10px;"><g:message code="designs"/></g:link>
				
				<g:link class="button yella small pull-right" controller="design" action="create" style="display:inline-block;margin-right:10px;"><g:message code="new.design"/></g:link>

			</div>
			
			<h2 class="left-float"><g:message code="edit.design"/></h2>
			
			<br class="clear"/>

			<p>Each page can have its own distinct design, js and css</p>


			<div class="form-row">
				<span class="form-label twohundred secondary"><g:message code="name"/> 
					<span class="information secondary block"><g:message code="name.unique"/></span>
				</span>
				<span class="input-container">
					<input name="name" type="text" class="form-control threefifty" value="${designInstance?.name}" id="name"/>
				</span>
				<br class="clear"/>
			</div>

			
			<h3><g:message code="design"/></h3>
			
			<p class="instructions">Design is wrapped by a wrapper html file that contains the latest bootstrap & jquery.</p>
			
			<p class="instructions">Design must contain <span class="black">{{CONTENT}}</span> tag. This is where page content will be rendered</p>
			
			<%
			def designContent = designInstance?.content
			if(!designContent)designContent = "{{CONTENT}}"
			%>

			<div style="color:#fff;border:solid 1px #ddd;background:#384248">
				<span class="design-code">&lt;html&gt;</span>
				<span class="design-code">&nbsp;&nbsp;&nbsp;&lt;body&gt;</span>
				<textarea id="design-textarea"
						name="content" 
						class="form-control">${designContent}</textarea>
				<span class="design-code">&nbsp;&nbsp;&nbsp;&lt;/body&gt;</span>
				<span class="design-code">&lt;/html&gt;</span>
			</div>
			

			<h3><g:message code="design.css"/></h3>
			<p class="instructions"></p>
			
			<div id="design-code-wrapper" class="css">
				<span class="design-code">&lt;style&gt;</span>
				<textarea id="css-textarea" 
						name="css" 
						class="form-control">${designInstance?.css}</textarea>
				<span class="design-code">&lt;/style&gt;</span>
			</div>
			
			
			<br class="clear"/>
			
		
			<h3><g:message code="design.javascript"/></h3>
			
			<p class="instructions">Please use single quotes ' for all js variables</p>
			

			<div id="design-code-wrapper" class="javascript">
				<span class="design-code">&lt;script&gt;</span>
				<textarea id="javascript-textarea" 
						name="javascript" 
						class="form-control">${designInstance?.javascript}</textarea>
				<span class="design-code">&lt;/script&gt;</span>
			</div>
					
			<br class="clear"/>
			
			<div class="form-row" style="margin-top:40px !important">
				<span class="form-label twohundred secondary"><g:message code="default"/> 
					<span class="information secondary block">Make this design the default for all pages.</span>
				</span>
				<span class="input-container">
					<g:checkBox name="defaultDesign" value="${designInstance?.defaultDesign}" checked="${designInstance?.defaultDesign}"/>
				</span>
				<br class="clear"/>
			</div>
			
			<div class="form-group" style="margin-top:30px">
				<g:submitButton class="button retro pull-right" name="save" value="${message(code:'update.design')}" />


				<g:actionSubmit class="button right-float" action="delete" value="Delete" formnovalidate="" onclick="return confirm('Are you sure you want to delete this Design?');" style="display:inline-block; margin-right:10px;"/>

				<br class="clear"/>
			</div>
			
		</g:form>


		<div style="margin-top:20px">
			
			<g:form controller="design" action="apply_pages" method="post" id="${designInstance?.id}">
				<input type="submit" value="Apply to all Pages" class="button yella" style="display:inline-block; margin-right:10px"/>
			</g:form>
			
		</div>
		
		
		
<script type="text/javascript">
$(document).ready(function(){
	var $designForm = $("#designForm");
	
	$("#design-textarea").allowTabChar();
	$("#css-textarea").allowTabChar();
	$("#javascript-textarea").allowTabChar();
	
});
</script>		
		
	</body>
</html>
