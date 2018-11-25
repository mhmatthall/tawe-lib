import java.util.ArrayList;

public class RequestQueue {
	private String resourceID;
	private ArrayList<String> usersWaiting = new ArrayList<String>();
	//mby we should use an actual queue/linked lists for this?
	
	public RequestQueue(String resourceID) {
		this.resourceID = resourceID;
	}
	public String getResourceID() {
		return resourceID;
	}
	public void addUser(String username) {
		usersWaiting.add(username);
	}
	public void reserveCopy() {
		//wuh?
	}
	public boolean isEmpty() {
		return true;
	}
	
}
