import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AlertBox {

	public static void display(String msg) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL); //Stay on top
		window.setTitle("Alert");
		window.setMinHeight(100);
		window.setMinWidth(225);
		
		Label lbl1 = new Label(msg);
	//	lbl1.setFont(new Font(16));
		
		Button btn1 = new Button("OK");
		btn1.setMinWidth(50);
		btn1.setOnAction(e -> window.close());
		
		/*
		 * Inside the VBox we load a label and an HBox that
		 * holds out command buttons
		 */
		VBox layout = new VBox(10); //pixels apart
		layout.getChildren().addAll(lbl1,btn1);
		layout.setAlignment(Pos.CENTER); 
		
		
		
		Scene scene = new Scene(layout);
		
		window.setScene(scene);
		window.showAndWait();
	}
	

}
