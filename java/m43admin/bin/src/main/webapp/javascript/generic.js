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

function invalidSession() {
	showNotification("Favor de iniciar Sesi&oacute;n", null, redirectHome);
}

function redirectHome() {
	location.href = "/";
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