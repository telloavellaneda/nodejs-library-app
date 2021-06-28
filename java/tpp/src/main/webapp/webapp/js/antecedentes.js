/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
$ = jQuery;

function validaAlimento()
{
    if($('.alimentos input').prop('checked')==true)
    {
        PF('buscaralimentoaler').show();
    }
    else
    {
        limpiarAlimentos();
        
    }
}

function validaMedicamento()
{
    if($('.medicamento input').prop('checked')==true)
    {
        PF('buscarmedicamento').show();
    }
    else
    {
        limpiarMedicamentos();
    }
}

function validaDiagnosticos1(idArea)
{
    $("#antecedentes-form\\:key-area").attr("value", idArea);       
    if($('.diag1 input').prop('checked')==true)
    {       
        PF('buscarProcedimientos').show();
    }
    else
    {
        borrarDiagnosticosRadio();
;
    }
}

function validaDiagnosticos2(idArea)
{
    $("#antecedentes-form\\:key-area").attr("value", idArea);       
    if($('.diag2 input').prop('checked')==true)
    {       
        PF('buscarDiagnosticos').show();
    }
    else
    {
        borrarDiagnosticosRadio();
    }
}
function validaDiagnosticos3(idArea)
{
    $("#antecedentes-form\\:key-area").attr("value", idArea);       
    if($('.diag3 input').prop('checked')==true)
    {       
        PF('buscarDiagnosticos').show();
    }
    else
    {
        borrarDiagnosticosRadio();
    }
}
function validaDiagnosticos4(idArea)
{
    $("#antecedentes-form\\:key-area").attr("value", idArea);       
    if($('.diag4 input').prop('checked')==true)
    {       
        PF('buscarDiagnosticos').show();
    }
    else
    {
        borrarDiagnosticosRadio();
    }
}

function validaDiagnosticos5(idArea)
{
    $("#antecedentes-form\\:key-area").attr("value", idArea);       
    if($('.diag5 input').prop('checked')==true)
    {       
        PF('buscarDiagnosticos').show();
    }
    else
    {
        borrarDiagnosticosRadio();
    }
}
function validaDiagnosticos6(idArea)
{
    $("#antecedentes-form\\:key-area").attr("value", idArea);       
    if($('.diag6 input').prop('checked')==true)
    {       
        PF('buscarDiagnosticos').show();
    }
    else
    {
        borrarDiagnosticosRadio();
    }
}
function validaDiagnosticos7(idArea)
{
    $("#antecedentes-form\\:key-area").attr("value", idArea);       
    if($('.diag7 input').prop('checked')==true)
    {       
        PF('buscarDiagnosticos').show();
    }
    else
    {
        borrarDiagnosticosRadio();
    }
}
function validaDiagnosticos8(idArea)
{
    $("#antecedentes-form\\:key-area").attr("value", idArea);       
    if($('.diag8 input').prop('checked')==true)
    {       
        PF('buscarDiagnosticos').show();
    }
    else
    {
        borrarDiagnosticosRadio();
    }
}
function validaDiagnosticos9(idArea)
{
    $("#antecedentes-form\\:key-area").attr("value", idArea);       
    if($('.diag9 input').prop('checked')==true)
    {       
        PF('buscarDiagnosticos').show();
    }
    else
    {
        borrarDiagnosticosRadio();
    }
}
function validaDiagnosticos10(idArea)
{
    $("#antecedentes-form\\:key-area").attr("value", idArea);       
    if($('.diag10 input').prop('checked')==true)
    {       
        PF('buscarDiagnosticos').show();
    }
    else
    {
        borrarDiagnosticosRadio();
    }
}
function validaDiagnosticos11(idArea)
{
    $("#antecedentes-form\\:key-area").attr("value", idArea);       
    if($('.diag11 input').prop('checked')==true)
    {       
        PF('buscarDiagnosticos').show();
    }
    else
    {
        borrarDiagnosticosRadio();
    }
}
function validaDiagnosticos12(idArea)
{
    $("#antecedentes-form\\:key-area").attr("value", idArea);       
    if($('.diag12 input').prop('checked')==true)
    {       
        PF('buscarDiagnosticos').show();
    }
    else
    {
        borrarDiagnosticosRadio();
    }
}
function validaDiagnosticos13(idArea)
{
    $("#antecedentes-form\\:key-area").attr("value", idArea);       
    if($('.diag13 input').prop('checked')==true)
    {       
        PF('buscarDiagnosticos').show();
    }
    else
    {
        borrarDiagnosticosRadio();
    }
}
function validaDiagnosticos14(idArea)
{
    $("#antecedentes-form\\:key-area").attr("value", idArea);       
    if($('.diag14 input').prop('checked')==true)
    {       
        PF('buscarDiagnosticos').show();
    }
    else
    {
        borrarDiagnosticosRadio();
    }
}