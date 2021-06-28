<%
force.eai.mx.util.Cliente cliente = ( session.getAttribute("cliente") != null ) ? (force.eai.mx.util.Cliente)session.getAttribute("cliente") : new force.eai.mx.util.Cliente() ;
force.eai.mx.form.FormUtils fu = new force.eai.mx.form.FormUtils(session);
force.eai.mx.form.FormElements fe = new force.eai.mx.form.FormElements();
fu.setCliente(cliente);

fe.setPreffix(fu.getFormSection("section3").getPreffix());

force.eai.mx.util.Persona persona02 = new force.eai.mx.util.Persona();
force.eai.mx.util.Persona personaMoral02 = new force.eai.mx.util.Persona();

if ( cliente.getTipo().equals("2") ) {
	personaMoral02 = cliente.getPersona();
	persona02 = cliente.getApoderado();
} else {
	persona02 = cliente.getPersona();
}
%>

<table>
	<tr>
		<td class="form-label">Tipo</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setId("tipo");
			fe.getControl().setValue(cliente.getTipo());
			fe.getControl().setRequired(true);
			fe.getControl().setInitValue(false);
			fe.getControl().setArrayOptions(fu.getFormCatalogue("tipo"));
			fe.getControl().setOnChange("validaTipo(this);");
			%>
			<%= fe.createSelectElement() %>
		</td>            
	</tr>
</table>

<table id="<%= fe.getPreffix() %>moral_datos" <%= fu.styleDisplayTipoMoral() %>>
	<tr>
		<td class="form-label">Raz&oacute;n Social</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setId("moral_nombre");
			fe.getControl().setValue(personaMoral02.getNombre());
			fe.getControl().setRequired(true);
			fe.getControl().setClassName("razonSocial");
			fe.getControl().setMaxLength("200");
			fe.getControl().setOnBlur("validaRazonSocial(this);");
			%>
			<%= fe.createTextareaElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Fecha</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("date");
			fe.getControl().setId("moral_fecha");
			fe.getControl().setValue(personaMoral02.getFecha());
			fe.getControl().setRequired(true);
			fe.getControl().setMaxLength("10");
			fe.getControl().setClassName("fecha");
			fe.getControl().setOnBlur("calcularEdad(this);");
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Antig&uuml;edad</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setId("moral_anios");
			fe.getControl().setValue(personaMoral02.getAnios());
			fe.getControl().setRequired(true);
			fe.getControl().setReadOnly(true);
			fe.getControl().setClassName("digits-02");
			fe.getControl().setMaxLength("2");
			%>
			<%= fe.createInputElement() %>
			A&ntilde;os
			<%
			fe.initControl();
			fe.getControl().setId("moral_meses");
			fe.getControl().setValue(personaMoral02.getMeses());
			fe.getControl().setRequired(true);
			fe.getControl().setReadOnly(true);
			fe.getControl().setClassName("digits-02");
			fe.getControl().setMaxLength("2");
			%>
			<%= fe.createInputElement() %>
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
			fe.initControl();
			fe.getControl().setType("rfc");
			fe.getControl().setId("moral_rfc");
			fe.getControl().setValue(personaMoral02.getRfc());
			fe.getControl().setRequired(true);
			fe.getControl().setClassName("rfc");
			fe.getControl().setMaxLength("10");
			%>
			<%= fe.createInputElement() %>
			<%
			fe.initControl();
			fe.getControl().setType("rfc");
			fe.getControl().setId("moral_homoclave");
			fe.getControl().setValue(personaMoral02.getHomoclave());
			fe.getControl().setRequired(true);
			fe.getControl().setClassName("homoclave");
			fe.getControl().setMaxLength("3");
			%>
			<%= fe.createInputElement() %>
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

