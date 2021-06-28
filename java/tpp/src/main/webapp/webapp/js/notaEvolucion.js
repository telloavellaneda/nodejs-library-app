$ = jQuery;

function solicito_interconsulta()
{
    if($('.interconsulta input').prop('checked')==true)
    {
        interconsulta.show();
        pendiente.disable();
    }
    
}

function mismasIndicaciones()
{
    if($('.mismasInd input').prop('checked') == false)
    {
        mismaIndica.show();
        pendiente.disable();
    }
}