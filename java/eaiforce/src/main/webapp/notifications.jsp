<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "force.eai.mx.util.Force" %>
<%@ page import = "force.eai.mx.util.Filter" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ page import = "force.eai.mx.form.FormUtils" %>
<%@ page import = "force.eai.mx.dao.QryLoadData" %>
<%@ page import = "force.eai.mx.database.Connect" %>
<%@ page import = "force.eai.mx.form.FormElements" %>
<%@ page import = "force.eai.mx.tools.FormatValues" %>

<%
if ( session.getAttribute("force") == null ) {
	response.sendRedirect("/");
	return;
}

Force force = (Force)session.getAttribute("force");
Filter filter = new Filter();
FormatValues fv = new FormatValues();
FormElements fElement = new FormElements();
FormUtils formUtils = new FormUtils(session);
QryLoadData loadData = new QryLoadData(force);

int contador = 0;
ResultSet rset = null;
Statement stmt = null;

String displayTableNotificaciones = (session.getAttribute("tableNotificaciones") != null) ? (String)session.getAttribute("tableNotificaciones") : "table";

Connect connect =  new Connect((String)session.getAttribute("env"));
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
<link rel="stylesheet" href="style/dashboard.css" type="text/css">
<link rel="stylesheet" href="style/form.css" type="text/css">
<script src="javascript/generic.js" type="text/javascript"></script>
<script src="javascript/main.js" type="text/javascript"></script>
</head>

<body onLoad="onLoad();">

<%@ include file = "modal.jsp" %>

<%@ include file = "menu.jsp" %>

<table class="width-1024 box" style="margin-bottom:15px;">
	<tr>
		<td class="title" style="cursor:pointer" onClick="toggleDivDisplay('tableNotificaciones');">NOTIFICACIONES &Uacute;LTIMOS 3 D&Iacute;AS</td>
	</tr>
	<tr>
		<td>
			<table id="tableNotificaciones" style="display:<%= displayTableNotificaciones %>">
				<tr class="widget-desktop notificaciones-title">
					<td>Usuario</td>
					<td>Mensaje</td>
					<td>Fecha</td>
					<td>Force Id</td>
					<td>Cliente</td>							
				</tr>
				<%
				try {
					stmt = connect.getConnection().createStatement();
	
					contador = 0;
					loadData.setFilter(filter);
					loadData.getFilter().setOrderDesc(true);
					rset = stmt.executeQuery(loadData.getNotificacionesQuery("2"));
					while ( rset.next() ) {
						int caracteres = 200;
						String evenOddClass = ( (contador % 2) == 0 ) ? "widget-row-odd" : "widget-row-even";
						String evenOddDivider = ( (contador % 2) == 0 ) ? "notificaciones-mobile-odd" : "notificaciones-mobile-even";
						String toShow = fv.shorten(fv.prettyFormat(rset.getString(4)), 200); 
						%>
						<tr class="widget-desktop notificaciones <%= evenOddClass %>" style="cursor:pointer" onClick="q('<%= rset.getString("FORCE_ID") %>');">
							<td><%= fv.titleCase(rset.getString("USUARIO")) %></td>
							<td id="bold"><%= toShow %></td>
							<td><%= fv.formatTimestamp(rset.getString("FECHA_CREACION")) %></td>
							<td id="bold"><%= rset.getString("FORCE_ID") %></td>
							<td><%= fv.fullName(new String[] { rset.getString("CL_PATERNO"), rset.getString("CL_MATERNO"), rset.getString("CL_NOMBRE"), rset.getString("CL_SEGUNDO") }) %></td>
						</tr>
						<tr class="widget-mobile <%= evenOddClass %>" style="cursor:pointer" onClick="q('<%= rset.getString("FORCE_ID") %>');">
							<td colspan="5">
								<table class="notificaciones-mobile <%= evenOddDivider %>">
									<tr>
										<td>Usuario</td>
										<td id="bold"><%= fv.titleCase(rset.getString("USUARIO")) %></td>
									</tr>
									<tr>
										<td>Mensaje</td>
										<td id="bold"><%= toShow %></td>
									</tr>
									<tr>
										<td>Fecha</td>
										<td id="bold"><%= fv.formatTimestamp(rset.getString("FECHA_CREACION")) %></td>
									</tr>
									<tr>
										<td>Force Id</td>
										<td id="bold"><%= rset.getString("FORCE_ID") %></td>
									</tr>
									<tr>
										<td>Cliente</td>
										<td id="bold"><%= fv.fullName(new String[] { rset.getString("CL_PATERNO"), rset.getString("CL_MATERNO"), rset.getString("CL_NOMBRE"), rset.getString("CL_SEGUNDO") }) %></td>
									</tr>
								</table>
							</td>
						</tr>
						<%
						contador++;
					}
	
					rset.close();
					stmt.close();
					connect.close();
					
				} catch(Exception ex) {
					ex.printStackTrace();
				}
				%>
				<tr>
					<td class="footer-box" colspan="5"><span><%= contador + " Mensajes." %></span></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table class="width-1024 disclaimer">
	<tr>
		<td>EAI Force &reg; 2016 v1.0</td>
	</tr>
</table>
<br>

</body>
</html>