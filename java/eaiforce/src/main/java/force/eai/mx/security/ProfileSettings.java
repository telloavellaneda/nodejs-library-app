package force.eai.mx.security;

public class ProfileSettings {

	private String perfil = "";
	
	private boolean admin 			= false;
	private boolean readOnly 		= false;
	private boolean cargaRapida 	= false;
	private boolean multiusuario 	= false;
	private boolean operaciones 	= false;
	private boolean driveAccess		= false;
	
	public ProfileSettings(String perfil) {
		this.perfil = perfil;
		
		setAdmin();
		setMultiusuario();
		setOperaciones();
		setReadOnly();
		setCargaRapida();
		setDriveAccess();
	}
	
	// PROFILE SETTINGS
	private void setAdmin() {
		if ( this.perfil.equals("99") )
			this.admin = true;
		else
			this.admin = false;
	}
	
	private void setMultiusuario() {
		if ( this.perfil.equals("10") ||
			 this.perfil.equals("11") ||
			 this.perfil.equals("12") || 
			 this.perfil.equals("13") || 
			 this.perfil.equals("99") )
			this.multiusuario = true;
		else
			this.multiusuario = false;
	}
	
	private void setOperaciones() {
		if ( this.perfil.equals("5")  ||
			 this.perfil.equals("13") )
			this.operaciones = true;
		else
			this.operaciones = false;
	}
	
	private void setReadOnly() {
		if ( this.perfil.equals("2") ||
			 this.perfil.equals("11") )
			this.readOnly = true;
		else
			this.readOnly = false;
	}
	
	private void setCargaRapida() {
		if ( this.perfil.equals("3") ||
			 this.perfil.equals("4") )
			this.cargaRapida = true;
		else
			this.cargaRapida = false;
	}
	
	private void setDriveAccess() {
		if ( this.perfil.equals("4") )
			this.driveAccess = false;
		else
			this.driveAccess = true;		
	}
	
	public String getPerfil() {
		return perfil;
	}
	public boolean isAdmin() {
		return admin;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public boolean isCargaRapida() {
		return cargaRapida;
	}
	public boolean isMultiusuario() {
		return multiusuario;
	}
	public boolean isOperaciones() {
		return operaciones;
	}
	public boolean isDriveAccess() {
		return driveAccess;
	}
}