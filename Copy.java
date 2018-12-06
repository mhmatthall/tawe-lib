/*
 * @author Constantinos Loizou
 * 
 * TODO add missing variables
 * TODO fix constructor to add missing variables
 */
public class Copy {
	private static int nextCopyID;
	private String copyID = ("C" + nextCopyID);
	private String resourceID;
	private int loanTime;
	private boolean isOnLoan;
	private boolean isReserved;
	private String reservingUser;	// Username of user reserving the copy
	
	public Copy(String resourceID, int loanTime) {
		this.resourceID = resourceID;
		this.loanTime = loanTime;
		isOnLoan = false;
		isReserved = false;
		reservingUser = "";
		nextCopyID++;
	}
	
	public Copy(String copyID, String resourceID, int loanTime, boolean isOnLoan, boolean isReserved, String reservingUser) {
		// This constructor required for loading copies from the database
		this.copyID = copyID;
		this.resourceID = resourceID;
		this.loanTime = loanTime;
		this.isOnLoan = isOnLoan;
		this.isReserved = isReserved;
		this.reservingUser = reservingUser;
	}
	
	public boolean isOnLoan() {
		return isOnLoan;
	}

	public void setOnLoan(boolean isOnLoan) {
		this.isOnLoan = isOnLoan;
	}

	public boolean isReserved() {
		return isReserved;
	}

	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}

	public String getReservingUser() {
		return reservingUser;
	}

	public void setReservingUser(String reservingUser) {
		this.reservingUser = reservingUser;
	}

	public String getCopyID() {
		return copyID;
	}

	public void setCopyID(String copyID) {
		this.copyID = copyID;
	}

	public String getResourceID() {
		return resourceID;
	}

	public void setResourceID(String resourceID) {
		this.resourceID = resourceID;
	}

	public int getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(int loanTime) {
		this.loanTime = loanTime;
	}	
}
