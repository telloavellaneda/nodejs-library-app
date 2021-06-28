<%@ page import = "java.sql.SQLException" %>
<%@ page import = "force.eai.mx.util.Force" %>
<%@ page import = "force.eai.mx.util.Pager" %>
<%@ page import = "force.eai.mx.form.FormUtils" %>
<%@ page import = "force.eai.mx.tools.FormatValues" %>

<%
if ( session.getAttribute("force") == null ) {
	response.sendRedirect("/");
	return;
}

session.setAttribute("currentPage","analisis");
session.setAttribute("showFullNameFilter","false");
session.removeAttribute("filter_force_id");
session.removeAttribute("filter_full_name");

Force force = (Force)session.getAttribute("force");
FormUtils fu = new FormUtils(session);
FormatValues fv = new FormatValues();
Pager pager = new Pager();

String ultimaFechaIngreso = fv.formatTimestamp(force.getUsuario("0").getFechaIngreso());

String displayDivFases 		= fu.getStringFromSession("divFases", "block");
String displayDivStatus 	= fu.getStringFromSession("divStatus", "block");
String displayDivBancos 	= fu.getStringFromSession("divBancos", "block");
String displayDivUsuarios 	= fu.getStringFromSession("divUsuarios", "block");
String displayDivMonth 		= fu.getStringFromSession("divMonth", "block");
String displayDivDecreto 	= fu.getStringFromSession("divDecreto", "block");
String displayDivProspectos = fu.getStringFromSession("showDivProspectos", "block");
String displayDivClientes 	= fu.getStringFromSession("showDivClientes", "block");

force.eai.mx.database.Connect connect = null;
force.eai.mx.orchestration.BuildDashboard buildDashboard = null;

try {
	connect = new force.eai.mx.database.Connect((String)session.getAttribute("env"));
	buildDashboard = new force.eai.mx.orchestration.BuildDashboard(connect.getConnection(), session);
	buildDashboard.setPageSize(fu.getIntFromSession("pageSize", 25));
	buildDashboard.count();
	buildDashboard.build();
		
	pager = buildDashboard.getPager();
	buildDashboard.fetchClientes(pager.getClCurrent(), true);
	buildDashboard.fetchProspectos(pager.getPrCurrent(), true);
		
} catch (SQLException|NullPointerException sqlException) {
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
<link rel="stylesheet" href="style/dashboard.css" type="text/css">
<link rel="stylesheet" href="style/main.css" type="text/css">
<link rel="stylesheet" href="style/form.css" type="text/css">
<script src="javascript/generic.js" type="text/javascript"></script>
<script src="javascript/main.js" type="text/javascript"></script>
</head>

<body onLoad="loadWidget();">

<%@ include file = "modal.jsp" %>

<%@ include file = "menu.jsp" %>

<%@ include file = "filter.jsp" %>

<table class="width-1024" style="margin-bottom:10px">
	<tr>
		<td class="main-message">
			<span><%= "Clientes: " + fv.formatMoneyCompact(pager.getAllTotal()) + "." %></span>
			<span><%= "Solicitado: " + fv.formatMoney(pager.getAllSolic()) + "." %></span>
			<span><%= "Aprobado: " + fv.formatMoney(pager.getAllAprob()) + "." %></span>
			<span><%= "Firmado: " + fv.formatMoney(pager.getAllFinal()) + "." %></span>
		</td>
	</tr>
</table>

<table class="width-1024 main">
	<tr>
		<td class="left">
			<table class="box green">
				<tr>
					<td class="title" style="cursor:pointer" onClick="toggleDivDisplay('showDivClientes');">BANDEJA DE CLIENTES</td>
				</tr>
				<tr>
					<td>
						<div id="showDivClientes" style="display:<%= displayDivClientes %>">
							<table class="pager pager-green">
								<tr>
									<td onClick="pager('clientes','down');">Anterior</td>
									<td onClick="pager('clientes','up');">Siguiente</td>
								</tr>
							</table>
							<div id="divclientes">
								<%@ include file = "pleaseWait.jsp" %>
							</div>
						</div>
					</td>
				</tr>
			</table>			
		</td>
		<td class="right">
			<table class="box yellow">
				<tr>
					<td class="title" style="cursor:pointer" onClick="toggleDivDisplay('showDivProspectos');">BANDEJA DE PROSPECTOS</td>
				</tr>
				<tr>
					<td>
						<div id="showDivProspectos" style="display:<%= displayDivProspectos %>">
							<table class="pager pager-yellow">
								<tr>
									<td onClick="pager('prospectos','down');">Anterior</td>
									<td onClick="pager('prospectos','up');">Siguiente</td>
								</tr>
							</table>
							<div id="divprospectos">
								<%@ include file = "pleaseWait.jsp" %>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table class="width-1024 main">
	<tr>
		<td>
			<table class="box">
				<tr>
					<td class="title" style="cursor:pointer" onClick="toggleDivDisplay('divFases');">CLIENTES Y MONTOS POR FASE</td>
				</tr>
				<tr>
					<td>
						<div id="divFases" style="height:100px; display:<%= displayDivFases %>">
							<%@ include file = "pleaseWait.jsp" %>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table class="box">
				<tr>
					<td class="title" style="cursor:pointer" onClick="toggleDivDisplay('divStatus');">CLIENTES Y MONTOS POR STATUS</td>
				</tr>
				<tr>
					<td>			
						<div id="divStatus" style="height:100px; display:<%= displayDivStatus %>">
							<%@ include file = "pleaseWait.jsp" %>
						</div>
					</td>
				</tr>
			</table>		
		</td>
	</tr>
	<tr>
		<td>
			<table class="box">
				<tr>
					<td class="title" style="cursor:pointer" onClick="toggleDivDisplay('divBancos');">CLIENTES Y MONTOS POR BANCO</td>
				</tr>
				<tr>
					<td>
						<div id="divBancos" style="height:100px; display:<%= displayDivBancos %>">
							<%@ include file = "pleaseWait.jsp" %>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table class="box">
				<tr>
					<td class="title" style="cursor:pointer" onClick="toggleDivDisplay('divUsuarios');">CLIENTES Y MONTOS POR USUARIO</td>
				</tr>
				<tr>
					<td>
						<div id="divUsuarios" style="height:100px; display:<%= displayDivUsuarios %>">
							<%@ include file = "pleaseWait.jsp" %>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table class="box">
				<tr>
					<td class="title" style="cursor:pointer" onClick="toggleDivDisplay('divMonth');">COMPORTAMIENTO POR MES</td>
				</tr>
				<tr>
					<td>			
						<div id="divMonth" style="height:100px; display:<%= displayDivMonth %>">
							<%@ include file = "pleaseWait.jsp" %>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td>
			<table class="box">
				<tr>
					<td class="title" style="cursor:pointer" onClick="toggleDivDisplay('divDecreto');">CLIENTES Y MONTOS POR DECRETO</td>
				</tr>
				<tr>
					<td>
						<div id="divDecreto" style="height:100px; display:<%= displayDivDecreto %>">
							<%@ include file = "pleaseWait.jsp" %>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table class="width-1024" style="margin-top:10px">
	<tr>
		<td class="main-message" id="floatDivMessage">
			<span><%= "Clientes: " + fv.formatMoneyCompact(pager.getAllTotal()) + "." %></span>
			<span><%= "Solicitado: " + fv.formatMoney(pager.getAllSolic()) + "." %></span>
			<span><%= "Aprobado: " + fv.formatMoney(pager.getAllAprob()) + "." %></span>
			<span><%= "Firmado: " + fv.formatMoney(pager.getAllFinal()) + "." %></span>
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