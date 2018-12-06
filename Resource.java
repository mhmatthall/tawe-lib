/*
 * TODO add missing methods defined in UML diagram
 * TODO fix missing setters
 */
public class Resource { 
	private static int nextID = 1;
	private String resourceID;
	protected String title;
	protected int year;
	protected Thumbnail thumbnail;
	protected RequestQueue queue;
	
	public Resource (String title, int year, Thumbnail thumbnail) {
		this.title = title;
		this.year = year;
		
		if (this.getClass() == Book.class) {
			resourceID = "B" + nextID;
		} else if (this.getClass() == Laptop.class) {
			resourceID = "L" + nextID;
		} else if (this.getClass() == DVD.class) {
			resourceID = "D" + nextID;
		}else if (this.getClass() == Resource.class) {
			resourceID = "R" + nextID;
		}
		nextID++;
		this.queue = new RequestQueue(this.resourceID);
		
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
