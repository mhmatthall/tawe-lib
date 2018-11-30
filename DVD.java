import java.util.ArrayList;

public class DVD extends Resource {
	protected static int fineDay;
	protected static int fineMax;
	private String director;
	private int runtime;
	private String language;
	ArrayList<String> SubLang = new ArrayList<String>();
	
	public DVD(String title, int year, Thumbnail thumbnail,String director, int runtime, String language, ArrayList<String> SubLang) {
		
		this.director = director;
		this.language = language;
		this.runtime = runtime;
		this.SubLang = SubLang;
		
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
		return SubLang;
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