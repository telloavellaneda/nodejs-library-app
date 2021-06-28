package force.eai.mx.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import force.eai.mx.util.Cliente;
import force.eai.mx.util.Message;
import force.eai.mx.form.FormUtils;
import force.eai.mx.database.Connect;
import force.eai.mx.tools.BuildJson;
import force.eai.mx.tools.CreateRfcCurp;
import force.eai.mx.orchestration.LoadZipCode;

/**
 * Servlet implementation class LoadFormElementsSevlet
 */
@WebServlet("/LoadFormElementsSevlet")
public class LoadFormElementsSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoadFormElementsSevlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Message message = new Message();
		HttpSession session = request.getSession();
		
		if ( session.getAttribute("force") == null )
			message = new Message("-2", "Inicie Sesión");

		else 
			if ( request.getParameter("action").equals("status") )
				message = getStatusElement(request,response);
			else if ( request.getParameter("action").equals("zipcode") )
				message = getZipCodeElements(request,response);
			else if ( request.getParameter("action").equals("rfc") )
				message = getRfcElements(request,response);
			else if ( request.getParameter("action").equals("adheridos") )
				message = getProductoElement(request,response);
			else if ( request.getParameter("action").equals("producto") )
				message = getProductoElement(request,response);
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write( new BuildJson().write(message) );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private Message getStatusElement(HttpServletRequest request, HttpServletResponse response) {
		FormUtils fu = new FormUtils(request.getSession());
		return new Message("0", fu.getFormStatus(request.getParameter("id_fase"), ""));
	}
	
	private Message getProductoElement(HttpServletRequest request, HttpServletResponse response) {
		FormUtils fu = new FormUtils(request.getSession());
		Cliente cliente = Cliente.class.cast(request.getSession().getAttribute("cliente"));
		return new Message("0", fu.getFormProducto(request.getParameter("id_banco"), cliente.getCreditos("0").getProducto()));
	}
	
	private Message getRfcElements(HttpServletRequest request, HttpServletResponse response) {		
		force.eai.mx.util.Persona persona = new force.eai.mx.util.Persona();		
		persona.setApellidoPaterno(request.getParameter("apellido_paterno"));
		persona.setApellidoMaterno(request.getParameter("apellido_materno"));
		persona.setNombre(request.getParameter("primer_nombre"));
		persona.setSegundoNombre(request.getParameter("segundo_nombre"));
		persona.setFecha(request.getParameter("fecha_nacimiento"));
		persona.setEstadoNacimiento(request.getParameter("estado"));
		persona.setSexo(request.getParameter("sexo"));
		persona.setTipo(request.getParameter("tipo"));
		
		CreateRfcCurp rfc = new CreateRfcCurp(persona);
		rfc.calcular();
		
		return new Message("0",
				rfc.getRfc() + "|" + 
				rfc.getHomoclave() + "|" + 
				rfc.getCurp()
		);
	}
	
	private Message getZipCodeElements(HttpServletRequest request, HttpServletResponse response) {
		Connect connect = null;
		LoadZipCode loadZipcode = null;
		
		try {
			connect =  new Connect((String)request.getSession().getAttribute("env"));
		
			loadZipcode = new LoadZipCode(connect.getConnection());
			loadZipcode.setZipCode(request.getParameter("x"));
			loadZipcode.setPreffix(request.getParameter("preffix"));
			loadZipcode.setRequired( (request.getParameter("required") != null) ? true : false );
			loadZipcode.loadZipCode();
			
			return new Message(
					"0",
					loadZipcode.getZipCodeObject().getColonia() + "|" +
					loadZipcode.getZipCodeObject().getDelegacion() + "|" +
					loadZipcode.getZipCodeObject().getCiudad() + "|" +
					loadZipcode.getZipCodeObject().getEstado()
			);
			
		} catch(Exception exception) {
			return new Message("-1", exception.toString());
		
		} finally {
			try {
				loadZipcode.close();
			} catch (Exception e) {
				// Nothing
			}

			try {
				connect.close();
			} catch (Exception e) {
				// Nothing
			}
		}
	}	
}