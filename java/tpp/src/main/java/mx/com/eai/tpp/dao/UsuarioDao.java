
/*
 * Copyright (C) 2017 Ashley Heyeral Ruiz Fernandez
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.dao;

import java.util.List;
import mx.com.eai.tpp.model.Dusuario;

/* *****************************************************************************
 * @author José Eduardo Figueroa Magaña  18-jul-2013 12:15:02  Description: 
 * UsuarioDao.java  
 * *****************************************************************************
 */
public interface UsuarioDao extends AbstractDao<Dusuario, Integer>
{    
    public Dusuario getEncontrarUsuario(String usuario) throws Exception;
    
    public List<Dusuario> usuariosAdministradores() throws Exception;
    
    public List<Dusuario> usuariosByNivel(Dusuario usuario) throws Exception;
    
    public boolean validaUsuario(String usuario) throws Exception;
}
