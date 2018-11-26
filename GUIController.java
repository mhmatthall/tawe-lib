/**
 * @author Constantinos Loizou
 */

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GUIController {

	@FXML
	public Button btnAbout;
	
	@FXML
	public Button btnExit;

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

	public void buttonExitPressed() {
		// TODO: Implement exit confirmation dialog primaryStage
		Platform.exit();
	}


}
