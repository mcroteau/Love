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

	<style type="text/css">
		@font-face { 
			font-family: Roboto-Regular; 
			src: url("${resource(dir:'fonts/Roboto-Regular.ttf')}"); 
		} 
		@font-face { 
			font-family: Roboto-Bold;
			src: url("${resource(dir:'fonts/Roboto-Bold.ttf')}"); 
		}
		@font-face { 
			font-family: Roboto-Thin; 
			src: url("${resource(dir:'fonts/Roboto-Thin.ttf')}"); 
		}
		@font-face { 
			font-family: Roboto-Light; 
			src: url("${resource(dir:'fonts/Roboto-Light.ttf')}"); 
		}
		@font-face { 
			font-family: Roboto-Black; 
			src: url("${resource(dir:'fonts/Roboto-Black.ttf')}"); 
		}
		@font-face { 
			font-family: Roboto-Medium; 
			src: url("${resource(dir:'fonts/Roboto-Medium.ttf')}"); 
		}

		/* http://meyerweb.com/eric/tools/css/reset/ 
		   v2.0 | 20110126
		   License: none (public domain)
		*/

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
			background:#fff;
		}

		th{
			background:#f8f8f8;
		}




		html,body,div,span,
h1,h2,h3,h4,p,
input,select,
table,th,td{
	color:#000;
	font-family: Roboto-Regular !important;
}

h1, h2{
	font-size:43px;
	font-family:Roboto-Black !important;
	margin:20px 0px 10px 0px;
}

h1 a{
	font-size:19px;
	font-family:Roboto-Regular;
}

#navigation{
	float:right;
	display:inline-block;
}
#navigation ul{
	margin:20px 0px 51px 0px;
	padding:0px;
	list-style:none;
	text-align:right;
}

#navigation ul li{
	margin-left:12px;
	display:inline-block;
}

#navigation ul li a{
	font-size:14px;
	color:#000;
	text-transform:uppercase;
}
.button,
input[type="button"], 
input[type="reset"], 
input[type="submit"] {
	cursor:pointer;
	opacity:0.97 !important;
	text-transform:uppercase;
	color:#fff;
	font-size:15px;
	font-size:13px;
	padding:15px 23px;
	padding:16px 21px;
	border:solid 1px #418dc5;
	display:inline-block;
	background:#0481bc;
	text-decoration:none !important;
	border-radius: 3px;
	-moz-border-radius: 3px;
	-webkit-border-radius: 3px;
	color:#2b2b34;
	background:#fff !important;
	border:solid 1px #c7ecfd !important;			
	-webkit-box-shadow: 0px 1px 3px 0px rgba(179,179,179,0.43) !important;
    -moz-box-shadow: 0px 1px 3px 0px rgba(179,179,179,0.43) !important;
    box-shadow: 0px 1px 3px 0px rgba(179,179,179,0.43) !important;
	-webkit-box-shadow: 0px 1px 3px 0px rgba(179,179,179,0.93) !important;
    -moz-box-shadow: 0px 1px 3px 0px rgba(179,179,179,0.93) !important;
    box-shadow: 0px 1px 3px 0px rgba(179,179,179,0.93) !important;
	outline:none !important;
}


.button{
	color:#2b2b34;
	background:#fff553;
	border:solid 1px #fff553;
}

.button-small{
	font-size:13px;
	padding:13px 21px;
}

.button:hover{
	opacity: 1;
	/*padding:14px 21px;*/
}

.button:active{
	/*margin-top:1px;*/
	-webkit-box-shadow: 0px 1px 4px 0px rgba(179,179,179,0.73);
    -moz-box-shadow: 0px 1px 4px 0px rgba(179,179,179,0.73);
    box-shadow: 0px 1px 4px 0px rgba(179,179,179,0.73);	
}


.button.small{
	font-size:11px;
	padding:15px 23px;
	padding:12px 15px;
}


.beauty{
	border:solid 1px #af134f;
	background:#c2175a;
	background:#ca2062;
	background:#cf2668 !important;
}

.beauty-light{
	color:#fff !important;
	background:#e24682 !important;
	border:solid 1px #e24682 !important;
}

input[type="button"].beauty-light,
input[type="reset"].beauty-light, 
input[type="submit"].beauty-light{
	color:#fff !important;
	background:#e24682 !important;
	border:solid 1px #e24682 !important;
}

.white-font{
	color:#fff !important;
}

