<% 
force.eai.mx.util.Force forceFilter = (force.eai.mx.util.Force)session.getAttribute("force");
force.eai.mx.form.FormElements fElementFilter = new force.eai.mx.form.FormElements();
force.eai.mx.form.FormUtils filterFU = new force.eai.mx.form.FormUtils(session);

String filterForceId = filterFU.getStringFromSession("filter_force_id");
String filterFullName = filterFU.getStringFromSession("filter_full_name");

String filterUsuario = filterFU.getStringFromSession("filter_user");
String filterUsuarioResponsable = filterFU.getStringFromSession("filter_user_responsable");
String filterFase = filterFU.getStringFromSession("filter_fase");
String filterStatus = filterFU.getStringFromSession("filter_status");
String filterBanco = filterFU.getStringFromSession("filter_banco");
String filterDecreto = filterFU.getStringFromSession("filter_decreto");

String filterYear = filterFU.getStringFromSession("filter_year");
String filterMonth = filterFU.getStringFromSession("filter_month");
String filterYearAut = filterFU.getStringFromSession("filter_year_autorizacion");
String filterMonthAut = filterFU.getStringFromSession("filter_month_autorizacion");
String filterYearFrm = filterFU.getStringFromSession("filter_year_firma");
String filterMonthFrm = filterFU.getStringFromSession("filter_month_firma");

String showFullNameFilter = filterFU.getStringFromSession("showFullNameFilter");
String currentPage = filterFU.getStringFromSession("currentPage");
%>

