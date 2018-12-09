import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ReservedItemsController {
	private Stage window;
	public User user;
	
	@FXML
	public Label lblTitle;
	@FXML
	public ListView<String> reservations;
	@FXML
	public Button exit;
	
	@FXML
	private void close() {
		window.close();
	}
	

	
	public void passStageReference(Stage window) {
		this.window = window;
	}
	
	public void setUser(User user) throws SQLException {
		this.user = user;
		ArrayList<Copy> items = new DatabaseRequest().getUserReservedCopies(user.getUsername());
		Resource resources;
		ArrayList<String> details = new ArrayList<>();
		for (Copy copy : items) {
			resources = new DatabaseRequest().getResource(copy.getResourceID());
			details.add(resources.toString());
		}
		ObservableList<String> values = FXCollections.observableArrayList(details);
		reservations.setItems(values);
	}
	

}
