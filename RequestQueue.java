import java.util.NoSuchElementException;

/*
 * What is this class doing? Isn't this what RequestQueue is supposed to do?
 */

/**
 * @author Rimantas
 *
 */
public class RequestQueue {
	private QueueElement first;
	private String resourceID;
	
	/**
	 * Constructs an empty Queue.
	 */
	public RequestQueue (String resourceID) {
		this.resourceID = resourceID;
	    first = new QueueElement(null, new QueueElement(null,null));
	}
	
	public String getResourceID() {
		return resourceID;
	}
	
	public void reserveCopy() {
		//wuh?
	}
	
	/**
	 * @return boolean indicating whatever queue is empty
	 */
	public boolean isEmpty () {
	    return first.getElement()== null;
	}
	

	/**
	 * @return 1st element in queue
	 * @throws NoSuchElementException why tho
	 */
	public Object peek() throws NoSuchElementException {
		if (first.getElement() == null){
			throw new NoSuchElementException("Queue is empty");
		}else{
			return first.getElement();
		}
	}
	

	/**
	 * @throws NoSuchElementException why tho
	 */
	public void dequeue () throws NoSuchElementException {
		if (first.getElement() == null){
			throw new NoSuchElementException("No such element exists to dequeue");
		}else{
			first = first.getNext();
		}
	}
	
	
	/**
	 * @param element puts object into Q
	 */
	public void addUser (Object element) {
		QueueElement newElement = new QueueElement(element, new QueueElement(null,null));
		QueueElement temp;
		
		if (first.getNext().getElement() != null){
			temp = first.getNext();
			while (temp.getNext().getElement() != null){
				temp = temp.getNext();
			}
			temp.setNext(newElement);
		}else if(first.getElement()== null){
			first.setElement(element);
		}else if(first.getNext().getElement()== null){
			first.setNext(newElement);
		}
	}
	
	/**
	 * Method to print the full contents of the queue in order from head to tail.
	 */
	public void print () {
	    String r = "The queue is empty\n";
	    if (first.getElement()!= null){
	    	r = first.getElement().toString() + "\n";
	    	if(first.getNext().getElement() != null){
	    		QueueElement temp = first.getNext();
	    		r += first.getNext().getElement().toString() + "\n";
	    		while(temp.getNext().getElement()!= null){
	    			r += temp.getNext().getElement().toString() + "\n";
	    			temp = temp.getNext();
	    		}
	    	}
	    	
	    }
	    System.out.print(r);
	}
	
	public String toString() {
	    String r = null;
	    if (first.getElement()!= null){
	    	r = first.getElement().toString();
	    	if(first.getNext().getElement() != null){
	    		QueueElement temp = first.getNext();
	    		r +=  "," + first.getNext().getElement().toString();
	    		while(temp.getNext().getElement()!= null){
	    			r += "," + temp.getNext().getElement().toString();
	    			temp = temp.getNext();
	    		}
	    	}
	    	
	    }
	    return r;
	}
}