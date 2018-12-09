import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.geometry.*;


/**
 * A pop up for alert box in GUI
 * 
 * @author Constantinos Loizou
 */
public class AlertBox {

	/**
	 * Displays alert box
	 *
	 * @param msg the message to be displayed on pop up
	 */
	public static void display(String msg) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL); //Stay on top
		window.setTitle("Alert");
		window.setMinHeight(100);
		window.setMinWidth(225);
		
		Label lbl1 = new Label(msg);
		
		Button btn1 = new Button("OK");
		btn1.setMinWidth(75);
		btn1.setOnAction(e -> window.close());
		
		ImageView info = new ImageView("/image_files/info.png");
		info.setFitHeight(30);
		info.setFitWidth(30);
		
		HBox layout = new HBox(10);
		layout.getChildren().addAll(info,lbl1);
		layout.setAlignment(Pos.CENTER);
		
		VBox root = new VBox(10);
		root.getChildren().addAll(layout,btn1);
		root.setAlignment(Pos.CENTER); 
		root.setPadding(new Insets(10, 10, 10, 10));
		
		
		
		Scene scene = new Scene(root);
		
		window.setScene(scene);
		window.showAndWait();
	}
	

}
