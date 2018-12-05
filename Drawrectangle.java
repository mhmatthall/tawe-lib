import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Drawrectangle extends Application{
	Rectangle rectangleR;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    @Override
    
    public void start(Stage primaryStage) {
    	rectangleR = new Rectangle(200,200,400,140);
    	rectangleR.setCursor(Cursor.MOVE);
    	rectangleR.setOnMousePressed(rectangleOnMousePressedEventHandler);
    	rectangleR.setOnMouseDragged(rectangleOnMouseDraggedEventHandler);
    	
    	Group root = new Group();
    	root.getChildren().addAll(rectangleR);
    	
    	primaryStage.setResizable(false);
    	primaryStage.setScene(new Scene(root, 600,450));
    	primaryStage.setTitle("Rectangle");
    	primaryStage.show();  	
    }
    
    public static void main(String[] args) {
    	launch(args);	
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
    }