package mx.com.eai.tpp.form;

import java.util.List;
import mx.com.eai.tpp.model.Dusuario;

/**
 * Created by Luis on 13/06/2015.
 */
public class CatalogoUsuariosForm
{
    private List<Dusuario> usuarios;
    private Dusuario usuario;
    private Dusuario usuarioSesion;
    private boolean modificarUsuario;
    private boolean modificarContrasenia;
    private boolean nuevoUsuario;
    private boolean matriz;
    private String contrasenia;
    private String contrasenia2;

    public CatalogoUsuariosForm()
    {
        this.usuario = new Dusuario();
        this.modificarUsuario = false;
        this.modificarContrasenia = false;
        this.nuevoUsuario = false;
    }

    public Dusuario getUsuario()
    {
        return usuario;
    }

    public void setUsuario(Dusuario usuario)
    {
        this.usuario = usuario;
    }

    public boolean isModificarUsuario()
    {
        return modificarUsuario;
    }

    public void setModificarUsuario(boolean modificarUsuario)
    {
        this.modificarUsuario = modificarUsuario;
    }

    public boolean isModificarContrasenia()
    {
        return modificarContrasenia;
    }

    public void setModificarContrasenia(boolean modificarContrasenia)
    {
        this.modificarContrasenia = modificarContrasenia;
    }

    public boolean isNuevoUsuario()
    {
        return nuevoUsuario;
    }

    public void setNuevoUsuario(boolean nuevoUsuario)
    {
        this.nuevoUsuario = nuevoUsuario;
    }

    public String getContrasenia()
    {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia)
    {
        this.contrasenia = contrasenia;
    }

    public String getContrasenia2()
    {
        return contrasenia2;
    }

    public void setContrasenia2(String contrasenia2)
    {
        this.contrasenia2 = contrasenia2;
    }

    public boolean isMatriz()
    {
        return matriz;
    }

    public void setMatriz(boolean matriz)
    {
        this.matriz = matriz;
    }
    
    /**
     * @return the usuarios
     */
    public List<Dusuario> getUsuarios()
    {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<Dusuario> usuarios)
    {
        this.usuarios = usuarios;
    }

    /**
     * @return the usuarioSesion
     */
    public Dusuario getUsuarioSesion()
    {
        return usuarioSesion;
    }

    /**
     * @param usuarioSesion the usuarioSesion to set
     */
    public void setUsuarioSesion(Dusuario usuarioSesion)
    {
        this.usuarioSesion = usuarioSesion;
    }
}
