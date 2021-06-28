package force.eai.mx.form;

import java.util.Iterator;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpSession;
import force.eai.mx.util.Section;
import force.eai.mx.util.Catalogo;
import force.eai.mx.util.Control;
import force.eai.mx.util.Cliente;

public class FormUtils {
	private static final String SPACE = " ";
	private static final String DBL_QUOTE = "\"";
	
	private Control control = new Control();
	private Cliente cliente = new Cliente();
	private HttpSession session = null;
	
	public FormUtils() {
		// Nothing
	}
	
	public FormUtils(HttpSession session) {
		this.session = session;
	}
	
	public String styleDisplayTipoMoral() {
		String tmp = ( cliente.getTipo().equals("2") ) ? "display:table; margin-top:10px;" : "display:none";
		return setAttribute("style", tmp);
	}
	
	/*
	 * Display "Decreto" Light Bulb status
	 */
	public String displayLightBulb (String value, String size) {
		String html = "";
		String color = "";
		String style = "";
		String description = "";
		
		value = ( value != null ) ? value : "";
		
		style += (!size.equals("")) ? "height:" + size + "px; width:" + size + "px;" : "";
		style += "vertical-align: middle";
		
		if ( value.equals("APROBADO") ) {
			color = "green";
			description = "Aprobado";
		} else if ( value.equals("RECHAZADO") ) {
			color = "red";
			description = "Rechazado";
		} else if ( value.equals("CONDICIONADO") ) {
			color = "yellow";
			description = "Condicionado";
		} else {
			color = "gray";
			description = "Prospecto";
		}
		
		html += "<img";
		html += setAttribute("src", "images/b-" + color + ".png");
		html += setAttribute("style", style);
		html += setAttribute("align","top");
		html += setAttribute("alt", description);
		html += setAttribute("title", description);
		html += ">";
		
		return html;
	}
	
	public String displayBankLogo(String id, String label, String size) {
		String imageSource = "";
		FormElements image = new FormElements();

		id = ( id != null ) ? id : "00";
		
		imageSource = "images/bancos_" + id + ".png";

		image.initControl();
		image.getControl().setValue(imageSource);
		image.getControl().setWidth(size);
		image.getControl().setHeight(size);
		image.getControl().setLabel(label);
		
		return image.createImage();
	}
	
	public String splitKey(String value, int index) {
		try {
			return value.split("\\|")[index];
		} catch (Exception e) {
			return "";
		}
	}
	public double getSplittedValue(String value, int index) {
		try {
			return Double.parseDouble(value.split("\\|")[index]);
		} catch (Exception e) {
			return 0;
		}
	}
		
	public String sumIngresos( String value1, String value2) {
		double numero1 = 0;
		double numero2 = 0;
		try {
			numero1 = Double.parseDouble(value1);
		} catch (Exception e) {}
		try {
			numero2 = Double.parseDouble(value2);
		} catch (Exception e) {}
		return (numero1 + numero2) + "";
	}
	
	public String[] getPreferences() {
		String filtros = "";
		String display = "";
		String otros = "";
		
		filtros += getPreference("filter_user");
		filtros += getPreference("filter_user_responsable");
		filtros += getPreference("filter_fase");
		filtros += getPreference("filter_status");
		filtros += getPreference("filter_banco");
		filtros += getPreference("filter_decreto");
		filtros += getPreference("filter_force_id");
		filtros += getPreference("filter_full_name");
		filtros += getPreference("filter_year");
		filtros += getPreference("filter_month");
		filtros += getPreference("filter_year_autorizacion");
		filtros += getPreference("filter_month_autorizacion");
		filtros += getPreference("filter_year_firma");
		filtros += getPreference("filter_month_firma");
		
		display += getPreference("divFases");
		display += getPreference("divStatus");
		display += getPreference("divBancos");
		display += getPreference("divUsuarios");
		display += getPreference("divMonth");			
		display += getPreference("divDecreto");
		display += getPreference("showDivClientes");
		display += getPreference("showDivProspectos");

		return new String[] {filtros, display, otros};
	}
	
