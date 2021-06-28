package force.eai.mx.orchestration;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import force.eai.mx.util.*;
import force.eai.mx.database.Upsert;
import force.eai.mx.tools.FormatValues;
import force.eai.mx.dao.QryLoadCustomer;
import force.eai.mx.security.SecurityUtils;

public class LoadCustomer {
	private Cliente cliente = new Cliente();
	private FormatValues fv = new FormatValues();
	private QryLoadCustomer queries = new QryLoadCustomer();
	
	private Upsert upsert;
	private ResultSet rset;
	private String forceId;

	public LoadCustomer(Connection connection) throws SQLException {
		this.upsert = new Upsert(connection);
	}

	public LoadCustomer(Connection connection, String forceId) throws SQLException {
		this.upsert = new Upsert(connection);
		this.forceId = forceId;
	}
	
	public void close() {
		try {
			this.rset.close();
		} catch (Exception exception) {
			// Nothing
		}
		this.upsert.closePreparedStatement();
		this.upsert.close();
	}
	
	public void loadData() throws SQLException {
		String q = new SecurityUtils().decode64(forceId);
		
		// CLIENTE		
		rset = upsert.query(queries.getQueryCliente(), q);
		if (rset.next()) {	
			cliente.setForceId( rset.getString("FORCE_ID") );
			cliente.setTipo( rset.getString("TIPO") );
			cliente.getPersona().setIdPersona(rset.getString("ID_PERSONA"));
			cliente.getConyuge().setIdPersona(rset.getString("ID_CONYUGE"));
			cliente.getApoderado().setIdPersona(rset.getString("ID_APODERADO"));
			cliente.getCoacreditado("0").setIdPersona(rset.getString("ID_COACREDITADO_1"));
			cliente.getUsuario().setIdPersona( rset.getString("ID_USUARIO") );
			cliente.getUsuarioCreacion().setIdPersona( validateString(rset.getString("ID_USUARIO_CREACION")) );
			cliente.getUsuarioResponsable().setIdPersona( validateString(rset.getString("ID_USUARIO_RESPONSABLE")) );
			cliente.getUsuarioModificacion().setIdPersona( validateString(rset.getString("ID_USUARIO_MODIFICACION")) );
			cliente.setIdFase( rset.getString("ID_FASE") );
			cliente.setIdStatus( rset.getString("ID_STATUS") );
			cliente.setSeccion( validateString(rset.getString("SECCION")) );
			cliente.setFechas("creacion",fv.formatTimestampDB(rset.getString("FECHA_CREACION")));
			cliente.setFechas("modificacion",fv.formatTimestampDB(rset.getString("FECHA_MODIFICACION")));
		}
		//System.out.println(queryCliente);
		
		// FECHAS
		rset = upsert.query(queries.getQueryFechas(), q);
		if (rset.next()) {
			cliente.setFechas("expediente",fv.formatDate( rset.getString("FECHA_EXPEDIENTE")) );
			cliente.setFechas("fase1",fv.formatDate(rset.getString("FECHA_FASE_1")));
			cliente.setFechas("fase2",fv.formatDate(rset.getString("FECHA_FASE_2")));
			cliente.setFechas("fase3",fv.formatDate(rset.getString("FECHA_FASE_3")));
			cliente.setFechas("fase4",fv.formatDate(rset.getString("FECHA_FASE_4")));
			cliente.setFechas("fase5",fv.formatDate(rset.getString("FECHA_FASE_5")));
			cliente.setFechas("autorizacion",fv.formatDate( rset.getString("FECHA_AUTORIZACION")) );
			cliente.setFechas("firma",fv.formatDate( rset.getString("FECHA_FIRMA")) );
			cliente.setFechas("linea",fv.formatDate( rset.getString("FECHA_VENC_LINEA")) );
			cliente.setFechas("certificado",fv.formatDate( rset.getString("FECHA_VENC_CERTIFICADO")) );
			cliente.setFechas("fase",fv.formatTimestampDB(rset.getString("FECHA_FASE")));
			cliente.setFechas("status",fv.formatTimestampDB(rset.getString("FECHA_STATUS")));			
		}
		
		// PERSONA
		rset = upsert.query(queries.getQueryPersona(), cliente.getPersona().getIdPersona());
		if ( rset.next() )
			cliente.setPersona(loadPersona(rset));
		//System.out.println(queryPersona);

		// CONYUGE
		rset = upsert.query(queries.getQueryPersona(), cliente.getConyuge().getIdPersona());
		if ( rset.next() )
			cliente.setConyuge(loadPersona(rset));
		//System.out.println(queryApoderado);

		// APODERADO
		rset = upsert.query(queries.getQueryPersona(), cliente.getApoderado().getIdPersona());
		if ( rset.next() )
			cliente.setApoderado(loadPersona(rset));
		//System.out.println(queryApoderado);
		
		// COACREDITADO
		rset = upsert.query(queries.getQueryPersona(), cliente.getCoacreditado("0").getIdPersona());
		if ( rset.next() )
			cliente.setCoacreditado("0", loadPersona(rset));
		//System.out.println(queryCoacreditado);
		
		// DIRECCIONES
		cliente.getPersona().setDirecciones(loadDirecciones(cliente.getPersona().getIdPersona()));
		cliente.getConyuge().setDirecciones(loadDirecciones(cliente.getConyuge().getIdPersona()));
		cliente.getApoderado().setDirecciones(loadDirecciones(cliente.getApoderado().getIdPersona()));
		cliente.getCoacreditado("0").setDirecciones(loadDirecciones(cliente.getCoacreditado("0").getIdPersona()));
		
		// TELEFONOS
		cliente.getPersona().setTelefonos(loadTelefonos(cliente.getPersona().getIdPersona()));
		cliente.getConyuge().setTelefonos(loadTelefonos(cliente.getConyuge().getIdPersona()));
		cliente.getApoderado().setTelefonos(loadTelefonos(cliente.getApoderado().getIdPersona()));
		cliente.getCoacreditado("0").setTelefonos(loadTelefonos(cliente.getCoacreditado("0").getIdPersona()));
		
		// EMPLEOS
		cliente.getPersona().setEmpleos(loadEmpleos(cliente.getPersona().getIdPersona()));
		cliente.getConyuge().setEmpleos(loadEmpleos(cliente.getConyuge().getIdPersona()));
		cliente.getApoderado().setEmpleos(loadEmpleos(cliente.getApoderado().getIdPersona()));
		cliente.getCoacreditado("0").setEmpleos(loadEmpleos(cliente.getCoacreditado("0").getIdPersona()));
		
		// REFERENCIAS
		cliente.getPersona().setReferencias(loadReferencias(cliente.getPersona().getIdPersona()));
		cliente.getApoderado().setReferencias(loadReferencias(cliente.getApoderado().getIdPersona()));
		cliente.getCoacreditado("0").setReferencias(loadReferencias(cliente.getCoacreditado("0").getIdPersona()));
				
		// CREDITOS
		rset = upsert.query(queries.getQueryCredito(), q);
		while ( rset.next() ) {
			cliente.getCreditos("0").setForceId( rset.getString("FORCE_ID") );
			cliente.getCreditos("0").setIdBanco( validateString(rset.getString("id_banco")) );
			cliente.getCreditos("0").setBanco( validateString(rset.getString("banco")) );
			cliente.getCreditos("0").setAdherido( validateString(rset.getString("adherido")) );
			cliente.getCreditos("0").setAdheridoOpcion( validateString(rset.getString("adherido_opcion")) );
			cliente.getCreditos("0").setProducto( validateString(rset.getString("producto")) );
			cliente.getCreditos("0").setDestino( validateString(rset.getString("destino")) );
			cliente.getCreditos("0").setImporteSolicitado( validateString(rset.getString("importe_solic")) );
			cliente.getCreditos("0").setImporteAprobado( validateString(rset.getString("importe_aprob")) );
			cliente.getCreditos("0").setImporteFinal( validateString(rset.getString("importe_final")) );
			cliente.getCreditos("0").setDecreto( validateString(rset.getString("decreto")) );
			cliente.getCreditos("0").setDecretoComentarios( validateString(rset.getString("decreto_comentarios")) );
			cliente.getCreditos("0").setCoacreditado( validateString(rset.getString("coacreditado")) );
			cliente.getCreditos("0").setValorEstimado( validateString(rset.getString("valor_estimado")) );
			cliente.getCreditos("0").setEnganche( validateString(rset.getString("enganche")) );
			cliente.getCreditos("0").setOrigenEnganche( validateString(rset.getString("origen_enganche")) );
			cliente.getCreditos("0").setPlazo( validateString(rset.getString("plazo")) );
			cliente.getCreditos("0").setTasa( validateString(rset.getString("tasa")) );
			cliente.getCreditos("0").setAforo( validateString(rset.getString("aforo")) );
			cliente.getCreditos("0").setTipoComisionApertura( validateString(rset.getString("tipo_comision_apertura")) );
			cliente.getCreditos("0").setComisionApertura( validateString(rset.getString("comision_apertura")) );
			cliente.getCreditos("0").setNotaria( validateString(rset.getString("notaria")) );
			cliente.getCreditos("0").setAbogado( validateString(rset.getString("abogado")) );
			cliente.getCreditos("0").setEjecutivoBanco( validateString(rset.getString("ejecutivo_banco")) );
			cliente.getCreditos("0").setFechaCreacion( fv.formatTimestamp(validateString(rset.getString("fecha_creacion"))) );
			cliente.getCreditos("0").setFechaModificacion( fv.formatTimestamp(validateString(rset.getString("fecha_modificacion"))) );
			cliente.getCreditos("0").setStatus( validateString(rset.getString("status")) );
			
		}
		//System.out.println(queryCredito);
						
		// USUARIO
		rset = upsert.query(queries.getQueryUsuario(), cliente.getUsuario().getIdPersona());		
		if ( rset.next() ) {				
			cliente.getUsuario().setNombre( validateString(rset.getString("NOMBRE")) );
			cliente.getUsuario().setSegundoNombre( validateString(rset.getString("SEGUNDO_NOMBRE")) );
			cliente.getUsuario().setApellidoPaterno( validateString(rset.getString("APELLIDO_PATERNO")) );
			cliente.getUsuario().setApellidoMaterno( validateString(rset.getString("APELLIDO_MATERNO")) );
			cliente.getUsuario().setNombreCompleto(validateString(rset.getString("NOMBRE")) + " " + validateString(rset.getString("APELLIDO_PATERNO")));
			cliente.getUsuario().setStatus( validateString(rset.getString("STATUS")) );
		}		

		// USUARIO CREACION
		rset = upsert.query(queries.getQueryUsuario(), cliente.getUsuarioCreacion().getIdPersona());
		if ( rset.next() ) {				
			cliente.getUsuarioCreacion().setNombre( validateString ( rset.getString("NOMBRE") ) );
			cliente.getUsuarioCreacion().setSegundoNombre( validateString ( rset.getString("SEGUNDO_NOMBRE") ) );
			cliente.getUsuarioCreacion().setApellidoPaterno( validateString ( rset.getString("APELLIDO_PATERNO") ) );
			cliente.getUsuarioCreacion().setApellidoMaterno( validateString ( rset.getString("APELLIDO_MATERNO") ) );
			cliente.getUsuarioCreacion().setStatus( validateString ( rset.getString("STATUS") ) );
		}
		
		// USUARIO RESPONSABLE
		rset = upsert.query(queries.getQueryUsuario(), cliente.getUsuarioResponsable().getIdPersona());
		if ( rset.next() ) {				
			cliente.getUsuarioResponsable().setNombre( validateString ( rset.getString("NOMBRE") ) );
			cliente.getUsuarioResponsable().setSegundoNombre( validateString ( rset.getString("SEGUNDO_NOMBRE") ) );
			cliente.getUsuarioResponsable().setApellidoPaterno( validateString ( rset.getString("APELLIDO_PATERNO") ) );
			cliente.getUsuarioResponsable().setApellidoMaterno( validateString ( rset.getString("APELLIDO_MATERNO") ) );
			cliente.getUsuarioResponsable().setEmail( rset.getString("USUARIO") );
			cliente.getUsuarioResponsable().setStatus( validateString ( rset.getString("STATUS") ) );
		}		

		// USUARIO MODIFICACION
		rset = upsert.query(queries.getQueryUsuario(), cliente.getUsuarioModificacion().getIdPersona());
		if ( rset.next() ) {				
			cliente.getUsuarioModificacion().setNombre( validateString ( rset.getString("NOMBRE") ) );
			cliente.getUsuarioModificacion().setSegundoNombre( validateString ( rset.getString("SEGUNDO_NOMBRE") ) );
			cliente.getUsuarioModificacion().setApellidoPaterno( validateString ( rset.getString("APELLIDO_PATERNO") ) );
			cliente.getUsuarioModificacion().setApellidoMaterno( validateString ( rset.getString("APELLIDO_MATERNO") ) );
			cliente.getUsuarioModificacion().setStatus( validateString ( rset.getString("STATUS") ) );			
		}
	}
		
