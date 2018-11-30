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
 */

public class DatabaseRequest {
	private static final String databaseName = "tawe-lib";
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
	
	// Removed for testing
//	public DatabaseRequest() {
//		establishConnection();
//	}
	
	private static void establishConnection() {
		try {
			conn = DriverManager.getConnection("jdbc:derby:" + databaseName);
		} catch (SQLException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	
	// TODO test addUser
//	public void addUser(User newUser) throws SQLException {
//		Statement genQuery = conn.createStatement();	// General query to update User table
//		genQuery.executeQuery("INSERT INTO LIBRARY_USER VALUES(" +
//								"'" + newUser.getUsername() + "', " +
//								"'" + newUser.getForename() + "', " +
//								"'" + newUser.getSurname() + "'," +
//								"'" + newUser.getPhoneNumber() + "', " +
//								"'" + newUser.getAddress() + "', " +
//								"'" + newUser.getProfileImage().filename);
//		
//		// Custom query changes depending on whether user is Librarian or Borrower
//		StringBuilder custQuery = new StringBuilder("INSERT INTO ");
//		if (newUser.isLibrarian()) {
//			custQuery.append(
//					"LIBRARIAN VALUES('" + newUser.getUsername() + "', " +
//							((Librarian)newUser).getEmploymentDate() + ", " +
//							((Librarian)newUser).getStaffNumber() + ")");
//		} else {
//			custQuery.append(
//					"BORROWER VALUES('" + newUser.getUsername() + "', " +
//							((Borrower)newUser).getBalance() + ")");
//		}
//
//	}
	
	public static ArrayList<ArrayList<String>> viewTable(String name) throws SQLException {
		name = name.toUpperCase();
		
		// Alters request for USER table to LIBRARY_USER table in order to match actual database table name
		if (name == "USER") {
			name = "LIBRARY_USER";
		}
		
		Statement query = conn.createStatement();
		ResultSet results = query.executeQuery("SELECT * FROM " + name);	// Run the SELECT query
		ResultSetMetaData resmd = results.getMetaData();
		int columnCount = resmd.getColumnCount();	// Get the number of columns in the table from its metadata
		
		ArrayList<String> row = new ArrayList<>(columnCount);	// Stores the data for each row in the table
		ArrayList<ArrayList<String>> output = new ArrayList<>();	// Stores the rows of the table

		while (results.next()) {
			int i = 1;
			while (i <= columnCount) {
				row.add(results.getString(i++));	// Get value at column i, then immediately increment i
			}
			output.add(new ArrayList<>(row));	// Adds the current row to the output list
			row.clear();	// Clears the data for the current row, ready for the next
		}
		return output;
	}
}