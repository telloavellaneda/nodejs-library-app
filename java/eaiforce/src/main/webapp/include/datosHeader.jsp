<%
force.eai.mx.util.Cliente cliente00 = ( session.getAttribute("cliente") != null ) ? (force.eai.mx.util.Cliente)session.getAttribute("cliente") : new force.eai.mx.util.Cliente();
force.eai.mx.util.Force force00 = ( session.getAttribute("force") != null ) ? (force.eai.mx.util.Force)session.getAttribute("force") : new force.eai.mx.util.Force();
force.eai.mx.form.FormUtils formUtils00 = new force.eai.mx.form.FormUtils(session);
force.eai.mx.tools.FormatValues fv = new force.eai.mx.tools.FormatValues();
%>

<table class="datos">
	<tr>
		<td id="force_id"><%= ( !cliente00.getForceId().equals("") ) ? cliente00.getForceId() : "Force Id" %></td>
		<td ><%= ( !cliente00.getForceId().equals("") ) ? cliente00.getPersona().getNombreCompleto() : "" %></td>
	</tr>
	<% if ( !cliente00.getForceId().equals("") ) { %>
	<tr>
		<td><%= cliente00.getCreditos("0").getBanco() %></td>
		<td>
			<%= fv.formatMoney(cliente00.getCreditos("0").getImporteSolicitado()) %>
			<%= formUtils00.displayLightBulb(cliente00.getCreditos("0").getDecreto(), "15") %>
		</td>
	</tr>
	<% } %>
	<tr>
		<td>Fase</td>
		<td><%= formUtils00.getFilterCatalogueObject("status",cliente00.getIdStatus()).getCampo("fase") %></td>
	</tr>
	<% if (!cliente00.getFechas("fase").equals("")) { %>
	<tr>
		<td>Fecha Fase</td>
		<td><%= fv.formatTimestamp( cliente00.getFechas("fase")) %></td>
	</tr>
	<% } %>
	<tr>
		<td>Status</td>
		<td><%= formUtils00.getFilterCatalogueObject("status",cliente00.getIdStatus()).getNombre() %></td>
	</tr>
	<% if (!cliente00.getFechas("status").equals("")) { %>
	<tr>
		<td>Fecha Status</td>
		<td><%= new force.eai.mx.tools.FormatValues().formatTimestamp( cliente00.getFechas("status")) %></td>
	</tr>
	<% } %>
	<% if (!cliente00.getForceId().equals("")) { %>
	<tr>
		<td>Asignado a</td>
		<td><%= fv.titleCase(cliente00.getUsuario().getNombreCompleto() ) %></td>
	</tr>
	<tr>
		<td colspan="2" style="height:15px"></td>
	</tr>
	<% } %>
	<% if (!cliente00.getFechas("creacion").equals("")) { %>
	<tr>
		<td>Creado por</td>
		<td><%= fv.titleCase(cliente00.getUsuarioCreacion().getNombre() + " " + cliente00.getUsuarioCreacion().getApellidoPaterno() ) %></td>
	</tr>
	<tr>
		<td>Fecha de Creaci&oacute;n</td>
		<td><%= new force.eai.mx.tools.FormatValues().formatTimestamp( cliente00.getFechas("creacion")) %></td>
	</tr>
	<% } %>
	<% if (!cliente00.getFechas("modificacion").equals("")) { %>
	<tr>
		<td>Modificado por</td>
		<td><%= fv.titleCase(cliente00.getUsuarioModificacion().getNombre() + " " + cliente00.getUsuarioModificacion().getApellidoPaterno() ) %></td>
	</tr>
	<tr>
		<td>Fecha de Modificaci&oacute;n</td>
		<td><%= new force.eai.mx.tools.FormatValues().formatTimestamp( cliente00.getFechas("modificacion")) %></td>
	</tr>
	<% } %>
</table>