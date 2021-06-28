package force.eai.mx.tools;

import java.util.Calendar;

public class RandomDate {
	
	public String randomDate(int yearMin, int yearMax) {
	    int yyyy = random(yearMin, yearMax);
	    int mm = random(1, 12);
	    int dd = 0; // will set it later depending on year and month
	
		switch(mm) {
			case 2:
				if (isLeapYear(yyyy)) {
					dd = random(1, 29);
				} else {
					dd = random(1, 28);
				}
				break;
			
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				dd = random(1, 31);
			break;
			
			default:
				dd = random(1, 30);
				break;
			}
	
		String year = Integer.toString(yyyy);
		String month = Integer.toString(mm);
		String day = Integer.toString(dd);
		
		if (mm < 10)
		    month = "0" + mm;
		if (dd < 10)
		    day = "0" + dd;
	
		return day + '/' + month + '/' + year;
	}

	public String randomTime() {
		int hh = random(0,23);
		int mm = random(0,59);
		int ss = random(0,59);
		
		return leadingZero(hh) + ":" + leadingZero(mm) + ":" + leadingZero(ss);
	}
	
	private int random (int lowerBound, int upperBound) {
		return (lowerBound + (int) Math.round(Math.random() * (upperBound - lowerBound)));
	}

	private boolean isLeapYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		int noOfDays = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
		
		if (noOfDays > 365) {
		    return true;
		}
		
		return false;
	}
	
	private String leadingZero(int value) {
		if (value < 10)
			return "0" + Integer.toString(value);
		else
			return Integer.toString(value);
	}
}