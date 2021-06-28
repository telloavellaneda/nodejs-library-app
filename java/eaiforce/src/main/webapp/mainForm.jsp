<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
if ( session.getAttribute("force") == null ) {
	response.sendRedirect("/");
	return;
}

String section = ( session.getAttribute("section") != null ) ? (String) session.getAttribute("section") : "section0";
force.eai.mx.util.Cliente clienteForm = ( session.getAttribute("cliente") != null ) ? (force.eai.mx.util.Cliente)session.getAttribute("cliente") : new force.eai.mx.util.Cliente() ;
force.eai.mx.util.Force forceForm = ( session.getAttribute("force") != null ) ? (force.eai.mx.util.Force)session.getAttribute("force") : new force.eai.mx.util.Force() ;

force.eai.mx.form.FormElements formElement = new force.eai.mx.form.FormElements();
force.eai.mx.form.FormUtils formUtil = new force.eai.mx.form.FormUtils(session);

String displayDivFormHeader = (session.getAttribute("divFormHeader") != null) ? (String)session.getAttribute("divFormHeader") : "block";
String displayDivFormStatus = (session.getAttribute("divFormStatus") != null) ? (String)session.getAttribute("divFormStatus") : "block";
String displayDivFormSection = (session.getAttribute("divFormSection") != null) ? (String)session.getAttribute("divFormSection") : "block";
String displayDivFormDocumentos = (session.getAttribute("divFormDocumentos") != null) ? (String)session.getAttribute("divFormDocumentos") : "block";
String displayDivFormMuro = (session.getAttribute("divFormMuro") != null) ? (String)session.getAttribute("divFormMuro") : "block";
String displayDivFormHistorial = (session.getAttribute("divFormHistorial") != null) ? (String)session.getAttribute("divFormHistorial") : "block";

