import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.application.Platform;
import javafx.geometry.*;


public class SelectionBox {
	
	private static int answer;
	
	public static int display(String title, String msg, String opt1, String opt2, String opt3) {
		
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinHeight(250);
		window.setMinWidth(275);
		window.setOnCloseRequest(e -> {
			e.consume();
			System.out.print("test");
			return;
		});
		
		Label lbl1 = new Label(msg);
		lbl1.setFont(new Font(16));
		
		Button btn1 = new Button(opt1);
		btn1.setOnAction(e -> {
			answer = 1;
			window.close();
		});
		
		Button btn2 = new Button(opt2);
		btn2.setOnAction(e -> {
			answer = 2;
			window.close();
		});
		
		Button btn3 = new Button(opt3);
		btn3.setOnAction(e -> {
			answer = 3;
			window.close();
		});
		
		VBox buttons = new VBox(10);
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
