import java.sql.SQLException;

// TODO: Auto-generated Javadoc
/**
 * 
 * @author Constantinos Loizou
 * @version 1.0
 * 
 * TODO fix broken methods 
 */

public class Fine {

	private static final double MINIMUM_PAYMENT = 0.01;
	private static int nextID = 0;

	private String fineID;
	private double amount;
	private double amountPaid;
	private String loanID;
	private Date dateIssued;
	private Date datePaid;
	private boolean paid;

	/**
	 * Creates a new fine
	 *
	 * @param loanID loan to which fine is going to be associated to
	 * @throws SQLException if connection to database fails
	 */
	public Fine(String loanID) throws SQLException {
		this.amount = calculateAmount();
		this.amountPaid = 0.0;
		this.dateIssued = new Date(); // Today's date
		this.paid = false;
		this.loanID = loanID;
		fineID = ("F" + nextID);
		nextID++;
	}

	/**
	 * Used for fetching fine from the database by DatabaseRequest.
	 *
	 * @param fineID the fine ID
	 * @param amount the amount
	 * @param amountPaid the amount paid
	 * @param loanID the loan ID
	 * @param datePaid the date paid
	 * @param dateIssued the date issued
	 * @param isPaid the is paid
	 */
	public Fine(String fineID, double amount, double amountPaid, String loanID, Date datePaid, Date dateIssued,
			boolean isPaid) {
		// Required to load Fines from the database
		this.fineID = fineID;
		this.amount = amount;
		this.amountPaid = amountPaid;
		this.loanID = loanID;
		this.datePaid = datePaid;
		this.dateIssued = dateIssued;
		this.paid = isPaid;
	}

	/**
	 * Gets the fine ID.
	 *
	 * @return fine ID
	 */
	public String getFineID() {
		return fineID;
	}

	/**
	 * Gets the fine amount.
	 *
	 * @return amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount to be set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * Gets the amount paid.
	 *
	 * @return amount paid
	 */
	public double getAmountPaid() {
		return amountPaid;
	}

	/**
	 * Sets the amount paid.
	 *
	 * @param amountPaid set amount paid
	 */
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	/**
	 * Gets the loan ID.
	 *
	 * @return loan ID
	 */
	public String getLoanID() {
		return loanID;
	}

	/**
	 * Gets the date issued.
	 *
	 * @return date issued
	 */
	public Date getDateIssued() {
		return dateIssued;
	}

	/**
	 * Sets the date issued.
	 *
	 * @param dateIssued 
	 */
	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
	}

	/**
	 * Gets the date paid.
	 *
	 * @return date paid
	 */
	public Date getDatePaid() {
		return datePaid;
	}

	/**
	 * Sets the date paid.
	 *
	 * @param datePaid the new date paid
	 */
	public void setDatePaid(Date datePaid) {
		this.datePaid = datePaid;
	}

	/**
	 * Checks if fine is paid.
	 *
	 * @return true, if fine is paid
	 */
	public boolean isPaid() {
		return paid;
	}

	/**
	 * Sets paid status
	 *
	 * @param paid True if paid, False otherwise
	 */
	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	/**
	 * Gets the minimum payment.
	 *
	 * @return the minimum payment
	 */
	public double getMinimumPayment() {
		return MINIMUM_PAYMENT;
	}

	/**
	 * Calculate amount the of fine.
	 *
	 * @return amount of fine to be paid
	 * @throws SQLException if connection to database fails
	 */
	private double calculateAmount() throws SQLException {
		int timeOverdue;

		Date today = new Date();
		Loan loan = new DatabaseRequest().getLoan(loanID);

		// If copy was returned on time fine is 0.
		if (loan.getReturnDate().isBefore(today)) {
			timeOverdue = loan.getReturnDate().compare(today);
			System.out.println("Days overdue: " + timeOverdue);
		} else {
			timeOverdue = 0;
			System.out.println("This loan is not overdue");
		}

		String resID = new DatabaseRequest().getCopy(loan.getCopyID()).getResourceID();
		Resource res = new DatabaseRequest().getResource(resID);

		if (res instanceof Book) {
			amount = (Book.getFineDay() * timeOverdue);
		} else if (res instanceof DVD) {
			amount = (DVD.getFineDay() * timeOverdue);
		} else if (res instanceof Laptop) {
			amount = (Laptop.getFineDay() * timeOverdue);
		}
		return amount;
	}

}
