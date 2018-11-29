import java.util.NoSuchElementException;

/**
 * @author Rimantas
 *
 */
public class Queue {
	private QueueElement first;
	
	/**
	 * Constructs an empty Queue.
	 */
	public Queue () {
	    first = new QueueElement(null, new QueueElement(null,null));
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
	public void enqueue (Object element) {
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
}