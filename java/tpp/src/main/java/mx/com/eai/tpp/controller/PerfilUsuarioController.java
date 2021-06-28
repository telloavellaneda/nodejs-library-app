
/*
 * Copyright (C) 2017 Ashley Heyeral Ruiz Fernandez
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.controller;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import mx.com.eai.tpp.model.Dsolicitud;
import mx.com.eai.tpp.model.Dusuario;
import mx.com.eai.tpp.service.SessionService;
import mx.com.eai.tpp.service.SolicitudService;
import mx.com.eai.tpp.util.Constant;
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/* *****************************************************************************
 * @author grc  30-jul-2013 8:59:52  Description: 
 * PerfilUsuarioController.java  
 * *****************************************************************************
 */
@Controller
@Scope(value = "view")
public class PerfilUsuarioController
{
    @Autowired
    private SessionService sessionService;
    @Autowired
    private SolicitudService solicitudService;
    private Logger log = Logger.getLogger(this.getClass());
    private Dusuario usuario;
    private List<Dsolicitud> solicitudes;

    public PerfilUsuarioController()
    {
        this.usuario = new Dusuario();
    }

    @PostConstruct
    public void init()
    {
        try
        {
            this.usuario = this.sessionService.getUsuarioFromSession();
            if (usuario.getTipousuarioid().getTipousuarioid() != Constant.ID_TIPO_USUARIO_ADMINISTRACION)
            {
                setSolicitudes(this.solicitudService.obtenerMisSolicitudes(this.usuario));
            }
        }
        catch (Exception e)
        {
            this.log.error("Error en PerfilUsuarioController: ", e);
        }
    }

    public void redirectSolicitud(SelectEvent event)
    {
        try
        {
            Dsolicitud sol = (Dsolicitud) event.getObject();
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + "/webapp/private/solicitud/detalle-solicitud.jsf?numSolicitud=" + sol.getSolicitudid());
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error: ", e);
        }
    }

    public void redirect(int tipo)
    {
        try
        {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            if(tipo == 1)
            {
            context.redirect(context.getRequestContextPath() + "/webapp/private/catalogos/catalogo-usuarios.jsf");
            }
            else if(tipo == 2)
            {
                context.redirect(context.getRequestContextPath() + "/webapp/private/solicitud/solicitudes-revisadas.jsf");
            }
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error: ", e);
        }

    }

    public void cerrarSesion()
    {
        try
        {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + "/logout");
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error cerrando la sesion: ", e);
        }
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
     * @return the solicitudes
     */
    public List<Dsolicitud> getSolicitudes()
    {
        return solicitudes;
    }

    /**
     * @param solicitudes the solicitudes to set
     */
    public void setSolicitudes(List<Dsolicitud> solicitudes)
    {
        this.solicitudes = solicitudes;
    }
}
