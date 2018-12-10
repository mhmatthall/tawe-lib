import java.util.NoSuchElementException;

/**
 * Queue ADT implamented, stores usernames
 * @author Rimantas Kazlauskas
 *
 */
public class RequestQueue {
	private QueueElement first;
	private String resourceID = null;

	/**
	 * Constructs an empty Queue with resource assicated with it.
	 *
	 * @param resourceID 
	 */
	public RequestQueue(String resourceID) {
		this.resourceID = resourceID;
		first = new QueueElement(null, new QueueElement(null, null));
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
	 * Checks if Queue is empty.
	 *
	 * @return boolean indicating whatever queue is empty
	 */
	public boolean isEmpty() {
		return first.getElement() == null;
	}

	/**
	 * Peek.
	 *
	 * @return 1st element in queue
	 */
	public String peek() {
		if (first.getElement() == null) {
			throw new NoSuchElementException("Queue is empty");
		} else {
			return first.getElement();
		}
	}

	/**
	 * Dequeue.
	 */
	public void dequeue() {
		if (first.getElement() == null) {
			throw new NoSuchElementException("No such element exists to dequeue");
		} else {
			first = first.getNext();
		}
	}

	/**
	 * Adds the user.
	 *
	 * @param element puts object into Q
	 */
	public void addUser(String element) {
		QueueElement newElement = new QueueElement(element, new QueueElement(null, null));
		QueueElement temp;

		if (first.getNext().getElement() != null) {
			temp = first.getNext();
			while (temp.getNext().getElement() != null) {
				temp = temp.getNext();
			}
			temp.setNext(newElement);
		} else if (first.getElement() == null) {
			first.setElement(element);
		} else if (first.getNext().getElement() == null) {
			first.setNext(newElement);
		}
	}

	/**
	 * human readable, effectively to string
	 */
	public void print() {
		String r = "The queue is empty\n";
		if (first.getElement() != null) {
			r = first.getElement().toString() + "\n";
			if (first.getNext().getElement() != null) {
				QueueElement temp = first.getNext();
				r += first.getNext().getElement().toString() + "\n";
				while (temp.getNext().getElement() != null) {
					r += temp.getNext().getElement().toString() + "\n";
					temp = temp.getNext();
				}
			}

		}
		System.out.print(r);
	}

	/**
	 * Database readable toString()
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String r = null;
		if (first.getElement() != null) {
			r = first.getElement().toString();
			if (first.getNext().getElement() != null) {
				QueueElement temp = first.getNext();
				r += "," + first.getNext().getElement().toString();
				while (temp.getNext().getElement() != null) {
					r += "," + temp.getNext().getElement().toString();
					temp = temp.getNext();
				}
			}

		}
		return r;
	}

	/**
	 * Sets the resource ID.
	 *
	 * @param resourceId the new resource ID
	 */
	public void setResourceID(String resourceId) {
		if (resourceID == null) {
			this.resourceID = resourceId;
		}
	}
}