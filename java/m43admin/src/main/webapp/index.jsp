<%@page import="com.mg43.service.FormElements"%>
<%
if ( session.getAttribute("force") != null ) {
	response.sendRedirect("home");
	return;
}

FormElements fElement = new FormElements();

String[][] meses = new String[][] { 
	{"2016-01","Enero 2016"},
	{"2016-02","Febrero 2016"},
	{"2016-03","Marzo 2016"},
	{"2016-04","Abril 2016"},
	{"2016-05","Mayo 2016"},
	{"2016-06","Junio 2016"},
	{"2016-07","Julio 2016"}
};
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui">
<title>EAI Admin</title>
<link rel="shortcut icon" href="images/favico.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="style/layout.css">
<link rel="stylesheet" type="text/css" href="style/generic.css">
<link rel="stylesheet" type="text/css" href="style/form.css">
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
						<div class="align-left">Mes</div>
						<div>
							<% 
							fElement.initControl();
							fElement.getControl().setId("usuario");
							fElement.getControl().setArrayOptions(meses);
							%>
							<%= fElement.createSelectElement() %>
						</div>
					</td>
				</tr>
				<tr>
					<td id="mensaje" class="height-25" style="color:#FF0004"></td>
				</tr>
				<tr>
					<td class="align_center">
						<input type="button" id="button" class="login" value="Generar Recibos" onClick="location.href = 'generar'">
						<div id="grecaptchaDiv"></div>					
					</td>
				</tr>
				<tr>
					<td>
						<table class="powered">
							<tr>
								<td></td>
							</tr>
						</table>
					</td>		
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="disclaimer">EAI Administraci�n &reg; 2016</td>
	</tr>	
</table>
</body>
</html>