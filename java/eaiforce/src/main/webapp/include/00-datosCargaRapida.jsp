<%
force.eai.mx.util.Cliente cliente = ( session.getAttribute("cliente") != null ) ? (force.eai.mx.util.Cliente)session.getAttribute("cliente") : new force.eai.mx.util.Cliente();
force.eai.mx.util.Credito credito = cliente.getCreditos("0");
force.eai.mx.form.FormUtils fUtil00 = new force.eai.mx.form.FormUtils(session);
force.eai.mx.form.FormElements fElement00 = new force.eai.mx.form.FormElements();
fUtil00.setCliente(cliente);

fElement00.setPreffix("pre_");

force.eai.mx.util.Persona persona = new force.eai.mx.util.Persona();
force.eai.mx.util.Persona personaMoral = new force.eai.mx.util.Persona();

if ( cliente.getTipo().equals("2") ) {
	personaMoral = cliente.getPersona();
	persona = cliente.getApoderado();
} else {
	persona = cliente.getPersona();
}
%>

<table>
	<tr>
		<td class="form-label">Banco</td>
		<td class="form-value">
			<%
			fElement00.initControl();
			fElement00.getControl().setId("id_banco");
			fElement00.getControl().setValue(credito.getIdBanco());
			fElement00.getControl().setRequired(true);
			fElement00.getControl().setIterator(fUtil00.getCatalogue("filterCatalogues", "bancosForm"));
			if ( !cliente.getForceId().equals("") ) fElement00.getControl().setInitValue(false);
			%>
			<%= fElement00.createSelectElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label">Monto Aproximado</td>
		<td class="form-value">
			<%
			fElement00.initControl();
			fElement00.getControl().setType("currency");
			fElement00.getControl().setId("importe_solic");
			fElement00.getControl().setValue(new force.eai.mx.tools.FormatValues().formatMoney(credito.getImporteSolicitado()));
			fElement00.getControl().setRequired(true);
			fElement00.getControl().setMaxLength("13");
			fElement00.getControl().setPrecision("2");
			%>
			<%= fElement00.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="height:15px"></td>
	</tr>
	<tr>
		<td class="form-label">Tipo</td>
		<td class="form-value">
			<%
			fElement00.initControl();
			fElement00.getControl().setId("tipo");
			fElement00.getControl().setValue(cliente.getTipo());
			fElement00.getControl().setRequired(true);
			fElement00.getControl().setInitValue(false);
			fElement00.getControl().setArrayOptions(fUtil00.getFormCatalogue("tipo"));
			fElement00.getControl().setOnChange("validaTipo(this);");
			%>
			<%= fElement00.createSelectElement() %>
		</td>
	</tr>
</table>

