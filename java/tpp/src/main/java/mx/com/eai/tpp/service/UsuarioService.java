/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.eai.tpp.service;

import java.util.List;
import mx.com.eai.tpp.form.CatalogoUsuariosForm;
import mx.com.eai.tpp.model.Dusuario;

/**
 *
 * @author AshleyHeyeral
 */
public interface UsuarioService
{
    public String encondePasswdWithSha(String valor) throws Exception;
    
    public void saveOrUpdate(Dusuario usuario) throws Exception;

    public boolean validaUsuario(String usuario) throws Exception;

    public void guardarUsuario(CatalogoUsuariosForm catalogoUsuariosForm) throws Exception;
    
    public List<Dusuario> usuariosActivos() throws Exception;
    
    public List<Dusuario> usuariosByNivel(Dusuario usuario) throws Exception;
}
