<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="images/favico.ico" type="image/x-icon">
<title>EAI Session Test</title>
</head>
<body>
<%

force.eai.mx.util.Cliente cliente = ( session.getAttribute("cliente") != null ) ? (force.eai.mx.util.Cliente)session.getAttribute("cliente") : new force.eai.mx.util.Cliente() ;

/*
force.eai.mx.util.Pager pager = (force.eai.mx.util.Pager)session.getAttribute("pager");
if ( pager != null ) {
	out.println(pager.getClTotal() + " " + pager.getClFrom() + "-" + pager.getClTo() + " [" + pager.getClCurrent() + "/" + pager.getClPages().size() + "]<br>");
	out.println(pager.getPrTotal() + " " + pager.getPrFrom() + "-" + pager.getPrTo() + " [" + pager.getPrCurrent() + "/" + pager.getPrPages().size() + "]<br>");
	out.println(pager.getAllTotal() + "<br>");
}*/

LinkedHashMap<String,LinkedHashMap<String,force.eai.mx.util.Catalogo>> profile = (LinkedHashMap<String,LinkedHashMap<String,force.eai.mx.util.Catalogo>>) session.getAttribute("filterCatalogues");

for (Iterator<force.eai.mx.util.Catalogo> iterator = profile.get("usuariosResponsables").values().iterator(); iterator.hasNext();) {
	force.eai.mx.util.Catalogo id = iterator.next();
	out.println(id.getNombre() + " " + id.getId() + "<br>");
	//out.println("documento " + id + " - " + documento.getForceId() + " - " + documento.getNombreDocumento() + " " + documento.getDriveBase() + " " + documento.getDriveId() + "<br>");
}
//out.println( cliente.getDriveId() + "<br>");

//for (Iterator<force.eai.mx.util.Seguimiento> iterator = cliente.getSeguimiento().values().iterator(); iterator.hasNext();) {
	//force.eai.mx.util.Seguimiento seguimiento = iterator.next();
	//out.println(seguimiento.getForceId() + " " + seguimiento.getId() + " " + seguimiento.getMensaje() + "<br>");
//}
//out.println("<br><br>");

%>
<table align="center" width="85%" style="border:1px; border-style:solid;" border="1">
<%
Enumeration<String> abc = session.getAttributeNames();
while ( abc.hasMoreElements() ) {
	String temp = abc.nextElement();
	//out.println(temp + " " + session.getAttribute(temp) + " " + session.getAttribute(temp).getClass() + "<br>");
	%>
	<tr>
		<td width="10%" valign="top"><%= temp %></td>
		<td width="15%" valign="top" align="center"><%= ( session.getAttribute(temp).getClass().getSimpleName().toString().equals("String") ) ? session.getAttribute(temp) : "" %></td>
		<td width="65%" valign="top"><%//= ( !session.getAttribute(temp).getClass().getSimpleName().toString().equals("String") ) ? session.getAttribute(temp) : "" %></td>		
		<td width="10%" valign="top"><%= session.getAttribute(temp).getClass().getSimpleName() %></td>
	</tr>
	<%
}
%>
</table>


</body>
</html>