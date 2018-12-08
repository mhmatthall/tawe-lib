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
import javafx.scene.Cursor;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
public class SelectUserImageController {
	User user;
	
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
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@FXML
	public void buttonProf1Pressed() throws SQLException {
		UserImage selectedImage = new UserImage("image_files//prof1.png");
		user.setProfileImage(selectedImage);
		new DatabaseRequest().editUser(user);
	}
	@FXML
	public void buttonProf2Pressed() throws SQLException {
		UserImage selectedImage = new UserImage("image_files//prof2.png");
		user.setProfileImage(selectedImage);
		new DatabaseRequest().editUser(user);
	}
	@FXML
	public void buttonProf3Pressed() throws SQLException {
		UserImage selectedImage = new UserImage("image_files//prof3.png");
		user.setProfileImage(selectedImage);
		new DatabaseRequest().editUser(user);
	}
	@FXML
	public void buttonProf4Pressed() throws SQLException {
		UserImage selectedImage = new UserImage("image_files//prof4.png");
		user.setProfileImage(selectedImage);
		new DatabaseRequest().editUser(user);
	}
}
