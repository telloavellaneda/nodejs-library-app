package force.eai.mx.orchestration;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.text.SimpleDateFormat;

import force.eai.mx.dao.*;
import force.eai.mx.util.Force;
import force.eai.mx.util.Message;
import force.eai.mx.util.Persona;
import force.eai.mx.util.Catalogo;
import force.eai.mx.database.Upsert;

public class UpsertForce {
	private Force force;
	private Upsert upsert;
	private Message message;
	
	private LinkedHashMap<String,LinkedHashMap<String,Catalogo>> initCatalogues;

	public UpsertForce(Connection connection) throws SQLException {
		this.upsert = new Upsert(connection);
		this.initCatalogues = new LinkedHashMap<String,LinkedHashMap<String,Catalogo>>();
	}
	
	public void close() throws SQLException {
		this.upsert.close();
	}

	private void validateIdCliente() throws SQLException {
		String query = "SELECT * FROM EAI_ADM_CLIENTES WHERE ID_CLIENTE = '" + force.getIdCliente() + "'";
		
		ResultSet rset = upsert.query(query);
		if (rset.next()) {
			force.setIdCliente(rset.getString("ID_CLIENTE"));
			force.setFechas("creacion",formatDate(rset.getString("FECHA_CREACION")));
		} 
		rset.close();
	}

	private String validateIdUsuario(String id, String usuario) throws SQLException {
		String response = "";

		String query = "SELECT * FROM EAI_ADM_USUARIOS\n"
				+ "WHERE ID_CLIENTE = '" + id + "'\n" 
				+ " AND USUARIO = '" + usuario + "'" ;
		
		ResultSet rset = upsert.query(query);
		if (rset.next())
			response = rset.getString("ID_USUARIO");
		
		rset.close();
		return response;
	}

	private void upsert() throws SQLException {
		EaiAdmClientes eaiAdmClientes = new EaiAdmClientes();
		EaiAdmUsuarios eaiAdmUsuarios = new EaiAdmUsuarios();

		if ( force.getIdCliente().equals("") )
			force.setIdCliente( upsert.queryValue(eaiAdmClientes.getNextValue()) );		
		
		eaiAdmClientes.setForce(force);
		upsert.setInsert(eaiAdmClientes.insert());
		upsert.setUpdate(eaiAdmClientes.update());
		upsert.upsert();

		for (Iterator<Persona> iterator = force.getUsuarios().values().iterator(); iterator.hasNext();) {
			Persona usuario = iterator.next();
			String idUsuario = validateIdUsuario(force.getIdCliente(), usuario.getUsuario());
			
			// USUARIO
			eaiAdmUsuarios.setIdCliente(force.getIdCliente());

			if ( idUsuario.equals("") )
				usuario.setIdPersona( upsert.queryValue(eaiAdmUsuarios.getNextValue()) );
			else
				usuario.setIdPersona(idUsuario);
			
			eaiAdmUsuarios.setUsuario(usuario);
			upsert.setInsert(eaiAdmUsuarios.insert());
			upsert.setUpdate(eaiAdmUsuarios.update());
			upsert.upsert();
		}
	}
	
	private String formatDate(String value) {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss"); 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
		try {
			value = sdf.format(dt.parse(value));
		} catch (Exception exception) {	}
		return value;
	}
	
	private void updateUser() throws SQLException {
		String query = "Update EAI_ADM_USUARIOS\n" +
				"SET FECHA_SALIDA = CURRENT_TIMESTAMP\n"+
				"WHERE ID_USUARIO = " + force.getUsuario("0").getIdPersona();
		
		upsert.execute(query);
	}
	
	public void upsertForce() throws SQLException {
		validateIdCliente();
		upsert();
		message = new Message("0","Registro guardado con Éxito");			
	}
	
