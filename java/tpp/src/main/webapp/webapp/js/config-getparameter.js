/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$ = jQuery;
$(function()
{
    var recId = $.getUrlVar('recId');
    var sinonimo = $.getUrlVar('sinonimo');
    var expediente = $.getUrlVar('expediente');
    var numIngreso = $.getUrlVar('numIngreso');
    var gabinete = $.getUrlVar('gabinete');
    var persId = $.getUrlVar('persId');
    var area = $.getUrlVar('area');
    $(".expediente input").attr("value", expediente);
    $(".numIngreso input").attr("value", numIngreso);
    $(".sinonimo input").attr("value", sinonimo);
    $(".recId input").attr("value", recId);
    $(".gabinete input").attr("value", gabinete);
    $(".persId input").attr("value", persId);
    $(".area input").attr("value", area);
    resetForm();        
});

