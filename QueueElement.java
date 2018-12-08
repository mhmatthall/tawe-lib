/**
 * @author Rimantas
 *
 */

/*
 * TODO conform to coding style
 * Why does this class exist? We should only be keeping usernames in the RequestQueue
 */
public class QueueElement {
	private String element; //the element contained in this linked list
	private QueueElement next; //the next element of the linked list
	
	public QueueElement(String e, QueueElement n) {
		this.element = e;
		this.next = n;
	}
	
	/**
	 * Method to set the element
	 */
	public void setElement(String element) {
		this.element = element;
	}
	
	/**
	 * Method to set the next linked list element
	 */
	public void setNext(QueueElement e) {
		this.next = e;
	}
	
	
	/**
	 * @return element
	 */
	public String getElement() {
		return element;
	}
	

	/**
	 * @return next element in linked list
	 */
	public QueueElement getNext() {
		return next;
	}
	
}