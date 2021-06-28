package mx.com.eai.tpp.controller;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import mx.com.eai.tpp.dao.UsuarioDao;
import mx.com.eai.tpp.model.Dusuario;
import mx.com.eai.tpp.service.MessageService;
import mx.com.eai.tpp.service.UsuarioService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

@Controller
@Lazy
@RequestScoped
public class LoginController implements Serializable
{
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private MessageService messageService;
    private Logger log = Logger.getLogger(this.getClass());
    private Dusuario usuario;

    /**
     * Direccionamiento hacia el contexto de spring
     *
     * @return
     */
    public LoginController()
    {
        this.usuario = new Dusuario();
    }

    @PostConstruct
    public void init()
    {
        try
        {
            this.usuario.setUsuario("");
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error en el postConstructor ", e);
        }
    }

    public void login() throws IOException, ServletException
    {
        try
        {
            Dusuario u = this.usuarioDao.getEncontrarUsuario(this.usuario.getUsuario());
            if (u != null)
            {
                String passSha = this.usuarioService.encondePasswdWithSha(this.usuario.getContrasenia());
                if (passSha.equals(u.getContrasenia()))
                {
                    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                    ServletRequest servletRequest = (ServletRequest) context.getRequest();
                    ServletResponse servletResponse = (ServletResponse) context.getResponse();
                    RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/j_spring_security_check");
                    dispatcher.forward(servletRequest, servletResponse);
                    FacesContext.getCurrentInstance().responseComplete();
                }
                else
                {
                    this.messageService.addError("Constraseña incorrecta", "");
                }
            }
            else
            {
                this.messageService.addError("Usuario incorrecto", "");
            }
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error en el metodo LoginAction.login", e);
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
}
