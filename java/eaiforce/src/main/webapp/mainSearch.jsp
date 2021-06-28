<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.io.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "java.util.HashMap" %>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.LinkedHashMap" %>
<%@ page import = "force.eai.mx.util.Force" %>
<%@ page import = "force.eai.mx.util.Pager" %>
<%@ page import = "force.eai.mx.form.FormUtils" %>
<%@ page import = "force.eai.mx.dao.QryLoadData" %>
<%@ page import = "force.eai.mx.database.Connect" %>
<%@ page import = "force.eai.mx.tools.FormatValues" %>

<%
if ( session.getAttribute("force") == null ) {
	response.sendRedirect("/");
	return;
}

session.setAttribute("currentPage", "buscar");
session.removeAttribute("showFullNameFilter");

Force force = (Force)session.getAttribute("force");
FormUtils formUtils = new FormUtils(session);
FormatValues fv = new FormatValues();
Pager pager = new Pager();

//LinkedHashMap<String, HashMap<String, String>> mainExport = new LinkedHashMap<String, HashMap<String, String>>();

force.eai.mx.database.Connect connect = null;
force.eai.mx.orchestration.BuildDashboard buildDashboard = null;

try {
	connect = new force.eai.mx.database.Connect((String)session.getAttribute("env"));
	buildDashboard = new force.eai.mx.orchestration.BuildDashboard(connect.getConnection(), session);
	buildDashboard.setPageSize(formUtils.getIntFromSession("searchPageSize", 25));
	buildDashboard.count();
	
	pager = buildDashboard.getPager();
	buildDashboard.fetchSearch(pager.getAllCurrent());
	//mainExport = buildDashboard.getDsBuscar();
	session.setAttribute("pager", pager);
	
} catch (SQLException sqlException) {
	sqlException.printStackTrace();
	
} finally {
	buildDashboard.close();
	connect.close();
}

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
<link rel="stylesheet" href="style/search.css" type="text/css">
<link rel="stylesheet" href="style/form.css" type="text/css">
<script src="javascript/generic.js" type="text/javascript"></script>
<script src="javascript/upload.js" type="text/javascript"></script>
<script src="javascript/main.js" type="text/javascript"></script>
<script src="javascript/mainFormValidations.js" type="text/javascript"></script>
</head>

<body onLoad="onLoad();">

<%@ include file = "modal.jsp" %>

<%@ include file = "menu.jsp" %>

<%@ include file = "filter.jsp" %>

<table class="width-1024 search-menu">
	<tr>
		<td onClick="pagerBuscar('down');"><img src="images/b-back.png" alt=""/></td>
		<td onClick="report();"><img src="images/b-excel.png" alt=""/></td>
		<td onClick="pagerBuscar('up');"><img src="images/b-next.png" alt=""/></td>
	</tr>
</table>

<div id="divSearch">
<jsp:include page="printSearch.jsp" flush="true" />
</div>

<table class="width-1024 disclaimer">
	<tr>
		<td>EAI Force &reg; 2016 v1.0</td>
	</tr>
</table>
<br>
</body>
</html>