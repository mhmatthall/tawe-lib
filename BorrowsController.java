import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/** 
 * Displays what the borrower is currently borrowing from the Library
 * 
 * @author Caleb Warburton
 *
 */
public class BorrowsController {
	private Stage window;
	public User user;
	
	@FXML
	public Label lblTitle;
	@FXML
	public ListView<String> borrows;
	@FXML
	public Button exit;
	
	/**
	 * Closes the window.
	 */
	@FXML
	private void close() {
		window.close();
	}
	

	
	/**
	 * Passes current stage onto next class to load new scene on it.
	 * Closes and reverts to previous stage.
	 *
	 * @param window the window
	 */
	public void passStageReference(Stage window) {
		this.window = window;
	}
	
	/**
	 * Retrieves what the borrower is borrowing from the database.
	 *
	 * @param user the new user
	 * @throws SQLException Cannot connect to the Database
	 */
	public void setUser(User user) throws SQLException {
		this.user = user;
		ArrayList<Loan> items = new DatabaseRequest().getUserCopiesOnLoan(user.getUsername());		
		Resource resources;
		Copy copies;
		ArrayList<String> details = new ArrayList<>();
		DatabaseRequest db = new DatabaseRequest();
		for (Loan loan : items) {
			if (loan.isReturned() == false) {
				copies = db.getCopy(loan.getCopyID());
				resources = db.getResource(copies.getResourceID());
				details.add("ID: " + resources.getResourceID() + " Title: " + resources.getTitle() + "Date Due: " + loan.getReturnDate().toString());
			}
		}
		ObservableList<String> values = FXCollections.observableArrayList(details);
		borrows.setItems(values);
	}
	

}