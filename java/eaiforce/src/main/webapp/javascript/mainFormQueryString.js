// JavaScript Document
"use strict";
function formQueryString() {
	if ( document.getElementById("section0") != null )
		return cargaRapidaQueryString() + cargaCompletaQueryString();
	else 
		return cargaCompletaQueryString();
}

function cargaRapidaQueryString() {
	var queryString = "";
	
	//queryString += setParameterWithValue("section", getObjectValue("section"));
	
	// PERSONA	
	queryString += genericQueryStringGenerales("pre_");
	
	// TELEFONO CELULAR
	queryString += genericQueryStringTelefono("pre_");
	
	// CREDITO
	queryString += genericQueryStringCredito("pre_");
	
	return queryString;
}

function cargaCompletaQueryString() {
	var queryString = "";
	
	//queryString += setParameterWithValue("section", getObjectValue("section"));
	
	queryString += sectionAgendaQueryString();
	queryString += sectionOneQueryString();	
	queryString += sectionTwoQueryString();
	queryString += sectionThreeQueryString();	
	queryString += genericQueryStringEmpleo("dl_");
	queryString += genericQueryStringIngresos("ing_");
	queryString += sectionReferenciasQueryString();

	// COACREDITADO
	if ( document.getElementById("cred_coacreditado").value != "" ) {
		queryString += sectionSixQueryString();
		queryString += sectionSevenQueryString();
		queryString += genericQueryStringEmpleo("coa_dl_");
		queryString += genericQueryStringIngresos("coa_ing_");
	}
	
	return queryString;
}

function genericQueryStringGenerales( preffix ) {
	var queryString = "";
	
	var tipo = document.getElementById(preffix + "tipo").value;
	
	queryString += setParameter(preffix + "tipo");
	queryString += setParameter(preffix + "apellido_paterno");
	queryString += setParameter(preffix + "apellido_materno");
	queryString += setParameter(preffix + "primer_nombre");
	queryString += setParameter(preffix + "segundo_nombre");
	queryString += setParameter(preffix + "fecha_nacimiento");
	queryString += setParameter(preffix + "rfc");
	queryString += setParameter(preffix + "homoclave");
	queryString += setParameter(preffix + "anios");
	queryString += setParameter(preffix + "meses");
	queryString += setParameter(preffix + "sexo");
	queryString += setParameter(preffix + "email");

	if ( tipo == "2" ){
		queryString += setParameter(preffix + "moral_nombre");
		queryString += setParameter(preffix + "moral_fecha");
		queryString += setParameter(preffix + "moral_rfc");
		queryString += setParameter(preffix + "moral_homoclave");
		queryString += setParameter(preffix + "moral_anios");
		queryString += setParameter(preffix + "moral_meses");
	}
	
	return queryString;
}

function genericQueryStringTelefono( preffix ) {
	var queryString = "";
	
	queryString += setParameter(preffix + "cel_lada");
	queryString += setParameter(preffix + "cel_numero");
	
	return queryString;
}

function genericQueryStringCredito( preffix ) {
	var queryString = "";
	
	queryString += setParameter(preffix + "id_banco");
	queryString += setParameter(preffix + "producto");
	queryString += setParameter(preffix + "destino");
	queryString += setParameterWithValue(preffix + "banco", getSelectText(preffix + "id_banco"));
	queryString += setParameterWithValue(preffix + "importe_solic", replaceFormat(getObjectValue(preffix + "importe_solic")) );

	return queryString;
}

function genericQueryStringEmpleo( preffix ) {
	var queryString = "";
	
	queryString += setParameter(preffix + "nombre_empresa");
	queryString += getOtherValue(preffix + "sector_empresa");
	queryString += getOtherValue(preffix + "giro_empresa");
	queryString += setParameter(preffix + "puesto");
	queryString += setParameter(preffix + "profesion");
	queryString += getOtherValue(preffix + "tipo_empleo");
	queryString += setParameter(preffix + "tipo_contrato");
	queryString += setParameter(preffix + "anios");
	queryString += setParameter(preffix + "meses");
	
	queryString += setParameter(preffix + "calle");
	queryString += setParameter(preffix + "num_exterior");
	queryString += setParameter(preffix + "num_interior");
	queryString += setParameter(preffix + "codigo");
	queryString += setParameter(preffix + "colonia");
	queryString += setParameter(preffix + "delegacion");
	queryString += setParameter(preffix + "ciudad");
	queryString += setParameter(preffix + "estado");
	
	return queryString;
}

function genericQueryStringIngresos( preffix ) {
	var queryString = "";
	
	queryString += setParameterWithValue(preffix + "ingresos_bruto", replaceFormat(getObjectValue(preffix + "ingresos_bruto")) );
	queryString += setParameterWithValue(preffix + "ingresos_neto", replaceFormat(getObjectValue(preffix + "ingresos_neto")) );
	queryString += setParameterWithValue(preffix + "otros_ingresos", replaceFormat(getObjectValue(preffix + "otros_ingresos")) );
	queryString += getOtherValue(preffix + "fuente_ingresos");
	
	return queryString;
}

