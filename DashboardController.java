import java.awt.Window;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.*;

/**
 * Borrower's dashboard
 * 
 * @author Constantinos Loizou
 */
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
	@FXML
	Button btnSearch;
	@FXML
	ImageView profImg;
	@FXML
	HBox upperElements;
	@FXML
	public ImageView userimage;

	private Stage window;

	/**
	 * Method for edit profile button, calls a pop up window and asks what you want
	 * to edit
	 */
	@FXML
	private void editProfile() {
		int selection = SelectionBox.display("Select", "What do you want to edit?", "Edit Personal Details",
				"Select new Profile Picture from Library", "Draw your own profile picture");
		try {
			switch (selection) {
			case 1:
				editDetails();
				break;
			case 2:
				loadImageSelecter();
				break;
			case 3:
				loadImageDrawer();
				break;
			default:
				return;
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

	/**
	 * Switches to a window for edditing user's details
	 *
	 * @throws IOException if file "EditUser.fxml" does not exist
	 */
	private void editDetails() throws IOException {
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/EditUser.fxml"));
		Pane pane = loader.load();
		EditUserController controller = loader.getController();
		Scene scene = new Scene(pane);
		window.setScene(scene);
		controller.passStageReference(window);
		controller.setUser(user);
		controller.setEditor(user);
		window.show();

	}
	
	private void openReservations() throws IOException, SQLException {
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/ReservedResources.fxml"));
		Pane pane = loader.load();
		ReservedItemsController controller = loader.getController();
		Scene scene = new Scene(pane);
		window.setScene(scene);
		controller.passStageReference(window);
		controller.setUser(user);
		window.show();

	}

	/**
	 * Method for exit button for closing the program.
	 */
	@FXML
	public void exit() {
		boolean exit = ConfirmationBox.display("Exit", "Are you sure you want to exit?");
		if (exit) {
			Platform.exit();
		}
	}

	/**
	 * Method for logout button which switches the scene to a log in window.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	void searchLibrary() throws IOException {
		Stage window4 = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/SearchLibrary.fxml"));
		Pane pane = loader.load();
		SearchLibraryController controller = loader.getController();
		Scene scene = new Scene(pane);
		window4.setScene(scene);
		controller.passStageReference(window4);
		window4.show();
	}

	@FXML
	public void logout() throws IOException {
		Stage window = (Stage) btnExit.getScene().getWindow();
		Pane previous = FXMLLoader.load(getClass().getResource("/fxml_files/Welcome.fxml"));
		window.setScene(new Scene(previous));
	}

	/**
	 * Load image drawer.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void loadImageDrawer() throws IOException {
		window.close();
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/CreateImage.fxml"));
		Pane pane = loader.load();
		ImageController controller = loader.getController();
		controller.setUser(user);
		Scene scene = new Scene(pane);
		window.setScene(scene);
		controller.passStageReference(window);
		window.show();
	}

	/**
	 * Load image selector.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void loadImageSelecter() throws IOException {
		window.close();
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/SelectUserImage.fxml"));
		Pane pane = loader.load();
		SelectUserImageController controller = loader.getController();
		controller.setUser(user);
		Scene scene = new Scene(pane);
		window.setScene(scene);
		controller.passStageReference(window);
		window.show();
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(User user) {
		this.user = user;
		userimage.setImage(new Image(user.getProfileImage().getImage()));
		lblUsername.setText("Username: " + user.getUsername());
		lblWelcome.setText("Welcome " + user.getForename() + " " + user.getSurname());
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Pass stage reference.
	 *
	 * @param window the window
	 */
	public void passStageReference(Stage window) {
		this.window = window;
	}

}
