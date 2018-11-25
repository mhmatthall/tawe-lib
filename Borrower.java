public class Borrower {
	private double balance; //mby float?
	
	public void requestResource(String resourceID) {
		//void? and huh?
	}
	//Array or arrayLists? cause we will know how many copies the guy has, right?
	public ArrayList<Copy> getLoanedResources() {
		//checking resources in possesion?
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
	public void displayStatistics() {
		//return type and how do i structure them?
	}
	public String toString() {
		return "filler";
	}
}
