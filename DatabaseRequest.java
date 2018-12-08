import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

/* DatabaseRequest
   @author Matt Hall
   @version 1.0
   
	+ search(tables : string, fieldName : string, query : string, numberOfResults : int) : ArrayList<Object>

	+ getOldestLoan(resourceID : string
	+ getOverdueLoans() : ArrayList<Copy>
	+ count copies
	+ get list of copies
	+ check if any copy of a resource is available
	+ loan out as objects
	+ total fines for given user
	+ get list of user's resources
 */

public class DatabaseRequest {
	private static final String DATABASE_NAME = "tawe-lib";
	private static Connection conn;

	// FOR TESTING ONLY
//	public static void main(String[] args) {
//		establishConnection();
//		try {
//			Statement uhoh = conn.createStatement();
//			//uhoh.addBatch("DELETE FROM LIBRARIAN WHERE USERNAME = 'l.oreilly'");
//			//uhoh.addBatch("DELETE FROM LIBRARY_USER WHERE USERNAME = 'l.oreilly'");
//			//uhoh.executeBatch();
//			//System.out.println(check + " rows changed by " + uhoh.getUpdateCount() + " statements");
//			
//			//ResultSet uhohRes = uhoh.executeQuery("SELECT * FROM LIBRARIAN WHERE USERNAME = 'l.oreilly'");
//			//uhohRes.next();
//			//System.out.println(uhohRes.getString(2) + " " + uhohRes.getString(3));
//			
//			//uhoh.executeUpdate("UPDATE LIBRARIAN SET EMPLOYMENT_DATE = 131211 WHERE USERNAME = 'l.oreilly'");
//			//Librarian u1 = new Librarian("l.oreilly", "Liam", "OReilly", "07706545232", "Trafalgar Place, Brynmill, SA2 0DC", null, 18, new Date(14, 12, 12));
//			//addUser(u1);
//			//Librarian meme = (Librarian)getUser("l.oreilly");
//			//System.out.println("Hi, " + meme.getForename() + meme.getSurname() + meme.getPhoneNumber() + meme.getAddress() + meme.getUsername() + meme.getProfileImage() + meme.getStaffNumber() + " empdate: " + meme.getEmploymentDate().getDay() + meme.getEmploymentDate().getMonth() + meme.getEmploymentDate().getYear());
//		} catch (Exception e) {
//			System.out.println("ERROR: " + e.getMessage());
//		}
//	}
	
	public DatabaseRequest() throws SQLException {
		establishConnection();
	}
	
	private void establishConnection() throws SQLException {
		conn = DriverManager.getConnection("jdbc:derby:" + DATABASE_NAME);
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
	}
	
	public void addUser(User newUser) throws SQLException {
		// Two queries are added to a batch, then run sequentially:
		//		- one inserts the user into the LIBRARY_USER table,
		//		- the other inserts the user into either the LIBRARIAN or BORROWER table
				
		// user table insertion
		Statement queries = conn.createStatement();
		queries.addBatch("INSERT INTO LIBRARY_USER VALUES(" +
								"'" + newUser.getUsername() + "', " +
								"'" + newUser.getForename() + "', " +
								"'" + newUser.getSurname() + "'," +
								"'" + newUser.getPhoneNumber() + "', " +
								"'" + newUser.getAddress() + "', " +
								"'" + newUser.getProfileImage().getImage() + "')");
		
		// librarian/borrower table insertion	
		if (newUser instanceof Librarian) {
			queries.addBatch(
					"INSERT INTO LIBRARIAN VALUES('" + newUser.getUsername() + "', " +
							"'" + ((Librarian)newUser).getEmploymentDate().toString() + "', " +
							((Librarian)newUser).getStaffNumber() + ")");
		} else {
			queries.addBatch(
					"INSERT INTO BORROWER VALUES('" + newUser.getUsername() + "', " +
							((Borrower)newUser).getBalance() + ")");
		}
		
		queries.executeBatch();	// Execute both statements in sequence
	}
	
