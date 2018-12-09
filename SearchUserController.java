import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Constantinos Loizou
 *
 */
public class SearchUserController {

	private Stage window;
	private String userID;
	private User user;

	@FXML
	Button btnSearch;
	@FXML
	Button btnCancel;
	@FXML
	TextField txtUserID;

	@FXML
	private void searchUser() throws SQLException {

		userID = txtUserID.getText();

		try {
			user = new DatabaseRequest().getUser(userID);
			// System.out.println("Successfully found user " + user.toString());
			window.close();
			editUser(user);
		} catch (SQLException e1) {
			AlertBox.display("User Not Found");
			txtUserID.setText("");
			return;
		} catch (IOException e2) {
			System.out.println("Caught IO Exception coming from class " + this.getClass().toString());
			System.out.println(e2.getMessage());
			System.out.println("\n");
			e2.printStackTrace();
		}
	}

	private void editUser(User user) throws IOException {
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/EditUser.fxml"));
		Pane pane = loader.load();
		EditUserController controller = loader.getController();
		Scene scene = new Scene(pane);
		window.setScene(scene);
		controller.passStageReference(window);
		controller.setUser(user);
		window.show();

	}

	/*
	 * FUNDAMENTAL CHANGE NEEDED. REMOVE LIST VIEW AND HAVE A PLAIN SEARCH TEXTFIELD
	 * IF USER IS NOT FOUND POP OUT NEW ALERT BOX IF USER IS FOUND SWITCH SCENE TO
	 * USER CONTROL SCENE
	 */

	@FXML

	private void close() {
		window.close();
	}

	@FXML
	public void onEnter(ActionEvent ae) throws SQLException {
		searchUser();
	}

	public void passStageReference(Stage window) {
		this.window = window;
	}

}
