package force.eai.mx.form;

import java.util.Map;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import force.eai.mx.util.*;

public class FormQueryString {
	
	private Force force;
	private Cliente cliente = new Cliente();
	
	private HttpSession session;
	private HttpServletRequest request;	
	private LinkedHashMap<String,Section> sections;
	
	@SuppressWarnings("unchecked")
	public FormQueryString(HttpServletRequest request) {
		this.request = request;
		this.session = request.getSession();
		this.force = Force.class.cast(request.getSession().getAttribute("force"));
		this.sections = (LinkedHashMap<String, Section>) this.session.getAttribute("formSections");
	}
		
	public void formQueryString() throws SQLException {
		String section = String.class.cast(session.getAttribute("section"));
		
		// Want to check what the request was?
		if ( !getParameter("show_parameters").equals("") )
			listAllParameters();
				
		checkProfile(section);
		
		if ( section.equals("section0") )
			cargaRapidaQueryString();
			
		else
			cargaCompletaQueryString();		
	}
	
	private void checkProfile(String section) throws SQLException {
		if ( force.getUsuario("0").getProfile().isCargaRapida() && !section.equals("section0") )
			throw new SQLException("Usuario solo de Carga Rápida","-10");
		
		// Multiusuario sin Edición Fase 0
		// sobre clientes que no le pertenecen
		if ( 	force.getUsuario("0").getProfile().getPerfil().equals("12") &&
				cliente.getIdFase().equals("0") &&
				!cliente.getUsuarioCreacion().getIdPersona().equals("") &&
				!force.getUsuario("0").getIdPersona().equals(cliente.getUsuarioCreacion().getIdPersona()) )
			throw new SQLException("Usuario sin Edición en Fase 0","-11");
	}
	
	private void cargaRapidaQueryString() {
		String preffix = sections.get("section0").getPreffix();
		cliente.setTipo(getParameter(preffix + "tipo"));
		
		if ( cliente.getTipo().equals("2") ) {
			cliente.setPersona(genericQueryStringMorales(preffix, cliente.getPersona()));
			cliente.setApoderado(genericQueryStringGenerales(preffix, cliente.getApoderado()));
			cliente.getApoderado().setTipo("0"); // UN APODERADO SIEMPRE ES PERSONA FISICA
			cliente.getApoderado().setTelefonos("2", genericQueryStringTelefono(preffix, cliente.getApoderado().getTelefonos("2")));
		} else {
			cliente.setPersona(genericQueryStringGenerales(preffix, cliente.getPersona()));
			cliente.getPersona().setTelefonos("2", genericQueryStringTelefono(preffix, cliente.getPersona().getTelefonos("2")));
		}
		
		cliente.setCreditos("0", genericQueryStringCredito(preffix, cliente.getCreditos("0")));		
	}
	
	private void cargaCompletaQueryString() {
		sectionDatosAgendaQueryString();
		sectionDatosCreditoQueryString();
		sectionDatosGeneralesQueryString();
		sectionDomicilioActualQueryString();
		sectionInformacionLaboralQueryString();
		sectionIngresosQueryString();
		sectionReferenciasQueryString();
		if ( !cliente.getCreditos("0").getCoacreditado().equals("") ) {
			sectionCoaDatosGeneralesQueryString();
			sectionCoaDomicilioActualQueryString();
			sectionCoaInformacionLaboralQueryString();
			sectionCoaIngresosQueryString();
		}
		
		cliente.setSeccion( String.class.cast(session.getAttribute("section")) );
	}
	
	private Persona genericQueryStringGenerales( String preffix, Persona persona ) {
		persona.setTipo(getParameter(preffix + "tipo"));
		persona.setNombre(getParameter(preffix + "primer_nombre"));
		persona.setSegundoNombre(getParameter(preffix + "segundo_nombre"));
		persona.setApellidoPaterno(getParameter(preffix + "apellido_paterno"));
		persona.setApellidoMaterno(getParameter(preffix + "apellido_materno"));
		persona.setFecha(getParameter(preffix + "fecha_nacimiento"));
		persona.setAnios(getParameter(preffix + "anios"));
		persona.setMeses(getParameter(preffix + "meses"));
		persona.setSexo(getParameter(preffix + "sexo"));
		persona.setRfc(getParameter(preffix + "rfc"));
		persona.setHomoclave(getParameter(preffix + "homoclave"));
		persona.setEmail(getParameter(preffix + "email"));
		
		persona.setNombreCompleto(persona.getNombre() + " " + persona.getSegundoNombre() + " " + persona.getApellidoPaterno() + " " + persona.getApellidoMaterno());
		persona.setNombreCompleto(persona.getNombreCompleto().replaceAll("  ", " ").trim());
		persona.setStatus("1");
		
		return persona;
	}
	
