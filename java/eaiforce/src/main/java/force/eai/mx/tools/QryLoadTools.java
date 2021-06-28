package force.eai.mx.tools;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class QryLoadTools {
	public String RESOURCES = "";
	
	public QryLoadTools(String resources) {
		this.RESOURCES = resources;
	}
	
	public String readQuery (String file) {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(RESOURCES + "/" + file);
		try {
			String query = IOUtils.toString(inputStream, "UTF-8");
			inputStream.close();
			return query;
		} catch (IOException ioException) {
			ioException.printStackTrace();
			return "";
		}		
	}
	
	public String build(String sql, String value) {
		 return ( !value.equals("") ) ? "\n\t\t" + sql + " = '" + value + "'" : "";
	}

	public String buildLike(String sql, String value) {
		 return ( !value.equals("") ) ? "\n\t\t" + sql : "";
	}

	public String clean(String value) {
		return value.replaceAll("(?m)^\\s*$[\n\r]{1,}", "");
	}
}
