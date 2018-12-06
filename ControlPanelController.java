
/**
 * @author Constantinos Loizou
 */

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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControlPanelController {

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
	Button btnCreateResource;
	@FXML
	Button btnSearchUser;
	
	
	// THE PROBLEM IS IN THESE 2
	/*
	 * @FXML Label lblUsername;
	 * 
	 * @FXML Label lblWelcome;
	 */

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

	/*
	 * Pops out a new window to select resource type and then creates a resource of
	 * that type
	 */
	public void createResource() {
		String type = SelectionBox.display("Select", "Please select resource type", "Book", "DVD", "Laptop");
		try {
			switch (type) {
			case "Book":
				createBook();
				break;
			case "DVD":
				createDVD();
				break;
			case "Laptop":
				createLaptop();
				break;
			// TODO: Throw an exception if the window has closed an no value returned.
			}
		} catch (IOException e) {
			System.out.println("Caught IO Exception coming from " + e.getCause() + e.getClass());
			System.out.println(e.getMessage());
			Platform.exit();
		}
	}
	
	//TODO: Throw exception or alerbox if one of the fields has been left empty
	
	private void createLaptop() throws IOException {
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("NewLaptop.fxml"));
		Pane pane = loader.load();
		NewLaptopController controller = loader.getController();
		Scene scene = new Scene(pane);
		window.setScene(scene);
		controller.passStageReference(window);
		window.show();

	}

	private void createDVD() throws IOException {
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("NewDVD.fxml"));
		Pane pane = loader.load();
		NewDVDController controller = loader.getController();
		Scene scene = new Scene(pane);
		window.setScene(scene);
		controller.passStageReference(window);
		window.show();
	}

	private void createBook() throws IOException {
		Stage window2 = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("NewBook.fxml"));
		Pane pane = loader.load();
		NewBookController controller = loader.getController();
		Scene scene = new Scene(pane);
		window2.setScene(scene);
		controller.passStageReference(window2);
		window2.show();

	}
	
	@FXML
	private void searchUser() throws IOException {
		Stage window3 = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchUser.fxml"));
		Pane pane = loader.load();
		SearchUserController controller = loader.getController();
		Scene scene = new Scene(pane);
		window3.setScene(scene);
		controller.passStageReference(window3);
		window3.show();
	}
	
	@FXML
	private void newUser() throws IOException {
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("NewUser.fxml"));
		Pane pane = loader.load();
		NewUserController controller = loader.getController();
		Scene scene = new Scene(pane);
		window.setScene(scene);
		controller.passStageReference(window);
		window.show();
	}
	
	// Set user object
	public void setUser(User user) {
		this.user = user;
		lblUsername.setText("Username: " + user.getUsername());
		lblWelcome.setText("Welcome " + user.getForename());
	}

}
