import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Class in charge of user being able to select his own avatar.
 * 
 * @author Caleb Warburton
 */
public class SelectUserImageController {
	
	private User user;
	
	private Stage window;
	
	@FXML
	public Button prof1;
	
	@FXML
	public Button btnExit;
	
	@FXML
	public Button prof2;
	
	@FXML
	public Button prof3;
	
	@FXML
	public Button prof4;
	
	/**
	 * sets the user who needs image change
	 *
	 * @param user the new user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * Passes the stage reference.
	 *
	 * @param window the stage to be passed
	 */
	public void passStageReference(Stage window) {
		this.window = window;
		
	}
	
	/**
	 * sets profile picture 1.
	 *
	 * @throws SQLException if database fails
	 */
	@FXML
	public void buttonProf1Pressed() throws SQLException {
		UserImage selectedImage = new UserImage("image_files//prof1.png");
		user.setProfileImage(selectedImage);
		new DatabaseRequest().editUser(user);
	}
	
	/**
	 * sets profile picture 2.
	 *
	 * @throws SQLException if database fails
	 */
	@FXML
	public void buttonProf2Pressed() throws SQLException {
		UserImage selectedImage = new UserImage("image_files//prof2.png");
		user.setProfileImage(selectedImage);
		new DatabaseRequest().editUser(user);
	}
	
	/**
	 * sets profile picture 3.
	 *
	 * @throws SQLException if database fails
	 */
	@FXML
	public void buttonProf3Pressed() throws SQLException {
		UserImage selectedImage = new UserImage("image_files//prof3.png");
		user.setProfileImage(selectedImage);
		new DatabaseRequest().editUser(user);
	}
	
	/**
	 * sets profile picture 4.
	 *
	 * @throws SQLException if database fails
	 */
	@FXML
	public void buttonProf4Pressed() throws SQLException {
		UserImage selectedImage = new UserImage("image_files//prof4.png");
		user.setProfileImage(selectedImage);
		new DatabaseRequest().editUser(user);
	}
	
	/**
	 * method for exit button
	 *
	 * @throws IOException if file "ControlPanle.fxml" doesn't exist in /fxml_files/
	 */
	@FXML
	private void exit() throws IOException {
		window.close();
		Stage window = new Stage();
		if (user.isLibrarian()) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/ControlPanel.fxml"));
			Pane dashboard = loader.load();
			ControlPanelController controller = loader.getController();
			controller.setUser(user);
			controller.passStageReference(window);
			Scene scene = new Scene(dashboard);
			window.setScene(scene);
			window.show();
		} else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/UserDashboard.fxml"));
			Pane dashboard = loader.load();
			DashboardController controller = loader.getController();
			controller.setUser(user);
			controller.passStageReference(window);
			Scene scene = new Scene(dashboard);
			window.setScene(scene);
			window.show();
		}
	}
}
