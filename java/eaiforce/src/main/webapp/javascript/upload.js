// JavaScript Document
function drive(key) {
	var queryString = "";
	queryString += "action=drive";
	queryString += "&key=" + key;
	
	request.url = "../uploadFiles";
	request.queryString = "?" + queryString;
	request.message = key;	
	makeAajaxCall(request, driveResponse);
}

function driveResponse(responseText, request) {
	var response = JSON.parse(responseText);
	var code = response["code"];
	var message = response["message"];
	
	if ( code == "-2" )
		invalidSession();
	
	else if ( code == "0" )
		download(request.message);	
}

function download(key) {
	var queryString = "";
	queryString += "action=download";
	queryString += "&key=" + key;	
	parent.location.href = "../uploadFiles?" + queryString;
}

function downloadExcel(filename) {
	var queryString = "";
	queryString += "action=download";
	queryString += "&key=" + filename;	
	location.href = "uploadFiles?" + queryString;
}

function placeText(element) {
	var fileName = element.value.split( '\\' ).pop();
	if (fileName.length > 15)
		fileName =  fileName.substring(0,15) + "...";
	
	if( fileName )
		getObject("label").innerHTML = fileName;
}

function validate() {
	if ( getObjectValue("file") != "" )
		showConfirm("\u00BFDeseas importar el archivo?", enviar);
}

function enviar() {
	showProgress(true);
	getObject("form").submit();
}

function fileOnLoad(message) {
	showProgress(false);
	if ( message != "" )
		showNotification(message);
}