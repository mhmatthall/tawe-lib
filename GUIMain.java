import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Handles GUI and is a starter class
 * 
 * @author Constantinos Loizou
 */

public class GUIMain extends Application {

	private static final int WINDOW_HEIGHT = 300;
	private static final int WINDOW_WIDTH = 425;

	private Stage window;
	private Scene welcomeScene;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage window) throws Exception {

		// System.out.println("USER: " + new
		// DatabaseRequest().getUser("qqqq").toString());

		// Specify the FXML controller
		Parent root = FXMLLoader.load(getClass().getResource("Welcome.fxml"));
		welcomeScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

//TODO: RE-ENABLE THIS FEATURE
		/*
		 * window.setTitle("Tawe-Lib by SwanGroup42 Studios PLC.");
		 * window.setOnCloseRequest(e -> { e.consume(); // Discard close request, we'll
		 * handle it manually close(); });
		 */

		window.setScene(welcomeScene);
		window.setMinWidth(WINDOW_WIDTH);
		window.setMinHeight(WINDOW_HEIGHT);
		window.show();

	}

	/**
	 * Method to be executed upon program close request. Pop out a confirmation
	 * window asking the user to confirm he wants to exit
	 */
	private void close() {
		boolean exit = ConfirmationBox.display("Exit", "Are you sure you want to exit?");
		if (exit) {
			Platform.exit();
		}
	}

	/**
	 * Gets windows height.
	 *
	 * @return window height
	 */
	public static int getWindowHeight() {
		return WINDOW_HEIGHT;
	}

	/**
	 * Gets window width.
	 *
	 * @return window width
	 */
	public static int getWindowWidth() {
		return WINDOW_WIDTH;
	}

}
