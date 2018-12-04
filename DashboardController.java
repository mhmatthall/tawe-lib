
/**
 * @author Constantinos Loizou
 */

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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DashboardController {

	private User user;
	
	@FXML Label lblWelcome;	
	@FXML Label lblUsername;	
	@FXML Button btnExit;
	@FXML Button btnLogout;



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


	public void setUser(User user) {
		this.user = user;
		lblUsername.setText("Username: " + user.getUsername());
		lblWelcome.setText("Welcome " + user.getForename());
	}

}
