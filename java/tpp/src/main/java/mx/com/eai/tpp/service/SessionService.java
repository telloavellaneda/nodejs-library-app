
/*
 * Copyright (C) 2017 Ashley Heyeral Ruiz Fernandez
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.service;

import mx.com.eai.tpp.model.Dusuario;

/* *****************************************************************************
 * @author grc  20-jul-2013 13:10:19  Description: 
 * SessionService.java 
 * *****************************************************************************
 */
public interface SessionService
{

    public void createSession(String nameSession, Object objectSession) throws Exception;

    public Object getSessionFromContext(String nameSession) throws Exception;

    public Dusuario getUsuarioFromSession() throws Exception;

    public void removeObjectFromSession(String nameSession) throws Exception;

    public void createUserGroupSession() throws Exception;
    
    public String getNombreUsuario() throws Exception;
}
