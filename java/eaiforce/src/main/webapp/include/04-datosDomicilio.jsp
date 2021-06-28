<%
force.eai.mx.util.Cliente cliente03 = ( session.getAttribute("cliente") != null ) ? (force.eai.mx.util.Cliente)session.getAttribute("cliente") : new force.eai.mx.util.Cliente() ;
force.eai.mx.form.FormUtils fUtil03 = new force.eai.mx.form.FormUtils(session);
force.eai.mx.form.FormElements fElement03 = new force.eai.mx.form.FormElements();
fUtil03.setCliente(cliente03);

fElement03.setPreffix("dg_");

force.eai.mx.util.Persona persona03 = new force.eai.mx.util.Persona();

if ( cliente03.getTipo().equals("2") )
	persona03 = cliente03.getApoderado();
else
	persona03 = cliente03.getPersona();
%>

<table>
	<tr>
		<td class="form-label">Calle</td>
		<td class="form-value">
			<%
			fElement03.initControl();
			fElement03.getControl().setId("calle");
			fElement03.getControl().setValue(persona03.getDirecciones("0").getCalle());
			fElement03.getControl().setRequired(true);
			fElement03.getControl().setMaxLength("50");
			%>
			<%= fElement03.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label">N&uacute;mero</td>
		<td class="form-value">
			<%
			fElement03.initControl();
			fElement03.getControl().setId("num_exterior");
			fElement03.getControl().setValue(persona03.getDirecciones("0").getNumeroExterior());
			fElement03.getControl().setRequired(true);
			fElement03.getControl().setMaxLength("5");
			fElement03.getControl().setClassName("digits-05");
			%>
			<%= fElement03.createInputElement() %>
			Ext.
			<%
			fElement03.initControl();
			fElement03.getControl().setId("num_interior");
			fElement03.getControl().setValue(persona03.getDirecciones("0").getNumeroInterior());
			fElement03.getControl().setMaxLength("5");
			fElement03.getControl().setClassName("digits-05");
			%>
			<%= fElement03.createInputElement() %>
			Int.
		</td>            
	</tr>
	<tr>
		<td class="form-label">Codigo Postal</td>
		<td class="form-value">
			<%
			fElement03.initControl();
			fElement03.getControl().setId("codigo");
			fElement03.getControl().setValue(persona03.getDirecciones("0").getCodigoPostal());
			fElement03.getControl().setRequired(true);
			fElement03.getControl().setMaxLength("5");
			fElement03.getControl().setClassName("digits-05");
			%>
			<%= fElement03.createInputElement() %>
			
		</td>            
	</tr>
	<tr>
		<td class="form-label"></td>
		<td class="form-value"><input type="button" id="buscar" value="Buscar" onClick="buscarCP();"></td>
	</tr>
	<tr>
		<td class="form-label">Colonia</td>
		<td class="form-value" id="<%= fElement03.getPreffix() + "colonia_td" %>">
			<%
			fElement03.initControl();
			fElement03.getControl().setId("colonia");
			fElement03.getControl().setValue(persona03.getDirecciones("0").getColonia());
			fElement03.getControl().setRequired(true);
			fElement03.getControl().setMaxLength("50");
			%>
			<%= fElement03.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label">Delegaci&oacute;n</td>
		<td class="form-value" id="<%= fElement03.getPreffix() + "delegacion_td" %>">
			<%
			fElement03.initControl();
			fElement03.getControl().setId("delegacion");
			fElement03.getControl().setValue(persona03.getDirecciones("0").getDelegacion());
			fElement03.getControl().setRequired(true);
			fElement03.getControl().setMaxLength("50");
			%>
			<%= fElement03.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label">Ciudad</td>
		<td class="form-value" id="<%= fElement03.getPreffix() + "ciudad_td" %>">
			<%
			fElement03.initControl();
			fElement03.getControl().setId("ciudad");
			fElement03.getControl().setValue(persona03.getDirecciones("0").getCiudad());
			fElement03.getControl().setRequired(true);
			fElement03.getControl().setMaxLength("50");
			%>
			<%= fElement03.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label">Estado</td>
		<td class="form-value">
			<%
			fElement03.initControl();
			fElement03.getControl().setId("estado");
			fElement03.getControl().setValue(persona03.getDirecciones("0").getEstado());
			fElement03.getControl().setRequired(true);
			fElement03.getControl().setArrayOptions(fUtil03.getFormCatalogue("estados"));
			%>
			<%= fElement03.createSelectElement() %>
		</td>
	</tr>
	<%
	// TIPO DE DOMICILIO
	fElement03.initControl();
	fElement03.getControl().setId("tipo_domicilio");
	fElement03.getControl().setValue( persona03.getDirecciones("0").getTipoDomicilio() );
	fElement03.getControl().setArrayOptions(fUtil03.getFormCatalogue("tipoDomicilio"));
	fElement03.getControl().setMaxLength("20");
	%>
	<%= fElement03.createSelectWithOtherElement("Tipo de Domicilio") %>
	<tr>
		<td class="form-label">Monto o Renta</td>
		<td class="form-value">
			<%
			fElement03.initControl();
			fElement03.getControl().setType("currency");
			fElement03.getControl().setId("monto");
			fElement03.getControl().setValue(new force.eai.mx.tools.FormatValues().formatMoney(persona03.getDirecciones("0").getMontoRenta()));
			fElement03.getControl().setMaxLength("13");
			fElement03.getControl().setPrecision("2");
			%>
			<%= fElement03.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Tiempo de Residencia</td>
		<td class="form-value">
			<%
			fElement03.initControl();
			fElement03.getControl().setType("number");
			fElement03.getControl().setId("anios");
			fElement03.getControl().setValue(persona03.getDirecciones("0").getAntiguedadAnios());
			fElement03.getControl().setClassName("digits-03");
			fElement03.getControl().setMaxLength("2");
			%>
			<%= fElement03.createInputElement() %>
			A&ntilde;os
			<%
			fElement03.initControl();
			fElement03.getControl().setType("number");
			fElement03.getControl().setId("meses");
			fElement03.getControl().setValue(persona03.getDirecciones("0").getAntiguedadMeses());
			fElement03.getControl().setClassName("digits-03");
			fElement03.getControl().setMaxLength("2");
			%>
			<%= fElement03.createInputElement() %>
			Meses
		</td>   
	</tr> 
