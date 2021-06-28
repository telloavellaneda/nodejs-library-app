package force.eai.mx.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import force.eai.mx.util.Force;
import force.eai.mx.util.Cliente;
import force.eai.mx.util.Message;
import force.eai.mx.tools.BuildJson;
import force.eai.mx.orchestration.NotifyUser;

/**
 * Servlet implementation class NotifyUserServlet
 */
@WebServlet("/NotifyUserServlet")
public class NotifyUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String FROM = "notificaciones@eai.mx";	
	
    public NotifyUserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Message message = new Message();
		HttpSession session = request.getSession();
		
		if ( session.getAttribute("force") == null )
			message.setCode("-2");

		else
			message = execute(request);
				
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write( new BuildJson().write(message) );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private Message execute(HttpServletRequest request) {
		try {
			return notifyUser(request);
			
		} catch(Exception exception){
			exception.printStackTrace();
			return new Message("-1", exception.toString());
		}
	}	
	
	private Message notifyUser(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		
		Force force = (Force)session.getAttribute("force");
		Cliente cliente = (Cliente)session.getAttribute("cliente");
				
		NotifyUser notifyUser = new NotifyUser();
		notifyUser.setForce(force);
		notifyUser.setCliente(cliente);
		notifyUser.getEmail().setFrom(FROM);
		//notifyUser.getEmail().setTo(new String[] { force.getUsuario("0").getEmail() });
		notifyUser.getEmail().setTo(new String[] { "stello@eai.mx" });
		notifyUser.getEmail().setCc(new String[] { cliente.getUsuarioResponsable().getEmail() });
		notifyUser.send(request.getParameter("message_id"));
		
		return new Message("0", "Mensaje enviado con éxito");
	}
}