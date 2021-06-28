package force.eai.mx.tools;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FormatValues {
	
	public String formatDate( String value ) {
		SimpleDateFormat sdParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy"); 
		try {
			value = sdFormat.format(sdParse.parse(value));
		} catch(Exception e) { }
		return value;
	}
	
	public String formatTimestamp( String value ) {
		SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {		
			return sdFormat.format( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value) );
		} catch(Exception e) {}
		
		try {
			return sdFormat.format( new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(value) );
		} catch(Exception e) {
			return ( value != null ) ? value : "";
		}
	}
	
	public String formatTimestampCompact( String value ) {
		SimpleDateFormat sdParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yy"); 
		try {
			value = sdFormat.format(sdParse.parse(value));
		} catch(Exception e) { }
		return ( value != null ) ? value : "";
	}
	
	public String formatTimestampDB( String value ) {
		if ( !(value != null && !value.equals("")) )
			return "";
		try {
			String miliseconds = ( value.split("\\.").length == 2 ) ? value.split("\\.")[1] : "000000";
			return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value)) + 
					"." + 
					miliseconds + 
					"000";
		} catch (Exception e) {
			return "";
		}
	}
	
	public String formatMoney( double value ) {
		try {
			return new DecimalFormat("#,##0.00").format(value);
		} catch (Exception e) {
			return "";
		}
	}

	public String formatMoney( String value ) {
		try {
			return formatMoney(Double.parseDouble(value));
		} catch (Exception e) {
			return "";
		}
	}

	public String formatMoneyCompact( double value ) {
		try {
			return new DecimalFormat("#,##0").format(value);
		} catch (Exception e) { 
			return "";
		}
	}

	public String formatMoneyCompact( String value ) {
		try {
			return formatMoneyCompact(Double.parseDouble(value));
		} catch (Exception e) { 
			return "";
		}
	}

	public double roundMoney( double value ) {
		try {
			java.math.BigDecimal bd = new java.math.BigDecimal(value);
		    bd = bd.setScale(0, java.math.BigDecimal.ROUND_UP);	    
			return bd.doubleValue();
		} catch (Exception e) {
			return 0;
		}
	}
		
	public String shorten( String value, int number ) {
		try {
			return ( value.length() > number ) ? value.substring(0, number) + "..." : value;
		} catch (Exception e) {
			return value;
		}
	}
	
	public String titleCase( String value ) {
		try {
			String titleCaseString = "";
			for (int i = 0; i < value.split(" ").length; i++ )
				titleCaseString += value.split(" ")[i].substring(0,1).toUpperCase() + value.split(" ")[i].substring(1).toLowerCase() + " ";
		    return titleCaseString.trim();
		} catch (Exception e) {
			return value;
		}
	}
	
	public String getInitial( String value ) {
		try {
			return ( value != null ) ? value.substring(0, 1) + "." : "";
		} catch (Exception e) {
			return "";
		}
	}
	
	public String fullName ( String[] value ) {
		try {
			String temp = "";
			for (int i = 0; i < value.length; i ++)
				temp += validate(value[i]) + " ";
			return temp.replaceAll("^ +| +$|( )+", " ").trim();
		} catch(Exception e) {
			return "";
		}
	}
	
	// Avoids any HTML tag
	public String prettyFormat(String value) {
		try {
			return value.replaceAll("%3C", "&lt;").replaceAll("%3E", "&gt;").replaceAll("''", "'");
		} catch(Exception e){
			return "";
		}
	}
	
	public double parseDouble(String value) {
		try {
			return Double.parseDouble(value);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public String validate( String value ) {
		return (value!= null) ? value : "";
	}
	
	public String getTimeStamp() {
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		return formatTimestamp(currentTimestamp + "");
	}
	
	public static void main(String a[]) {
		String nombre = null;
		System.out.println(new FormatValues().titleCase(nombre));
		System.out.println(new FormatValues().formatTimestamp("2016-01-19 11:37:23.")); //2016-01-19 11:37:23.861987
	}
}
