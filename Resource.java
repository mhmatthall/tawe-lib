
public class Resource {
	private static int counter = 0; 
	private final int nextID;
	private String resourceID;
	private String title;
	private int year;
	private Thumbnail thumbnail;
	private queue RequestQueue;
	
	
	public Resource (String title, int year, Thumbnail thumbnail) {
		this.title = title;
		this.year = year;
		this.nextID = counter++;
	}
	
	public String getResourceID() {
		return resourceID;
	}
	
	public String getTitle() {
		return title;
	}
	
	// change in document
	public int getYear() {
		return year;
	}
	
	public void setTitle(String title) {	
	}
	
	public void setYear(String year) {
		
	}
	
	

}
