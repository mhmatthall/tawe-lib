
/**
 * @author Constantinos Loizou
 */

import java.awt.Window;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
<<<<<<< HEAD
import javafx.scene.layout.HBox;
=======
>>>>>>> 415fefa4a316030ef16f74d65d41c3dd1e5250e3
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.*;

public class DashboardController {

	private User user;

	@FXML
	Label lblWelcome;
	@FXML
	Label lblUsername;
	@FXML
	Button btnExit;
	@FXML
	Button btnLogout;
	@FXML
	Button btnEdit;
<<<<<<< HEAD
	@FXML ImageView profImg;
	@FXML HBox upperElements;

	private Stage window;
=======
	@FXML
	public ImageView userimage;
>>>>>>> 415fefa4a316030ef16f74d65d41c3dd1e5250e3

	@FXML
	private void editProfile() {
		int selection = SelectionBox.display("Select", "What do you want to edit?", "Edit Personal Details",
				"Select new Profile Picture from Library", "Draw your own profile picture");
		try {
			switch (selection) {
			case 1:
				// Edit profile details
				break;
			case 2:
				loadImageSelecter();
				break;
			case 3:
				// Draw custom image
				loadImageDrawer();
				break;
			// TODO: Throw an exception if the window has closed an no value returned.
			}
		} catch (IOException e) {
			System.out.println("Caught IO Exception coming from " + e.getCause() + e.getClass() + " from class "
					+ this.getClass().toString());
			System.out.println(e.getMessage());
			System.out.println("\n");
			e.printStackTrace();
			Platform.exit();
		}
	}

	@FXML
	public void exit() {
		boolean exit = ConfirmationBox.display("Exit", "Are you sure you want to exit?");
		if (exit) {
			Platform.exit();
		}
	}

	@FXML
	public void logout() throws IOException {
		Stage window = (Stage) btnExit.getScene().getWindow();
		Pane previous = FXMLLoader.load(getClass().getResource("Welcome.fxml"));
		window.setScene(new Scene(previous));
	}

	private void loadImageDrawer() throws IOException {
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateImage.fxml"));
		Pane pane = loader.load();
		ImageController controller = loader.getController();
		controller.setUser(user);
		Scene scene = new Scene(pane);
		window.setScene(scene);
		controller.passStageReference(window);
		window.show();
	}
	private void loadImageSelecter() throws IOException {
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectUserImage.fxml"));
		Pane pane = loader.load();
		SelectUserImageController controller = loader.getController();
		controller.setUser(user);
		Scene scene = new Scene(pane);
		window.setScene(scene);
		controller.passStageReference(window);
		window.show();
	}

	public void setUser(User user) {
		this.user = user;
		userimage.setImage(new Image(user.getProfileImage().getImage()));
		lblUsername.setText("Username: " + user.getUsername());
		lblWelcome.setText("Welcome " + user.getForename() + " " + user.getSurname());
	}

	public User getUser() {
		return user;
	}

<<<<<<< HEAD
	public void passStageReference(Stage window) {
		this.window = window;
=======
	public void initialize() {
>>>>>>> 415fefa4a316030ef16f74d65d41c3dd1e5250e3
		
	}

}