	private Persona loadPersona(ResultSet rset) throws SQLException {
		Persona persona = new Persona();
		
		persona.setIdPersona( rset.getString("ID_PERSONA") );
		persona.setTipo( validateString(rset.getString("TIPO")) );
		persona.setNombre( validateString(rset.getString("NOMBRE")) );
		persona.setSegundoNombre( validateString(rset.getString("SEGUNDO_NOMBRE")) );
		persona.setApellidoPaterno( validateString(rset.getString("APELLIDO_PATERNO")) );
		persona.setApellidoMaterno( validateString(rset.getString("APELLIDO_MATERNO")) );
		persona.setFecha( fv.formatDate( validateString(rset.getString("FECHA"))) );
		persona.setAnios( validateString(rset.getString("ANIOS")) );
		persona.setMeses( validateString(rset.getString("MESES")) );
		persona.setSexo( validateString(rset.getString("SEXO") ));
		persona.setEmail( validateString(rset.getString("EMAIL")) );
		
		persona.setPaisNacimiento( validateString(rset.getString("PAIS_NACIMIENTO")) );
		persona.setEstadoNacimiento( validateString(rset.getString("ESTADO_NACIMIENTO")) );
		persona.setLugarNacimiento( validateString(rset.getString("LUGAR_NACIMIENTO")) );
		persona.setNacionalidad( validateString(rset.getString("NACIONALIDAD")) );
		persona.setRfc( validateString(rset.getString("RFC")) );
		persona.setHomoclave( validateString(rset.getString("HOMOCLAVE")) );
		persona.setCurp( validateString(rset.getString("CURP")) );
		persona.setNoImss( validateString(rset.getString("NO_IMSS")) );
		persona.setNivelAcademico( validateString(rset.getString("NIVEL_ACADEMICO")) );
		persona.setEstadoCivil( validateString(rset.getString("ESTADO_CIVIL")) );
		persona.setRegimen( validateString(rset.getString("REGIMEN")) );
		persona.setIdentificacion( validateString(rset.getString("IDENTIFICACION")) );
		persona.setNumeroIdentificacion( validateString(rset.getString("NUMERO_IDENTIFICACION")) );
		persona.setStatus(validateString(rset.getString("STATUS")));

		if ( persona.getTipo().equals("2") )
			persona.setNombreCompleto(validateString(rset.getString("NOMBRE")));
		else {
			persona.setNombreCompleto(persona.getNombre() + " " + persona.getSegundoNombre() + " " + persona.getApellidoPaterno() + " " + persona.getApellidoMaterno());
			persona.setNombreCompleto(persona.getNombreCompleto().replaceAll("  ", " ").trim());
		}		
		return persona;
	}
	
