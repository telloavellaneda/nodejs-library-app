$ = jQuery;

$(document).ready(function() {
    if ($('#archivos-form\\:area').attr("value") == '1')
    {
        $('.areaInter').css("display", "none");
        $('.areaInterLabel').css("display", "none");        
    }
});
