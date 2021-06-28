<%
force.eai.mx.util.Force force = ( session.getAttribute("force") != null ) ? (force.eai.mx.util.Force)session.getAttribute("force") : new force.eai.mx.util.Force() ;
force.eai.mx.util.Cliente cliente = ( session.getAttribute("cliente") != null ) ? (force.eai.mx.util.Cliente)session.getAttribute("cliente") : new force.eai.mx.util.Cliente();
force.eai.mx.util.Credito credito = cliente.getCreditos("0");
force.eai.mx.form.FormUtils fu = new force.eai.mx.form.FormUtils(session);
force.eai.mx.form.FormElements fe = new force.eai.mx.form.FormElements();
force.eai.mx.tools.FormatValues fv = new force.eai.mx.tools.FormatValues();
fu.setCliente(cliente);

fe.setPreffix(fu.getFormSection("section1").getPreffix());
%>

<table style="margin-bottom:20px">
	<tr>
		<td class="form-label">Asignado a</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setId("usuario_asignado");
			fe.getControl().setValue(cliente.getUsuario().getIdPersona());
			fe.getControl().setIterator(fu.getCatalogue("filterCatalogues", "usuariosDisponibles"));
			fe.getControl().setInitValue(false);
			fe.getControl().setOnlyValue( !force.getUsuario("0").getProfile().isMultiusuario() && !force.getUsuario("0").getProfile().isOperaciones() );
			%>
			<%= fe.createSelectElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Responsable</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setId("responsable");
			fe.getControl().setValue(cliente.getUsuarioResponsable().getIdPersona());
			fe.getControl().setIterator(fu.getCatalogue("filterCatalogues", "usuariosResponsables"));
			fe.getControl().setInitValue(false);
			fe.getControl().setOnlyValue( !force.getUsuario("0").getProfile().isMultiusuario() );
			%>
			<%= fe.createSelectElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Ejecutivo Banco</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("name");
			fe.getControl().setId("ejecutivo_banco");
			fe.getControl().setValue(credito.getEjecutivoBanco());
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Notar&iacute;a</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("");
			fe.getControl().setId("notaria");
			fe.getControl().setValue(credito.getNotaria());
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>	
	<tr>
		<td class="form-label">Abogado</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("name");
			fe.getControl().setId("abogado");
			fe.getControl().setValue(credito.getAbogado());
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="height:10px"></td>
	</tr>	
	<tr>
		<td class="form-label">Fecha Expediente</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("date");
			fe.getControl().setId("fecha_expediente");
			fe.getControl().setValue(cliente.getFechas("expediente"));
			fe.getControl().setMaxLength("10");
			fe.getControl().setClassName("fecha");
			fe.getControl().setAutoComplete(true);
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Fecha Autorizaci&oacute;n</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("date");
			fe.getControl().setId("fecha_autorizacion");
			fe.getControl().setValue(cliente.getFechas("autorizacion"));
			fe.getControl().setMaxLength("10");
			fe.getControl().setClassName("fecha");
			fe.getControl().setAutoComplete(true);
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Fecha Firma</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("date");
			fe.getControl().setId("fecha_firma");
			fe.getControl().setValue(cliente.getFechas("firma"));
			fe.getControl().setMaxLength("10");
			fe.getControl().setClassName("fecha");
			fe.getControl().setAutoComplete(true);
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="height:10px"></td>
	</tr>
	<tr>
		<td class="form-label">Fecha Fase 1</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("date");
			fe.getControl().setId("fecha_fase_1");
			fe.getControl().setValue(cliente.getFechas("fase1"));
			fe.getControl().setMaxLength("10");
			fe.getControl().setClassName("fecha");
			fe.getControl().setAutoComplete(true);
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Fecha Fase 2</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("date");
			fe.getControl().setId("fecha_fase_2");
			fe.getControl().setValue(cliente.getFechas("fase2"));
			fe.getControl().setMaxLength("10");
			fe.getControl().setClassName("fecha");
			fe.getControl().setAutoComplete(true);
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Fecha Fase 3</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("date");
			fe.getControl().setId("fecha_fase_3");
			fe.getControl().setValue(cliente.getFechas("fase3"));
			fe.getControl().setMaxLength("10");
			fe.getControl().setClassName("fecha");
			fe.getControl().setAutoComplete(true);
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Fecha Vencimiento L&iacute;nea</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("date");
			fe.getControl().setId("fecha_vencimiento_linea");
			fe.getControl().setValue(cliente.getFechas("linea"));
			fe.getControl().setMaxLength("10");
			fe.getControl().setClassName("fecha");
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Fecha Vencimiento Certificado</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("date");
			fe.getControl().setId("fecha_vencimiento_certificado");
			fe.getControl().setValue(cliente.getFechas("certificado"));
			fe.getControl().setMaxLength("10");
			fe.getControl().setClassName("fecha");
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
</table>