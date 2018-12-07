import java.io.File;
import java.io.IOException;
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


public class ImageController {
	
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
	private Canvas canvas1;


		
	@FXML
	public void initialize() {
	//    ObservableList<String> value = FXCollections.observableArrayList("apples", "oranges");
	//	shapeType.setItems(value);
	}
	
	
	@FXML
	public void buttonCreatePressed() throws IOException{
		    //Circle circleR = new Circle(70.0f, Color.RED);
	        //circleR.setCursor(Cursor.HAND);
	        //circleR.setCenterX(150);
	        //circleR.setCenterY(150);
	        //circleR.setOnMousePressed(circleOnMousePressedEventHandler);
	        //circleR.setOnMouseDragged(circleOnMouseDraggedEventHandler);
				int x = 100;
				int y = 100;
				
				// Access the graphic context of the canvas
				GraphicsContext gc = canvas1.getGraphicsContext2D();
				
				// Set the fill color to Red
				gc.setFill(pickColour.getValue());
				
				// Draw a circle at the coordinates
				gc.fillRect(x, y, 40, 40);
	}
	@FXML
	public void buttonSavePressed() {
		WritableImage image = canvas1.snapshot(new SnapshotParameters(), null);
	    
	    // TODO: probably use a file chooser here
	    File file = new File("image_files\\custom.png");
	    
	    try {
	        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
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

}
