import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Drawrectangle extends Application{
	Rectangle rectangleR;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    @Override
    
    public void start(Stage primaryStage) {
    	rectangleR = new Rectangle(200,200,400,140);
    	rectangleR.setFill(Color.RED);
    	rectangleR.setCursor(Cursor.MOVE);
    	rectangleR.setOnMousePressed(rectangleOnMousePressedEventHandler);
    	rectangleR.setOnMouseDragged(rectangleOnMouseDraggedEventHandler);
    	rectangleR.setOnMouseClicked(rectangleOnMouseClickedEventHandler);
    	addMouseScrolling(rectangleR);
    	
    	Group root = new Group();
    	root.getChildren().addAll(rectangleR);
    	
    	Scene scene = new Scene(root, 600, 450);
    	primaryStage.setResizable(false);
    	primaryStage.setScene(scene);
    	primaryStage.setTitle("Rectangle");
    	primaryStage.show();  	
    }
    
    public static void main(String[] args) {
    	launch(args);	
    }
    
    EventHandler<MouseEvent> rectangleOnMousePressedEventHandler = new EventHandler<MouseEvent>() {
     
            @Override
            public void handle(MouseEvent t) {
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
                orgTranslateX = ((Rectangle)(t.getSource())).getTranslateX();
                orgTranslateY = ((Rectangle)(t.getSource())).getTranslateY();
            }
    };
    EventHandler<MouseEvent> rectangleOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
     
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
    
     public void addMouseScrolling(Node node) {
         node.setOnScroll((ScrollEvent t) -> {

             double zoom = 1.05;
             double deltaY = t.getDeltaY();
             if (deltaY < 0){
                 zoom = 2.0 - zoom;
             }
             node.setScaleX(node.getScaleX() * zoom);
             node.setScaleY(node.getScaleY() * zoom);
         });

     }
     EventHandler<MouseEvent> rectangleOnMouseClickedEventHandler = new EventHandler<MouseEvent>() {
    	 
    	 @Override
    	 public void handle(MouseEvent t) {
    		 Node shape = (Node) t.getSource();
    		 shape.getTransforms().add(new Rotate(30.0, t.getX(),t.getY()));
    	 }
     };
}