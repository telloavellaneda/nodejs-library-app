$ = jQuery;

function displayRecibePaciente()
{    
    if ($(".recibe-panel2").css("display") == "none")
    {
        $('.recibe-panel2').css("display", "inline");
    }else
    {
        $('.recibe-panel2').css("display", "none");
    }
}