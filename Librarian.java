import java.sql.SQLException;

/*
 * Created by Matt
 */
public class Librarian extends User {
	protected int staffNumber;
	protected Date employmentDate;
	
	public Librarian(String username, String forename, String surname, String phoneNumber, String address,
			UserImage profileImage, int staffNumber, Date employmentDate) {
		super(username, forename, surname, phoneNumber, address, profileImage);
		this.staffNumber = staffNumber;
		this.employmentDate = employmentDate;
		this.isLibrarian = true;
	}

	public int getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(int staffNumber) {
		this.staffNumber = staffNumber;
	}

	public Date getEmploymentDate() {
		return employmentDate;
	}

	public void setEmploymentDate(Date employmentDate) {
		this.employmentDate = employmentDate;
	}
	
	public void issueLoan(String CopyID, String UserID) throws SQLException {
		Loan l = new Loan(CopyID, UserID);
		new DatabaseRequest().addLoan(l);
		
	}
	
	public void returnLoan(Loan loan) throws SQLException{
		
		Copy c = new DatabaseRequest().getCopy(loan.getCopyID());
		Resource r = new DatabaseRequest().getResource(c.getResourceID());
		
		if ((new Date()).isBefore(loan.getReturnDate())) {
			Fine f = new Fine(loan.getLoanID());
			new DatabaseRequest().addFine(f);
		}
		
		loan.setLoanStatus(false);
		
		if (r.getQueue().isEmpty()) {
			loan.returnResource();
		} else {
			Loan l = new Loan(loan.getCopyID(), r.getQueue().peek());
			loan.setReservationStatus(true, r.getQueue().peek());
			r.getQueue().dequeue();
		}
		
		new DatabaseRequest().editResource(r);
		new DatabaseRequest().editLoan(loan);
	}
	
	public void PayFine(String fineID, double amount) {
		Fine f = new DatabaseRequest().getFine();
		
		if (amount < f.getMinimumPayment())
			throw new IllegalArgumentException("Cannot pay less than �" + f.getMinimumPayment());
		else if (f.getAmountPaid() + amount > f.getAmount()) {
			throw new IllegalArgumentException("Cant pay more than the total fine amount ");
		}
		
		f.setAmountPaid(f.getAmountPaid() + amount);
		new DatabaseRequest().editFine(f);
		
	}
}
