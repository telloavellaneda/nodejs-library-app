package force.eai.mx.util;

public class Telefono {
	private String idPersona = "";
	private String tipo = "";
	private String codigo = "";
	private String lada = "";
	private String numero = "";
	private String status = "";

	public String getIdPersona() {
		return idPersona;
	}
	public String getTipo() {
		return tipo;
	}
	public String getCodigo() {
		return codigo;
	}
	public String getLada() {
		return lada;
	}
	public String getNumero() {
		return numero;
	}
	public String getStatus() {
		return status;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public void setLada(String lada) {
		this.lada = lada;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
}
