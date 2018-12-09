import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.Node;
import javafx.scene.input.ScrollEvent;

public class DrawVline extends Application{
	Line lineR = new Line();
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    @Override
    
    public void start(Stage stage) {
    	lineR = new Line();
    	lineR.setCursor(Cursor.MOVE);
    	lineR.setOnMousePressed(lineOnMousePressedEventHandler);
    	lineR.setOnMouseDragged(lineOnMouseDraggedEventHandler);
    	addMouseScrolling(lineR);
    	
    	Group root = new Group(lineR);
    	lineR.setStartX(150.0);
    	lineR.setStartY(500.0);
    	lineR.setEndX(150.0);
    	lineR.setEndY(100.0);
    	Scene scene = new Scene(root, 600, 450);
    	stage.setResizable(false);
    	stage.setScene(scene);
    	stage.setTitle("Line");
    	stage.show();  	
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
        public void addMouseScrolling(Node node) 
        {node.setOnScroll((ScrollEvent t) -> {
        	double zoom = 1.05;
        	double deltaY = t.getDeltaY();
        	if (deltaY < 0){
        		zoom = 2.0 - zoom;
                }
        	node.setScaleX(node.getScaleX() * zoom);
        	node.setScaleY(node.getScaleY() * zoom);
            });

    }
    }