<table id="<%= fElement00.getPreffix() %>moral_datos" <%= fUtil00.styleDisplayTipoMoral() %>>
	<tr>
		<td class="form-label">Raz&oacute;n Social</td>
		<td class="form-value">
			<%
			fElement00.initControl();
			fElement00.getControl().setId("moral_nombre");
			fElement00.getControl().setValue(personaMoral.getNombre());
			fElement00.getControl().setRequired(true);
			fElement00.getControl().setClassName("razonSocial");
			fElement00.getControl().setMaxLength("200");
			fElement00.getControl().setOnBlur("validaRazonSocial(this);");
			%>
			<%= fElement00.createTextareaElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Fecha</td>
		<td class="form-value">
			<%
			fElement00.initControl();
			fElement00.getControl().setType("date");
			fElement00.getControl().setId("moral_fecha");
			fElement00.getControl().setValue(personaMoral.getFecha());
			fElement00.getControl().setRequired(true);
			fElement00.getControl().setMaxLength("10");
			fElement00.getControl().setClassName("fecha");
			fElement00.getControl().setOnBlur("calcularEdad(this);");
			%>
			<%= fElement00.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Antig&uuml;edad</td>
		<td class="form-value">
			<%
			fElement00.initControl();
			fElement00.getControl().setId("moral_anios");
			fElement00.getControl().setValue(personaMoral.getAnios());
			fElement00.getControl().setRequired(true);
			fElement00.getControl().setReadOnly(true);
			fElement00.getControl().setClassName("digits-02");
			fElement00.getControl().setMaxLength("2");
			%>
			<%= fElement00.createInputElement() %>
			A&ntilde;os
			<%
			fElement00.initControl();
			fElement00.getControl().setId("moral_meses");
			fElement00.getControl().setValue(personaMoral.getMeses());
			fElement00.getControl().setRequired(true);
			fElement00.getControl().setReadOnly(true);
			fElement00.getControl().setClassName("digits-02");
			fElement00.getControl().setMaxLength("2");
			%>
			<%= fElement00.createInputElement() %>
			Meses
		</td>
	</tr>
	<tr>
		<td class="form-label"></td>
		<td class="form-value">
			<input type="button" class="calcular" id="buscar" value="Calcular RFC" onClick="calcularRfcCurp(this, 'moral', 'apellido_paterno');">
		</td>
	</tr>	
	<tr>
		<td class="form-label">RFC</td>
		<td class="form-value">
			<%
			fElement00.initControl();
			fElement00.getControl().setType("rfc");
			fElement00.getControl().setId("moral_rfc");
			fElement00.getControl().setValue(personaMoral.getRfc());
			fElement00.getControl().setRequired(true);
			fElement00.getControl().setClassName("rfc");
			fElement00.getControl().setMaxLength("10");
			%>
			<%= fElement00.createInputElement() %>
			<%
			fElement00.initControl();
			fElement00.getControl().setType("rfc");
			fElement00.getControl().setId("moral_homoclave");
			fElement00.getControl().setValue(personaMoral.getHomoclave());
			fElement00.getControl().setRequired(true);
			fElement00.getControl().setClassName("homoclave");
			fElement00.getControl().setMaxLength("3");
			%>
			<%= fElement00.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="height:20px"></td>
	</tr>
	<tr>
		<td class="form-label"></td>
		<td class="form-value">Apoderado Legal</td>
	</tr>
</table>

