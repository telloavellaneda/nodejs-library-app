<%
force.eai.mx.util.Cliente cliente06 = ( session.getAttribute("cliente") != null ) ? (force.eai.mx.util.Cliente)session.getAttribute("cliente") : new force.eai.mx.util.Cliente() ;
force.eai.mx.form.FormUtils fUtil06 = new force.eai.mx.form.FormUtils(session);
force.eai.mx.form.FormElements fElement06 = new force.eai.mx.form.FormElements();
fUtil06.setCliente(cliente06);

fElement06.setPreffix("coa_dg_");
%>

<table style="margin-bottom:20px">
	<tr>
		<td class="form-label">Apellido Paterno</td>
		<td class="form-value">
			<%
			fElement06.initControl();
			fElement06.getControl().setType("name");
			fElement06.getControl().setId("apellido_paterno");
			fElement06.getControl().setValue(cliente06.getCoacreditado("0").getApellidoPaterno());
			fElement06.getControl().setRequired(true);
			fElement06.getControl().setMaxLength("100");
			%>
			<%= fElement06.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Apellido Materno</td>
		<td class="form-value">
			<%
			fElement06.initControl();
			fElement06.getControl().setType("name");
			fElement06.getControl().setId("apellido_materno");
			fElement06.getControl().setValue(cliente06.getCoacreditado("0").getApellidoMaterno());
			fElement06.getControl().setMaxLength("100");
			%>
			<%= fElement06.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Primer Nombre</td>
		<td class="form-value">
			<%
			fElement06.initControl();
			fElement06.getControl().setType("name");
			fElement06.getControl().setId("primer_nombre");
			fElement06.getControl().setValue(cliente06.getCoacreditado("0").getNombre());
			fElement06.getControl().setRequired(true);
			fElement06.getControl().setMaxLength("50");
			%>
			<%= fElement06.createInputElement() %>	
		</td>
	</tr>
	<tr>
		<td class="form-label">Segundo Nombre</td>
		<td class="form-value">
			<%
			fElement06.initControl();
			fElement06.getControl().setType("name");
			fElement06.getControl().setId("segundo_nombre");
			fElement06.getControl().setValue(cliente06.getCoacreditado("0").getSegundoNombre());
			fElement06.getControl().setMaxLength("50");
			%>
			<%= fElement06.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Fecha de Nacimiento</td>
		<td class="form-value">
			<%
			fElement06.initControl();
			fElement06.getControl().setType("date");
			fElement06.getControl().setId("fecha_nacimiento");
			fElement06.getControl().setValue(cliente06.getCoacreditado("0").getFecha());
			fElement06.getControl().setRequired(true);
			fElement06.getControl().setMaxLength("10");
			fElement06.getControl().setClassName("fecha");
			fElement06.getControl().setOnBlur("calcularEdad(this);");
			%>
			<%= fElement06.createInputElement() %>	
		</td>
	</tr>
	<tr>
		<td class="form-label">Edad</td>
		<td class="form-value">
			<%
			fElement06.initControl();
			fElement06.getControl().setId("anios");
			fElement06.getControl().setValue(cliente06.getCoacreditado("0").getAnios());
			fElement06.getControl().setRequired(true);
			fElement06.getControl().setReadOnly(true);
			fElement06.getControl().setClassName("digits-02");
			fElement06.getControl().setMaxLength("2");
			%>
			<%= fElement06.createInputElement() %>
			A&ntilde;os
			<%
			fElement06.initControl();
			fElement06.getControl().setId("meses");
			fElement06.getControl().setValue(cliente06.getCoacreditado("0").getMeses());
			fElement06.getControl().setRequired(true);
			fElement06.getControl().setReadOnly(true);
			fElement06.getControl().setClassName("digits-02");
			fElement06.getControl().setMaxLength("2");
			%>
			<%= fElement06.createInputElement() %>
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
			fElement06.initControl();
			fElement06.getControl().setId("sexo");
			fElement06.getControl().setValue(cliente06.getCoacreditado("0").getSexo());
			fElement06.getControl().setRequired(true);
			fElement06.getControl().setArrayOptions(fUtil06.getFormCatalogue("sexo"));
			%>
			<%= fElement06.createSelectElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Pais de Nacimiento</td>
		<td class="form-value">
			<%
			fElement06.initControl();
			fElement06.getControl().setId("pais_nacimiento");
			fElement06.getControl().setValue(cliente06.getCoacreditado("0").getPaisNacimiento());
			fElement06.getControl().setRequired(true);
			fElement06.getControl().setIterator(fUtil06.getCatalogue("formCataloguesHashMap", "paises"));
			%>
			<%= fElement06.createSelectElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Estado de Nacimiento</td>
		<td class="form-value">
			<%
			fElement06.initControl();
			fElement06.getControl().setId("estado_nacimiento");
			fElement06.getControl().setValue(cliente06.getCoacreditado("0").getEstadoNacimiento());
			fElement06.getControl().setArrayOptions(fUtil06.getFormCatalogue("estados"));
			%>
			<%= fElement06.createSelectElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Lugar de Nacimiento</td>
		<td class="form-value">
			<%
			fElement06.initControl();
			fElement06.getControl().setId("lugar_nacimiento");
			fElement06.getControl().setValue(cliente06.getCoacreditado("0").getLugarNacimiento());
			fElement06.getControl().setMaxLength("30");
			%>
			<%= fElement06.createInputElement() %>
		</td>
	</tr>
	<%
	// NACIONALIDAD
	fElement06.initControl();
	fElement06.getControl().setId("nacionalidad");
	fElement06.getControl().setValue( cliente06.getCoacreditado("0").getNacionalidad() );
	fElement06.getControl().setRequired(true);
	fElement06.getControl().setArrayOptions(fUtil06.getFormCatalogue("nacionalidad"));
	fElement06.getControl().setMaxLength("20");
	%>
	<%= fElement06.createSelectWithOtherElement("Nacionalidad") %>
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
			fElement06.initControl();
			fElement06.getControl().setType("rfc");
			fElement06.getControl().setId("rfc");
			fElement06.getControl().setValue(cliente06.getCoacreditado("0").getRfc());
			fElement06.getControl().setRequired(true);
			fElement06.getControl().setClassName("rfc");
			fElement06.getControl().setMaxLength("10");
			%>
			<%= fElement06.createInputElement() %>
			<%
			fElement06.initControl();
			fElement06.getControl().setType("rfc");
			fElement06.getControl().setId("homoclave");
			fElement06.getControl().setValue(cliente06.getCoacreditado("0").getHomoclave());
			fElement06.getControl().setRequired(true);
			fElement06.getControl().setClassName("homoclave");
			fElement06.getControl().setMaxLength("3");
			%>
			<%= fElement06.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">CURP</td>
		<td class="form-value">
			<%
			fElement06.initControl();
			fElement06.getControl().setType("alphanumeric");
			fElement06.getControl().setId("curp");
			fElement06.getControl().setValue(cliente06.getCoacreditado("0").getCurp());
			fElement06.getControl().setMaxLength("20");
			%>
			<%= fElement06.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">No. IMSS</td>
		<td class="form-value">
			<%
			fElement06.initControl();
			fElement06.getControl().setType("alphanumeric");
			fElement06.getControl().setId("no_imss");
			fElement06.getControl().setValue(cliente06.getCoacreditado("0").getNoImss());
			fElement06.getControl().setMaxLength("11");
			%>
			<%= fElement06.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="height:15px"></td>
	</tr>
	<%
	// NIVEL ACADEMICO
	fElement06.initControl();
	fElement06.getControl().setId("nivel_academico");
	fElement06.getControl().setValue( cliente06.getCoacreditado("0").getNivelAcademico() );
	fElement06.getControl().setArrayOptions(fUtil06.getFormCatalogue("nivelAcademico"));
	fElement06.getControl().setMaxLength("20");
	%>
	<%= fElement06.createSelectWithOtherElement("Nivel Académico") %>
	<tr>
		<td class="form-label">Estado Civil</td>
		<td class="form-value">
			<%
			fElement06.initControl();
			fElement06.getControl().setId("estado_civil");
			fElement06.getControl().setValue(cliente06.getCoacreditado("0").getEstadoCivil());
			fElement06.getControl().setRequired(true);
			fElement06.getControl().setOnChange("muestraTr(this,'CASADO');");
			fElement06.getControl().setArrayOptions(fUtil06.getFormCatalogue("estadoCivil"));
			%>
			<%= fElement06.createSelectElement() %>
		</td>
	</tr>
	<tr id="<%= fElement06.getPreffix() %>estado_civil_tr" <%= ( cliente06.getCoacreditado("0").getEstadoCivil().equals("CASADO") ) ? "" : "style=\"display:none\"" %>>
		<td class="form-label">R&eacute;gimen Conyugal</td>
		<td class="form-value">
			<%
			fElement06.initControl();
			fElement06.getControl().setId("regimen");
			fElement06.getControl().setValue(cliente06.getCoacreditado("0").getRegimen());
			fElement06.getControl().setArrayOptions(fUtil06.getFormCatalogue("regimen"));
			%>
			<%= fElement06.createSelectElement() %>
		</td>
	</tr>
	<%
	// IDENTIFICACION
	fElement06.initControl();
	fElement06.getControl().setId("identificacion");
	fElement06.getControl().setValue( cliente06.getCoacreditado("0").getIdentificacion() );
	fElement06.getControl().setRequired(true);
	fElement06.getControl().setArrayOptions(fUtil06.getFormCatalogue("identificacion"));
	fElement06.getControl().setMaxLength("20");
	%>
	<%= fElement06.createSelectWithOtherElement("Identificación") %>
	<tr>
		<td class="form-label">Num. Identificaci&oacute;n</td>
		<td class="form-value">
			<%
			fElement06.initControl();
			fElement06.getControl().setId("numero_identificacion");
			fElement06.getControl().setValue(cliente06.getCoacreditado("0").getNumeroIdentificacion());
			fElement06.getControl().setMaxLength("20");
			%>
			<%= fElement06.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="height:15px"></td>
	</tr>
	<tr>
		<td class="form-label">Email</td>
		<td class="form-value">
			<%
			fElement06.initControl();
			fElement06.getControl().setType("email");
			fElement06.getControl().setId("email");
			fElement06.getControl().setValue(cliente06.getCoacreditado("0").getEmail());
			fElement06.getControl().setRequired(true);
			fElement06.getControl().setMaxLength("50");
			%>
			<%= fElement06.createInputElement() %>
		</td>
	</tr>
</table>