<% 
force.eai.mx.util.Force forceMenu = (force.eai.mx.util.Force)session.getAttribute("force");
force.eai.mx.tools.FormatValues fvMenu = new force.eai.mx.tools.FormatValues();
%>

<table class="width-1024 menu-bar shadow">
	<tr>
		<td class="title">EAI Force</td>
		<td class="menu">
			<table>
				<tr>
					<td class="b" onClick="main();">
						<img src="images/b-home.png"/>
					</td>
					<td class="a" onClick="location.href='invalidateSession.jsp'">
						<img src="images/b-session.png"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table class="width-1024 menu-welcome">
	<tr>
		<td>
			<span>Bienvenido</span>
			<span><%= fvMenu.titleCase(forceMenu.getUsuario("0").getNombre() + " " + forceMenu.getUsuario("0").getApellidoPaterno()) %></span>
		</td>
		<td>
			<span>&Uacute;ltimo Ingreso</span>
			<span><%= fvMenu.formatTimestamp(forceMenu.getUsuario("0").getFechaIngreso()) %></span>		
		</td>
	</tr>
</table>