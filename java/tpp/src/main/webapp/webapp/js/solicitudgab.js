$ = jQuery;

/* $(function() {
    var img = $("#form\\:showimage").attr("value");    
    if (img == '1')
    {        
        imagend.show();
        alert(img);
    }
}); */

//$( document ).ready(function() {
//        imagend.show();
//});

function showConfirm(gabineteId)
{
    $("#form\\:gabineteId").attr("value", gabineteId);
    confirmacion.show();
}
