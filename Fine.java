import java.sql.SQLException;

/*
 * @author Constantinos Loizou
 * @version 1.0
 * 
 * TODO fix broken methods 
 * TODO add auto fineID generation
 */

public class Fine {

	private static final double MINIMUM_PAYMENT = 0.01;
	private static int nextID = 0;
	
	private String fineID = ("F" + nextID);	// FineID = F + fine number; ...F9, F10, F11,...
	private double amount;
	private double amountPaid;
	private String loanID;
	private Date dateIssued;
	private Date datePaid;
	private boolean paid;

	public Fine(String loanID) throws SQLException {
		this.amount = calculateAmount();
		this.amountPaid = 0.0;
		this.dateIssued = new Date(); // Today's date
		this.paid = false;
		this.loanID = loanID;
		nextID++;
	}

	public Fine(String fineID, double amount, double amountPaid, String loanID, Date datePaid, Date dateIssued, boolean isPaid) {
		// Required to load Fines from the database
		this.fineID = fineID;
		this.amount = amount;
		this.amountPaid = amountPaid;
		this.loanID = loanID;
		this.datePaid = datePaid;
		this.dateIssued = dateIssued;
		this.paid = isPaid;
	}
	
	public String getFineID() {
		return fineID;
	}

	public void setFineID(String fineID) {
		this.fineID = fineID;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getLoanID() {
		return loanID;
	}

	public void setLoanID(String loanID) {
		this.loanID = loanID;
	}

	public Date getDateIssued() {
		return dateIssued;
	}

	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
	}

	public Date getDatePaid() {
		return datePaid;
	}

	public void setDatePaid(Date datePaid) {
		this.datePaid = datePaid;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	
	public double getMinimumPayment() {
		return MINIMUM_PAYMENT;
	}
	
	


	/*
	 * Calculates the amount of the fine
	 */
	private double calculateAmount() throws SQLException {
		int timeOverdue;
		
		Date today = new Date();		
		Loan loan = new DatabaseRequest().getLoan(loanID);
		
		//If copy was returned on time fine is 0.
		if  ( loan.getReturnDate().isBefore(today) ) {
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
		} else if(res instanceof Laptop) {
			amount = (Laptop.getFineDay() * timeOverdue);
		}
		return amount;
	}
	
	/*
	 * @param amount
	 * 		The amount paid. Fine may have only been partially paid
	 */
	
	

	//TODO : IMPLEMENT TOSTRING()
	
}
