(function(a) {
    a.fn.validCampoFranz = function(b) {
        a(this).on({keypress: function(a) {
                if (a.keyCode === 13) {
                    $(".botonBuscar").trigger("click");
                }
                var c = a.which, d = a.keyCode, e = String.fromCharCode(c).toLowerCase(), f = b;
                (-1 != f.indexOf(e) || 9 == d || 37 != c && 37 == d || 39 == d && 39 != c || 8 == d || 46 == d && 46 != c) && 161 != c || a.preventDefault()
            }})
    }
})(jQuery);

$(function() {
    //Para escribir solo letras
    $('.campoAlfabetico').validCampoFranz(' abcdefghijklmnñopqrstuvwxyzáéíóúÁÉÍÓÚ\n');

    //Para escribir solo numeros    
    $('.campoNumerico').validCampoFranz('0123456789.');
});

function validarNumerico(campo) {
    $(campo).validCampoFranz('0123456789.');
}
