<% def applicationService = grailsApplication.classLoader.loadClass('xyz.ioc.ApplicationService').newInstance()%>

<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="administrator">
		<title>File Import</title>
	</head>
	<body>
	
		<div id="file-upload" class="content scaffold-create" role="main">
			
			<h1>File Import</h1>
			
			<g:if test="${flash.message}">
				<div class="notify" role="status">${flash.message}</div>
			</g:if>
			

			<g:uploadForm action="upload" method="post" >
			
				<div class="form-group">
					<label>Select file to upload</label>
					<input type="file" name="file" id="file" />	
				</div>
				
				<div class="form-group" style="margin-top:20px;">	

					<g:link uri="/donations" name="cancel">Cancel</g:link>
					<g:submitButton name="add" class="button small" value="Upload File" />
				</div>
				<p class="secondary">File names may change during upload</p>
			</g:uploadForm>
			
			<g:if test="${uploadInstanceList.size() > 0}">
				<table class="table">
					<thead>
						<tr>	
							<th>Id</th>
							<th>File URL</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<g:each in="${uploadInstanceList}" status="i" var="upload">
						<tr>
							<td>${upload?.id}</td>
							<td>
								<a href="/${applicationService.getContextName()}/static/${upload?.uri}" target="_blank">
									/${applicationService.getContextName()}/static/${upload?.uri}
								</a>
							</td>
							<td><g:link action="remove" class="btn btn-default" id="${upload?.id}">Remove</g:link>
							</td>
						</tr>
						</g:each>
					</tbody>
				</table>

				<div class="btn-group">
					<g:paginate total="${uploadInstanceTotal}" />
				</div>
				
			</g:if>
		</div>

	</body>
</html>
