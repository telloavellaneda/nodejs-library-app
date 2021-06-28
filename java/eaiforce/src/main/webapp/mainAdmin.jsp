<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.sql.*" %>
<%@ page import = "force.eai.mx.database.Connect" %>

<% 
if ( session.getAttribute("force") == null ) {
	//response.sendRedirect("/");
	//return;
}

Connect connect =  new Connect("dev");
ResultSet rset = null;
Statement stmt = null;

stmt = connect.getConnection().createStatement();

String idCliente = "1001";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EAI Force Admin</title>
<script src="javascript/generic.js" type="text/javascript"></script>
<script>
function carga() {
	var bancos = document.getElementById("bancos");
	var productos = document.getElementById("productos");
	var bancosProductos = document.getElementById("bancos_productos");
	
	for ( var i = 0; i < bancos.length; i ++) {
		
		if ( bancos[i].selected ) {
			for ( var j = 0; j < productos.length; j ++) {		
				if ( productos[j].selected ) {			
					var x = document.createElement("option");
					x.value = bancos[i].value + "_" + productos[j].value;
					x.innerHTML = bancos[i].innerHTML + " - " + productos[j].innerHTML;
					bancosProductos.appendChild(x);
				}
			}
		}
	}
}

function queryStringAdmin() {
	var queryString = "";
	var bancosProductos = document.getElementById("bancos_productos");
	var idCliente = document.getElementById("id_cliente");
	
	queryString = "x=" + idCliente.value;
	for ( var i = 0; i < bancosProductos.length; i ++)	
		queryString += "&q=" + bancosProductos[i].value;
	
	return queryString;
}

function adminUpsert( button ) {
	var queryString = queryStringAdmin();
	
	request.url = "administerData";
	request.queryString = "?" + queryString;
	request.message = "";
	request.value = button.value;
	request.object = document.getElementById("floatDivMessage");
	request.button = button;
	
	makeAajaxCall(request, adminUpsertResponse);
}


function adminUpsertResponse( abc ) {
	alert(abc);
	
}
</script>
</head>
<body>

Id Cliente
	<input type="text" id="id_cliente" value="<%= idCliente %>">
	
	<br>
	
	<select name="select" id="bancos" multiple style="height:200px">
		<%
rset = stmt.executeQuery("SELECT * FROM EAI_CAT_BANCOS ORDER BY NOMBRE");
while (rset.next()) { %>
		<option value="<%= rset.getString(1) %>"><%= rset.getString(3) %></option>
		<%
}
%>
	</select>
	<br>
	<select name="select" id="productos" multiple style="height:200px">
		<%
rset = stmt.executeQuery("SELECT * FROM EAI_CAT_PRODUCTOS ORDER BY NOMBRE");
while (rset.next()) {
%>
		<option value="<%= rset.getString(1) %>"><%= rset.getString(2) %></option>
		<%
}
%>
	</select>
	<br>
	<select name="select" id="bancos_productos" multiple style="height:150px">
	</select>
	
<br>
<div id="floatDivMessage"></div>
<input type="button" name="button" id="button" value="Agregar" onClick="carga();">
<input type="button" name="button" id="button" value="Insertar" onClick="adminUpsert(this);">
<br>
<br>
<%
String query = "";

query += "SELECT\n" +
		"B.NOMBRE BANCO, C.NOMBRE PRODUCTO\n" +
		"FROM EAI_ADM_USUARIOS_BANCOS A,\n" +
		"EAI_CAT_BANCOS B,\n" +
		"EAI_CAT_PRODUCTOS C\n" +
		"WHERE A.ID_BANCO = B.ID_BANCO\n" +
		"AND A.ID_PRODUCTO = C.ID_PRODUCTO\n" +
		"ORDER BY B.NOMBRE, C.NOMBRE";

rset = stmt.executeQuery(query);
while (rset.next()) {
	out.println(rset.getString(1) + " " + rset.getString(2) + "<br>");
}
%>

</body>
</html>