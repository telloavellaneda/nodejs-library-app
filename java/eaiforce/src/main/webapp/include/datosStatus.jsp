<%
force.eai.mx.util.Force force = ( session.getAttribute("force") != null ) ? (force.eai.mx.util.Force)session.getAttribute("force") : new force.eai.mx.util.Force();
force.eai.mx.util.Cliente cliente = ( session.getAttribute("cliente") != null ) ? (force.eai.mx.util.Cliente)session.getAttribute("cliente") : new force.eai.mx.util.Cliente();
force.eai.mx.form.FormUtils fu = new force.eai.mx.form.FormUtils(session);
force.eai.mx.form.FormElements fe = new force.eai.mx.form.FormElements();

String onClick15 = ( !force.getUsuario("0").getProfile().isReadOnly() && !cliente.getForceId().equals("") ) ? "onClick=\"upsertStatus();\"" : "";
%>
<div class="form-div-status" style="margin-top:15px">
	<div>
		<%
		fe.initControl();
		fe.getControl().setId("id_fase");
		fe.getControl().setValue(cliente.getIdFase());
		fe.getControl().setInitValue(false);
		fe.getControl().setClassName("status-combo");
		fe.getControl().setOnChange("getStatusElement(this);");
		if ( !cliente.getForceId().equals("") )
			fe.getControl().setIterator(fu.getCatalogue("filterCatalogues", "fases"));
		else
			fe.getControl().setArrayOptions(new String[][] {{"0", "FASE 0"}});
		%>
		<%= fe.createSelectElement() %>
	</div>
	<div id="formStatusCombo">
		<%
		fe.initControl();
		fe.getControl().setId("id_status");
		fe.getControl().setValue(cliente.getIdStatus());
		fe.getControl().setInitValue(false);
		fe.getControl().setClassName("status-combo");
		if ( !cliente.getForceId().equals("") )
			fe.getControl().setIterator(fu.getCatalogue("filterCatalogues", "fase" + cliente.getIdFase()));
		else
			fe.getControl().setArrayOptions(new String[][] {{"0", "PROSPECTO"}});
		%>
		<%= fe.createSelectElement() %>
	</div>
</div>

<div class="width-100" style="margin:10px auto">
	<input type="button" class="status" value="Enviar a" id="id_status_action" <%= onClick15 %>>
</div>
