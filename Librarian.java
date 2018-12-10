import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Matt Hall, Ben Rochford
 */
public class Librarian extends User {
	protected int staffNumber;
	protected Date employmentDate;

	/**
	 * Used for creating a new librarian
	 *
	 * @param username of the librarian
	 * @param forename of the librarian
	 * @param surname of the librarian
	 * @param phoneNumber of the librarian
	 * @param address of the librarian
	 * @param profileImage avatar of the librarian
	 * @param staffNumber of the librarian
	 * @param employmentDate of the librarian
	 */
	public Librarian(String username, String forename, String surname, String phoneNumber, 
			String address,	UserImage profileImage, int staffNumber, Date employmentDate) {
		super(username, forename, surname, phoneNumber, address, profileImage);
		this.staffNumber = staffNumber;
		this.employmentDate = employmentDate;
		this.isLibrarian = true;
	}

	/**
	 * Gets the staff number.
	 *
	 * @return the staff number
	 */
	public int getStaffNumber() {
		return staffNumber;
	}

	/**
	 * Sets the staff number.
	 *
	 * @param staffNumber to be set
	 */
	public void setStaffNumber(int staffNumber) {
		this.staffNumber = staffNumber;
	}

	/**
	 * Gets the employment date.
	 *
	 * @return the employment date
	 */
	public Date getEmploymentDate() {
		return employmentDate;
	}

	/**
	 * Sets the employment date.
	 *
	 * @param employmentDate to be changed to
	 */
	public void setEmploymentDate(Date employmentDate) {
		this.employmentDate = employmentDate;
	}

	/**
	 * Issues any available copy to a given user.
	 *
	 * @param resourceID to look up the copy for
	 * @param username of a borrower
	 * @throws SQLException if connection to the database has failed
	 */
	public void issueLoan(String resourceID, String username) throws SQLException {
		DatabaseRequest db = new DatabaseRequest();
		// gets the first available copy
		Copy c = db.getAvailableCopies(resourceID).get(0);

		// issue new loan
		Loan l = new Loan(c.getCopyID(), username);

		// save the new loan in the database
		db.addLoan(l);

	}

	/**
	 * Pay fine.
	 *
	 * @param fineID of a fine to be paid
	 * @param amount to be paid
	 * @throws SQLException if connection to database has failed
	 */
	public void payFine(String fineID, double amount) throws SQLException {
		DatabaseRequest db = new DatabaseRequest();
		Fine f = db.getFine(fineID);

		if (amount < f.getMinimumPayment())
			throw new IllegalArgumentException("Cannot pay less than " + f.getMinimumPayment());
		else if (f.getAmountPaid() + amount > f.getAmount()) {
			throw new IllegalArgumentException("Cant pay more than the total fine amount");
		}

		f.setAmountPaid(f.getAmountPaid() + amount);

		// save the fine in the database
		db.editFine(f);

	}
}
