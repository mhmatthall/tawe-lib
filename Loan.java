import java.sql.SQLException;

/*
 * TODO fix double constructor
 * TODO add missing UML methods 
 */

public class Loan {
	private static int nextLoanID;
	private String loanID = ("L" + nextLoanID);
	private String copyID;
	private Date issueDate;
	private String username;
	private Date returnDate;
	private boolean isReturned;


 
	//creating new loan
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
	//db constructor
	public Loan(String loanID, Date issueDate, String username, String copyID, 
			Date returnDate, boolean isReturned) {
		this.loanID = loanID;
		this.issueDate = issueDate;
		this.username = username;
		this.copyID = copyID;
		this.returnDate = returnDate;
		this.isReturned = isReturned;
	}
	
	public String getLoanID() {
		return loanID;
	}
	public boolean isReturned() {
		return isReturned;
	}
	public void returnResource() {
		isReturned = true;
	}

	public void setLoanID(String loanID) {
		this.loanID = loanID;
	}

	public String getCopyID() {
		return copyID;
	}

	public void setCopyID(String copyID) {
		this.copyID = copyID;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public void setLoanStatus(Boolean isOnLoan) throws SQLException {
		Copy c = new DatabaseRequest().getCopy(copyID);
		c.setOnLoan(isOnLoan);
		new DatabaseRequest().editCopy(c);
	}
	
	public void setReservationStatus(Boolean isReserved) throws SQLException{
		Copy c = new DatabaseRequest().getCopy(copyID);
		c.setReserved(isReserved);
		new DatabaseRequest().editCopy(c);
		
	}
}
