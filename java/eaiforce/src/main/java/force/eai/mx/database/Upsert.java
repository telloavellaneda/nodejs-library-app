package force.eai.mx.database;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class Upsert {
	private String query = "";
	private String insert = "";
	private String update = "";
	private Statement stmt = null;
	private Connection connection = null;
	private PreparedStatement pstmt = null;

	public Upsert(Connection connection) throws SQLException {
		this.connection = connection;
		this.stmt = this.connection.createStatement();
	}
	
	public void close() {
		try {
			if ( this.stmt != null )
				this.stmt.close();			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public void closePreparedStatement() {
		try {
			if ( pstmt != null)
				pstmt.close();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public void upsert() throws SQLException {
		try {
			stmt.executeUpdate(insert);
			query = insert;
			insert = "";
			
		} catch(SQLException insertException) {
			if ( insertException.getErrorCode() == 1 )
				try {
					stmt.executeUpdate(update);
					query = update;
					update = "";
					
				} catch (SQLException updateException) {
					query = update;
					throw updateException;
				}
			else {
				query = insert;
				throw insertException;
			}
		}
	}

	public int execute(String query) throws SQLException {
		return stmt.executeUpdate(query);
	}
	
	public ResultSet query(String query) throws SQLException {
		return stmt.executeQuery(query);
	}
	
	public ResultSet query(String query, String parameter) throws SQLException {
		pstmt = connection.prepareStatement(query);
		pstmt.setString(1,parameter);
		return pstmt.executeQuery();
	}
	
	public String queryValue(String query) throws SQLException {
		this.query =  query;
		
		ResultSet rset = stmt.executeQuery( query );
		String tmpValue = ( rset.next() ) ? rset.getString(1) : "";
		rset.close();
		
		return tmpValue;
	}	
	
	public String getQuery() {
		return query;
	}
	public Connection getConnection() {
		return connection;
	}
	public void setInsert(String insert) {
		this.insert = insert;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
}