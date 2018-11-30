import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/* DatabaseRequest
   @author Matt Hall
   @version 1.0
   
   TODO Need to remove static attributes (and main method) when testing complete
	+ addResource(newResource : Resource)
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
			System.out.println("USER table: " + viewTable("USER").toString());
			System.out.println("RESOURCE table: " + viewTable("RESOURCE").toString());
			System.out.println("BOOK table: " + viewTable("BOOK").toString());
		} catch (SQLException e) {
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
		Statement genQuery = conn.createStatement();	// General query to update User table
		genQuery.executeQuery("INSERT INTO LIBRARY_USER VALUES(" +
								"'" + newUser.getUsername() + "', " +
								"'" + newUser.getForename() + "', " +
								"'" + newUser.getSurname() + "'," +
								"'" + newUser.getPhoneNumber() + "', " +
								"'" + newUser.getAddress() + "', " +
								"'" + newUser.getProfileImage().filename);
		
		// Custom query changes depending on whether user is Librarian or Borrower
		StringBuilder custQuery = new StringBuilder("INSERT INTO ");
		if (newUser.isLibrarian()) {
			custQuery.append(
					"LIBRARIAN VALUES('" + newUser.getUsername() + "', " +
							((Librarian)newUser).getEmploymentDate() + ", " +
							((Librarian)newUser).getStaffNumber() + ")");
		} else {
			custQuery.append(
					"BORROWER VALUES('" + newUser.getUsername() + "', " +
							((Borrower)newUser).getBalance() + ")");
		}

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
		
		if (newDetails.isLibrarian()) {
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
		query.executeQuery("DELETE FROM LIBRARY_USER WHERE username = '" + username + "'");
	}
	
	public User getUser(String username) throws SQLException {
		Statement query = conn.createStatement();
		
		ResultSet results = query.executeQuery("SELECT * FROM LIBRARY_USER WHERE username = '" + username + "'");
		
		User out = new User(username,
							results.getString("forename"),
							results.getString("surname"),
							results.getString("phone_number"),
							results.getString("address"),
							new UserImage(results.getString("profile_image"))
							);
		
		results.close();
		query.close();
		return out;
	}

	///help////////////////////////////////////
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