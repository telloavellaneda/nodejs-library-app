/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var clickedButton = false;

function check()
{
    if (clickedButton)
    {
        clickedButton = false;
        return true;
    }
    else
    {
        return false;
    }
}