	public void editUser(User newDetails) throws SQLException {
		Statement query = conn.createStatement();
		
		query.addBatch("UPDATE LIBRARY_USER SET " +
						"forename = '" + newDetails.getForename() + "', " +
						"surname = '" + newDetails.getSurname() + "', " +
						"phone_number = '" + newDetails.getPhoneNumber() + "', " +
						"address = '" + newDetails.getAddress() + "', " +
						"profile_image = '" + newDetails.getProfileImage().getImage() + "' " +
						"WHERE username = '" + newDetails.getUsername() + "'");
		
		if (newDetails instanceof Librarian) {			
			query.addBatch("UPDATE LIBRARIAN SET " +
							"staff_number = " + ((Librarian) newDetails).getStaffNumber() + ", " +
							"employment_date = '" + ((Librarian)newDetails).getEmploymentDate().toString() + "' " +
							"WHERE username = '" + newDetails.getUsername() + "'");
		} else {
			query.addBatch("UPDATE BORROWER SET " +
					"balance = " + ((Borrower) newDetails).getBalance() + " " +
					"WHERE username = '" + newDetails.getUsername() + "'");
		}
		
		query.executeBatch();	// Runs the queued queries sequentially
	}
	
	public void deleteUser(String username) throws SQLException {
		Statement query = conn.createStatement();
		
		// Must delete from subclass tables first as to not break dependencies
		if (userIsLibrarian(username)) {
			query.addBatch("DELETE FROM LIBRARIAN WHERE username = '" + username + "'");
		} else {
			query.addBatch("DELETE FROM BORROWER WHERE username = '" + username + "'");
		}
		
		query.addBatch("DELETE FROM LIBRARY_USER WHERE username = '" + username + "'");
		
		query.executeBatch();
	}
	
	public User getUser(String username) throws SQLException {
		Statement query = conn.createStatement();
		ResultSet results;
		User out = null;
		
		if (userIsLibrarian(username)) {
			// User is a librarian
			results = query.executeQuery("SELECT LIBRARY_USER.*, LIBRARIAN.STAFF_NUMBER, LIBRARIAN.EMPLOYMENT_DATE "
					+ "FROM LIBRARY_USER INNER JOIN LIBRARIAN ON LIBRARY_USER.USERNAME = LIBRARIAN.USERNAME "
					+ "WHERE LIBRARY_USER.USERNAME = '" + username + "'");
			results.next();
			
			out = new Librarian(username,
					results.getString(2),	// forename
					results.getString(3),	// surname
					results.getString(4),	// phone number
					results.getString(5),	// address
					new UserImage(results.getString(6)),	// profile image
					results.getInt(7),	// staff number
					new Date(results.getString(8)));	// employment date
			
		} else {
			// User is a borrower
			results = query.executeQuery("SELECT LIBRARY_USER.*, BORROWER.BALANCE "
					+ "FROM LIBRARY_USER INNER JOIN BORROWER ON LIBRARY_USER.USERNAME = BORROWER.USERNAME "
					+ "WHERE LIBRARY_USER.USERNAME = '" + username + "'");
			results.next();
			
			out = new Borrower(username,
					results.getString(2),	// forename
					results.getString(3),	// surname
					results.getString(4),	// phone number
					results.getString(5),	// address
					new UserImage(results.getString(6)), // profile image
					results.getDouble(7));	// balance
		}
		
		return out;
	}
	
	// Determine the type of user being retrieved
	private boolean userIsLibrarian(String username) throws SQLException {
		Statement userTypeCheck = conn.createStatement();
		ResultSet rs = userTypeCheck.executeQuery("SELECT COUNT(*) FROM LIBRARIAN WHERE username = '" + username + "'");
		rs.next();
		int userType = rs.getInt(1);	// userType = 0 if borrower, 1 if librarian
		return (userType == 1);
	}
	
