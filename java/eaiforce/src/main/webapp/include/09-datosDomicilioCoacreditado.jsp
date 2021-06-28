<%
force.eai.mx.util.Cliente cliente07 = ( session.getAttribute("cliente") != null ) ? (force.eai.mx.util.Cliente)session.getAttribute("cliente") : new force.eai.mx.util.Cliente() ;
force.eai.mx.form.FormUtils fUtil07 = new force.eai.mx.form.FormUtils(session);
force.eai.mx.form.FormElements fElement07 = new force.eai.mx.form.FormElements();
fUtil07.setCliente(cliente07);

fElement07.setPreffix("coa_dom_");
%>

<table>
	<tr>
		<td class="form-label">Calle</td>
		<td class="form-value">
			<%
			fElement07.initControl();
			fElement07.getControl().setId("calle");
			fElement07.getControl().setValue(cliente07.getCoacreditado("0").getDirecciones("0").getCalle());
			fElement07.getControl().setMaxLength("50");
			%>
			<%= fElement07.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label">N&uacute;mero</td>
		<td class="form-value">
			<%
			fElement07.initControl();
			fElement07.getControl().setId("num_exterior");
			fElement07.getControl().setValue(cliente07.getCoacreditado("0").getDirecciones("0").getNumeroExterior());
			fElement07.getControl().setMaxLength("5");
			fElement07.getControl().setClassName("digits-05");
			%>
			<%= fElement07.createInputElement() %>
			Ext.
			<%
			fElement07.initControl();
			fElement07.getControl().setId("num_interior");
			fElement07.getControl().setValue(cliente07.getCoacreditado("0").getDirecciones("0").getNumeroInterior());
			fElement07.getControl().setMaxLength("5");
			fElement07.getControl().setClassName("digits-05");
			%>
			<%= fElement07.createInputElement() %>
			Int.
		</td>            
	</tr>
	<tr>
		<td class="form-label">Codigo Postal</td>
		<td class="form-value">
			<%
			fElement07.initControl();
			fElement07.getControl().setId("codigo");
			fElement07.getControl().setValue(cliente07.getCoacreditado("0").getDirecciones("0").getCodigoPostal());
			fElement07.getControl().setMaxLength("5");
			fElement07.getControl().setClassName("digits-05");
			%>
			<%= fElement07.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label"></td>
		<td class="form-value"><input type="button" id="buscar" value="Buscar" onClick="buscarCP();"></td>
	</tr>
	<tr>
		<td class="form-label">Colonia</td>
		<td class="form-value" id="<%= fElement07.getPreffix() + "colonia_td" %>">
			<%
			fElement07.initControl();
			fElement07.getControl().setId("colonia");
			fElement07.getControl().setValue(cliente07.getCoacreditado("0").getDirecciones("0").getColonia());
			fElement07.getControl().setMaxLength("50");
			%>
			<%= fElement07.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label">Delegaci&oacute;n</td>
		<td class="form-value" id="<%= fElement07.getPreffix() + "delegacion_td" %>">
			<%
			fElement07.initControl();
			fElement07.getControl().setId("delegacion");
			fElement07.getControl().setValue(cliente07.getCoacreditado("0").getDirecciones("0").getDelegacion());
			fElement07.getControl().setMaxLength("50");
			%>
			<%= fElement07.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label">Ciudad</td>
		<td class="form-value" id="<%= fElement07.getPreffix() + "ciudad_td" %>">
			<%
			fElement07.initControl();
			fElement07.getControl().setId("ciudad");
			fElement07.getControl().setValue(cliente07.getCoacreditado("0").getDirecciones("0").getCiudad());
			fElement07.getControl().setMaxLength("50");
			%>
			<%= fElement07.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label">Estado</td>
		<td class="form-value">
			<%
			fElement07.initControl();
			fElement07.getControl().setId("estado");
			fElement07.getControl().setValue(cliente07.getCoacreditado("0").getDirecciones("0").getEstado());
			fElement07.getControl().setArrayOptions(fUtil07.getFormCatalogue("estados"));
			%>
			<%= fElement07.createSelectElement() %>
		</td>
	</tr>
	<%
	// TIPO DE DOMICILIO
	fElement07.initControl();
	fElement07.getControl().setId("tipo_domicilio");
	fElement07.getControl().setValue( cliente07.getCoacreditado("0").getDirecciones("0").getTipoDomicilio() );
	fElement07.getControl().setArrayOptions(fUtil07.getFormCatalogue("tipoDomicilio"));
	fElement07.getControl().setMaxLength("20");
	%>
	<%= fElement07.createSelectWithOtherElement("Tipo de Domicilio") %>
	<tr>
		<td class="form-label">Monto o Renta</td>
		<td class="form-value">
			<%
			fElement07.initControl();
			fElement07.getControl().setType("currency");
			fElement07.getControl().setId("monto");
			fElement07.getControl().setValue(new force.eai.mx.tools.FormatValues().formatMoney( cliente07.getCoacreditado("0").getDirecciones("0").getMontoRenta() ));
			fElement07.getControl().setMaxLength("13");
			fElement07.getControl().setPrecision("2");
			%>
			<%= fElement07.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Tiempo de Residencia</td>
		<td class="form-value">
			<%
			fElement07.initControl();
			fElement07.getControl().setType("number");
			fElement07.getControl().setId("anios");
			fElement07.getControl().setValue(cliente07.getCoacreditado("0").getDirecciones("0").getAntiguedadAnios());
			fElement07.getControl().setClassName("digits-03");
			fElement07.getControl().setMaxLength("2");
			%>
			<%= fElement07.createInputElement() %>
			A&ntilde;os
			<%
			fElement07.initControl();
			fElement07.getControl().setType("number");
			fElement07.getControl().setId("meses");
			fElement07.getControl().setValue(cliente07.getCoacreditado("0").getDirecciones("0").getAntiguedadMeses());
			fElement07.getControl().setClassName("digits-03");
			fElement07.getControl().setMaxLength("2");
			%>
			<%= fElement07.createInputElement() %>
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
			fElement07.initControl();
			fElement07.getControl().setType("phone");
			fElement07.getControl().setId("casa_lada");
			fElement07.getControl().setValue(cliente07.getCoacreditado("0").getTelefonos("0").getLada());
			fElement07.getControl().setClassName("digits-03");
			fElement07.getControl().setMaxLength("3");
			%>
			<%= fElement07.createInputElement() %>
			Lada
			<%
			fElement07.initControl();
			fElement07.getControl().setType("phone");
			fElement07.getControl().setId("casa_numero");
			fElement07.getControl().setValue(cliente07.getCoacreditado("0").getTelefonos("0").getNumero());
			fElement07.getControl().setClassName("digits-08");
			fElement07.getControl().setMaxLength("8");
			%>
			<%= fElement07.createInputElement() %>
			N&uacute;mero
		</td>
	</tr>
	<tr>
		<td class="form-label">Oficina</td>
		<td class="form-value">
			<%
			fElement07.initControl();
			fElement07.getControl().setType("phone");
			fElement07.getControl().setId("oficina_lada");
			fElement07.getControl().setValue(cliente07.getCoacreditado("0").getTelefonos("1").getLada());
			fElement07.getControl().setClassName("digits-03");
			fElement07.getControl().setMaxLength("3");
			%>
			<%= fElement07.createInputElement() %>
			Lada
			<%
			fElement07.initControl();
			fElement07.getControl().setType("phone");
			fElement07.getControl().setId("oficina_numero");
			fElement07.getControl().setValue(cliente07.getCoacreditado("0").getTelefonos("1").getNumero());
			fElement07.getControl().setClassName("digits-08");
			fElement07.getControl().setMaxLength("8");
			%>
			<%= fElement07.createInputElement() %>
			N&uacute;mero
		</td>
	</tr>
	<tr>
		<td class="form-label">Celular</td>
		<td class="form-value">
			<%
			fElement07.initControl();
			fElement07.getControl().setType("phone");
			fElement07.getControl().setId("cel_lada");
			fElement07.getControl().setValue(cliente07.getCoacreditado("0").getTelefonos("2").getLada());
			fElement07.getControl().setClassName("digits-03");
			fElement07.getControl().setMaxLength("3");
			%>
			<%= fElement07.createInputElement() %>
			Lada
			<%
			fElement07.initControl();
			fElement07.getControl().setType("phone");
			fElement07.getControl().setId("cel_numero");
			fElement07.getControl().setValue(cliente07.getCoacreditado("0").getTelefonos("2").getNumero());
			fElement07.getControl().setClassName("digits-08");
			fElement07.getControl().setMaxLength("8");
			%>
			<%= fElement07.createInputElement() %>
			N&uacute;mero
		</td>
	</tr>
	<tr>
		<td class="form-label">Fax</td>
		<td class="form-value">
			<%
			fElement07.initControl();
			fElement07.getControl().setType("phone");
			fElement07.getControl().setId("fax_lada");
			fElement07.getControl().setValue(cliente07.getCoacreditado("0").getTelefonos("3").getLada());
			fElement07.getControl().setClassName("digits-03");
			fElement07.getControl().setMaxLength("3");
			%>
			<%= fElement07.createInputElement() %>
			Lada
			<%
			fElement07.initControl();
			fElement07.getControl().setType("phone");
			fElement07.getControl().setId("fax_numero");
			fElement07.getControl().setValue(cliente07.getCoacreditado("0").getTelefonos("3").getNumero());
			fElement07.getControl().setClassName("digits-08");
			fElement07.getControl().setMaxLength("8");
			%>
			<%= fElement07.createInputElement() %>
			N&uacute;mero
		</td>
	</tr>
</table>