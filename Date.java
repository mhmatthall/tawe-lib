/*
 * @author
 * 		Rimantas Kazlauskas
 */

import java.util.Calendar;

public class Date {
	Calendar date;
	public Date(int year, int month, int day){
		date.set(year, month, day);
	}
	public Date(){
		date = Calendar.getInstance();
	}
	public int getYear() {
		return date.get(Calendar.YEAR);
		
	}
	
	public int getMonth() {
		return date.get(Calendar.MONTH);
		
	}
	
	public int getDay() {
		return date.get(Calendar.DATE);
		
	}
	
	public String toString() {
		return "Year: " + getYear() + ", Month: " + getMonth() + ", Day: " + getDay();
	}
	
}
