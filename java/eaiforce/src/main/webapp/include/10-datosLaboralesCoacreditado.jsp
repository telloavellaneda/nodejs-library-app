<%
force.eai.mx.util.Cliente cliente08 = ( session.getAttribute("cliente") != null ) ? (force.eai.mx.util.Cliente)session.getAttribute("cliente") : new force.eai.mx.util.Cliente() ;
force.eai.mx.form.FormUtils fUtil08 = new force.eai.mx.form.FormUtils(session);
force.eai.mx.form.FormElements fElement08 = new force.eai.mx.form.FormElements();
fUtil08.setCliente(cliente08);

fElement08.setPreffix("coa_dl_");

force.eai.mx.util.Persona persona08 = cliente08.getCoacreditado("0");
%>

<table style="margin-bottom:20px;">
	<tr>
		<td class="form-label">Nombre de la Empresa</td>
		<td class="form-value">
			<%
			fElement08.initControl();
			fElement08.getControl().setId("nombre_empresa");
			fElement08.getControl().setValue(persona08.getEmpleos(persona08.getIdPersona()).getNombreEmpresa());
			fElement08.getControl().setRequired(true);
			fElement08.getControl().setClassName("razonSocial");
			fElement08.getControl().setMaxLength("200");
			fElement08.getControl().setOnBlur("validaRazonSocial(this);");
			%>
			<%= fElement08.createTextareaElement() %>
		</td>                                       
	</tr>
	<%
	// SECTOR LABORAL
	fElement08.initControl();
	fElement08.getControl().setId("sector_empresa");
	fElement08.getControl().setValue( persona08.getEmpleos(persona08.getIdPersona()).getSector() );
	fElement08.getControl().setArrayOptions(fUtil08.getFormCatalogue("sectorEmpresa"));
	fElement08.getControl().setMaxLength("40");
	%>
	<%= fElement08.createSelectWithOtherElement("Sector Laboral") %>
	<%
	// GIRO
	fElement08.initControl();
	fElement08.getControl().setId("giro_empresa");
	fElement08.getControl().setValue( persona08.getEmpleos(persona08.getIdPersona()).getGiro() );
	fElement08.getControl().setArrayOptions(fUtil08.getFormCatalogue("giroEmpresa"));
	fElement08.getControl().setMaxLength("40");
	%>
	<%= fElement08.createSelectWithOtherElement("Giro") %>
	<tr>
		<td class="form-label">Puesto</td>
		<td class="form-value">
			<%
			fElement08.initControl();
			fElement08.getControl().setId("puesto");
			fElement08.getControl().setValue(persona08.getEmpleos(persona08.getIdPersona()).getPuesto());
			fElement08.getControl().setMaxLength("20");
			%>
			<%= fElement08.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Profesi&oacute;n</td>
		<td class="form-value">
			<%
			fElement08.initControl();
			fElement08.getControl().setId("profesion");
			fElement08.getControl().setValue(persona08.getEmpleos(persona08.getIdPersona()).getProfesion());
			fElement08.getControl().setMaxLength("20");
			%>
			<%= fElement08.createInputElement() %>
		</td>
	</tr>
	<%
	// TIPO EMPLEO
	fElement08.initControl();
	fElement08.getControl().setId("tipo_empleo");
	fElement08.getControl().setValue( persona08.getEmpleos(persona08.getIdPersona()).getTipoEmpleo() );
	fElement08.getControl().setArrayOptions(fUtil08.getFormCatalogue("tipoEmpleo"));
	fElement08.getControl().setMaxLength("40");
	%>
	<%= fElement08.createSelectWithOtherElement("Tipo Empleo") %>
	<tr>
		<td class="form-label">Tipo Contrato</td>
		<td class="form-value">
			<%
			fElement08.initControl();
			fElement08.getControl().setId("tipo_contrato");
			fElement08.getControl().setValue(persona08.getEmpleos(persona08.getIdPersona()).getTipoContrato());
			fElement08.getControl().setArrayOptions(fUtil08.getFormCatalogue("tipoContrato"));
			%>
			<%= fElement08.createSelectElement() %>		
		</td>
	</tr>
	<tr>
		<td class="form-label">Antig&uuml;edad</td>
		<td class="form-value">
			<%
			fElement08.initControl();
			fElement08.getControl().setType("number");
			fElement08.getControl().setId("anios");
			fElement08.getControl().setValue(persona08.getEmpleos(persona08.getIdPersona()).getAntiguedadAnios());
			fElement08.getControl().setClassName("digits-03");
			fElement08.getControl().setMaxLength("2");
			%>
			<%= fElement08.createInputElement() %>
			A&ntilde;os
			<%
			fElement08.initControl();
			fElement08.getControl().setType("number");
			fElement08.getControl().setId("meses");
			fElement08.getControl().setValue(persona08.getEmpleos(persona08.getIdPersona()).getAntiguedadMeses());
			fElement08.getControl().setClassName("digits-03");
			fElement08.getControl().setMaxLength("2");
			%>
			<%= fElement08.createInputElement() %>
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
			fElement08.initControl();
			fElement08.getControl().setId("calle");
			fElement08.getControl().setValue(persona08.getDirecciones("1").getCalle());
			fElement08.getControl().setMaxLength("50");
			%>
			<%= fElement08.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label">N&uacute;mero</td>
		<td class="form-value">
			<%
			fElement08.initControl();
			fElement08.getControl().setId("num_exterior");
			fElement08.getControl().setValue(persona08.getDirecciones("1").getNumeroExterior());
			fElement08.getControl().setMaxLength("10");
			fElement08.getControl().setClassName("digits-08");
			%>
			<%= fElement08.createInputElement() %>
			Ext.
			<%
			fElement08.initControl();
			fElement08.getControl().setId("num_interior");
			fElement08.getControl().setValue(persona08.getDirecciones("1").getNumeroInterior());
			fElement08.getControl().setMaxLength("10");
			fElement08.getControl().setClassName("digits-08");
			%>
			<%= fElement08.createInputElement() %>
			Int.
		</td>            
	</tr>
	<tr>
		<td class="form-label">Codigo Postal</td>
		<td class="form-value">
			<%
			fElement08.initControl();
			fElement08.getControl().setId("codigo");
			fElement08.getControl().setValue(persona08.getDirecciones("1").getCodigoPostal());
			fElement08.getControl().setMaxLength("5");
			fElement08.getControl().setClassName("digits-05");
			%>
			<%= fElement08.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label"></td>
		<td class="form-value"><input type="button" id="buscar" value="Buscar" onClick="buscarCP();"></td>
	</tr>
	<tr>
		<td class="form-label">Colonia</td>
		<td class="form-value" id="<%= fElement08.getPreffix() + "colonia_td" %>">
			<%
			fElement08.initControl();
			fElement08.getControl().setId("colonia");
			fElement08.getControl().setValue(persona08.getDirecciones("1").getColonia());
			fElement08.getControl().setMaxLength("50");
			%>
			<%= fElement08.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label">Delegaci&oacute;n</td>
		<td class="form-value" id="<%= fElement08.getPreffix() + "delegacion_td" %>">
			<%
			fElement08.initControl();
			fElement08.getControl().setId("delegacion");
			fElement08.getControl().setValue(persona08.getDirecciones("1").getDelegacion());
			fElement08.getControl().setMaxLength("50");
			%>
			<%= fElement08.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label">Ciudad</td>
		<td class="form-value" id="<%= fElement08.getPreffix() + "ciudad_td" %>">
			<%
			fElement08.initControl();
			fElement08.getControl().setId("ciudad");
			fElement08.getControl().setValue(persona08.getDirecciones("1").getCiudad());
			fElement08.getControl().setMaxLength("50");
			%>
			<%= fElement08.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label">Estado</td>
		<td class="form-value">
			<%
			fElement08.initControl();
			fElement08.getControl().setId("estado");
			fElement08.getControl().setValue(persona08.getDirecciones("1").getEstado());
			fElement08.getControl().setArrayOptions(fUtil08.getFormCatalogue("estados"));
			%>
			<%= fElement08.createSelectElement() %>
		</td>
	</tr> 
</table>