	private LinkedHashMap<String,Direccion> loadDirecciones(String idPersona) throws SQLException {
		LinkedHashMap<String,Direccion> direcciones = new LinkedHashMap<String, Direccion>();
		
		rset = upsert.query(queries.getQueryDirecciones(), idPersona);		
		while ( rset.next() ) {
			direcciones.put(rset.getString("TIPO"), new Direccion());
			direcciones.get(rset.getString("TIPO")).setIdPersona(validateString(rset.getString("ID_PERSONA")));
			direcciones.get(rset.getString("TIPO")).setTipo(validateString(rset.getString("TIPO")));
			direcciones.get(rset.getString("TIPO")).setCalle(validateString(rset.getString("CALLE")));
			direcciones.get(rset.getString("TIPO")).setNumeroExterior(validateString(rset.getString("NUMERO_EXT")));
			direcciones.get(rset.getString("TIPO")).setNumeroInterior(validateString(rset.getString("NUMERO_INT")));
			direcciones.get(rset.getString("TIPO")).setColonia(validateString(rset.getString("COLONIA")));
			direcciones.get(rset.getString("TIPO")).setCodigoPostal(validateString(rset.getString("CODIGO_POSTAL")));
			direcciones.get(rset.getString("TIPO")).setDelegacion(validateString(rset.getString("DELEGACION")));
			direcciones.get(rset.getString("TIPO")).setCiudad(validateString(rset.getString("CIUDAD")));
			direcciones.get(rset.getString("TIPO")).setEstado(validateString(rset.getString("ESTADO")));
			direcciones.get(rset.getString("TIPO")).setTipoDomicilio(validateString(rset.getString("TIPO_DOMICILIO")));
			direcciones.get(rset.getString("TIPO")).setMontoRenta(validateString(rset.getString("MONTO_RENTA")));
			direcciones.get(rset.getString("TIPO")).setAntiguedadAnios(validateString(rset.getString("ANT_ANIOS")));
			direcciones.get(rset.getString("TIPO")).setAntiguedadMeses(validateString(rset.getString("ANT_MESES")));
			direcciones.get(rset.getString("TIPO")).setStatus(validateString(rset.getString("STATUS")));
		}
		
		return direcciones;
	}
	
