package force.eai.mx.tools;

public class DatabaseTools {

	
	public String update(String key, String value) {
		if ( !validate(value).equals("") )
			return "," + key + " = " + "'" + value + "'" + "\n";
		else
			return "";
	}
	
	private String validate(String value) {
		return (value != null) ? value : "";
	}
}
