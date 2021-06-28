/*
 * Copyright (C) 2017 Ashley Heyeral Ruiz Fernandez
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.service;

import mx.com.eai.tpp.dao.UsuarioDao;
import mx.com.eai.tpp.model.Actor;
import mx.com.eai.tpp.model.Dusuario;
import mx.com.eai.tpp.util.Constant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/* *****************************************************************************
 * @author grc  20-jul-2013 13:10:19  Description: 
 * AutenticacionServiceImpl.java 
 * *****************************************************************************
 */
public class AutenticacionServiceImpl implements UserDetailsService, AutenticacionService
{

    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private MessageService messageService;
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * spring: Realiza el match del usuario, clave introducida con el usuario,
     * clave disponible en BD
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Actor actor = new Actor();
        try
        {
            Dusuario usuario = this.usuarioDao.getEncontrarUsuario(username);

            if (usuario != null)//si el usuario existe
            {
                actor.setdUsuario(usuario);
                this.sessionService.createSession(Constant.USUARIO_SESSION, usuario);
                this.sessionService.createUserGroupSession();
            }
            else
            {
                this.messageService.addWarn("Credenciales Invalidas", "El nombre de usuario no existe");
            }
        } catch (Exception e)
        {
            this.log.error("Ha ocurrido un error en la autenticación", e);
            this.messageService.addError("Error el la autenticación", " Ha ocurrido un error en la autenticación");
        }
        return actor;
    }

    /**
     * Eliminar los datos del usuario
     */
    public void removeSessionUser()
    {
        try
        {
            log.info("Entro al metodo removeSessionUser");
            this.sessionService.removeObjectFromSession(Constant.USUARIO_SESSION);
        } catch (Exception e)
        {
            this.log.error("Ha ocurrido un error al dar de baja la session del usuario", e);
        }
    }
}