function sectionAgendaQueryString() {
	var preffix = "ag_";
	var queryString = "";

	queryString += setParameter(preffix + "usuario_asignado");
	queryString += setParameterWithValue(preffix + "nombre_usuario_asignado", getSelectText(preffix + "usuario_asignado"));	
	queryString += setParameter(preffix + "responsable");
	queryString += setParameter(preffix + "ejecutivo_banco");
	queryString += setParameter(preffix + "notaria");
	queryString += setParameter(preffix + "abogado");
	queryString += setParameter(preffix + "fecha_expediente");
	queryString += setParameter(preffix + "fecha_fase_1");
	queryString += setParameter(preffix + "fecha_fase_2");
	queryString += setParameter(preffix + "fecha_fase_3");
	queryString += setParameter(preffix + "fecha_firma");
	queryString += setParameter(preffix + "fecha_autorizacion");
	queryString += setParameter(preffix + "fecha_vencimiento_linea");
	queryString += setParameter(preffix + "fecha_vencimiento_certificado");

	return queryString;
}

function sectionOneQueryString() {
	var preffix = "cred_";
	var queryString = "";
	
	queryString += genericQueryStringCredito(preffix);
	queryString += setParameter(preffix + "adherido");
	queryString += setParameter(preffix + "adherido_opcion");
	queryString += setParameterWithValue(preffix + "importe_aprob", replaceFormat(getObjectValue(preffix + "importe_aprob")) );
	queryString += setParameterWithValue(preffix + "importe_final", replaceFormat(getObjectValue(preffix + "importe_final")) );
	queryString += setParameter(preffix + "decreto");
	queryString += setParameterWithValue(preffix + "decreto_comentarios", encodeURI(getObjectValue(preffix + "decreto_comentarios")).replace(/\r?\n|\r/g,"%26%2313%3B"));
	queryString += getOtherValue(preffix + "coacreditado");
	queryString += setParameterWithValue(preffix + "valor_estimado", replaceFormat(getObjectValue(preffix + "valor_estimado")) );
	queryString += setParameterWithValue(preffix + "enganche", replaceFormat(getObjectValue(preffix + "enganche")) );
	queryString += getOtherValue(preffix + "origen_enganche");
	queryString += getOtherValue(preffix + "plazo");
	queryString += setParameter(preffix + "tasa");
	queryString += setParameter(preffix + "aforo");
	queryString += setParameter(preffix + "tipo_comision_apertura");
	queryString += setParameter(preffix + "comision_apertura");

	return queryString;
}

function sectionTwoQueryString() {
	var preffix = "dem_";
	var queryString = "";
	
	queryString += genericQueryStringGenerales(preffix);
	queryString += setParameter(preffix + "pais_nacimiento");
	queryString += setParameter(preffix + "estado_nacimiento");
	queryString += setParameter(preffix + "lugar_nacimiento");
	queryString += getOtherValue(preffix + "nacionalidad");
	queryString += setParameter(preffix + "curp");
	queryString += setParameter(preffix + "no_imss");
	queryString += getOtherValue(preffix + "nivel_academico");
	queryString += setParameter(preffix + "estado_civil");
	queryString += setParameter(preffix + "regimen");
	queryString += getOtherValue(preffix + "identificacion");
	queryString += setParameter(preffix + "numero_identificacion");
	
	return queryString;
}

function sectionThreeQueryString() {
	var preffix = "dg_";
	var queryString = "";
		
	queryString += setParameter(preffix + "calle");
	queryString += setParameter(preffix + "num_exterior");
	queryString += setParameter(preffix + "num_interior");
	queryString += setParameter(preffix + "codigo");
	queryString += setParameter(preffix + "colonia");
	queryString += setParameter(preffix + "delegacion");
	queryString += setParameter(preffix + "ciudad");
	queryString += setParameter(preffix + "estado");
	queryString += getOtherValue(preffix + "tipo_domicilio");
	queryString += setParameterWithValue(preffix + "monto", replaceFormat(getObjectValue(preffix + "monto")) );
	queryString += setParameter(preffix + "anios");
	queryString += setParameter(preffix + "meses");

	queryString += setParameter(preffix + "casa_lada");
	queryString += setParameter(preffix + "casa_numero");
	queryString += setParameter(preffix + "oficina_lada");
	queryString += setParameter(preffix + "oficina_numero");
	queryString += genericQueryStringTelefono(preffix);
	queryString += setParameter(preffix + "fax_lada");
	queryString += setParameter(preffix + "fax_numero");
	
	return queryString;
}

