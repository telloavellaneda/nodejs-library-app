
/*
 * Copyright (C) 2017 Ashley Heyeral Ruiz Fernandez
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.service;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import mx.com.eai.tpp.dao.UsuarioDao;
import mx.com.eai.tpp.model.Dusuario;
import mx.com.eai.tpp.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* *****************************************************************************
 * @author grc  20-jul-2013 13:10:51  Description: 
 * SessionServiceImpl.java  
 * *****************************************************************************
 */
@Service
public class SessionServiceImpl implements SessionService
{

    @Autowired
    private UsuarioDao usuarioDao;

    /**
     * Subir objeto a session
     *
     * @param nameSession
     * @param objectOnSession
     * @throws Exception
     */
    public void createSession(String nameSession, Object objectOnSession) throws Exception
    {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);
        httpSession.setAttribute(nameSession, objectOnSession);
    }

    /**
     * Obtener la session
     *
     * @param nameSession
     * @return
     */
    public Object getSessionFromContext(String nameSession) throws Exception
    {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);
        Object session = httpSession.getAttribute(nameSession);
        return session;
    }

    /**
     * Baja el objeto de la session
     *
     * @param nameSession
     * @throws Exception
     */
    public void removeObjectFromSession(String nameSession) throws Exception
    {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);
        httpSession.removeAttribute(nameSession);
    }

    /**
     * Bajar de la sesión al objecto usuario
     *
     * @return
     * @throws Exception
     */
    public Dusuario getUsuarioFromSession() throws Exception
    {
        Dusuario usuario = (Dusuario) this.getSessionFromContext(Constant.USUARIO_SESSION);
        if (usuario != null)
        {
            usuario = this.usuarioDao.getEncontrarUsuario(usuario.getUsuario());
        }
        return usuario;
    }

    /**
     * Subir el grupo donde inició sessión el usuario
     *
     * @throws Exception
     */
    public void createUserGroupSession() throws Exception
    {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        ServletRequest servletRequest = (ServletRequest) context.getRequest();
//        int idSucursal = (Integer) servletRequest.getAttribute("idSucursal");
//        this.createSession(Constant.ID_SUCURSAL_SESSION, idSucursal);
    }

    public String getNombreUsuario() throws Exception
    {
        Dusuario usuario = this.getUsuarioFromSession();
        return usuario.getNombre() + " " + usuario.getApaterno();
    }
}
