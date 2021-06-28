package force.eai.mx.util;

import java.util.LinkedHashMap;

public class Cliente {
	private String forceId = "";
	private String driveId = "";
	private String previousId = "";
	private String tipo = "0";
	private String idFase = "0";
	private String fase = "FASE 0";
	private String idStatus = "0";
	private String status = "PROSPECTO";
	private String seccion = "";

	private Persona usuario = new Persona();
	private Persona usuarioCreacion = new Persona();
	private Persona usuarioResponsable = new Persona();
	private Persona usuarioModificacion = new Persona();

	private Persona persona = new Persona();
	private Persona conyuge = new Persona();
	private Persona apoderado = new Persona();
	private LinkedHashMap<String,Persona> coacreditado = new LinkedHashMap<String, Persona>();
	private LinkedHashMap<String,Persona> referencias = new LinkedHashMap<String, Persona>();
	
	private LinkedHashMap<String,String> fechas = new LinkedHashMap<String, String>();
	private LinkedHashMap<String,Credito> creditos = new LinkedHashMap<String, Credito>();
	private LinkedHashMap<String,Status> historial = new LinkedHashMap<String, Status>();
	private LinkedHashMap<String,Documento> documentos = new LinkedHashMap<String, Documento>();
	private LinkedHashMap<String,Seguimiento> seguimiento = new LinkedHashMap<String, Seguimiento>();
	
	private String validateString( String value ) {
		return ( value != null ) ? value : "";
	}
	public String getForceId() {
		return forceId;
	}
	public String getDriveId() {
		return driveId;
	}
	public String getPreviousId() {
		return previousId;
	}
	public String getTipo() {
		return tipo;
	}
	public String getIdFase() {
		return idFase;
	}
	public String getFase() {
		return fase;
	}
	public String getIdStatus() {
		return idStatus;
	}
	public String getStatus() {
		return status;
	}
	public String getSeccion() {
		return seccion;
	}
	public Persona getUsuario() {
		return usuario;
	}
	public Persona getUsuarioCreacion() {
		return usuarioCreacion;
	}
	public Persona getUsuarioResponsable() {
		return usuarioResponsable;
	}
	public Persona getUsuarioModificacion() {
		return usuarioModificacion;
	}
	public Persona getPersona() {
		return persona;
	}
	public Persona getConyuge() {
		return conyuge;
	}
	public Persona getApoderado() {
		return apoderado;
	}
	public Persona getCoacreditado(String key) {
		if (coacreditado.get(key) == null)
			coacreditado.put(key, new Persona());
		return coacreditado.get(key);
	}
	public Persona getReferencia(String key) {
		if (referencias.get(key) == null)
			referencias.put(key, new Persona());
		return referencias.get(key);
	}
	public String getFechas(String key) {
		if ( fechas.get(key) == null )
			setFechas( key, "");
		return fechas.get(key);
	}
	public LinkedHashMap<String,String> getFechas() {
		return fechas;
	}
	public Credito getCreditos(String key) {
		if ( creditos.get(key) == null )
			setCreditos( key, new Credito() );
		return creditos.get(key);
	}
	public Seguimiento getSeguimiento(String key) {
		if ( seguimiento.get(key) == null )
			setSeguimiento( key, new Seguimiento() );
		return seguimiento.get(key);
	}
	public LinkedHashMap<String, Status> getHistorial() {
		return historial;
	}
	public LinkedHashMap<String,Seguimiento> getSeguimiento() {
		return seguimiento;
	}
	public Documento getDocumento(String key) {
		if ( documentos.get(key) == null )
			setDocumento( key, new Documento() );
		return documentos.get(key);
	}	
	public LinkedHashMap<String,Documento> getDocumentos() {
		return documentos;
	}
	public void setForceId(String forceId) {
		this.forceId = validateString(forceId);
	}
	public void setDriveId(String driveId) {
		this.driveId = driveId;
	}
	public void setPreviousId(String previousId) {
		this.previousId = previousId;
	}
	public void setTipo(String tipo) {
		this.tipo = validateString(tipo);
	}
	public void setIdFase(String idFase) {
		this.idFase = idFase;
	}
	public void setFase(String fase) {
		this.fase = fase;
	}
	public void setIdStatus(String idStatus) {
		this.idStatus = idStatus;
	}
	public void setStatus(String status) {
		this.status = validateString(status);
	}
	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}
	public void setUsuario(Persona usuario) {
		this.usuario = usuario;
	}
	public void setUsuarioCreacion(Persona usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}
	public void setUsuarioResponsable(Persona usuarioResponsable) {
		this.usuarioResponsable = usuarioResponsable;
	}
	public void setUsuarioModificacion(Persona usuario) {
		this.usuarioModificacion = usuario;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public void setConyuge(Persona conyuge) {
		this.conyuge = conyuge;
	}
	public void setApoderado(Persona apoderado) {
		this.apoderado = apoderado;
	}
	public void setCoacreditado(String key, Persona coacreditado) {
		this.coacreditado.put(key, coacreditado);
	}
	public void setReferencia(String key, Persona referencia) {
		this.referencias.put(key, referencia);
	}
	public void setFechas(String key, String value) {
		this.fechas.put(key,validateString(value));
	}
	public void setCreditos(String key, Credito value) {
		this.creditos.put(key,value);
	}
	public void setHistorial(LinkedHashMap<String, Status> historial) {
		this.historial = historial;
	}
	public void setSeguimiento(String key, Seguimiento value) {
		this.seguimiento.put(key,value);
	}
	public void setDocumento(String key, Documento value) {
		this.documentos.put(key,value);
	}
	public void removeSeguimiento(String key) {
		if ( this.seguimiento.get(key) != null )
			this.seguimiento.remove(key);
	}
}