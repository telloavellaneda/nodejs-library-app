package force.eai.mx.tools;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import force.eai.mx.util.Seguimiento;

public class InsertComments {

	public void run() {

		String csvFile = "/Users/sateav/EAI/Customers/ROSBOL/development/excel/09-comentarios-new.csv";
		BufferedReader br = null;
		String line = "";

		try {
			String[] mensajes = null;
			
			br = new BufferedReader( new InputStreamReader(new FileInputStream(csvFile),"ISO-8859-1") );
			//br = new BufferedReader(new FileReader(csvFile));
			
			Class.forName( "oracle.jdbc.driver.OracleDriver" );
			Connection conn = DriverManager.getConnection( "jdbc:oracle:thin:@centos.eai.mx:1521:EAI", "eairosbol", "admin" );	
			Statement stmt = conn.createStatement();
			
			while ( ( line = br.readLine() ) != null ) {

				String folioRosbol = line.substring(0,5).trim();
				String linea = line.substring( 5 ).trim();
								
				try {
					mensajes = linea.split("//");
				} catch ( Exception e ) {
					mensajes = new String[]{"25/09/15 ROSBOLnet: Error en el mensaje de seguimiento "};
				}
				
				//for (int i = 0; i<1; i++) {
				for (int i = 0; i<mensajes.length; i++) {
					java.util.Date utilDate = null;
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
					String tempExtract = mensajes[i].trim().replace("\"", "");
					String mensaje = "";
				    
					try {
						utilDate = formatter.parse( tempExtract.split(" ")[0] );
					} catch ( Exception e ) { 
						utilDate = new Date();
						mensaje = "ROSBOLnet: Verifique valor de fecha en su base de datos [ Valor extraído = \"" + tempExtract.split(" ")[0] + "\" ]";
					}
					
					if ( mensaje.equals("") ) {
						try {
							mensaje = tempExtract.substring(8).trim();
						} catch (Exception e) {
							mensaje = "ROSBOLnet: Sin comentarios en base de Datos";
						}	
					}
					
					String query = "Insert into EAIROSBOL.EAI_REL_SEGUIMIENTO ( " +
					" FOLIO_ROSBOL" +
					" , ID_USUARIO" +
					" , MENSAJE" +
					" , FECHA_CREACION" +
					" ) VALUES ( " +
					folioRosbol +
					", 1" +
					", '" + mensaje + "'" +
					", to_date('"+ formatter.format(utilDate) +"','DD/MM/YY') " +
					")";
					
					System.out.println( query );
					
					stmt.executeUpdate(query);

				}
			}
			
			stmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	  }
	
	public LinkedHashMap<String, Seguimiento> loadObject( String folioRosbol, String value ) {

		String[] message = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		LinkedHashMap<String, Seguimiento> seguimiento = new LinkedHashMap<String, Seguimiento>();
		
		try {
			message = value.split("//");
		} catch ( Exception e ) {
			message = new String[0];
		}
		
		for (int i = 0; i < message.length; i++) {
			String mensaje = "";
			Seguimiento objeto = new Seguimiento();
//			objeto.setFolioRosbol(folioRosbol);
			String tempExtract = message[i].trim().replace("\"", "");
		    
			try {
				objeto.setFechaCreacion( formatter.format(formatter.parse(tempExtract.split(" ")[0])) );
			} catch ( Exception e ) { 
				objeto.setFechaCreacion( formatter.format(new Date()) );
			}
			
			if ( mensaje.equals("") ) {
				try {
					objeto.setMensaje( tempExtract.substring(8).trim() );
				} catch (Exception e) { }
			}
			
			seguimiento.put("" + i, objeto);
		}
		
		return seguimiento;
	}
	
	public static void main(String a[]){
		InsertComments obj = new InsertComments();
		try {
			obj.run();
		} catch (Exception e) {
			
		}
	}
}
