// JavaScript Document
"use strict";
var interval;

function guardar() {
	if ( getObject("force_id").innerHTML != "Force Id" )
		upsertCustomer();
	else
		if ( sectionZeroCheckRequired() )	
			showConfirm("\u00BFDeseas guardar el registro?", upsertCustomer);
		else
			showNotification("Favor de completar los campos marcados como requeridos <span class='required'>*</span>", "yellow");
}

function registroExitoso(message) {
	showNotification(message);	
	refreshDivContent("include/datosHeader.jsp","divFormHeader");
	refreshDivContent("include/01-datosAgenda.jsp","divDatosAgenda");
	
	if ( getObject("section").value == "section0" ) {
		refreshDivContent("include/02-datosCredito.jsp","divDatosCredito");
		refreshDivContent("include/03-datosPersonales.jsp","divDatosGenerales");
		refreshDivContent("include/04-datosDomicilio.jsp","divDomicilioActual");
		refreshDivContent("include/12-datosDocumentacion.jsp","divFormDocumentos");
		refreshDivContent("include/datosStatus.jsp","divFormStatus");
		refreshDivContent("include/datosHistorial.jsp","divFormHistorial");
	
		getObject("botonSiguiente").style.display = "";
	}
}

function registroError(message) {
	showNotification(message, "red");
}

function isVisible(id) {
	if (document.getElementById(id).style.display == "none" )
		return false;
	else
		return true;
}

function getPreffix() {
	return getObjectAttribute(getObjectValue("section"),"preffix");
}

function setParameter(id) {
	return "&" + id + "=" + getObjectValue(id);
}

function setParameterWithValue(parameter, value) {
	return "&" + parameter + "=" + value;
}

function focusNext ( fromObject, toObject ) {
	if ( fromObject.value != "") {
		document.getElementById( toObject ).focus();
	}
}

function escondeTr( identificador, opcion ) {
	if ( identificador.value == opcion ) {
		document.getElementById( identificador.id + "_tr" ).style.display = "none";	
	} else {
		document.getElementById( identificador.id+ "_tr" ).style.display = "";	
	}
}

function muestraTr( identificador, opcion ) {
	if ( identificador.value == opcion ) {
		document.getElementById( identificador.id + "_tr" ).style.display = "";	
	} else {
		document.getElementById( identificador.id+ "_tr" ).style.display = "none";	
	}
}

function muestraOtroTr( objeto ) {
	if ( objeto.value == "OTRO" ) {
		document.getElementById( objeto.id + "_otro_tr" ).style.display = "table-row";
		document.getElementById( objeto.id + "_otro" ).focus();
	} else {
		document.getElementById( objeto.id + "_otro_tr" ).style.display = "none";	
	}
}

function muestraImporteAprobado( objeto ) {
	if ( objeto.value == "RECHAZADO" ) {
		document.getElementById("cred_importe_aprob_tr").style.display = "none";
		document.getElementById("cred_importe_aprob").value = "";
	} else
		document.getElementById("cred_importe_aprob_tr").style.display = "table-row";	
}

function getOtherValue( identificador ) {	
	if ( document.getElementById( identificador + "_otro_tr").style.display == "table-row" ) {
		var queryString = ""; 
		queryString += "&" + identificador + "=" + document.getElementById(identificador).value;
		queryString += ( document.getElementById(identificador + "_otro").value != "" ) ?  "|" + document.getElementById(identificador + "_otro").value : "";
		
		return  queryString;
	}
	else
		return "&" + identificador + "=" + document.getElementById(identificador).value;	
}

function sumIngresos() {
	var preffix = getPreffix();
	var valor = castNumber(preffix + "ingresos_bruto") + castNumber(preffix + "otros_ingresos");
	getObject(preffix + "total_ingresos").value = formatNumber(valor.toFixed(2));
}

function castNumber( id ) {
	if ( getObjectValue(id) != "" ) 
		return Number(replaceFormat(getObjectValue(id)));
	else
		return 0;
}

