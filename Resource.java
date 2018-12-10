import java.sql.SQLException;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
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

	/**
	 * Instantiates a new resource.
	 *
	 * @param title the title
	 * @param year the year
	 * @param thumbnail the thumbnail
	 */
	public Resource(String title, int year, Thumbnail thumbnail) {
		this.title = title;
		this.year = year;
		if (this.getClass() == Book.class) {
			resourceID = "B" + nextID;
		} else if (this.getClass() == Laptop.class) {
			resourceID = "L" + nextID;
		} else if (this.getClass() == DVD.class) {
			resourceID = "D" + nextID;
		} else if (this.getClass() == Resource.class) {
			resourceID = "R" + nextID;
		}

		nextID++;

		this.queue = new RequestQueue(this.resourceID);
		this.thumbnail = thumbnail;
	}

	/**
	 * Instantiates a new resource.
	 *
	 * @param resourceID the resource ID
	 * @param title the title
	 * @param year the year
	 * @param thumbnail the thumbnail
	 * @param queue the queue
	 */
	public Resource(String resourceID, String title, int year, Thumbnail thumbnail, RequestQueue queue) {
		this.resourceID = resourceID;
		this.title = title;
		this.year = year;
		this.thumbnail = thumbnail;
		this.queue = queue;
	}

	/**
	 * Gets the resource ID.
	 *
	 * @return the resource ID
	 */
	public String getResourceID() {
		return resourceID;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {

	}

	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	// change in document
	public int getYear() {
		return year;
	}

	/**
	 * Sets the year.
	 *
	 * @param year the new year
	 */
	public void setYear(String year) {

	}

	/**
	 * Gets the thumbnail.
	 *
	 * @return the thumbnail
	 */
	public Thumbnail getThumbnail() {
		return thumbnail;
	}

	/**
	 * Sets the thumbnail.
	 *
	 * @param thumbnail the new thumbnail
	 */
	public void setThumbnail(Thumbnail thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * Gets the queue.
	 *
	 * @return the queue
	 */
	public RequestQueue getQueue() {
		return queue;
	}

	/**
	 * Sets the queue.
	 *
	 * @param queue the new queue
	 */
	public void setQueue(RequestQueue queue) {
		this.queue = queue;
	}

	/**
	 * Creates the copy.
	 *
	 * @param loanTime the loan time
	 * @throws SQLException the SQL exception
	 */
	public void createCopy(int loanTime) throws SQLException {
		Copy c = new Copy(resourceID, loanTime);
		new DatabaseRequest().addCopy(c);

	}

	/**
	 * View copies.
	 *
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<Copy> viewCopies() throws SQLException {
		return new DatabaseRequest().getCopies(resourceID);
	}

	/**
	 * Checks if is copy available.
	 *
	 * @return true, if is copy available
	 * @throws SQLException the SQL exception
	 */
	public boolean isCopyAvailable() throws SQLException {
		return new DatabaseRequest().checkAvailability(resourceID);
	}
}
