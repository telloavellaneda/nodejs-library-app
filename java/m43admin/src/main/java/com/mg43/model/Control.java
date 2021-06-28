package com.mg43.model;

import java.util.Iterator;
import java.util.LinkedHashMap;

public class Control {
	private String type = "";
	private String id = "";
	private String name = "";
	private String value = "";
	private String className = "";
	private String style = "";
	private String label = "";
	private String maxLength = "";
	private String precision = "";
	private String width = "";
	private String height = "";
	private String onChange = "";
	private String onFocus = "";
	private String onBlur = "";
	private String onKeyPress = "";
	private String onKeyUp = "";
	private String onClick = "";
	private boolean isRequired = false;
	private boolean isReadOnly = false;
	private boolean isInitValue = true;
	private boolean isOnlyValue = false;
	private boolean isAutoComplete = false;
	private int labelLength = 0;
	
	private String[][] arrayOptions;
	private Iterator<Catalogo> iterator = null;
	private LinkedHashMap<String,Catalogo> options = new LinkedHashMap<String,Catalogo>();
	
	public String getType() {
		return type;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	public String getClassName() {
		return className;
	}
	public String getStyle() {
		return style;
	}
	public String getLabel() {
		return label;
	}
	public int getLabelLength() {
		return labelLength;
	}
	public String getMaxLength() {
		return maxLength;
	}
	public String getPrecision() {
		return precision;
	}
	public String getWidth() {
		return width;
	}
	public String getHeight() {
		return height;
	}
	public String getOnChange() {
		return onChange;
	}
	public String getOnFocus() {
		return onFocus;
	}
	public String getOnBlur() {
		return onBlur;
	}
	public String getOnKeyPress() {
		return onKeyPress;
	}
	public String getOnKeyUp() {
		return onKeyUp;
	}
	public String getOnClick() {
		return onClick;
	}
	public Iterator<Catalogo> getIterator() {
		return iterator;
	}
	public Catalogo getOption(String key) {
		if ( options.get(key) == null)
			setOption(key, new Catalogo());
		return options.get(key);
	}
	public Iterator<Catalogo> getOptions() {
		return options.values().iterator();
	}
	public String[][] getArrayOptions() {
		return arrayOptions;
	}
	public boolean isRequired() {
		return isRequired;
	}
	public boolean isReadOnly() {
		return isReadOnly;
	}
	public boolean isInitValue() {
		return isInitValue;
	}	
	public boolean isOnlyValue() {
		return isOnlyValue;
	}
	public boolean isAutoComplete() {
		return isAutoComplete;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public void setLabelLength(int labelLength) {
		this.labelLength = labelLength;
	}
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}
	public void setOnFocus(String onFocus) {
		this.onFocus = onFocus;
	}
	public void setOnBlur(String onBlur) {
		this.onBlur = onBlur;
	}
	public void setOnKeyPress(String onKeyPress) {
		this.onKeyPress = onKeyPress;
	}
	public void setOnKeyUp(String onKeyUp) {
		this.onKeyUp = onKeyUp;
	}
	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}
	public void setIterator(Iterator<Catalogo> iterator) {
		this.iterator = iterator;
	}
	public void setOption(String key, Catalogo value) {
		this.options.put(key, value);
	}
	public void setArrayOptions(String[][] arrayOptions) {
		this.arrayOptions = arrayOptions;
	}
	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}
	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}
	public void setInitValue(boolean isInitValue) {
		this.isInitValue = isInitValue;
	}
	public void setOnlyValue(boolean isOnlyValue) {
		this.isOnlyValue = isOnlyValue;
	}
	public void setAutoComplete(boolean isAutoComplete) {
		this.isAutoComplete = isAutoComplete;
	}
}