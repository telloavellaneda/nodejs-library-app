package force.eai.mx.util;

public class Filter {
	
	private String forceId = "";
	private String fullName = "";
	private String idUsuario = "";
	private String idUsuarioResponsable = "";
	private String fase = "";
	private String status = "";
	private String idBanco = "";
	private String decreto = "";
	private String dateExpYear = "";
	private String dateExpMonth = "";
	private String dateAutYear = "";
	private String dateAutMonth = "";
	private String dateFrmYear = "";
	private String dateFrmMonth = "";
	
	private boolean orderByName = false;
	private boolean orderDesc = false;
	
	public String getForceId() {
		return forceId;
	}
	public String getFullName() {
		return fullName;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public String getIdUsuarioResponsable() {
		return idUsuarioResponsable;
	}
	public String getFase() {
		return fase;
	}
	public String getStatus() {
		return status;
	}
	public String getIdBanco() {
		return idBanco;
	}
	public String getDecreto() {
		return decreto;
	}
	public String getDateExpYear() {
		return dateExpYear;
	}
	public String getDateExpMonth() {
		return dateExpMonth;
	}
	public String getDateAutYear() {
		return dateAutYear;
	}
	public String getDateAutMonth() {
		return dateAutMonth;
	}
	public String getDateFrmYear() {
		return dateFrmYear;
	}
	public String getDateFrmMonth() {
		return dateFrmMonth;
	}
	public boolean isOrderByName() {
		return orderByName;
	}
	public boolean isOrderDesc() {
		return orderDesc;
	}
	public void setForceId(String forceId) {
		this.forceId = forceId;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public void setIdUsuarioResponsable(String idUsuarioResponsable) {
		this.idUsuarioResponsable = idUsuarioResponsable;
	}	
	public void setFase(String fase) {
		this.fase = fase;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setIdBanco(String idBanco) {
		this.idBanco = idBanco;
	}
	public void setDecreto(String decreto) {
		this.decreto = decreto;
	}
	public void setDateExpYear(String dateExpYear) {
		this.dateExpYear = dateExpYear;
	}
	public void setDateExpMonth(String dateExpMonth) {
		this.dateExpMonth = dateExpMonth;
	}
	public void setDateAutYear(String dateAutYear) {
		this.dateAutYear = dateAutYear;
	}
	public void setDateAutMonth(String dateAutMonth) {
		this.dateAutMonth = dateAutMonth;
	}
	public void setDateFrmYear(String dateFrmYear) {
		this.dateFrmYear = dateFrmYear;
	}
	public void setDateFrmMonth(String dateFrmMonth) {
		this.dateFrmMonth = dateFrmMonth;
	}
	public void setOrderByName(boolean orderByName) {
		this.orderByName = orderByName;
	}
	public void setOrderDesc(boolean orderDesc) {
		this.orderDesc = orderDesc;
	}
}