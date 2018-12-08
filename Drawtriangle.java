import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

public class Drawtriangle extends Application{
	//variables
	Polygon triangleR;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    @Override
    
    public void start(Stage stage) {
    	//Create a triangle
    	triangleR = new Polygon(300,250,200);
    	triangleR.setCursor(Cursor.MOVE);
    	triangleR.setOnMousePressed(triangleOnMousePressedEventHandler);
    	triangleR.setOnMouseDragged(triangleOnMouseDraggedEventHandler);
    	triangleR.setOnMouseClicked(triangleOnMouseClickedEventHandler);
    	addMouseScrolling(triangleR);
    	
    	Group root = new Group(triangleR);
    	triangleR.getPoints().addAll(new Double[] {
    			500.0, 400.0,
    			500.0,
    	});
    	//set scene
    	Scene scene = new Scene(root, 600, 450);
    	stage.setResizable(false);
    	stage.setScene(scene);
    	stage.setTitle("Triangle");
    	stage.show();
    }
    
    public static void main(String[] args) {
    	launch(args);	
    }
    //When the mouse is pressed
    EventHandler<MouseEvent> triangleOnMousePressedEventHandler = new EventHandler<MouseEvent>() {
    	
            @Override
            public void handle(MouseEvent t) {
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
                orgTranslateX = ((Polygon)(t.getSource())).getTranslateX();
                orgTranslateY = ((Polygon)(t.getSource())).getTranslateY();
            }
            };
    //When the mouse is dragged
     EventHandler<MouseEvent> triangleOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
     
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
          //Scrolling
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
      EventHandler<MouseEvent> triangleOnMouseClickedEventHandler = new EventHandler<MouseEvent>() {
           	 
           	 @Override
           	 public void handle(MouseEvent t) {
           		Node shape = (Node) t.getSource();
           		shape.getTransforms().add(new Rotate(30.0, t.getX(),t.getY()));
           	 }
      };
        
}
            