<table style="margin-bottom:20px">
	<tr>
		<td class="form-label">Apellido Paterno</td>
		<td class="form-value">
			<%
			fElement00.initControl();
			fElement00.getControl().setType("name");
			fElement00.getControl().setId("apellido_paterno");
			fElement00.getControl().setValue(persona.getApellidoPaterno());
			fElement00.getControl().setRequired(true);
			fElement00.getControl().setMaxLength("100");
			%>
			<%= fElement00.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Apellido Materno</td>
		<td class="form-value">
			<%
			fElement00.initControl();
			fElement00.getControl().setType("name");
			fElement00.getControl().setId("apellido_materno");
			fElement00.getControl().setValue(persona.getApellidoMaterno());
			fElement00.getControl().setMaxLength("100");
			%>
			<%= fElement00.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Primer Nombre</td>
		<td class="form-value">
			<%
			fElement00.initControl();
			fElement00.getControl().setType("name");
			fElement00.getControl().setId("primer_nombre");
			fElement00.getControl().setValue(persona.getNombre());
			fElement00.getControl().setRequired(true);
			fElement00.getControl().setMaxLength("50");
			%>
			<%= fElement00.createInputElement() %>	
		</td>
	</tr>
	<tr>
		<td class="form-label">Segundo Nombre</td>
		<td class="form-value">
			<%
			fElement00.initControl();
			fElement00.getControl().setType("name");
			fElement00.getControl().setId("segundo_nombre");
			fElement00.getControl().setValue(persona.getSegundoNombre());
			fElement00.getControl().setMaxLength("50");
			%>
			<%= fElement00.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Fecha de Nacimiento</td>
		<td class="form-value">
			<%
			fElement00.initControl();
			fElement00.getControl().setType("date");
			fElement00.getControl().setId("fecha_nacimiento");
			fElement00.getControl().setValue(persona.getFecha());
			fElement00.getControl().setRequired(true);
			fElement00.getControl().setMaxLength("10");
			fElement00.getControl().setClassName("fecha");
			fElement00.getControl().setOnBlur("calcularEdad(this);");
			%>
			<%= fElement00.createInputElement() %>	
		</td>
	</tr>
	<tr>
		<td class="form-label">Edad</td>
		<td class="form-value">
			<%
			fElement00.initControl();
			fElement00.getControl().setId("anios");
			fElement00.getControl().setValue(persona.getAnios());
			fElement00.getControl().setRequired(true);
			fElement00.getControl().setReadOnly(true);
			fElement00.getControl().setClassName("digits-02");
			fElement00.getControl().setMaxLength("2");
			%>
			<%= fElement00.createInputElement() %>
			A&ntilde;os
			<%
			fElement00.initControl();
			fElement00.getControl().setId("meses");
			fElement00.getControl().setValue(persona.getMeses());
			fElement00.getControl().setRequired(true);
			fElement00.getControl().setReadOnly(true);
			fElement00.getControl().setClassName("digits-02");
			fElement00.getControl().setMaxLength("2");
			%>
			<%= fElement00.createInputElement() %>
			Meses			
		</td>
	</tr>
	<tr>
		<td class="form-label"></td>
		<td class="form-value">
			<input type="button" id="buscar" class="calcular" value="Calcular RFC" onClick="calcularRfcCurp(this, '', 'sexo');">
		</td>
	</tr>
	<tr>
		<td class="form-label">RFC</td>
		<td class="form-value">
			<%
			fElement00.initControl();
			fElement00.getControl().setType("rfc");
			fElement00.getControl().setId("rfc");
			fElement00.getControl().setValue(persona.getRfc());
			fElement00.getControl().setRequired(true);
			fElement00.getControl().setClassName("rfc");
			fElement00.getControl().setMaxLength("10");
			%>
			<%= fElement00.createInputElement() %>
			<%
			fElement00.initControl();
			fElement00.getControl().setType("rfc");
			fElement00.getControl().setId("homoclave");
			fElement00.getControl().setValue(persona.getHomoclave());
			fElement00.getControl().setRequired(true);
			fElement00.getControl().setClassName("homoclave");
			fElement00.getControl().setMaxLength("3");
			%>
			<%= fElement00.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Sexo</td>
		<td class="form-value">
			<%
			fElement00.initControl();
			fElement00.getControl().setId("sexo");
			fElement00.getControl().setValue(persona.getSexo());
			fElement00.getControl().setRequired(true);
			fElement00.getControl().setArrayOptions(fUtil00.getFormCatalogue("sexo"));
			%>
			<%= fElement00.createSelectElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Email</td>
		<td class="form-value">
			<%
			fElement00.initControl();
			fElement00.getControl().setType("email");
			fElement00.getControl().setId("email");
			fElement00.getControl().setValue(persona.getEmail());
			fElement00.getControl().setRequired(true);
			fElement00.getControl().setMaxLength("50");
			%>
			<%= fElement00.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="height:10px"></td>
	</tr>
	<tr>
		<td class="form-label">Celular</td>
		<td class="form-value">
			Lada
			<%
			fElement00.initControl();
			fElement00.getControl().setType("phone");
			fElement00.getControl().setId("cel_lada");
			fElement00.getControl().setValue(persona.getTelefonos("2").getLada());
			fElement00.getControl().setRequired(true);
			fElement00.getControl().setClassName("digits-03");
			fElement00.getControl().setMaxLength("3");
			%>
			<%= fElement00.createInputElement() %>
			N&uacute;mero
			<%
			fElement00.initControl();
			fElement00.getControl().setType("phone");
			fElement00.getControl().setId("cel_numero");
			fElement00.getControl().setValue(persona.getTelefonos("2").getNumero());
			fElement00.getControl().setRequired(true);
			fElement00.getControl().setClassName("digits-08");
			fElement00.getControl().setMaxLength("8");
			%>
			<%= fElement00.createInputElement() %>			
		</td>
	</tr>
</table>