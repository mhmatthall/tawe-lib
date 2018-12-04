
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

public class ControlPanelController implements Initializable {

	private String userID;

	@FXML Button btnExit;
	@FXML Button btnLogout;
	
	//THE PROBLEM IS IN THESE 2
/*	@FXML Label lblUsername;
	@FXML Label lblWelcome; */

	@FXML
	public void exit() {
		boolean exit = ConfirmationBox.display("Exit", "Are you sure you want to exit?");
		if (exit) {
			Platform.exit();
		}
	}

	public void logout() throws IOException {
		Stage window = (Stage) btnExit.getScene().getWindow();
		Pane previous = FXMLLoader.load(getClass().getResource("Welcome.fxml"));
		window.setScene(new Scene(previous));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		try {
//			btnExit.setText("Welcome " + new DatabaseRequest().getUser(userID).getForename());
//		} catch (SQLException e) {
//			System.out.println("Error, username not in database");
//			e.printStackTrace();
//		}
		
		

	}

	/*
	 * @param userID 
	 * 		username of the logged in user
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

}
