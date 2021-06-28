package force.eai.mx.util;

import java.util.LinkedHashMap;

public class Catalogo {
	private	String id = "";
	private	String nombre = "";
	private	String descripcion = "";
	private String status = "";
	
	private LinkedHashMap<String,String> campos = new LinkedHashMap<String, String>();
	
	public Catalogo() {
		// Default Constructor
	}

	public Catalogo(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Catalogo(String id, String nombre, String status) {
		this.id = id;
		this.nombre = nombre;
		this.status = status;
	}

	public String getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getStatus() {
		return status;
	}
	public String getCampo(String key) {
		if ( campos.get(key) ==  null)
			setCampo(key, new String());
		return campos.get(key);
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setCampo(String key, String value) {
		this.campos.put(key, value);
	}
}
