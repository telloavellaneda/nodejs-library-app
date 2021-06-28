<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
if ( session.getAttribute("force") == null || request.getParameter("x") == null ) {
	return;
}

force.eai.mx.util.Cliente cliente = ( session.getAttribute("cliente") != null ) ? (force.eai.mx.util.Cliente)session.getAttribute("cliente") : new force.eai.mx.util.Cliente(); 
force.eai.mx.security.SecurityUtils su = new force.eai.mx.security.SecurityUtils();
force.eai.mx.tools.FormatValues fv = new force.eai.mx.tools.FormatValues();

String queryString = su.decode64(request.getParameter("x"));
String id = queryString.split("&")[0];
String msg = "";

if ( queryString.split("&").length > 1 ) {
	id = queryString.split("&")[0].split("=")[1];
	msg = new force.eai.mx.tools.BuildJson().getValue(queryString.split("&")[1].split("=")[1],"message");
}

String bIndicator = ( !cliente.getDocumento(id).getNombreArchivo().equals("") ) ? "green" : "gray" ;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<title>EAI Force</title>
<link rel="shortcut icon" href="../images/favico.ico" type="image/x-icon">
<link rel="stylesheet" href="../style/layout.css" type="text/css">
<link rel="stylesheet" href="../style/upload.css" type="text/css">
<script src="../javascript/generic.js" type="text/javascript"></script>
<script src="../javascript/upload.js" type="text/javascript"></script>
</head>

<body onLoad="fileOnLoad('<%= msg %>');">
<table class="upload">
	<tr>
		<td>
			<table class="info">
				<tr>
					<td><img src="../images/b-<%= bIndicator %>.png"/></td>
					<td class="link" onClick="drive('<%= id %>');"><%= cliente.getDocumento(id).getNombreArchivo() %></td>
				</tr>
				<tr>
					<td colspan="2" class="fecha"><%= (!cliente.getDocumento(id).getFechaEntrega().equals("")) ? "Fecha: " + fv.formatTimestamp(cliente.getDocumento(id).getFechaEntrega()) : "" %></td>
				</tr>
			</table>		
		</td>
	</tr>
	<tr>
		<td>
			<table>
				<tr>
					<td class="label">
						<label for="file">
							<table>
								<tr>
									<td id="label" >Seleccione</td>
								</tr>
							</table>
						</label>
					</td>
					<td class="button" onClick="validate();">
						<svg width="20" height="17" viewBox="0 0 20 17">
							<path d="M10 0l-5.2 4.9h3.3v5.1h3.8v-5.1h3.3l-5.2-4.9zm9.3 11.5l-3.2-2.1h-2l3.4 2.6h-3.5c-.1 0-.2.1-.2.1l-.8 2.3h-6l-.8-2.2c-.1-.1-.1-.2-.2-.2h-3.6l3.4-2.6h-2l-3.2 2.1c-.4.3-.7 1-.6 1.5l.6 3.1c.1.5.7.9 1.2.9h16.3c.6 0 1.1-.4 1.3-.9l.6-3.1c.1-.5-.2-1.2-.7-1.5z"/>
						</svg>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<form id="form" action="/uploadFiles" method="post" enctype="multipart/form-data">
<input type="file" id="file" name="<%= request.getParameter("x") %>" class="inputfile inputfile-1" onChange="placeText(this);">
</form>					

</body>
</html>