	private Persona genericQueryStringMorales( String preffix, Persona persona ) {
		persona.setTipo(getParameter(preffix + "tipo"));
		persona.setNombre(getParameter(preffix + "moral_nombre"));
		persona.setFecha(getParameter(preffix + "moral_fecha"));
		persona.setRfc(getParameter(preffix + "moral_rfc"));
		persona.setHomoclave(getParameter(preffix + "moral_homoclave"));
		persona.setAnios(getParameter(preffix + "moral_anios"));
		persona.setMeses(getParameter(preffix + "moral_meses"));

		persona.setNombreCompleto(persona.getNombre().trim());

		persona.setSegundoNombre("");
		persona.setApellidoPaterno("");
		persona.setApellidoMaterno("");
		persona.setSexo("");
		persona.setPaisNacimiento("");
		persona.setEstadoNacimiento("");
		persona.setLugarNacimiento("");
		persona.setNacionalidad("");
		persona.setCurp("");
		persona.setNoImss("");
		persona.setNivelAcademico("");
		persona.setEstadoCivil("");
		persona.setRegimen("");
		persona.setIdentificacion("");
		persona.setNumeroIdentificacion("");
		
		persona.setStatus("1");

		return persona;
	}
	
	private Telefono genericQueryStringTelefono( String preffix, Telefono telefono ) {
		telefono.setLada(getParameter(preffix + "cel_lada"));
		telefono.setNumero(getParameter(preffix + "cel_numero"));

		telefono.setTipo("2");
		telefono.setCodigo("52");

		return telefono;
	}
	
	private Credito genericQueryStringCredito( String preffix, Credito credito ) {
		credito.setIdBanco(getParameter(preffix + "id_banco"));
		credito.setBanco(getParameter(preffix + "banco"));
		credito.setProducto(getParameter(preffix + "producto"));
		credito.setDestino(getParameter(preffix + "destino"));
		credito.setImporteSolicitado(getParameter(preffix + "importe_solic"));
		credito.setStatus("1");
		
		return credito;
	}
	
	private Persona genericQueryStringEmpleo( String preffix, Persona persona ) {
		persona.getEmpleos(persona.getIdPersona()).setNombreEmpresa(getParameter(preffix + "nombre_empresa"));
		persona.getEmpleos(persona.getIdPersona()).setSector(getParameter(preffix + "sector_empresa"));
		persona.getEmpleos(persona.getIdPersona()).setGiro(getParameter(preffix + "giro_empresa"));
		persona.getEmpleos(persona.getIdPersona()).setPuesto(getParameter(preffix + "puesto"));
		persona.getEmpleos(persona.getIdPersona()).setProfesion(getParameter(preffix + "profesion"));
		persona.getEmpleos(persona.getIdPersona()).setTipoEmpleo(getParameter(preffix + "tipo_empleo"));
		persona.getEmpleos(persona.getIdPersona()).setTipoContrato(getParameter(preffix + "tipo_contrato"));
		persona.getEmpleos(persona.getIdPersona()).setAntiguedadAnios(getParameter(preffix + "anios"));
		persona.getEmpleos(persona.getIdPersona()).setAntiguedadMeses(getParameter(preffix + "meses"));
		
		persona.getDirecciones("1").setTipo("1");
		persona.getDirecciones("1").setCalle(getParameter(preffix + "calle"));
		persona.getDirecciones("1").setNumeroExterior(getParameter(preffix + "num_exterior"));
		persona.getDirecciones("1").setNumeroInterior(getParameter(preffix + "num_interior"));
		persona.getDirecciones("1").setCodigoPostal(getParameter(preffix + "codigo"));
		persona.getDirecciones("1").setColonia(getParameter(preffix + "colonia"));
		persona.getDirecciones("1").setDelegacion(getParameter(preffix + "delegacion"));
		persona.getDirecciones("1").setCiudad(getParameter(preffix + "ciudad"));
		persona.getDirecciones("1").setEstado(getParameter(preffix + "estado"));
		
		return persona;
	}
	
