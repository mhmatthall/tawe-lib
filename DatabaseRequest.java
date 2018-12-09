import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

/** DatabaseRequest
 *  @author Matt Hall
 *  @version 1.0
 */
public class DatabaseRequest {
	// Name (and subsequently directory) of the database
	private static final String DATABASE_NAME = "tawe-lib";
	private static Connection conn;

	/**
	 * Instantiates a new database request, tries to establish a connection.
	 *
	 * @throws SQLException if it fails to establish a connection
	 */
	public DatabaseRequest() throws SQLException {
		establishConnection();
	}

	/**
	 * Establish connection to the database.
	 *
	 * @throws SQLException if connection fails to be established
	 */
	private void establishConnection() throws SQLException {
		// Prompts the driver file (derby.jar) to load the database
		conn = DriverManager.getConnection("jdbc:derby:" + DATABASE_NAME);
		
		/*
		 * This sets the transaction data isolation settings for the database.
		 * This setting, READ_UNCOMMITTED, allows a row to be accessed whilst it
		 * is being edited.
		 * 
		 * Others run queries concurrently, which creates a deadlock and times out
		 * the connection.
		 */
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
	}

	/**
	 * Adds a new user into the database.
	 *
	 * @param newUser the user to be added
	 * @throws SQLException if the connection fails to access the Database
	 */
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

	/**
	 * Edits user already inside the database.
	 *
	 * @param newDetails user with new details
	 * @throws SQLException if accessing database failed
	 */
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

	/**
	 * Deletes the user from the database.
	 *
	 * @param username of user to be deleted
	 * @throws SQLException if access to database fails
	 */
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

	/**
	 * Gets the user from the database.
	 *
	 * @param username of the user
	 * @return the user if successful, null if user doesn't exist
	 * @throws SQLException if accessing the database fails
	 */
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

	/**
	 * Checks whatever the user is a lib.
	 *
	 * @param username of the user
	 * @return true if user is librarian
	 * @throws SQLException if accessing database has failed
	 */
	private boolean userIsLibrarian(String username) throws SQLException {
		Statement userTypeCheck = conn.createStatement();
		ResultSet rs = userTypeCheck.executeQuery("SELECT COUNT(*) FROM LIBRARIAN WHERE username = '" + username + "'");
		rs.next();
		int userType = rs.getInt(1);	// userType = 0 if borrower, 1 if librarian
		return (userType == 1);
	}

	/**
	 * Adds resource to the database.
	 *
	 * @param newResource resource to be added
	 * @throws SQLException if connection to the database has failed
	 */
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
				newResource.getYear() + ", " +
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
							"" + ((DVD)newResource).getRuntime() + ", " +
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

	/**
	 * Edits the resource in a database.
	 *
	 * @param newDetails the new details of the resource
	 * @throws SQLException if connection to the database has failed
	 */
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

	/**
	 * Deletes the resource from the database.
	 *
	 * @param resourceID of the resource to be deleted
	 * @throws SQLException if connection to database has failed
	 */
	public void deleteResource(String resourceID) throws SQLException {
		Statement query = conn.createStatement();

		// Must delete from subclass tables first as to not break dependencies
		query.addBatch("DELETE FROM " + getResourceType(resourceID) + " WHERE resource_id = '" + resourceID + "'");

		query.addBatch("DELETE FROM RESOURCE WHERE resource_id = '" + resourceID + "'");

		query.executeBatch();
	}

	/**
	 * Gets the resource from the database.
	 *
	 * @param resourceID ID of a resource to be retrieved from the database
	 * @return the resource
	 * @throws SQLException if connection to the database fails
	 */
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

	/**
	 * Gets the resource type, <strong>assuming resource already exists!</strong>
	 *
	 * @param resourceID ID of a resource
	 * @return the resource type
	 * @throws SQLException if connection to the database has failed
	 */
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

	/**
	 * Convert string of queue from database into RequestQueue
	 *
	 * @param queue as a string
	 * @return the request queue
	 * @throws SQLException if connection to database has failed
	 */
	private RequestQueue convertRequestQueue(String queue) throws SQLException {
		String[] requests = queue.split(",");

		RequestQueue rq = new RequestQueue();
		for (int i = 0; i < requests.length; i++) {
			rq.addUser(requests[i]);
		}

		return rq;
	}

