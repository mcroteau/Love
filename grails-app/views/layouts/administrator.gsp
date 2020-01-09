<%@ page import="grails.util.Environment" %>

<% def commonUtilities = grailsApplication.classLoader.loadClass('xyz.ioc.common.CommonUtilities').newInstance()%>

<% def applicationService = grailsApplication.classLoader.loadClass('xyz.ioc.ApplicationService').newInstance()%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<link rel="icon" type="image/png" href="/${applicationService.getContextName()}/static/images/favicon.png" />
	
	<title>♥ ${applicationService.getSiteName()}: <g:layoutTitle default="Open Source Charity Software" /></title>

	<script type="text/javascript" src="${resource(dir:'js/lib/jquery/jquery.min.js')}"></script>

	<link rel="stylesheet" href="${resource(dir:'bootstrap', file:'responsive.css')}" />

	<link rel="stylesheet" href="${resource(dir:'css', file:'admin.css')}" />

	<style type="text/css">

	</style>
	

	<g:layoutHead/>
	
</head>
<body style="background:#d5e2e8;background:#dee3e5;">
	
	<%
		def account = commonUtilities.getAuthenticatedAccount()
	%>	


	<div id="outer-container" class="container">
		
		<div class="row">
			
			<div class="col-md-2"></div>
			<div class="col-md-8">

				<div style="background:#f5fafd;background:#eff2f4;background:#fff;padding:20px 23px 100px 23px;margin-top:10px;" class="shadow-lg" >
					<div id="header">

			    		<g:link uri="/accounts" elementId="logo-logo">
			    			&hearts;
			    			<!--愛--> <br/>
							<span style="font-size:19px">Love<span>

			    		</g:link>
						<span class="tiny-tiny" style="display:inline-block;margin-top:20px;">[open source<br/> charity software]</span>


						<div id="navigation">
							<ul class="light" style="margin-bottom:21px !important">

								<li><g:link uri="/donations">Donations</g:link></li>

								<li><g:link uri="/plans">Donation Plans</g:link></li>

								<li><g:link uri="/pages">Content</g:link></li>

								<li><g:link uri="/accounts">Donors</g:link></li>

							</ul>
						</div>
						
						<br class="clear"/>

					</div>

					<div id="container">
						<g:layoutBody/>
					</div>


					<div class="align-right" style="margin-top:70px;">
						<g:link uri="/settings" class="href-dotted">Settings</g:link>
						&nbsp;&nbsp;
						<g:link uri="/logout">Logout</g:link>
					</div>

				</div>
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>




	<div style="margin-top:221px; text-align:center"></div>



</body>
</html>
