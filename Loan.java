
public class Loan {
	private static int nextLoanID;
	private String LoanID = ("L" + nextLoanID);
	private String copyID;
	private Date issueDate;
	private String username;
	private Date returnDate;
	
	public Loan() {
		// TOD
	}

	public Loan(String copyID, Date issueDate, String username, Date returnDate) {
		this.copyID = copyID;
		this.issueDate = issueDate;
		this.username = username;
		this.returnDate = returnDate;
		nextLoanID++;
	}

	public String getLoanID() {
		return LoanID;
	}

	public void setLoanID(String loanID) {
		LoanID = loanID;
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
