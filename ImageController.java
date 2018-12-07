import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
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


public class ImageController {
	
	Rectangle rectangleR;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    
    private User user;
	
	private Stage window;
	
	@FXML
	public Button btnCreate;
	
	@FXML
	public Button btnExit;
	
	@FXML
	public Button btnSave;
	
	@FXML
	public ColorPicker pickColour;
	
	@FXML
	public CheckBox fill;

	@FXML
	public ChoiceBox<String> shapeType; 
	
	@FXML
	private VBox editorScene;
	
	@FXML
	private AnchorPane anch;
	
	@FXML
	private AnchorPane canvas1;


		
	@FXML
	public void initialize() {
	//    ObservableList<String> value = FXCollections.observableArrayList("apples", "oranges");
	//	shapeType.setItems(value);
	}
	
	
	@FXML
	public void buttonCreatePressed() throws IOException{
		
				rectangleR = new Rectangle(100,100,100,100);
    			rectangleR.setFill(pickColour.getValue());
    			rectangleR.setCursor(Cursor.MOVE);
    			rectangleR.setOnMousePressed(rectangleOnMousePressedEventHandler);
    			rectangleR.setOnMouseDragged(rectangleOnMouseDraggedEventHandler);
				int x = 100;
				int y = 100;
				
				canvas1.getChildren().addAll(rectangleR);
	}
	@FXML
	public void buttonSavePressed() throws SQLException {
		WritableImage image = canvas1.snapshot(new SnapshotParameters(), null);
	    
	    // TODO: probably use a file chooser here
	    File file = new File("image_files\\custom.png");
	    
	    try {
	        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
	        UserImage custom = new UserImage("image_files\\custom.png");
	        user.setProfileImage(custom);
	        DatabaseRequest db1 = new DatabaseRequest();
	        db1.editUser(user);
	        
	    } catch (IOException e) {
	        // TODO: handle exception here
	    }
	}


	public void passStageReference(Stage window) {
		this.window = window;
		
	}

	@FXML
	private void exit() {
		window.close();
	}

	EventHandler<MouseEvent> rectangleOnMousePressedEventHandler = 
            new EventHandler<MouseEvent>() {
     
            @Override
            public void handle(MouseEvent t) {
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
                orgTranslateX = ((Rectangle)(t.getSource())).getTranslateX();
                orgTranslateY = ((Rectangle)(t.getSource())).getTranslateY();
            }
            };
         EventHandler<MouseEvent> rectangleOnMouseDraggedEventHandler = 
            new EventHandler<MouseEvent>() {
     
            @Override
            public void handle(MouseEvent t) {
                double offsetX = t.getSceneX() - orgSceneX;
                double offsetY = t.getSceneY() - orgSceneY;
                double newTranslateX = orgTranslateX + offsetX;
                double newTranslateY = orgTranslateY + offsetY;
                 
                ((Rectangle)(t.getSource())).setTranslateX(newTranslateX);
                ((Rectangle)(t.getSource())).setTranslateY(newTranslateY);
            }
        };
	public void setUser(User user) {
		this.user = user;
	}
}
