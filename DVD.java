import java.util.ArrayList;

/**
 * The Class DVD.
 * Models DVD in a library
 * 
 * @author Ben Rochford
 *
 */

public class DVD extends Resource {
	protected static double fineDay = 2.00;
	protected static double fineMax = 25.00;
	private String director;
	private int runtime;  //in minutes
	private String language;
	private ArrayList<String> subLang = new ArrayList<String>();

	
	/**
	 * Creates a new DVD resource in a library
	 *
	 * @param title of DVD
	 * @param year of release
	 * @param thumbnail of the DVD
	 * @param director of the DVD
	 * @param runtime of DVD in minutes
	 * @param language of DVD
	 */
	public DVD(String title, int year, Thumbnail thumbnail, String director, int runtime, String language) {
		super(title, year, thumbnail);
		this.director = director;
		this.language = language;
		this.runtime = runtime;
	}

	
	/**
	 * initialises a DVD. Used by DatabaseRequest.
	 *
	 * @param resourceID of the DVD
	 * @param title of the DVD
	 * @param year of release
	 * @param thumbnail of DVD
	 * @param queue of users that are waiting to receive it
	 * @param director of the DVD
	 * @param runtime of DVD in minutes
	 * @param language of DVD
	 * @param subLang of DVD
	 */
	public DVD(String resourceID, String title, int year, Thumbnail thumbnail, 
			RequestQueue queue, String director, int runtime, String language, 
			ArrayList<String> subLang) {
		super(resourceID, title, year, thumbnail, queue);
		this.director = director;
		this.language = language;
		this.runtime = runtime;
		this.subLang = subLang;
	}

	/**
	 * human readable
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String x;
		x = ("Title: " + title + " \nYear: " + year + " \nDirector: " + director + " \nRuntime: " + runtime
				+ " \nLanguage: " + language);
		return x;
	}

	/**
	 * Gets the director.
	 *
	 * @return director
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * Sets the director.
	 *
	 * @param director to be set
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * Gets the runtime of DVD
	 *
	 * @return the runtime in minutes
	 */
	public int getRuntime() {
		return runtime;
	}

	/**
	 * Sets the runtime of the DVD
	 *
	 * @param runtime in minutes of DVD
	 */
	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	/**
	 * Gets the language.
	 *
	 * @return language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets the language.
	 *
	 * @param language of a DVD
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Gets the sub langGets the sub lang.
	 *
	 * @return the sub lang
	 */
	public ArrayList<String> getSubLang() {
		return subLang;
	}

	/**
	 * Sets the sub lang.
	 *
	 * @param subLang arrayList of subLanguages
	 */
	public void setSubLang(ArrayList<String> subLang) {
		this.subLang = subLang;
	}

	
	/**
	 * Gets the fine per day when overdue
	 *
	 * @return the fine day
	 */
	public static double getFineDay() {
		return fineDay;
	}

	/**
	 * Gets maximum amount of fine
	 *
	 * @return max fine
	 */
	public static double getFineMax() {
		return fineMax;
	}

}