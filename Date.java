/*
 * @author
 * 		Rimantas Kazlauskas
 */

import java.util.Calendar;


public class Date {
	Calendar date;
	
	/**
	 * Initialises to a set date
	 * @param year 
	 * @param month between 0-11 (e.g. May is 4)
	 * @param day between 0-30 (e.g. 25th is 24)
	 */
	public Date(int day, int month, int year){
		date = Calendar.getInstance();
		year = Integer.parseInt("20" + year);
		setDate(year, month-1, day-1);
	}
	
	/**
	 * Initialises current date
	 */
	public Date(){
		date = Calendar.getInstance();
	}
	
	/**
	 * @return year
	 */
	public int getYear() {
		return date.get(Calendar.YEAR);
		
	}
	
	/**
	 * @return month
	 */
	public int getMonth() {
		return date.get(Calendar.MONTH);
		
	}

	/**
	 * @return day
	 */
	public int getDay() {
		return date.get(Calendar.DATE);
		
	}
	
	/**
	 * @param anotherDate 
	 * @return day difference between 2 dates
	 */
	public int compare(Date anotherDate) {
		int year = getYear() - anotherDate.getYear();
		if (year == 0) {
			return Math.abs(getDaysInYear() - anotherDate.getDaysInYear());
		} else {
			int days = 0;
			if (year < 0) {
				int remainingDays = getMaxDaysInYear() - getDaysInYear();
				Calendar tempDate = date;
				for(int i = 1; i <= Math.abs(year); i++) {
					if(i == Math.abs(year)) {
						days += remainingDays + anotherDate.getDaysInYear();
					}else {
						tempDate.set(tempDate.get(Calendar.YEAR) + 1, 
								tempDate.get(Calendar.MONTH), 
								tempDate.get(Calendar.DATE));
						days += tempDate.getMaximum(Calendar.DAY_OF_YEAR);
					}
				}
			}else {
				int remainingDays = anotherDate.getMaxDaysInYear() - 
						anotherDate.getDaysInYear();
				Date tempDate = anotherDate;
				for(int i = 1; i <= Math.abs(year); i++) {
					if(i == Math.abs(year)) {
						days += remainingDays + getDaysInYear();
					}else {
						tempDate.setDate(tempDate.getYear() + 1, 
								tempDate.getMonth(), tempDate.getDay());
						days += tempDate.getMaxDaysInYear();
					}
				}
			}
			return days;
		}
	}
	
	private int getDaysInYear() {
		return date.get(Calendar.DAY_OF_YEAR);
	}
	
	private int getMaxDaysInYear() {
		return date.getMaximum(Calendar.DAY_OF_YEAR);
	}
	
	private void setDate(int year, int month, int day) {
		date.set(year, month, day);
	}
	
	public String toString() {
		return "Year: " + getYear() + ", Month: " + getMonth() + ", Day: " + 
	getDay();
	}
	
	/**
	 * @param date
	 * @return boolean, whatever the the object is before param date
	 */
	public boolean isBefore(Date date) {
		int bool = this.date.compareTo(date.toCalendar());
		return bool > 0;
	}
	private Calendar toCalendar() {
		return date;
	}
}
