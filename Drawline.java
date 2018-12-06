import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class Drawline extends Application{
	Line lineR = new Line();
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    @Override
    
    public void start(Stage primaryStage) {
    	lineR = new Line();
    	lineR.setCursor(Cursor.MOVE);
    	lineR.setOnMousePressed(lineOnMousePressedEventHandler);
    	lineR.setOnMouseDragged(lineOnMouseDraggedEventHandler);
    	
    	Group root = new Group(lineR);
    	lineR.setStartX(100.0);
    	lineR.setStartY(150.0);
    	lineR.setEndX(500.0);
    	lineR.setEndY(150.0);
    	primaryStage.setResizable(false);
    	primaryStage.setScene(new Scene(root, 600,450));
    	primaryStage.setTitle("Line");
    	primaryStage.show();  	
    }
    
    public static void main(String[] args) {
    	launch(args);	
    }
    
    EventHandler<MouseEvent> lineOnMousePressedEventHandler = 
            new EventHandler<MouseEvent>() {
     
            @Override
            public void handle(MouseEvent t) {
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
                orgTranslateX = ((Line)(t.getSource())).getTranslateX();
                orgTranslateY = ((Line)(t.getSource())).getTranslateY();
            }
            };
         EventHandler<MouseEvent> lineOnMouseDraggedEventHandler = 
            new EventHandler<MouseEvent>() {
     
            @Override
            public void handle(MouseEvent t) {
                double offsetX = t.getSceneX() - orgSceneX;
                double offsetY = t.getSceneY() - orgSceneY;
                double newTranslateX = orgTranslateX + offsetX;
                double newTranslateY = orgTranslateY + offsetY;
                 
                ((Line)(t.getSource())).setTranslateX(newTranslateX);
                ((Line)(t.getSource())).setTranslateY(newTranslateY);
            }
        };
    }