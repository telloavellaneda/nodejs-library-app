package force.eai.mx.orchestration;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;

import force.eai.mx.dao.*;
import force.eai.mx.util.*;
import force.eai.mx.database.Upsert;

public class UpsertCustomer {	
	private String forceId = "";
	private String idPersona = "";
	private String sysdate = "";
	private String timeStamp = "";
	
	private Upsert upsert;
	private Cliente cliente;
	
	public UpsertCustomer(Connection connection) throws SQLException {
		this.upsert = new Upsert(connection);
		createTimestamp();
	}
	
	public void close() {
		this.upsert.close();
	}
	
	public void setCliente(Cliente cliente) throws SQLException {
		this.cliente = cliente;
		if (cliente.getForceId().equals(""))
			isNewCustomer();
	}

	private void isNewCustomer() throws SQLException {
		getNextForceId();
		getNextIdPersona();
	}

	private void validateForceId() throws SQLException {
		String query = "SELECT * FROM EAI_REL_CLIENTES WHERE FORCE_ID = '" + cliente.getForceId() + "'";
		
		ResultSet rset = upsert.query(query);
		if (rset.next()) {
			cliente.setForceId(rset.getString("FORCE_ID"));
			cliente.getPersona().setIdPersona(rset.getString("ID_PERSONA"));
			cliente.getConyuge().setIdPersona(rset.getString("ID_CONYUGE"));
			cliente.getApoderado().setIdPersona(rset.getString("ID_APODERADO"));
			cliente.getCoacreditado("0").setIdPersona(rset.getString("ID_COACREDITADO_1"));
			cliente.getCoacreditado("1").setIdPersona(rset.getString("ID_COACREDITADO_2"));
		} 
		rset.close();		
	}
	
	private void upsert() throws SQLException {
		EaiRelClientes eaiRelClientes = new EaiRelClientes();
		EaiRelCreditos eaiRelCreditos = new EaiRelCreditos();

		// CLIENTE
		if ( cliente.getForceId().equals("") ) {
			long id = Long.parseLong(idPersona);
			
			cliente.setForceId(forceId);
			cliente.getCreditos("0").setForceId(forceId);
			cliente.getPersona().setIdPersona(idPersona);
			cliente.getConyuge().setIdPersona( (id+1) + "" );
			cliente.getApoderado().setIdPersona( (id+2) + "" );
			cliente.getCoacreditado("0").setIdPersona( (id+3) + "" );
			cliente.getCoacreditado("1").setIdPersona( (id+4) + "" );
			cliente.getReferencia("0").setIdPersona( (id+5) + "" );
			cliente.getReferencia("1").setIdPersona( (id+6) + "" );
			cliente.getReferencia("2").setIdPersona( (id+7) + "" );
			
			eaiRelClientes.setCliente(cliente);
			upsert.execute(eaiRelClientes.insert());
			
		} else {
			cliente.getCreditos("0").setForceId(cliente.getForceId());

			eaiRelClientes.setCliente(cliente);
			upsert.execute(eaiRelClientes.update());
		}		

		// PERSONA
		upsertPersona(cliente.getPersona());		

		// CREDITO
		if ( !cliente.getCreditos("0").getIdBanco().equals("") ) {
			eaiRelCreditos.setCredito(cliente.getCreditos("0"));
			
			upsert.setInsert(eaiRelCreditos.insert());
			upsert.setUpdate(eaiRelCreditos.update());
			upsert.upsert();
		}
		
		// APODERADO
		if ( !cliente.getApoderado().getNombre().equals("") && !cliente.getApoderado().getApellidoPaterno().equals("") )
			upsertPersona(cliente.getApoderado());

		// COACREDITADO
		if ( !cliente.getCreditos("0").getCoacreditado().equals("") )
			upsertPersona(cliente.getCoacreditado("0"));		
	}
		
	private void upsertPersona(Persona persona) throws SQLException {
		EaiEntPersonas eaiEntPersonas = new EaiEntPersonas();
		
		persona.setStatus( (!persona.getStatus().equals("")) ? persona.getStatus() : "1" );
		
		eaiEntPersonas.setPersona(persona);
		upsert.setInsert(eaiEntPersonas.insert());
		upsert.setUpdate(eaiEntPersonas.update());
		upsert.upsert();
		
		upsertDirecciones(persona.getIdPersona(), persona.getDirecciones());
		upsertTelefonos(persona.getIdPersona(), persona.getTelefonos());
		upsertEmpleos(persona.getIdPersona(), persona.getEmpleos());
		upsertReferencias(persona.getIdPersona(), persona.getReferencias());
	}
	
	private void upsertDirecciones(String idPersona, LinkedHashMap<String,Direccion> direcciones) throws SQLException {
		EaiEntDirecciones eaiEntDirecciones = new EaiEntDirecciones();
		
		for (Iterator<Direccion> iterator = direcciones.values().iterator(); iterator.hasNext();) {
			Direccion element = iterator.next();
			if ( !element.getCalle().equals("") || 
				!element.getNumeroExterior().equals("") || 
				!element.getCodigoPostal().equals("") ||
				!element.getDelegacion().equals("") ||
				!element.getCiudad().equals("") ||
				!element.getEstado().equals("") ) {
				
				element.setIdPersona(idPersona);
				element.setStatus( (!element.getStatus().equals("")) ? element.getStatus() : "1" );
				
				eaiEntDirecciones.setDireccion(element);
				upsert.setInsert(eaiEntDirecciones.insert());
				upsert.setUpdate(eaiEntDirecciones.update());
				upsert.upsert();
			}			
		}
	}
	
