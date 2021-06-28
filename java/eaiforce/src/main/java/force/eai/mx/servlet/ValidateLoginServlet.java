package force.eai.mx.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import force.eai.mx.util.Force;
import force.eai.mx.util.Message;
import force.eai.mx.database.Connect;
import force.eai.mx.orchestration.LoadProfile;
import force.eai.mx.security.SecurityUtils;
import force.eai.mx.security.ValidateLogin;
import force.eai.mx.tools.BuildJson;

/**
 * Servlet implementation class ValidateLoginServlet
 */
@WebServlet("/ValidateLoginServlet")
public class ValidateLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connect connect;
	private ValidateLogin vl;
	private LoadProfile loadProfile;
	
	private final String ENVIRONMENT 		= "env";
	private final String PAGE_SIZE 			= "pageSize";
	private final String MAIN_PAGE_SIZE     = "searchPageSize";
       
    public ValidateLoginServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Message message = execute(request);
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write( new BuildJson().write(message) );
	}

	private Message execute(HttpServletRequest request) {		
		if ( request.getParameter("x") != null && !request.getParameter("x").equals("") )
			try {
				return validateLogin(request);
				
			} catch(SQLException sqlException) {
				return new Message("-1", sqlException.getMessage());
				
			} finally {
				close();

			}

		else if ( request.getParameter("recaptcha") != null && !request.getParameter("recaptcha").equals("") )	
			return validateRecaptcha(request.getParameter("recaptcha"));
		
		else
			return new Message("-1", "Operación Inválida");
	}
	
	private void close() {
		try {
			vl.close();
		} catch (Exception exception) {
			// Nothing
		}

		try {
			loadProfile.close();
		} catch (Exception exception) {
			// Nothing
		}

		try {
			connect.close();
		} catch (Exception exception) {
			// Nothing
		}
	}
	
	private Message validateLogin(HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
		                
		if ( getServletContext().getInitParameter(ENVIRONMENT) == null )
			throw new SQLException("Por favor configure archivo web.xml");
		
		@SuppressWarnings("unchecked")
		Enumeration<String> attributes = session.getAttributeNames();
		while ( attributes.hasMoreElements() ) 
			session.removeAttribute(attributes.nextElement());
				
		connect = new Connect(getServletContext().getInitParameter(ENVIRONMENT));

		String browser = "";
		String hostName = "";
		String ipAddress = "";
		try {
			browser = request.getHeader("user-agent");
			hostName = java.net.Inet4Address.getLocalHost().getHostName();
			ipAddress = java.net.Inet4Address.getLocalHost().getHostAddress();
						
		} catch(java.net.UnknownHostException uhException) {
			throw new SQLException(uhException.getMessage(), "-11");
		}
		
		vl = new ValidateLogin(connect.getConnection());
		vl.validateLogin(request.getParameter("x"), hostName, ipAddress, browser);
		
		session.setAttribute("force", vl.getForce());
		session.setAttribute(ENVIRONMENT, getServletContext().getInitParameter(ENVIRONMENT));
		session.setAttribute(PAGE_SIZE, getServletContext().getInitParameter(PAGE_SIZE));
		session.setAttribute(MAIN_PAGE_SIZE, getServletContext().getInitParameter(MAIN_PAGE_SIZE));
		session.setMaxInactiveInterval(60*60);
		
		loadPreferences(session, vl.getForce());
		
		loadProfile = new LoadProfile(connect.getConnection());
		loadProfile.load(vl.getForce());
		session.setAttribute("filterCatalogues", loadProfile.getProfile());

		return new Message("0", "Cargando Perfil...");
	}
	
	private Message validateRecaptcha(String recaptcha) {
		try {
			return new Message("0", new SecurityUtils().openUrl(recaptcha));

		} catch (Exception exception) { 
			return new Message("-1", exception.toString());
		}
	}
	
	private void loadPreferences(HttpSession session, Force force) {
		if ( !force.getUsuario("0").getFiltros().equals("") )
			setPreferences(session, force.getUsuario("0").getFiltros());
		else {
			session.setAttribute("filter_year", new java.text.SimpleDateFormat("yyyy").format(java.util.Calendar.getInstance().getTime()));
			session.setAttribute("filter_month", new java.text.SimpleDateFormat("MM").format(java.util.Calendar.getInstance().getTime()));
		}
		setPreferences(session, force.getUsuario("0").getWidgets());
		setPreferences(session, force.getUsuario("0").getSelecciones());
	}
	
	private void setPreferences(HttpSession session, String value) {
		String[] values = value.split("\\|");
		for ( int i = 0; i < values.length; i++ )
			try {
				session.setAttribute(values[i].split("=")[0], values[i].split("=")[1]);
				//System.out.println( values[i].split("=")[0] + " " + values[i].split("=")[1]);
			} catch (Exception e) { 
				// Nothing
			}
	}
}