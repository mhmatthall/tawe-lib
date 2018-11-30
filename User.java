public class User {
	private String username;
	private String forename;
	private String surname;
	private String phoneNumber;
	private String address;	
	private UserImage profileImage;
	private boolean isLibrarian;
	
	public User(String username, String forename, String surname, String phoneNumber, String address, UserImage profileImage){
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
	
	public browseResources(){
	
	}
	
	public searchResources(String searchType, String query){
	
	}
	
	public viewResourceDetails(){
	
	}
	
	public boolean isLibrarian() {
		return isLibrarian;
	}
	
	public loadUserData(){
	
	}