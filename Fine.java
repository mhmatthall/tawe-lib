package application;

public class Fine {
	
	private static final double MINIMUM_PAYMENT = 0.01;
	private static int nextID = 0;
	
	//FineID = F + fine number. Example F1
	private String fineID = ("F" + nextID);
	private double amount;
	private double amountPaid;
	
	private String loanID;
	private Date dateIssued;
	private Date datePaid;
	
	private boolean paid;
	
	
	
	//Constructor
	public Fine(double amount, String loanID) {
		this.amount = amount;
		this.loanID = loanID;
		this.amountPaid = 0;
		this.dateIssued = new Date();
		this.paid = false;
		
		nextID++;
	}
	
	//to do this
	private double calculateAmount() {
		amount = fineDay * numberOfDaysOverdue;
		//this is very wrong but just to show our thoughts
	}
	
	public String getFineID() {
		return fineID;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public Date getDateIssued() {
		return dateIssued;
	}
	
	public Date getDatePaid() {
		return datePaid;
	}
	
	public String getLoanID() {
		return loanID;
	}
	
	public boolean isPaid() {
		return paid;
	}
	
	public void setPaid() {
		paid = true;
	}
	
	public void payFine(double amount) throws IllegalArgumentException{
		if (amount < MINIMUM_PAYMENT) {
			throw new IllegalArgumentException("Can't pay less than £" + MINIMUM_PAYMENT);
		} else {
		//TODO: Handle case that user pays more than the fine's total amount
			amountPaid += amount;
		}
	}
	
}
