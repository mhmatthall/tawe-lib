import java.sql.SQLException;
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
	 * Sets the profile image.
	 *
	 * @param profileImage Image to be set as an avatar
	 */
	public void setProfileImage(UserImage profileImage) {
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
	 * Requests a resource.
	 *
	 * @param resourceID to be requested
	 * @param username of a user that is requesting
	 * @throws SQLException if the connection to database has failed
	 */
	public void requestResource(String resourceID, String username) throws SQLException {

		DatabaseRequest db = new DatabaseRequest();

		if (db.checkAvailability(resourceID)) {
			reserveResource(resourceID, username);

		} else {

			Resource r = db.getResource(resourceID);
			r.getQueue().addUser(username);

			db.editResource(r);

		}
	}

	/**
	 * Reserve resource.
	 *
	 * @param resourceID of a resource to be reserved
	 * @param username of a user who is reserving
	 * @throws SQLException if connection to the database has failed
	 */
	public void reserveResource(String resourceID, String username) throws SQLException {

		DatabaseRequest db = new DatabaseRequest();
		Copy c = null;
		ArrayList<Copy> copies = db.getAvailableCopies(resourceID);
		if (copies.isEmpty()) {

			Loan l = db.getOldestLoan(resourceID);
			l.setReservationStatus(true, username);

			c = db.getCopy(l.getCopyID());
			int loanLength = c.getLoanTime();
			c.setReservingUser(username);
			c.setReserved(true);

			Date d = l.getIssueDate();
			d.forwardDate(loanLength);

			if (d.isBefore(new Date())) {
				Date tomorrow = new Date();
				tomorrow.forwardDate(1);
				l.setReturnDate(tomorrow);
			} else {
				Date dueDate = l.getIssueDate();
				dueDate.forwardDate(loanLength);

			}
		} else {
			c = copies.get(0);
			c.setReserved(true);
			c.setReservingUser(username);
		}

		db.editCopy(c);

	}
	
	/**
	 * Return a user's loan and free up the copy in the system
	 *
	 * @param loan to be returned
	 * @throws SQLException if connection to database has failed
	 */
	public void returnLoan(Loan loan) throws SQLException {
		DatabaseRequest db = new DatabaseRequest();
		Copy c = db.getCopy(loan.getCopyID());
		Resource r = db.getResource(c.getResourceID());

		// if the loan is overdue
		if ((new Date()).isBefore(loan.getReturnDate())) {
			// fine the user
			Fine f = new Fine(loan.getLoanID());
			// save the fine in the database
			db.addFine(f);
		}

		// set the copy as no longer on loan
		loan.setLoanStatus(false);

		// if there isn't anyone waiting for the resource
		if (r.getQueue().isEmpty()) {
			// set the loan as returned (therefore complete)
			loan.returnResource();
		} else {
			// reserve the resource for the user next in the resource's request queue
			reserveResource(r.getResourceID(), r.getQueue().peek());
			// then remove them from the queue
			r.getQueue().dequeue();
		}

		// update the resource and loan in the database
		db.editResource(r);
		db.editLoan(loan);
	}
	
}