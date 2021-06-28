/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.controller;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import mx.com.eai.tpp.form.SolicitudesRevisadasForm;
import mx.com.eai.tpp.model.Dsolicitud;
import mx.com.eai.tpp.service.SolicitudService;
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  27-dic-2017 20:18:19  Description: 
 * SolicitudesRevisadasController.java  
 * *****************************************************************************
 */
@Controller
@Scope(value = "view")
public class SolicitudesRevisadasController
{

    @Autowired
    private SolicitudService solicitudService;

    private SolicitudesRevisadasForm solicitudesRevisadasForm;
    private Logger log = Logger.getLogger(this.getClass());

    public SolicitudesRevisadasController()
    {
        this.solicitudesRevisadasForm = new SolicitudesRevisadasForm();
    }

    @PostConstruct
    public void init()
    {
        try
        {

        }
        catch (Exception e)
        {
            log.error("Ocurrio un error: ", e);
        }
    }

    public void buscarSolicitudes()
    {
        try
        {
            this.solicitudesRevisadasForm.setSolicitudes(this.solicitudService.buscarSolicitudesRevisadasByFecha(
                    this.solicitudesRevisadasForm.getBusquedaFechaInicio(),
                    this.solicitudesRevisadasForm.getBusquedaFechaFin(),
                    this.solicitudesRevisadasForm.getEstatusSeleccionado(),
                    this.solicitudesRevisadasForm.getFolioSolicitud()
            ));
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error: ", e);
        }
    }

    public void redirectSolicitud(SelectEvent event)
    {
        try
        {
            Dsolicitud sol = (Dsolicitud) event.getObject();
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + "/webapp/private/solicitud/detalle-solicitud.jsf?numSolicitud=" + sol.getSolicitudid() + "&revisar=T");
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error: ", e);
        }
    }

    /**
     * @return the solicitudesRevisadasForm
     */
    public SolicitudesRevisadasForm getSolicitudesRevisadasForm()
    {
        return solicitudesRevisadasForm;
    }

    /**
     * @param solicitudesRevisadasForm the solicitudesRevisadasForm to set
     */
    public void setSolicitudesRevisadasForm(SolicitudesRevisadasForm solicitudesRevisadasForm)
    {
        this.solicitudesRevisadasForm = solicitudesRevisadasForm;
    }
}
