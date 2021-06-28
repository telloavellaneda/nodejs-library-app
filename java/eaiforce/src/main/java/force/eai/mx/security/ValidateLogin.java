package force.eai.mx.security;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

import force.eai.mx.util.Force;
import force.eai.mx.util.Persona;
import force.eai.mx.database.Upsert;
import force.eai.mx.dao.EaiAdmUsuarios;

public class ValidateLogin {
	private Force force;
	private Upsert upsert;
	private ResultSet rset;
	
	public ValidateLogin(Connection connection) throws SQLException {
		this.upsert = new Upsert(connection);
		this.force = new Force();
	}
	
	public void close() {
		try {
			this.rset.close();
			this.upsert.close();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public void validateLogin(String x, String hostname, String ipAddress, String browser) throws SQLException {
		if ( hostname.equals("") || ipAddress.equals("") || browser.equals("") )
			throw new SQLException("La solicitud no puede ser procesada debido a factores de seguridad.");
		
		force.setHeader("hostname", hostname);
		force.setHeader("ipAddress", ipAddress);
		force.setHeader("browser", browser);
		
		String valores[] = decodeQueryString(x);
		String usuario = valores[0];
		String password = valores[1];
		
		validateCustomer(usuario.split("@")[1]);
		validateUser(usuario, password);
		updateUser();
		loadPreferences();		
	}
        
	private void validateCustomer(String dominio) throws SQLException {
		String query = 	"SELECT * FROM EAI_ADM_CLIENTES " +
						"WHERE UPPER(DOMINIO) = UPPER('" + dominio + "')";

		rset = upsert.query(query);
		if (rset.next()) {
			force.setIdCliente(rset.getString("ID_CLIENTE"));
			force.getPersona().setTipo(rset.getString("TIPO"));
			force.getPersona().setNombre(rset.getString("NOMBRE"));
			force.getPersona().setSegundoNombre(rset.getString("SEGUNDO_NOMBRE"));
			force.getPersona().setApellidoPaterno(rset.getString("APELLIDO_PATERNO"));
			force.getPersona().setApellidoMaterno(rset.getString("APELLIDO_MATERNO"));
			force.getPersona().setFecha(rset.getString("FECHA"));
			force.getPersona().setAnios(rset.getString("ANIOS"));
			force.getPersona().setMeses(rset.getString("MESES"));
			force.getPersona().setSexo(rset.getString("SEXO"));
			force.getPersona().setRfc(rset.getString("RFC"));
			force.getPersona().setHomoclave(rset.getString("HOMOCLAVE"));
			force.getPersona().setEmail(rset.getString("EMAIL"));
			force.getPersona().setFechaCreacion(rset.getString("FECHA_CREACION"));
			force.getPersona().setFechaModificacion(rset.getString("FECHA_MODIFICACION"));
			force.setStatus(rset.getString("STATUS"));
			force.getPerfil().setSitioWeb(rset.getString("SITIO_WEB"));
			force.getPerfil().setDominio(rset.getString("DOMINIO"));	
		}
		
		if ( force.getIdCliente().equals("") )
			throw new SQLException("Cliente No Registrado en EAI Force");
		else if ( force.getStatus().equals("2") )
			throw new SQLException("Cliente Suspendido");
		else if ( force.getStatus().equals("3") )
			throw new SQLException("Cliente en Mantenimiento");
		else if ( force.getStatus().equals("4") )
			throw new SQLException("Cliente Inactivo");
	}
	
	private void validateUser(String usuario, String password) throws SQLException {
		String query = 	"SELECT A.*\n " +
						"FROM EAI_ADM_USUARIOS A\n" +
						" WHERE A.ID_CLIENTE = " + force.getIdCliente() + "\n" +
						" AND USUARIO = '" + usuario + "'";
		
		//System.out.println(query);
		rset = upsert.query(query);
		if (rset.next()) {
			force.setUsuario("0", new Persona());
			force.getUsuario("0").setIdPersona(rset.getString("ID_USUARIO"));
			force.getUsuario("0").setNombre(rset.getString("NOMBRE"));
			force.getUsuario("0").setSegundoNombre(rset.getString("SEGUNDO_NOMBRE"));
			force.getUsuario("0").setApellidoPaterno(rset.getString("APELLIDO_PATERNO"));
			force.getUsuario("0").setApellidoMaterno(rset.getString("APELLIDO_MATERNO"));
			force.getUsuario("0").setNombreCompleto(force.getUsuario("0").getNombre() + " " + force.getUsuario("0").getApellidoPaterno());
			force.getUsuario("0").setFechaCreacion(rset.getString("FECHA_CREACION"));
			force.getUsuario("0").setFechaIngreso(validate(rset.getString("FECHA_INGRESO")));
			force.getUsuario("0").setUsuario(rset.getString("USUARIO"));
			force.getUsuario("0").setContrasena(rset.getString("CONTRASENA"));
			force.getUsuario("0").setProfile(rset.getString("PERFIL"));
			force.getUsuario("0").setEmail(rset.getString("EMAIL"));
		}
		
		if ( force.getUsuario("0").getIdPersona().equals("") )
			throw new SQLException("Usuario No Registrado en EAI Force");
		else if ( !force.getUsuario("0").getContrasena().equals(encryptPassword(password)) )
			throw new SQLException("Usuario y/o Contrase&ntilde;a Invalidas");		
	}
		
	private void loadPreferences() throws SQLException {
		String query = 	"SELECT A.* " +
						"FROM EAI_ADM_PREFERENCIAS A" +
						" WHERE A.ID_USUARIO = '" + force.getUsuario("0").getIdPersona() + "'";
		
		//System.out.println(query);
		rset = upsert.query(query);
		if (rset.next()) {
			force.getUsuario("0").setFiltros(rset.getString("FILTROS"));
			force.getUsuario("0").setWidgets(rset.getString("WIDGETS"));
			force.getUsuario("0").setSelecciones(rset.getString("SELECCIONES"));
		}
	}
		
	private void updateUser() throws SQLException {
		EaiAdmUsuarios eau = new EaiAdmUsuarios(force);
		upsert.execute(eau.updateUser());		
		upsert.execute(eau.insertLog());
	}
	
	private String encryptPassword(String password) {
		try {
			return new SecurityUtils().encryptAes(password);
		} catch (Exception e) {
			return "";
		}
	}
	
	private String[] decodeQueryString(String x) {
		try{
			String valores[] = new SecurityUtils().decode64(x).split("&");
			return new String[] {
				valores[0].split("=")[1],
				valores[1].split("=")[1]
			};
		} catch (Exception e) {
			return new String[] { "", "" };
		}
	}
	
	private String validate( String value ) {
		return ( value != null ) ? value : "";
	}

	public Force getForce() {
		return force;
	}
}