	public void addResource(Resource newResource) throws SQLException {
		// Two queries are added to a batch, then run sequentially:
		//		- one inserts the resource into the RESOURCE table,
		//		- the other inserts the resource into either the BOOK, DVD, or LAPTOP table
	
		String queuedUsers = "";
		while (!newResource.getQueue().isEmpty()) {
			queuedUsers = queuedUsers + "," + newResource.getQueue().peek();
			newResource.getQueue().dequeue();
		}
		
		// resource table insertion
		Statement queries = conn.createStatement();
		queries.addBatch("INSERT INTO RESOURCE VALUES(" +
				"'" + newResource.getResourceID() + "', " +
				"'" + newResource.getTitle() + "', " +
				"'" + newResource.getYear() + "', " +
				"'" + newResource.getThumbnail().getImage() + "', " +
				"'" + queuedUsers + "')");

		// librarian/borrower table insertion
		if (newResource instanceof Book) {
			queries.addBatch(
					"INSERT INTO BOOK VALUES('" + newResource.getResourceID() + "', " +
							"'" + ((Book)newResource).getAuthor() + "', " +
							"'" + ((Book)newResource).getPublisher() + "', " +
							"'" + ((Book)newResource).getGenre() + "', " +
							"'" + ((Book)newResource).getISBN() + "', " +
							"'" + ((Book)newResource).getLanguage() + "')");
			
		} else if (newResource instanceof DVD) {
			// Reformat list of languages for database insertion 
			String subtitleLanguages = "";
			for (String language : ((DVD)newResource).getSubLang()) {
				subtitleLanguages = subtitleLanguages + language + ",";
			}

			queries.addBatch(
					"INSERT INTO DVD VALUES('" + newResource.getResourceID() + "', " +
							"'" + ((DVD)newResource).getDirector() + "', " +
							"'" + ((DVD)newResource).getRuntime() + "', " +
							"'" + ((DVD)newResource).getLanguage() + "', " +
							"'" + subtitleLanguages + "')");
			
		} else {
			queries.addBatch(
					"INSERT INTO LAPTOP VALUES('" + newResource.getResourceID() + "', " +
							"'" + ((Laptop)newResource).getManufacturer() + "', " +
							"'" + ((Laptop)newResource).getModel() + "', " +
							"'" + ((Laptop)newResource).getOperatingSys() + "')");
		}
		
		queries.executeBatch();	// Execute both statements in sequence
	}
	
	public void editResource(Resource newDetails) throws SQLException {
		Statement query = conn.createStatement();
		
		query.addBatch("UPDATE RESOURCE SET " +
				"title = '" + newDetails.getTitle() + "', " +
				"year_released = '" + newDetails.getYear() + "', " +
				"thumbnail = '" + newDetails.getThumbnail().getImage() + "', " +
				"queue = '" + newDetails.getQueue().toString() + "', " +
				"WHERE resource_id = '" + newDetails.getResourceID() + "'");
	
		if (newDetails instanceof Book) {
			query.addBatch("UPDATE BOOK SET " +
					"author = '" + ((Book) newDetails).getAuthor() + "', " +
					"publisher = '" + ((Book) newDetails).getPublisher() + "', " +
					"genre = '" + ((Book) newDetails).getGenre() + "', " +
					"isbn = '" + ((Book) newDetails).getISBN() + "', " +
					"language = '" + ((Book) newDetails).getLanguage() + "', " +
					"WHERE resource_id = '" + newDetails.getResourceID() + "'");
	
		} else if (newDetails instanceof DVD) {
			String subtitleLanguages = "";
			for (String language : ((DVD)newDetails).getSubLang()) {
				subtitleLanguages = subtitleLanguages + language + ",";
			}
			
			query.addBatch("UPDATE DVD SET " +
					"director = '" + ((DVD) newDetails).getDirector() + "', " +
					"runtime = '" + ((DVD) newDetails).getRuntime() + "', " +
					"language = '" + ((DVD) newDetails).getLanguage() + "', " +
					"subtitle_languages = '" + subtitleLanguages + "', " +
					"WHERE resource_id = '" + newDetails.getResourceID() + "'");
		} else {
			query.addBatch("UPDATE LAPTOP SET " +
					"manufacturer = '" + ((Laptop) newDetails).getManufacturer() + "', " +
					"model = '" + ((Laptop) newDetails).getModel() + "', " +
					"operating_system = '" + ((Laptop) newDetails).getOperatingSys() + "', " +
					"WHERE resource_id = '" + newDetails.getResourceID() + "'");
		}
		
		query.executeBatch();	// Runs the queued queries sequentially
	}
	
	public void deleteResource(String resourceID) throws SQLException {
		Statement query = conn.createStatement();
		
		// Must delete from subclass tables first as to not break dependencies
		query.addBatch("DELETE FROM " + getResourceType(resourceID) + " WHERE resource_id = '" + resourceID + "'");
		
		query.addBatch("DELETE FROM RESOURCE WHERE resource_id = '" + resourceID + "'");
		
		query.executeBatch();
	}
	
