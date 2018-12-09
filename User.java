import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for modelling User, mainly used as a sort-of-abstract class and is a
 * super class for Librarian and Borrower
 * 
 * @author Caleb Warburton
 */
/*
 * TODO add missing UML methods is this still needed?
 */
public class User {
	private String username;
	private String forename;
	private String surname;
	private String phoneNumber;
	private String address;
	private UserImage profileImage;
	protected boolean isLibrarian;

	/**
	 * Instantiates a new user.
	 *
	 * @param username     the username
	 * @param forename     the forename
	 * @param surname      the surname
	 * @param phoneNumber  the phone number
	 * @param address      the address
	 * @param profileImage the profile image
	 */
	public User(String username, String forename, String surname, String phoneNumber, String address,
			UserImage profileImage) {
		this.username = username;
		this.forename = forename;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.profileImage = profileImage;
	}

	/**
	 * Gets the surname.
	 *
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Gets the forename.
	 *
	 * @return the forename
	 */
	public String getForename() {
		return forename;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the phone number.
	 *
	 * @return the phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Gets the profile image.
	 *
	 * @return the profile image
	 */
	public UserImage getProfileImage() {
		return profileImage;
	}

	/**
	 * To string old.
	 *
	 * @return the string
	 */
	public String toStringOld() {
		String x;
		x = ("UserName: " + username + "\nForename: " + forename + "\nSurname: " + surname + "\nPhone Number: "
				+ phoneNumber + "\nAddress: " + address);
		return x;
	}

	/**
	 * Checks if user is librarian.
	 *
	 * @return true, if it is librarian
	 */
	public boolean isLibrarian() {
		return isLibrarian;
	}

	/**
	 * human readable
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String result;
		result = "User ID: " + this.username;
		result += "\nFirst Name; " + this.forename;
		result += "\nLast Name; " + this.surname;
		result += "\nPhone Number; " + this.phoneNumber;
		result += "\nAddress; " + this.address;
		if (this.isLibrarian) {
			result += "\nAccount Type: LIBRARIAN";
		} else {
			result += "\nAccount Type: BORROWER";
		}

		return result;
	}

	/**
	 * Sets the profile image.
	 *
	 * @param profileImage Image to be set as an avatar
	 */
	public void setProfileImage(UserImage profileImage) {
		this.profileImage = profileImage;
	}
}