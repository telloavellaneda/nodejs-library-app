package force.eai.mx.orchestration;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import force.eai.mx.util.Force;
import force.eai.mx.util.Message;
import force.eai.mx.util.Catalogo;
import force.eai.mx.database.Upsert;
import force.eai.mx.dao.QryLoadProfile;
import force.eai.mx.security.ProfileSettings;

public class LoadProfile {
	
	private Upsert upsert;
	private QryLoadProfile queries;
	private Message message = new Message();
	
	private LinkedHashMap<String,LinkedHashMap<String,Catalogo>> profile = new LinkedHashMap<String,LinkedHashMap<String,Catalogo>>();
	
	final private String[][] meses = new String[][] {
		{ "01", "ENERO" },
		{ "02", "FEBRERO" },
		{ "03", "MARZO" },
		{ "04", "ABRIL" },
		{ "05", "MAYO" },
		{ "06", "JUNIO" },
		{ "07", "JULIO" },
		{ "08", "AGOSTO" },
		{ "09", "SEPTIEMBRE" },
		{ "10", "OCTUBRE" },
		{ "11", "NOVIEMBRE" },
		{ "12", "DICIEMBRE" }
	};
	
	final private String[] decreto = new String[] { 
		"APROBADO", 
		"RECHAZADO", 
		"CONDICIONADO" 
	};
		
	public LoadProfile(Connection connection) throws SQLException {
		this.upsert = new Upsert(connection);
	}
	
	public void close() throws SQLException {
		upsert.close();
	}

