package force.eai.mx.util;

public class Message {
	private String code = "";
	private String message = "";
	private String description = "";
	private String exception = "";
	
	public Message() {
		// Default Constructor
	}
	public Message(String code) {
		this.code = code;
	}
	public Message(String code, String message){
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	public String getDescription() {
		return description;
	}
	public String getException() {
		return exception;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
}
