/**
 * element of a queue, needed to make queue ADT work.
 * @author Rimantas Kazlauskas
 *
 */
public class QueueElement {
	private String element; // the element contained in this linked list
	private QueueElement next; // the next element of the linked list

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