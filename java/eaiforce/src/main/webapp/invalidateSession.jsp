<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import = "force.eai.mx.util.Force" %>
<%@ page import = "force.eai.mx.form.FormUtils" %>
<%@ page import = "force.eai.mx.database.Connect" %>
<%@ page import = "force.eai.mx.orchestration.UpsertForce" %>

<%
if ( session.getAttribute("force") == null ) {
	response.sendRedirect("/");
	return;
}

// Variable Definition
String alert = "";
PrintWriter salida = response.getWriter();

Connect connect;
if ( session.getAttribute("force") != null ) {
	connect = new Connect((String)session.getAttribute("env"));
	Force force = (Force)session.getAttribute("force");
	
	FormUtils formUtils = new FormUtils(session);
	String[] preferences = formUtils.getPreferences();
	force.getUsuario("0").setFiltros(preferences[0]);
	force.getUsuario("0").setWidgets(preferences[1]);
	
	UpsertForce upsertForce = new UpsertForce(connect.getConnection());
	upsertForce.setForce(force);
	upsertForce.upsertPreferences();
	
	alert = upsertForce.getMessage().getMessage();
	connect.close();
}

session.invalidate();
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
<link rel="stylesheet" href="style/form.css" type="text/css">
<script src="javascript/generic.js" type="text/javascript"></script>
</head>

<body onLoad="thankNote();">

<%@ include file = "modal.jsp" %>

<input type="hidden" id="note" value="<%= alert %>">
</body>
</html>