</table>

<table style="margin-top:10px; margin-bottom:20px;">
	<tr>
		<td colspan="2" class="form-sub-section">TELEFONOS</td>
	</tr>
	<tr>
		<td colspan="2" style="height:10px"></td>
	</tr>
	<tr>
		<td class="form-label">Casa</td>
		<td class="form-value">
			<%
			fElement03.initControl();
			fElement03.getControl().setType("phone");
			fElement03.getControl().setId("casa_lada");
			fElement03.getControl().setValue(persona03.getTelefonos("0").getLada());
			fElement03.getControl().setRequired(true);
			fElement03.getControl().setClassName("digits-03");
			fElement03.getControl().setMaxLength("3");
			%>
			<%= fElement03.createInputElement() %>
			Lada
			<%
			fElement03.initControl();
			fElement03.getControl().setType("phone");
			fElement03.getControl().setId("casa_numero");
			fElement03.getControl().setValue(persona03.getTelefonos("0").getNumero());
			fElement03.getControl().setRequired(true);
			fElement03.getControl().setClassName("digits-08");
			fElement03.getControl().setMaxLength("8");
			%>
			<%= fElement03.createInputElement() %>
			N&uacute;mero
		</td>
	</tr>
	<tr>
		<td class="form-label">Oficina</td>
		<td class="form-value">
			<%
			fElement03.initControl();
			fElement03.getControl().setType("phone");
			fElement03.getControl().setId("oficina_lada");
			fElement03.getControl().setValue(persona03.getTelefonos("1").getLada());
			fElement03.getControl().setRequired(true);
			fElement03.getControl().setClassName("digits-03");
			fElement03.getControl().setMaxLength("3");
			%>
			<%= fElement03.createInputElement() %>
			Lada
			<%
			fElement03.initControl();
			fElement03.getControl().setType("phone");
			fElement03.getControl().setId("oficina_numero");
			fElement03.getControl().setValue(persona03.getTelefonos("1").getNumero());
			fElement03.getControl().setRequired(true);
			fElement03.getControl().setClassName("digits-08");
			fElement03.getControl().setMaxLength("8");
			%>
			<%= fElement03.createInputElement() %>
			N&uacute;mero
		</td>
	</tr>
	<tr>
		<td class="form-label">Celular</td>
		<td class="form-value">
			<%
			fElement03.initControl();
			fElement03.getControl().setType("phone");
			fElement03.getControl().setId("cel_lada");
			fElement03.getControl().setValue(persona03.getTelefonos("2").getLada());
			fElement03.getControl().setRequired(true);
			fElement03.getControl().setClassName("digits-03");
			fElement03.getControl().setMaxLength("3");
			%>
			<%= fElement03.createInputElement() %>
			Lada
			<%
			fElement03.initControl();
			fElement03.getControl().setType("phone");
			fElement03.getControl().setId("cel_numero");
			fElement03.getControl().setValue(persona03.getTelefonos("2").getNumero());
			fElement03.getControl().setRequired(true);
			fElement03.getControl().setClassName("digits-08");
			fElement03.getControl().setMaxLength("8");
			%>
			<%= fElement03.createInputElement() %>
			N&uacute;mero
		</td>
	</tr>
	<tr>
		<td class="form-label">Fax</td>
		<td class="form-value">
			<%
			fElement03.initControl();
			fElement03.getControl().setType("phone");
			fElement03.getControl().setId("fax_lada");
			fElement03.getControl().setValue(persona03.getTelefonos("3").getLada());
			fElement03.getControl().setClassName("digits-03");
			fElement03.getControl().setMaxLength("3");
			%>
			<%= fElement03.createInputElement() %>
			<span class="required" style="visibility:hidden">*</span>		
			Lada
			<%
			fElement03.initControl();
			fElement03.getControl().setType("phone");
			fElement03.getControl().setId("fax_numero");
			fElement03.getControl().setValue(persona03.getTelefonos("3").getNumero());
			fElement03.getControl().setClassName("digits-08");
			fElement03.getControl().setMaxLength("8");
			%>
			<%= fElement03.createInputElement() %>				
			<span class="required" style="visibility:hidden">*</span>		
			N&uacute;mero
		</td>
	</tr>
</table>