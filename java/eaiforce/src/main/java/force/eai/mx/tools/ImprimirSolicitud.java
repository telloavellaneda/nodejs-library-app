package force.eai.mx.tools;

import java.io.*;
import java.sql.*;
import com.aspose.pdf.*;

@SuppressWarnings("unused")
public class ImprimirSolicitud {

	public String licencia = "";
	public String directorio = "";
	public String archivo = "";
	
	public String dbUsername = "";
	public String dbPassword = "";
	public String dbURL = "";
	public String dbDriver = "";
	
	String id_persona = "";
	String tipo = "";
	String apellido_paterno = "";
	String apellido_materno = "";
	String primer_nombre = "";
	String segundo_nombre = "";
	String fecha_nacimiento = "";
	String edad = "";
	String sexo = "";
	String pais_nacimiento = "";
	String lugar_nacimiento = "";
	String nacionalidad = "";
	String rfc = "";
	String homoclave = "";
	String curp = "";
	String no_imss = "";
	String nivel_academico = "";
	String estado_civil = "";
	String regimen = "";
	String fecha_matrimonio = "";
	String identificacion = "";
	String numero_identificacion = "";
	String email = "";
	
	public String imprimirBanamex( String folio_rosbol ) {
		String outputHTML = "";
		
		String queryPersona = "SELECT B.* " +
				", TO_CHAR(FECHA_NACIMIENTO,'DD/MM/YYYY') FECHA_NACIM_FORMAT " +
				", TO_CHAR(FECHA_MATRIMONIO,'DD/MM/YYYY') FECHA_MATRI_FORMAT " +
				"FROM EAIROSBOL.EAI_REL_CLIENTES A " +
				",EAIROSBOL.EAI_ENT_PERSONAS B " +
				"WHERE FOLIO_ROSBOL = " + folio_rosbol +
				"AND A.ID_PERSONA = B.ID";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			Class.forName( "oracle.jdbc.driver.OracleDriver" );
			conn = DriverManager.getConnection("jdbc:oracle:thin:@centos.eai.mx:1521:EAI", "eairosbol", "admin");
			stmt = conn.createStatement();

		} catch(Exception ex) {
			outputHTML = "ROSBOLnet Exception: " + ex.toString();
			System.out.println( outputHTML );
		}
				
		TextBoxField tb = null;
                RadioButtonField rb = null;
                CheckboxField cb = null;
		
		// Create license object
		License license = new License();
		// Load the license file into FileStream object
		try {			
			license.setLicense( new FileInputStream( this.licencia ) );
		} catch (Exception e2) {
		    // TODO Auto-generated catch block
		    e2.printStackTrace();
		}
    	
            // The path to the documents directory.
            String dataDir = this.directorio;
            String archivo = this.archivo;

            //open document
            Document pdfDocument = new Document(dataDir + archivo);
            //get particular page
            Page pdfPage = pdfDocument.getPages().get_Item(1);

            // PERSONA
            try {
                    rset = stmt.executeQuery(queryPersona);
                    if ( rset.next() ) {
                            id_persona = rset.getString("ID");
                            tipo = cleanString( rset.getString("TIPO") );
                            apellido_paterno = cleanString( rset.getString("APELLIDO_PATERNO") );
                            apellido_materno = cleanString( rset.getString("APELLIDO_MATERNO") );
                            primer_nombre = cleanString( rset.getString("PRIMER_NOMBRE") );
                            segundo_nombre = cleanString( rset.getString("SEGUNDO_NOMBRE") );
                            fecha_nacimiento = cleanString( rset.getString("FECHA_NACIM_FORMAT") );
                            edad = cleanString( rset.getString("EDAD") );
                            sexo = cleanString(  rset.getString("SEXO") );
                            email = cleanString( rset.getString("EMAIL") );

                            pais_nacimiento = cleanString( rset.getString("PAIS_NACIMIENTO") );
                            lugar_nacimiento = cleanString( rset.getString("LUGAR_NACIMIENTO") );
                            nacionalidad = cleanString( rset.getString("NACIONALIDAD") );
                            rfc = cleanString( rset.getString("RFC") );
                            homoclave = cleanString( rset.getString("HOMOCLAVE") );
                            curp = cleanString( rset.getString("CURP") );
                            no_imss = cleanString( rset.getString("NO_IMSS") );
                            nivel_academico = cleanString( rset.getString("NIVEL_ACADEMICO") );
                            estado_civil = cleanString( rset.getString("ESTADO_CIVIL") );
                            regimen = cleanString( rset.getString("REGIMEN") );
                            fecha_matrimonio = cleanString( rset.getString("FECHA_MATRI_FORMAT") );
                            identificacion = cleanString( rset.getString("IDENTIFICACION") );
                            numero_identificacion = cleanString( rset.getString("NUMERO_IDENTIFICACION") );
                    }
            } catch (Exception e) {

            }

