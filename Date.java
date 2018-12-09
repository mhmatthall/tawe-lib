import java.util.Calendar;

/**
 * Encapsulation of Java's Calendar class for ease of use
 * 
 * @author Rimantas Kazlauskas
 */

public class Date {
	Calendar date;

	/**
	 * initialises date to a set date.
	 *
	 * @param day   between 1-31
	 * @param month between 1-12
	 * @param year
	 */
	public Date(int day, int month, int year) {
		date = Calendar.getInstance();
		setDate(year, month - 1, day);
	}

	/**
	 * initialises date to a set date. used by DatabaseRequest
	 *
	 * @param date as a string of 8 characters "DDMMYYYY"
	 */
	public Date(String date) {
		this.date = Calendar.getInstance();
		int day = Integer.parseInt(date.substring(0, 2));
		int month = Integer.parseInt(date.substring(2, 4));
		int year = Integer.parseInt(date.substring(4, 8));

		setDate(year, month - 1, day);
	}

	/**
	 * initialises current date.
	 */
	public Date() {
		date = Calendar.getInstance();
	}

	/**
	 * Gets the year.
	 *
	 * @return year as string
	 */
	public String getYear() {
		return "" + date.get(Calendar.YEAR);

	}

	/**
	 * Gets the month.
	 *
	 * @return month as string
	 */
	public String getMonth() {
		if ((date.get((Calendar.MONTH))) + 1 < 10) {
			return "0" + (date.get(Calendar.MONTH) + 1);
		} else {
			return "" + (date.get(Calendar.MONTH) + 1);
		}

	}

	/**
	 * Gets the day.
	 *
	 * @return day as string
	 */
	public String getDay() {
		if (date.get((Calendar.DATE)) < 10) {
			return "0" + (date.get(Calendar.DATE));
		} else {
			return "" + (date.get(Calendar.DATE));
		}

	}

	/**
	 * Compares 2 dates.
	 * <p>
	 * Example: date1.compare(date2).
	 * </p>
	 * 
	 * @param anotherDate date to be compared with
	 * @return day difference between 2 dates
	 */
	public int compare(Date anotherDate) {
		int year = Integer.parseInt(getYear()) - Integer.parseInt(anotherDate.getYear());
		if (year == 0) {
			return Math.abs(getDaysInYear() - anotherDate.getDaysInYear());
		} else {
			int days = 0;
			if (year < 0) {
				int remainingDays = getMaxDaysInYear() - getDaysInYear();
				Calendar tempDate = date;
				for (int i = 1; i <= Math.abs(year); i++) {
					if (i == Math.abs(year)) {
						days += remainingDays + anotherDate.getDaysInYear();
					} else {
						tempDate.set(tempDate.get(Calendar.YEAR) + 1, tempDate.get(Calendar.MONTH),
								tempDate.get(Calendar.DATE));
						days += tempDate.getMaximum(Calendar.DAY_OF_YEAR);
					}
				}
			} else {
				int remainingDays = anotherDate.getMaxDaysInYear() - anotherDate.getDaysInYear();
				Date tempDate = anotherDate;
				for (int i = 1; i <= Math.abs(year); i++) {
					if (i == Math.abs(year)) {
						days += remainingDays + getDaysInYear();
					} else {
						tempDate.setDate(Integer.parseInt(tempDate.getYear()) + 1,
								Integer.parseInt(tempDate.getMonth()), Integer.parseInt(tempDate.getDay()));
						days += tempDate.getMaxDaysInYear();
					}
				}
			}
			return days;
		}
	}

	/**
	 * Gets date as days instead of the usual dd/mm/yyyy format (e.g. december 31st
	 * is 365)
	 *
	 * @return date in days
	 */
	private int getDaysInYear() {
		return date.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * Gets the maximum days in a year.
	 *
	 * @return max days in a year
	 */
	private int getMaxDaysInYear() {
		return date.getMaximum(Calendar.DAY_OF_YEAR);
	}

	/**
	 * Forwards the date by a specified amount of days
	 *
	 * @param days to be forwarded
	 */
	public void forwardDate(int days) {
		date.add(Calendar.DAY_OF_MONTH, days);
	}

	/**
	 * sets date to a specified date.
	 *
	 * @param year  minimum 1
	 * @param month 0-11
	 * @param day   1-30
	 */
	private void setDate(int year, int month, int day) {
		date.set(year, month, day);
	}

	/**
	 * returns date in DDMMYYYY format, used for database
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getDay() + "" + getMonth() + "" + getYear();
	}

	/**
	 * Checks whatever the date is before another date
	 *
	 * @param date to be compared
	 * @return True of <b>this</b> is before the param date
	 */
	public boolean isBefore(Date date) {
		int bool = this.date.compareTo(date.toCalendar());
		return bool > 0;
	}

	/**
	 * effectively casting back to Calendar
	 *
	 * @return <b>this</b> as calendar
	 */
	private Calendar toCalendar() {
		return date;
	}
}
