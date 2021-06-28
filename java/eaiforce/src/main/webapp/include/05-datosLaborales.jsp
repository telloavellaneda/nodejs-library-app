<%
force.eai.mx.util.Cliente cliente04 = ( session.getAttribute("cliente") != null ) ? (force.eai.mx.util.Cliente)session.getAttribute("cliente") : new force.eai.mx.util.Cliente() ;
force.eai.mx.form.FormUtils fUtil04 = new force.eai.mx.form.FormUtils(session);
force.eai.mx.form.FormElements fElement04 = new force.eai.mx.form.FormElements();
fUtil04.setCliente(cliente04);

fElement04.setPreffix("dl_");

force.eai.mx.util.Persona persona04 = new force.eai.mx.util.Persona();

if ( cliente04.getTipo().equals("2") )
	persona04 = cliente04.getApoderado();
else
	persona04 = cliente04.getPersona();
%>

<table style="margin-bottom:20px">
	<tr>
		<td class="form-label">Nombre de la Empresa</td>
		<td class="form-value">
			<%
			fElement04.initControl();
			fElement04.getControl().setId("nombre_empresa");
			fElement04.getControl().setValue(persona04.getEmpleos(persona04.getIdPersona()).getNombreEmpresa());
			fElement04.getControl().setRequired(true);
			fElement04.getControl().setClassName("razonSocial");
			fElement04.getControl().setMaxLength("200");
			fElement04.getControl().setOnBlur("validaRazonSocial(this);");
			%>
			<%= fElement04.createTextareaElement() %>
		</td>                                       
	</tr>
	<%
	// SECTOR LABORAL
	fElement04.initControl();
	fElement04.getControl().setId("sector_empresa");
	fElement04.getControl().setValue( persona04.getEmpleos(persona04.getIdPersona()).getSector() );
	fElement04.getControl().setArrayOptions(fUtil04.getFormCatalogue("sectorEmpresa"));
	fElement04.getControl().setMaxLength("40");
	%>
	<%= fElement04.createSelectWithOtherElement("Sector Laboral") %>
	<%
	// GIRO
	fElement04.initControl();
	fElement04.getControl().setId("giro_empresa");
	fElement04.getControl().setValue( persona04.getEmpleos(persona04.getIdPersona()).getGiro() );
	fElement04.getControl().setArrayOptions(fUtil04.getFormCatalogue("giroEmpresa"));
	fElement04.getControl().setMaxLength("40");
	%>
	<%= fElement04.createSelectWithOtherElement("Giro") %>
	<tr>
		<td class="form-label">Puesto</td>
		<td class="form-value">
			<%
			fElement04.initControl();
			fElement04.getControl().setId("puesto");
			fElement04.getControl().setValue(persona04.getEmpleos(persona04.getIdPersona()).getPuesto());
			fElement04.getControl().setMaxLength("20");
			%>
			<%= fElement04.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Profesi&oacute;n</td>
		<td class="form-value">
			<%
			fElement04.initControl();
			fElement04.getControl().setId("profesion");
			fElement04.getControl().setValue(persona04.getEmpleos(persona04.getIdPersona()).getProfesion());
			fElement04.getControl().setMaxLength("20");
			%>
			<%= fElement04.createInputElement() %>
		</td>
	</tr>
	<%
	// TIPO EMPLEO
	fElement04.initControl();
	fElement04.getControl().setId("tipo_empleo");
	fElement04.getControl().setValue( persona04.getEmpleos(persona04.getIdPersona()).getTipoEmpleo() );
	fElement04.getControl().setArrayOptions(fUtil04.getFormCatalogue("tipoEmpleo"));
	fElement04.getControl().setMaxLength("40");
	%>
	<%= fElement04.createSelectWithOtherElement("Tipo Empleo") %>
	<tr>
		<td class="form-label">Tipo Contrato</td>
		<td class="form-value">
			<%
			fElement04.initControl();
			fElement04.getControl().setId("tipo_contrato");
			fElement04.getControl().setValue(persona04.getEmpleos(persona04.getIdPersona()).getTipoContrato());
			fElement04.getControl().setArrayOptions(fUtil04.getFormCatalogue("tipoContrato"));
			%>
			<%= fElement04.createSelectElement() %>		
		</td>
	</tr>
	<tr>
		<td class="form-label">Antig&uuml;edad</td>
		<td class="form-value">
			<%
			fElement04.initControl();
			fElement04.getControl().setType("number");
			fElement04.getControl().setId("anios");
			fElement04.getControl().setValue(persona04.getEmpleos(persona04.getIdPersona()).getAntiguedadAnios());
			fElement04.getControl().setClassName("digits-03");
			fElement04.getControl().setMaxLength("2");
			%>
			<%= fElement04.createInputElement() %>
			A&ntilde;os
			<%
			fElement04.initControl();
			fElement04.getControl().setType("number");
			fElement04.getControl().setId("meses");
			fElement04.getControl().setValue(persona04.getEmpleos(persona04.getIdPersona()).getAntiguedadMeses());
			fElement04.getControl().setClassName("digits-03");
			fElement04.getControl().setMaxLength("2");
			%>
			<%= fElement04.createInputElement() %>
			Meses
		</td>
	</tr>
	<tr>
		<td colspan="2" style="height:15px"></td>
	</tr>	
	<tr>
		<td class="form-label">Calle</td>
		<td class="form-value">
			<%
			fElement04.initControl();
			fElement04.getControl().setId("calle");
			fElement04.getControl().setValue(persona04.getDirecciones("1").getCalle());
			fElement04.getControl().setMaxLength("50");
			%>
			<%= fElement04.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label">N&uacute;mero</td>
		<td class="form-value">
			<%
			fElement04.initControl();
			fElement04.getControl().setId("num_exterior");
			fElement04.getControl().setValue(persona04.getDirecciones("1").getNumeroExterior());
			fElement04.getControl().setMaxLength("5");
			fElement04.getControl().setClassName("digits-05");
			%>
			<%= fElement04.createInputElement() %>
			Ext.
			<%
			fElement04.initControl();
			fElement04.getControl().setId("num_interior");
			fElement04.getControl().setValue(persona04.getDirecciones("1").getNumeroInterior());
			fElement04.getControl().setMaxLength("10");
			fElement04.getControl().setClassName("digits-05");
			%>
			<%= fElement04.createInputElement() %>
			Int.
		</td>            
	</tr>
	<tr>
		<td class="form-label">Codigo Postal</td>
		<td class="form-value">
			<%
			fElement04.initControl();
			fElement04.getControl().setId("codigo");
			fElement04.getControl().setValue(persona04.getDirecciones("1").getCodigoPostal());
			fElement04.getControl().setMaxLength("5");
			fElement04.getControl().setClassName("digits-05");
			%>
			<%= fElement04.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label"></td>
		<td class="form-value"><input type="button" id="buscar" value="Buscar" onClick="buscarCP();"></td>
	</tr>
	<tr>
		<td class="form-label">Colonia</td>
		<td class="form-value" id="<%= fElement04.getPreffix() + "colonia_td" %>">
			<%
			fElement04.initControl();
			fElement04.getControl().setId("colonia");
			fElement04.getControl().setValue(persona04.getDirecciones("1").getColonia());
			fElement04.getControl().setMaxLength("50");
			%>
			<%= fElement04.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label">Delegaci&oacute;n</td>
		<td class="form-value" id="<%= fElement04.getPreffix() + "delegacion_td" %>">
			<%
			fElement04.initControl();
			fElement04.getControl().setId("delegacion");
			fElement04.getControl().setValue(persona04.getDirecciones("1").getDelegacion());
			fElement04.getControl().setMaxLength("50");
			%>
			<%= fElement04.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label">Ciudad</td>
		<td class="form-value" id="<%= fElement04.getPreffix() + "ciudad_td" %>">
			<%
			fElement04.initControl();
			fElement04.getControl().setId("ciudad");
			fElement04.getControl().setValue(persona04.getDirecciones("1").getCiudad());
			fElement04.getControl().setMaxLength("50");
			%>
			<%= fElement04.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label">Estado</td>
		<td class="form-value">
			<%
			fElement04.initControl();
			fElement04.getControl().setId("estado");
			fElement04.getControl().setValue(persona04.getDirecciones("1").getEstado());
			fElement04.getControl().setArrayOptions(fUtil04.getFormCatalogue("estados"));
			%>
			<%= fElement04.createSelectElement() %>
		</td>
	</tr> 
</table>
