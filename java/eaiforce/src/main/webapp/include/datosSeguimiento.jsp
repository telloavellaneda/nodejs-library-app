<% force.eai.mx.util.Cliente cliente20 = ( session.getAttribute("cliente") != null ) ? (force.eai.mx.util.Cliente)session.getAttribute("cliente") : new force.eai.mx.util.Cliente(); %>
<table>
	<tr>
		<td>
			<div class="form-div-seguimiento">
				<table>
					<%
					force.eai.mx.tools.FormatValues formatValues08 = new force.eai.mx.tools.FormatValues();
					Object[] seguimientoArray = cliente20.getSeguimiento().values().toArray();
					
					for ( int i = seguimientoArray.length-1; i>=0 ; i-- ) {
						force.eai.mx.util.Seguimiento seguimiento = (force.eai.mx.util.Seguimiento) seguimientoArray[i];
						String evenRowClass = ( (i % 2) == 0 ) ? "seguimiento-odd" : "seguimiento-even";
					%>
					<tr class="seguimiento-row <%= evenRowClass %>">
						<td colspan="2" class="align-left">
							<span>
								<%= formatValues08.titleCase( seguimiento.getUsuario().getNombre() + " " + seguimiento.getUsuario().getApellidoPaterno() ) %>
							</span>
							<span>
								<%= formatValues08.prettyFormat(seguimiento.getMensaje()) %>
							</span>
						</td>
					</tr>
					<tr class="seguimiento-row <%= evenRowClass %>">
						<td class="align-left" width="50%">
							<span style="color:#0082ca; font-weight:normal"><%= formatValues08.formatTimestamp(seguimiento.getFechaCreacion()) %></span>
						</td>
						<td width="50%" class="a" onClick="notify('<%= seguimiento.getId() %>');"><img src="../images/b-mail.png"></td>
					</tr>
					<%
					}
					%>
				</table>
			</div>
		</td>
	</tr>
	<tr>
		<td class="footer-box"><span><%= seguimientoArray.length + " Mensajes." %></span></td>
	</tr>	
</table>
