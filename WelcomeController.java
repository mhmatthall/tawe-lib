/**
 * @author Constantinos Loizou
 */

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WelcomeController {

	@FXML
	public Button btnAbout;
	
	@FXML
	public Button btnExit;
	
	@FXML
	public Button btnLogin;

	
	@FXML
	   private BorderPane welcomeScene;
	
	

	@FXML
	public void buttonAboutPressed() throws Exception {
	//	btnAbout.setText("DONT TOUCH ME OK?");
		
		Parent root = FXMLLoader.load(getClass().getResource("About.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("About Tawe-Lib");
		primaryStage.setScene(scene);
		primaryStage.initModality(Modality.APPLICATION_MODAL); //Set window on top
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
	public void buttonLoginPressed() throws IOException {
		//Login methods here 
		GUIMain.showDashboard();
		System.out.println("Login button pressed");
		
		AnchorPane pane = FXMLLoader.load(getClass().getResource("UserDashboard.fxml"));
	    welcomeScene.getChildren().setAll(pane);
		
	}


}
