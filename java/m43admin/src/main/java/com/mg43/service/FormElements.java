package com.mg43.service;

import java.util.Iterator;
import com.mg43.model.Control;
import com.mg43.model.Catalogo;

public class FormElements {
	private static final String CR = "\n";
	private static final String TAB = "\t";
	private static final String SPACE = " ";
	private static final String DBL_QUOTE = "\"";
	
	private String preffix = "";
	private Control control = null;
		
	/*
	 * Create HTML Select Element
	 */
	public String createSelectElement() {
		String html = "";
		String temp = "";
		String label = "";
		String onBlur = "";
		String onFocus = "";
		String onChange = "";
		
		Iterator<Catalogo> tmp = null;
		
		if ( control.getIterator() != null ) 
			tmp = control.getIterator();

		else if ( control.getArrayOptions() != null ) {
			for (int i = 0; i < control.getArrayOptions().length; i ++ ) {
				control.getOption(i+"").setId(control.getArrayOptions()[i][0]);
				control.getOption(i+"").setNombre(control.getArrayOptions()[i][1].toUpperCase());
			}
			tmp = control.getOptions();

		} else if ( control.getOptions() != null )
			tmp = control.getOptions();
		
		html += "<select";
		html += setAttribute("id",preffix + control.getId());
		html += setAttribute("name",preffix + control.getName());
		html += setAttribute("initValue",control.getValue());
		html += setAttribute("class",control.getClassName());
		
		onFocus		+= (!control.getOnFocus().equals(""))		? SPACE + control.getOnFocus() : ""; 
		onBlur		+= (!control.getOnBlur().equals(""))		? SPACE + control.getOnBlur() : "";
		onChange	+= (!control.getOnChange().equals(""))		? SPACE + control.getOnChange() : ""; 

		// REQUIRED FIELD
		temp = ( control.isRequired() ) ? SPACE + "requiredFlag(this.id);" : "";
		onBlur  += temp;
		onChange  += temp;

		if ( !control.isReadOnly() ) {
			html += addListener("onFocus",onFocus);
			html += addListener("onBlur",onBlur);
			html += addListener("onChange",onChange);
		}
			
		html += ( control.isRequired() ) ? setAttribute("required","true") : "";
		
		html += ">" + CR;
		
		// Default Label
		label = ( !control.getLabel().equals("") ) ? "-" + SPACE + control.getLabel() + SPACE + "-" : "- Seleccione -";
		
		html += ( control.isInitValue() ) ? TAB + TAB + TAB + TAB + "<option value=\"\">" + label + "</option>" + CR : "" ;		
		if ( tmp != null ) {
			String multiple = "";
			String single = "";
			for (Iterator<Catalogo> iterator = tmp; iterator.hasNext();) {
				Catalogo catalogo = iterator.next();
				String tempLabel = "";
				if ( catalogo.getNombre() != null && !catalogo.getNombre().equals("") && control.getLabelLength() > 0 && catalogo.getNombre().length() > control.getLabelLength() )
					tempLabel = catalogo.getNombre().substring(0, control.getLabelLength()) + "...";
				else
					tempLabel = catalogo.getNombre(); 
				String selected = ( catalogo.getId().equals( control.getValue() ) ) ? " selected" : "" ;
				multiple += TAB + TAB + TAB + TAB + "<option value=\"" + catalogo.getId() + "\"" + selected + ">" + tempLabel + "</option>" + CR;
				
				if ( catalogo.getId().equals( control.getValue() ) ) 
					single = TAB + TAB + TAB + TAB + "<option value=\"" + catalogo.getId() + "\"" + selected + ">" + tempLabel + "</option>" + CR; 
			}
			html += ( control.isOnlyValue() ) ? single : multiple;
		}
		html += TAB + TAB + TAB; 
		html += "</select>";
		
		html += requiredSpan();
		
		return html;
	}
	
