import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

/* DatabaseRequest
   @author Matt Hall
   @version 1.0
   
   TODO Need to remove static attributes (and main method) when testing complete
   	+ editResource
	+ deleteResource(resourceID : string) + editResource(resourceID : string, newDetails : Resource)
	+ getResource
	+ addCopy(newCopy : Copy)
   	+ editCopy
	+ deleteCopy(copyID : string) + editCopy(copyID : string, newDetails : Copy)
	+ getCopy
	+ search(tables : string, fieldName : string, query : string, numberOfResults : int) : ArrayList<Object>
	+ browse(table : string) : ArrayList<Object>
	+ getLoanHistory(copyID : string) : ArrayList<Loan>
	+ getOldestLoan(resourceID : string
	+ getOverdueLoans() : ArrayList<Copy>
	+ count copies
	+ get list of copies
	+ check if any copy of a resource is available
	+ loan out as objects
	+ total fines for given user
	+ 
 */

public class DatabaseRequest {
	private static final String DATABASE_NAME = "tawe-lib";
	private static Connection conn;

	// FOR TESTING ONLY
	public static void main(String[] args) {
		establishConnection();
		try {
			Statement uhoh = conn.createStatement();
			//ResultSet uhohRes =
			//uhoh.executeUpdate("UPDATE LIBRARIAN SET EMPLOYMENT_DATE = 131211 WHERE USERNAME = 'l.oreilly'");
			//uhohRes.next();
			//System.out.println(uhohRes.getString(2) + " " + uhohRes.getString(3));
			//Librarian u1 = new Librarian("l.oreilly", "Liam", "OReilly", "07706545232", "Trafalgar Place, Brynmill, SA2 0DC", null, 18, new Date(14, 12, 12));
			//addUser(u1);
			//Librarian meme = (Librarian)getUser("l.oreilly");
			//System.out.println("Hi, " + meme.getForename() + meme.getSurname() + meme.getPhoneNumber() + meme.getAddress() + meme.getUsername() + meme.getProfileImage() + meme.getStaffNumber() + " empdate: " + meme.getEmploymentDate().getDay() + meme.getEmploymentDate().getMonth() + meme.getEmploymentDate().getYear());
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	
	public DatabaseRequest() {
		establishConnection();
	}
	
	private static void establishConnection() {
		try {
			conn = DriverManager.getConnection("jdbc:derby:" + DATABASE_NAME);
		} catch (SQLException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	
	public void addUser(User newUser) throws SQLException {
		// Two queries are added to a batch, then run sequentially:
		//		- one inserts the user into the LIBRARY_USER table,
		//		- the other inserts the user into either the LIBRARIAN or BORROWER table
		
		// Reformatting image for database insertion
		String imageFilename;
		if (newUser.getProfileImage() == null) {
			imageFilename = "";
		} else {
			imageFilename = newUser.getProfileImage().getImage();
		}
		
		// user table insertion
		Statement queries = conn.createStatement();
		queries.addBatch("INSERT INTO LIBRARY_USER VALUES(" +
								"'" + newUser.getUsername() + "', " +
								"'" + newUser.getForename() + "', " +
								"'" + newUser.getSurname() + "'," +
								"'" + newUser.getPhoneNumber() + "', " +
								"'" + newUser.getAddress() + "', " +
								"'" + imageFilename + "')");
		
		// librarian/borrower table insertion	
		if (newUser instanceof Librarian) {
			// Reformatting date for database insertion
			Date ed = ((Librarian)newUser).getEmploymentDate();
			String employmentDate = String.valueOf(ed.getDay() + ed.getMonth() + ed.getYear());
			
			queries.addBatch(
					"INSERT INTO LIBRARIAN VALUES('" + newUser.getUsername() + "', " +
							 employmentDate + ", " +
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
		
		query.addBatch("UPDATE LIBRARY_USER SET" +
						"forename = '" + newDetails.getForename() + "', " +
						"surname = '" + newDetails.getForename() + "', " +
						"phone_number = '" + newDetails.getPhoneNumber() + "', " +
						"address = '" + newDetails.getAddress() + "', " +
						"profile_image = '" + newDetails.getProfileImage().getImage() + "' " +
						"WHERE username = '" + newDetails.getUsername() + "'");
		
		if (newDetails instanceof Librarian) {
			query.addBatch("UPDATE LIBRARIAN SET " +
							"staff_number = " + ((Librarian) newDetails).getStaffNumber() + ", " +
							"employment_date = " + ((Librarian) newDetails).getEmploymentDate() + " " +
							"WHERE username = '" + newDetails.getUsername() + "'");
		} else {
			query.addBatch("UPDATE BORROWER SET" +
					"balance = " + ((Borrower) newDetails).getBalance() + ", " +
					"WHERE username = '" + newDetails.getUsername() + "'");
		}
		query.executeBatch();	// Runs the queued queries sequentially
	}
	
	public void deleteUser(String username) throws SQLException {
		Statement query = conn.createStatement();
		
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
			
			String ed = results.getString(8);	// employment date
			Date empDate = new Date(Integer.parseInt(ed.substring(0, 2)),
									Integer.parseInt(ed.substring(2, 4)),
									Integer.parseInt(ed.substring(4, 6)));
			
			out = new Librarian(username,
					results.getString(2),	// forename
					results.getString(3),	// surname
					results.getString(4),	// phone number
					results.getString(5),	// address
					new UserImage(results.getString(6)),	// profile image
					results.getInt(7),	// balance
					empDate);
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
	
	// TODO needs fixing like adduser was
	public void addResource(Resource newResource) throws SQLException {
		// Two queries are run; one inserts the resource into the RESOURCE table,
		//						the other inserts the resource into either the BOOK, DVD, or LAPTOP table

		// resource table insertion
		Statement genQuery = conn.createStatement();
		genQuery.executeQuery("INSERT INTO RESOURCE VALUES(" +
				"'" + newResource.getResourceID() + "', " +
				"'" + newResource.getTitle() + "', " +
				"'" + newResource.getYear() + "'," +
				"'" + newResource.getThumbnail().getImage() + "')");

		// librarian/borrower table insertion
		StringBuilder custQuery = new StringBuilder("INSERT INTO ");

		if (newResource instanceof Book) {
			custQuery.append(
					"BOOK VALUES('" + newResource.getResourceID() + "', " +
							((Book)newResource).getAuthor() + ", " +
							((Book)newResource).getPublisher() + ", " +
							((Book)newResource).getGenre() + ", " +
							((Book)newResource).getISBN() + ", " +
							((Book)newResource).getLanguage() + ")");
		} else if (newResource instanceof DVD) {
			custQuery.append(
					"DVD VALUES('" + newResource.getResourceID() + "', " +
							((DVD)newResource).getDirector() + ", " +
							((DVD)newResource).getRuntime() + ", " +
							((DVD)newResource).getLanguage() + ", ");
			
			String subtitleLanguages = "";
			
			for (String language : ((DVD)newResource).getSubLang()) {
				subtitleLanguages = subtitleLanguages + language + ",";
			}
			
			custQuery.append("'" + subtitleLanguages + "')");
		} else {
			custQuery.append(
					"LAPTOP VALUES('" + newResource.getResourceID() + "', " +
							((Laptop)newResource).getManufacturer() + ", " +
							((Laptop)newResource).getModel() + ", " +
							((Laptop)newResource).getOperatingSys() + ")");
		}
		
		Statement query = conn.createStatement();
		query.executeQuery(custQuery.toString());	// Collate the statements and execute the query
	}
	
	///not finished don't use!!!!////////////////////////////////////
	public static ArrayList<ArrayList<String>> retrieve(String name) throws SQLException {
		return retrieve(name, null);
	}
	
	public static ArrayList<ArrayList<String>> retrieve(String name, String condition) throws SQLException {
		name = name.toUpperCase();
		
		// Alters request for USER table to LIBRARY_USER table in order to match actual database table name
		if (name == "USER") {
			name = "LIBRARY_USER";
		}
		
		Statement query = conn.createStatement();
		ResultSet results;
		
		if (condition == null) {
			// If we haven't been given a condition, then just select all from table
			results = query.executeQuery("SELECT * FROM " + name);
		} else {
			// If we have been given a condition, then refine the query
			results = query.executeQuery("SELECT * FROM " + name + " WHERE " + condition);	// Run the SELECT query with the filter criteriaResultSet results = executeBrowse(name, condition);			
		}
		
		// Get the number of columns in the table from its metadata
		ResultSetMetaData resmd = results.getMetaData();
		int columnCount = resmd.getColumnCount();
		
		ArrayList<String> attributes = new ArrayList<>(columnCount);	// Stores the data for each row in the table
		ArrayList<ArrayList<String>> rows = new ArrayList<>();	// Stores the rows of the table

		while (results.next()) {
			int i = 1;
			while (i <= columnCount) {
				attributes.add(results.getString(i++));	// Get value at column i, then immediately increment i
			}
			rows.add(new ArrayList<>(attributes));	// Adds the current row to the output list
			attributes.clear();	// Clears the data for the current row, ready for the next
		}
		return rows;
	}
}