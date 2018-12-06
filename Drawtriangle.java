import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;;

public class Drawtriangle extends Application{
	Polygon triangleR;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    @Override
    
    public void start(Stage primaryStage) {
    	triangleR = new Polygon(300,250,200);
    	triangleR.setCursor(Cursor.MOVE);
    	triangleR.setOnMousePressed(triangleOnMousePressedEventHandler);
    	triangleR.setOnMouseDragged(triangleOnMouseDraggedEventHandler);
    	
    	Group root = new Group(triangleR);
    	triangleR.getPoints().addAll(new Double[] {
    			300.0, 200.0,
    			450.0,
    	});
    	
    	primaryStage.setResizable(false);
    	primaryStage.setScene(new Scene(root, 600,450));
    	primaryStage.setTitle("Triangle");
    	primaryStage.show();  	
    }
    
    public static void main(String[] args) {
    	launch(args);	
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
    }