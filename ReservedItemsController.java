import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/** Checks user's reserved copies
 * 
 * @author Caleb Warburton
 *
 */

public class ReservedItemsController {
	private Stage window;
	public User user;

	@FXML
	public Label lblTitle;
	@FXML
	public ListView<String> reservations;
	@FXML
	public Button exit;

	/**
	 * Closing the window.
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
	 * Retrieve user's reserved copies from the database.
	 *
	 * @param user the new user
	 * @throws SQLException if cannot connect to the Database
	 */
	public void setUser(User user) throws SQLException {
		this.user = user;
		ArrayList<Copy> items = new DatabaseRequest().getUserReservedCopies(user.getUsername());
		Resource resources;
		ArrayList<String> details = new ArrayList<>();
		for (Copy copy : items) {
			resources = new DatabaseRequest().getResource(copy.getResourceID());
			details.add("ID: " + resources.getResourceID() + " Title: " + resources.getTitle());
		}
		ObservableList<String> values = FXCollections.observableArrayList(details);
		reservations.setItems(values);
	}

}