.yella{
	color:#2b2b34 !important;
	background:#fff553 !important;
	border:solid 1px #fff553 !important;
	-webkit-box-shadow: 0px 1px 3px 0px rgba(179,179,179,0.43);
    -moz-box-shadow: 0px 1px 3px 0px rgba(179,179,179,0.43);
    box-shadow: 0px 1px 3px 0px rgba(179,179,179,0.43);
}
input[type="button"].yella, 
input[type="reset"].yella, 
input[type="submit"].yella{
	color:#2b2b34 !important;
	background:#fff553 !important;
	border:solid 1px #fff553 !important;
	-webkit-box-shadow: 0px 1px 3px 0px rgba(179,179,179,0.43);
    -moz-box-shadow: 0px 1px 3px 0px rgba(179,179,179,0.43);
    box-shadow: 0px 1px 3px 0px rgba(179,179,179,0.43);
}

.sky{
	color:#2b2b34 !important;
	background:#F3F3F7 !important;
	border:solid 1px #F3F3F7 !important;
	-webkit-box-shadow: 0px 1px 3px 0px rgba(179,179,179,0.43);
    -moz-box-shadow: 0px 1px 3px 0px rgba(179,179,179,0.43);
    box-shadow: 0px 1px 3px 0px rgba(179,179,179,0.43);
}

.sky:hover{
	background:#fff;
	border:solid 1px #2b2b34;	
}

.button.sky:active{
	background:#fff;
}

.button.yella:active, 
.button.light:active{
	-webkit-box-shadow: none;
    -moz-box-shadow: none;
    box-shadow: none;
}


.retro{
	color:#fff !important;
	background:#2cafed !important;	
	background:#12ADFD !important;
	background:#00a8ff !important;
	border:solid 1px #1f9bc4 !important;
	border:solid 1px #1792b7 !important;
	border:solid 1px #1f9bc4 !important;	
	-webkit-box-shadow: 0px 1px 3px 0px rgba(179,179,179,0.43);
    -moz-box-shadow: 0px 1px 3px 0px rgba(179,179,179,0.43);
    box-shadow: 0px 1px 3px 0px rgba(179,179,179,0.43);
}

input[type="button"].retro, 
input[type="reset"].retro, 
input[type="submit"].retro{
	color:#fff !important;
	background:#00a8ff !important;
	border:solid 1px #1f9bc4 !important;		
}

.button.retro:hover,
.button.retro:active{
	color:#fff;
}

.shadow{
	webkit-box-shadow: 1px 2px 3px 0px rgba(209,209,209,0.52);
	-moz-box-shadow: 1px 2px 3px 0px rgba(209,209,209,0.52);
	box-shadow: 1px 2px 3px 0px rgba(209,209,209,0.52);
}

.shadow-lg{
	-webkit-box-shadow: 0px 0px 9px 0px rgba(191,191,191,1);
	-moz-box-shadow: 0px 0px 9px 0px rgba(191,191,191,1);
	box-shadow: 0px 0px 9px 0px rgba(191,191,191,1);
}

a{
	color:#03afff;
}
				
a:hover{
	cursor:hand;
	color:#03afff
}


input[type="text"],
input[type="password"],
input[type="text"]:hover,
input[type="password"]:hover,
input[type="text"]:active,
input[type="password"]:active{
	color:#221;
	font-family:Roboto-Regular !important;
	font-size:19px !important;
	background:#edf7ff !important;
	background:#ffffea !important;
	background:#fdff82 !important;
	background:#f8f8f8 !important;
	line-height:1.0em !important;
	padding:10px 12px !important;
	border:solid 1px #ccc;
	-webkit-border-radius: 3px !important;
	-moz-border-radius: 3px !important;
	border-radius: 3px !important;
}

input::placeholder{
	font-size:20px;
}

textarea{
	width:100%;
	font-size:12px;
	padding:20px;
	color:#000 !important;
	background:#f8f8f8;
	background:#fdff82 !important;
	background:#f8f8f8 !important;
	border:solid 1px #ccc;
	font-family: Monaco,"MonacoRegular",monospace;
}

h3{
	margin-top:50px;
	font-family:Roboto-Bold !important;
}
.design-code{
	color:#efefef;
	display:block;
	font-size:12px;
	background:#384248 !important;
	font-family: Monaco,"MonacoRegular",monospace !important;
}
.instructions{
	margin:10px auto;
}

#design-textarea{
	height:431px !important;
}

#css-textarea,
#javascript-textarea{
	height:230px !important;
}


#logo-logo{
	font-size:27px !important;
	font-family:Roboto-Black;
	float:left;
	opacity:1.0;
	margin-top:10px;
	text-align:center;
}


#logo-container{
	height:109px;
	position:relative;
}
#logo-logo-logo,
#logo-tagline,
#logo-byodo{
	display:block;
	line-height: 1.0em;
	position:absolute;
}

#logo-logo-logo{
	top:3px;
	font-size:76px;
}
#logo-bydodo{
	top:27px;
	left:62px;
	font-size:27px;
}
#logo-tagline{
	top:9px;
	left:62px;
	font-size:17px;
}