// Functional Validation
// If users reloads page before saving "Coacreditado" field
if ( clienteForm.getCreditos("0").getCoacreditado().equals("") && 
		(
		section.equals("section8") || 
		section.equals("section9") || 
		section.equals("section10") || 
		section.equals("section11") 
		) 
	) {
	section = "section1";
	session.setAttribute("section", section);
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
<link rel="stylesheet" href="style/mainForm.css" type="text/css">
<link rel="stylesheet" href="style/form.css" type="text/css">
<script src="javascript/generic.js" type="text/javascript"></script>
<script src="javascript/mainForm.js" type="text/javascript"></script>
<script src="javascript/mainFormQueryString.js" type="text/javascript"></script>
<script src="javascript/mainFormCalls.js" type="text/javascript" charset="ISO-8859-1"></script>
<script src="javascript/mainFormValidations.js" type="text/javascript"></script>
<script src="javascript/mainFormRequired.js" type="text/javascript"></script>
<script src="javascript/mainFormDatosGenerales.js" type="text/javascript"></script>
</head>

<body onLoad="getFormTitle(); trackingInterval(); onLoad();">

<%@ include file = "modal.jsp" %>

<%@ include file = "menu.jsp" %>

<table class="width-1024 form">
	<tr>
		<td class="left">
			<!-- REFRESH BUTTON -->
			<table>
				<tr>
					<td class="refresh-title" style="cursor:pointer" onClick="refreshCustomer();">
						<img src="images/b-refresh.png"/>
					</td>
				</tr>
			</table>
			
			<!-- DATOS PRINCIPALES -->
			<table class="box">
				<tr>
					<td class="title" style="cursor:pointer" onClick="toggleDivDisplay('divFormHeader');">DATOS PRINCIPALES</td>
				</tr>
				<tr>
					<td id="divFormHeader" style="display:<%= displayDivFormHeader %>">
						<%@ include file = "include/datosHeader.jsp" %>
					</td>
				</tr>
			</table>
			<!-- STATUS -->
			<table class="box yellow">
				<tr>
					<td class="title" style="cursor:pointer;" onClick="toggleDivDisplay('divFormStatus');">STATUS</td>
				</tr>
				<tr>
					<td id="divFormStatus" style="display:<%= displayDivFormStatus %>">
						<%@ include file = "include/datosStatus.jsp" %>
					</td>
				</tr>
			</table>
			<!-- MAIN FORM -->
			<table class="box blue">
				<tr>
					<td class="title" id="formSectionTitle" style="cursor:pointer" onClick="toggleDivDisplay('divFormSection');"></td>
				</tr>
				<tr>
					<td id="divFormSection" style="display:<%= displayDivFormSection %>">
						<%@ include file = "include/datosHeaderMenu.jsp" %>
						
						<div class="form-div">
						<% if ( section.equals("section0") /*&& clienteForm.getForceId().equals("")*/ ) { 
							force.eai.mx.util.Section itSection = formUtil.getFormSection("section0");
							%>
							<table id="<%= itSection.getSection() %>" formTitle="<%= itSection.getTitle() %>" preffix="<%= itSection.getPreffix() %>" <%= ( section.equals(itSection.getSection()) ) ? "" : "style=\"display:none\"" %>>
								<tr>
									<td>
										<div id="<%= itSection.getDivId() %>">
											<jsp:include page="<%= itSection.getIncludeFile() %>" flush="true" />
										</div>
									</td>
								</tr>
							</table>								
						<% } %>

						<% for ( java.util.Iterator<force.eai.mx.util.Section> iterator = formUtil.getFormSections(); iterator.hasNext(); ) { 
							force.eai.mx.util.Section itSection = iterator.next();
							if (!itSection.getSection().equals("section0")) {
							%>
							<table id="<%= itSection.getSection() %>" formTitle="<%= itSection.getTitle() %>" preffix="<%= itSection.getPreffix() %>" <%= ( section.equals(itSection.getSection()) ) ? "" : "style=\"display:none\"" %>>
								<tr>
									<td>
										<div id="<%= itSection.getDivId() %>">
											<jsp:include page="<%= itSection.getIncludeFile() %>" flush="true" />
										</div>
									</td>
								</tr>
							</table>
							<% } %>
						<% } %>
						</div>
						<table>
							<tr>
								<td class="footer-box">
									<span id="floatDivMessage"></span>
								</td>
							</tr>
						</table>			
					</td>
				</tr>
			</table>			
		</td>
		<td class="right">	

			<table class="box gray">
				<tr>
					<td class="title" style="cursor:pointer" onClick="toggleDivDisplay('divFormDocumentos');">DOCUMENTACI&Oacute;N</td>
				</tr>
				<tr>
					<td>
						<div id="divFormDocumentos" style="display:<%= displayDivFormDocumentos %>">
							<%@ include file = "include/12-datosDocumentacion.jsp" %>
						</div>
					</td>
				</tr>
			</table>	
			
			<!-- SEGUIMIENTO [INICIO] -->
			<table class="box green">
				<tr>
					<td class="title" style="cursor:pointer" onClick="toggleDivDisplay('divFormMuro');">MI MURO</td>
				</tr>
				<tr>
					<td>
						<div id="divFormMuro" style="display:<%= displayDivFormMuro %>">
							<table class="seguimiento">
								<tr>
									<td colspan="2">
										<%
										formElement.initControl();
										formElement.getControl().setId("mensajeSeguimiento");
										formElement.getControl().setClassName("seguimiento");
										formElement.getControl().setMaxLength("2500");
										%>
										<%= formElement.createTextareaElement() %>
									</td>
								</tr>
								<tr>
									<td class="a">
										<table onClick="upsertSeguimiento('');">
											<tr>
												<td><img src="images/b-post.png"/></td>
											</tr>
										</table>
									</td>
									<td class="b">
										<table onClick="upsertSeguimiento('refresh');">
											<tr>
												<td><img src="images/b-refresh.png"/></td>
											</tr>
										</table>										
									</td>
								</tr>
								<tr>
									<td colspan="2" style="height:30px; color:#0082CA; text-align:left; font-style:italic; padding-left:10px; background-color:#EAE8E8;">Lista de Distribuci&oacute;n:</td>
								</tr>
								<tr>
									<td colspan="2">
										<%
										formElement.initControl();
										formElement.getControl().setId("email_seguimiento");
										formElement.getControl().setClassName("seguimiento-lista");
										formElement.getControl().setMaxLength("300");
										formElement.getControl().setOnBlur("this.value = validaListaCorreos(this.value);");
										%>
										<%= formElement.createTextareaElement() %>
									</td>
								</tr>
							</table>
							<div id="divSeguimiento">
								<%@ include file = "include/datosSeguimiento.jsp" %>
							</div>
						</div>
					</td>
				</tr>
			</table>
			<!-- SEGUIMIENTO [FIN] -->	

			<!-- AUDITORIA -->
			<table class="box pink">
				<tr>
					<td class="title" style="cursor:pointer" onClick="toggleDivDisplay('divFormHistorial');">AUDITOR&Iacute;A DE FASES Y STATUS</td>
				</tr>
				<tr>
					<td>
						<div id="divFormHistorial"  style="display:<%= displayDivFormHistorial %>">
							<jsp:include page="include/datosHistorial.jsp" flush="true" />
						</div>					
					</td>
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

<input type="hidden" id="section" name="section" value="<%= section %>">
</body>
</html>