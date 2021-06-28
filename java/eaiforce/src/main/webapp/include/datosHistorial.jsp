<%
force.eai.mx.util.Cliente cliente = ( session.getAttribute("cliente") != null ) ? (force.eai.mx.util.Cliente)session.getAttribute("cliente") : new force.eai.mx.util.Cliente() ;
force.eai.mx.tools.FormatValues fv = new force.eai.mx.tools.FormatValues();
%>
<table>
	<tr>
		<td>
			<table class="historial historial-title">
				<tr class="desktop">
					<td id="bold">Fase</td>
					<td>Status</td>
					<td id="bold">Usuario</td>
					<td>Fecha</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<div class="form-div-historial">
				<table class="historial">
				<%
				int contador = 0;
				for (java.util.Iterator<force.eai.mx.util.Status> iterator = cliente.getHistorial().values().iterator(); iterator.hasNext();) {
					force.eai.mx.util.Status status = iterator.next();
					String evenOddClass = ( (contador % 2) != 0 ) ? "odd" : "even";	
					%>
					<tr class="desktop <%= evenOddClass %>">
						<td id="bold"><%= status.getFase() %></td>
						<td title="<%= status.getStatus() %>"><%= fv.shorten(status.getStatus(),20) %></td>
						<td id="bold"><%= status.getUsuario() %></td>
						<td><%= fv.formatTimestamp(status.getFecha()) %></td>
					</tr>
					<tr class="mobile <%= evenOddClass %>">
						<td colspan="4">
							<table>
								<tr>
									<td>Fase</td>
									<td id="bold"><%= status.getFase() %></td>
								</tr>
								<tr>
									<td>Status</td>
									<td id="bold"><%= status.getStatus() %></td>
								</tr>
								<tr>
									<td>Usuario</td>
									<td id="bold"><%= status.getUsuario() %></td>
								</tr>
								<tr>
									<td>Fecha</td>
									<td id="bold"><%= fv.formatTimestamp(status.getFecha()) %></td>
								</tr>
							</table>
						</td>
					</tr>
					<%
					contador++;
				}
				%>
				</table>
			</div>
		</td>
	</tr>
	<tr>
		<td colspan="4" class="footer-box">
			<span><%= "Registros: " + contador + "." %></span>
		</td>
	</tr>
</table>
