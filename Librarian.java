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
			throw new IllegalArgumentException("Cannot pay less than £" + f.getMinimumPayment());
		else if (f.getAmountPaid() + amount > f.getAmount()) {
			throw new IllegalArgumentException("Cant pay more than the total fine amount");
		}

		f.setAmountPaid(f.getAmountPaid() + amount);

		// save the fine in the database
		db.editFine(f);

	}


	/**
	 * Checks whether a resource is available, then either reserves a resource or
	 * adds the user to the request queue
	 *
	 * @param resourceID to be requested
	 * @param username of a user that is requesting
	 * @throws SQLException if the connection to database has failed
	 */
	public void requestResource(String resourceID, String username) throws SQLException {
		DatabaseRequest db = new DatabaseRequest();

		if (db.checkAvailability(resourceID)) {
			// if there are copies that can be reserved for the given resource
			reserveResource(resourceID, username);
		} else {
			// if there are no copies that can be reserved for the given resource
			
			// then get the resource and our username to the request queue
			Resource r = db.getResource(resourceID);
			r.getQueue().addUser(username);

			// save the resource in the database
			db.editResource(r);
		}
	}

	/**
	 * Reserves a resource for a user, marks a copy as reserved and sets a return date
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
			// if there are no available copies for the given resource
			
			// reserve the oldest loan
			Loan l = db.getOldestLoan(resourceID);
			l.setReservationStatus(true, username);

			// set the return date for the user with the oldest loan
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
			// there are copies available
			
			// get one copy and set it as reserved in our name
			c = copies.get(0);
			c.setReserved(true);
			c.setReservingUser(username);
		}

		// update the database
		db.editCopy(c);
	}
}