<table style="margin-bottom:20px;">
	<tr>
		<td class="form-label">Apellido Paterno</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("name");
			fe.getControl().setId("apellido_paterno");
			fe.getControl().setValue(persona02.getApellidoPaterno());
			fe.getControl().setRequired(true);
			fe.getControl().setMaxLength("100");
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Apellido Materno</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("name");
			fe.getControl().setId("apellido_materno");
			fe.getControl().setValue(persona02.getApellidoMaterno());
			fe.getControl().setMaxLength("100");
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Primer Nombre</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("name");
			fe.getControl().setId("primer_nombre");
			fe.getControl().setValue(persona02.getNombre());
			fe.getControl().setRequired(true);
			fe.getControl().setMaxLength("50");
			%>
			<%= fe.createInputElement() %>	
		</td>
	</tr>
	<tr>
		<td class="form-label">Segundo Nombre</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("name");
			fe.getControl().setId("segundo_nombre");
			fe.getControl().setValue(persona02.getSegundoNombre());
			fe.getControl().setMaxLength("50");
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Fecha de Nacimiento</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("date");
			fe.getControl().setId("fecha_nacimiento");
			fe.getControl().setValue(persona02.getFecha());
			fe.getControl().setRequired(true);
			fe.getControl().setMaxLength("10");
			fe.getControl().setClassName("fecha");
			fe.getControl().setOnBlur("calcularEdad(this);");
			%>
			<%= fe.createInputElement() %>	
		</td>
	</tr>
	<tr>
		<td class="form-label">Edad</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setId("anios");
			fe.getControl().setValue(persona02.getAnios());
			fe.getControl().setRequired(true);
			fe.getControl().setReadOnly(true);
			fe.getControl().setClassName("digits-02");
			fe.getControl().setMaxLength("2");
			%>
			<%= fe.createInputElement() %>
			A&ntilde;os
			<%
			fe.initControl();
			fe.getControl().setId("meses");
			fe.getControl().setValue(persona02.getMeses());
			fe.getControl().setRequired(true);
			fe.getControl().setReadOnly(true);
			fe.getControl().setClassName("digits-02");
			fe.getControl().setMaxLength("2");
			%>
			<%= fe.createInputElement() %>
			Meses			
		</td>
	</tr>
	<tr>
		<td colspan="2" style="height:15px"></td>
	</tr>	
	<tr>
		<td class="form-label">Sexo</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setId("sexo");
			fe.getControl().setValue(persona02.getSexo());
			fe.getControl().setInitValue(false);
			fe.getControl().setArrayOptions(fu.getFormCatalogue("sexo"));
			%>
			<%= fe.createSelectElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Pais de Nacimiento</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setId("pais_nacimiento");
			fe.getControl().setValue(persona02.getPaisNacimiento());
			fe.getControl().setRequired(true);
			fe.getControl().setIterator(fu.getCatalogue("formCataloguesHashMap", "paises"));
			%>
			<%= fe.createSelectElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Estado de Nacimiento</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setId("estado_nacimiento");
			fe.getControl().setValue(persona02.getEstadoNacimiento());
			fe.getControl().setRequired(true);
			fe.getControl().setArrayOptions(fu.getFormCatalogue("estados"));
			%>
			<%= fe.createSelectElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Lugar de Nacimiento</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setId("lugar_nacimiento");
			fe.getControl().setValue(persona02.getLugarNacimiento());
			fe.getControl().setMaxLength("30");
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<%
	// NACIONALIDAD
	fe.initControl();
	fe.getControl().setId("nacionalidad");
	fe.getControl().setValue( persona02.getNacionalidad() );
	fe.getControl().setRequired(true);
	fe.getControl().setArrayOptions(fu.getFormCatalogue("nacionalidad"));
	fe.getControl().setMaxLength("20");
	%>
	<%= fe.createSelectWithOtherElement("Nacionalidad") %>
	<tr>
		<td class="form-label"></td>
		<td class="form-value">
			<input type="button" class="calcular" id="buscar" value="Calcular RFC y CURP" onClick="calcularRfcCurp(this, '', 'no_imss');">
		</td>
	</tr>
	<tr>
		<td class="form-label">RFC</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("rfc");
			fe.getControl().setId("rfc");
			fe.getControl().setValue(persona02.getRfc());
			fe.getControl().setRequired(true);
			fe.getControl().setClassName("rfc");
			fe.getControl().setMaxLength("10");
			%>
			<%= fe.createInputElement() %>
			<%
			fe.initControl();
			fe.getControl().setType("rfc");
			fe.getControl().setId("homoclave");
			fe.getControl().setValue(persona02.getHomoclave());
			fe.getControl().setRequired(true);
			fe.getControl().setClassName("homoclave");
			fe.getControl().setMaxLength("3");
			%>
			<%= fe.createInputElement() %>			
		</td>
	</tr>
	<tr>
		<td class="form-label">CURP</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("alphanumeric");
			fe.getControl().setId("curp");
			fe.getControl().setValue(persona02.getCurp());
			fe.getControl().setMaxLength("20");
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">No. IMSS</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("alphanumeric");
			fe.getControl().setId("no_imss");
			fe.getControl().setValue(persona02.getNoImss());
			fe.getControl().setMaxLength("11");
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="height:15px"></td>
	</tr>
	<%
	// NIVEL ACADEMICO
	fe.initControl();
	fe.getControl().setId("nivel_academico");
	fe.getControl().setValue( persona02.getNivelAcademico() );
	fe.getControl().setArrayOptions(fu.getFormCatalogue("nivelAcademico"));
	fe.getControl().setMaxLength("20");
	%>
	<%= fe.createSelectWithOtherElement("Nivel Académico") %>
	<tr>
		<td class="form-label">Estado Civil</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setId("estado_civil");
			fe.getControl().setValue(persona02.getEstadoCivil());
			fe.getControl().setOnChange("muestraTr(this,'CASADO');");
			fe.getControl().setArrayOptions(fu.getFormCatalogue("estadoCivil"));
			%>
			<%= fe.createSelectElement() %>
		</td>
	</tr>
	<tr id="<%= fe.getPreffix() %>estado_civil_tr" <%= ( cliente.getPersona().getEstadoCivil().equals("CASADO") ) ? "" : "style=\"display:none\"" %>>
		<td class="form-label">R&eacute;gimen Conyugal</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setId("regimen");
			fe.getControl().setValue(persona02.getRegimen());
			fe.getControl().setArrayOptions(fu.getFormCatalogue("regimen"));
			%>
			<%= fe.createSelectElement() %>
		</td>
	</tr>
	<%
	// IDENTIFICACION
	fe.initControl();
	fe.getControl().setId("identificacion");
	fe.getControl().setValue( persona02.getIdentificacion() );
	fe.getControl().setRequired(true);
	fe.getControl().setArrayOptions(fu.getFormCatalogue("identificacion"));
	fe.getControl().setMaxLength("20");
	%>
	<%= fe.createSelectWithOtherElement("Identificación") %>
	<tr>
		<td class="form-label">Num. Identificaci&oacute;n</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setId("numero_identificacion");
			fe.getControl().setValue(persona02.getNumeroIdentificacion());
			fe.getControl().setMaxLength("20");
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="height:15px"></td>
	</tr>
	<tr>
		<td class="form-label">Email</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("email");
			fe.getControl().setId("email");
			fe.getControl().setValue(persona02.getEmail());
			fe.getControl().setRequired(true);
			fe.getControl().setMaxLength("50");
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
</table>
