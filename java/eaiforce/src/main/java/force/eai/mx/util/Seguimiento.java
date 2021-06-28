package force.eai.mx.util;

public class Seguimiento {
	private String forceId = "";
	private String id = "";
	private String mensaje = "";
	private Persona usuario = new Persona();
	private String fechaCreacion = "";
	
	public String getForceId() {
		return forceId;
	}
	public String getId() {
		return id;
	}
	public String getMensaje() {
		return mensaje;
	}
	public Persona getUsuario() {
		return usuario;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setForceId(String forceId) {
		this.forceId = forceId;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setUsuario(Persona usuario) {
		this.usuario = usuario;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
}