            tb = (TextBoxField)pdfDocument.getForm().get_Item("APELLIDO_PATERNO_1");
            tb.setValue( apellido_paterno );
            tb.setReadOnly(true);		
            tb = (TextBoxField)pdfDocument.getForm().get_Item("APELLIDO_MATERNO_1");
            tb.setValue( apellido_materno );
            tb.setReadOnly(true);		
            tb = (TextBoxField)pdfDocument.getForm().get_Item("NOMBRE_1");
            tb.setValue( primer_nombre + " " + segundo_nombre );
            tb.setReadOnly(true);
            tb = (TextBoxField)pdfDocument.getForm().get_Item("Pais_Nac_1");
            tb.setValue( pais_nacimiento );
            tb.setReadOnly(true);
            tb = (TextBoxField)pdfDocument.getForm().get_Item("Ent_Fed_1");
            tb.setValue( lugar_nacimiento );
            tb.setReadOnly(true);

            tb = (TextBoxField)pdfDocument.getForm().get_Item("Fecha_Nac_1");
            tb.setValue( fecha_nacimiento );
            tb.setReadOnly(true);
            tb = (TextBoxField)pdfDocument.getForm().get_Item("Edad_1");
            tb.setValue( edad );
            tb.setReadOnly(true);
            tb = (TextBoxField)pdfDocument.getForm().get_Item("RFC_1");
            tb.setValue( rfc + "" + homoclave );
            tb.setReadOnly(true);
            tb = (TextBoxField)pdfDocument.getForm().get_Item("CURP_1");
            tb.setValue( curp );
            tb.setReadOnly(true);
            tb = (TextBoxField)pdfDocument.getForm().get_Item("SS_SAR_1");
            tb.setValue( no_imss );
            tb.setReadOnly(true);

            tb = (TextBoxField)pdfDocument.getForm().get_Item("Correo_A1");
            tb.setValue( email.split("@")[0] );
            tb.setReadOnly(true);
            tb = (TextBoxField)pdfDocument.getForm().get_Item("Correo_B1");
            tb.setValue( email.split("@")[1] );
            tb.setReadOnly(true);

            rb = (RadioButtonField) pdfDocument.getForm().get_Item("GENERO_1");
            if ( sexo.equals("M") )
                    rb.getOptions().get(1).setSelected(true);
            else
                    rb.getOptions().get(2).setSelected(true);
            rb.setReadOnly(true);

            rb = (RadioButtonField) pdfDocument.getForm().get_Item("ESCOLARIDAD_1");
            if ( nivel_academico.equals("1") )
                    rb.getOptions().get(1).setSelected(true);
            else if ( nivel_academico.equals("2") )
                    rb.getOptions().get(2).setSelected(true);
            else if ( nivel_academico.equals("3") )
                    rb.getOptions().get(3).setSelected(true);
            else if ( nivel_academico.equals("4") )
                    rb.getOptions().get(4).setSelected(true);
            else if ( nivel_academico.equals("5") )
                    rb.getOptions().get(5).setSelected(true);
            else if ( nivel_academico.equals("6") )
                    rb.getOptions().get(6).setSelected(true);
            rb.setReadOnly(true);

