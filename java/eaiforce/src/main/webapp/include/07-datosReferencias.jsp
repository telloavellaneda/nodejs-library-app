<%
force.eai.mx.util.Cliente cliente06Ref = ( session.getAttribute("cliente") != null ) ? (force.eai.mx.util.Cliente)session.getAttribute("cliente") : new force.eai.mx.util.Cliente();
force.eai.mx.form.FormUtils fUtil06Ref = new force.eai.mx.form.FormUtils(session);
force.eai.mx.form.FormElements fElement06Ref = new force.eai.mx.form.FormElements();

fElement06Ref.setPreffix("ref_");

force.eai.mx.util.Persona persona06Ref = new force.eai.mx.util.Persona();

if ( cliente06Ref.getTipo().equals("2") ) {
	persona06Ref = cliente06Ref.getApoderado();
} else {
	persona06Ref = cliente06Ref.getPersona();
}
%>

<table>
<% for ( int i = 1; i < 4; i ++ ) { %>
	<%
	// PARENTESCO
	fElement06Ref.initControl();
	fElement06Ref.getControl().setId("parentesco_" + i);
	fElement06Ref.getControl().setValue(persona06Ref.getReferencias(i+"").getParentesco());
	fElement06Ref.getControl().setArrayOptions(fUtil06Ref.getFormCatalogue("parentesco"));
	fElement06Ref.getControl().setMaxLength("40");
	%>
	<%= fElement06Ref.createSelectWithOtherElement("Parentesco") %>
	<tr>
		<td class="form-label">Apellido Paterno</td>
		<td class="form-value">
			<%
			fElement06Ref.initControl();
			fElement06Ref.getControl().setType("name");
			fElement06Ref.getControl().setId("apellido_paterno_" + i);
			fElement06Ref.getControl().setValue(persona06Ref.getReferencias(i+"").getApellidoPaterno());
			fElement06Ref.getControl().setMaxLength("100");
			%>
			<%= fElement06Ref.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Apellido Materno</td>
		<td class="form-value">
			<%
			fElement06Ref.initControl();
			fElement06Ref.getControl().setType("name");
			fElement06Ref.getControl().setId("apellido_materno_" + i);
			fElement06Ref.getControl().setValue(persona06Ref.getReferencias(i+"").getApellidoMaterno());
			fElement06Ref.getControl().setMaxLength("100");
			%>
			<%= fElement06Ref.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Primer Nombre</td>
		<td class="form-value">
			<%
			fElement06Ref.initControl();
			fElement06Ref.getControl().setType("name");
			fElement06Ref.getControl().setId("primer_nombre_" + i);
			fElement06Ref.getControl().setValue(persona06Ref.getReferencias(i+"").getNombre());
			fElement06Ref.getControl().setMaxLength("50");
			%>
			<%= fElement06Ref.createInputElement() %>	
		</td>
	</tr>
	<tr>
		<td class="form-label">Segundo Nombre</td>
		<td class="form-value">
			<%
			fElement06Ref.initControl();
			fElement06Ref.getControl().setType("name");
			fElement06Ref.getControl().setId("segundo_nombre_" + i);
			fElement06Ref.getControl().setValue(persona06Ref.getReferencias(i+"").getSegundoNombre());
			fElement06Ref.getControl().setMaxLength("50");
			%>
			<%= fElement06Ref.createInputElement() %>
		</td>
	</tr>
	<tr>
		<td class="form-label">Teléfono</td>
		<td class="form-value">
			<%
			fElement06Ref.initControl();
			fElement06Ref.getControl().setType("phone");
			fElement06Ref.getControl().setId("tel_lada_" + i);
			fElement06Ref.getControl().setValue(persona06Ref.getReferencias(i+"").getLada());
			fElement06Ref.getControl().setClassName("digits-03");
			fElement06Ref.getControl().setMaxLength("3");
			%>
			<%= fElement06Ref.createInputElement() %>
			Lada
			<%
			fElement06Ref.initControl();
			fElement06Ref.getControl().setType("phone");
			fElement06Ref.getControl().setId("tel_numero_" + i);
			fElement06Ref.getControl().setValue(persona06Ref.getReferencias(i+"").getNumero());
			fElement06Ref.getControl().setClassName("digits-08");
			fElement06Ref.getControl().setMaxLength("8");
			%>
			<%= fElement06Ref.createInputElement() %>
			N&uacute;mero
		</td>
	</tr>
	<tr>
		<td colspan="2" style="height:20px"></td>
	</tr>	
<% } %>
</table>