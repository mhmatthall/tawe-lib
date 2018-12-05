import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;

public class ImageController {
	@FXML
	public Button btnCreate;
	
	@FXML
	public Button btnExit;
	
	@FXML
	public Button btnSave;

	@FXML
	private VBox editorScene;
	
	@FXML
	private Canvas canvas1;
		
	
	@FXML
	public void buttonCreatePressed() throws IOException{
				

				int x = 100;
				int y = 100;
				
				// Access the graphic context of the canvas
				GraphicsContext gc = canvas1.getGraphicsContext2D();
				
				// Set the fill color to Red
				gc.setFill(Color.BLUE);
				
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

}