            tb = (TextBoxField)pdfDocument.getForm().get_Item("ID_1");
            tb.setValue( identificacion );
            tb.setReadOnly(true);
            tb = (TextBoxField)pdfDocument.getForm().get_Item("Num_ID_1");
            tb.setValue( numero_identificacion );
            tb.setReadOnly(true);

            tb = (TextBoxField)pdfDocument.getForm().get_Item("Tel_C_1");
            tb.setValue( "" + "" );
            tb.setReadOnly(true);

                    /*
            for ( int i = 0; i < pdfDocument.getForm().size(); i++){
                    System.out.println( pdfDocument.getForm().get_Item(i+1).getFullName() );

                    tb = null;
                    rb = null;
                    cb = null;
                    String abc = pdfDocument.getForm().get_Item(i+1).getClass().toString().substring( 6 );

                    //try {
                    if ( abc.equals("com.aspose.pdf.TextBoxField") ) {
                            tb = (TextBoxField)pdfDocument.getForm().get_Item(i+1);
                            tb.setValue(tb.getFullName());
                            tb.setReadOnly(true);
                    } else if ( abc.equals("com.aspose.pdf.RadioButtonField") ) {
                            rb = (RadioButtonField) pdfDocument.getForm().get_Item(i+1);
                            rb.setValue("true");
                            rb.setReadOnly(true);
                    } else {
                            cb = (CheckboxField) pdfDocument.getForm().get_Item(i+1);
                            cb.setChecked(true);
                            cb.setReadOnly(true);
                    }

                    //} catch (Exception e) {
                            //System.out.println("Error " + textBoxField.getFullName() );
                    //}
            }*/


                    // save updated PDF file
            pdfDocument.save(dataDir + folio_rosbol + "_" + archivo);

            //Print message
            //System.out.println("Text is successfully added. Check output file.");

            return dataDir + folio_rosbol + "_" + archivo;
	}
	
	private String cleanString( String value ) {
		return ( value != null ) ? value : "";
	}
	
	
    public static void main(String[] args) throws Exception {

    	ImprimirSolicitud objeto = new ImprimirSolicitud();
    	
    	objeto.setLicencia("/Users/sateav/Downloads/Aspose.Pdf.lic");
    	objeto.setDirectorio("/Users/sateav/Downloads/");
    	objeto.setArchivo("sol_Banamex.pdf");
    	objeto.setDbDriver("oracle.jdbc.driver.OracleDriver");
    	objeto.setDbURL("jdbc:oracle:thin:@centos.eai.mx:1521:EAI");
    	objeto.setDbUsername("eairosbol");
    	objeto.setDbPassword("admin");
    	System.out.println( objeto.imprimirBanamex( "1127" ) );

        /*
        //create text fragment
        TextFragment textFragment = null;
        		
        textFragment = new TextFragment("TELLO");
        textFragment.setPosition(new Position(18, 525));
        
        //set text properties
        textFragment.getTextState().setFont( FontRepository.findFont("Verdana"));
        textFragment.getTextState().setFontSize(11);
        
        textFragment.getTextState().setForegroundColor(com.aspose.pdf.Color.getBlack());

        // create TextBuilder object
        TextBuilder textBuilder = new TextBuilder(pdfPage);
        // append the text fragment to the PDF page
        //textBuilder.appendText(textFragment);

        textFragment = new TextFragment("AVELLANEDA");
        textFragment.setPosition(new Position(138, 525));
        
        //textBuilder.appendText(textFragment);

        
        textFragment = new TextFragment("08/11/1980");
        textFragment.setPosition(new Position(18, 498));
        
        //textBuilder.appendText(textFragment);

        textFragment = new TextFragment("34 a 11 m");
        textFragment.setPosition(new Position(88, 498));
        
        //textBuilder.appendText(textFragment);
         */
    }

	public void setLicencia(String licencia) {
		this.licencia = licencia;
	}

	public void setDirectorio(String directorio) {
		this.directorio = directorio;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}

	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
}