	public Resource getResource(String resourceID) throws SQLException {
		Statement query = conn.createStatement();
		ResultSet results;
		Resource out = null;
		
		if (getResourceType(resourceID).equals("BOOK")) {
			// Resource is a book
			results = query.executeQuery("SELECT RESOURCE.*, BOOK.AUTHOR, BOOK.PUBLISHER, BOOK.GENRE, BOOK.ISBN, BOOK.LANGUAGE "
					+ "FROM RESOURCE INNER JOIN BOOK ON RESOURCE.RESOURCE_ID = BOOK.RESOURCE_ID "
					+ "WHERE RESOURCE.RESOURCE_ID = '" + resourceID + "'");
			results.next();
			
			RequestQueue queue = convertRequestQueue(results.getString(5));	// request queue
			
			out = new Book(resourceID,
					results.getString(2),	// title
					results.getInt(3),	// year
					new Thumbnail(results.getString(4)),	// thumbnail
					queue,	// queue
					results.getString(6),	// author
					results.getString(7),	// publisher
					results.getString(8),	// genre
					results.getString(9),	// isbn
					results.getString(10));	// language
			
		} else if (getResourceType(resourceID).equals("DVD")) {
			// Resource is a DVD
			results = query.executeQuery("SELECT RESOURCE.*, DVD.DIRECTOR, DVD.RUNTIME, DVD.LANGUAGE, DVD.SUBTITLE_LANGUAGES "
					+ "FROM RESOURCE INNER JOIN DVD ON RESOURCE.RESOURCE_ID = DVD.RESOURCE_ID "
					+ "WHERE RESOURCE.RESOURCE_ID = '" + resourceID + "'");
			results.next();

			RequestQueue queue = convertRequestQueue(results.getString(5));	// request queue
			
			// Format languages back into arraylist from database string
			String[] langs = results.getString(10).split(",");
			ArrayList<String> subLang = new ArrayList<>(Arrays.asList(langs));

			out = new DVD(resourceID,
					results.getString(2),	// title
					results.getInt(3),	// year
					new Thumbnail(results.getString(4)),	// thumbnail
					queue,	// queue
					results.getString(6),	// director
					results.getInt(7),	// runtime
					results.getString(8),	// language
					subLang);	// subtitle languages

		} else {
			// Resource is a laptop
			results = query.executeQuery("SELECT RESOURCE.*, LAPTOP.MANUFACTURER, LAPTOP.MODEL, LAPTOP.OPERATING_SYSTEM "
					+ "FROM RESOURCE INNER JOIN LAPTOP ON RESOURCE.RESOURCE_ID = LAPTOP.RESOURCE_ID "
					+ "WHERE RESOURCE.RESOURCE_ID = '" + resourceID + "'");
			results.next();

			RequestQueue queue = convertRequestQueue(results.getString(5));	// request queue
			
			out = new Laptop(resourceID,
					results.getString(2),	// title
					results.getInt(3),	// year
					new Thumbnail(results.getString(4)),	// thumbnail
					queue,	// queue
					results.getString(6),	// manufacturer
					results.getString(7),	// model
					results.getString(8));	// OS
		}
		
		return out;
	}
	
	private String getResourceType(String resourceID) throws SQLException {
		// Check if there's an entry in the BOOK table; it must be a book
		Statement isBook = conn.createStatement();
		ResultSet rsBook = isBook.executeQuery("SELECT COUNT(*) FROM LIBRARIAN WHERE username = '" + resourceID + "'");
		rsBook.next();
		
		if (rsBook.getInt(1) == 1) {
			return "BOOK";
		}
		
		// Check if there's an entry in the DVD table; it must be a DVD
		Statement isDVD = conn.createStatement();
		ResultSet rsDVD = isDVD.executeQuery("SELECT COUNT(*) FROM LIBRARIAN WHERE username = '" + resourceID + "'");
		rsDVD.next();
		
		if (rsDVD.getInt(1) == 1) {
			return "DVD";
		}
		
		// If none of the above, then it must be a laptop
		return "LAPTOP";
	}
	
	private RequestQueue convertRequestQueue(String queue) throws SQLException {
		String[] requests = queue.split(",");

		RequestQueue rq = new RequestQueue();
		for (int i = 0; i < requests.length; i++) {
			rq.addUser(requests[i]);
		}
		
		return rq;
	}
	
