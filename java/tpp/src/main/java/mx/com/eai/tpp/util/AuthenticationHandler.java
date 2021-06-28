package mx.com.eai.tpp.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class AuthenticationHandler extends SimpleUrlAuthenticationFailureHandler
{
    private Logger log = Logger.getLogger(this.getClass());

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException auth) throws ServletException, IOException
    {
        try
        {
            super.onAuthenticationFailure(request, response, auth);
        } catch (IOException e)
        {
            this.log.error("Error mientras se daba de baja la sesión del usuario", e);
        } catch (ServletException e)
        {
            this.log.error("Error mientras se daba de baja la sesión del usuario", e);
        }

    }
}
