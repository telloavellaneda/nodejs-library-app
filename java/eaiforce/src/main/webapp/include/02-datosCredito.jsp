<%
force.eai.mx.util.Cliente cliente = ( session.getAttribute("cliente") != null ) ? (force.eai.mx.util.Cliente)session.getAttribute("cliente") : new force.eai.mx.util.Cliente() ;
force.eai.mx.util.Credito credito = cliente.getCreditos("0");
force.eai.mx.form.FormUtils fu = new force.eai.mx.form.FormUtils(session);
force.eai.mx.form.FormElements fe = new force.eai.mx.form.FormElements();
fu.setCliente(cliente);

fe.setPreffix(fu.getFormSection("section2").getPreffix());
%>

<table style="margin-bottom:20px">
	<tr>
		<td class="form-label">Banco</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setId("id_banco");
			fe.getControl().setValue(credito.getIdBanco());
			fe.getControl().setRequired(true);
			fe.getControl().setInitValue(false);
			fe.getControl().setIterator(fu.getCatalogue("filterCatalogues", "bancosForm"));
			fe.getControl().setOnChange("getProductElement(this);");
			%>
			<%= fe.createSelectElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Producto</td>
		<td class="form-value" id="cred_producto_td">
			<%
			fe.initControl();
			fe.getControl().setId("producto");
			fe.getControl().setValue(credito.getProducto());
			fe.getControl().setIterator(fu.getCatalogue("filterCatalogues", "banco" + credito.getIdBanco()));
			%>
			<%= fe.createSelectElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Destino</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setId("destino");
			fe.getControl().setValue(credito.getDestino());
			fe.getControl().setIterator(fu.getCatalogue("filterCatalogues", "destinos"));
			%>
			<%= fe.createSelectElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Adherido</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setId("adherido");
			fe.getControl().setValue(credito.getAdherido());
			fe.getControl().setIterator(fu.getCatalogue("formCataloguesHashMap", "adheridos"));
			fe.getControl().setOnChange("muestraTr(this,'3');");
			%>
			<%= fe.createSelectElement() %>
		</td>
	</tr>
	<tr id="<%= fe.getPreffix() %>adherido_tr" <%= ( credito.getAdherido().equals("3") ) ? "" : "style=\"display:none\"" %>>
		<td class="form-label"></td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setId("adherido_opcion");
			fe.getControl().setValue(credito.getAdheridoOpcion());
			fe.getControl().setInitValue(false);
			fe.getControl().setLabelLength(35);
			fe.getControl().setIterator(fu.getCatalogue("formCataloguesHashMap", "adheridos3"));
			%>
			<%= fe.createSelectElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Monto Solicitado</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("currency");
			fe.getControl().setId("importe_solic");
			fe.getControl().setValue(new force.eai.mx.tools.FormatValues().formatMoney(credito.getImporteSolicitado()));
			fe.getControl().setRequired(true);
			fe.getControl().setMaxLength("13");
			fe.getControl().setPrecision("2");
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="height:15px"></td>
	</tr>
	<tr>
		<td class="form-label">Decreto</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setId("decreto");
			fe.getControl().setValue(credito.getDecreto());
			fe.getControl().setArrayOptions(fu.getFormCatalogue("decreto"));
			fe.getControl().setOnChange("muestraTr(this,'CONDICIONADO'); muestraImporteAprobado(this);");
			%>
			<%= fe.createSelectElement() %>
		</td>
	</tr>
	<tr id="<%= fe.getPreffix() %>decreto_tr" <%= ( !credito.getDecreto().equals("CONDICIONADO") ) ? "style=\"display:none\"" : "" %>>
		<td class="form-label"></td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setId("decreto_comentarios");
			fe.getControl().setValue(credito.getDecretoComentarios());
			fe.getControl().setMaxLength("500");
			%>
			<%= fe.createTextareaElement() %>
		</td>
	</tr>
	<tr id="<%= fe.getPreffix() %>importe_aprob_tr" <%= ( credito.getDecreto().equals("RECHAZADO") ) ? "style=\"display:none\"" : "" %>>
		<td class="form-label">Monto Aprobado</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("currency");
			fe.getControl().setId("importe_aprob");
			fe.getControl().setValue(new force.eai.mx.tools.FormatValues().formatMoney(credito.getImporteAprobado()));
			fe.getControl().setMaxLength("13");
			fe.getControl().setPrecision("2");
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<tr id="<%= fe.getPreffix() %>importe_final_tr" <%= ( credito.getDecreto().equals("RECHAZADO") ) ? "style=\"display:none\"" : "" %>>
		<td class="form-label">Monto Firmado</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("currency");
			fe.getControl().setId("importe_final");
			fe.getControl().setValue(new force.eai.mx.tools.FormatValues().formatMoney(credito.getImporteFinal()));
			fe.getControl().setMaxLength("13");
			fe.getControl().setPrecision("2");
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="height:15px"></td>
	</tr>
	<%
	// COACREDITADO
	fe.initControl();
	fe.getControl().setId("coacreditado");
	fe.getControl().setValue( credito.getCoacreditado() );
	fe.getControl().setArrayOptions(fu.getFormCatalogue("coacreditado"));
	fe.getControl().setInitValue(false);
	fe.getControl().setOnChange("getFormTitle(); upsertAttributes('coacreditado=' + this.value);");
	fe.getControl().setMaxLength("20");
	%>
	<%= fe.createSelectWithOtherElement("Coacreditado") %>
	<tr>
		<td colspan="2" style="height:15px"></td>
	</tr>	
	<tr>
		<td class="form-label">Valor Estimado</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("currency");
			fe.getControl().setId("valor_estimado");
			fe.getControl().setValue(new force.eai.mx.tools.FormatValues().formatMoney(credito.getValorEstimado()));
			fe.getControl().setMaxLength("13");
			fe.getControl().setPrecision("2");
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Enganche</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("currency");
			fe.getControl().setId("enganche");
			fe.getControl().setValue(new force.eai.mx.tools.FormatValues().formatMoney(credito.getEnganche()));
			fe.getControl().setMaxLength("13");
			fe.getControl().setPrecision("2");
			%>
			<%= fe.createInputElement() %>
		</td>
	</tr>
	<%
	// ORIGEN ENGANCHE
	fe.initControl();
	fe.getControl().setId("origen_enganche");
	fe.getControl().setValue( credito.getOrigenEnganche() );
	fe.getControl().setArrayOptions(fu.getFormCatalogue("origenEnganche"));
	fe.getControl().setMaxLength("20");
	%>
	<%= fe.createSelectWithOtherElement("Origen Enganche") %>
	<%
	// PLAZO
	fe.initControl();
	fe.getControl().setId("plazo");
	fe.getControl().setValue( credito.getPlazo() );
	fe.getControl().setArrayOptions(fu.getFormCatalogue("plazo"));
	fe.getControl().setMaxLength("2");
	fe.getControl().setType("number");
	fe.getControl().setClassName("digits-03");
	%>
	<%= fe.createSelectWithOtherElement("Plazo") %>
	<tr>
		<td class="form-label">Tasa</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("number");
			fe.getControl().setId("tasa");
			fe.getControl().setValue(credito.getTasa());
			fe.getControl().setClassName("digits-03");
			fe.getControl().setMaxLength("4");
			fe.getControl().setPrecision("2");
			%>
			<%= fe.createInputElement() %>
			%
		</td>
	</tr>
	<tr>
		<td class="form-label">Aforo</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("number");
			fe.getControl().setId("aforo");
			fe.getControl().setValue(credito.getAforo());
			fe.getControl().setClassName("digits-03");
			fe.getControl().setMaxLength("4");
			fe.getControl().setPrecision("1");
			%>
			<%= fe.createInputElement() %>
			%
		</td>
	</tr>
	<tr>
		<td class="form-label">Tipo Comisión por Apertura</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setId("tipo_comision_apertura");
			fe.getControl().setValue(credito.getTipoComisionApertura());
			fe.getControl().setArrayOptions(fu.getFormCatalogue("tipoComision"));
			fe.getControl().setOnChange("escondeTr(this,'SIN COMISION');");
			%>
			<%= fe.createSelectElement() %>
		</td>
	</tr>
	<tr id="<%= fe.getPreffix() %>tipo_comision_apertura_tr" <%= ( credito.getTipoComisionApertura().equals("SIN COMISION") ) ? "style=\"display:none\"" : "" %>>
		<td class="form-label">Comisión por Apertura</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("number");
			fe.getControl().setId("comision_apertura");
			fe.getControl().setValue(credito.getComisionApertura());
			fe.getControl().setClassName("digits-03");
			fe.getControl().setMaxLength("4");
			fe.getControl().setPrecision("2");
			%>
			<%= fe.createInputElement() %>
			%
		</td>
	</tr>
</table>