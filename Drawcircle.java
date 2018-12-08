import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.input.ScrollEvent;
import javafx.scene.Node;
/**
 *
 * @author kevin
 */
public class Drawcircle extends Application {
	//varibles
    Circle circleR;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    @Override
    public void start(Stage stage) {
    	//Create circle
        circleR = new Circle(70.0f, Color.RED);
        circleR.setCursor(Cursor.MOVE);
        circleR.setCenterX(150);
        circleR.setCenterY(150);
        circleR.setOnMousePressed(circleOnMousePressedEventHandler);
        circleR.setOnMouseDragged(circleOnMouseDraggedEventHandler);
        addMouseScrolling(circleR);
        
        Group root = new Group();
        root.getChildren().add(circleR);

        //set scene
        Scene scene = new Scene(root, 600,450);
        stage.setResizable(false);       
        stage.setTitle("Circle");
        stage.show();
        stage.setScene(scene);
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
//Mouse is pressed
    EventHandler<MouseEvent> circleOnMousePressedEventHandler = new EventHandler<MouseEvent>() {
 
        @Override
        public void handle(MouseEvent t) {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            orgTranslateX = ((Circle)(t.getSource())).getTranslateX();
            orgTranslateY = ((Circle)(t.getSource())).getTranslateY();
        }
    };
//Mouse is dragged
     EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
 
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
//Scrolling
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
}