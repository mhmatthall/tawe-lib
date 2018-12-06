import java.util.ArrayList;

/*
 * TODO Complete setter methods
 */
public class DVD extends Resource {
	protected static double fineDay = 2.00;
	protected static double fineMax = 25.00;
	private String director;
	private int runtime;
	private String language;
	private ArrayList<String> subLang = new ArrayList<String>();
	
	public DVD(String title, int year, Thumbnail thumbnail, RequestQueue queue, String director, int runtime, String language) {
		super(title, year, thumbnail);
		this.director = director;
		this.language = language;
		this.runtime = runtime;
	}
	
	public DVD(String resourceID, String title, int year, Thumbnail thumbnail, RequestQueue queue, String director, int runtime, String language, ArrayList<String> subLang) {
		super(resourceID, title, year, thumbnail, queue);
		this.director = director;
		this.language = language;
		this.runtime = runtime;
		this.subLang = subLang;
	}
	
	public String toString() {	
		String x;
		x = ("Title: " + title + " \nYear: " + year + " \nDirector: " + director + " \nRuntime: " + runtime + " \nLanguage: " + language);
		return x;
	}
	
	public String getDirector() {
		return director;
	}
	
	public int getRuntime() {
		return runtime;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public ArrayList<String> getSubLang() {
		return subLang;
	}
	
	public void setDirector(String director) {
		
	}
	
	public void setRuntime(int runtime) {
		
	}
	
	public void setLanguage(String language) {
		
	}
	
	public void setSubLang(ArrayList<String> SubLang) {
		
	}
}