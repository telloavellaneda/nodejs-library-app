/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$ = jQuery;

function validarCantidades(idmedicamento, cantidadPendiente, existencias)
{
    var antidadSurtiur = parseInt($(".class-cantidad" + idmedicamento).attr("value"));

    if (antidadSurtiur > cantidadPendiente) {
        alert("La cantidad a surtir no puede ser mayor que la cantidad pendiente");
        $(".class-cantidad" + idmedicamento).focus();
        return null;
    }
    else if(antidadSurtiur > existencias)
    {
        alert("No hay suficientes existencias para surtir el medicamento completo");
        $(".class-cantidad" + idmedicamento).focus();
        return null;
    }
}
