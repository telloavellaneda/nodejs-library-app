$ = jQuery;

$('body').on('keydown', 'input, select', function(e) {
    var self = $(this)
            , form = self.parents('form:eq(0)')
            , focusable
            , next
            ;
    if (e.keyCode === 13) {
        focusable = form.find('input,a,select,button,textarea').filter(':visible');
        next = focusable.eq(focusable.index(this) + 1);
        if (next.length) {
            next.focus();
        } else {
            form.submit();
        }
        return false;
    }
});

function validarNumero(campo) {
    if (!$.isNumeric(campo.value))
    {    
        valNum();
        campo.value = "0";
    }
    else
    {
        onEdit();
    }
}

function borrar(campo)
{
    campo.value = "";
}

