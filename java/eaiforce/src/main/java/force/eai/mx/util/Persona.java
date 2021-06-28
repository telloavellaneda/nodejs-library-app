package force.eai.mx.util;

import java.util.LinkedHashMap;
import force.eai.mx.security.ProfileSettings;

public class Persona {
	private String idPersona = "";
	private String tipo = "";	
	private String nombre = "";
	private String segundoNombre = "";
	private String apellidoPaterno = "";
	private String apellidoMaterno = "";
	private String nombreCompleto = "";
	private String fecha = "";
	private String anios = "";
	private String meses = "";
	private String sexo = "";
	private String paisNacimiento = "";
	private String estadoNacimiento = "";
	private String lugarNacimiento = "";
	private String nacionalidad = "";
	private String rfc = "";
	private String homoclave = "";
	private String curp = "";
	private String noImss = "";
	private String nivelAcademico = "";
	private String estadoCivil = "";
	private String regimen = "";
	private String identificacion = "";
	private String numeroIdentificacion = "";
	private String email = "";
	private String fechaIngreso = "";
	private String fechaCreacion = "";
	private String fechaModificacion = "";
	private String status = "";

	private String usuario = "";
	private String contrasena = "";
	private String filtros = "";
	private String widgets = "";
	private String selecciones = "";
	
	private ProfileSettings profile;	
	
	private LinkedHashMap<String,Empleo> empleos = new LinkedHashMap<String, Empleo>();
	private LinkedHashMap<String,Telefono> telefonos = new LinkedHashMap<String, Telefono>();
	private LinkedHashMap<String,Direccion> direcciones = new LinkedHashMap<String, Direccion>();
	private LinkedHashMap<String,Referencia> referencias = new LinkedHashMap<String, Referencia>();
	
	public Persona() {
		// Default Constructor
	}
	public Persona(String perfil) {
		this.profile = new ProfileSettings(perfil);
	}
	
	public String getIdPersona() {
		return idPersona;
	}
	public String getTipo() {
		return tipo;
	}
	public String getNombre() {
		return nombre;
	}
	public String getSegundoNombre() {
		return segundoNombre;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public String getFecha() {
		return fecha;
	}
	public String getAnios() {
		return anios;
	}
	public String getMeses() {
		return meses;
	}
	public String getSexo() {
		return sexo;
	}
	public String getPaisNacimiento() {
		return paisNacimiento;
	}
	public String getEstadoNacimiento() {
		return estadoNacimiento;
	}
	public String getLugarNacimiento() {
		return lugarNacimiento;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public String getRfc() {
		return rfc;
	}
	public String getHomoclave() {
		return homoclave;
	}
	public String getCurp() {
		return curp;
	}
	public String getNoImss() {
		return noImss;
	}
	public String getNivelAcademico() {
		return nivelAcademico;
	}
	public String getEstadoCivil() {
		return estadoCivil;
	}
	public String getRegimen() {
		return regimen;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}
	public String getEmail() {
		return email;
	}
	public String getFechaIngreso() {
		return fechaIngreso;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public String getFechaModificacion() {
		return fechaModificacion;
	}
	public String getStatus() {
		return status;
	}
	public String getUsuario() {
		return usuario;
	}
	public String getContrasena() {
		return contrasena;
	}
	public String getFiltros() {
		return filtros;
	}
	public String getWidgets() {
		return widgets;
	}
	public String getSelecciones() {
		return selecciones;
	}
	public ProfileSettings getProfile() {
		return profile;
	}
	public LinkedHashMap<String,Telefono> getTelefonos() {
		return telefonos;
	}
	public LinkedHashMap<String,Direccion> getDirecciones() {
		return direcciones;
	}
	public LinkedHashMap<String,Empleo> getEmpleos() {
		return empleos;
	}
	public LinkedHashMap<String,Referencia> getReferencias() {
		return referencias;
	}
	public Telefono getTelefonos(String key) {
		if ( telefonos.get(key) == null )
			setTelefonos( key, new Telefono() );
		return telefonos.get(key);
	}
	public Direccion getDirecciones(String key) {
		if ( direcciones.get(key) == null )
			setDirecciones( key, new Direccion() );
		return direcciones.get(key);
	}
	public Empleo getEmpleos(String key) {
		if ( empleos.get(key) == null )
			setEmpleos( key, new Empleo() );
		return empleos.get(key);
	}	
	public Referencia getReferencias(String key) {
		if ( referencias.get(key) == null )
			setReferencias( key, new Referencia() );
		return referencias.get(key);
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = validateString(idPersona);
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public void setAnios(String anios) {
		this.anios = anios;
	}
	public void setMeses(String meses) {
		this.meses = meses;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public void setPaisNacimiento(String paisNacimiento) {
		this.paisNacimiento = paisNacimiento;
	}
	public void setEstadoNacimiento(String estadoNacimiento) {
		this.estadoNacimiento = estadoNacimiento;
	}
	public void setLugarNacimiento(String lugarNacimiento) {
		this.lugarNacimiento = lugarNacimiento;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public void setHomoclave(String homoclave) {
		this.homoclave = homoclave;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public void setNoImss(String noImss) {
		this.noImss = noImss;
	}
	public void setNivelAcademico(String nivelAcademico) {
		this.nivelAcademico = nivelAcademico;
	}
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public void setRegimen(String regimen) {
		this.regimen = regimen;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setUsuario(String usuario) {
		this.usuario = validateString(usuario);
	}
	public void setContrasena(String contrasena) {
		this.contrasena = validateString(contrasena);
	}
	public void setFiltros(String filtros) {
		this.filtros = validateString(filtros);
	}
	public void setWidgets(String widgets) {
		this.widgets = validateString(widgets);
	}
	public void setSelecciones(String selecciones) {
		this.selecciones = validateString(selecciones);
	}
	public void setProfile(String perfil) {
		this.profile = new ProfileSettings(perfil);
	}
	public void setTelefonos(String key, Telefono value) {
		this.telefonos.put(key,value);
	}
	public void setTelefonos(LinkedHashMap<String, Telefono> telefonos) {
		this.telefonos = telefonos;
	}
	public void setDirecciones(String key, Direccion value) {
		this.direcciones.put(key, value);
	}
	public void setDirecciones(LinkedHashMap<String, Direccion> direcciones) {
		this.direcciones = direcciones;
	}
	public void setEmpleos(String key, Empleo value) {
		this.empleos.put(key,value);
	}
	public void setEmpleos(LinkedHashMap<String, Empleo> empleos) {
		this.empleos = empleos;
	}
	public void setReferencias(String key, Referencia value) {
		this.referencias.put(key, value);
	}
	public void setReferencias(LinkedHashMap<String, Referencia> referencias) {
		this.referencias = referencias;
	}
	private String validateString( String value ) {
		return ( value != null ) ? value : "";
	}
}