<%@ page import = "force.eai.mx.util.Force" %>
<%@ page import = "force.eai.mx.tools.FormatValues" %>

<%
if ( session.getAttribute("force") == null ) {
	response.sendRedirect("/");
	return;
}

Force force = (Force)session.getAttribute("force");
FormatValues fv = new FormatValues();

String displayTableAnual = (session.getAttribute("tableAnual") != null) ? (String)session.getAttribute("tableAnual") : "table";
session.setAttribute("showFullNameFilter","false");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<title>EAI Force</title>
<link rel="shortcut icon" href="images/favico.ico" type="image/x-icon">
<link rel="stylesheet" href="style/layout.css" type="text/css">
<link rel="stylesheet" href="style/generic.css" type="text/css">
<link rel="stylesheet" href="style/menu.css" type="text/css">
<script src="javascript/generic.js" type="text/javascript"></script>
<script src="javascript/main.js" type="text/javascript"></script>
</head>

<body onLoad="onLoad();">

<%@ include file = "modal.jsp" %>

<table class="width-768 menu-bar shadow">
	<tr>
		<td class="title">EAI Force</td>
	</tr>
</table>

<table class="width-768 menu-welcome">
	<tr>
		<td>
			<span>Bienvenido</span>
			<span><%= fv.titleCase(force.getUsuario("0").getNombre() + " " + force.getUsuario("0").getApellidoPaterno()) %></span>
		</td>
		<td>
			<span>&Uacute;ltimo Ingreso</span>
			<span><%= fv.formatTimestamp(force.getUsuario("0").getFechaIngreso()) %></span>		
		</td>
	</tr>
</table>

<table class="width-768 menu-container">
	<tr>
		<td>
			<table class="menu-button menu-button-a shadow" onClick="showProgress(true); prospecto();">
				<tr>
					<td>
						<div>
							<div style="background-image:url('../images/menu-new.png');"></div>
						</div>
					</td>
				</tr>
				<tr>
					<td>Prospecto</td>
				</tr>
			</table>
		</td>
		<td>
			<table class="menu-button menu-button-b shadow" onClick="showProgress(true); location.href='buscar';">
				<tr>
					<td>
						<div>
							<div style="background-image:url('../images/menu-search.png');"></div>
						</div>
					</td>
				</tr>
				<tr>
					<td>Buscar</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table class="menu-button menu-button-c shadow" onClick="showProgress(true); location.href='analisis';">
				<tr>
					<td>
						<div>
							<div style="background-image:url('../images/menu-analysis.png');"></div>
						</div>
					</td>
				</tr>
				<tr>
					<td style="color:#FFFFFF">An&aacute;lisis</td>
				</tr>
			</table>
		</td>
		<td>
			<table class="menu-button menu-button-d shadow" onClick="showProgress(true); location.href='notificaciones'">
				<tr>
					<td>
						<div>
							<div style="background-image:url('../images/menu-notification.png');"></div>
						</div>
					</td>
				</tr>
				<tr>
					<td style="color:#FFFFFF">Notificaciones</td>
				</tr>
			</table>
		</td>
	</tr>	
	<tr>
		<td>&nbsp;</td>
		<td>
			<table class="menu-button menu-button-e shadow" onClick="location.href='cerrar'">
				<tr>
					<td>
						<div>
							<div style="background-image:url('../images/menu-session.png');"></div>
						</div>
					</td>
				</tr>
				<tr>
					<td style="color:#FFFFFF">Cerrar Sesi&oacute;n</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table class="width-768 disclaimer">
	<tr>
		<td>EAI Force &reg; 2016 v1.0</td>
	</tr>
</table>
<br>

</body>
</html>