	/*
	 * Create HTML Input Element
	 */
	public String createInputElement() {
		String html = "";
		String temp = "";
		String onFocus = "";
		String onBlur = "";
		String onChange = "";
		String onKeyUp = "";
		String onKeyPress = "";
		
		html += "<input";
		html += setAttribute("type","text");
		html += setAttribute("id", preffix + control.getId() );
		html += setAttribute("name",preffix + control.getName());
		html += setAttribute("initValue",control.getValue());
		html += setAttribute("value",control.getValue());
		html += setAttribute("class",control.getClassName());
		html += setAttribute("maxlength",control.getMaxLength());
		html += setAttribute("precision",control.getPrecision());
		html += ( control.isRequired() ) ? setAttribute("required","true") : "";
		html += ( control.isReadOnly() ) ? setAttribute("readonly") : "";
		html += ( control.getType().equals("email") ) ? setAttribute("autocapitalize","off") : "";		
		
		if ( control.getType().equals("date") ) {
			onBlur		+= SPACE + "validaFecha(this);";
			onKeyUp		+= SPACE + "autoCompleteDateFormat(this);"; 
			onKeyPress	+= SPACE + "autoCompleteDateFormat(this);"; 

		} else if ( control.getType().equals("currency") ) { 
			onFocus += SPACE + "this.value=replaceFormat(this.value);";
			onBlur  += SPACE + "validaNumero(this); this.value=formatNumber(this.value);";

		} else if ( control.getType().equals("number") ) { 
			onBlur  += SPACE + "validaNumero(this);";
			
		} else if ( control.getType().equals("rfc") )  {
			onBlur  += SPACE + "validaRFC(this);";
			
		} else if ( control.getType().equals("email") ) {
			onBlur  += SPACE + "validaCorreo(this);";

		} else if ( control.getType().equals("phone") ) {
			onBlur  += SPACE + "validaTelefono(this);";
		
		} else if ( control.getType().equals("name")) {
			onBlur  += SPACE + "validaNombre(this);";

		} else {
			onBlur  += SPACE + "validaAlfanumerico(this);";
		}
		
		if ( control.isAutoComplete() )
			onBlur += SPACE + "autoComplete(this);";
		
		onFocus		+= (!control.getOnFocus().equals(""))		? SPACE + control.getOnFocus() : ""; 
		onBlur		+= (!control.getOnBlur().equals(""))		? SPACE + control.getOnBlur() : "";
		onChange	+= (!control.getOnChange().equals(""))		? SPACE + control.getOnChange() : ""; 
		onKeyUp		+= (!control.getOnKeyUp().equals("") )		? SPACE + control.getOnKeyUp() : "";
		onKeyPress	+= (!control.getOnKeyPress().equals(""))	? SPACE + control.getOnKeyPress() : "";

		// REQUIRED FIELD
		temp = ( control.isRequired() ) ? SPACE + "requiredFlag(this.id);" : "";
		onBlur  += temp;

		if ( !control.isReadOnly() ) {
			html += addListener("onFocus",onFocus);
			html += addListener("onBlur", onBlur);
			html += addListener("onChange",onChange);
			html += addListener("onKeyUp",onKeyUp);
			html += addListener("onKeyPress",onKeyPress);
		}
			
		html += ">";

		html += requiredSpan();
		
		html += ( control.getType().equals("date") ) ? " (dd/mm/yyyy)" : "";
		
		return html;
	}
	
	/*
	 * Create HTML Textarea
	 */
	public String createTextareaElement() {
		String html = "";
		String temp = "";
		String onChange = "";
		String onBlur = "";
		
		html += "<textarea";
		html += setAttribute("id", preffix + control.getId() );
		html += setAttribute("initValue",control.getValue());
		html += setAttribute("class",control.getClassName());
		html += setAttribute("maxlength",control.getMaxLength());
		
		onBlur		+= (!control.getOnBlur().equals(""))		? SPACE + control.getOnBlur() : ""; 
		onChange	+= (!control.getOnChange().equals(""))		? SPACE + control.getOnChange() : ""; 

		// REQUIRED FIELD
		temp = ( control.isRequired() ) ? SPACE + "requiredFlag(this.id);" : "";
		onBlur  += temp;

		if ( !control.isReadOnly() ) {
			html += addListener("onBlur", onBlur);
			html += addListener("onChange",onChange);
		}

		html += ">" + control.getValue() + "</textarea>";
		
		html += requiredSpan();
		
		return html;
	}
	
