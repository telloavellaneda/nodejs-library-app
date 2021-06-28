package force.eai.mx.dao;

import force.eai.mx.util.Force;
import force.eai.mx.util.Persona;

public class EaiAdmUsuarios {
	private String idCliente = "";
	private Force force;
	private Persona usuario;

	public EaiAdmUsuarios() {
		// Nothing
	}
	
	public EaiAdmUsuarios(Force force) {
		this.force = force;
	}
	
	public String insert() {
		String query = "Insert into EAI_ADM_USUARIOS\n" +
				"(\n" +
				"ID_CLIENTE\n" +
				",ID_USUARIO\n" +
				",NOMBRE\n" +
				",SEGUNDO_NOMBRE\n" +
				",APELLIDO_PATERNO\n" +
				",APELLIDO_MATERNO\n" +
				",USUARIO\n" +
				",CONTRASENA\n" +
				",EMAIL\n" +
				",PERFIL\n" +
				",FECHA_INGRESO\n" +
				",FECHA_CREACION\n" +
				",STATUS\n" +
				") values (\n" +
				"'"  + idCliente + "'\n" + 
				",'" + usuario.getIdPersona() + "'\n" +
				",'" + usuario.getNombre() + "'\n" +
				",'" + usuario.getSegundoNombre() + "'\n" +
				",'" + usuario.getApellidoPaterno() + "'\n" +
				",'" + usuario.getApellidoMaterno() + "'\n" +
				",'" + usuario.getUsuario() + "'\n" +
				",'" + usuario.getContrasena() + "'\n" +
				",'" + usuario.getEmail() + "'\n" +
				",'" + usuario.getProfile().getPerfil() + "'\n" +
				",TO_DATE('" + usuario.getFechaIngreso() + "','DD/MM/YYYY HH24:MI:SS')\n" +
				",CURRENT_TIMESTAMP\n" +
				", '" + usuario.getStatus() + "'\n" +
				")";

		//System.out.println(query);
		return query;
	}
	
	public String update() {
		String query = "Update EAI_ADM_USUARIOS\n" + 
				"SET\n" +
				"NOMBRE = '" + usuario.getNombre() + "'\n" +
				",SEGUNDO_NOMBRE = '" + usuario.getSegundoNombre() + "'\n" +
				",APELLIDO_PATERNO = '" + usuario.getApellidoPaterno() + "'\n" +
				",APELLIDO_MATERNO = '" + usuario.getApellidoMaterno() + "'\n" +
				",USUARIO = '" + usuario.getUsuario() + "'\n" +
				",CONTRASENA = '" + usuario.getContrasena() + "'\n" +
				",EMAIL = '" + usuario.getEmail() + "'\n" +
				",PERFIL = '" + usuario.getProfile().getPerfil() + "'\n" +
				",FECHA_INGRESO = TO_DATE('" + usuario.getFechaIngreso() + "','DD/MM/YYYY HH24:MI:SS')\n" +
				",FECHA_MODIFICACION = CURRENT_TIMESTAMP\n" +
				",STATUS = '" + usuario.getStatus() + "'\n" +
				"WHERE ID_CLIENTE = " + idCliente + "\n" +
				" AND ID_USUARIO = " + usuario.getIdPersona();

		//System.out.println(query);
		return query;
	}
		
	public String updateUser() {
		String query = "Update EAI_ADM_USUARIOS\n" +
				"SET FECHA_INGRESO = CURRENT_TIMESTAMP\n"+
				"WHERE ID_USUARIO = " + force.getUsuario("0").getIdPersona();
		return query;
	}
	
	public String insertLog() {
		String query = "Insert into EAI_ADM_USUARIOS_LOG\n" +
				"(\n" +
				"ID_CLIENTE\n" +
				",ID_USUARIO\n" +
				",HOSTNAME\n" +
				",IP_ADDRESS\n" +
				",BROWSER\n" +
				") values (\n" +
				"'"  + force.getIdCliente() + "'\n" + 
				",'" + force.getUsuario("0").getIdPersona() + "'\n" +
				",'" + force.getHeader("hostname") + "'\n" +
				",'" + force.getHeader("ipAddress") + "'\n" +
				",'" + force.getHeader("browser") + "'\n" +
				")";

		//System.out.println(query);
		return query;
	}
	
	public String getNextValue() {
		return "SELECT EAI_ADM_USUARIOS_SEQ.NEXTVAL FROM SYS.DUAL";		
	}
	
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	public void setUsuario(Persona usuario) {
		this.usuario = usuario;
	}
}