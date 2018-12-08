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
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class ImageController {
	Polygon triangleR;
	Rectangle rectangleR;
	Circle circleR;
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
	ObservableList<String> value = FXCollections.observableArrayList("rectangle", "circle","triangle");
	shapeType.setItems(value);
	shapeType.getSelectionModel().selectFirst();
	}
	
	
	@FXML
	public void buttonCreatePressed() throws IOException{
				if (shapeType.getSelectionModel().getSelectedItem() == "rectangle") {
					rectangleR = new Rectangle(100,100,100,100);
					if (fill.isSelected()) {
						rectangleR.setFill(pickColour.getValue());
					} else {
						rectangleR.setFill(Color.TRANSPARENT);
						rectangleR.setStroke(pickColour.getValue());
					}
    				rectangleR.setCursor(Cursor.MOVE);
    				rectangleR.setOnMousePressed(rectangleOnMousePressedEventHandler);
    				rectangleR.setOnMouseDragged(rectangleOnMouseDraggedEventHandler);
					int x = 100;
					int y = 100;
				
					canvas1.getChildren().addAll(rectangleR);
				} else if (shapeType.getSelectionModel().getSelectedItem() == "circle") {
					circleR = new Circle(70.0f);
					if (fill.isSelected()) {
						circleR.setFill(pickColour.getValue());
					} else {
						circleR.setFill(Color.TRANSPARENT);
						circleR.setStroke(pickColour.getValue());
					}
			        circleR.setCursor(Cursor.MOVE);
			        circleR.setCenterX(150);
			        circleR.setCenterY(150);
			        circleR.setOnMousePressed(circleOnMousePressedEventHandler);
			        circleR.setOnMouseDragged(circleOnMouseDraggedEventHandler);
			        canvas1.getChildren().addAll(circleR);
				}else if (shapeType.getSelectionModel().getSelectedItem() == "triangle") {
					triangleR = new Polygon(300,250,200);
			    	triangleR.setCursor(Cursor.MOVE);
			    	if (fill.isSelected()) {
						triangleR.setFill(pickColour.getValue());
					} else {
						triangleR.setFill(Color.TRANSPARENT);
						triangleR.setStroke(pickColour.getValue());
					}
			    	triangleR.setOnMousePressed(triangleOnMousePressedEventHandler);
			    	triangleR.setOnMouseDragged(triangleOnMouseDraggedEventHandler);
					canvas1.getChildren().addAll(triangleR);
					triangleR.getPoints().addAll(new Double[] {
			    			300.0, 200.0,
			    			450.0,
			    	});
				}
	}
	
	 			EventHandler<MouseEvent> triangleOnMousePressedEventHandler = 
	            new EventHandler<MouseEvent>() {
	     
	            @Override
	            public void handle(MouseEvent t) {
	                orgSceneX = t.getSceneX();
	                orgSceneY = t.getSceneY();
	                orgTranslateX = ((Polygon)(t.getSource())).getTranslateX();
	                orgTranslateY = ((Polygon)(t.getSource())).getTranslateY();
	            }
	            };
	         EventHandler<MouseEvent> triangleOnMouseDraggedEventHandler = 
	            new EventHandler<MouseEvent>() {
	     
	            @Override
	            public void handle(MouseEvent t) {
	                double offsetX = t.getSceneX() - orgSceneX;
	                double offsetY = t.getSceneY() - orgSceneY;
	                double newTranslateX = orgTranslateX + offsetX;
	                double newTranslateY = orgTranslateY + offsetY;
	                 
	                ((Polygon)(t.getSource())).setTranslateX(newTranslateX);
	                ((Polygon)(t.getSource())).setTranslateY(newTranslateY);
	            }
	        };
	
	@FXML
	public void buttonSavePressed() throws SQLException {
		WritableImage image = canvas1.snapshot(new SnapshotParameters(), null);
	    
	    // TODO: probably use a file chooser here
	    File file = new File(user.getUsername() + ".png");
	    
	    try {
	        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
	        UserImage custom = new UserImage(user.getUsername() + ".png");
	        user.setProfileImage(custom);
	        new DatabaseRequest().editUser(user);
	        
	    } catch (IOException e) {
	        // TODO: handle exception here
	    }
	}


	public void passStageReference(Stage window) {
		this.window = window;
		
	}

	@FXML
	private void exit() throws IOException {
		window.close();
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UserDashboard.fxml"));
		Pane dashboard = loader.load();
		DashboardController controller = loader.getController();
		controller.setUser(user);
		controller.passStageReference(window);
		Scene scene = new Scene(dashboard);
		window.setScene(scene);
		window.show();
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
        EventHandler<MouseEvent> circleOnMousePressedEventHandler = 
                new EventHandler<MouseEvent>() {
         
                @Override
                public void handle(MouseEvent t) {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                    orgTranslateX = ((Circle)(t.getSource())).getTranslateX();
                    orgTranslateY = ((Circle)(t.getSource())).getTranslateY();
                }
                };
             EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = 
                new EventHandler<MouseEvent>() {
         
                @Override
                public void handle(MouseEvent t) {
                    double offsetX = t.getSceneX() - orgSceneX;
                    double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;
                     
                    ((Circle)(t.getSource())).setTranslateX(newTranslateX);
                    ((Circle)(t.getSource())).setTranslateY(newTranslateY);
                }
            };
	public void setUser(User user) {
		this.user = user;
	}
}
