import java.sql.SQLException;


/**
 * Models Loan in a library
 * 
 * @author konulv
 *
 */
/*
 */

public class Loan {
	private static int nextLoanID;
	private String loanID = ("L" + nextLoanID);
	private String copyID;
	private Date issueDate;
	private String username;
	private Date returnDate;
	private boolean isReturned;

	/**
	 * creates a new loan
	 *
	 * @param copyID of a copy to be loaned
	 * @param username of a user who is getting the loan
	 * @throws SQLException if connection to database fails
	 */
	public Loan(String copyID, String username) throws SQLException {
		DatabaseRequest db = new DatabaseRequest();
		this.copyID = copyID;
		this.issueDate = new Date();
		this.username = username;
		this.isReturned = false;
		this.returnDate = issueDate;
		this.returnDate.forwardDate(db.getCopy(copyID).getLoanTime());
		nextLoanID++;
	}

	/**
	 * Used by databaseRequest for getting a loan from database
	 *
	 * @param loanID the loan ID
	 * @param issueDate the issue date
	 * @param username the username
	 * @param copyID the copy ID
	 * @param returnDate the return date
	 * @param isReturned the is returned
	 */
	public Loan(String loanID, Date issueDate, String username, String copyID, 
			Date returnDate, boolean isReturned) {
		this.loanID = loanID;
		this.issueDate = issueDate;
		this.username = username;
		this.copyID = copyID;
		this.returnDate = returnDate;
		this.isReturned = isReturned;
	}

	/**
	 * Gets the loan ID.
	 *
	 * @return loan ID
	 */
	public String getLoanID() {
		return loanID;
	}

	/**
	 * Checks if loan is returned.
	 *
	 * @return true, if  returned
	 */
	public boolean isReturned() {
		return isReturned;
	}

	/**
	 * Return resource.
	 */
	public void returnResource() {
		isReturned = true;
	}

	/**
	 * Sets the loan ID.
	 *
	 * @param loanID the new loan ID
	 */
	public void setLoanID(String loanID) {
		this.loanID = loanID;
	}

	/**
	 * Gets the copy ID.
	 *
	 * @return the copy ID
	 */
	public String getCopyID() {
		return copyID;
	}

	/**
	 * Sets the copy ID.
	 *
	 * @param copyID the new copy ID
	 */
	public void setCopyID(String copyID) {
		this.copyID = copyID;
	}

	/**
	 * Gets the issue date.
	 *
	 * @return issue date
	 */
	public Date getIssueDate() {
		return issueDate;
	}

	/**
	 * Sets the issue date.
	 *
	 * @param issueDate 
	 */
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * Gets the username.
	 *
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the return date.
	 *
	 * @return the return date
	 */
	public Date getReturnDate() {
		return returnDate;
	}

	/**
	 * Sets the return date.
	 *
	 * @param returnDate the new return date
	 */
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	/**
	 * Sets the loan status.
	 *
	 * @param isOnLoan the new loan status
	 * @throws SQLException if cannot connect to Database
	 */
	public void setLoanStatus(boolean isOnLoan) throws SQLException {
		Copy c = new DatabaseRequest().getCopy(copyID);
		c.setOnLoan(isOnLoan);
		new DatabaseRequest().editCopy(c);
	}

	/**
	 * Sets the reservation status.
	 *
	 * @param isReserved the is reserved
	 * @param newUsername the new username
	 * @throws SQLException if cannot connect to Database
	 */
	public void setReservationStatus(boolean isReserved, String newUsername) throws SQLException {
		Copy c = new DatabaseRequest().getCopy(copyID);
		c.setReserved(isReserved);
		c.setReservingUser(newUsername);
		new DatabaseRequest().editCopy(c);

	}

}
