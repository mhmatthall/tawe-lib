import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Matt Hall
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
		Copy c = db.getAvailableCopies(resourceID).get(0);

		Loan l = new Loan(c.getCopyID(), username);

		db.addLoan(l);

	}

	/**
	 * Return loan.
	 *
	 * @param loan to be returned
	 * @throws SQLException if connection to database has failed
	 */
	public void returnLoan(Loan loan) throws SQLException {

		DatabaseRequest db = new DatabaseRequest();
		Copy c = db.getCopy(loan.getCopyID());
		Resource r = db.getResource(c.getResourceID());

		if ((new Date()).isBefore(loan.getReturnDate())) {
			Fine f = new Fine(loan.getLoanID());
			db.addFine(f);
		}

		loan.setLoanStatus(false);

		if (r.getQueue().isEmpty()) {
			loan.returnResource();
		} else {
			reserveResource(r.getResourceID(), r.getQueue().peek());
			r.getQueue().dequeue();
		}

		db.editResource(r);
		db.editLoan(loan);
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
			throw new IllegalArgumentException("Cannot pay less than �" + f.getMinimumPayment());
		else if (f.getAmountPaid() + amount > f.getAmount()) {
			throw new IllegalArgumentException("Cant pay more than the total fine amount");
		}

		f.setAmountPaid(f.getAmountPaid() + amount);

		db.editFine(f);

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

}
