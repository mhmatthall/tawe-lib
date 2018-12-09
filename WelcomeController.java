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

/**
 * A start up window class
 * 
 * @author Constantinos Loizou
 */
public class WelcomeController {

	private User user;

	private String username;

	@FXML
	Button btnAbout;
	@FXML
	Button btnExit;
	@FXML
	Button btnLogin;
	@FXML
	TextField txtUsername;

	/**
	 * Method for "about" button.
	 * 
	 * @throws IOException if file "About.fmxl" in /fxml_files/ doesn't exist
	 */
	@FXML
	public void buttonAboutPressed() throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/About.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);

		primaryStage.setTitle("About Tawe-Lib");
		primaryStage.setScene(scene);
		primaryStage.initModality(Modality.APPLICATION_MODAL); // Set window on top
		primaryStage.show();

	}

	/**
	 * Method for exit button; exits the program
	 */
	@FXML
	public void buttonExitPressed() {
		boolean exit = ConfirmationBox.display("Exit", "Are you sure you want to exit?");
		if (exit) {
			Platform.exit();
		}
	}

	/**
	 * Method for Login button.
	 */
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

	/**
	 * Opens up Librarian dashboard
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void showControlPanel() throws IOException {
		/*
		 * C:\Users\lkonn\Google Drive\University\Year 2\CS230\Assignment
		 * 2\tawe-lib\fxml_files\ControlPanel FXMLLoader loader = new
		 * FXMLLoader(getClass().getResource("ControlPanel.fxml"));
		 */
		Stage window = (Stage) btnExit.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/ControlPanel.fxml"));
		Pane controlPanel = loader.load();
		ControlPanelController controller = loader.getController();
		controller.setUser(user);
		controller.passStageReference(window);
		Scene scene = new Scene(controlPanel);
		window.setScene(scene);
		window.show();

	}

	/**
	 * Opens up borrowers dashboard.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void showDashboard() throws IOException {

		Stage window = (Stage) btnExit.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/UserDashboard.fxml"));
		Pane dashboard = loader.load();
		DashboardController controller = loader.getController();
		controller.setUser(user);
		controller.passStageReference(window);
		Scene scene = new Scene(dashboard);
		window.setScene(scene);
		window.show();

	}

	/**
	 * On enter press Login
	 *
	 * @param ae Activation event
	 */
	@FXML
	public void onEnter(ActionEvent ae) {
		buttonLoginPressed();
	}

}
