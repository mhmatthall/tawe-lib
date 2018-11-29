public class RequestQueue {
	private String resourceID;
	private Queue usersWaiting = new Queue();

	
	public RequestQueue(String resourceID) {
		this.resourceID = resourceID;//fok is this?
	}
	public String getResourceID() {
		return resourceID;
	}
	public void addUser(String username) {
		usersWaiting.enqueue(username);
	}
	public void reserveCopy() {
		//wuh?
	}
	public boolean isEmpty() {
		return usersWaiting.isEmpty();
	}
	
}
