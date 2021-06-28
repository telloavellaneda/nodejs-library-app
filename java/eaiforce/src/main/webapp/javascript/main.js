function loadWidget() {
	onLoad();

	loadWidgetRequest("dashboard/byFase.jsp","divFases");
	loadWidgetRequest("dashboard/byStatus.jsp","divStatus");
	loadWidgetRequest("dashboard/byBanco.jsp","divBancos");
	loadWidgetRequest("dashboard/byUsuario.jsp","divUsuarios");
	loadWidgetRequest("dashboard/byMonth.jsp","divMonth");
	loadWidgetRequest("dashboard/byDecreto.jsp","divDecreto");
	loadWidgetRequest("dashboard/clientes.jsp","divclientes");
	loadWidgetRequest("dashboard/prospectos.jsp","divprospectos");
}

function loadWidgetRequest(url, div) {
	var request = getRequest();
	request.url = url;
	request.div = getObject(div);
	makeAajaxCall(request, loadWidgetResponse)
}

function loadWidgetResponse(responseText, request) {
	request.div.innerHTML = responseText;
	request.div.style.height = "auto";
}

function pager(dataset, opcion) {
	var request = getRequest();
	upsertAttributes(dataset + "=" + opcion);
	
	request.url = "upsertAttribute";
	request.queryString = "?" + dataset + "=" + opcion;
	request.redirect = dataset;
	makeAajaxCall(request, pagerRequest);
}

function pagerRequest(responseText, request) {
	request.url = "buildDashboard";
	request.queryString = "?action=" + request.redirect;
	makeAajaxCall(request, pagerResponse);
}

function pagerResponse(responseText, request) {
	if ( JSON.parse(responseText)["code"] == "0" ) 
		refreshDivContent("dashboard/" + request.redirect + ".jsp", "div" + request.redirect);
}

function pagerBuscar(opcion) {
	var request = getRequest();
	
	request.url = "upsertAttribute";
	request.queryString = "?buscar=" + opcion;
	request.redirect = "divSearch";
	makeAajaxCall(request, pagerBuscarRequest);
}

function pagerBuscarRequest(responseText, request) {	
	request.url = "buildDashboard";
	request.queryString = "?action=buscar";
	makeAajaxCall(request, pagerBuscarResponse);
}

function pagerBuscarResponse(responseText, request) {
	if ( JSON.parse(responseText)["code"] == "0" ) 
		refreshDivContent("printSearch.jsp", request.redirect);
}

function report() {
	var request = getRequest();
	
	showProgress(true);
	request.url = "buildDashboard";
	request.queryString = "?action=report";
	makeAajaxCall(request, reportResponse);
}

function reportResponse(responseText, request) {
	showProgress(false);
	if ( JSON.parse(responseText)["code"] == "0" ) 
		location.href = "generateReport.jsp";
}

function upsertFilter(redirect) {
	getObject("filter-message").innerHTML = "Procesando";

	request.url = "upsertAttribute";
	request.redirect = redirect;
	request.queryString = "?" + filterQueryString();	
	
	makeAajaxCall(request, upsertFilterResponse);
}

function upsertFilterResponse(responseText, request) {		
	if ( JSON.parse(responseText)["code"] == "0" ) {
		showProgress(true);
		location.href = request.redirect;
	}
}

function filterQueryString() {
	var queryString = "";
	
	queryString += "filter_user=" + getObjectValue("usuario");
	queryString += "&filter_user_responsable=" + getObjectValue("responsable");
	queryString += "&filter_fase=" + getObjectValue("fase");
	queryString += "&filter_status=" + getObjectValue("status");
	queryString += "&filter_banco=" + getObjectValue("banco");
	queryString += "&filter_decreto=" + getObjectValue("decreto");
	queryString += "&filter_year=" + getObjectValue("year");
	queryString += "&filter_month=" + getObjectValue("month");
	queryString += "&filter_year_autorizacion=" + getObjectValue("year_autorizacion");
	queryString += "&filter_month_autorizacion=" + getObjectValue("month_autorizacion");
	queryString += "&filter_year_firma=" + getObjectValue("year_firma");
	queryString += "&filter_month_firma=" + getObjectValue("month_firma");

	queryString += ( getObject("force_id") != null ) ? "&filter_force_id=" + getObjectValue("force_id") : "";
	queryString += ( getObject("full_name") != null ) ? "&filter_full_name=" + getObjectValue("full_name") : "";
	
	return queryString;
}

function changeLabel() {
	var label = "Actualizar";
	if (isDifferent("banco") || isDifferent("usuario") || isDifferent("responsable") || 
		isDifferent("fase") || isDifferent("status") || isDifferent("decreto") ||  
		isDifferent("year") || isDifferent("month") || 
		isDifferent("year_autorizacion") || isDifferent("month_autorizacion") || 
		isDifferent("year_firma") || isDifferent("month_firma") 
		)
		label = "Filtrar";
	
	limpiaCampos();
	getObject("filter-message").innerHTML = label;
}

function limpiaCampos() {
	if ( getObject('force_id') != null ) 
		getObject('force_id').value = "";
	if ( getObject('full_name') != null ) 
		getObject('full_name').value = "";	
}

function limpiaFiltros() {
	getObject("banco").value = "";
	getObject("usuario").value = "";
	getObject("responsable").value = "";
	getObject("fase").value = "";
	getObject("status").value = "";
	getObject("decreto").value = "";
	getObject("year").value = "";
	getObject("month").value = "";
	getObject("year_autorizacion").value = "";
	getObject("month_autorizacion").value = "";
	getObject("year_firma").value = "";
	getObject("month_firma").value = "";
}

function limpiaBusqueda(objeto) {
	if (objeto.id == "force_id" && objeto.value != "")
		getObject('full_name').value = "";
	else if (objeto.id == "full_name" && objeto.value != "")
		getObject('force_id').value = "";
	
	if (objeto.value != "") {
		getObject("filter-message").innerHTML = "Buscar";
		limpiaFiltros();		
	}
}

function isDifferent(id) {
	if ( getObjectAttribute(id,"initvalue") != getObjectValue(id) )
		return true;
	else
		return false;
}