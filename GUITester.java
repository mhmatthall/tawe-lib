import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GUITester extends Application {

	private static final int WINDOW_HEIGHT = 250;
	private static final int WINDOW_WIDTH = 400;
	Stage window;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;

		// Specify the FXML controller
		Parent root = FXMLLoader.load(getClass().getResource("CreateImage.fxml"));

		window.setTitle("Tawe-Lib by SwanGroup42 Studios PLC.");
		window.setOnCloseRequest(e -> {
			e.consume(); // Discard close request, we'll handle it manually
			close();
		});
		window.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
		window.show();		
	}
	
	public static void showDashboard() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(GUIMain.class.getResource("CreateImage.fxml"));
		//window.
	}
	
	/**
	 * Method to be executed upon program close request.
	 * Pop out a confirmation window asking the user to confirm he wants to exit
	 */
	private void close() {
		boolean exit = ConfirmationBox.display("Exit", "Are you sure you want to exit?");
		if (exit) {
			Platform.exit();
		}
	}

}