	public void addCopy(Copy newCopy) throws SQLException {
		// Format boolean true/false to 1/0 for database insertion
		int isOnLoan = newCopy.isOnLoan() ? 1 : 0;
		int isReserved = newCopy.isReserved() ? 1 : 0;
		
		Statement query = conn.createStatement();
		query.executeUpdate("INSERT INTO COPY VALUES(" +
				"'" + newCopy.getCopyID() + "', " +
				"'" + newCopy.getResourceID() + "', " +
				"'" + newCopy.getLoanTime() + "', " +
				"'" + isOnLoan + "', " +
				"'" + isReserved + "', " +
				"'" + newCopy.getReservingUser() + "')");
	}
	
	public void editCopy(Copy newDetails) throws SQLException {
		int isOnLoan = newDetails.isOnLoan() ? 1 : 0;
		int isReserved = newDetails.isReserved() ? 1 : 0;
		
		Statement query = conn.createStatement();
		query.executeUpdate("UPDATE COPY SET " +
				"resource_id = '" + newDetails.getResourceID() + "', " +
				"loan_duration = '" + newDetails.getLoanTime() + "', " +
				"is_on_loan = '" + isOnLoan + "', " +
				"is_reserved = '" + isReserved + "', " +
				"reserved_by_user_id = '" + newDetails.getReservingUser() + " " +
				"WHERE copy_id = '" + newDetails.getCopyID() + "'");
	}
	
	public void deleteCopy(String copyID) throws SQLException {
		Statement query = conn.createStatement();
		
		query.executeUpdate("DELETE FROM COPY WHERE copy_id = '" + copyID + "'");
	}
	
	public ArrayList<Borrower> browseBorrowers() throws SQLException {
		Statement query = conn.createStatement();
		
		ResultSet results = query.executeQuery("SELECT LIBRARY_USER.*, BORROWER.BALANCE"
				+ " FROM LIBRARY_USER INNER JOIN BORROWER"
				+ " ON USER.USERNAME = BORROWER.USERNAME");
		results.next();
		
		ArrayList<Borrower> out = new ArrayList<Borrower>();
		Borrower temp;
		
		while (results.next()) {
			temp = new Borrower(results.getString(1),	// username
					results.getString(2),	// forename
					results.getString(3),	// surname
					results.getString(4),	// phone number
					results.getString(5),	// address
					new UserImage(results.getString(6)), // profile image
					results.getDouble(7));	// balance
			
			out.add(temp);
		}
		
		return out;
	}
	
	public ArrayList<Resource> browseResources() throws SQLException {
		Statement query = conn.createStatement();
		
		ResultSet results = query.executeQuery("SELECT * FROM RESOURCE");
		
		ArrayList<Resource> out = new ArrayList<Resource>();
		Resource temp;
		
		while (results.next()) {
			temp = new Resource(results.getString(1),	// resourceID
					results.getString(2),	// title
					results.getInt(3),	// year
					new Thumbnail(results.getString(4)),	// thumbnail
					convertRequestQueue(results.getString(5)));	// request queue
			
			out.add(temp);
		}
		
		return out;
	}
	
	public ArrayList<Loan> getLoanHistory(String copyID) throws SQLException {
		Statement query = conn.createStatement();
		
		ResultSet results = query.executeQuery("SELECT * FROM LOAN WHERE COPY_ID = " + copyID);
		
		ArrayList<Loan> out = new ArrayList<Loan>();
		Loan temp;
		
		while (results.next()) {
			temp = new Loan(results.getString(1),	// loanID
					new Date(results.getString(2)),	// issue date
					results.getString(3),	// username
					results.getString(4),	// copyID
					new Date(results.getString(5)));	// return date
			
			out.add(temp);
		}
		
		return out;
	}

//	public Loan getOldestLoan(String resourceID) throws SQLException {
//		Statement query = conn.createStatement();
//		
//		ResultSet results = query.executeQuery("SELECT * FROM LOAN WHERE COPY_ID = " + copyID);
//		
//		ArrayList<Loan> out = new ArrayList<Loan>();
//		Loan temp;
//		
//		while (results.next()) {
//			temp = new Loan(results.getString(1),	// loanID
//					new Date(results.getString(2)),	// issue date
//					results.getString(3),	// username
//					results.getString(4),	// copyID
//					new Date(results.getString(5)));	// return date
//			
//			out.add(temp);
//		}
//		return null;
//	}
}