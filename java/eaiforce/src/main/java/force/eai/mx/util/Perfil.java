package force.eai.mx.util;

import java.util.LinkedHashMap;

public class Perfil {
	private String banco = "";
	private String cuenta = "";
	private String clabe = "";
	private String tarjetaCredito = "";
	private String fechaExpiracion = "";
	private String codigoSeguridad = "";
	private String sitioWeb = "";
	private String dominio = "";
	private String fechaCreacion = "";
	private String fechaModificacion = "";
	private String status = "";

	private LinkedHashMap<String,Catalogo> parametros = new LinkedHashMap<String, Catalogo>();

	public String getBanco() {
		return banco;
	}
	public String getCuenta() {
		return cuenta;
	}
	public String getClabe() {
		return clabe;
	}
	public String getTarjetaCredito() {
		return tarjetaCredito;
	}
	public String getFechaExpiracion() {
		return fechaExpiracion;
	}
	public String getCodigoSeguridad() {
		return codigoSeguridad;
	}
	public String getSitioWeb() {
		return sitioWeb;
	}
	public String getDominio() {
		return dominio;
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
	public LinkedHashMap<String, Catalogo> getParametros() {
		return parametros;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public void setClabe(String clabe) {
		this.clabe = clabe;
	}
	public void setTarjetaCredito(String tarjetaCredito) {
		this.tarjetaCredito = tarjetaCredito;
	}
	public void setFechaExpiracion(String fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}
	public void setCodigoSeguridad(String codigoSeguridad) {
		this.codigoSeguridad = codigoSeguridad;
	}
	public void setSitioWeb(String sitioWeb) {
		this.sitioWeb = sitioWeb;
	}
	public void setDominio(String dominio) {
		this.dominio = dominio;
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
	public void setParametro(String key, Catalogo value) {
		this.parametros.put(key, value);
	}
}