	private LinkedHashMap<String,Telefono> loadTelefonos(String idPersona) throws SQLException {
		LinkedHashMap<String,Telefono> telefonos = new LinkedHashMap<String, Telefono>();

		rset = upsert.query(queries.getQueryTelefonos(), idPersona);		
		while ( rset.next() ) {
			telefonos.put(rset.getString("TIPO"), new Telefono());
			telefonos.get(rset.getString("TIPO")).setIdPersona(validateString(rset.getString("ID_PERSONA")));
			telefonos.get(rset.getString("TIPO")).setCodigo(validateString(rset.getString("CODIGO")));
			telefonos.get(rset.getString("TIPO")).setTipo(validateString(rset.getString("TIPO")));
			telefonos.get(rset.getString("TIPO")).setLada(validateString(rset.getString("LADA")));
			telefonos.get(rset.getString("TIPO")).setNumero(validateString(rset.getString("NUMERO")));
		}
		
		return telefonos;
	}

	private LinkedHashMap<String,Empleo> loadEmpleos(String idPersona) throws SQLException {
		LinkedHashMap<String,Empleo> empleos = new LinkedHashMap<String, Empleo>();
		
		rset = upsert.query(queries.getQueryEmpleos(), idPersona);		
		while ( rset.next() ) {
			empleos.put(rset.getString("ID_PERSONA"), new Empleo());
			empleos.get(rset.getString("ID_PERSONA")).setNombreEmpresa(validateString(rset.getString("NOMBRE_EMPRESA")));
			empleos.get(rset.getString("ID_PERSONA")).setSector(validateString(rset.getString("SECTOR")));
			empleos.get(rset.getString("ID_PERSONA")).setGiro(validateString(rset.getString("GIRO")));
			empleos.get(rset.getString("ID_PERSONA")).setPuesto(validateString(rset.getString("PUESTO")));
			empleos.get(rset.getString("ID_PERSONA")).setProfesion(validateString(rset.getString("PROFESION")));
			empleos.get(rset.getString("ID_PERSONA")).setTipoEmpleo(validateString(rset.getString("TIPO_EMPLEO")));			
			empleos.get(rset.getString("ID_PERSONA")).setTipoContrato(validateString(rset.getString("TIPO_CONTRATO")));
			empleos.get(rset.getString("ID_PERSONA")).setAntiguedadAnios(validateString(rset.getString("ANT_ANIOS")));
			empleos.get(rset.getString("ID_PERSONA")).setAntiguedadMeses(validateString(rset.getString("ANT_MESES")));
			empleos.get(rset.getString("ID_PERSONA")).setIngresosBruto(validateString(rset.getString("INGRESOS_BRUTO")));			
			empleos.get(rset.getString("ID_PERSONA")).setIngresosNeto(validateString(rset.getString("INGRESOS_NETO")));			
			empleos.get(rset.getString("ID_PERSONA")).setOtrosIngresos(validateString(rset.getString("OTROS_INGRESOS")));			
			empleos.get(rset.getString("ID_PERSONA")).setFuenteIngresos(validateString(rset.getString("FUENTE_INGRESOS")));			
			empleos.get(rset.getString("ID_PERSONA")).setFechaCreacion(validateString(rset.getString("FECHA_CREACION")));			
			empleos.get(rset.getString("ID_PERSONA")).setFechaModificacion(validateString(rset.getString("FECHA_MODIFICACION")));			
			empleos.get(rset.getString("ID_PERSONA")).setStatus(validateString(rset.getString("STATUS")));			
		}
		
		return empleos;
	}

