import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.collections.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class User {
	String username;
	String forename;
	String surname;
	String phoneNumber;
	String address;	
	UserImage profileImage;
	
	public User(String username, String forename, String surname, String phoneNumber, String address, UserImage profileImage){
		this.username = username;
		this.forename = forename;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.profileImage = profileImage;
	}
	
	public String getSurname(){
		return surname;
	}
	
	public String getForename(){
		return forename;
	}

	public String getUsername(){
		return username;
	}
	
	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	public String getaddress(){
		return address;
	}
	
	
	public ArrayList[] browseResources(){
		List DVDs = new ArrayList[DatabaseRequest.search("DVD","*","",null)]
		List books = new ArrayList[DatabaseRequest.search("Book","*","",null)]
		List laptops = new ArrayList[DatabaseRequest.search("Laptop","*","",null)]
		List resources = DVDs;
		resources.addAll(books);
		resources.addAll(laptops);
		
		
	}
	
	public ArrayList[] searchResources(String searchType, String query, int numberOfResults){
		return DatabaseRequest.search(searchType,"*",query, numberOfResults)
	}
	
	public viewResourceDetails(){
	
	}
	
	
	public loadUserData(){
	
	}