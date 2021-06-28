<% 
force.eai.mx.util.Cliente cliente10 = (force.eai.mx.util.Cliente)session.getAttribute("cliente"); 
force.eai.mx.security.SecurityUtils su = new force.eai.mx.security.SecurityUtils();
%>
<table>
	<tr>
		<td>
			<div class="form-div-documentos">
				<table class="documentos">
					<%
					for (java.util.Iterator<force.eai.mx.util.Documento> iterator10 = cliente10.getDocumentos().values().iterator(); iterator10.hasNext(); ) {
						force.eai.mx.util.Documento documento = iterator10.next();
						%>
						<tr>
							<td class="form-label"><%= documento.getNombreDocumento() %></td>
							<td class="form-value">
								<div>
									<iframe class="contenedor" src="include/uploadForm.jsp?x=<%= su.encode64(documento.getIdDocumento().getBytes()) %>" scrolling="no"></iframe>
								</div>
							</td>
						</tr>
						<%
					}
					%>
				</table>		
			</div>
		</td>
	</tr>
	<tr>
		<td class="footer-box">
		
		</td>
	</tr>
</table>
