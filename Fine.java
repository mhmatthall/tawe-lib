package application;

public class Fine {

	private static final double MINIMUM_PAYMENT = 0.01;
	private static int nextID = 0;

	// FineID = F + fine number. Example F1
	private String fineID = ("F" + nextID);
	private double amount;
	private double amountPaid;

	private String loanID;
	private Date dateIssued;
	private Date datePaid;
	private Loan loanFined;

	private boolean paid;

	// Constructor
	public Fine(String loanID) {
		this.amount = calculateAmount();
		this.loanID = loanID;
		this.amountPaid = 0;
		this.dateIssued = new Date();
		this.paid = false;

		nextID++;
	}

	// to do this
	private double calculateAmount() {
		amount = (fineDay * numberOfDaysOverdue);
		// this is very wrong but just to show our thoughts
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
	public double getAmount() {
		return amount;
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
	}

	/*
	 * @param amount
	 * 		The amount paid. Fine may have only been partially paid
	 */
	public void payFine(double amount) throws IllegalArgumentException {
		if (amount < MINIMUM_PAYMENT) {
			throw new IllegalArgumentException("Can't pay less than £" + MINIMUM_PAYMENT);
		} else if (amount > (this.amount - amountPaid)) {
			throw new IllegalArgumentException("Can't pay fore than total fine amount");
		}
		amountPaid += amount;
	}

	//TODO : IMPLEMENT TOSTRING()
	
}
