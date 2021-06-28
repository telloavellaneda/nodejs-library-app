/*
 * Copyright (C) 2017 Ashley Heyeral Ruiz Fernandez
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.form;

import mx.com.eai.tpp.model.Dusuario;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  7/03/2015 10:24:40 AM  Description: 
 * CambioContraseniaForm.java  
 * *****************************************************************************
 */
public class CambioContraseniaForm
{
    private String contraseniaActual;
    private String contraseniaNueva;
    private String confirmacionContrasenia;
    private Dusuario usuario;
    private boolean mostrarPanel;
    
    public CambioContraseniaForm()
    {
        this.usuario = new Dusuario();
        this.mostrarPanel = true;
    }

    /**
     * @return the contraseniaActual
     */
    public String getContraseniaActual()
    {
        return contraseniaActual;
    }

    /**
     * @param contraseniaActual the contraseniaActual to set
     */
    public void setContraseniaActual(String contraseniaActual)
    {
        this.contraseniaActual = contraseniaActual;
    }

    /**
     * @return the confirmacionContrasenia
     */
    public String getConfirmacionContrasenia()
    {
        return confirmacionContrasenia;
    }

    /**
     * @param confirmacionContrasenia the confirmacionContrasenia to set
     */
    public void setConfirmacionContrasenia(String confirmacionContrasenia)
    {
        this.confirmacionContrasenia = confirmacionContrasenia;
    }

    /**
     * @return the usuario
     */
    public Dusuario getUsuario()
    {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Dusuario usuario)
    {
        this.usuario = usuario;
    }

    /**
     * @return the contraseniaNueva
     */
    public String getContraseniaNueva()
    {
        return contraseniaNueva;
    }

    /**
     * @param contraseniaNueva the contraseniaNueva to set
     */
    public void setContraseniaNueva(String contraseniaNueva)
    {
        this.contraseniaNueva = contraseniaNueva;
    }

    /**
     * @return the mostrarPanel
     */
    public boolean isMostrarPanel()
    {
        return mostrarPanel;
    }

    /**
     * @param mostrarPanel the mostrarPanel to set
     */
    public void setMostrarPanel(boolean mostrarPanel)
    {
        this.mostrarPanel = mostrarPanel;
    }
}