	/*
	 * HTML Required SPAN
	 */
	private String requiredSpan() {
		String html = "";
		if ( control.isRequired() ) {
			html += CR;
			html += TAB + TAB + TAB;
			html += "<span";
			html += setAttribute("class","required");
			html += setAttribute("id", preffix + control.getId() + "_req");
			html += ( !control.getValue().equals("") ) ? setAttribute("style","visibility:hidden") : "";
			html += ">";
			html += "*";
			html += "</span>";
		}
		return html;
	}
	
	public String createSelectWithOtherElement(String label) {
		String html = "";
		String otro = ( control.getValue().split("\\|").length > 1 ) ? control.getValue().split("\\|")[1] : "";
		String display = ( !control.getValue().split("\\|")[0].equals("OTRO")) ? "display:none" : "display:table-row";
		String onChange = "muestraOtroTr(this);" + SPACE + control.getOnChange();

		Control trElement = this.control;

		// SELECT Element
		control.setValue(control.getValue().split("\\|")[0]);
		control.setOnChange(onChange.trim());
		html += createTableRow(trElement, label, createSelectElement());

		// Input Text Element
		trElement.setStyle(display);
		control.setId(control.getId() + "_otro");
		control.setValue(otro);
		control.setOnChange("");
		control.setOnBlur("");		
		html += CR + TAB;
		html += createTableRow(trElement, "", createInputElement());
		
		return html;
	}

	private String createTableRow(Control control, String label, String value) {
		String html = "";

		html += "<tr";
		html +=	setAttribute("id", preffix + control.getId() + "_tr");
		html += setAttribute("style", control.getStyle());
		html +=	">" + CR;
		html += TAB + TAB + "<td";
		html += setAttribute("class","form-label");
		html += ">" + label + "</td>";
		html += CR;
		html += TAB + TAB + "<td";
		html += setAttribute("class","form-value");
		html += ">";
		html += CR + TAB + TAB + TAB;
		html += value;
		html += CR + TAB + TAB + "</td>";
		html += CR + TAB + "</tr>";
		
		return html;
	}
	
	public String createImage() {
		String html = "";
		
		html =  "<img";
		html += setAttribute("src", control.getValue());
		html += setAttribute("style","height:" + control.getHeight() + "px; width:" + control.getWidth() + "px; vertical-align: middle;");
		html += setAttribute("align","top");
		html += setAttribute("alt", control.getLabel());
		html += setAttribute("title", control.getLabel()); 
		html += "> ";
		
		return html;
	}
	
	private String setAttribute( String attribute ) {
		return ( !attribute.trim().equals("") ) ? SPACE + attribute : "";
	}

	private String setAttribute( String attribute, String value ) {
		return ( !value.equals("") ) ? SPACE + attribute + "=" + DBL_QUOTE + value + DBL_QUOTE : "";
	}

	private String addListener( String listener, String function ) {
		return ( !function.trim().equals("") ) ? CR + TAB + TAB + TAB + TAB + listener + "=" + DBL_QUOTE + function.trim() + DBL_QUOTE : "";
	}
	
	public void initControl () {
		this.control = new Control();
	}
	public String getPreffix() {
		return preffix;
	}
	public Control getControl() {
		return control;
	}
	public void setPreffix(String preffix) {
		this.preffix = preffix;
	}
	public void setControl(Control control) {
		this.control = control;
	}

	public static void main (String a[]) {
		FormElements fu = new FormElements();
		fu.initControl();
		fu.getControl().setId("id_banco");
		fu.getControl().setType("email");
		fu.getControl().setOnChange("requiredFlag(this.id); initBanco(''); obtenerCombo(this);");
		fu.getControl().setOnBlur("alert('Hola Mundo!');");
		fu.getControl().setOption("0", new Catalogo());
		fu.getControl().setOption("1", new Catalogo());
		fu.getControl().setOption("2", new Catalogo());
		fu.getControl().getOption("0").setId("0");
		fu.getControl().getOption("0").setNombre("BLA");
		fu.getControl().getOption("1").setId("1");
		fu.getControl().getOption("1").setNombre("BLE");
		fu.getControl().getOption("2").setId("2");
		fu.getControl().getOption("2").setNombre("BLI");
		fu.getControl().setRequired(true);
		
		//System.out.println("\t" + fu.createSelectWithOtherElement("Tipo de Algo"));
	}
}