/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.eai.tpp.service;

import java.util.List;
import mx.com.eai.tpp.dao.UsuarioDao;
import mx.com.eai.tpp.form.CatalogoUsuariosForm;
import mx.com.eai.tpp.model.Dusuario;
import mx.com.eai.tpp.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author AshleyHeyeral
 */
@Service
public class UsuarioServiceImpl implements UsuarioService
{
    @Autowired
    private UsuarioDao usuarioDao;

    public void saveOrUpdate(Dusuario usuario) throws Exception
    {
        this.usuarioDao.saveOrUpdate(usuario);
    }

    public String encondePasswdWithSha(String valor) throws Exception
    {
        ShaPasswordEncoder sha = new ShaPasswordEncoder();
        valor = sha.encodePassword(valor, null);
        return valor;
    }

    public boolean validaUsuario(String usuario) throws Exception
    {
        return this.usuarioDao.validaUsuario(usuario);
    }

    public void guardarUsuario(CatalogoUsuariosForm catalogoUsuariosForm) throws Exception
    {
        catalogoUsuariosForm.getUsuario().setCuentabloqueada(Constant.ACTIVO);
        catalogoUsuariosForm.getUsuario().setCuentaexpirada(Constant.ACTIVO);
        catalogoUsuariosForm.getUsuario().setCredencialexpirada(Constant.ACTIVO);
        catalogoUsuariosForm.getUsuario().setActivo(Constant.ACTIVO);
        catalogoUsuariosForm.getUsuario().setNombre(catalogoUsuariosForm.getUsuario().getNombre().toUpperCase());
        catalogoUsuariosForm.getUsuario().setApaterno(catalogoUsuariosForm.getUsuario().getApaterno().toUpperCase());
        catalogoUsuariosForm.getUsuario().setAmaterno(catalogoUsuariosForm.getUsuario().getAmaterno().toUpperCase());
        if (catalogoUsuariosForm.isNuevoUsuario() && catalogoUsuariosForm.getUsuario().getTipousuarioid().getTipousuarioid() != Constant.ID_TIPO_USUARIO_ADMINISTRACION)
        {
            catalogoUsuariosForm.getUsuario().setUsuariopadreid(catalogoUsuariosForm.getUsuarioSesion());
        }
        if (catalogoUsuariosForm.isNuevoUsuario() || catalogoUsuariosForm.isModificarContrasenia())
        {
            catalogoUsuariosForm.getUsuario().setContrasenia(this.encondePasswdWithSha(catalogoUsuariosForm.getContrasenia()));
        }
        this.usuarioDao.saveOrUpdate(catalogoUsuariosForm.getUsuario());
        if (catalogoUsuariosForm.isNuevoUsuario())
        {
            catalogoUsuariosForm.getUsuarios().add(catalogoUsuariosForm.getUsuario());
        }
    }
    
    public List<Dusuario> usuariosActivos() throws Exception
    {
        return this.usuarioDao.getAll();
    }
    
    public List<Dusuario> usuariosByNivel(Dusuario usuario) throws Exception
    {
        return usuarioDao.usuariosByNivel(usuario);
    }
}
