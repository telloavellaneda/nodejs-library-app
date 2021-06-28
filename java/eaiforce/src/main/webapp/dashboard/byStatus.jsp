<% 
force.eai.mx.form.FormUtils fu = new force.eai.mx.form.FormUtils(session);

java.util.LinkedHashMap<String,java.util.LinkedHashMap<String,String>> dataSet = fu.getDashboardSet("dsByStatus");
%>
<table class="widget">
	<tr class="widget-desktop">
		<td>Status</td>
		<td id="bold">Clientes</td>
		<td>Solicitado</td>
		<td id="bold">Aprobado</td>
		<td>Firmado</td>
	</tr>
	<%		
	int contador = 0;
	if ( dataSet != null ) {
		for (java.util.Iterator<java.util.LinkedHashMap<String,String>> iterator = dataSet.values().iterator(); iterator.hasNext(); ) {
			java.util.LinkedHashMap<String,String> row = iterator.next();
			if ( row.get("STATUS") == null )
				continue;
			
			String evenOddClass = ( (contador % 2) == 0 ) ? "widget-row-odd" : "widget-row-even";
			%>
			<tr class="widget-desktop <%= evenOddClass %>" title="<%= "[" + row.get("FASE") + "] " + row.get("STATUS") %>">
				<td><%= new force.eai.mx.tools.FormatValues().shorten(row.get("STATUS"),40) %></td>
				<td id="bold"><%= row.get("CLIENTES") %></td>
				<td><%= row.get("MONTO") %></td>
				<td id="bold"><%= row.get("MONTO_APROBADO") %></td>
				<td><%= row.get("MONTO_FINAL") %></td>
			</tr>
			<tr class="widget-mobile wg-row <%= evenOddClass %>">
				<td colspan="4">
					<table>
						<tr>
							<td>Status</td>
							<td id="bold"><%= row.get("STATUS") %></td>
						</tr>
						<tr>
							<td>Clientes</td>
							<td id="bold"><%= row.get("CLIENTES") %></td>
						</tr>
						<tr>
							<td>Solicitado</td>
							<td id="bold"><%= row.get("MONTO") %></td>
						</tr>
						<tr>
							<td>Aprobado</td>
							<td id="bold"><%= row.get("MONTO_APROBADO") %></td>
						</tr>
						<tr>
							<td>Firmado</td>
							<td id="bold"><%= row.get("MONTO_FINAL") %></td>
						</tr>
					</table>
				</td>
			</tr>
			<%
			contador++;
		}
	}	
	%>
	<tr class="widget-desktop">
		<td></td>
		<td id="bold"><%= dataSet.get("TOTAL").get("CLIENTES") %></td>
		<td><%= dataSet.get("TOTAL").get("MONTO") %></td>
		<td id="bold"><%= dataSet.get("TOTAL").get("MONTO_APROBADO") %></td>
		<td><%= dataSet.get("TOTAL").get("MONTO_FINAL") %></td>
	</tr>
	<tr class="widget-mobile">
		<td colspan="5">
			<table>
				<tr>
					<td></td>
					<td id="bold">TOTAL</td>
				</tr>
				<tr>
					<td>Clientes</td>
					<td id="bold"><%= dataSet.get("TOTAL").get("CLIENTES") %></td>
				</tr>
				<tr>
					<td>Solicitado</td>
					<td id="bold"><%= dataSet.get("TOTAL").get("MONTO") %></td>
				</tr>
				<tr>
					<td>Aprobado</td>
					<td id="bold"><%= dataSet.get("TOTAL").get("MONTO_APROBADO") %></td>
				</tr>
				<tr>
					<td>Firmado</td>
					<td id="bold"><%= dataSet.get("TOTAL").get("MONTO_FINAL") %></td>
				</tr>
			</table>
		</td>
	</tr>
</table>