package force.eai.mx.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import force.eai.mx.util.Force;
import force.eai.mx.util.Message;
import force.eai.mx.tools.BuildJson;
import force.eai.mx.database.Connect;
import force.eai.mx.orchestration.LoadCustomer;
import force.eai.mx.orchestration.LoadCatalogues;

/**
 * Servlet implementation class LoadCustomerServlet
 */
@WebServlet("/LoadCustomerServlet")
public class LoadCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connect connect;
	private LoadCustomer loadCustomer;
       
    public LoadCustomerServlet() {
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
			return loadCustomer(request);
			
		} catch(SQLException sqlException){
			sqlException.printStackTrace();
			return new Message("-1", sqlException.getMessage());
			
		} finally {
			close();
		}
	}
	
	private void close() {
		loadCustomer.close();
		connect.close();
	}

	private Message loadCustomer(HttpServletRequest request) throws SQLException {
		Message message = new Message();
		HttpSession session = request.getSession();
		
		connect = new Connect((String)session.getAttribute("env"));

		// LOAD CATALOGUES
		if ( session.getAttribute("formCatalogues") == null ) {
			
			LoadCatalogues loadCatalogues = new LoadCatalogues(connect.getConnection());
			loadCatalogues.loadCatalogues();
			loadCatalogues.close();
		
			session.setAttribute("formSections", loadCatalogues.getFormSections() );
			session.setAttribute("formCatalogues", loadCatalogues.getFormCatalogues() );
			session.setAttribute("formCataloguesHashMap", loadCatalogues.getFormCataloguesHashMap() );				

			message = loadCatalogues.getMessage();
		}	
		
		// LOAD CUSTOMER
		loadCustomer = new LoadCustomer(connect.getConnection(), request.getParameter("q"));
		if ( request.getParameter("q") != null ) {
			Force force = (Force)session.getAttribute("force");
			
			loadCustomer.loadData();
			loadCustomer.loadStatus();
			loadCustomer.loadDocumentos(force.getIdCliente());
			loadCustomer.loadSeguimiento();
						
			if ( !loadCustomer.getCliente().getSeccion().equals("") )
				session.setAttribute("section", loadCustomer.getCliente().getSeccion());
			else 
				session.setAttribute("section", "section1");

			message = new Message("0", "Cliente cargado con Éxito");
			
		} else {
			session.setAttribute("section", "section0");
			message = new Message("0", "New Customer");
		}
		
		session.setAttribute("cliente", loadCustomer.getCliente());
		return message;
	}
}