	public void load(Force force) throws SQLException {
		ResultSet rset;
		String query = "";
		queries = new QryLoadProfile(force);
		LinkedHashMap<String,Catalogo> temporal = null;
		
		// Usuarios
		profile.put("usuarios", new LinkedHashMap<String,Catalogo>());
		profile.put("usuariosAdmin", new LinkedHashMap<String,Catalogo>());
		profile.put("usuariosDisponibles", new LinkedHashMap<String,Catalogo>());
		profile.put("usuariosResponsables", new LinkedHashMap<String,Catalogo>());
		// Fechas
		profile.put("expediente", new LinkedHashMap<String,Catalogo>());
		profile.put("autorizacion", new LinkedHashMap<String,Catalogo>());
		profile.put("firma", new LinkedHashMap<String,Catalogo>());
		// Fases y Status
		profile.put("fases", new LinkedHashMap<String,Catalogo>());
		profile.put("status", new LinkedHashMap<String,Catalogo>());
		// Parametros
		profile.put("parametros", new LinkedHashMap<String,Catalogo>());
		
		// USUARIOS CON CLIENTES EN LA BD
		query = queries.getUsuariosQuery("ID_USUARIO", "AND PERFIL NOT IN (2,11,5,13)");
		rset = upsert.query(query);
		while ( rset.next() )
			profile.get("usuarios").put(rset.getString(1), new Catalogo(rset.getString(1), rset.getString(2), rset.getString(5)));

		// USUARIOS RESPONSABLES CON CLIENTES EN LA BD
		query = queries.getUsuariosQuery("ID_USUARIO_RESPONSABLE", "AND PERFIL IN (5,13,99)");
		
		rset = upsert.query(query);
		while ( rset.next() )
			profile.get("usuariosResponsables").put(rset.getString(1), new Catalogo(rset.getString(1), rset.getString(2), rset.getString(5)));
		
		query = queries.getListaUsuariosQuery();
		rset = upsert.query(query);
		while ( rset.next() ) {
			String id = rset.getString("ID_USUARIO");
			String fullName = rset.getString("USUARIO");
			String perfil = rset.getString("PERFIL");
			String status = rset.getString("STATUS");
			
			Catalogo cat = new Catalogo(id, fullName, status);
			ProfileSettings ps = new ProfileSettings(perfil);
			
			if ( ps.isAdmin() )
				profile.get("usuariosAdmin").put(id, cat);
			
			if ( !ps.isReadOnly() && !ps.isOperaciones() && status.equals("1") ) 
				profile.get("usuariosDisponibles").put(id, cat);
		}
		
		// FECHA_EXPEDIENTE
		rset = upsert.query(queries.getYearsQuery("FECHA_EXPEDIENTE"));
		while ( rset.next() ) {
			profile.get("expediente").put(rset.getString(1), new Catalogo());
			profile.get("expediente").get(rset.getString(1)).setId(rset.getString(1));
			profile.get("expediente").get(rset.getString(1)).setNombre(rset.getString(1));
		}

		// FECHA_AUTORIZACION
		rset = upsert.query(queries.getYearsQuery("FECHA_AUTORIZACION"));
		while ( rset.next() ) {
			profile.get("autorizacion").put(rset.getString(1), new Catalogo());
			profile.get("autorizacion").get(rset.getString(1)).setId(rset.getString(1));
			profile.get("autorizacion").get(rset.getString(1)).setNombre(rset.getString(1));
		}

		// FECHA_FIRMA
		rset = upsert.query(queries.getYearsQuery("FECHA_FIRMA"));
		while ( rset.next() ) {
			profile.get("firma").put(rset.getString(1), new Catalogo());
			profile.get("firma").get(rset.getString(1)).setId(rset.getString(1));
			profile.get("firma").get(rset.getString(1)).setNombre(rset.getString(1));
		}

		// FASES Y STATUS
		rset = upsert.query(queries.getFasesStatusQuery());
		while ( rset.next() ) {
			// Fases		
			profile.get("fases").put(rset.getString(3), new Catalogo());
			profile.get("fases").get(rset.getString(3)).setId(rset.getString(3));
			profile.get("fases").get(rset.getString(3)).setNombre(rset.getString(4));

			profile.get("status").put(rset.getString(1), new Catalogo());
			profile.get("status").get(rset.getString(1)).setId(rset.getString(1));
			profile.get("status").get(rset.getString(1)).setNombre(rset.getString(2));
			profile.get("status").get(rset.getString(1)).setCampo("idFase", rset.getString(3));
			profile.get("status").get(rset.getString(1)).setCampo("fase", rset.getString(4));
									
			// Status x Fases
			if ( profile.get("fase" + rset.getString(3)) == null )
				profile.put("fase" + rset.getString(3), new LinkedHashMap<String,Catalogo>());
			profile.get("fase" + rset.getString(3)).put(rset.getString(1), new Catalogo());
			profile.get("fase" + rset.getString(3)).get(rset.getString(1)).setId(rset.getString(1));
			profile.get("fase" + rset.getString(3)).get(rset.getString(1)).setNombre(rset.getString(2));
		}
		
		// BANCOS
		temporal = new LinkedHashMap<String,Catalogo>();
		rset = upsert.query(queries.getBancosQuery());
		while ( rset.next() ) {
			temporal.put(rset.getString(1), new Catalogo());
			temporal.get(rset.getString(1)).setId(rset.getString(1));
			temporal.get(rset.getString(1)).setNombre(rset.getString(2));

			if ( rset.getString(5) != null && rset.getString(5).equals("1") ) {
				if ( profile.get("bancosForm") == null )
					profile.put("bancosForm", new LinkedHashMap<String,Catalogo>());
				profile.get("bancosForm").put(rset.getString(1), new Catalogo());
				profile.get("bancosForm").get(rset.getString(1)).setId(rset.getString(1));
				profile.get("bancosForm").get(rset.getString(1)).setNombre(rset.getString(2));
			
				// Productos x Bancos
				if ( profile.get("banco" + rset.getString(1)) == null )
					profile.put("banco" + rset.getString(1), new LinkedHashMap<String,Catalogo>());
				profile.get("banco" + rset.getString(1)).put(rset.getString(3), new Catalogo());
				profile.get("banco" + rset.getString(1)).get(rset.getString(3)).setId(rset.getString(3));
				profile.get("banco" + rset.getString(1)).get(rset.getString(3)).setNombre(rset.getString(4));
			}			
		}
		setFilterValues("bancos",temporal);

		// DESTINOS
		rset = upsert.query(queries.getDestinosQuery());
		while ( rset.next() ) {
			if ( profile.get("destinos") == null )
				profile.put("destinos", new LinkedHashMap<String,Catalogo>());

			profile.get("destinos").put(rset.getString(1), new Catalogo());
			profile.get("destinos").get(rset.getString(1)).setId(rset.getString(1));
			profile.get("destinos").get(rset.getString(1)).setNombre(rset.getString(2));
		}
		
		// DECRETO
		temporal = new LinkedHashMap<String,Catalogo>();
		for (int i = 0; i < decreto.length; i ++ ) {
			temporal.put(decreto[i], new Catalogo());
			temporal.get(decreto[i]).setId(decreto[i]);
			temporal.get(decreto[i]).setNombre(decreto[i]);
		}
		setFilterValues("decreto",temporal);
		
		// PARAMETROS
		rset = upsert.query(queries.getParametrosQuery());
		while ( rset.next() ) {
			profile.get("parametros").put(rset.getString(1), new Catalogo());
			profile.get("parametros").get(rset.getString(1)).setId(rset.getString(1));
			profile.get("parametros").get(rset.getString(1)).setNombre(rset.getString(2));
			profile.get("parametros").get(rset.getString(1)).setDescripcion(rset.getString(3));
		}
		
		// MONTHS
		temporal = new LinkedHashMap<String,Catalogo>();
		for (int i = 0; i < meses.length; i ++ ) {
			temporal.put(meses[i][0], new Catalogo());
			temporal.get(meses[i][0]).setId(meses[i][0]);
			temporal.get(meses[i][0]).setNombre(meses[i][1]);
		}
		setFilterValues("months",temporal);
		
		rset.close();
	}
		
	private void setFilterValues(String key, LinkedHashMap<String,Catalogo> values) {
		if (values != null && values.size() > 0)
			profile.put(key, values);
	}
			
	public LinkedHashMap<String, LinkedHashMap<String, Catalogo>> getProfile() {
		return profile;
	}
	public Message getMessage() {
		return message;
	}
}