<% 
force.eai.mx.form.FormUtils fu = new force.eai.mx.form.FormUtils(session);
force.eai.mx.tools.FormatValues fv = new force.eai.mx.tools.FormatValues();
force.eai.mx.util.Pager pager = (force.eai.mx.util.Pager)session.getAttribute("pager");

java.util.LinkedHashMap<String,java.util.LinkedHashMap<String,String>> dataSet = fu.getDashboardSet("dsProspectos");
double tmpFrom = (pager.getPrPages().size() != 0) ? pager.getPrFrom() + 1 : pager.getPrFrom();
double tmpPage = (pager.getPrPages().size() != 0) ? pager.getPrCurrent() + 1 : pager.getPrCurrent();
%>
<table>
	<tr>
		<td>
			<div class="main-div main-div-height">
				<table>
					<tr class="main-header main-desktop">
						<td>Id</td>
						<td>Nombre</td>
						<td>Monto</td>
						<td>Banco</td>
						<td>Fecha</td>
					</tr>
					<%
					int contador = 0;
					if ( dataSet != null ) {
						for (java.util.Iterator<java.util.LinkedHashMap<String,String>> iterator = dataSet.values().iterator(); iterator.hasNext(); ) {
							java.util.LinkedHashMap<String,String> row = iterator.next();
							if ( row.get("FORCE_ID") == null )
								continue;
							
							String evenRowClass = ( (contador % 2) == 0 ) ? "main-row-yellow" : "main-row-white";
							%>
						<tr class="main-desktop main-row <%= evenRowClass %>" style="cursor:pointer" onClick="q('<%= row.get("FORCE_ID") %>');">
							<td><%= row.get("FORCE_ID") %></td>
							<td id="bold"><%= row.get("NOMBRE") %></td>
							<td>
								<span><%= row.get("IMPORTE_SOLIC") %></span>
								<%= fu.displayLightBulb(row.get("DECRETO"),"") %>
							</td>
							<td id="bold"><%= row.get("BANCO") %></td>
							<td><%= row.get("FECHA_CREACION") %></td>
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
										<td id="bold"><%= row.get("FECHA_CREACION") %></td>
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
						<span><%= fv.formatMoneyCompact(tmpFrom) + " - " + fv.formatMoneyCompact(pager.getPrTo()) +
						" (" + fv.formatMoneyCompact(tmpPage) + "/" + fv.formatMoneyCompact(pager.getPrPages().size()) + ")" %></span>
					</td>
				</tr>
				<tr>
					<td>
						<span><%= "Clientes: " + fv.formatMoneyCompact(pager.getPrTotal()) + "." %></span>
						<span><%= "Solicitado: " + fv.formatMoney(pager.getPrSolic()) + "." %></span>
					</td>
				</tr>
			</table>		
		</td>
	</tr>	
</table>