	private Persona genericQueryStringIngresos( String preffix, Persona persona ) {
		persona.getEmpleos(persona.getIdPersona()).setIngresosBruto(getParameter(preffix + "ingresos_bruto"));
		persona.getEmpleos(persona.getIdPersona()).setIngresosNeto(getParameter(preffix + "ingresos_neto"));
		persona.getEmpleos(persona.getIdPersona()).setOtrosIngresos(getParameter(preffix + "otros_ingresos"));
		persona.getEmpleos(persona.getIdPersona()).setFuenteIngresos(getParameter(preffix + "fuente_ingresos"));
		
		return persona;
	}

	private void sectionDatosAgendaQueryString() {
		String preffix = sections.get("section1").getPreffix();

		cliente.getUsuario().setIdPersona(getParameter(preffix + "usuario_asignado"));
		cliente.getUsuario().setNombreCompleto(getParameter(preffix + "nombre_usuario_asignado"));
		cliente.getUsuarioResponsable().setIdPersona(getParameter(preffix + "responsable"));
		cliente.getCreditos("0").setEjecutivoBanco(getParameter(preffix + "ejecutivo_banco"));
		cliente.getCreditos("0").setNotaria(getParameter(preffix + "notaria"));
		cliente.getCreditos("0").setAbogado(getParameter(preffix + "abogado"));

		cliente.setFechas("expediente", getParameter(preffix + "fecha_expediente"));
		cliente.setFechas("fase1", getParameter(preffix + "fecha_fase_1"));
		cliente.setFechas("fase2", getParameter(preffix + "fecha_fase_2"));
		cliente.setFechas("fase3", getParameter(preffix + "fecha_fase_3"));
		cliente.setFechas("autorizacion", getParameter(preffix + "fecha_autorizacion"));
		cliente.setFechas("firma", getParameter(preffix + "fecha_firma"));
		cliente.setFechas("linea", getParameter(preffix + "fecha_vencimiento_linea"));
		cliente.setFechas("certificado", getParameter(preffix + "fecha_vencimiento_certificado"));
	}
	
	private void sectionDatosCreditoQueryString() {
		String preffix = sections.get("section2").getPreffix();
				
		cliente.setCreditos("0", genericQueryStringCredito("cred_", cliente.getCreditos("0")));
		
		cliente.getCreditos("0").setAdherido(getParameter(preffix + "adherido"));
		cliente.getCreditos("0").setAdheridoOpcion(getParameter(preffix + "adherido_opcion"));
		cliente.getCreditos("0").setImporteAprobado(getParameter(preffix + "importe_aprob"));
		cliente.getCreditos("0").setImporteFinal(getParameter(preffix + "importe_final"));
		cliente.getCreditos("0").setDecreto(getParameter(preffix + "decreto"));
		cliente.getCreditos("0").setDecretoComentarios(getParameter(preffix + "decreto_comentarios"));
		cliente.getCreditos("0").setCoacreditado(getParameter(preffix + "coacreditado"));
		cliente.getCreditos("0").setValorEstimado(getParameter(preffix + "valor_estimado"));
		cliente.getCreditos("0").setEnganche(getParameter(preffix + "enganche"));
		cliente.getCreditos("0").setOrigenEnganche(getParameter(preffix + "origen_enganche"));
		cliente.getCreditos("0").setPlazo(getParameter(preffix + "plazo"));
		cliente.getCreditos("0").setTasa(getParameter(preffix + "tasa"));
		cliente.getCreditos("0").setAforo(getParameter(preffix + "aforo"));
		cliente.getCreditos("0").setTipoComisionApertura(getParameter(preffix + "tipo_comision_apertura"));
		cliente.getCreditos("0").setComisionApertura(getParameter(preffix + "comision_apertura"));
	}
	
