package force.eai.mx.exception;

public class ForceException extends Exception {
	private String code = "";
	private String message = "";
	private String description = "";
	private String exception = "";
	
	private static final long serialVersionUID = 7387273769069767134L;
		
	public ForceException() {
	}

	public ForceException(String message) {
		super(message);
	}

	public ForceException(Throwable cause) {
		super(cause);
		this.description = super.getMessage();
		this.exception =  super.getClass().getName();
	}

	public ForceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ForceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
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
