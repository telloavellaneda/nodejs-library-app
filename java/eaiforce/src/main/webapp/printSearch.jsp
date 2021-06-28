<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.io.*" %>
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

int contador = 0;
double[] monto = new double[] {0, 0, 0};
Force force = (Force)session.getAttribute("force");
FormUtils formUtils = new FormUtils(session);
FormatValues fv = new FormatValues();
Pager pager = (session.getAttribute("pager") != null) ? (Pager)session.getAttribute("pager") : new Pager();

double tmpFrom = (pager.getAllPages().size() != 0) ? pager.getAllFrom() + 1 : pager.getAllFrom();
double tmpPage = (pager.getAllPages().size() != 0) ? pager.getAllCurrent() + 1 : pager.getAllCurrent();

@SuppressWarnings("unchecked")
LinkedHashMap<String, HashMap<String, String>> mainExport = (LinkedHashMap<String, HashMap<String, String>>) session.getAttribute("dsBuscar");
%>

<table class="width-1024 search">
	<tr class="search-summary search-summary-top">
		<td colspan="7">
			<span><%= fv.formatMoneyCompact(tmpFrom) + " - " + fv.formatMoneyCompact(pager.getAllTo()) + " (" + fv.formatMoneyCompact(tmpPage) + "/" + pager.getAllPages().size() + ")" %></span>
			<span><%= "Clientes: " + fv.formatMoneyCompact(pager.getAllTotal()) + "." %></span>
			<span><%= "Solicitado: " + fv.formatMoney(pager.getAllSolic()) + "." %></span>
			<span><%= "Aprobado: " + fv.formatMoney(pager.getAllAprob()) + "." %></span>
			<span><%= "Firmado: " + fv.formatMoney(pager.getAllFinal()) + "." %></span>
		</td>
	</tr>
	<tr class="search-desktop search-title">
		<td>FORCE ID</td>
		<td>NOMBRE</td>
		<td>STATUS</td>
		<td>FECHA</td>
		<td>BANCO</td>
		<td>MONTO</td>
		<td>USUARIO</td>
	</tr>
	<%		
	for (Iterator<String> iterator = mainExport.keySet().iterator(); iterator.hasNext();) {
		String id = iterator.next();		
		if ( id.equals("-1") )
			continue;
		
		String mobileRowClass = ( (contador % 2) == 0 ) ? "search-odd" : "search-even";
		%>
		<tr class="search-desktop search-desktop-row <%= mobileRowClass %>" onClick="q('<%= mainExport.get(id).get("INT_FORCE_ID") %>');">
			<td><%= mainExport.get(id).get("INT_FORCE_ID") %></td>
			<td>
				<%= fv.fullName(new String[] { mainExport.get(id).get("APELLIDO_PATERNO"), mainExport.get(id).get("APELLIDO_MATERNO"), mainExport.get(id).get("PRIMER_NOMBRE"), mainExport.get(id).get("SEGUNDO_NOMBRE") } ) %>
			</td>
			<td id="bold"><%= fv.shorten(mainExport.get(id).get("STATUS"), 20) %></td>
			<td><%= fv.formatTimestampCompact(mainExport.get(id).get("FEC_FECHA_EXPEDIENTE")) %></td>
			<td id="bold"><%= mainExport.get(id).get("BANCO") %></td>
			<td class="align-right" style="padding-right:5px">
				<%= fv.formatMoneyCompact(mainExport.get(id).get("DBL_IMPORTE_SOLIC")) %>
				<%= formUtils.displayLightBulb(mainExport.get(id).get("DECRETO"),"15") %>
			</td>
			<td id="bold"><%= mainExport.get(id).get("USUARIO") %></td>
		</tr>
		<tr class="search-mobile" onClick="q('<%= mainExport.get(id).get("INT_FORCE_ID") %>');">
			<td colspan="7">
				<table class="<%= mobileRowClass %>">
					<tr>
						<td>Force Id</td>
						<td id="bold"><%= mainExport.get(id).get("INT_FORCE_ID") %></td>
					</tr>
					<tr>
						<td>Nombre</td>
						<td id="bold"><%= fv.fullName(new String[] { mainExport.get(id).get("APELLIDO_PATERNO"), mainExport.get(id).get("APELLIDO_MATERNO"), mainExport.get(id).get("PRIMER_NOMBRE"), mainExport.get(id).get("SEGUNDO_NOMBRE") } ) %></td>
					</tr>
					<tr>
						<td>Status</td>
						<td id="bold"><%= fv.shorten(mainExport.get(id).get("STATUS"),20) %></td>
					</tr>
					<tr>
						<td>Fecha</td>
						<td id="bold"><%= fv.formatTimestamp(mainExport.get(id).get("FEC_FECHA_EXPEDIENTE")) %></td>
					</tr>
					<tr>
						<td>Banco</td>
						<td id="bold"><%= mainExport.get(id).get("BANCO") %></td>
					</tr>
					<tr>
						<td>Solicitado</td>
						<td id="bold"><%= fv.formatMoney(mainExport.get(id).get("DBL_IMPORTE_SOLIC")) %></td>
					</tr>
					<tr>
						<td>Aprobado</td>
						<td id="bold"><%= fv.formatMoney(mainExport.get(id).get("DBL_IMPORTE_APROB")) %></td>
					</tr>
					<tr>
						<td>Firmado</td>
						<td id="bold"><%= fv.formatMoney(mainExport.get(id).get("DBL_IMPORTE_FINAL")) %></td>
					</tr>
					<tr>
						<td>Decreto</td>
						<td id="bold">
							<%= formUtils.displayLightBulb(mainExport.get(id).get("DECRETO"),"15") %>
							<%= fv.validate(mainExport.get(id).get("DECRETO")) %>
						</td>
					</tr>
					<tr>
						<td>Usuario</td>
						<td id="bold"><%= mainExport.get(id).get("USUARIO") %></td>
					</tr>
				</table>
			</td>
		</tr>
		<%
		monto[0] += Double.parseDouble(mainExport.get(id).get("DBL_IMPORTE_SOLIC"));
		monto[1] += Double.parseDouble(mainExport.get(id).get("DBL_IMPORTE_APROB"));
		monto[2] += Double.parseDouble(mainExport.get(id).get("DBL_IMPORTE_FINAL"));
		contador++;
	} 							
	%>
	<tr class="search-summary search-summary-bottom">
		<td colspan="7">
			<span><%= fv.formatMoneyCompact(tmpFrom) + " - " + fv.formatMoneyCompact(pager.getAllTo()) + " (" + fv.formatMoneyCompact(tmpPage) + "/" + pager.getAllPages().size() + ")" %></span>
			<span><%= "Clientes: " + fv.formatMoneyCompact(pager.getAllTotal()) + "." %></span>
			<span><%= "Solicitado: " + fv.formatMoney(pager.getAllSolic()) + "." %></span>
			<span><%= "Aprobado: " + fv.formatMoney(pager.getAllAprob()) + "." %></span>
			<span><%= "Firmado: " + fv.formatMoney(pager.getAllFinal()) + "." %></span>
		</td>
	</tr>
</table>