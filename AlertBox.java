import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
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
		btn1.setMinWidth(50);
		btn1.setOnAction(e -> window.close());
		
		/*
		 * Inside the VBox we load a label and an HBox that
		 * holds out command buttons
		 */
		VBox layout = new VBox(10);
		layout.getChildren().addAll(lbl1,btn1);
		layout.setAlignment(Pos.CENTER); 
		
		
		
		Scene scene = new Scene(layout);
		
		window.setScene(scene);
		window.showAndWait();
	}
	

}
