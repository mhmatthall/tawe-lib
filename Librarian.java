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
	
	public void issueLoan(String CopyID, String UserID) {
		Loan l = new Loan(CopyID, UserID);
		new DatabaseRequest().addLoan(l);
		
	}
	
	public void returnLoan(Loan loan) throws SQLException{
		
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
			
		}
		
		new DatabaseRequest().editLoan(loan);
	}
}
