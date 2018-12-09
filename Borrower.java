import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The Class Borrower.
 * Modules borrowers for the library system
 * @author Rimantas Kazlauskas
 */
public class Borrower extends User {
	
	/** The balance of borrower. */
	private double balance;
	
	
	/**
	 * Used for creating new borrower.
	 * Also used by DatabaseRequest for fetching it from the database
	 *
	 * @param username the username
	 * @param forename the forename
	 * @param surname the surname
	 * @param phoneNumber the phone number
	 * @param address the address
	 * @param profileImage the profile image
	 * @param balance the balance
	 */
	public Borrower(String username, String forename, String surname, 
			String phoneNumber, String address, UserImage profileImage,
			double balance) {
		super(username, forename, surname, phoneNumber, address, profileImage);
		this.balance = balance;
		this.isLibrarian = false;
	}

	/**
	 * Gets the array list of loaned copies.
	 * TODO re-add commented out return
	 * @return the loaned resources
	 * @throws SQLException 
	 */
	public ArrayList<Copy> getLoanedCopies() throws SQLException {
		DatabaseRequest db = new DatabaseRequest();
		return null; //db.getUserLoans(getUsername());
	}
	
	/**
	 * Gets the array list of reserved resources.
	 *
	 * @return the reserved resources
	 */
	public ArrayList<Copy> getReservedResources(){
		// get reserved resources by the current user in a similar way to getLoanedResources
		// however Resources not Copies as we're not sure which copy is reserved yet
		return null;
	}
	
	/**
	 * Gets the balance of borrower.
	 * Represents fines to be paid, should be negative or 0
	 *
	 * @return balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * Sets the balance.
	 *
	 * @param amount the new balance
	 */
	public void setBalance(double amount) {
		balance += amount;
	}
	
	/* 
	 * Human readable toString
	 */
	public String toString() {
		String result;
		result = "User ID: " + this.getUsername();
		result += "\nFirst Name: " + this.getForename();
		result += "\nLast Name: " + this.getSurname();
		result += "\nPhone Number: " + this.getPhoneNumber();
		result += "\nAddress: " + this.getAddress();
		result += "\nBalance: " + this.balance;			
		return result;
	}
}
