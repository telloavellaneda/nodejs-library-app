package force.eai.mx.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import force.eai.mx.util.Force;
import force.eai.mx.util.Cliente;
import force.eai.mx.util.Message;
import force.eai.mx.util.Seguimiento;
import force.eai.mx.database.Connect;
import force.eai.mx.form.FormQueryString;
import force.eai.mx.orchestration.LoadCustomer;
import force.eai.mx.orchestration.UpsertCustomer;
import force.eai.mx.tools.BuildJson;

/**
 * Servlet implementation class UpsertCustomerServlet
 */
@WebServlet("/UpsertCustomerServlet")
public class UpsertCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connect connect;
	private LoadCustomer loadCustomer;
	private UpsertCustomer upsertCustomer;

    public UpsertCustomerServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Message message = new Message();
		
		if ( request.getSession().getAttribute("force") == null )
			message.setCode("-2");

		else
			message = execute(request);
				
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write( new BuildJson().write(message) );
	}
	
	private Message execute(HttpServletRequest request) {
		try {
			if ( validateString(request.getParameter("action")).equals("upsert") )
				return upsertCustomer(request);
			
			else if ( validateString(request.getParameter("action")).equals("upsertStatus") )
				return upsertStatus(request);
				
			else if ( validateString(request.getParameter("action")).equals("upsertSeguimiento") ) 
				return upsertSeguimiento(request);
			
			else
				return new Message("-1", "Selección Inválida");
			
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();

			if ( !sqlException.getSQLState().equals("-10") &&
				 !sqlException.getSQLState().equals("-11") 	) {
				return new Message("-1", sqlException.getMessage());
				
			} else {
				return new Message("0", sqlException.getMessage());
				
			}

		} finally {
			close();
		}
	}
	
	private void close() {
		if ( loadCustomer != null)
			loadCustomer.close();
		if ( upsertCustomer != null )
			upsertCustomer.close();
		if ( connect != null )
			connect.close();
	}
	
	private Message upsertCustomer(HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
				
		connect = new Connect((String) session.getAttribute("env"));
		
		boolean isNewCustomer = false;
		Force force = (Force)session.getAttribute("force");
		Cliente cliente = (Cliente)session.getAttribute("cliente");

		FormQueryString queryString = new FormQueryString(request);
		queryString.setCliente(cliente);
		queryString.formQueryString();
		cliente = queryString.getCliente();

		upsertCustomer = new UpsertCustomer(connect.getConnection());		

		// New Customer vs Current Customer
		if ( cliente.getForceId().equals("") ) {
			isNewCustomer = true;
			cliente.setUsuario(force.getUsuario("0"));
			cliente.setUsuarioCreacion(force.getUsuario("0"));
			cliente.setUsuarioModificacion(force.getUsuario("0"));
			cliente.setFechas("creacion", upsertCustomer.getTimestamp());
			cliente.setFechas("fase", upsertCustomer.getTimestamp());
			cliente.setFechas("status", upsertCustomer.getTimestamp());
			cliente.setFechas("expediente", upsertCustomer.getSysdate());
		
		} else { 
			cliente.setUsuarioModificacion(force.getUsuario("0"));
			cliente.setFechas("modificacion", upsertCustomer.getTimestamp());
		}
		
		upsertCustomer.setCliente(cliente);
		upsertCustomer.upsertCustomer();
		upsertCustomer.upsertFechas();
		
		if (isNewCustomer) {
			upsertCustomer.insertStatusLog();
			
			loadCustomer = new LoadCustomer(connect.getConnection());
			loadCustomer.setCliente(upsertCustomer.getCliente());
			loadCustomer.loadStatus();
			loadCustomer.loadDocumentos(force.getIdCliente());
			session.setAttribute("cliente", loadCustomer.getCliente());

		} else
			session.setAttribute("cliente", upsertCustomer.getCliente());

		return new Message("0", "Registro guardado con Éxito");
	}
	
	private Message upsertStatus(HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
		
		Force force = (Force)session.getAttribute("force");
		Cliente cliente = (Cliente)session.getAttribute("cliente");
		
		if ( force.getUsuario("0").getProfile().isCargaRapida() &&
			 !cliente.getIdFase().equals("0") )
			return new Message("0", "Usuario de Carga Rápida no puede modificar el status");
		
		connect = new Connect((String) session.getAttribute("env"));
		
		upsertCustomer = new UpsertCustomer(connect.getConnection());

		// Valida si debe modificar la fecha del cambio de Fase en la Base de Datos
		String faseActual = cliente.getIdFase();
		String faseNueva = request.getParameter("id_fase");

		cliente.setIdFase(request.getParameter("id_fase"));
		cliente.setFase(request.getParameter("fase"));
		cliente.setIdStatus(request.getParameter("id_status"));
		cliente.setStatus(request.getParameter("status"));

		if ( !faseActual.equals(faseNueva) ) {
			cliente.setFechas("fase",upsertCustomer.getTimestamp());
			cliente.setFechas("fase" + cliente.getIdFase(), upsertCustomer.getSysdate());
		}
		cliente.setFechas("status", upsertCustomer.getTimestamp());
		cliente.setFechas("modificacion", upsertCustomer.getTimestamp());
		
		cliente.setUsuarioModificacion(force.getUsuario("0"));

		upsertCustomer.setCliente(cliente);
		upsertCustomer.upsertStatus();
		upsertCustomer.upsertFechas();
		upsertCustomer.insertStatusLog();
		
		loadCustomer = new LoadCustomer(connect.getConnection());
		loadCustomer.setCliente(upsertCustomer.getCliente());
		loadCustomer.loadStatus();
		
		session.setAttribute("cliente", loadCustomer.getCliente());
		
		return new Message("0", "Status actualizado con Éxito");		
	}
	
	private Message upsertSeguimiento (HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
				
		connect = new Connect((String)session.getAttribute("env"));
		
		Force force = (Force)session.getAttribute("force");
		Cliente cliente = (Cliente)session.getAttribute("cliente");

		if ( request.getParameter("refresh") != null ) {
			loadCustomer = new LoadCustomer(connect.getConnection());
			loadCustomer.setCliente(cliente);
			loadCustomer.loadSeguimiento();
			
			session.setAttribute("cliente", loadCustomer.getCliente());
			return new Message("0", "Seguimiento cargado con Éxito");
			
		} else {			
			upsertCustomer = new UpsertCustomer(connect.getConnection());

			Seguimiento seguimiento = new Seguimiento();
			seguimiento.setForceId(cliente.getForceId());
			seguimiento.setId(upsertCustomer.getNextIdSeguimiento(cliente.getForceId()));
			seguimiento.setMensaje(request.getParameter("q"));
			seguimiento.setUsuario(force.getUsuario("0"));
			seguimiento.setFechaCreacion(upsertCustomer.getTimestamp());

			upsertCustomer.upsertSeguimiento(seguimiento);
			cliente.setSeguimiento(seguimiento.getId(), seguimiento);
			
			session.setAttribute("cliente", cliente);
			return new Message("0", "Comentario guardado con Éxito");
		}		
	}
		
	private String validateString(String value) {
		return ( value != null ) ? value : "";
	}
}