	/**
	 * Checks if there are any available copies of a given resource
	 *
	 * @param resourceID of a resource we are checking available copies for
	 * @return true if there are available copies
	 * @throws SQLException if the connection to the database has failed
	 */
	public boolean checkAvailability(String resourceID) throws SQLException {
		Statement query = conn.createStatement();
		ResultSet results = query.executeQuery("SELECT COUNT(*) "
				+ "FROM COPY "
				+ "WHERE resource_id = '" + resourceID + "' "
				+ "AND is_reserved = 0 "
				+ "AND is_on_loan = 0");

		results.next();
		
		if (results.getInt(1) == 0) {
			return false;
		}

		return true;
	}
	
	/**
	 * Adds a copy to the database.
	 *
	 * @param newCopy a copy to be added
	 * @throws SQLException if connection to the database has failed
	 */
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

	/**
	 * Edits details of a copy.
	 *
	 * @param newDetails a copy with new details
	 * @throws SQLException if connection to the database has failed
	 */
	public void editCopy(Copy newDetails) throws SQLException {
		// Format boolean true/false to 1/0 for database insertion
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

	/**
	 * Delete copy.
	 *
	 * @param copyID the copyID fo a copy to be deleted
	 * @throws SQLException if connection to the database has failed
	 */
	public void deleteCopy(String copyID) throws SQLException {
		Statement query = conn.createStatement();

		query.executeUpdate("DELETE FROM COPY WHERE copy_id = '" + copyID + "'");
	}

	/**
	 * Gets copy from the database.
	 *
	 * @param copyID of a copy to be fetched
	 * @return the copy
	 * @throws SQLException if connection to the database has failed
	 */
	public Copy getCopy(String copyID) throws SQLException {
		Statement query = conn.createStatement();
		ResultSet results = query.executeQuery("SELECT * FROM COPY WHERE COPY_ID = '" + copyID + "'");

		results.next();

		// Format integer 1/0 from database back into boolean true/false
		boolean isOnLoan = results.getInt(4) != 0;
		boolean isReserved = results.getInt(5) != 0;

		Copy out = new Copy(copyID,
				results.getString(2),
				results.getInt(3),
				isOnLoan,
				isReserved,
				results.getString(6));

		return out;
	}

	/**
	 * Gets all the copies of a given resource
	 * @param resourceID of which copies we want to look up
	 * @return ArrayList of copies
	 * @throws SQLException if connection to the database has failed
	 */
	public ArrayList<Copy> getCopies(String resourceID) throws SQLException {
		Statement query = conn.createStatement();
		ResultSet results = query.executeQuery("SELECT * FROM COPY WHERE resource_id = '" + resourceID + "'");
		
		boolean isOnLoan;
		boolean isReserved;
		Copy currentCopy;
		ArrayList<Copy> out = new ArrayList<Copy>();
		
		while (results.next()) {
			// Format integer 1/0 from database back into boolean true/false
			isOnLoan = results.getInt(4) != 0;
			isReserved = results.getInt(5) != 0;
			
			currentCopy = new Copy(results.getString(1),
					results.getString(2),
					results.getInt(3),
					isOnLoan,
					isReserved,
					results.getString(6));
			
			out.add(currentCopy);
		}
		
		return out;
	}
	
	/**
	 * Gets all the copies that are reserved for a user.
	 *
	 * @param username username
	 * @return ArrayList of reserved copies
	 * @throws SQLException if connection to the database has failed
	 */
	public ArrayList<Copy> getUserReservedCopies(String username) throws SQLException {
		Statement query = conn.createStatement();
		ResultSet results = query.executeQuery("SELECT * FROM COPY WHERE reserved_by_user_id = '" + username + "'");
		
		boolean isOnLoan;
		boolean isReserved;
		Copy currentCopy;
		ArrayList<Copy> out = new ArrayList<Copy>();
		
		while (results.next()) {
			// Format integer 1/0 from database back into boolean true/false
			isOnLoan = results.getInt(4) != 0;
			isReserved = results.getInt(5) != 0;
			
			currentCopy = new Copy(results.getString(1),
					results.getString(2),
					results.getInt(3),
					isOnLoan,
					isReserved,
					results.getString(6));
			
			out.add(currentCopy);
		}
		
		return out;
	}
	
	/**
	 * Gets all available copies of a given resource from the database.
	 *
	 * @param resourceID
	 * @return ArrayList of the available copies
	 * @throws SQLException if connection to the database has failed
	 */
	public ArrayList<Copy> getAvailableCopies(String resourceID) throws SQLException {
		Statement query = conn.createStatement();
		ResultSet results = query.executeQuery("SELECT * FROM COPY "
				+ "WHERE resource_id = '" + resourceID + "' "
				+ "AND is_on_loan = 0 "
				+ "AND is_reserved = 0");
		
		boolean isOnLoan;
		boolean isReserved;
		Copy currentCopy;
		ArrayList<Copy> out = new ArrayList<Copy>();
		
		while (results.next()) {
			// Format integer 1/0 from database back into boolean true/false
			isOnLoan = results.getInt(4) != 0;
			isReserved = results.getInt(5) != 0;
			
			currentCopy = new Copy(results.getString(1),
					results.getString(2),
					results.getInt(3),
					isOnLoan,
					isReserved,
					results.getString(6));
			
			out.add(currentCopy);
		}
		
		return out;
	}
	
	/**
	 * gets all the borrowers that are registered in the database.
	 *
	 * @return ArrayList of borrowers
	 * @throws SQLException tif connection to the database has failed
	 */
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

	/**
	 * gets all the resources from the database
	 *
	 * @return ArrayList of all resources
	 * @throws SQLException if connection to the database has failed
	 */
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

	/**
	 * Adds loan to the database.
	 *
	 * @param newLoan the loan to be added to the database
	 * @throws SQLException if connection to the database fails
	 */
	public void addLoan(Loan newLoan) throws SQLException {
		// Format boolean true/false to 1/0 for database insertion
		int isReturned = newLoan.isReturned() ? 1 : 0;

		Statement query = conn.createStatement();
		query.executeUpdate("INSERT INTO LOAN VALUES(" +
				"'" + newLoan.getLoanID() + "', " +
				"'" + newLoan.getIssueDate().toString() + "', " +
				"'" + newLoan.getUsername() + "', " +
				"'" + newLoan.getCopyID() + "', " +
				"'" + newLoan.getReturnDate().toString() + "', " +
				isReturned + ")");
	}
	
	/**
	 * Edits loan in the database.
	 *
	 * @param newDetails Loan with new details
	 * @throws SQLException if connection to the database has failed
	 */
	public void editLoan(Loan newDetails) throws SQLException {
		// Format boolean true/false to 1/0 for database insertion
		int isReturned = newDetails.isReturned() ? 1 : 0;

		Statement query = conn.createStatement();
		query.executeUpdate("UPDATE LOAN SET " +
				"issue_date = '" + newDetails.getIssueDate().toString() + "', " +
				"username = '" + newDetails.getUsername() + "', " +
				"copy_id = '" + newDetails.getCopyID() + "', " +
				"return_date = '" + newDetails.getReturnDate().toString() + "', " +
				"is_returned = " + isReturned + " " +
				"WHERE loan_id = '" + newDetails.getCopyID() + "'");
	}
	
	/**
	 * Deletes the loan from the database.
	 *
	 * @param loanID for the loan to be deleted
	 * @throws SQLException if connection to database has failed
	 */
	public void deleteLoan(String loanID) throws SQLException {
		Statement query = conn.createStatement();
		query.executeUpdate("DELETE FROM LOAN WHERE loan_id = '" + loanID + "'");
	}
	
	/**
	 * Gets a loan from the database.
	 *
	 * @param loanID for specifying which loan to be fetched
	 * @return Loan
	 * @throws SQLException if connection to the database has failed
	 */
	public Loan getLoan(String loanID) throws SQLException {
		Statement query = conn.createStatement();
		ResultSet results = query.executeQuery("SELECT * FROM LOAN WHERE loan_id = '" + loanID + "'");

		results.next();

		// Format integer 1/0 from database back into boolean true/false
		boolean isReturned = results.getInt(6) != 0;

		Loan out = new Loan(loanID,
				new Date(results.getString(2)),
				results.getString(3),
				results.getString(4),
				new Date(results.getString(5)),
				isReturned);

		return out;
	}
	
	/**
	 * Gets the loan history of a given copy?.
	 *
	 * @param copyID of the copies history to fetch
	 * @return ArrayList of loans
	 * @throws SQLException if connection to the database has failed
	 */
	public ArrayList<Loan> getLoanHistory(String copyID) throws SQLException {
		Statement query = conn.createStatement();

		ResultSet results = query.executeQuery("SELECT * FROM LOAN WHERE copy_id = '" + copyID + "'");

		ArrayList<Loan> out = new ArrayList<Loan>();
		Loan temp;
		boolean isReturned = results.getInt(6) != 0;

		while (results.next()) {
			temp = new Loan(results.getString(1),	// loanID
					new Date(results.getString(2)),	// issue date
					results.getString(3),	// username
					results.getString(4),	// copyID
					new Date(results.getString(5)),
					isReturned);	// return date

			out.add(temp);
		}

		return out;
	}

	/**
	 * Gets the oldest loan.
	 *
	 * @param resourceID 
	 * @return the oldest loan
	 * @throws SQLException if connection to the database has failed
	 */
	public Loan getOldestLoan(String resourceID) throws SQLException {
		Statement query = conn.createStatement();
		ResultSet results = query.executeQuery("SELECT LOAN.* "
				+ "FROM LOAN INNER JOIN COPY ON LOAN.COPY_ID = COPY.COPY_ID "
				+ "WHERE COPY.RESOURCE_ID = '" + resourceID + "' "
				+ "AND COPY.IS_ON_LOAN = 1");

		ArrayList<Loan> loans = new ArrayList<Loan>();
		Loan temp;
		boolean isReturned = results.getInt(6) != 0;

		while (results.next()) {
			temp = new Loan(results.getString(1),	// loanID
					new Date(results.getString(2)),	// issue date
					results.getString(3),	// username
					results.getString(4),	// copyID
					new Date(results.getString(5)),	// return date
					isReturned);

			loans.add(temp);
		}

		Loan oldestLoan = new Loan("", new Date(1, 1, 1), "", "", new Date(), false);

		for (Loan loanToCompare : loans) {
			if (oldestLoan.getIssueDate().isBefore(loanToCompare.getIssueDate())) {
				oldestLoan = loanToCompare;
			}
		}

		return oldestLoan;
	}

	/**
	 * Gets overdue loans from the database 
	 *
	 * @return ArrayList of loans
	 * @throws SQLException if connection to database has failed
	 */
	public ArrayList<Loan> getOverdueLoans() throws SQLException {
		Statement query = conn.createStatement();
		ResultSet results = query.executeQuery("SELECT * FROM LOAN");

		ArrayList<Loan> overdueLoans = new ArrayList<Loan>();
		Loan currentLoan;
		Date currentDate = new Date();
		boolean isReturned = results.getInt(6) != 0;

		while (results.next()) {
			currentLoan = new Loan(results.getString(1),	// loanID
					new Date(results.getString(2)),	// issue date
					results.getString(3),	// username
					results.getString(4),	// copyID
					new Date(results.getString(5)),	// return date
					isReturned);

			if (currentDate.isBefore(currentLoan.getReturnDate())) {
				overdueLoans.add(currentLoan);
			}
		}

		return overdueLoans;
	}

	/**
	 * Gets all copies a given user has on loan.
	 *
	 * @param username of a user
	 * @return arrayList of copies
	 * @throws SQLException if connection to database has failed
	 */
	public ArrayList<Copy> getUserCopiesOnLoan(String username) throws SQLException {
		Statement query = conn.createStatement();
		ResultSet results = query.executeQuery("SELECT * FROM "
				+ "COPY INNER JOIN LOAN ON COPY.copy_id = LOAN.copy_id " +
				"WHERE username = '" + username + "' " +
				"AND is_returned = 0");

		ArrayList<Copy> userLoans = new ArrayList<Copy>();
		Copy currentCopy;

		while (results.next()) {
			// Format integer 1/0 from database back into boolean true/false
			boolean isOnLoan = results.getInt(4) != 0;
			boolean isReserved = results.getInt(5) != 0;

			currentCopy = new Copy(results.getString(1),
					results.getString(2),
					results.getInt(3),
					isOnLoan,
					isReserved,
					results.getString(6));

			userLoans.add(currentCopy);
		}

		return userLoans;
	}

	/**
	 * Adds a fine to the database.
	 *
	 * @param newFine to be added to a database
	 * @throws SQLException if connection to the database has failed
	 */
	public void addFine(Fine newFine) throws SQLException {
		// Format boolean true/false to 1/0 for database insertion
		int isPaid = newFine.isPaid() ? 1 : 0;

		Statement query = conn.createStatement();
		query.executeUpdate("INSERT INTO FINE VALUES(" +
				"'" + newFine.getFineID() + "', " +
				newFine.getAmount() + ", " +
				newFine.getAmountPaid() + ", " +
				"'" + newFine.getLoanID() + "', " +
				"'" + newFine.getDatePaid().toString() + "', " +
				"'" + newFine.getDateIssued().toString() + "', " +
				isPaid + ")");
	}
	
	/**
	 * Edits fine in the database.
	 *
	 * @param newDetails fine with new details
	 * @throws SQLException if connection to the database fails
	 */
	public void editFine(Fine newDetails) throws SQLException {
		// Format boolean true/false to 1/0 for database insertion
		int isPaid = newDetails.isPaid() ? 1 : 0;

		Statement query = conn.createStatement();
		query.executeUpdate("UPDATE LOAN SET " +
				"amount = " + newDetails.getAmount() + ", " +
				"amount_paid = " + newDetails.getAmountPaid() + ", " +
				"loan_id = '" + newDetails.getLoanID() + "', " +
				"date_paid = '" + newDetails.getDatePaid().toString() + "', " +
				"date_issued = " + newDetails.getDateIssued().toString() + ", " +
				"is_paid = " + isPaid + " " +
				"WHERE fine_id = '" + newDetails.getFineID() + "'");
	}
	
	/**
	 * Delete fine from the database.
	 *
	 * @param fineID to be deleted
	 * @throws SQLException if connection to the database has failed
	 */
	public void deleteFine(String fineID) throws SQLException {
		Statement query = conn.createStatement();
		query.executeUpdate("DELETE FROM FINE WHERE fine_id = '" + fineID + "'");
	}
	
	/**
	 * Gets fine from the database.
	 * <p>this so monotonous </p>
	 *
	 * @param fineID the fine ID
	 * @return the fine
	 * @throws SQLException the SQL exception
	 */
	public Fine getFine(String fineID) throws SQLException {
		Statement query = conn.createStatement();
		ResultSet results = query.executeQuery("SELECT * FROM FINE WHERE fine_id = '" + fineID + "'");

		results.next();

		// Format integer 1/0 from database back into boolean true/false
		boolean isPaid = results.getInt(7) != 0;

		Fine out = new Fine(fineID,
				results.getDouble(2),
				results.getDouble(3),
				results.getString(4),
				new Date(results.getString(5)),
				new Date(results.getString(6)),
				isPaid);

		return out;
	}

	/**
	 * Total user fines.
	 *
	 * @param username of the user to be checked
	 * @return total amount of a fine
	 * @throws SQLException if connection to database has failed
	 */
	public double totalUserFines(String username) throws SQLException {
		Statement query = conn.createStatement();
		ResultSet results = query.executeQuery("SELECT SUM(amount) - SUM(amount_paid) "
				+ "FROM FINE WHERE username = '" + username + "' "
				+ "AND is_paid = 0");
		
		results.next();
		
		return results.getDouble(1);
	}
	
	public ArrayList<Resource> searchResources(String searchTerm) throws SQLException {
		Statement query = conn.createStatement();
		ResultSet results = query.executeQuery("SELECT resource_id FROM "
				+ "RESOURCE "
				+ "WHERE resource_id LIKE '" + searchTerm + "%', "
				+ "OR title LIKE '" + searchTerm + "%', "
				+ "OR year LIKE '" + searchTerm + "%'");

		ArrayList<Resource> resultsList = new ArrayList<Resource>();
		
		while (results.next()) {
			resultsList.add(getResource(results.getString(1)));
		}
		
		return resultsList;
	}
}