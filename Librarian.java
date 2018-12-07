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
	
}
