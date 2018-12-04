import java.util.ArrayList;

public class Borrower extends User {
	private double balance;
	
	public Borrower(String username, String forename, String surname, String phoneNumber, String address,
			UserImage profileImage, double balance) {
		super(username, forename, surname, phoneNumber, address, profileImage);
		this.balance = balance;
	}
	
	public void requestResource(String resourceID) {
		//void? and huh?
	}
	//Array or arrayLists? cause we will know how many copies the guy has, right?
	public ArrayList<Copy> getLoanedResources() {
		//checking resources in possesion?
		return null;
	}
	public ArrayList<Resource> getReservedResources(){
		//get reserves
	}
	public double getBalance() {
		return balance;
	}
	//Transaction class ain't a thing and we not keeping history on that
	public void setBalance(double amount) {
		balance += amount;
		//should borrower have this? since it should go thro librarian
	}
	public void requestReservedItem(String resourceID) {
		//ya wot? if its reserved, ya dont need to request? or is this checking?
	}
	public String toString() {
		return "filler";
	}
}
