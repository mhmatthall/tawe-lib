import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.geometry.*;


public class ConfirmationBox {
	
	private static boolean answer;
	
	public static boolean display(String title, String msg) {
		
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinHeight(150);
		window.setMinWidth(250);
		
		Label lbl1 = new Label(msg);
	//	lbl1.setFont(new Font(16));
		
		Button btn1 = new Button("YES");
		btn1.setOnAction(e -> {
			answer = true;
			window.close();
		});
		Button btn2 = new Button("NO");
		btn2.setOnAction(e -> {
			answer = false;
			window.close();
		});
		
		//Here we place the buttons
		HBox layout3 = new HBox(10);
		layout3.getChildren().addAll(btn1,btn2);
		layout3.setAlignment(Pos.CENTER);
		
		
		/*
		 * Inside the VBox we load a label and an HBox that
		 * holds out command buttons
		 */
		VBox layout = new VBox(10); //pixels apart
		layout.getChildren().addAll(lbl1,layout3);
		layout.setAlignment(Pos.CENTER); 
		
		Scene scene = new Scene(layout);
		
		window.setScene(scene);
		window.showAndWait();
		
		return answer;
	}
	

}
