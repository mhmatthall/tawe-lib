
public class Resource {
	private static int counter = 0; 
	private final int nextID;
	private String resourceID;
	private String title;
	private int year;
	private Thumbnail thumbnail;
	private RequestQueue queue;
	
	
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
	
	public void setTitle(String title) {
		
	}
	
	// change in document
	public int getYear() {
		return year;
	}
	
	public void setYear(String year) {
		
	}
	
	public Thumbnail getThumbnail() {
		return thumbnail;
	}
	
	public void setThumbnail(Thumbnail thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public RequestQueue getQueue() {
		return queue;
	}
	
	public void setQueue(RequestQueue queue) {
		this.queue = queue;
	}
}