<table class="width-1024 filter filter-green-border">
	<tr>
		<td>
			<span>Banco</span>
			<%
			fElementFilter.initControl();
			fElementFilter.getControl().setId("banco");
			fElementFilter.getControl().setValue(filterBanco);
			fElementFilter.getControl().setClassName("filtro");
			fElementFilter.getControl().setLabel("TODOS");
			fElementFilter.getControl().setIterator(filterFU.getCatalogue("filterCatalogues", "bancos"));
			fElementFilter.getControl().setOnChange("changeLabel();");
			%>
			<%= fElementFilter.createSelectElement() %>
		</td>
		<td>
			<span>Usuario</span>
			<%
			fElementFilter.initControl();
			fElementFilter.getControl().setId("usuario");
			fElementFilter.getControl().setValue(filterUsuario);
			fElementFilter.getControl().setClassName("filtro");
			fElementFilter.getControl().setLabel("TODOS");
			fElementFilter.getControl().setIterator(filterFU.getCatalogue("filterCatalogues", "usuarios"));
			fElementFilter.getControl().setOnChange("changeLabel();");
			if ( !forceFilter.getUsuario("0").getProfile().isMultiusuario() && !forceFilter.getUsuario("0").getProfile().isOperaciones() ) {
				fElementFilter.getControl().setValue(forceFilter.getUsuario("0").getIdPersona());
				fElementFilter.getControl().setInitValue(false);
				fElementFilter.getControl().setOnlyValue(true);
			}
			%>
			<%= fElementFilter.createSelectElement() %>
		</td>
		<td>
			<span>Responsable</span>
			<%
			fElementFilter.initControl();
			fElementFilter.getControl().setId("responsable");
			fElementFilter.getControl().setValue(filterUsuarioResponsable);
			fElementFilter.getControl().setClassName("filtro");
			fElementFilter.getControl().setLabel("TODOS");
			fElementFilter.getControl().setIterator(filterFU.getCatalogue("filterCatalogues", "usuariosResponsables"));
			fElementFilter.getControl().setOnChange("changeLabel();");
			if ( !forceFilter.getUsuario("0").getProfile().isMultiusuario() ) {
				fElementFilter.getControl().setValue(forceFilter.getUsuario("0").getIdPersona());
				fElementFilter.getControl().setInitValue(false);
				fElementFilter.getControl().setOnlyValue(true);
			}
			%>
			<%= fElementFilter.createSelectElement() %>		
		</td>
	</tr>
	<tr>
		<td>
			<span>Fase</span>
			<%
			fElementFilter.initControl();
			fElementFilter.getControl().setId("fase");
			fElementFilter.getControl().setValue(filterFase);
			fElementFilter.getControl().setClassName("filtro");
			fElementFilter.getControl().setLabel("TODOS");
			fElementFilter.getControl().setIterator(filterFU.getCatalogue("filterCatalogues", "fases"));
			fElementFilter.getControl().setOnChange("changeLabel();");
			%>
			<%= fElementFilter.createSelectElement() %>
		</td>
		<td>
			<span>Status</span>
			<%
			fElementFilter.initControl();
			fElementFilter.getControl().setId("status");
			fElementFilter.getControl().setValue(filterStatus);
			fElementFilter.getControl().setClassName("filtro");
			fElementFilter.getControl().setLabel("TODOS");
			fElementFilter.getControl().setLabelLength(25);
			fElementFilter.getControl().setIterator(filterFU.getCatalogue("filterCatalogues", "status"));
			fElementFilter.getControl().setOnChange("changeLabel();");
			%>
			<%= fElementFilter.createSelectElement() %>
		</td>
		<td>
			<span>Decreto</span>
			<%
			fElementFilter.initControl();
			fElementFilter.getControl().setId("decreto");
			fElementFilter.getControl().setValue(filterDecreto);
			fElementFilter.getControl().setClassName("filtro");
			fElementFilter.getControl().setLabel("TODOS");
			fElementFilter.getControl().setIterator(filterFU.getCatalogue("filterCatalogues", "decreto"));
			fElementFilter.getControl().setOnChange("changeLabel();");
			%>
			<%= fElementFilter.createSelectElement() %>
		</td>
	</tr>	
	<tr>
		<td>
			<span>Expediente</span>
			<table>
				<tr>
					<td>
						<%
						fElementFilter.initControl();
						fElementFilter.getControl().setId("year");
						fElementFilter.getControl().setValue(filterYear);
						fElementFilter.getControl().setClassName("filtro");
						fElementFilter.getControl().setLabel("A&Ntilde;OS");
						fElementFilter.getControl().setIterator(filterFU.getCatalogue("filterCatalogues", "expediente"));
						fElementFilter.getControl().setOnChange("changeLabel();");
						%>
						<%= fElementFilter.createSelectElement() %>
					</td>
					<td>
						<%
						fElementFilter.initControl();
						fElementFilter.getControl().setId("month");
						fElementFilter.getControl().setValue(filterMonth);
						fElementFilter.getControl().setLabel("MESES");
						fElementFilter.getControl().setClassName("filtro");
						fElementFilter.getControl().setIterator(filterFU.getCatalogue("filterCatalogues", "months"));
						fElementFilter.getControl().setOnChange("changeLabel();");
						%>
						<%= fElementFilter.createSelectElement() %>
					</td>
				</tr>
			</table>
		</td>
		<td>
			<span>Autorizacion</span>
			<table>
				<tr>
					<td>
						<%
						fElementFilter.initControl();
						fElementFilter.getControl().setId("year_autorizacion");
						fElementFilter.getControl().setValue(filterYearAut);
						fElementFilter.getControl().setClassName("filtro");
						fElementFilter.getControl().setLabel("A&Ntilde;OS");
						fElementFilter.getControl().setIterator(filterFU.getCatalogue("filterCatalogues", "autorizacion"));
						fElementFilter.getControl().setOnChange("changeLabel();");
						%>
						<%= fElementFilter.createSelectElement() %>
					</td>
					<td>
						<%
						fElementFilter.initControl();
						fElementFilter.getControl().setId("month_autorizacion");
						fElementFilter.getControl().setValue(filterMonthAut);
						fElementFilter.getControl().setClassName("filtro");
						fElementFilter.getControl().setLabel("MESES");
						fElementFilter.getControl().setIterator(filterFU.getCatalogue("filterCatalogues", "months"));
						fElementFilter.getControl().setOnChange("changeLabel();");
						%>
						<%= fElementFilter.createSelectElement() %>			
					</td>
				</tr>
			</table>
		</td>
		<td>
			<span>Firma</span>
			<table>
				<tr>
					<td>
						<%
						fElementFilter.initControl();
						fElementFilter.getControl().setId("year_firma");
						fElementFilter.getControl().setValue(filterYearFrm);
						fElementFilter.getControl().setClassName("filtro");
						fElementFilter.getControl().setLabel("A&Ntilde;OS");
						fElementFilter.getControl().setIterator(filterFU.getCatalogue("filterCatalogues", "firma"));
						fElementFilter.getControl().setOnChange("changeLabel();");
						%>
						<%= fElementFilter.createSelectElement() %>
					</td>
					<td>
						<%
						fElementFilter.initControl();
						fElementFilter.getControl().setId("month_firma");
						fElementFilter.getControl().setValue(filterMonthFrm);
						fElementFilter.getControl().setClassName("filtro");
						fElementFilter.getControl().setLabel("MESES");
						fElementFilter.getControl().setIterator(filterFU.getCatalogue("filterCatalogues", "months"));
						fElementFilter.getControl().setOnChange("changeLabel();");
						%>
						<%= fElementFilter.createSelectElement() %>			
					</td>
				</tr>
			</table>

		</td>
	</tr>
	<% if ( session.getAttribute("showFullNameFilter") == null ) { %>
	<tr>
		<td colspan="2">
			<span>Nombre</span>
			<%
			fElementFilter.initControl();
			fElementFilter.getControl().setId("full_name");
			fElementFilter.getControl().setType("name");
			fElementFilter.getControl().setValue(filterFullName);
			fElementFilter.getControl().setClassName("filtro");
			fElementFilter.getControl().setMaxLength("100");
			fElementFilter.getControl().setOnBlur("limpiaBusqueda(this);");
			%>
			<%= fElementFilter.createInputElement() %>
		</td>
		<td>
			<span>Force Id</span>
			<%
			fElementFilter.initControl();
			fElementFilter.getControl().setId("force_id");
			fElementFilter.getControl().setType("number");
			fElementFilter.getControl().setValue(filterForceId);
			fElementFilter.getControl().setClassName("filtro");
			fElementFilter.getControl().setOnBlur("limpiaBusqueda(this);");
			%>
			<%= fElementFilter.createInputElement() %>		
		</td>
	</tr>
	<% } %>
	<tr>
		<td colspan="3"></td>
	</tr>
	<tr>
		<td colspan="3" onClick="upsertFilter('<%= currentPage %>');">
			<table>
				<tr>
					<td id="filter-message">Actualizar</td>
				</tr>
			</table>
		</td>
	</tr>
</table>