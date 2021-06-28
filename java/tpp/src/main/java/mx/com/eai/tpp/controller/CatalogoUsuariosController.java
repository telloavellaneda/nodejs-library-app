package mx.com.eai.tpp.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import mx.com.eai.tpp.form.CatalogoUsuariosForm;
import mx.com.eai.tpp.model.Dusuario;
import mx.com.eai.tpp.service.MessageService;
import mx.com.eai.tpp.service.SessionService;
import mx.com.eai.tpp.service.UsuarioService;
import mx.com.eai.tpp.util.Constant;

/**
 * Created by Luis on 13/06/2015.
 */
@Controller
@Scope(value = "view")
public class CatalogoUsuariosController
{
    private Logger log = Logger.getLogger(this.getClass());
    private CatalogoUsuariosForm catalogoUsuariosForm;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private SessionService sessionService;

    public CatalogoUsuariosController()
    {
        this.catalogoUsuariosForm = new CatalogoUsuariosForm();
    }

    @PostConstruct
    public void init()
    {
        try
        {
            this.catalogoUsuariosForm.setUsuarioSesion(this.sessionService.getUsuarioFromSession());
            if (this.catalogoUsuariosForm.getUsuarioSesion().getTipousuarioid().getTipousuarioid() == Constant.ID_TIPO_USUARIO_ADMINISTRACION)
            {
                this.catalogoUsuariosForm.setUsuarios(this.usuarioService.usuariosActivos());
            }
            else
            {
                this.catalogoUsuariosForm.setUsuarios(this.usuarioService.usuariosByNivel(this.catalogoUsuariosForm.getUsuarioSesion()));
            }
        }
        catch (Exception e)
        {
            this.log.error("Error init CatalogoUsuariosController: ", e);
        }
    }

    public void nuevo()
    {
        this.catalogoUsuariosForm.setUsuario(new Dusuario());
        this.catalogoUsuariosForm.setModificarUsuario(true);
        this.catalogoUsuariosForm.setModificarContrasenia(true);
        this.catalogoUsuariosForm.setNuevoUsuario(true);
    }

    public void modificarUsuario(Dusuario usuario, int i)
    {
        try
        {
            this.catalogoUsuariosForm.setUsuario(usuario);
            this.catalogoUsuariosForm.setNuevoUsuario(false);
            if (i == 1)
            {
                this.catalogoUsuariosForm.setModificarUsuario(true);
                this.catalogoUsuariosForm.setModificarContrasenia(false);
            }
            else
            {
                this.catalogoUsuariosForm.setModificarUsuario(false);
                this.catalogoUsuariosForm.setModificarContrasenia(true);
            }
        }
        catch (Exception e)
        {
            this.log.error("Error al editar");
        }
    }

    public void validaUsuario()
    {
        try
        {
            if (this.usuarioService.validaUsuario(this.catalogoUsuariosForm.getUsuario().getUsuario()))
            {
                this.messageService.addError("Error", "El nombre del usuario ya existe!");
                this.catalogoUsuariosForm.getUsuario().setUsuario("");
            }
        }
        catch (Exception e)
        {
            this.log.error("Error al validar usuario: ", e);
        }
    }

    public void borrar()
    {
        try
        {
            this.catalogoUsuariosForm.getUsuario().setActivo(Constant.INACTIVO);
            this.usuarioService.saveOrUpdate(this.catalogoUsuariosForm.getUsuario());
            this.catalogoUsuariosForm.getUsuarios().remove(this.catalogoUsuariosForm.getUsuario());
            this.messageService.addInfo("Usuario", "Borrado correctemente!");
        }
        catch (Exception e)
        {
            this.log.error("Error al borrar usuario: ", e);
        }
    }

    public void guardar()
    {
        try
        {
            this.usuarioService.guardarUsuario(this.catalogoUsuariosForm);
            this.messageService.addInfo("Usuario", "Guardado Correctamente!");
        }
        catch (Exception e)
        {
            this.log.error("Error al guardar usuario: ", e);
        }
    }

    public CatalogoUsuariosForm getCatalogoUsuariosForm()
    {
        return catalogoUsuariosForm;
    }

    public void setCatalogoUsuariosForm(CatalogoUsuariosForm catalogoUsuariosForm)
    {
        this.catalogoUsuariosForm = catalogoUsuariosForm;
    }
}
