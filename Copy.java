/*
 * @author
 * 		Constantinos Loizou
 */
public class Copy {
	
	private static int nextCopyID;
	
	private String copyID = ("C" + nextCopyID);
	private String resourceID;
	private int loanTime;
	
	public Copy(String resourceID, int loanTime) {
		this.resourceID = resourceID;
		this.loanTime = loanTime;
		nextCopyID++;
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
