import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * TODO sort out broken (commented out) methods
 * TODO add missing UML methods
 */
public class User {
	private String username;
	private String forename;
	private String surname;
	private String phoneNumber;
	private String address;
	private UserImage profileImage;
	protected boolean isLibrarian;

	public User(String username, String forename, String surname, String phoneNumber, String address,
		UserImage profileImage) {
		this.username = username;
		this.forename = forename;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.profileImage = profileImage;
	}

	public String getSurname() {
		return surname;
	}

	public String getForename() {
		return forename;
	}

	public String getUsername() {
		return username;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public UserImage getProfileImage() {
		return profileImage;
	}

	public String toStringOld() {
		String x;
		x = ("UserName: " + username + "\nForename: " + forename + "\nSurname: " + surname + "\nPhone Number: " + phoneNumber + "\nAddress: " + address);
		return x;
	}
	
//	public ArrayList[] browseResources(){
//		ArrayList[] DVDs = new ArrayList[DatabaseRequest.viewTable("DVD")];
//		ArrayList[] books = new ArrayList[DatabaseRequest.viewTable("Book")];
//		ArrayList[] laptops = new ArrayList[DatabaseRequest.viewTable("Laptop")];
//		ArrayList[] resources = DVDs;
//		resources.addAll(books);
//		resources.addAll(laptops);
//		return resources;
//		
//		
//	}
//	
//	public ArrayList[] searchResources(String searchType, String query, int numberOfResults){
//		return DatabaseRequest.search(searchType,"*",query, numberOfResults)
//	}
//	
//	public viewResourceDetails(){
//	
//	}
//	
	public boolean isLibrarian() {
		return isLibrarian;
	}
	
	public String toString() {
		String result;
		result = "User ID: " + this.username;
		result += "\nFirst Name; " + this.forename;
		result += "\nLast Name; " + this.surname;
		result += "\nPhone Number; " + this.phoneNumber;
		result += "\nAddress; " + this.address;
		result += "\nForename; " + this.forename;
		if (this.isLibrarian) {
			result += "\nAccount Type: LIBRARIAN";
		} else {
			result += "\nAccount Type: BORROWER";
		}
		
		return result;
	}
	
//	
//	public loadUserData(){
//	
}