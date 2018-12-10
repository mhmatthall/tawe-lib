/**
 * A super class for userImages and thumbnails
 * Holds a path to an image
 * @author Caleb Warburton
 *
 */
public class MyImage {
	private String defaultImage = "";
	private String filename;

	/**
	 * Instantiates a new my image.
	 *
	 * @param filename the filename
	 */
	public MyImage(String filename) {
		if (filename == null) {
			this.filename = defaultImage;
		} else {
			this.filename = filename;
		}
	}

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public String getImage() {
		return this.filename;
	}

	/**
	 * Sets the image.
	 *
	 * @param filename the new image
	 */
	public void setImage(String filename) {
		this.filename = filename;
	}

}