package force.eai.mx.util;

public class Section {
	private String id = "";
	private String section = "";
	private String title = "";
	private String divId = "";
	private String includeFile = "";
	private String preffix = "";
	private String status = "";
	private boolean isVisible = true;
	
	public String getId() {
		return id;
	}
	public String getSection() {
		return section;
	}
	public String getTitle() {
		return title;
	}
	public String getDivId() {
		return divId;
	}
	public String getIncludeFile() {
		return includeFile;
	}
	public String getPreffix() {
		return preffix;
	}
	public String getStatus() {
		return status;
	}
	public boolean isVisible() {
		return isVisible;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDivId(String divId) {
		this.divId = divId;
	}
	public void setIncludeFile(String includeFile) {
		this.includeFile = includeFile;
	}
	public void setPreffix(String preffix) {
		this.preffix = preffix;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}