function sectionSixQueryString() {
	var preffix = "coa_dg_";
	var queryString = "";
	
	queryString += setParameter(preffix + "apellido_paterno");
	queryString += setParameter(preffix + "apellido_materno");
	queryString += setParameter(preffix + "primer_nombre");
	queryString += setParameter(preffix + "segundo_nombre");
	queryString += setParameter(preffix + "fecha_nacimiento");
	queryString += setParameter(preffix + "anios");
	queryString += setParameter(preffix + "meses");
	queryString += setParameter(preffix + "sexo");

	queryString += setParameter(preffix + "pais_nacimiento");
	queryString += setParameter(preffix + "estado_nacimiento");
	queryString += setParameter(preffix + "lugar_nacimiento");
	queryString += getOtherValue(preffix + "nacionalidad");
	queryString += setParameter(preffix + "rfc");
	queryString += setParameter(preffix + "homoclave");
	queryString += setParameter(preffix + "curp");
	queryString += setParameter(preffix + "no_imss");
	queryString += getOtherValue(preffix + "nivel_academico");
	queryString += setParameter(preffix + "estado_civil");
	queryString += setParameter(preffix + "regimen");
	queryString += getOtherValue(preffix + "identificacion");
	queryString += setParameter(preffix + "numero_identificacion");
	queryString += setParameter(preffix + "email");
	
	return queryString;
}

function sectionSevenQueryString() {
	var queryString = "";
	var preffix = "coa_dom_";

	queryString += setParameter(preffix + "calle");
	queryString += setParameter(preffix + "num_exterior");
	queryString += setParameter(preffix + "num_interior");
	queryString += setParameter(preffix + "codigo");
	queryString += setParameter(preffix + "colonia");
	queryString += setParameter(preffix + "delegacion");
	queryString += setParameter(preffix + "ciudad");
	queryString += setParameter(preffix + "estado");
	queryString += getOtherValue(preffix + "tipo_domicilio");
	queryString += setParameterWithValue(preffix + "monto",replaceFormat(getObjectValue(preffix + "monto")) );
	queryString += setParameter(preffix + "anios");
	queryString += setParameter(preffix + "meses");

	queryString += setParameter(preffix + "casa_lada");
	queryString += setParameter(preffix + "casa_numero");
	queryString += setParameter(preffix + "oficina_lada");
	queryString += setParameter(preffix + "oficina_numero");
	queryString += setParameter(preffix + "cel_lada");
	queryString += setParameter(preffix + "cel_numero");
	queryString += setParameter(preffix + "fax_lada");
	queryString += setParameter(preffix + "fax_numero");
	
	return queryString;
}

function sectionReferenciasQueryString() {
	var queryString = "";
	var preffix = "ref_";
	
	for ( var i = 1; i < 4; i++ ) {
		queryString += setParameter(preffix + "parentesco_" + i);
		queryString += setParameter(preffix + "apellido_paterno_" + i);
		queryString += setParameter(preffix + "apellido_materno_" + i);
		queryString += setParameter(preffix + "primer_nombre_" + i);
		queryString += setParameter(preffix + "segundo_nombre_" + i);
		queryString += setParameter(preffix + "tel_lada_" + i);
		queryString += setParameter(preffix + "tel_numero_" + i);		
	}
	
	return queryString;
}

function rfcCurpQueryString() {
	var tipo = "";
	var queryString = "";
	var preffix = getPreffix();	

	if ( document.getElementById( preffix + "apellido_paterno").value == "" ||
		 document.getElementById( preffix + "primer_nombre").value == "" ||
		 document.getElementById( preffix + "fecha_nacimiento").value == ""
		)
		return queryString;
	
	if ( getObjectValue( preffix + "tipo") != "2" )
		tipo = getObjectValue( preffix + "tipo");
	else
		tipo = "0";
	
	queryString += "action=rfc";
	queryString += "&tipo=" + tipo;
	queryString += "&apellido_paterno=" + replaceAccents(getObjectValue( preffix + "apellido_paterno"));
	queryString += "&apellido_materno=" + replaceAccents(getObjectValue( preffix + "apellido_materno"));
	queryString += "&primer_nombre=" + replaceAccents(getObjectValue( preffix + "primer_nombre"));
	queryString += "&segundo_nombre=" + replaceAccents(getObjectValue( preffix + "segundo_nombre"));
	queryString += "&fecha_nacimiento=" + getObjectValue( preffix + "fecha_nacimiento");
	queryString += "&sexo=" + getObjectValue( preffix + "sexo");
	queryString += ( document.getElementById( preffix + "estado_nacimiento") != null ) ? "&estado=" + getSelectText(preffix + "estado_nacimiento") : "";
	
	return queryString;
}

function rfcMoralQueryString() {
	var queryString = "";
	var preffix = getPreffix();	
	var tipo = document.getElementById( preffix + "tipo");
	var tmpNombre = replaceAccents(document.getElementById( preffix + "moral_nombre").value.toString());

	if ( tmpNombre == "" || document.getElementById( preffix + "moral_fecha").value == "" )
		return queryString;
	
	queryString += "action=rfc";
	queryString += "&tipo=" + tipo.value;
	queryString += "&primer_nombre=" + tmpNombre;
	queryString += "&fecha_nacimiento=" + document.getElementById( preffix + "moral_fecha").value;
	
	return queryString;
}

// Reemplaza acentos de las palabras
function replaceAccents( value ) {
	return value.replace(/[\xc0-\xc5]/g,"A").replace(/[\xc8-\xcb]/g,"E").replace(/[\xcc-\xcf]/g,"I").replace(/[\xd2-\xd6]/g,"O").replace(/[\xd9-\xdc]/g,"U");
}