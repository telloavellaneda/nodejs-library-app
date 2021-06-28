package force.eai.mx.util;

import java.util.LinkedHashMap;

public class Force {
	private String idCliente = "";
	private String status = "";

	private Persona persona = new Persona();
	private Perfil  perfil  = new Perfil(); 

	private LinkedHashMap<String,String> header = new LinkedHashMap<String, String>();
	private LinkedHashMap<String,String> fechas = new LinkedHashMap<String, String>();
	private LinkedHashMap<String,Persona> usuarios = new LinkedHashMap<String, Persona>();
	private LinkedHashMap<String,Telefono> telefonos = new LinkedHashMap<String, Telefono>();
	private LinkedHashMap<String,Direccion> direcciones = new LinkedHashMap<String, Direccion>();
	
	public String getIdCliente() {
		return idCliente;
	}
	public String getStatus() {
		return status;
	}
	public Persona getPersona() {
		return persona;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public String getHeader(String key) {
		return ( header.get(key) != null ) ? header.get(key) : "";
	}
	public String getFechas(String key) {
		return ( fechas.get(key) != null ) ? fechas.get(key) : "";
	}
	public Persona getUsuario(String key) {
		if ( usuarios.get(key) == null )
			setUsuario( key, new Persona() );
		return usuarios.get(key);
	}
	public LinkedHashMap<String, Persona> getUsuarios() {
		return usuarios;
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
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	public void setHeader(String key, String value) {
		this.header.put(key, value);
	}
	public void setFechas(String key, String value) {
		this.fechas.put(key, value);
	}
	public void setUsuario(String key, Persona value) {
		this.usuarios.put(key, value);
	}
	public void setTelefonos(String key, Telefono value) {
		this.telefonos.put(key,value);
	}
	public void setDirecciones(String key, Direccion value) {
		this.direcciones.put(key, value);
	}
}