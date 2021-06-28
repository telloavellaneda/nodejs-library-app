<% 
force.eai.mx.form.FormUtils fu = new force.eai.mx.form.FormUtils(session);
force.eai.mx.tools.FormatValues fv = new force.eai.mx.tools.FormatValues();
force.eai.mx.util.Pager pager = (force.eai.mx.util.Pager)session.getAttribute("pager");

java.util.LinkedHashMap<String,java.util.LinkedHashMap<String,String>> dataSet = fu.getDashboardSet("dsClientes");
double tmpFrom = (pager.getClPages().size() != 0) ? pager.getClFrom() + 1 : pager.getClFrom();
double tmpPage = (pager.getClPages().size() != 0) ? pager.getClCurrent() + 1 : pager.getClCurrent();
%>
<table>
	<tr>
		<td>
			<div class="main-div main-div-height">
				<table>
					<tr class="main-header main-desktop">
						<td>Id</td>
						<td>Nombre</td>
						<td>Solic.</td>
						<td>Banco</td>
						<td>Status</td>
					</tr>
					<%
					int contador = 0;
					if ( dataSet != null ) {
						for (java.util.Iterator<java.util.LinkedHashMap<String,String>> iterator = dataSet.values().iterator(); iterator.hasNext(); ) {
							java.util.LinkedHashMap<String,String> row = iterator.next();
							if ( row.get("FORCE_ID") == null )
								continue;
							
							String evenRowClass = ( (contador % 2) == 0 ) ? "main-row-blue" : "main-row-white";
							%>
							<tr class="main-desktop main-row <%= evenRowClass %>" style="cursor:pointer" onClick="q('<%= row.get("FORCE_ID") %>');">
								<td><%= row.get("FORCE_ID") %></td>
								<td id="bold"><%= row.get("NOMBRE") %></td>
								<td>
									<span><%= row.get("IMPORTE_SOLIC") %></span>
									<%= fu.displayLightBulb(row.get("DECRETO"),"") %>
								</td>
								<td id="bold"><%= row.get("BANCO") %></td>
								<td title="<%= row.get("NOMBRE_STATUS") %>"><%= new force.eai.mx.tools.FormatValues().shorten(row.get("NOMBRE_STATUS"),20) %></td>
							</tr>
							<tr class="main-mobile main-row <%= evenRowClass %>" style="cursor:pointer" onClick="q('<%= row.get("FORCE_ID") %>');">
								<td colspan="5">
									<table>
										<tr>
											<td>Id</td>
											<td id="bold"><%= row.get("FORCE_ID") %></td>
										</tr>
										<tr>
											<td>Nombre</td>
											<td id="bold"><%= row.get("NOMBRE") %></td>
										</tr>
										<tr>
											<td>Monto</td>
											<td id="bold">
												<span><%= row.get("IMPORTE_SOLIC") %></span>
												<%= fu.displayLightBulb(row.get("DECRETO"),"") %>
											</td>
										</tr>
										<tr>
											<td>Banco</td>
											<td id="bold"><%= row.get("BANCO") %></td>
										</tr>
										<tr>
											<td>Fecha</td>
											<td id="bold"><%= row.get("NOMBRE_STATUS") %></td>
										</tr>
									</table>
								</td>
							</tr>
							<%
						contador++;
						}
					}
					%>
				</table>
			</div>
		</td>
	</tr>
	<tr>
		<td class="footer-box">
			<table>
				<tr>
					<td>
						<span><%= fv.formatMoneyCompact(tmpFrom) + " - " + fv.formatMoneyCompact(pager.getClTo()) +
						" (" + fv.formatMoneyCompact(tmpPage) + "/" + fv.formatMoneyCompact(pager.getClPages().size()) + ")" %></span>
					</td>
				</tr>
				<tr>
					<td>
						<span><%= "Clientes: " + fv.formatMoneyCompact(pager.getClTotal()) %></span>
						<span><%= "Solicitado: " + fv.formatMoney(pager.getClSolic()) %></span>
						<span><%= "Aprobado: " + fv.formatMoney(pager.getClAprob()) %></span>
						<span><%= "Firmado: " + fv.formatMoney(pager.getClFinal()) %></span>					
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>