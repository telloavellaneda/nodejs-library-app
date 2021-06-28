$ = jQuery;

function validarNumero(campo) {
    if (!$.isNumeric(campo.value))
    {    
        valNum();
        campo.value = "";
    }
    else
    {
        onEdit();
    }
}

function validarEgresos(campo) {
    if (!$.isNumeric(campo.value))
    {    
        valNum();
        campo.value = "";
    }
    else
    {
        sumaLiqEgreso();
    }
}