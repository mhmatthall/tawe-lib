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


/** Searches Users in the Library
 *
 * @author Constantinos Loizou
 *
 */
public class SearchUserController {

	private Stage window;
	private String userID;
	private User user;
	private User sessionUser;

	@FXML
	Button btnSearch;
	@FXML
	Button btnCancel;
	@FXML
	TextField txtUserID;

	/**
	 * Searches Database for user using userID.
	 *
	 * @throws SQLException fails to connect to Database
	 */
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

	/**
	 * Edits the user.
	 *
	 * @param user the user
	 * @throws IOException if file "EditUser.fxml" cannot be found
	 */
	private void editUser(User user) throws IOException {
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/EditUser.fxml"));
		Pane pane = loader.load();
		EditUserController controller = loader.getController();
		Scene scene = new Scene(pane);
		window.setScene(scene);
		controller.passStageReference(window);
		controller.setUser(user);
		controller.setEditor(sessionUser);
		window.show();

	}

	

	/**
	 * Closes window.
	 */
	@FXML

	private void close() {
		window.close();
	}

	/**
	 * Searches on pressing enter.
	 *
	 * @param ae action event
	 * @throws SQLException if cannot connect to Database
	 */
	
	@FXML
	public void onEnter(ActionEvent ae) throws SQLException {
		searchUser();
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
	 * Sets the session user.
	 *
	 * @param sessionUser the new session user
	 */
	
	public void setSessionUser(User sessionUser) {
		this.sessionUser = sessionUser;
	}

}
