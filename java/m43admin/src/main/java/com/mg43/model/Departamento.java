package com.mg43.model;

import java.util.LinkedHashMap;

public class Departamento {
	private int index;
	private String id;
	private String mes;
	private String fecha;
	private String notas;
	private String total;
	private String email;
	private String recibo;
	private String adeudo;
	private String nombre;
	private String archivo;
	private String telefonos;
	private String paperless;
	private String referencia;
	private String departamento;
	private String detalleAdeudo;
	private String emailComplemento;
	private LinkedHashMap<String, Monto> montos = new LinkedHashMap<String, Monto>();
	private LinkedHashMap<String, Cobranza> cobranza = new LinkedHashMap<String, Cobranza>();

	public int getIndex() {
		return index;
	}

	public String getId() {
		return id;
	}

	public String getMes() {
		return mes;
	}

	public String getFecha() {
		return fecha;
	}

	public String getNotas() {
		return notas;
	}

	public String getTotal() {
		return total;
	}

	public String getEmail() {
		return email;
	}

	public String getRecibo() {
		return recibo;
	}

	public String getAdeudo() {
		return adeudo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getArchivo() {
		return archivo;
	}

	public String getTelefonos() {
		return telefonos;
	}

	public String getPaperless() {
		return paperless;
	}

	public String getReferencia() {
		return referencia;
	}

	public String getDepartamento() {
		return departamento;
	}

	public String getDetalleAdeudo() {
		return detalleAdeudo;
	}

	public LinkedHashMap<String, Monto> getMontos() {
		return montos;
	}

	public LinkedHashMap<String, Cobranza> getCobranza() {
		return cobranza;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRecibo(String recibo) {
		this.recibo = recibo;
	}

	public void setAdeudo(String adeudo) {
		this.adeudo = adeudo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}

	public void setPaperless(String paperless) {
		this.paperless = paperless;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public void setDetalleAdeudo(String detalleAdeudo) {
		this.detalleAdeudo = detalleAdeudo;
	}

	public void setMontos(LinkedHashMap<String, Monto> montos) {
		this.montos = montos;
	}

	public void setCobranza(LinkedHashMap<String, Cobranza> cobranza) {
		this.cobranza = cobranza;
	}

	public String getEmailComplemento() {
		return emailComplemento;
	}

	public void setEmailComplemento(String emailComplemento) {
		this.emailComplemento = emailComplemento;
	}

}