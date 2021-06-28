function upsertCustomer() {
	var queryString = "";
	var request = getRequest();
		
	showProgress(true);
	queryString += setParameterWithValue("action","upsert");
	//queryString += setParameterWithValue("show_parameters","true");
	queryString += formQueryString();
	
	request.url = "upsertCustomer";
	request.queryString = "?" + queryString;	
	makeAajaxCall(request, upsertCustomerResponse);
}

function upsertCustomerResponse(responseText) {
	var response = JSON.parse(responseText);
	var code = response["code"];
	var message = response["message"];
	
	showProgress(false);
	if ( code == "0" )
		registroExitoso(message);
	else
		registroError(message);
}

function upsertStatus() {
	var queryString = "";
	
	queryString += setParameterWithValue("action","upsertStatus");
	queryString += "&id_fase=" + document.getElementById("id_fase").value;
	queryString += "&fase=" + getSelectText("id_fase");
	queryString += "&id_status=" + document.getElementById("id_status").value;
	queryString += "&status=" + getSelectText("id_status");
	
	request.url = "upsertCustomer";
	request.queryString = "?" + queryString;
	request.message = "Actualizando Status...";
	request.object = document.getElementById("floatDivMessage");
	
	if ( isNewStatus() ) {
		showProgress(true);
		makeAajaxCall(request, upsertStatusResponse);
	} else
		showNotification("Por favor asigne un status diferente","yellow");
}

function upsertStatusResponse(responseText) {
	var response = JSON.parse(responseText);
	var code = response["code"];
	var message = response["message"];
	
	if ( code == "0" ) {
		refreshDivContent("include/datosHeader.jsp","divFormHeader");
		refreshDivContent("include/datosHistorial.jsp","divFormHistorial");
		refreshDivContent("include/01-datosAgenda.jsp","divDatosAgenda");
		setObjectAttribute("id_fase", "initvalue", getObjectValue("id_fase"));
		setObjectAttribute("id_status", "initvalue", getObjectValue("id_status"));
		showProgress(false);
		showNotification(message);
		document.getElementById("floatDivMessage").innerHTML = "";	
	}
}

function upsertSeguimiento( option ) {
	var queryString = ""
	var seguimiento = document.getElementById("mensajeSeguimiento");
	var refresh = ( option != "" ) ? setParameterWithValue("refresh","true") : "";
	
	if (seguimiento.value == "" && refresh == "")
		return;
	
	if ( document.getElementById("force_id").innerHTML == "Force Id" )
		return;

	queryString += "?";
	queryString += setParameterWithValue("action","upsertSeguimiento");
	queryString += setParameterWithValue("q",encodeURI(seguimiento.value).replace(/\r?\n|\r/g,"<br>"));
	queryString += refresh
	
	request.url = "upsertCustomer";
	request.queryString = queryString;
	request.message = "";
	request.object = document.getElementById("floatDivMessage");
	
	makeAajaxCall(request, upsertSeguimientoResponse);
}

function upsertSeguimientoResponse( responseText ) {
	var response = JSON.parse(responseText);
	var code = response["code"];

	if ( code == "0" ) {
		upsertSeguimientoExito();
		var seguimiento = document.getElementById("mensajeSeguimiento");
		seguimiento.value = "";
		seguimiento.focus();	
	}
}

function notify( id ) {
	var queryString = "";
	var request = getRequest();
	
	request.url = "notifyUser";
	request.queryString = "?message_id=" + id;
	makeAajaxCall(request, notifyResponse);
}

function notifyResponse( responseText ) {
	var response = JSON.parse(responseText);
	var code = response["code"];
	var message = response["message"];
		
	showNotification(message);
}

function refreshSeguimiento() {
	var request = getRequest();
	request.url = "upsertCustomer";
	request.queryString = "?post=true&refresh=true";
	makeAajaxCall(request, refreshSeguimientoResponse);
}

function refreshSeguimientoResponse( responseText ) {
	var response = JSON.parse(responseText);
	var code = response["code"];
	var description = response["description"];
	
	if ( code == "0" && description == "true" )
		upsertSeguimientoExito();		
}

function refreshCustomer() {
	if (getObject("force_id").innerHTML == "Force Id")
		return;	
	var request = getRequest();		
	request.url = "loadCustomer";
	request.queryString = "?" + "q=" + btoa(getObject("force_id").innerHTML);
	showProgress(true);
	makeAajaxCall(request, refreshCustomerResponse);
}

function refreshCustomerResponse( responseText ) {
	var response = JSON.parse(responseText);
	var code = response["code"];
	
	if ( code == "0" ) {
		refreshDivContent("include/datosHeader.jsp","divFormHeader");
		refreshDivContent("include/datosStatus.jsp","divFormStatus");
		refreshDivContent("include/12-datosDocumentacion.jsp","divFormDocumentos");
		refreshDivContent("include/datosSeguimiento.jsp","divSeguimiento");
		refreshDivContent("include/datosHistorial.jsp","divFormHistorial");
		
		refreshDivContent("include/01-datosAgenda.jsp","divDatosAgenda");
		refreshDivContent("include/02-datosCredito.jsp","divDatosCredito");
		refreshDivContent("include/03-datosPersonales.jsp","divDatosGenerales");
		refreshDivContent("include/04-datosDomicilio.jsp","divDomicilioActual");
		refreshDivContent("include/05-datosLaborales.jsp","divInformacionLaboral");
		refreshDivContent("include/06-datosIngresos.jsp","divIngresos");
		refreshDivContent("include/07-datosReferencias.jsp","divReferencias");
		refreshDivContent("include/08-datosPersonalesCoacreditado.jsp","divCoaDatosGenerales");
		refreshDivContent("include/09-datosDomicilioCoacreditado.jsp","divCoaDomicilioActual");
		refreshDivContent("include/10-datosLaboralesCoacreditado.jsp","divCoaInformacionLaboral");
		refreshDivContent("include/11-datosIngresosCoacreditado.jsp","divCoaIngresos");
	}
	showProgress(false);
}

