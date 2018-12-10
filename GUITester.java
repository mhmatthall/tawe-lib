import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
public class GUITester extends Application {

	private static final int WINDOW_HEIGHT = 500;
	private static final int WINDOW_WIDTH = 600;

	static Stage window;
	static Book resource = new Book("B-09", "title", 2017, null, null, "author",
			"publisher", "genre", "ISBN","language");
	static User user = new Librarian("hey", "", "", "", "", null, 0, null);

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;

		// Specify the FXML controller
		Parent root = FXMLLoader.load(getClass().getResource("ResourcePage.fxml"));

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(GUIMain.class.getResource("ResourcePage.fxml"));
		Pane dashboard = loader.load();
		ResourcePageController controller = loader.getController();
		controller.setResource(resource);
		controller.setUser(user);
		controller.passStageReference(window);
		Scene scene = new Scene(dashboard);
		window.setScene(scene);
		window.show();

	}

	/**
	 * Show dashboard.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void showDashboard() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(GUIMain.class.getResource("ResourcePage.fxml"));
		Pane dashboard = loader.load();
		ResourcePageController controller = loader.getController();
		controller.setResource(resource);
		controller.setUser(user);
		controller.passStageReference(window);
		Scene scene = new Scene(dashboard);
		window.setScene(scene);
		window.show();
		// window.
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

}