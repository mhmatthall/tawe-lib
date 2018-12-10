import java.awt.Window;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
	@FXML Label lblBalance;
	@FXML
	Button btnExit;
	@FXML
	Button btnLogout;
	@FXML
	Button btnEdit;
	@FXML
	Button btnSearch;
	@FXML
	Button btnReservations;
	@FXML
	Button btnLoan;
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
		int selection = SelectionBox.display("Select", "What do you want to edit?",
				"Edit Personal Details","Select new Profile Picture from Library",
				"Draw your own profile picture");
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
			System.out.println("Caught IO Exception coming from " +
			e.getCause() + e.getClass() + " from class "+ this.getClass().toString());
			System.out.println(e.getMessage());
			System.out.println("\n");
			e.printStackTrace();
			Platform.exit();
		}
	}

	/**
	 * Switches to a window for editing user's details
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
	
	/**
	 * Opens a window for users to reserve a resource
	 * 
	 * @throws IOException if file "ReservedResources.fxml" does not exist
	 * @throws SQLException if cannot connect to Database
	 */
	@FXML
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
	 * Opens a window for users to see their borrowed resources
	 * 
	 * @throws IOException if file BorrowedResources.fxml
	 * @throws SQLException if cannot connect to Database
	 */

	@FXML
	private void openBorrowedItems() throws IOException, SQLException {
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/BorrowedResources.fxml"));
		Pane pane = loader.load();
		BorrowsController controller = loader.getController();
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
	 * @throws IOException if file "SearchLibrary.fxml" does not exist
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
		controller.setUser(user);
		window4.show();
	}
	
	/**
	 * Method to logout of account.
	 * 
	 * @throws IOException if file "Welcome.fxml" does not exist
	 */
	@FXML
	public void logout() throws IOException {
		Stage window = (Stage) btnExit.getScene().getWindow();
		Pane previous = FXMLLoader.load(getClass().getResource("/fxml_files/Welcome.fxml"));
		window.setScene(new Scene(previous));
	}
	

	/**
	 * Load image drawer to draw an image.
	 *
	 * @throws IOException if file "CreateImage.fxml" does not exist
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
	 * @throws IOException if file "SelectUserImage.fxml" does not exist
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
	 * Welcome screen for the user.
	 *
	 * @param user the user
	 * @throws SQLException 
	 */
	public void setUser(User user) throws SQLException {
		this.user = user;
		userimage.setImage(new Image(user.getProfileImage().getImage()));
		lblUsername.setText("Username: " + user.getUsername());
		lblWelcome.setText("Welcome " + user.getForename() + " " + user.getSurname());
		//lblBalance.setText(Double.toString(new DatabaseRequest().totalUserFines(user.getUsername())));
		
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
	 * Passes current stage onto next class to load new scene on it.
	 * Closes and reverts to previous stage.
	 *
	 * @param window the window
	 */
	public void passStageReference(Stage window) {
		this.window = window;
	}

}