function atras() {
	var seccionAtras = "";
	var section = document.getElementById("section");
	var coacreditado = document.getElementById("cred_coacreditado");
	
	if ( section.value == "section2" ) seccionAtras = "section1";
	if ( section.value == "section3" ) seccionAtras = "section2";
	if ( section.value == "section4" ) seccionAtras = "section3";
	if ( section.value == "section5" ) seccionAtras = "section4";
	if ( section.value == "section6" ) seccionAtras = "section5";
	if ( section.value == "section7" ) seccionAtras = "section6";

	if ( coacreditado. value != "" ) {
		if ( section.value == "section8" ) seccionAtras = "section7";
		if ( section.value == "section9" ) seccionAtras = "section8";
		if ( section.value == "section10" ) seccionAtras = "section9";
		if ( section.value == "section11" ) seccionAtras = "section10";

	}
	
	getObject( "botonSiguiente" ).style.display = "";
	getObject( section.value ).style.display = "none";
	getObject( seccionAtras ).style.display = "";

	section.value = seccionAtras;
	upsertAttributes("section=" + seccionAtras);	
	getFormTitle();

	if ( seccionAtras == "section1" ) 
		getObject( "botonAtras" ).style.display = "none";
}

function siguiente() {
	var seccionSiguiente = "";
	var section = document.getElementById("section");
	var coacreditado = document.getElementById("cred_coacreditado");
	
	if ( section.value == "section0" ) seccionSiguiente = "section1";
	if ( section.value == "section1" ) seccionSiguiente = "section2";
	if ( section.value == "section2" ) seccionSiguiente = "section3";
	if ( section.value == "section3" ) seccionSiguiente = "section4";
	if ( section.value == "section4" ) seccionSiguiente = "section5";
	if ( section.value == "section5" ) seccionSiguiente = "section6";
	if ( section.value == "section6" ) seccionSiguiente = "section7";
	
	if ( coacreditado. value != "" ) {
		if ( section.value == "section7" ) seccionSiguiente = "section8";
		if ( section.value == "section8" ) seccionSiguiente = "section9";		
		if ( section.value == "section9" ) seccionSiguiente = "section10";		
		if ( section.value == "section10" ) seccionSiguiente = "section11";
	}

	if ( coacreditado.value == "" && seccionSiguiente == "section7" ) 
		getObject("botonSiguiente").style.display = "none";

	if ( coacreditado.value != "" && seccionSiguiente == "section11" ) 
		getObject("botonSiguiente").style.display = "none";
	
	getObject( section.value ).style.display = "none";
	getObject( seccionSiguiente ).style.display = "";
	getObject( "botonAtras" ).style.display = (section.value != "section0" && section.value != "section1") ? "" : "none";

	section.value = seccionSiguiente;
	upsertAttributes("section=" + seccionSiguiente);	
	getFormTitle();
}

function getFormTitle() {
	var sectionArray = [
		"section0",
		"section1",
		"section2",
		"section3",
		"section4",
		"section5",
		"section6",
		"section7",
		"section8",
		"section9",
		"section10",
		"section11"
	];

	var contador = 1;
	var section = getObjectValue("section");
	var coacreditado = getObjectValue("cred_coacreditado");
	var formSectionTitle = document.getElementById("formSectionTitle");
	
	var title = getObjectAttribute(section,"formTitle");
	
	// GET FORM POSITION
	sectionArray.splice(0,1);
	
	if ( coacreditado == "" )
		sectionArray.splice(7,4);

	for (var i = 0; i < sectionArray.length; i++ ) {
		if ( sectionArray[i] == section )
			break;
		contador ++;
	}
	
	var position = ( section != "section0" ) ? " (" + contador + "/" + sectionArray.length + ")" : "";
	
	formSectionTitle.innerHTML = title + position;
}

function trackingInterval() {
    //interval = setInterval(refreshSeguimiento, 3000);
}