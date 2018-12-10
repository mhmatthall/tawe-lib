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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller of librarian control panel/dashboard
 * 
 * @author Constantinos Loizou
 */
public class ControlPanelController {

	private User user;
	private Stage window;

	@FXML
	Label lblWelcome;
	@FXML
	Label lblUsername;
	@FXML
	Button btnExit;
	@FXML
	Button btnLogout;
	@FXML
	Button btnCreateResource;
	@FXML
	Button btnSearchUser;
	@FXML
	Button btnSearchLibrary;
	@FXML
	Button btnNewUser;
	@FXML
	Button btnEdit;
	@FXML
	public ImageView libImage;

	/**
	 * Method for exit button.
	 */
	@FXML
	public void exit() {
		boolean exit = ConfirmationBox.display("Exit", "Are you sure you want" + " to exit?");
		if (exit) {
			Platform.exit();
		}
	}

	/**
	 * edit the profile of a user method for update account button.
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
	 * for Logout button, switches from dashboard to welcome scene.
	 *
	 * @throws IOException if it fails to switch scenes.
	 */
	@FXML
	public void logout() throws IOException {
		Stage window = (Stage) btnExit.getScene().getWindow();
		Pane previous = FXMLLoader.load(getClass().getResource("/fxml_files/Welcome.fxml"));
		window.setScene(new Scene(previous));
	}

	/**
	 * Creates the resource by a Pop out of a new window to select resource type and
	 * then creates a resource of that type
	 */
	public void createResource() {
		int type = SelectionBox.display("Select", "Please select resource type", "Book", "DVD", "Laptop");
		try {
			switch (type) {
			case 1:
				createBook();
				break;
			case 2:
				createDVD();
				break;
			case 3:
				createLaptop();
				break;
			}
		} catch (IOException e) {
			System.out.println("Caught IO Exception coming from " + e.getCause() + e.getClass());
			System.out.println(e.getMessage());
			Platform.exit();
		}
	}

	
	/**
	 * Method for a button for creating a laptop.
	 *
	 * @throws IOException if file "NewLaptop.fxml" does not exist.
	 */
	private void createLaptop() throws IOException {
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/NewLaptop.fxml"));
		Pane pane = loader.load();
		NewLaptopController controller = loader.getController();
		Scene scene = new Scene(pane);
		window.setScene(scene);
		controller.passStageReference(window);
		window.show();

	}

	/**
	 * Method for a button for creating a DVD.
	 *
	 * @throws IOException if file "NewDVD.fxml" does not exist.
	 */
	private void createDVD() throws IOException {
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/NewDVD.fxml"));
		Pane pane = loader.load();
		NewDVDController controller = loader.getController();
		Scene scene = new Scene(pane);
		window.setScene(scene);
		controller.passStageReference(window);
		window.show();
	}

	/**
	 * Method for a button for creating a book.
	 *
	 * @throws IOException if file "NewBook.fxml" does not exist.
	 */
	private void createBook() throws IOException {
		Stage window2 = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/NewBook.fxml"));
		Pane pane = loader.load();
		NewBookController controller = loader.getController();
		Scene scene = new Scene(pane);
		window2.setScene(scene);
		controller.passStageReference(window2);
		window2.show();

	}

	/**
	 * Method for a button which leads to resource search.
	 *
	 * @throws IOException if file "SearchLibrary.fxml" does not exist.
	 */
	@FXML
	void searchLibrary() throws IOException {
		Stage window4 = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/SearchLibrary.fxml"));
		Pane pane = loader.load();
		SearchLibraryController controller = loader.getController();
		Scene scene = new Scene(pane);
		window4.setScene(scene);
		controller.setUser(user);
		controller.passStageReference(window4);
		window4.show();
	}

	/**
	 * Method for a button for searching users.
	 *
	 * @throws IOException if file "SearchUser.fxml" does not exist.
	 */
	@FXML
	private void searchUser() throws IOException {
		Stage window3 = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/SearchUser.fxml"));
		Pane pane = loader.load();
		SearchUserController controller = loader.getController();
		Scene scene = new Scene(pane);
		window3.setScene(scene);
		controller.passStageReference(window3);
		controller.setSessionUser(user);
		window3.show();
	}

	/**
	 * Method for a button to edit details.
	 * 
	 * 
	 * @throws IOException if file "EditUser.fxml" does not exist.
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

	/**
	 * Method for a button to make a new user.
	 *
	 * @throws IOException if file "NewUser.fxml" does not exist.
	 */
	@FXML
	private void newUser() throws IOException {
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/NewUser.fxml"));
		Pane pane = loader.load();
		NewUserController controller = loader.getController();
		Scene scene = new Scene(pane);
		window.setScene(scene);
		controller.passStageReference(window);
		window.show();
	}

	/**
	 * Load image drawer.
	 *
	 * @throws IOException if file "CreateImage.fxml" doesn't exist.
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
	 * Load image selecter.
	 *
	 * @throws IOException if file "SelectUserImage.fxml" does not exist .
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
	 * Passes current stage onto next class to load new scene on it.
	 * Closes and reverts to previous stage.
	 * 
	 * @param window the window
	 */
	public void passStageReference(Stage window) {
		this.window = window;
	}

	/**
	 * displays user details on a dashboard
	 *
	 * @param user to be displayed
	 */
	// Set user object
	public void setUser(User user) {
		this.user = user;
		libImage.setImage(new Image(user.getProfileImage().getImage()));
		lblUsername.setText("Username: " + user.getUsername());
		lblWelcome.setText("Welcome " + user.getForename() + " " + user.getSurname());
	}

}