	private LinkedHashMap<String,Referencia> loadReferencias(String idPersona) throws SQLException {
		LinkedHashMap<String,Referencia> referencias = new LinkedHashMap<String, Referencia>();
		
		rset = upsert.query(queries.getQueryReferencias(), idPersona);
		while ( rset.next() ) {
			referencias.put(rset.getString("TIPO"), new Referencia());
			referencias.get(rset.getString("TIPO")).setIdPersona(validateString(rset.getString("ID_PERSONA")));
			referencias.get(rset.getString("TIPO")).setTipo(validateString(rset.getString("TIPO")));
			referencias.get(rset.getString("TIPO")).setParentesco(validateString(rset.getString("PARENTESCO")));
			referencias.get(rset.getString("TIPO")).setNombre(validateString(rset.getString("NOMBRE")));
			referencias.get(rset.getString("TIPO")).setSegundoNombre(validateString(rset.getString("SEGUNDO_NOMBRE")));
			referencias.get(rset.getString("TIPO")).setApellidoPaterno(validateString(rset.getString("APELLIDO_PATERNO")));			
			referencias.get(rset.getString("TIPO")).setApellidoMaterno(validateString(rset.getString("APELLIDO_MATERNO")));
			referencias.get(rset.getString("TIPO")).setLada(validateString(rset.getString("LADA")));
			referencias.get(rset.getString("TIPO")).setNumero(validateString(rset.getString("NUMERO")));
			referencias.get(rset.getString("TIPO")).setFechaCreacion(validateString(rset.getString("FECHA_CREACION")));			
			referencias.get(rset.getString("TIPO")).setFechaModificacion(validateString(rset.getString("FECHA_MODIFICACION")));			
			referencias.get(rset.getString("TIPO")).setStatus(validateString(rset.getString("STATUS")));			
		}
		
		return referencias;
	}
		
