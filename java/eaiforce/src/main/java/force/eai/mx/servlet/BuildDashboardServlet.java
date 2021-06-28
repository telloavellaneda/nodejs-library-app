package force.eai.mx.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import force.eai.mx.util.Pager;
import force.eai.mx.util.Message;
import force.eai.mx.form.FormUtils;
import force.eai.mx.tools.BuildJson;
import force.eai.mx.database.Connect;
import force.eai.mx.orchestration.BuildDashboard;

/**
 * Servlet implementation class BuildDashboardServlet
 */
@WebServlet("/BuildDashboardServlet")
public class BuildDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connect connect;
	private BuildDashboard buildDashboard;
       
    public BuildDashboardServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Message message;
		HttpSession session = request.getSession();
		
		if ( session.getAttribute("force") == null )
			message = new Message("-2", "Inicie Sesión");
		else
			message = execute(request);
				
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write( new BuildJson().write(message) );
	}
	
	private Message execute(HttpServletRequest request) {
		try {
			if ( request.getParameter("action") != null && request.getParameter("action").equals("clientes") ) 
				return fetchClientes(request);
			else if ( request.getParameter("action") != null && request.getParameter("action").equals("prospectos") ) 
				return fetchProspectos(request);
			else if ( request.getParameter("action") != null && request.getParameter("action").equals("buscar") ) 
				return fetchSearch(request);
			else if ( request.getParameter("action") != null && request.getParameter("action").equals("report") ) 
				return fetchReport(request);
			else
				return new Message("-1", "Selección Inválida");
			
		} catch(SQLException|NullPointerException sqlException){
			sqlException.printStackTrace();
			return new Message("-1", sqlException.getMessage());
			
		} finally {
			close();
		}
	}
	
	private void close() {
		try {
			buildDashboard.close();
		} catch (Exception exception) {
			// Nothing
		}

		try {
			connect.close();
		} catch (Exception exception) {
			// Nothing
		}
	}
		
	private Message fetchClientes(HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
		FormUtils fu = new FormUtils(session);
		
		String direction = fu.getStringFromSession("clientes", "up");
		Pager pager = (Pager) session.getAttribute("pager");
		int requestedPage = pager.getClCurrent();
		
		if (direction.equals("down"))
			requestedPage = pager.getClCurrent() - 1;
		else
			requestedPage = pager.getClCurrent() + 1;
		
		connect = new Connect((String)session.getAttribute("env"));
		buildDashboard = new BuildDashboard(connect.getConnection(), session);
		buildDashboard.setPageSize(fu.getIntFromSession("pageSize", 25));
		buildDashboard.fetchClientes(requestedPage, true);
		return new Message("0", "Clientes cargados con Exito");
	}
	
	private Message fetchProspectos(HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
		FormUtils fu = new FormUtils(session);
		
		String direction = fu.getStringFromSession("prospectos", "up");
		Pager pager = (Pager) session.getAttribute("pager");
		int requestedPage = pager.getPrCurrent();
		
		if (direction.equals("down"))
			requestedPage = pager.getPrCurrent() - 1;
		else
			requestedPage = pager.getPrCurrent() + 1;
		
		connect = new Connect((String)session.getAttribute("env"));
		buildDashboard = new BuildDashboard(connect.getConnection(), session);
		buildDashboard.setPageSize(fu.getIntFromSession("pageSize", 25));
		buildDashboard.fetchProspectos(requestedPage, true);
		return new Message("0", "Prospectos cargados con Exito");
	}
	
	private Message fetchSearch(HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
		FormUtils fu = new FormUtils(session);

		String direction = fu.getStringFromSession("buscar", "up");
		Pager pager = (Pager) session.getAttribute("pager");
		int requestedPage = pager.getAllCurrent();
		
		if (direction.equals("down"))
			requestedPage = pager.getAllCurrent() - 1;
		else
			requestedPage = pager.getAllCurrent() + 1;
				
		connect = new Connect((String)session.getAttribute("env"));
		buildDashboard = new BuildDashboard(connect.getConnection(), session);
		buildDashboard.setPageSize(fu.getIntFromSession("pageSize", 50));		
		buildDashboard.fetchSearch(requestedPage);		
		return new Message("0", "Report created successfully");		
	}

	private Message fetchReport(HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
		FormUtils fu = new FormUtils(session);
		connect = new Connect((String)session.getAttribute("env"));
		buildDashboard = new BuildDashboard(connect.getConnection(), session);
		buildDashboard.setPageSize(fu.getIntFromSession("pageSize", 50));		
		buildDashboard.fetchSearch(-99);		
		return new Message("0", "Report created successfully");		
	}
}