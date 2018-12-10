import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/** To check user's reserved copies
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
	 * Pass stage reference.
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
	 * @throws SQLException the SQL exception
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
