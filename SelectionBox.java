import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.application.Platform;
import javafx.geometry.*;


public class SelectionBox {
	
	private static String answer;
	
	public static String display(String title, String msg, String opt1, String opt2, String opt3) {
		
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinHeight(175);
		window.setMinWidth(250);
		
		Label lbl1 = new Label(msg);
		lbl1.setFont(new Font(16));
		
		Button btn1 = new Button(opt1);
		btn1.setOnAction(e -> {
			answer = "Book";
			window.close();
		});
		
		Button btn2 = new Button(opt2);
		btn2.setOnAction(e -> {
			answer = "DVD";
			window.close();
		});
		
		Button btn3 = new Button(opt3);
		btn3.setOnAction(e -> {
			answer = "Laptop";
			window.close();
		});
		
		HBox buttons = new HBox(10);
		buttons.getChildren().addAll(btn1,btn2,btn3);
		buttons.setAlignment(Pos.CENTER);
		
		
		/*
		 * Inside the VBox we load a label and an HBox that
		 * holds out command buttons
		 */
		VBox layout = new VBox(10); //pixels apart
		layout.getChildren().addAll(lbl1,buttons);
		layout.setAlignment(Pos.CENTER); 
		
		Scene scene = new Scene(layout);
		
		window.setScene(scene);
		window.setOnCloseRequest(e -> window.close());
		window.showAndWait();
		
		return answer;
	}
	

}
