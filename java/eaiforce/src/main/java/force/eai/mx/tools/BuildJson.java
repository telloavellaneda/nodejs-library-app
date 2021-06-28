package force.eai.mx.tools;

import force.eai.mx.util.Message;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class BuildJson {

	public String getValue(String json, String key) {
		JSONParser parser = new JSONParser();
		try {
			return String.class.cast( ((JSONObject)parser.parse(json)).get(key) );
		} catch (Exception e) {
			return "";
		}
	}
	
	@SuppressWarnings("unchecked")
	public String write(Message message) {
		JSONObject json = new JSONObject();
		json.put("code", message.getCode());
		json.put("message", message.getMessage());
		json.put("description", message.getDescription());
		json.put("exception", message.getException());
		
		return json.toJSONString();
	}
	
	public static void main(String a[]) {
		Message message = new Message();
		message.setCode("-1");
		message.setMessage("bla bla bla");
		message.setDescription("ok ok");
		
		String result = new BuildJson().write(message);
		
		System.out.println( result );
		System.out.println( new BuildJson().getValue(result, "message") );
	}
	
}
