import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* DatabaseRequest
   @author Matt Hall
*/

public class DatabaseRequest {
	private static final String databaseName = "tawe-lib";
	
	public static void main(String[] args) {
		establishConnection();
	}
	
	public static void establishConnection() {
		Connection conn;
		try {
			conn = DriverManager.getConnection("jdbc:derby:" + databaseName);
			System.out.println("Connection successful?: " + conn.isValid(0));
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}