	public void loadStatus() throws SQLException {
		// STATUS
		int i = 0;
		rset = upsert.query(queries.getQueryStatus(), cliente.getForceId());
		cliente.getHistorial().clear();
		while (rset.next()) {
			String key = rset.getString("FECHA") + i;
			cliente.getHistorial().put(key, new Status());
			cliente.getHistorial().get(key).setForceId(rset.getString("FORCE_ID"));
			cliente.getHistorial().get(key).setIdUsuario(rset.getString("ID_USUARIO"));
			cliente.getHistorial().get(key).setUsuario( fv.getInitial(rset.getString("NOMBRE")) + " " + fv.titleCase(rset.getString("APELLIDO_PATERNO")));
			cliente.getHistorial().get(key).setIdFase(rset.getString("ID_FASE"));
			cliente.getHistorial().get(key).setFase(rset.getString("FASE"));
			cliente.getHistorial().get(key).setIdStatus(rset.getString("ID_STATUS"));
			cliente.getHistorial().get(key).setStatus(rset.getString("STATUS"));
			cliente.getHistorial().get(key).setFecha(rset.getString("FECHA"));
			i++;
		}
		//System.out.println(queryStatus);
	}
	
	public void loadSeguimiento() throws SQLException {
		// SEGUIMIENTO
		rset = upsert.query(queries.getQuerySeguimiento(), cliente.getForceId());
		cliente.getSeguimiento().clear();
		while (rset.next()) {
			cliente.setSeguimiento(rset.getString("ID"), new Seguimiento());
			cliente.getSeguimiento(rset.getString("ID")).setForceId(rset.getString("FORCE_ID"));
			cliente.getSeguimiento(rset.getString("ID")).setMensaje(rset.getString("MENSAJE"));
			cliente.getSeguimiento(rset.getString("ID")).setId(rset.getString("ID"));
			cliente.getSeguimiento(rset.getString("ID")).setFechaCreacion(fv.formatTimestamp(rset.getString("FECHA_CREACION")));
			cliente.getSeguimiento(rset.getString("ID")).getUsuario().setNombre(rset.getString("NOMBRE"));
			cliente.getSeguimiento(rset.getString("ID")).getUsuario().setApellidoPaterno(rset.getString("APELLIDO_PATERNO"));			
		}
		//System.out.println(querySeguimiento);
	}

