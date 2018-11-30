/*
 * @author
 * 		Constantinos Loizou
 * 
 * @version 1.0
 */

public class Fine {

	private static final double MINIMUM_PAYMENT = 0.01;
	private static int nextID = 0;

	// FineID = F + fine number. Example F1
	private String fineID = ("F" + nextID);
	private double amountTotal;
	private double amountPaid;
	private double amountLeft;

	private String loanID;
	private Date dateIssued;
	private Date datePaid;
	private Loan loanFined;

	private boolean paid;

	// Constructor
	public Fine(String loanID) {
		this.amountTotal = calculateAmount();
		this.amountPaid = 0;
		this.amountLeft = 0;
		this.dateIssued = new Date(); //Current date set
		this.paid = false;
		this.loanID = loanID;
		nextID++;
	}

	/*
	 * Calculates the amount of the fine
	 */
	private double calculateAmount() {
		int timeOverdue;
		
		//If copy was returned on time fine is 0.
		if  ( loan.getActualReturnDate().isBefore(loan.getExpectedReturnDay())) {
			amountTotal = 0;
		} else {
			amountTotal = loan.getActualReturnDate().compare(loan.expectedReturnDate());
		}
		
		amountTotal = (resource.getFinePerDay() * timeOverdue);
	}
	
	/*
	 * @return The fineID
	 */
	public String getFineID() {
		return fineID;
	}
	
	/*
	 * @return Fine amount
	 */
	public double getAmountTotal() {
		return amountTotal;
	}
	
	/*
	 * @return Amount Paid
	 */
	public double getAmountPaid() {
		return amountPaid;
	}
	
	/*
	 * @return Amount left to pay
	 */
	public double getAmountLeft() {
		return amountLeft;
	}
	
	/*
	 * @return date the fine was issued
	 */
	public Date getDateIssued() {
		return dateIssued;
	}
	
	/*
	 * @return Date the fine was paid
	 */
	public Date getDatePaid() {
		return datePaid;
	}

	/*
	 * @return True if fine has been paid. False otherwise
	 */
	public boolean isPaid() {
		return paid;
	}

	/*
	 * Set fine as paid
	 */
	public void setPaid() {
		paid = true;
		datePaid = new Date();
	}

	/*
	 * @param amount
	 * 		The amount paid. Fine may have only been partially paid
	 */
	public void payFine(double amount) throws IllegalArgumentException {
		if (amount < MINIMUM_PAYMENT) {
			throw new IllegalArgumentException("Can't pay less than £" + MINIMUM_PAYMENT);
		} else if (amount > (this.amountLeft - amountPaid)) {
			throw new IllegalArgumentException("Can't pay more than total fine amount");
		}
		amountPaid += amount;
		amountLeft -= amount;
		
		if (amountLeft <= amountPaid) {
			setPaid();
		}
	}

	//TODO : IMPLEMENT TOSTRING()
	
}