	private void upsertTelefonos(String idPersona, LinkedHashMap<String,Telefono> telefonos) throws SQLException {
		EaiEntTelefonos eaiEntTelefonos = new EaiEntTelefonos();
		
		for (Iterator<Telefono> iterator = telefonos.values().iterator(); iterator.hasNext();) {
			Telefono element = iterator.next();
			if ( !element.getLada().equals("") && !element.getNumero().equals("") ) {
				element.setIdPersona(idPersona);
				element.setStatus( (!element.getStatus().equals("")) ? element.getStatus() : "1" );
				
				eaiEntTelefonos.setTelefono(element);
				upsert.setInsert(eaiEntTelefonos.insert());
				upsert.setUpdate(eaiEntTelefonos.update());
				upsert.upsert();
			}			
		}
	}
	
	private void upsertEmpleos(String idPersona, LinkedHashMap<String,Empleo> empleos) throws SQLException {
		EaiEntEmpleos eaiEntEmpleos = new EaiEntEmpleos();
		
		for (Iterator<Empleo> iterator = empleos.values().iterator(); iterator.hasNext();) {
			Empleo element = iterator.next();
			element.setIdPersona(idPersona);
			element.setStatus( (!element.getStatus().equals("")) ? element.getStatus() : "1" );
			
			eaiEntEmpleos.setEmpleo(element);
			upsert.setInsert(eaiEntEmpleos.insert());
			upsert.setUpdate(eaiEntEmpleos.update());
			upsert.upsert();
		}
	}

	private void upsertReferencias(String idPersona, LinkedHashMap<String,Referencia> referencias) throws SQLException {
		EaiEntReferencias eaiEntReferencias;
		
		for (Iterator<Referencia> iterator = referencias.values().iterator(); iterator.hasNext();) {
			Referencia element = iterator.next();
			if ( !element.getNombre().equals("") && !element.getApellidoPaterno().equals("") ) {
				element.setIdPersona(idPersona);
				element.setStatus( (!element.getStatus().equals("")) ? element.getStatus() : "1" );
				
				eaiEntReferencias = new EaiEntReferencias(element);
				upsert.setInsert(eaiEntReferencias.insert());
				upsert.setUpdate(eaiEntReferencias.update());
				upsert.upsert();
			}			
		}
	}
			
	public void upsertCustomer() throws SQLException {
		validateForceId();
		upsert();
	}
	
	public void upsertFechas() throws SQLException {		
		EaiRelFechas erf = new EaiRelFechas(cliente);	
		upsert.setInsert(erf.insert());
		upsert.setUpdate(erf.update());
		upsert.upsert();
	}
	
	public void upsertStatus() throws SQLException {
		upsert.execute( new EaiRelClientes(cliente).updateStatus() );
	}
	
	public void upsertSeguimiento(Seguimiento seguimiento) throws SQLException {
		upsert.execute(new EaiRelSeguimiento(seguimiento).insert());
	}

	public void insertStatusLog() throws SQLException {
		upsert.execute( new EaiRelStatus(cliente, cliente.getUsuarioModificacion().getIdPersona()).insert() );	
	}
		
	public void upsertDocumento(Documento documento) throws SQLException {
		EaiRelDocumentos eaiRelDocumentacion = new EaiRelDocumentos(documento);
		upsert.setInsert(eaiRelDocumentacion.insert());
		upsert.setUpdate(eaiRelDocumentacion.update());
		upsert.upsert();
	}

	public void upsertDriveId() throws SQLException {
		EaiRelClientes eaiRelClientes = new EaiRelClientes();
		eaiRelClientes.setCliente(cliente);
		upsert.execute(eaiRelClientes.updateDrive());
	}
		
	public String getNextForceId() throws SQLException {
		this.forceId = upsert.queryValue(new EaiRelClientes().getNextValue());
		return this.forceId;
	}	

	public String getNextIdPersona() throws SQLException {
		this.idPersona = upsert.queryValue(new EaiEntPersonas().getNextValue());
		return this.idPersona;
	}

	public String getNextIdSeguimiento(String forceId) throws SQLException {
		return upsert.queryValue(new EaiRelSeguimiento().getNextValue(forceId));
	}

	public void createTimestamp() throws SQLException {
		this.timeStamp = upsert.queryValue("SELECT TO_CHAR(CURRENT_TIMESTAMP,'DD/MM/YYYY HH24:MI:SSXFF') || '000' CURRENT_TIMESTAMP FROM DUAL"); 
		this.sysdate = this.timeStamp.substring(0, this.timeStamp.indexOf(" ") ).trim();
	}
	
	public String getForceId() {
		return forceId;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public String getTimestamp() {
		return timeStamp;
	}
	public String getSysdate() {
		return sysdate;
	}
	public Cliente getCliente() {
		return cliente;
	}
}