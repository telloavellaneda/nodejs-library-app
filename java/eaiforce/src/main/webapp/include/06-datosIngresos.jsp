<%
force.eai.mx.util.Cliente cliente = ( session.getAttribute("cliente") != null ) ? (force.eai.mx.util.Cliente)session.getAttribute("cliente") : new force.eai.mx.util.Cliente() ;
force.eai.mx.form.FormUtils fu = new force.eai.mx.form.FormUtils(session);
force.eai.mx.form.FormElements fe = new force.eai.mx.form.FormElements();
fu.setCliente(cliente);

fe.setPreffix("ing_");

force.eai.mx.util.Persona persona05 = new force.eai.mx.util.Persona();

if ( cliente.getTipo().equals("2") )
	persona05 = cliente.getApoderado();
else
	persona05 = cliente.getPersona();
%>

<% 

%>
<table style="margin-bottom:20px">
	<tr>
		<td class="form-label">Ingreso Bruto</td>
		<td class="form-value">
			<%
			fe.initControl();
			fe.getControl().setType("currency");
			fe.getControl().setId("ingresos_bruto");
			fe.getControl().setValue(new force.eai.mx.tools.FormatValues().formatMoney(persona05.getEmpleos(persona05.getIdPersona()).getIngresosBruto()));
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
			fe.getControl().setValue(new force.eai.mx.tools.FormatValues().formatMoney(persona05.getEmpleos(persona05.getIdPersona()).getIngresosNeto()));
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
			fe.getControl().setValue(new force.eai.mx.tools.FormatValues().formatMoney(persona05.getEmpleos(persona05.getIdPersona()).getOtrosIngresos()));
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
					persona05.getEmpleos(persona05.getIdPersona()).getIngresosBruto(), 
					persona05.getEmpleos(persona05.getIdPersona()).getOtrosIngresos() 
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
	fe.getControl().setValue( persona05.getEmpleos(persona05.getIdPersona()).getFuenteIngresos() );
	fe.getControl().setArrayOptions(fu.getFormCatalogue("fuenteIngresos"));
	fe.getControl().setMaxLength("20");
	%>
	<%= fe.createSelectWithOtherElement("Fuente de Ingresos") %>
</table>