	public void loadDocumentos(String idCliente) throws SQLException {
		// DOCUMENTOS
		java.sql.PreparedStatement pstmt = upsert.getConnection().prepareStatement(queries.getQueryDocumentacion());
		pstmt.setString(1,idCliente);
		pstmt.setString(2,cliente.getForceId());
		rset = pstmt.executeQuery();
		while ( rset.next() ) {
			String key = rset.getString("ID_DOCUMENTO");
			cliente.getDocumento(key).setForceId(rset.getString("FORCE_ID"));
			cliente.getDocumento(key).setIdDocumento(key);
			cliente.getDocumento(key).setNombreDocumento( validateString(rset.getString("NOMBRE")) );
			cliente.getDocumento(key).setNombreArchivo( validateString(rset.getString("NOM_ARCHIVO")) );
			cliente.getDocumento(key).setFechaEntrega( fv.formatTimestampDB(validateString(rset.getString("FECHA_ENTREGA"))) );
			cliente.getDocumento(key).setFechaCreacion( validateString(rset.getString("FECHA_CREACION")) );
			cliente.getDocumento(key).setFechaModificacion( validateString(rset.getString("FECHA_MODIFICACION")) );
			cliente.getDocumento(key).setStatus( validateString(rset.getString("STATUS_DOC")) );

			cliente.getDocumento(key).setDriveId( validateString(rset.getString("DRIVE_ID")) );
			cliente.getDocumento(key).setDriveSize( validateString(rset.getString("DRIVE_SIZE")) );
			cliente.getDocumento(key).setDriveBase( validateString(rset.getString("NOMBRE_ARCHIVO")) );
		}
		pstmt.close();
		//System.out.println(queryDocumentacion);
	}
	
	private String validateString( String value ) {
		return ( value != null ) ? value : "";
	}
				
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}