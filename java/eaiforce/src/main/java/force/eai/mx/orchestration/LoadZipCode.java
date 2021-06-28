package force.eai.mx.orchestration;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

import force.eai.mx.util.ZipCode;
import force.eai.mx.database.Upsert;
import force.eai.mx.database.Connect;
import force.eai.mx.form.FormElements;

public class LoadZipCode {
	private Upsert upsert;

	private String zipCode = "";
	private String preffix = "";
	private boolean required =  false;
	private ZipCode zipCodeObject = new ZipCode();
	private LinkedHashMap<String,ZipCode> codigosPostales = new LinkedHashMap<String,ZipCode>();
	
	final private String query = "SELECT * " +
			"FROM EAI_CAT_SEPOMEX " +
			"WHERE CODIGO = ? ";
		
	public LoadZipCode(Connection connection) throws SQLException {
		this.upsert = new Upsert(connection);
	}
	
	public void close() throws SQLException {
		this.upsert.close();
	}
	
	private void searchCode() throws SQLException {
		// BANCOS
		ResultSet rset = upsert.query(query, zipCode);		
		int contador = 0;
		while ( rset.next() ) {
			codigosPostales.put(contador + "", new ZipCode());
			codigosPostales.get(contador + "").setCodigo(validateString(rset.getString("CODIGO")));
			codigosPostales.get(contador + "").setColonia(validateString(rset.getString("COLONIA")));
			codigosPostales.get(contador + "").setDelegacion(validateString(rset.getString("DELEGACION")));
			codigosPostales.get(contador + "").setCiudad(validateString(rset.getString("CIUDAD")));
			codigosPostales.get(contador + "").setEstado(validateString(rset.getString("ESTADO")));
			codigosPostales.get(contador + "").setIndex(validateString(rset.getString("SELECTED_INDEX")));
			codigosPostales.get(contador + "").setStatus(validateString(rset.getString("STATUS")));
			
			contador ++;
		}		
		rset.close();
	}
	
	private String validateString( String value ) {
		return ( value != null ) ? value.trim().toUpperCase() : "";
	}
	
	private void createFormElement() {
		String tmpDelegacion = "";
		String tmpCiudad = "";
		String tmpEstado = "";
		FormElements element = new FormElements();
	
		element.setPreffix(preffix);
		element.initControl();
		element.getControl().setInitValue(false);
		element.getControl().setId("colonia");
		element.getControl().setRequired(required);

		if ( codigosPostales.size() > 1) {
			int contador = 0;
			for (Iterator<ZipCode> iterator = codigosPostales.values().iterator(); iterator.hasNext();) {
				ZipCode zipcode = iterator.next();
				element.getControl().getOption(contador + "").setId(zipcode.getColonia());
				element.getControl().getOption(contador + "").setNombre(zipcode.getColonia());
				contador ++;
			}
			zipCodeObject.setColonia(element.createSelectElement());
		} else {
			element.getControl().setValue( (codigosPostales.get("0") != null) ? codigosPostales.get("0").getColonia() : "");
			zipCodeObject.setColonia(element.createInputElement());
		}	
		
		if (codigosPostales.get("0") != null) {
			tmpDelegacion = codigosPostales.get("0").getDelegacion();
			tmpCiudad = codigosPostales.get("0").getCiudad();
			tmpEstado = codigosPostales.get("0").getIndex();
		}
		
		element.initControl();
		element.getControl().setId("delegacion");
		element.getControl().setValue(tmpDelegacion);
		element.getControl().setRequired(required);
		zipCodeObject.setDelegacion(element.createInputElement());
		
		element.initControl();
		element.getControl().setId("ciudad");
		element.getControl().setValue(tmpCiudad);
		element.getControl().setRequired(required);
		zipCodeObject.setCiudad(element.createInputElement());
		
		zipCodeObject.setEstado(tmpEstado);
	}
		
	public void loadZipCode() {
		try {
			searchCode();
			createFormElement();
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
		
	public static void main(String a[]) throws Exception{
		Connect connect =  new Connect("dev");
		LoadZipCode loadZipcode = new LoadZipCode(connect.getConnection());
		
		loadZipcode.setPreffix("abc_");
		loadZipcode.setZipCode("37216");
		//loadZipcode.loadZipCode();
		//System.out.println(loadZipcode.getZipCodeObject().getColonia() );
		connect.close();
	}
	
	public ZipCode getZipCodeObject() {
		return zipCodeObject;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public void setPreffix(String preffix) {
		this.preffix = preffix;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
}