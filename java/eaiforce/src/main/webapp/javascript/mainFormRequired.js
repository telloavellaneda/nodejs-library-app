// JavaScript Document
function requiredFlag( objectName ) {
	var formObject = document.getElementById( objectName );
	var formIndicator = document.getElementById( objectName + "_req" );

	if ( formObject == null || formIndicator == null )
		return;
	formIndicator.style.visibility = ( formObject.value == "" ) ? "visible" : "hidden";
}

function checkRequired() {
	var section = document.getElementById("section");
	
	if ( section.value == "section0" ) return sectionZeroCheckRequired();
	
	return true;
}

function sectionZeroCheckRequired() {
	var result = true;
	
	if ( getObjectValue("pre_id_banco") == "" ) return false; 
	if ( getObjectValue("pre_importe_solic") == "" ) return false;

	if ( getObjectValue("pre_tipo") == "" ) return false;
	if ( getObjectValue("pre_apellido_paterno") == "" ) return false;
	if ( getObjectValue("pre_primer_nombre") == "" ) return false;
	if ( getObjectValue("pre_fecha_nacimiento") == "" ) return false;
	if ( getObjectValue("pre_anios") == "" ) return false;
	if ( getObjectValue("pre_meses") == "" ) return false;
	if ( getObjectValue("pre_rfc") == "" ) return false;
	if ( getObjectValue("pre_homoclave") == "" ) return false;
	if ( getObjectValue("pre_sexo") == "" ) return false;
	if ( getObjectValue("pre_email") == "" ) return false;

	if ( getObjectValue("pre_cel_lada") == "" ) return false;
	if ( getObjectValue("pre_cel_numero") == "" ) return false;

	if ( getObjectValue("pre_tipo") == "2" ) {
		if ( getObjectValue("pre_moral_nombre") == "" ) return false;
		if ( getObjectValue("pre_moral_fecha") == "" ) return false;
		if ( getObjectValue("pre_moral_rfc") == "" ) return false;
		if ( getObjectValue("pre_moral_homoclave") == "" ) return false;
		if ( getObjectValue("pre_moral_anios") == "" ) return false;
		if ( getObjectValue("pre_moral_meses") == "" ) return false;
	}
	
	return result;
}
