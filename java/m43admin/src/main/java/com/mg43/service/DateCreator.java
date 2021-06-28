package com.mg43.service;

public class DateCreator {

	private static java.util.LinkedHashMap<String, String> meses;
	private static java.util.LinkedHashMap<String, String[]> financialColumns;
	
	static {
		meses = new java.util.LinkedHashMap<String, String>();
		financialColumns = new java.util.LinkedHashMap<String, String[]>();
		
		meses.put("01", "Enero");
		meses.put("02", "Febrero");
		meses.put("03", "Marzo");
		meses.put("04", "Abril");
		meses.put("05", "Mayo");
		meses.put("06", "Junio");
		meses.put("07", "Julio");
		meses.put("08", "Agosto");
		meses.put("09", "Septiembre");
		meses.put("10", "Octubre");
		meses.put("11", "Noviembre");
		meses.put("12", "Diciembre");

		String YEAR = "2021";

		financialColumns.put(YEAR + "-01", new String[] { "B", "E"});
		financialColumns.put(YEAR + "-02", new String[] { "G", "J"});
		financialColumns.put(YEAR + "-03", new String[] { "L", "O"});
		financialColumns.put(YEAR + "-04", new String[] { "Q", "T"});
		financialColumns.put(YEAR + "-05", new String[] { "V", "Y"});
		financialColumns.put(YEAR + "-06", new String[] { "AA", "AD"});
		financialColumns.put(YEAR + "-07", new String[] { "AF", "AI"});
		financialColumns.put(YEAR + "-08", new String[] { "AK", "AN"});
		financialColumns.put(YEAR + "-09", new String[] { "AP", "AS"});
		financialColumns.put(YEAR + "-10", new String[] { "AU", "AX"});
		financialColumns.put(YEAR + "-11", new String[] { "AZ", "BC"});
		financialColumns.put(YEAR + "-12", new String[] { "BE", "BH"});
	}
	
	public static String getDate(String value) {
		String[] temp = value.split("/");
		return temp[0] + " de " + meses.get(temp[1]) + " de " + temp[2];
	}
	
	public static String getMonthString(String value) {
		String[] temp = value.split("-");
		return meses.get(temp[1]) + " " + temp[0];
	}

	public static String[] getFinancialColumns(String folio) {
		return financialColumns.get(folio);
	}
}
