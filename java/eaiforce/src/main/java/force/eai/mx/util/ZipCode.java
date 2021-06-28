package force.eai.mx.util;

public class ZipCode {
	private String codigo = "";
	private String colonia = "";
	private String tipoColonia = "";
	private String delegacion = "";
	private String ciudad = "";
	private String estado = "";
	private String index = "";
	private String status = "";
	
	public String getCodigo() {
		return codigo;
	}
	public String getColonia() {
		return colonia;
	}
	public String getTipoColonia() {
		return tipoColonia;
	}
	public String getDelegacion() {
		return delegacion;
	}
	public String getCiudad() {
		return ciudad;
	}
	public String getEstado() {
		return estado;
	}
	public String getIndex() {
		return index;
	}
	public String getStatus() {
		return status;
	}
	public void setCodigo(String codigo) {
		this.codigo = validateString(codigo);
	}
	public void setColonia(String colonia) {
		this.colonia = validateString(colonia);
	}
	public void setTipoColonia(String tipoColonia) {
		this.tipoColonia = validateString(tipoColonia);
	}
	public void setDelegacion(String delegacion) {
		this.delegacion = validateString(delegacion);
	}
	public void setCiudad(String ciudad) {
		this.ciudad = validateString(ciudad);
	}
	public void setEstado(String estado) {
		this.estado = validateString(estado);
	}
	public void setIndex(String index) {
		this.index = validateString(index);
	}
	public void setStatus(String status) {
		this.status = validateString(status);
	}
	private String validateString( String value ) {
		return ( value != null ) ? value : "";
	}
}