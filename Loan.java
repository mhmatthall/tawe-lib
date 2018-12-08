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
	private DatabaseRequest db = new DatabaseRequest();
 
	//creating new loan
	public Loan(String copyID, String username) {
		
		this.copyID = copyID;
		this.issueDate = new Date();
		this.username = username;
		this.isReturned = false;
		this.returnDate = issueDate.forwardDate(db.getCopy(copyID).getLoanTime());
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
	public boolean getReturnStatus() {
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

}
