/**
 * Class for displaying avatars of a user
 * 
 * @author Caleb Warburton
 * @version 1.0.0
 */

public class UserImage extends MyImage {
	private String defaultFilename = "image_files//prof1.png";

	/**
	 * initialises a new user image.
	 *
	 * @param fileName file's path as a string
	 */
	public UserImage(String fileName) {
		super(fileName);
	}

}