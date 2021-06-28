// JavaScript Document
var special = /[\/\\|&#+()$\^~%\-@'":\;,*`´¿!¡?<>{}]/g;
var extraSpaces = /  +/g;
var letters = /[a-zA-Z]/g;
var numbers = /[0-9]/g;
var dateString = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
var rfcString = /^([a-zA-Z]{3,4}[0-9]{6})$/g;
var homoclaveString =/^([a-zA-Z0-9]{3})$/g;
var emailString = /^([a-zA-Z0-9._-]+@[a-z0-9.-]+\.[a-z]{2,3})$/;
var ladaString = /^([0-9]{2,3})$/g;
var telefonoString = /^([0-9]{7,8})$/g;
var razonSocialString = /[\/\\|&#+()$\^~%\-@'":\;*`´¿!¡?<>{}]/g;
var passwordString = /^[a-zA-Z]+$/;

var dot = /[.]/g;

function encodeURI( value ) {
	var elements = [
		["<","%3C"],
		[">","%3E"],
		["#","%23"],
		["%","%25"],
		["{","%7B"],
		["}","%7D"],
		["\\|","%7C"],
		["\\\\","%5C"],
		["\\^","%5E"],
		["~","%7E"],
		["\\[","%5B"],
		["]","%5D"],
		["`","%60"],
		[";","%3B"],
		["\\/","%2F"],
		["\\?","%3F"],
		[":","%3A"],
		["@","%40"],
		["=","%3D"],
		["&","%26"],
		["\\$","%24"],
		["\\+","%2B"],
		["\\\"","%22"],
		[" ","%20"],
		["'","''"]
	];	
	for ( var i = 0; i < elements.length; i ++ ) {
		var regEx = new RegExp( elements[i][0],"g");
		value = value.replace( regEx, elements[i][1]);
	}
	return value;
}

function validaFecha(object) {	
	// regular expression to match required date format
	if ( !(object.value.match(dateString) && isValidDate( object.value)) ) {
		if ( getRelatives(object,"anios") != null)
			getRelatives(object,"anios").value = "";
		if ( getRelatives(object,"meses") != null)
			getRelatives(object,"meses").value = "";
		if ( getRelatives(object,"rfc") != null)
			getRelatives(object,"rfc").value = "";
		if ( getRelatives(object,"homoclave") != null)
			getRelatives(object,"homoclave").value = "";

		object.value = "";
	}
}

function validaListaCorreos(value) {
	var temporal = "";
	
	value = value.replace(/\r?\n|\r/g,"|");
	value = value.replace(/[,;]/g,"|");
	value = value.replace(/\|+/g," ").trim().replace(/\ /g,"; ").trim();
		
	for ( var i = 0; i < value.split(";").length; i ++)
		temporal += ( value.split(";")[i].trim().match(emailString) ) ? value.split(";")[i].trim() + "; " : "";

	return temporal.trim();
}

function validaNumero ( objeto ) {
	var tempNumber = objeto.value.toString().replace(special,"").replace(extraSpaces," ").replace(letters,"").trim().toUpperCase();
	
	if ( isNaN( tempNumber ) || tempNumber == "" )
		objeto.value = "";
	else {
		var precision = ( objeto.getAttribute("precision") != "" ) ? objeto.getAttribute("precision") : 0;
		objeto.value = parseFloat(tempNumber).toFixed(precision);
	}
}

function validaRFC( objeto ) {
	var tmpValue = objeto.value.toString().replace(special,"").replace(extraSpaces," ").replace(dot,"").trim().toUpperCase();
	var regEx = ( objeto.maxLength == 3 ) ? homoclaveString : rfcString;

	if ( doTheMatch( regEx, objeto )) {
		objeto.value = tmpValue;		
	}
}

function validaCorreo( objeto ) {
	doTheMatch( emailString, objeto );
}

function validaPassword ( objeto ) {
	doTheMatch( passwordString, objeto );
}

function validaTelefono ( objeto ) {
	var regEx = ( objeto.maxLength == 3 ) ? ladaString : telefonoString;
	doTheMatch( regEx, objeto );
}

function validaNombre ( objeto ) {
	var tmpValue = objeto.value.toString().replace(special,"").replace(extraSpaces," ").replace(numbers,"").replace(dot,"").trim().toUpperCase();
	
	objeto.value = tmpValue;
}

function validaAlfanumerico ( objeto ) {
	var tmpValue = objeto.value.toString().replace(special,"").replace(extraSpaces," ").trim().toUpperCase();
	
	objeto.value = tmpValue;
}

function validaRazonSocial ( objeto ) {
	var tmpValue = objeto.value.toString().replace(razonSocialString,"").replace(/\r?\n|\r/g," ").replace(/\s\s+/g," ").replace(extraSpaces,"").replace(dot,"");
	
	objeto.value = tmpValue.trim().toUpperCase();
}

function validaComentarios ( objeto ) {
	var tmpValue = objeto.value.toString().replace(special,"").replace(extraSpaces," ").trim();
	
	objeto.value = tmpValue;
}

function validaTipo( objeto ) {
	var preffix = getPreffix();
	
	if ( objeto.value == "2" )
		document.getElementById(preffix + "moral_datos").style.display = "table";
	else
		document.getElementById(preffix + "moral_datos").style.display = "none";
}

function isValidDate( dateStr ) {
	var day = dateStr.split("/")[0];
	var month = dateStr.split("/")[1];
	var year = dateStr.split("/")[2];
	 
	if (month < 1 || month > 12) {
		return false;
	}
	if (day < 1 || day > 31) {
		return false;
	}
	if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {
		return false;
	}
	if (month == 2) { // check for february 29th
		var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
		if (day>29 || (day==29 && !isleap)) {
			return false;
		}
	}
	if ( year < 1930 ) {
		return false;
	}
	return true;  // date is valid
}

function getRelatives (object, name) {
	var base = (object.id.split("fecha").length > 0 ) ? object.id.split("fecha")[0] : ""; 	
	return document.getElementById(base+name);
}

function autoComplete( objeto ) {
	objeto.value = ( objeto.value == "" ) ? getObjectAttribute(objeto.id, "initValue") : objeto.value;
}

function autoCompleteDateFormat( objeto ) {
	var valor = objeto.value;
	
	if ( valor.length == 2 || valor.length == 5 )
		objeto.value = valor + "/";
}

function formatNumber(num) {
	if ( num != null && num != "" ) {
		var tmpNumero = num.split(".");
		var first = /(\d)(?=(\d{3})+(?!\d))/g;
		
		if ( tmpNumero.length == 2 ) 
			return tmpNumero[0].toString().replace(first, "$1,") + "." + tmpNumero[1];
		else
			return tmpNumero[0].toString().replace(first, "$1,");

	} else
		return "";
}

function replaceFormat(num) {
	return num.toString().replace(special,"");
}

function doTheMatch( regEx, objeto ) {
	if ( !objeto.value.match( regEx ) )  {  
		objeto.value = "";
		return false;
	}		
	return true;
}
