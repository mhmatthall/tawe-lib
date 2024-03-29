import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Models resources in library, acts as a super class for book, dvd and laptop
 * @author Ben Rochford
 *
 */
public class Resource {
	private static int nextID = 1;
	private String resourceID;
	protected String title;
	protected int year;
	protected Thumbnail thumbnail;
	protected RequestQueue queue;

	/**
	 * creates a new resource for database
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
	 * Initialises resource object from the database by databaseRequest
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
		this.title = title;
	}

	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Sets the year.
	 *
	 * @param year the new year
	 */
	public void setYear(int year) {
		this.year = year;
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
	 * Creates the copy for this resource.
	 *
	 * @param loanTime the loan time
	 * @throws SQLException cannot connect to the Database
	 */
	public void createCopy(int loanTime) throws SQLException {
		Copy c = new Copy(resourceID, loanTime);
		new DatabaseRequest().addCopy(c);

	}

	/**
	 * View copies.
	 *
	 * @return the array list
	 * @throws SQLException cannot connect to the Database
	 */
	public ArrayList<Copy> viewCopies() throws SQLException {
		return new DatabaseRequest().getCopies(resourceID);
	}

	/**
	 * Checks if is copy available.
	 *
	 * @return true, if is copy available
	 * @throws SQLException if connection to database fails
	 */
	public boolean isCopyAvailable() throws SQLException {
		return new DatabaseRequest().checkAvailability(resourceID);
	}
}