	public void upsertStatus() throws SQLException {
		EaiAdmClientes eaiAdmClientes = new EaiAdmClientes();
		eaiAdmClientes.setForce(force);
		
		for (Iterator<Catalogo> iterator = initCatalogues.get("STATUS").values().iterator(); iterator.hasNext();) {
			eaiAdmClientes.setCatalogo(iterator.next());
			try {
				upsert.execute(eaiAdmClientes.insertStatus());
			} catch (SQLException sql) {
				if ( sql.getErrorCode() != 1 )
					throw sql;
			}
		}
		message = new Message("0","Información actualizada con Éxito");			
	}

	public void upsertBancosProductos() throws SQLException {
		EaiAdmClientes eaiAdmClientes = new EaiAdmClientes();
		eaiAdmClientes.setForce(force);
		
		for (Iterator<Catalogo> iterator = initCatalogues.get("BANCOS").values().iterator(); iterator.hasNext();) {
			eaiAdmClientes.setCatalogo(iterator.next());
			try {
				upsert.execute(eaiAdmClientes.insertBancosProductos());
			} catch (SQLException sql) {
				if ( sql.getErrorCode() != 1 )
					throw sql;
			}
		}
		message = new Message("0","Información actualizada con Éxito");			
	}
	
	public void upsertDestinos() throws SQLException {
		EaiAdmClientes eaiAdmClientes = new EaiAdmClientes();
		eaiAdmClientes.setForce(force);
		
		for (Iterator<Catalogo> iterator = initCatalogues.get("DESTINOS").values().iterator(); iterator.hasNext();) {
			eaiAdmClientes.setCatalogo(iterator.next());
			try {
				upsert.execute(eaiAdmClientes.insertDestinos());
			} catch (SQLException sql) {
				if ( sql.getErrorCode() != 1 )
					throw sql;
			}			
		}
		message = new Message("0","Información actualizada con Éxito");			
	}

	public void upsertDocumentos() throws SQLException {
		EaiAdmClientes eaiAdmClientes = new EaiAdmClientes();
		eaiAdmClientes.setForce(force);
		
		for (Iterator<Catalogo> iterator = initCatalogues.get("DOCUMENTOS").values().iterator(); iterator.hasNext();) {
			eaiAdmClientes.setCatalogo(iterator.next());
			try {
				upsert.execute(eaiAdmClientes.insertDocumentos());
			} catch (SQLException sql) {
				if ( sql.getErrorCode() != 1 )
					throw sql;
			}
		}
		message = new Message("0","Información actualizada con Éxito");
	}
	
	public void upsertParametros() throws SQLException {
		EaiAdmClientes eaiAdmClientes = new EaiAdmClientes();
		eaiAdmClientes.setForce(force);
		
		for (Iterator<Catalogo> iterator = initCatalogues.get("PARAMETROS").values().iterator(); iterator.hasNext();) {
			eaiAdmClientes.setCatalogo(iterator.next());
			try {
				upsert.execute(eaiAdmClientes.insertParametros());
			} catch (SQLException sql) {
				if ( sql.getErrorCode() != 1 )
					throw sql;
			}
		}
		message = new Message("0","Información actualizada con Éxito");
	}
	
	public void upsertPreferences() throws SQLException {
		updateUser();
		
		EaiAdmPreferencias eaiAdmPreferencias = new EaiAdmPreferencias();
		eaiAdmPreferencias.setUsuario(force.getUsuario("0"));
		upsert.setInsert(eaiAdmPreferencias.insert());
		upsert.setUpdate(eaiAdmPreferencias.update());
		upsert.upsert();		
		message = new Message("0","Gracias por utilizar EAI Force");			
	}
	
	public Force getForce() {
		return force;
	}
	public Message getMessage() {
		return message;
	}
	public LinkedHashMap<String, LinkedHashMap<String, Catalogo>> getInitCatalogues() {
		return initCatalogues;
	}
	public void setForce(Force force) {
		this.force = force;
	}
	public void setInitCatalogues(LinkedHashMap<String, LinkedHashMap<String, Catalogo>> initCatalogues) {
		this.initCatalogues = initCatalogues;
	}
}