	private void sectionDatosGeneralesQueryString() {
		String preffix = sections.get("section3").getPreffix();
		Persona persona = new Persona();
		
		cliente.setTipo(getParameter(preffix + "tipo"));

		if ( cliente.getTipo().equals("2") ) {
			cliente.setPersona(genericQueryStringMorales(preffix, cliente.getPersona()));
			cliente.setApoderado(genericQueryStringGenerales(preffix, cliente.getApoderado()));
			cliente.getApoderado().setTipo("0"); // UN APODERADO SIEMPRE ES PERSONA FISICA

			persona = cliente.getApoderado();
		} else {
			cliente.setPersona(genericQueryStringGenerales(preffix, cliente.getPersona()));

			persona = cliente.getPersona();
		}
				
		persona.setPaisNacimiento(getParameter(preffix + "pais_nacimiento"));
		persona.setEstadoNacimiento(getParameter(preffix + "estado_nacimiento"));
		persona.setLugarNacimiento(getParameter(preffix + "lugar_nacimiento"));
		persona.setNacionalidad(getParameter(preffix + "nacionalidad"));
		persona.setCurp(getParameter(preffix + "curp"));
		persona.setNoImss(getParameter(preffix + "no_imss"));
		persona.setNivelAcademico(getParameter(preffix + "nivel_academico"));
		persona.setEstadoCivil(getParameter(preffix + "estado_civil"));
		persona.setRegimen(getParameter(preffix + "regimen"));
		persona.setIdentificacion(getParameter(preffix + "identificacion"));
		persona.setNumeroIdentificacion(getParameter(preffix + "numero_identificacion"));

		if ( cliente.getTipo().equals("2") ) {
			persona.setTipo("0"); // UN APODERADO SIEMPRE ES PERSONA FISICA
			cliente.setApoderado(persona);

		} else
			cliente.setPersona(persona);
	}
	
	private void sectionDomicilioActualQueryString() {
		String preffix = sections.get("section4").getPreffix();
		Persona persona = new Persona();
		
		if ( cliente.getTipo().equals("2") )
			persona = cliente.getApoderado();
		else
			persona = cliente.getPersona();
		
		persona.getDirecciones("0").setTipo("0");
		persona.getDirecciones("0").setCalle(getParameter(preffix + "calle"));
		persona.getDirecciones("0").setNumeroExterior(getParameter(preffix + "num_exterior"));
		persona.getDirecciones("0").setNumeroInterior(getParameter(preffix + "num_interior"));
		persona.getDirecciones("0").setCodigoPostal(getParameter(preffix + "codigo"));
		persona.getDirecciones("0").setColonia(getParameter(preffix + "colonia"));
		persona.getDirecciones("0").setDelegacion(getParameter(preffix + "delegacion"));
		persona.getDirecciones("0").setCiudad(getParameter(preffix + "ciudad"));
		persona.getDirecciones("0").setEstado(getParameter(preffix + "estado"));
		persona.getDirecciones("0").setTipoDomicilio(getParameter(preffix + "tipo_domicilio"));
		persona.getDirecciones("0").setMontoRenta(getParameter(preffix + "monto"));
		persona.getDirecciones("0").setAntiguedadAnios(getParameter(preffix + "anios"));
		persona.getDirecciones("0").setAntiguedadMeses(getParameter(preffix + "meses"));
		persona.getDirecciones("0").setStatus("1");
		
		persona.getTelefonos("0").setCodigo("52");
		persona.getTelefonos("0").setLada(getParameter(preffix + "casa_lada"));
		persona.getTelefonos("0").setNumero(getParameter(preffix + "casa_numero"));
		persona.getTelefonos("0").setTipo("0");
		persona.getTelefonos("0").setStatus("1");

		persona.getTelefonos("1").setCodigo("52");
		persona.getTelefonos("1").setLada(getParameter(preffix + "oficina_lada"));
		persona.getTelefonos("1").setNumero(getParameter(preffix + "oficina_numero"));
		persona.getTelefonos("1").setTipo("1");
		persona.getTelefonos("1").setStatus("1");

		persona.setTelefonos("2", genericQueryStringTelefono(preffix, persona.getTelefonos("2")));
		
		persona.getTelefonos("3").setCodigo("52");
		persona.getTelefonos("3").setLada(getParameter(preffix + "fax_lada"));
		persona.getTelefonos("3").setNumero(getParameter(preffix + "fax_numero"));
		persona.getTelefonos("3").setTipo("3");
		persona.getTelefonos("3").setStatus("1");
		
		if ( cliente.getTipo().equals("2"))
			cliente.setApoderado(persona);	
		else 
			cliente.setPersona(persona);
	}
	
	private void sectionInformacionLaboralQueryString() {
		String preffix = sections.get("section5").getPreffix();
				
		if ( cliente.getTipo().equals("2"))
			cliente.setApoderado(genericQueryStringEmpleo(preffix, cliente.getApoderado()));	
		else 
			cliente.setPersona(genericQueryStringEmpleo(preffix, cliente.getPersona()));
	}

	private void sectionIngresosQueryString() {
		String preffix = sections.get("section6").getPreffix();
		
		if ( cliente.getTipo().equals("2"))
			cliente.setApoderado(genericQueryStringIngresos(preffix,cliente.getApoderado()));	
		else 
			cliente.setPersona(genericQueryStringIngresos(preffix,cliente.getPersona()));
	}
	
