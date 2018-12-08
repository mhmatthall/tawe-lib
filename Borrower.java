import java.util.ArrayList;

public class Borrower extends User {
	private double balance;
	
	
	public Borrower(String username, String forename, String surname, 
			String phoneNumber, String address, UserImage profileImage,
			double balance) {
		super(username, forename, surname, phoneNumber, address, profileImage);
		this.balance = balance;
		this.isLibrarian = false;
	}
	
	public void requestResource(String resourceID) {
		
		// this method should request a copy of the given resource by either:
		//			+ adding their name to the request queue
		//			+ asking resource to get a list of the copies and reserve one copy
	}

	public ArrayList<Copy> getLoanedResources() {
		// This will return an arraylist, as there is no fixed amount of loaned items
		// This should use a DBReq to return a list of the copies that the user currently has on loan
		return null;
	}
	
	public ArrayList<Resource> getReservedResources(){
		// get reserved resources by the current user in a similar way to getLoanedResources
		// however Resources not Copies as we're not sure which copy is reserved yet
		return null;
	}
	public double getBalance() {
		return balance;
	}

	public void setBalance(double amount) {
		balance += amount;
		// Needed here so librarian dashboard can simply go 'userX.setBalance(5.00)' or whatever
		// rather than librarian.setBalance(userID, amount) - less elegant
	}
	
	public String toString() {
		String result;
		result = "User ID: " + this.getUsername();
		result += "\nFirst Name: " + this.getForename();
		result += "\nLast Name: " + this.getSurname();
		result += "\nPhone Number: " + this.getPhoneNumber();
		result += "\nAddress: " + this.getAddress();
		result += "\nBalance: " + this.balance;			
		return result;
	}
}
