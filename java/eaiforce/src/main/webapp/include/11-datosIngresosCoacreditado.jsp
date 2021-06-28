<%
force.eai.mx.util.Cliente cliente = ( session.getAttribute("cliente") != null ) ? (force.eai.mx.util.Cliente)session.getAttribute("cliente") : new force.eai.mx.util.Cliente() ;
force.eai.mx.form.FormUtils fu = new force.eai.mx.form.FormUtils(session);
force.eai.mx.form.FormElements fe = new force.eai.mx.form.FormElements();
fu.setCliente(cliente);

fe.setPreffix("coa_ing_");

force.eai.mx.util.Persona persona = cliente.getCoacreditado("0");
%>

<table style="margin-bottom:20px;">
	<tr>
		<td class="form-label">Ingreso Bruto</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("currency");
			fe.getControl().setId("ingresos_bruto");
			fe.getControl().setValue(new force.eai.mx.tools.FormatValues().formatMoney(persona.getEmpleos(persona.getIdPersona()).getIngresosBruto()));
			fe.getControl().setMaxLength("10");
			fe.getControl().setPrecision("2");
			fe.getControl().setOnBlur("sumIngresos();");
			%>
			<%= fe.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label">Ingreso Neto</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("currency");
			fe.getControl().setId("ingresos_neto");
			fe.getControl().setValue(new force.eai.mx.tools.FormatValues().formatMoney(persona.getEmpleos(persona.getIdPersona()).getIngresosNeto()));
			fe.getControl().setMaxLength("10");
			fe.getControl().setPrecision("2");
			%>
			<%= fe.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label">Otros Ingresos</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("currency");
			fe.getControl().setId("otros_ingresos");
			fe.getControl().setValue(new force.eai.mx.tools.FormatValues().formatMoney(persona.getEmpleos(persona.getIdPersona()).getOtrosIngresos()));
			fe.getControl().setMaxLength("10");
			fe.getControl().setPrecision("2");
			fe.getControl().setOnBlur("sumIngresos();");
			%>
			<%= fe.createInputElement() %>
		</td>            
	</tr>
	<tr>
		<td class="form-label">Total Ingresos</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("currency");
			fe.getControl().setId("total_ingresos");
			fe.getControl().setValue( 
				new force.eai.mx.tools.FormatValues().formatMoney( fu.sumIngresos(
					persona.getEmpleos(persona.getIdPersona()).getIngresosBruto(), 
					persona.getEmpleos(persona.getIdPersona()).getOtrosIngresos() 
				)) 
			);
			fe.getControl().setReadOnly(true);
			%>
			<%= fe.createInputElement() %>
		</td>            
	</tr>
	<%
	// FUENTE DE INGRESOS
	fe.initControl();
	fe.getControl().setId("fuente_ingresos");
	fe.getControl().setValue( persona.getEmpleos(persona.getIdPersona()).getFuenteIngresos() );
	fe.getControl().setArrayOptions(fu.getFormCatalogue("fuenteIngresos"));
	fe.getControl().setMaxLength("20");
	%>
	<%= fe.createSelectWithOtherElement("Fuente de Ingresos") %>
</table>