// TODO: Auto-generated Javadoc
/**
 * The Class Copy.
 * Models copy of a resource 
 * @author Constantinos Loizou
 * 
 */
public class Copy {
	
	/** The next copy ID. Used for assigning IDs */
	private static int nextCopyID;
	
	/** The copy ID. */
	private String copyID = ("C" + nextCopyID);
	
	/** The resource ID. */
	private String resourceID;
	
	/** The loan time in days */
	private int loanTime;
	
	/** The is on loan. */
	private boolean isOnLoan;
	
	/** The is reserved. */
	private boolean isReserved;
	
	/** The reserving user. */
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
	 * Sets the on loan.
	 *
	 * @param isOnLoan the new on loan
	 */
	public void setOnLoan(boolean isOnLoan) {
		this.isOnLoan = isOnLoan;
	}

	/**
	 * Checks if is reserved.
	 *
	 * @return true, if is reserved
	 */
	public boolean isReserved() {
		return isReserved;
	}

	/**
	 * Sets the reserved.
	 *
	 * @param isReserved the new reserved
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
	 * Sets the copy ID.
	 *
	 * @param copyID the new copy ID
	 */
	public void setCopyID(String copyID) {
		this.copyID = copyID;
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
	 * Sets the resource ID.
	 *
	 * @param resourceID the new resource ID
	 */
	public void setResourceID(String resourceID) {
		this.resourceID = resourceID;
	}

	/**
	 * Gets the loan time.
	 *
	 * @return the loan time
	 */
	public int getLoanTime() {
		return loanTime;
	}

	/**
	 * Sets the loan time.
	 *
	 * @param loanTime the new loan time
	 */
	public void setLoanTime(int loanTime) {
		this.loanTime = loanTime;	
		}
	

	
}
