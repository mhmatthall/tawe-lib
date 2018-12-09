// TODO: Auto-generated Javadoc
/**
 * The Class Copy.
 * Models copy of a resource 
 * @author Constantinos Loizou
 * 
 */
public class Copy {
	
	private static int nextCopyID;
	private String copyID;
	private String resourceID;
	private int loanTime;
	private boolean isOnLoan;
	private boolean isReserved;
	private String reservingUser;	// Username of user reserving the copy
	
	/**
	 * Used for creating a new copy
	 *
	 * @param resourceID the resource ID
	 * @param loanTime the loan time
	 */
	public Copy(String resourceID, int loanTime) {
		this.resourceID = resourceID;
		this.loanTime = loanTime;
		isOnLoan = false;
		isReserved = false;
		reservingUser = "";
		copyID = ("C" + nextCopyID);
		nextCopyID++;
	}
	
	/**
	 * Used for fetching a copy by DatabaseRequest from database
	 *
	 * @param copyID the copy ID
	 * @param resourceID the resource ID
	 * @param loanTime the loan time in days
	 * @param isOnLoan the is on loan
	 * @param isReserved the is reserved
	 * @param reservingUser the reserving user
	 */
	public Copy(String copyID, String resourceID, int loanTime, 
			boolean isOnLoan, boolean isReserved, String reservingUser) {
		this.copyID = copyID;
		this.resourceID = resourceID;
		this.loanTime = loanTime;
		this.isOnLoan = isOnLoan;
		this.isReserved = isReserved;
		this.reservingUser = reservingUser;
	}
	
	/**
	 * Checks if copy is loaned.
	 *
	 * @return true, if copy is loaned
	 */
	public boolean isOnLoan() {
		return isOnLoan;
	}

	/**
	 * Sets loan status
	 *
	 * @param isOnLoan True if its loaned, false otherwise.
	 */
	public void setOnLoan(boolean isOnLoan) {
		this.isOnLoan = isOnLoan;
	}

	/**
	 * Checks if copy is reserved.
	 *
	 * @return true if reserved
	 */
	public boolean isReserved() {
		return isReserved;
	}

	/**
	 * Sets reservation status
	 *
	 * @param isReserved True if its reserved, false otherwise
	 */
	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}

	/**
	 * Gets the reserving user.
	 *
	 * @return the reserving user
	 */
	public String getReservingUser() {
		return reservingUser;
	}

	/**
	 * Sets the reserving user.
	 *
	 * @param reservingUser the new reserving user
	 */
	public void setReservingUser(String reservingUser) {
		this.reservingUser = reservingUser;
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
	 * Gets the resource ID.
	 *
	 * @return the resource ID
	 */
	public String getResourceID() {
		return resourceID;
	}

	/**
	 * Gets the loan
	 *
	 * @return the loan time in days
	 */
	public int getLoanTime() {
		return loanTime;
	}

	/**
	 * Sets the loan time in days.
	 *
	 * @param loanTime the loan time in days
	 */
	public void setLoanTime(int loanTime) {
		this.loanTime = loanTime;	
		}
	

	
}
