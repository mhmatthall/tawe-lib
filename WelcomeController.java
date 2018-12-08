
/**
 * @author Constantinos Loizou
 */

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WelcomeController {

	private User user;

	private String username;

	@FXML Button btnAbout;
	@FXML Button btnExit;
	@FXML Button btnLogin;
	@FXML TextField txtUsername;

	@FXML
	public void buttonAboutPressed() throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("About.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);

		primaryStage.setTitle("About Tawe-Lib");
		primaryStage.setScene(scene);
		primaryStage.initModality(Modality.APPLICATION_MODAL); // Set window on top
		primaryStage.show();
		


	}

	@FXML
	public void buttonExitPressed() {
		boolean exit = ConfirmationBox.display("Exit", "Are you sure you want to exit?");
		if (exit) {
			Platform.exit();
		}
	}

	@FXML
	public void buttonLoginPressed() {

		if (txtUsername.getText().trim().isEmpty()) {
			AlertBox.display("Please enter a valid username");
			return;
		}

		username = txtUsername.getText().trim();

		try {
			user = new DatabaseRequest().getUser(username);
		} catch (SQLException e1) {
			AlertBox.display("Invalid username");
			txtUsername.setText("");
			return;
		}

		// Display the librarian control screen or user dashboard accordingly
		if (user.isLibrarian()) {
			try {
				showControlPanel();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				Platform.exit();
			}
		} else {
			try {
				showDashboard();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				Platform.exit();
			}
		}
	}

	private void showControlPanel() throws IOException {

		Stage window = (Stage) btnExit.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ControlPanel.fxml"));
		Pane controlPanel = loader.load();
		ControlPanelController controller = loader.getController();
		controller.setUser(user);
		Scene scene = new Scene(controlPanel);
		window.setScene(scene);
		window.show();

	}

	private void showDashboard() throws IOException {
		
		Stage window = (Stage) btnExit.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UserDashboard.fxml"));
		Pane dashboard = loader.load();
		DashboardController controller = loader.getController();
		controller.setUser(user);
		controller.passStageReference(window);
		Scene scene = new Scene(dashboard);
		window.setScene(scene);
		window.show();

	}

	@FXML
	public void onEnter(ActionEvent ae) {
		buttonLoginPressed();
	}

	public void initialize() {
	//	txtUsername.requestFocus();
	//	txtUsername.setText("Foo");
		System.out.println(this);
	}	
}
