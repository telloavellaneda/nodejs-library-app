package force.eai.mx.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.DriverManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Connect {
	private static final String FILE_NAME = "eaiforce.properties";
	
	private String environment;
	private Connection connection = null;

	public Connect(String environment) throws SQLException {
		this.environment = environment;
		connect(false);
	}

	public Connect(String environment, boolean direct) throws SQLException {
		this.environment = environment;
		connect(true);
	}	

	public void close() {
		try {
			if ( connection != null )
				connection.close();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			// Nothing to do really
		}
	}
	
	private void connect(boolean direct) throws SQLException {
		Properties properties;
		try {
			properties = this.getDatabaseValues();
		} catch (IOException ioException) {
			ioException.printStackTrace();
			throw new SQLException("Error en la lectura de configuración", "-1", ioException);
		}
		
		String dbDriver = properties.getProperty("db.driver");

		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException cnfException) {
			cnfException.printStackTrace();
			throw new SQLException("Error en la carga del driver de Base de Datos", "-1", cnfException);
		}
		
		try {
			if ( direct )
				direct(properties);
			else
				datasource();
			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			throw new SQLException("Sin conexión con la Base de Datos", sqlException.getSQLState(), sqlException);
		} catch (NamingException nException) {
			nException.printStackTrace();
			throw new SQLException("Sin conexión con la Base de Datos", "-1", nException);			
		}
	}
	
	private void direct(Properties properties) throws SQLException, NamingException {
		String environment = "db." + this.environment + ".";
		String dbURL = properties.getProperty(environment + "url");
		String dbUsername = properties.getProperty(environment + "username");
		String dbPassword = properties.getProperty(environment + "password");
		connection = DriverManager.getConnection( dbURL , dbUsername , dbPassword );
	}

	private void datasource() throws SQLException, NamingException {
		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:comp/env");
		DataSource ds = (DataSource) envContext.lookup("jdbc/eaiforce");
		connection = ds.getConnection();
	}
			
	private Properties getDatabaseValues() throws IOException {
		Properties properties = new Properties();		
		InputStream input = Connect.class.getClassLoader().getResourceAsStream(FILE_NAME);		
		//InputStream input = this.getClass().getResourceAsStream(FILE_NAME);
		properties.load( input );
		return properties;
	}
		
	public Connection getConnection() {
		return connection;
	}
}