function upsertSeguimientoExito() {
	refreshDivContent("include/datosSeguimiento.jsp","divSeguimiento");
	document.getElementById("floatDivMessage").innerHTML = "";
}

function isNewStatus() {
	if ( getObjectAttribute("id_fase","initvalue") != getObjectValue("id_fase") ||
		 getObjectAttribute("id_status","initvalue") != getObjectValue("id_status") )
		return true;
	else
		return false;
}

function buscarCP() {
	var queryString = "";
	var preffix = getPreffix();
	var codigo = document.getElementById( preffix + "codigo" );
	
	queryString += "action=zipcode";
	queryString += "&x=" + codigo.value;
	queryString += "&preffix=" + preffix;
	queryString += (codigo.getAttribute("required") != null ) ? "&required=" + codigo.getAttribute("required") : "";
		
	request.url = "loadFormElements";
	request.queryString = "?" + queryString;
	request.message = "Buscando C\u00F3digo Postal...";
	request.object = document.getElementById("floatDivMessage");
	
	makeAajaxCall(request, buscarCPResponse);
}

function buscarCPResponse(responseText) {
	var response = JSON.parse(responseText);
	var code = response["code"];
	var message = response["message"];
	
	if ( code == "-2" ) {
		invalidSession();
		return;
	}
	
	var preffix = getPreffix();
	var codigo = document.getElementById( preffix + "codigo" );
	var colonia = document.getElementById( preffix + "colonia_td" );
	var delegacion = document.getElementById( preffix + "delegacion_td" );
	var ciudad = document.getElementById( preffix + "ciudad_td" );
	var estado = document.getElementById( preffix + "estado" );
	
	document.getElementById("floatDivMessage").innerHTML = "";
	
	var resultado = message.split("|");
	colonia.innerHTML = resultado[0];
	delegacion.innerHTML = resultado[1];
	ciudad.innerHTML = resultado[2];
	estado.selectedIndex = resultado[3];
	if (estado.getAttribute("required") != null)
		requiredFlag(estado.id);
	
	if ( document.getElementById( preffix + "codigo" ).value != "" )
		document.getElementById( preffix + "colonia" ).focus();
	else if ( document.getElementById( preffix + "codigo" ).value == "" && document.getElementById( preffix + "codigo" ).getAttribute("required") != null )
		document.getElementById( preffix + "codigo" ).focus();
}

function calcularRfcCurp( button, tipo, next ) {
	var request = getRequest();
	var preffix = getPreffix();
	var preffixTipo = ( tipo == "moral" ) ? "moral_" : "";
	var queryString = ( tipo == "moral" ) ? rfcMoralQueryString() : rfcCurpQueryString();
	
	if ( queryString == "" )
		return;

	request.url = "loadFormElements";
	request.queryString = "?" + queryString;
	request.message = button.value;
	request.value = button.value;
	request.object = document.getElementById("floatDivMessage");
	request.next = document.getElementById(preffix + next);
	request.button = button;
	request.elements = [
		preffix + preffixTipo + "rfc",
		preffix + preffixTipo + "homoclave",
		preffix + preffixTipo + "curp"
	];
	
	button.value = "Procesando...";
	button.disabled = true;

	makeAajaxCall(request, calcularRfcCurpResponse);	
}

function calcularRfcCurpResponse(responseText, request) {
	var response = JSON.parse(responseText);
	var code = response["code"];
	var message = response["message"];
	
	var resultado = message.split("|");
	request.button.value = request.value;
	request.button.disabled = false;

	if ( message.split("|")[0].trim() != "" ) {
		setObjectValue(request.elements[0], resultado[0].trim());
		setObjectValue(request.elements[1], resultado[1].trim());
		setObjectValue(request.elements[2], resultado[2].trim());

		requiredFlag( request.elements[0] );
		requiredFlag( request.elements[1] );
		requiredFlag( request.elements[2] );
		
		request.next.focus();
	}
	request.object.innerHTML = "";
}

function getStatusElement( object ) {
	var queryString = "";
	var request = getRequest();
		
	queryString += "action=status";
	queryString += "&id_fase=" + object.value;
	
	request.url = "loadFormElements";	
	request.queryString = "?" +queryString;
	request.div = getObject("formStatusCombo");
	request.object = "id_status";
	
	makeAajaxCall(request, getElementResponse);
}

function getProductElement( object ) {
	var queryString = "";
	var request = getRequest();
	
	queryString += "action=producto";
	queryString += "&id_banco=" + object.value;
	
	request.url = "loadFormElements";	
	request.queryString = "?" +queryString;
	request.div = getObject("cred_producto_td");
	request.object = "cred_producto";
	
	makeAajaxCall(request, getElementResponse);
}

function getElementResponse(responseText, request) {
	var code = JSON.parse(responseText)["code"];
	var message = JSON.parse(responseText)["message"];

	if ( code == "0" ) {		
		request.div.innerHTML = message;
		getObject(request.object).focus();
	}
}