	private void sectionReferenciasQueryString() {
		String preffix = sections.get("section7").getPreffix();
		Persona persona = new Persona();
		
		if ( cliente.getTipo().equals("2") )
			persona = cliente.getApoderado();
		else
			persona = cliente.getPersona();
		
		for (int i = 1; i < 4; i++) {
			persona.getReferencias(i+"").setTipo(i + "");
			persona.getReferencias(i+"").setParentesco(getParameter(preffix + "parentesco_" + i));
			persona.getReferencias(i+"").setNombre(getParameter(preffix + "primer_nombre_" + i));
			persona.getReferencias(i+"").setSegundoNombre(getParameter(preffix + "segundo_nombre_" + i));
			persona.getReferencias(i+"").setApellidoPaterno(getParameter(preffix + "apellido_paterno_" + i));
			persona.getReferencias(i+"").setApellidoMaterno(getParameter(preffix + "apellido_materno_" + i));
			persona.getReferencias(i+"").setLada(getParameter(preffix + "tel_lada_" + i));
			persona.getReferencias(i+"").setNumero(getParameter(preffix + "tel_numero_" + i));
		}
		
		if ( cliente.getTipo().equals("2"))
			cliente.setApoderado(persona);	
		else 
			cliente.setPersona(persona);
	}
		
	private void sectionCoaDatosGeneralesQueryString() {
		String preffix = sections.get("section8").getPreffix();
		
		cliente.getCoacreditado("0").setTipo("0");
		cliente.getCoacreditado("0").setNombre(getParameter(preffix + "primer_nombre"));
		cliente.getCoacreditado("0").setSegundoNombre(getParameter(preffix + "segundo_nombre"));
		cliente.getCoacreditado("0").setApellidoPaterno(getParameter(preffix + "apellido_paterno"));
		cliente.getCoacreditado("0").setApellidoMaterno(getParameter(preffix + "apellido_materno"));
		cliente.getCoacreditado("0").setFecha(getParameter(preffix + "fecha_nacimiento"));
		cliente.getCoacreditado("0").setAnios(getParameter(preffix + "anios"));
		cliente.getCoacreditado("0").setMeses(getParameter(preffix + "meses"));
		cliente.getCoacreditado("0").setSexo(getParameter(preffix + "sexo"));
		cliente.getCoacreditado("0").setRfc(getParameter(preffix + "rfc"));
		cliente.getCoacreditado("0").setHomoclave(getParameter(preffix + "homoclave"));

		cliente.getCoacreditado("0").setPaisNacimiento(getParameter(preffix + "pais_nacimiento"));
		cliente.getCoacreditado("0").setEstadoNacimiento(getParameter(preffix + "estado_nacimiento"));
		cliente.getCoacreditado("0").setLugarNacimiento(getParameter(preffix + "lugar_nacimiento"));
		cliente.getCoacreditado("0").setNacionalidad(getParameter(preffix + "nacionalidad"));
		cliente.getCoacreditado("0").setCurp(getParameter(preffix + "curp"));
		cliente.getCoacreditado("0").setNoImss(getParameter(preffix + "no_imss"));
		cliente.getCoacreditado("0").setNivelAcademico(getParameter(preffix + "nivel_academico"));
		cliente.getCoacreditado("0").setEstadoCivil(getParameter(preffix + "estado_civil"));
		cliente.getCoacreditado("0").setRegimen(getParameter(preffix + "regimen"));
		cliente.getCoacreditado("0").setIdentificacion(getParameter(preffix + "identificacion"));
		cliente.getCoacreditado("0").setNumeroIdentificacion(getParameter(preffix + "numero_identificacion"));
		cliente.getCoacreditado("0").setEmail(getParameter(preffix + "email"));

		cliente.getCoacreditado("0").setNombreCompleto(
				cliente.getCoacreditado("0").getNombre() + " " +
				cliente.getCoacreditado("0").getSegundoNombre() + " " +
				cliente.getCoacreditado("0").getApellidoPaterno() + " " +
				cliente.getCoacreditado("0").getApellidoMaterno()
			);
		cliente.getCoacreditado("0").setNombreCompleto( cliente.getCoacreditado("0").getNombreCompleto().replaceAll("  ", " "));
	}
	
