var grecaptchaValidation = false;
var widgetId;

function checkForm() {	
	if ( checkFieldValues() ) {
		toggleFunctionality( true );
		
		if (grecaptchaValidation) {
			document.getElementById("button").style.display = "none";			
			
			widgetId = grecaptcha.render("grecaptchaDiv", {
				'sitekey' : '6LdrgxETAAAAADvTdpTWPivcXujfoUgRLNHC5zmL',
				'callback' : checkgrecaptcha,
				'size' : 'normal',
				'expired-callback': resetgrepcaptcha
			});
		} else 
			triggerValidation();
	} else 
		document.getElementById("mensaje").innerHTML = "Ingrese usuario y contrase&ntilde;a";
}

function checkFieldValues() {
	var usuario = document.getElementById("usuario").value;
	var password = document.getElementById("password").value;
	if ( usuario != "" && password != "" )
		return true;
	else
		return false;
}

function checkgrecaptcha() {
	request.url = "validateLogin";
	request.queryString = "?recaptcha=" + grecaptcha.getResponse(widgetId);
	request.message = "";
	request.object = document.getElementById("mensaje");
	
	makeAajaxCall(request, checkgrecaptchaResponse);
}

function checkgrecaptchaResponse(responseText) {
	var response = JSON.parse(responseText);
	var code = response["code"];
	var message = response["message"];
	
	if ( code == "0" && message == "true" )
		triggerValidation();
}

function triggerValidation() {
	var usuario = document.getElementById("usuario").value;
	var password = document.getElementById("password").value;
	validateLogin( btoa("usuario=" + usuario + "&password=" + password) );
}

function toggleFunctionality( option ) {
	document.getElementById("usuario").disabled = option;
	document.getElementById("password").disabled = option;
	document.getElementById("button").disabled = option;
}

function validateLogin(x) {	
	request.url = "validateLogin";
	request.queryString = "?x=" + x;
	request.message = "Por favor espere...";
	request.object = document.getElementById("mensaje");
	
	makeAajaxCall(request, validateLoginResponse);
}

function validateLoginResponse(responseText) {
	var response = JSON.parse(responseText);
	var code = response["code"];
	var message = response["message"];
	
	document.getElementById("mensaje").innerHTML = message;
	
	if ( code == "0" )
		location.href = "home";
	else
		initLogin();
}

function initLogin() {
	toggleFunctionality(false);
	
	if (grecaptchaValidation) {
		document.getElementById("button").style.display = "";
		document.getElementById("grecaptchaDiv").style.display = "none";
		document.getElementById("usuario").onfocus = resetgrepcaptcha;
		document.getElementById("password").onfocus = resetgrepcaptcha;	
		document.getElementById("button").onfocus = resetgrepcaptcha;
	}
}

function resetgrepcaptcha() {
	location.reload();
}

function borraMensaje() {
	document.getElementById("mensaje").innerHTML = "";		
}

function validaCorreo( objeto ) {
	objeto.value = ( !objeto.value.match(/^([a-zA-Z0-9._-]+@[a-z0-9.-]+\.[a-z]{2,3})$/) ) ? "" : objeto.value;  
}

function validaPassword ( objeto ) {
	objeto.value = ( !objeto.value.match(/^[a-zA-Z]+$/) ) ? "" : objeto.value; 
}
