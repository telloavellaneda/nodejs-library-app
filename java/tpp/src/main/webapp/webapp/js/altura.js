/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$ = jQuery;
$(function() { 
  var myWidth = 0, myHeight = 0; 
  if( typeof( window.innerWidth ) == 'number' ) { 
    //No-IE 
    myWidth = window.innerWidth; 
    myHeight = window.innerHeight; 
  } else if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) { 
    //IE 6+ 
    myWidth = document.documentElement.clientWidth; 
    myHeight = document.documentElement.clientHeight; 
  } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) { 
    //IE 4 compatible 
    myWidth = document.body.clientWidth; 
    myHeight = document.body.clientHeight; 
  }
     $(".altura input").attr("value", myHeight);

});
