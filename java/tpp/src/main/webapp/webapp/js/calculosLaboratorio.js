/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$ = jQuery;

function calculaSuma()
{
    var campo3 = parseFloat($(".campo-5").attr("value"));
    var campo4 = parseFloat($(".campo-6").attr("value"));
    var resultado = campo4 + campo3;
    alert(resultado);
    $(".campo-7").attr("value", resultado);
}