#bottom-navigation{
	color:#428bca;
	text-align:right;
	margin:71px 0px;
}

#bottom-navigation a{
	text-transform:uppercase;
}

label{
	font-family:Roboto-Regular;
	font-size:19px !important;
}


.notify{
	color:#2b2b34;
	margin-top:20px;
	margin-bottom:23px;
	padding:10px 20px;
	/*border:solid 1px #2b2b34;*/
	color:#3c3d41;
	border:solid 1px #3c3d41;	
	background:#fff;
	display:block;
	font-size:17px;
	cursor:hand;
	cursor:pointer;
	border-radius: 3px;
	-moz-border-radius: 3px;
	-webkit-border-radius: 3px;
}

.buttons-container{
	margin-top:70px;
}

.button-group a,
.button-group span{
	margin-right:12px;
	display:inline-block;
}

.back-href{
	display:block;
	margin-top:102px;
	margin-bottom:203px;
}

.form-row{
	display:block;
	clear:both;
	margin:10px auto 10px auto !important;
}

ul.navigation,
ul.navigation-tabs,
ul.nav-tabs{
	text-align: right;
	margin:20px auto;
}
ul.navigation li, 
ul.navigation-tabs li,
ul.nav-tabs li{
	float:right;
	margin-left:20px;
	text-align: right;
	padding-bottom:7px;
}

ul.navigation.left li, 
ul.navigation-tabs.left li,
ul.nav-tabs.left li{
	float:left;
	margin-right:20px;
	margin-left:0px;
	text-align: right;
	padding-bottom:7px;
}

ul.navigation li a, 
ul.navigation-tabs li a,
ul.nav-tabs li a{
	text-decoration:none;
}

.active{
	border-bottom:dashed 3px #12AFFF;
}

.input-container{
	display:block;
	margin-top:4px;
	margin-bottom:2px;
}

.tooltip {
  position: relative;
  display: inline-block;
  visibility: visible !important;
  opacity: 1.0 !important;
}

.tooltip .tooltiptext {
  visibility: hidden;
  width: 120px;
  background-color: #3b3b3b;
  color: #fff;
  text-align: center;
  border-radius: 6px;
  padding: 5px 0;

  /* Position the tooltip */
  position: absolute;
  z-index: 10001;
}

.tooltip:hover .tooltiptext {
  visibility: visible;
}

.grey{
	color:grey;
}

.href-dotted{
	border-bottom:dotted 1px #428bca;
}


.fifty{
	width:50px;
}
.seventyfive{
	width:75px;
}
.onehundred{
	width:100px;
}
.onefifty{
	width:150px;
}
.oneseventyfive{
	width:175px;
}
.twohundred{
	width:200px;
}
.twotwentyfive{
	width:225px;
}
.twofifty{
	width:250px;
}
.twoseventyfive{
	width:275px;
}
.threehundred{
	width:300px;
}
.threetwentyfive{
	width:325px;
}
.threefifty{
	width:350px;
}
.threeseventyfive{
	width:375px;
}
.fourhundred{
	width:400px;
}

.autowidth{
	width: auto !important;
}

.align-left{
	text-align: left;
}

.align-center{
	text-align: center;
}

.align-right{
	text-align: right;
}

.valign-middle{
	vertical-align: middle !important;
}

.valign-bottom{
	vertical-align: bottom !important;
}

.right-float{
	float:right;
}

.left-float{
	float:left;
}


.tiny,
.information,
.instructions{
	font-size:12px;
}

.tiny-tiny{
	font-size:10px;
}

p{
	font-size:19px;
	margin:20px 0px;
	line-height:1.62em;
}

.black {
	font-family:Roboto-Black !important;
}

.bold{
	font-family:Roboto-Bold !important;
}

.light{
	font-family:Roboto-Light !important;
}

.thin{
	font-family:Roboto-Thin !important;
}

.regular{
	font-family: Roboto-Regular !important;
}

.center{
	text-align:center;
}

.block{
	display:block;
}

.clear{
	clear:both;
}


.form-label {
	display:block;
	font-family:Roboto-Bold !important;
}

#pages-list-container{
	right:-344px; 
	top:250px; 
	width:350px;
	height:321px;
	position:absolute; 
	background:#fff; 
	padding:0px 30px 30px;
}
#pages-list-inner-container{
	height:201px !important;
	overflow:scroll;
	border-bottom:solid 1px #ddd;
}
#files-list-container{
	right:-200px; 
	top:500px; 
	width:350px;
	height:300px;
	overflow:scroll;
	position:absolute; 
	background:#fff; 
	padding:0px 30px 30px;
}

.page-container{
	margin-bottom:13px;
}
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
