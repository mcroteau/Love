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

	<style type="text/css">
		html, body, div, span, applet, object, iframe,
		h1, h2, h3, h4, h5, h6, p, blockquote, pre,
		a, abbr, acronym, address, big, cite, code,
		del, dfn, em, img, ins, kbd, q, s, samp,
		small, strike, strong, sub, sup, tt, var,
		b, u, i, center,
		dl, dt, dd, ol, ul, li,
		fieldset, form, label, legend,
		table, caption, tbody, tfoot, thead, tr, th, td,
		article, aside, canvas, details, embed, 
		figure, figcaption, footer, header, hgroup, 
		menu, nav, output, ruby, section, summary,
		time, mark, audio, video {
			margin: 0;
			padding: 0;
			border: 0;
			font-size: 100%;
			font: inherit;
			vertical-align: baseline;
		}
		/* HTML5 display-role reset for older browsers */
		article, aside, details, figcaption, figure, 
		footer, header, hgroup, menu, nav, section {
			display: block;
		}
		body {
			line-height: 1;
		}
		ol, ul {
			list-style: none;
		}
		blockquote, q {
			quotes: none;
		}
		blockquote:before, blockquote:after,
		q:before, q:after {
			content: '';
			content: none;
		}
		table {
			border-collapse: collapse;
			border-spacing: 0;
		}


	</style>

	</style>
	

	<g:layoutHead/>
	
</head>
<body>
	
<div class="container">
	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6"><g:layoutBody/></div>
		<div class="col-md-3"></div>
	</div>
</div>


	<div style="margin-top:221px; text-align:center"></div>


</body>
</html>