/*
 * Copyright (C) 2017 Ashley Heyeral Ruiz Fernandez
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.controller;

import javax.annotation.PostConstruct;
import mx.com.eai.tpp.dao.UsuarioDao;
import mx.com.eai.tpp.form.CambioContraseniaForm;
import mx.com.eai.tpp.service.MessageService;
import mx.com.eai.tpp.service.SessionService;
import mx.com.eai.tpp.service.UsuarioService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  7/03/2015 10:24:22 AM  Description: 
 * CambioContraseniaController.java  
 * *****************************************************************************
 */
@Controller
@Scope(value = "view")
public class CambioContraseniaController
{
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private MessageService messageService;
    private CambioContraseniaForm cambioContraseniaForm;
    private Logger log = Logger.getLogger(this.getClass());

    public CambioContraseniaController()
    {
        this.cambioContraseniaForm = new CambioContraseniaForm();
    }

    @PostConstruct
    public void init()
    {
        try
        {
            this.cambioContraseniaForm.setUsuario(this.sessionService.getUsuarioFromSession());
        } catch (Exception e)
        {
            this.log.error("Ocurrio un error en el init: ", e);
        }
    }

    public void cambiarcontrasenia()
    {
        try
        {
            String passSha = this.usuarioService.encondePasswdWithSha(this.cambioContraseniaForm.getContraseniaActual());
            if (passSha.equals(this.cambioContraseniaForm.getUsuario().getContrasenia()))
            {
                this.cambioContraseniaForm.getUsuario().setContrasenia(this.usuarioService.encondePasswdWithSha(this.cambioContraseniaForm.getContraseniaNueva()));
                this.usuarioDao.saveOrUpdate(this.cambioContraseniaForm.getUsuario());
                this.messageService.addInfo("Contraseña actualizada", "La contraseña se actualizó de forma correcta");
                this.cambioContraseniaForm.setMostrarPanel(false);
            }
            else
            {
                this.messageService.addWarn("Contraseña incorrecta", "La contraseña actual introducida es incorrecta");
            }
        } catch (Exception e)
        {
            this.log.error("Ocurrio un error cambiando la contraseña: ", e);
        }
    }

    /**
     * @return the cambioContraseniaForm
     */
    public CambioContraseniaForm getCambioContraseniaForm()
    {
        return cambioContraseniaForm;
    }

    /**
     * @param cambioContraseniaForm the cambioContraseniaForm to set
     */
    public void setCambioContraseniaForm(CambioContraseniaForm cambioContraseniaForm)
    {
        this.cambioContraseniaForm = cambioContraseniaForm;
    }
}
