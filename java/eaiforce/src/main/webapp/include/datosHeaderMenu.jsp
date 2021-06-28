<%
force.eai.mx.util.Force forceHeaderMenu = ( session.getAttribute("force") != null ) ? (force.eai.mx.util.Force)session.getAttribute("force") : new force.eai.mx.util.Force() ;
force.eai.mx.util.Cliente clienteHeaderMenu = ( session.getAttribute("cliente") != null ) ? (force.eai.mx.util.Cliente)session.getAttribute("cliente") : new force.eai.mx.util.Cliente() ;

String sectionHeaderMenu = (String) session.getAttribute("section");
String atrasDisplay = "";
String siguienteDisplay = "";

String onClickGuardar = "onClick=\"guardar();\"";
String onClickSiguiente = "onClick=\"siguiente();\"";

if ( clienteHeaderMenu.getForceId().equals("") ) {
	atrasDisplay = "display:none;";
	siguienteDisplay = "display:none;";

} else
	if ( sectionHeaderMenu.equals("section0") )
		atrasDisplay = "display:none;";
	else if ( sectionHeaderMenu.equals("section1") )
		atrasDisplay = "display:none;";
	else if ( !clienteHeaderMenu.getCreditos("0").getCoacreditado().equals("") && sectionHeaderMenu.equals("section11") )
		siguienteDisplay = "display:none;";		
	else if ( clienteHeaderMenu.getCreditos("0").getCoacreditado().equals("") && sectionHeaderMenu.equals("section7") )
		siguienteDisplay = "display:none;";		
%>

<table class="form-menu" id="form_header">
	<tr>
		<td width="33%">
			<table id="botonAtras" class="a" onClick="atras();" style="<%= atrasDisplay %>">
				<tr>
					<td><img src="images/b-back.png"/></td>
				</tr>
			</table>
		</td>
		<td width="33%">
			<table id="crear" class="b" <%= onClickGuardar %> tabindex="1">
				<tr>
					<td><img src="images/b-save.png"/></td>
				</tr>
			</table>
		</td>
		<td width="33%">
			<table id="botonSiguiente" class="c" <%= onClickSiguiente %> style="<%= siguienteDisplay %>">
				<tr>
					<td><img src="images/b-next.png"/></td>
				</tr>
			</table>
		</td>
	</tr>
</table>