	private String getPreference(String key) {
		try {
			String value = ( (String)session.getAttribute(key) != null )  ?  (String)session.getAttribute(key) : "";
			return key + "=" + value + "|";
		} catch (Exception e) {
			return "";
		}
	}
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap<String,LinkedHashMap<String,String>> getDashboardSet(String key) {
		try {
			return (LinkedHashMap<String,LinkedHashMap<String,String>>) session.getAttribute(key);
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public LinkedHashMap<String,String> getTotalsDashboardSet(String key) {
		try {
			return (LinkedHashMap<String,String>) session.getAttribute(key);
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public String[][] getFormCatalogue(String key) {
		try {
			LinkedHashMap<String, String[][]> formCatalogues = ( LinkedHashMap<String, String[][]> ) session.getAttribute("formCatalogues");
			return formCatalogues.get(key);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Iterator<Section> getFormSections() {
		try {
			LinkedHashMap<String, Section> formSections = ( LinkedHashMap<String, Section> ) session.getAttribute("formSections");
			return formSections.values().iterator();
		} catch ( Exception e ) { 
			return null;			
		}
	}

	@SuppressWarnings("unchecked")
	public Section getFormSection( String key ) {
		try {
			LinkedHashMap<String, Section> formSections = ( LinkedHashMap<String, Section> ) session.getAttribute("formSections");
			return formSections.get(key);
		} catch ( Exception e ) { 
			return new Section();			
		}
	}
	
	public String getFilterCatalogueValue( String key, String id ) {
		try {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String,LinkedHashMap<String,Catalogo>> filterCatalogues = ( LinkedHashMap<String,LinkedHashMap<String,Catalogo>> ) session.getAttribute("filterCatalogues");
			return filterCatalogues.get(key).get(id).getNombre();
		} catch ( Exception e ) { 
			return "";
		}
	}

	public Catalogo getFilterCatalogueObject( String key, String id ) {
		try {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String,LinkedHashMap<String,Catalogo>> filterCatalogues = ( LinkedHashMap<String,LinkedHashMap<String,Catalogo>> ) session.getAttribute("filterCatalogues");
			return filterCatalogues.get(key).get(id);
		} catch ( Exception e ) { 
			return new Catalogo();
		}
	}
	
	public Iterator<Catalogo> getCatalogue(String hashId, String key) {
		try {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String,LinkedHashMap<String,Catalogo>> filterCatalogues = ( LinkedHashMap<String,LinkedHashMap<String,Catalogo>> ) session.getAttribute(hashId);
			return filterCatalogues.get(key).values().iterator();
		} catch ( Exception e ) { 
			return null;			
		}
	}
		
	public String getFormStatus( String key, String value ) {		
		FormElements fe = new FormElements();
		
		fe.initControl();
		fe.getControl().setId("id_status");
		fe.getControl().setValue(value);
		fe.getControl().setInitValue(false);
		fe.getControl().setClassName("status-combo");
		fe.getControl().setIterator( getCatalogue("filterCatalogues", "fase" + key));
		
		return fe.createSelectElement();
	}
	
	public String getFormProducto( String key, String value ) {		
		FormElements fe = new FormElements();
		
		fe.initControl();
		fe.getControl().setId("cred_producto");
		fe.getControl().setValue(value);
		fe.getControl().setIterator( getCatalogue("filterCatalogues", "banco" + key));
		
		return fe.createSelectElement();
	}
	
	public int getIntFromSession(String key, int defaultValue) {
		try {
			return Integer.parseInt(String.class.cast(session.getAttribute(key)));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public String getStringFromSession(String key) {
		try {
			return (session.getAttribute(key) != null) ? String.class.cast(session.getAttribute(key)) : "";
		} catch (Exception e) {
			return "";
		}
	}

	public String getStringFromSession(String key, String defaultValue) {
		try {
			return (session.getAttribute(key) != null) ? String.class.cast(session.getAttribute(key)) : defaultValue;
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public double getTotal( String key ) {
		try {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String,Double> object = ( LinkedHashMap<String,Double> ) session.getAttribute("totals");
			return object.get(key);
		} catch ( Exception e ) { 
			return 0;
		}
	}
		
	private String setAttribute( String attribute, String value ) {
		return ( !value.trim().equals("") ) ? SPACE + attribute + "=" + DBL_QUOTE + value + DBL_QUOTE : "";
	}
	
	public Control getControl() {
		return control;
	}
	public void setControl(Control control) {
		this.control = control;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}