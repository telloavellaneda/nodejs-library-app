
/*
 * Copyright (C) 2017 Ashley Heyeral Ruiz Fernandez
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.service;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/* *****************************************************************************
 * @author grc  22-jun-2013 12:38:40  Description: 
 * MessageServiceIml.java  
 * *****************************************************************************
 */
@Service
public class MessageServiceIml implements MessageService
{

    private static final Logger log = Logger.getLogger(MessageServiceIml.class);

    /**
     * Mensaje informativo
     *
     * @param title
     * @param description
     */
    public void addInfo(String title, String description)
    {
        try
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, title, description));
        } catch (Exception e)
        {
                  log.error("Ocurrio un error en el metodo CapturaEncuestaAnterioriServiceImpl.addInfo", e);
        }
    }

    /**
     * Mensaje de error
     *
     * @param title
     * @param description
     */
    public void addError(String title, String description)
    {
        try
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, title, description));
        } catch (Exception e)
        {
            //  log.error("Ocurrio un error en el metodo CapturaEncuestaAnterioriServiceImpl.addInfo", e);
        }
    }

    /**
     * Mensajes de advertencia
     *
     * @param title
     * @param description
     */
    public void addWarn(String title, String description)
    {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, title, description));
    }

    /**
     *
     * @param title
     * @param description
     */
    public void addFatal(String title, String description)
    {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, title, description));
    }
}