	private void sectionCoaDomicilioActualQueryString() {
		String preffix = sections.get("section9").getPreffix();

		cliente.getCoacreditado("0").getDirecciones("0").setTipo("0");
		cliente.getCoacreditado("0").getDirecciones("0").setCalle(getParameter(preffix + "calle"));
		cliente.getCoacreditado("0").getDirecciones("0").setNumeroExterior(getParameter(preffix + "num_exterior"));
		cliente.getCoacreditado("0").getDirecciones("0").setNumeroInterior(getParameter(preffix + "num_interior"));
		cliente.getCoacreditado("0").getDirecciones("0").setCodigoPostal(getParameter(preffix + "codigo"));
		cliente.getCoacreditado("0").getDirecciones("0").setColonia(getParameter(preffix + "colonia"));
		cliente.getCoacreditado("0").getDirecciones("0").setDelegacion(getParameter(preffix + "delegacion"));
		cliente.getCoacreditado("0").getDirecciones("0").setCiudad(getParameter(preffix + "ciudad"));
		cliente.getCoacreditado("0").getDirecciones("0").setEstado(getParameter(preffix + "estado"));
		cliente.getCoacreditado("0").getDirecciones("0").setTipoDomicilio(getParameter(preffix + "tipo_domicilio"));
		cliente.getCoacreditado("0").getDirecciones("0").setMontoRenta(getParameter(preffix + "monto"));
		cliente.getCoacreditado("0").getDirecciones("0").setAntiguedadAnios(getParameter(preffix + "anios"));
		cliente.getCoacreditado("0").getDirecciones("0").setAntiguedadMeses(getParameter(preffix + "meses"));
		cliente.getCoacreditado("0").getDirecciones("0").setStatus("1");
		
		cliente.getCoacreditado("0").getTelefonos("0").setCodigo("52");
		cliente.getCoacreditado("0").getTelefonos("0").setLada(getParameter(preffix + "casa_lada"));
		cliente.getCoacreditado("0").getTelefonos("0").setNumero(getParameter(preffix + "casa_numero"));
		cliente.getCoacreditado("0").getTelefonos("0").setTipo("0");
		cliente.getCoacreditado("0").getTelefonos("0").setStatus("1");

		cliente.getCoacreditado("0").getTelefonos("1").setCodigo("52");
		cliente.getCoacreditado("0").getTelefonos("1").setLada(getParameter(preffix + "oficina_lada"));
		cliente.getCoacreditado("0").getTelefonos("1").setNumero(getParameter(preffix + "oficina_numero"));
		cliente.getCoacreditado("0").getTelefonos("1").setTipo("1");
		cliente.getCoacreditado("0").getTelefonos("1").setStatus("1");

		cliente.getCoacreditado("0").getTelefonos("2").setCodigo("52");
		cliente.getCoacreditado("0").getTelefonos("2").setLada(getParameter(preffix + "cel_lada"));
		cliente.getCoacreditado("0").getTelefonos("2").setNumero(getParameter(preffix + "cel_numero"));
		cliente.getCoacreditado("0").getTelefonos("2").setTipo("2");
		cliente.getCoacreditado("0").getTelefonos("2").setStatus("1");
		
		cliente.getCoacreditado("0").getTelefonos("3").setCodigo("52");
		cliente.getCoacreditado("0").getTelefonos("3").setLada(getParameter(preffix + "fax_lada"));
		cliente.getCoacreditado("0").getTelefonos("3").setNumero(getParameter(preffix + "fax_numero"));
		cliente.getCoacreditado("0").getTelefonos("3").setTipo("3");
		cliente.getCoacreditado("0").getTelefonos("3").setStatus("1");
	}
	
	private void sectionCoaInformacionLaboralQueryString() {
		String preffix = sections.get("section10").getPreffix();
				
		cliente.setCoacreditado("0", genericQueryStringEmpleo(preffix, cliente.getCoacreditado("0")));	
	}

	private void sectionCoaIngresosQueryString() {
		String preffix = sections.get("section11").getPreffix();
				
		cliente.setCoacreditado("0", genericQueryStringIngresos(preffix, cliente.getCoacreditado("0")));	
	}
	
	private String getParameter( String key ) {
		return ( request.getParameter(key) != null ) ? request.getParameter(key) : "";
	}
	
	private void listAllParameters() {
		@SuppressWarnings("unchecked")
		Map<String, String[]> map = request.getParameterMap();
		for (java.util.Iterator<Map.Entry<String,String[]>> it = map.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<String, String[]> abc = it.next();
			System.out.print( abc.getKey() + " ");
			for (int i = 0; i < abc.getValue().length; i++) 
				System.out.print( abc.getValue()[i] + " ");
			System.out.print("\n");
		}
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}