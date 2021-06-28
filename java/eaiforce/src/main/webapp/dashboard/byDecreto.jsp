<% 
force.eai.mx.form.FormUtils fu = new force.eai.mx.form.FormUtils(session);
force.eai.mx.tools.FormatValues fv = new force.eai.mx.tools.FormatValues();

java.util.LinkedHashMap<String,java.util.LinkedHashMap<String,String>> dataSet = fu.getDashboardSet("dsByDecreto");

String[][] decretos = new String[][] {
	{"Aprobados", "APROBADO"},
	{"Rechazados", "RECHAZADO"},
	{"Condic.", "CONDICIONADO"},
	{"S/Decreto", "SIN_DECRETO"}
};
%>

<table class="decreto">
	<tr class="widget-desktop">
		<td>Banco</td>
		<td colspan="3" id="bold">Aprobados</td>
		<td colspan="3">Condicionados</td>
		<td colspan="2" id="bold">Rechazados</td>
		<td colspan="2">Sin Decreto</td>
	</tr>
	<tr class="widget-desktop">
		<td></td>
		<td id="bold">#</td>
		<td id="bold">SOLIC</td>
		<td id="bold">FINAL</td>
		<td>#</td>
		<td>SOLIC</td>
		<td>FINAL</td>
		<td id="bold">#</td>
		<td id="bold">SOLIC</td>
		<td>#</td>
		<td>SOLIC</td>
	</tr>
	<%
	double[] sum = new double[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	int contador = 0;
	if ( dataSet != null ) {
		for ( java.util.Iterator<String> iterator = dataSet.keySet().iterator(); iterator.hasNext();) {			
			String idBanco = (String)iterator.next();
			if ( idBanco.equals("TOTAL") )
				continue;
			
			java.util.LinkedHashMap<String,String> temp = dataSet.get(idBanco);
			String evenOddClass = ( (contador % 2) == 0 ) ? "widget-row-odd" : "widget-row-even";
			String evenOddDivider = ( (contador % 2) == 0 ) ? "widget-row-odd-divider" : "widget-row-even-divider";
			
			sum[0] += fu.getSplittedValue(temp.get("APROBADO"), 0);
			sum[1] += fu.getSplittedValue(temp.get("APROBADO"), 1);
			sum[2] += fu.getSplittedValue(temp.get("APROBADO"), 2);

			sum[3] += fu.getSplittedValue(temp.get("CONDICIONADO"), 0);
			sum[4] += fu.getSplittedValue(temp.get("CONDICIONADO"), 1);
			sum[5] += fu.getSplittedValue(temp.get("CONDICIONADO"), 2);

			sum[6] += fu.getSplittedValue(temp.get("RECHAZADO"), 0);
			sum[7] += fu.getSplittedValue(temp.get("RECHAZADO"), 1);

			sum[8] += fu.getSplittedValue(temp.get("SIN_DECRETO"), 0);
			sum[9] += fu.getSplittedValue(temp.get("SIN_DECRETO"), 1);
			%>
			<tr class="widget-desktop <%= evenOddClass %>">
				<td><%= fu.splitKey(idBanco,1) %></td>
				<td id="bold"><%= fv.formatMoneyCompact(fu.getSplittedValue(temp.get("APROBADO"), 0)) %></td>
				<td id="bold"><%= fv.formatMoney(fu.getSplittedValue(temp.get("APROBADO"), 1)) %></td>
				<td id="bold"><%= fv.formatMoney(fu.getSplittedValue(temp.get("APROBADO"), 2)) %></td>
				<td><%= fv.formatMoneyCompact(fu.getSplittedValue(temp.get("CONDICIONADO"), 0)) %></td>
				<td><%= fv.formatMoney(fu.getSplittedValue(temp.get("CONDICIONADO"), 1)) %></td>
				<td><%= fv.formatMoney(fu.getSplittedValue(temp.get("CONDICIONADO"), 2)) %></td>
				<td id="bold"><%= fv.formatMoneyCompact(fu.getSplittedValue(temp.get("RECHAZADO"), 0)) %></td>
				<td id="bold"><%= fv.formatMoney(fu.getSplittedValue(temp.get("RECHAZADO"), 1)) %></td>
				<td><%= fv.formatMoneyCompact(fu.getSplittedValue(temp.get("SIN_DECRETO"), 0)) %></td>
				<td><%= fv.formatMoney(fu.getSplittedValue(temp.get("SIN_DECRETO"), 1)) %></td>
			</tr>
			<tr class="widget-mobile <%= evenOddClass %>">
				<td colspan="11">
					<table>
						<tr>
							<td></td>
							<td id="bold">
								<%= fu.displayBankLogo(fu.splitKey(idBanco,0), fu.splitKey(idBanco,1), "25") %>
								<span><%= fu.splitKey(idBanco,1) %></span>
							</td>
						</tr>
						<% for (int i = 0; i < decretos.length; i++) { 
								if ( fu.getSplittedValue(temp.get( decretos[i][1] ), 0) != 0 ) {
								%>
								<tr>
									<td><%= decretos[i][0] %></td>
									<td id="bold">
										<span>Clientes</span>
										<%= fv.formatMoneyCompact(fu.getSplittedValue(temp.get( decretos[i][1] ), 0)) %>
									</td>
								</tr>
								<tr>
									<td></td>
									<td id="bold">
										<span>Solicitado</span>
										<%= fv.formatMoney(fu.getSplittedValue(temp.get( decretos[i][1] ), 1)) %>
									</td>
								</tr>
								<tr class="<%= evenOddDivider %>">
									<td></td>
									<td id="bold">
										<span>Firmado</span>
										<%= fv.formatMoney(fu.getSplittedValue(temp.get( decretos[i][1] ), 2)) %>
									</td>
								</tr>
								<% 
								}
						}
						%>
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
			<td id="bold"><%= fv.formatMoneyCompact(sum[0]) %></td>
			<td id="bold"><%= fv.formatMoney(sum[1]) %></td>
			<td id="bold"><%= fv.formatMoney(sum[2]) %></td>
			<td><%= fv.formatMoneyCompact(sum[3]) %></td>
			<td><%= fv.formatMoney(sum[4]) %></td>
			<td><%= fv.formatMoney(sum[5]) %></td>
			<td id="bold"><%= fv.formatMoneyCompact(sum[6]) %></td>
			<td id="bold"><%= fv.formatMoney(sum[7]) %></td>
			<td><%= fv.formatMoneyCompact(sum[8]) %></td>
			<td><%= fv.formatMoney(sum[9]) %></td>
		</tr>
</table>