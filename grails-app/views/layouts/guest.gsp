<%@ page import="grails.util.Environment" %>

<% def commonUtilities = grailsApplication.classLoader.loadClass('xyz.ioc.common.CommonUtilities').newInstance()%>

<% def applicationService = grailsApplication.classLoader.loadClass('xyz.ioc.ApplicationService').newInstance()%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
	<title>${applicationService.getSiteName()}: <g:layoutTitle/></title>

	<link rel="stylesheet" href="${resource(dir:'bootstrap', file:'responsive.css')}" />

	<link rel="stylesheet" href="${resource(dir:'css', file:'admin.css')}" />

	<g:layoutHead/>
	
</head>
<body style="background:#d5e2e8;background:#dee3e5;">
	
	<%
		def account = commonUtilities.getAuthenticatedAccount()
	%>	


	<div id="outer-container" class="container">
		
		<div class="row">
			
			<div class="col-md-3"></div>
			<div class="col-md-6">

				<div style="background:#f5fafd;background:#eff2f4;background:#fff;padding:20px 23px 100px 23px;margin-top:10px;" class="shadow-lg" >
					<div id="header" style="text-align:center">

			    		<g:link uri="/accounts" elementId="logo-logo" style="float:none !important;">
			    			&hearts;
			    			<!--愛--> <br/>
							<span style="font-size:19px">Love<span>

			    		</g:link>
						<span class="tiny-tiny" style="display:block;margin-top:20px;">[open source<br/> charity software]</span>

						
						<br class="clear"/>

					</div>

					<div id="container">
						<g:layoutBody/>
					</div>

				</div>
			</div>
			<div class="col-md-3"></div>
		</div>
	</div>




	<div style="margin-top:221px; text-align:center"></div>


</body>
</html>
