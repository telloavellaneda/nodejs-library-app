<%
if ( session.getAttribute("force") != null ) {
	response.sendRedirect("home");
	return;
}

force.eai.mx.form.FormElements fElement = new force.eai.mx.form.FormElements();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui">
<title>EAI Force</title>
<link rel="shortcut icon" href="images/favico.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="style/layout.css">
<link rel="stylesheet" type="text/css" href="style/generic.css">
<link rel="stylesheet" type="text/css" href="style/form.css">
<script src="javascript/login.js" type="text/javascript"></script>
<script src="javascript/generic.js" type="text/javascript"></script>
<script src="https://www.google.com/recaptcha/api.js?render=explicit&hl=es" async defer></script>
<!-- Begin DigiCert site seal JavaScript code -->
<script type="text/javascript">
var __dcid = __dcid || [];__dcid.push(["DigiCertClickID_mmlfeMlu", "13", "s", "black", "mmlfeMlu"]);(function(){var cid=document.createElement("script");cid.async=true;cid.src="//seal.digicert.com/seals/cascade/seal.min.js";var s = document.getElementsByTagName("script");var ls = s[(s.length - 1)];ls.parentNode.insertBefore(cid, ls.nextSibling);}());
</script>
<!-- End DigiCert site seal JavaScript code -->
</head>

<body onLoad="self.focus(); document.getElementById('usuario').focus();">

<table class="width-470 box shadow top-10">
	<tr>
		<td class="title">Login</td>
	</tr>
	<tr>
		<td class="container">
			<table>
				<tr>
					<td>
						<div class="logo">
							<div class="outer"></div>
						</div>					
					</td>
				</tr>
				<tr>
					<td class="login">
						<div class="align-left">Usuario</div>
						<div>
							<% 
							fElement.initControl();
							fElement.getControl().setId("usuario");
							fElement.getControl().setType("email");
							fElement.getControl().setClassName("login");
							fElement.getControl().setValue("gerente@byc.mx");
							fElement.getControl().setOnFocus("borraMensaje();");
							%>
							<%= fElement.createInputElement() %>
						</div>
					</td>
				</tr>
				<tr>
					<td class="login">
						<div class="align-left">Contrase&ntilde;a</div>
						<div>
							<input type="password" id="password" class="login" onFocus="borraMensaje();" onBlur="validaPassword(this);" maxlength="15" value="force">	
						</div>
					</td>
				</tr>
				<tr>
					<td id="mensaje" class="height-25" style="color:#FF0004"></td>
				</tr>
				<tr>
					<td class="align_center">
						<input type="button" id="button" class="login" value="Ingresar" onClick="checkForm();">
						<div id="grecaptchaDiv"></div>					
					</td>
				</tr>
				<tr>
					<td>
						<table class="powered">
							<tr>
								<td>
									<!-- Begin DigiCert site seal HTML -->
									<div id="DigiCertClickID_mmlfeMlu" data-language="en"></div>
									<!-- End DigiCert site seal HTML -->					
								</td>
							</tr>
						</table>
					</td>		
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="disclaimer">EAI Force &reg; 2016 v1.0</td>
	</tr>	
</table>
<br>
</body>
</html>