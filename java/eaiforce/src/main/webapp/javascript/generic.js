var request = {
		url:"",
		queryString:"",
		message:"",
		value:"",
		redirect:"",
		object:null,
		div:null,
		next:null,
		button:null,
		elements:null
};

var response = {
		code:"",
		message:"",
		description:"", 
		exception:""
};

function getRequest() {
	var request = {
			url:"",
			queryString:"",
			message:"",
			value:"",
			redirect:"",
			object:null,
			div:null,
			next:null,
			button:null,
			elements:null
	};
	return request;
}

function getResponse() {
	var response = {
			code:"",
			message:"",
			description:"", 
			exception:""
	};
	return response;
}

function makeAajaxCall( request, funcion ) {
	var xmlhttp;
	
	if ( window.XMLHttpRequest ) // code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	else // code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {		
			// Check JSON Code when it's available
			if ( isValidJson(xmlhttp.responseText) && JSON.parse(xmlhttp.responseText)["code"] == "-2" ) {
				invalidSession();
				return;
			}
			
			if ( funcion != null )
				funcion(xmlhttp.responseText, request);
				
			else if ( request.div != null )
				request.div.innerHTML = xmlhttp.responseText;
		}
	}
	
	if ( request.object != null ) 
		request.object.innerHTML = request.message;
	xmlhttp.open("POST", request.url + request.queryString, true);
	xmlhttp.setRequestHeader('Content-type', 'charset=ISO-8859-1');
	xmlhttp.send();
}

function refreshDivContent( url, div ) {
	var request = getRequest();
	request.url = url;
	request.div = getObject(div);
	makeAajaxCall(request);
}

function upsertAttributes() {	
	var queryString = "";
	for (i = 0; i < arguments.length; i++)
		queryString += ( i != 0 ) ? "&" + arguments[i] : arguments[i];
	
	if ( queryString == "" )
		return;
		
	var request = getRequest();
	request.url = "upsertAttribute";
	request.queryString = "?" + queryString;
	makeAajaxCall(request);
}

function showConfirm(message, accept, cancel) {	
	getObjectExtended("modal").style.display = "block";
	getObjectExtended("modal_message").innerHTML = message;
	getObjectExtended("modal_confirm").value = "Si";
	getObjectExtended("modal_confirm").onclick = accept;
	getObjectExtended("modal_ok").value = "No";
	getObjectExtended("modal_ok").focus();
	if ( cancel != null )
		getObjectExtended("modal_ok").onclick = cancel;

	getObjectExtended("modal_box").className = "modal-box";	
	getObjectExtended("modal_title").className = "modal-title";
	getObjectExtended("modal_confirm_td").style.display = "table-cell";
}

function showNotification(message, level, accept) {
	getObjectExtended("modal").style.display = "block";
	getObjectExtended("modal_confirm_td").style.display = "none";
	getObjectExtended("modal_message").innerHTML = message;
	getObjectExtended("modal_ok").value = "Aceptar";
	getObjectExtended("modal_ok").focus();
	if ( accept != null )
		getObjectExtended("modal_ok").onclick = accept;
	
	if ( level != null && level != "" ) {
		getObjectExtended("modal_title").className = "modal-title modal-title-" + level;
		getObjectExtended("modal_box").className = "modal-box modal-box-" + level;
	}
}

function showProgress(toggle) {
	getObjectExtended("modal").style.display = (toggle) ? "block" : "none";
	getObjectExtended("form_modal").style.display = (toggle) ? "none" : "table";
	getObjectExtended("form_progress").style.display = (toggle) ? "table" : "none";

	getObjectExtended("modal_title").className = "modal-title";
	getObjectExtended("modal_box").className = "modal-box";
}

function dismiss() {
	getObjectExtended("modal").style.display = "none";
	getObjectExtended("modal_box").className = "modal-box";
	getObjectExtended("modal_title").className = "modal-title";
	getObjectExtended("modal_ok").value = "Aceptar";
	getObjectExtended("modal_ok").onclick = dismiss;
}

function invalidSession() {
	showNotification("Favor de iniciar Sesi&oacute;n", null, redirectHome);
}

function thankNote() {
	showProgress(false);
	showNotification(getObjectValue("note"), null, redirectHome);
}

function redirectHome() {
	location.href = "/";
}

function onLoad() {
	showProgress(false);
}

function main() {
	showProgress(true);
	location.href = "home";
}

function prospecto() {
	initCliente();
}

function q(q) {
	initCliente("q=" + btoa(q));
}

function initCliente(q) {
	var request = getRequest();
	var queryString = ( q != null ) ? q : "";
	
	request.url = "loadCustomer";
	request.queryString = "?" + queryString;
	request.message = "<span>Por favor espere...</span>";
	request.object = document.getElementById("floatDivMessage");
	
	makeAajaxCall(request, initClienteResponse);	
}

function initClienteResponse(responseText) {
	var response = JSON.parse(responseText);
	var code = response["code"];
	var message = response["message"];
	
	if ( code == "0" ) {
		location.href = "prospecto";
	}
}

function toggleDivDisplay( div ) {
	var object = getObject(div);
	var display = ( object.style.display == "none" ) ? "block" : "none";
			
	object.style.display = display;
	
	var queryString = object.id + "=" + display;
	
	request.url = "upsertAttribute";
	request.queryString = "?" + queryString;
	
	makeAajaxCall(request, toggleTableDisplayResponse);
}

function toggleTableDisplayResponse (responseText) {
	var response = JSON.parse(responseText);
	var code = response["code"];
	
	if ( code == "-2" )
		invalidSession();
}

function getObject(id) {
	return document.getElementById(id);
}

function getObjectExtended(id) {
	if ( document.getElementById(id) != null )
		return document.getElementById(id);
	else if ( parent.document.getElementById(id) != null )
		return parent.document.getElementById(id);
}

function getObjectValue(id) {
	return (document.getElementById(id) != null) ? document.getElementById(id).value : "";
}

function getObjectAttribute(id, attribute) {
	return (document.getElementById(id) != null && document.getElementById(id).getAttribute(attribute) != null ) ? document.getElementById(id).getAttribute(attribute) : "";
}

function getSelectText( name ){
	var select = document.getElementById( name );
	var text = ( select != null && select.value != "" ) ? select.options[ select.selectedIndex ].innerHTML : "";	
	return text;
}

function setObjectValue(id, value) {
	if (document.getElementById(id) != null) document.getElementById(id).value = value;
}

function setObjectAttribute(id, attribute, value) {
	if (document.getElementById(id) != null) document.getElementById(id).setAttribute(attribute, value);
}

function removeObjectAttribute(id, attribute) {
	if (document.getElementById(id) != null) document.getElementById(id).removeAttribute(attribute);
}

function isValidJson(json) {
    try {
        JSON.parse(json);
        return true;
    } catch (e) {
        return false;
    }
}