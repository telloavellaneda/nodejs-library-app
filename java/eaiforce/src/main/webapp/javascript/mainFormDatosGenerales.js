function calcularEdad(objeto) {
	var fecha = objeto.value;
	
	if ( fecha == "" ) {
	    getRelatives(objeto,"anios").value = "";
	    getRelatives(objeto,"meses").value = "";
		return;
	}
    
	// Si la fecha es correcta, calculamos la edad
	var values = fecha.split("/");
	var dia = values[0];
	var mes = values[1];
	var ano = values[2];
 
    var fecha_hoy = new Date();
    var ahora_ano = fecha_hoy.getYear();
    var ahora_mes = fecha_hoy.getMonth() + 1;
    var ahora_dia = fecha_hoy.getDate();
 
    // realizamos el calculo
    var edad = (ahora_ano + 1900) - ano;
    if ( ahora_mes < mes ) {
        edad--;
    }
    if ((mes == ahora_mes) && (ahora_dia < dia)) {
        edad--;
    }
    if (edad > 1900) {
        edad -= 1900;
    }
 
	// calculamos los meses
    var meses = 0;
    if(ahora_mes > mes)
        meses = ahora_mes-mes;
    if(ahora_mes < mes)
        meses = 12 - (mes-ahora_mes);
    if(ahora_mes == mes && dia > ahora_dia)
        meses = 11;
 
    // calculamos los dias
    var dias = 0;
	 var ultimoDiaMes = 0;
    if( ahora_dia > dia )
        dias = ahora_dia - dia;
    if( ahora_dia < dia ) {
        ultimoDiaMes = new Date(ahora_ano, ahora_mes, 0);
        dias = ultimoDiaMes.getDate() - (dia-ahora_dia);
    }
    getRelatives(objeto,"anios").value = edad;
    getRelatives(objeto,"meses").value = meses;
	requiredFlag( getRelatives(objeto,"anios").id );
	requiredFlag( getRelatives(objeto,"meses").id );
}