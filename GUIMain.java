/**
 * @author Constantinos Loizou
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIMain extends Application {
	
	private static final int WINDOW_HEIGHT = 250;
	private static final int WINDOW_WIDTH = 400; 
	Stage window;
	
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
    	window = primaryStage;
    	
    	//Specify the FXML controller
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        
        window.setTitle("Tawe-Lib by SwanGroup42 Studios PLC.");
        window.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        window.show();
    }
    
}
