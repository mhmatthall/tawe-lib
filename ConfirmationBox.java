import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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
		
		btn1.setMinWidth(50);
		btn2.setMinWidth(50);
		
		ImageView attention = new ImageView("/image_files/attention.png");
		attention.setFitHeight(30);
		attention.setFitWidth(30);
		
		HBox upperPart = new HBox(10);
		upperPart.getChildren().addAll(attention, lbl1);
		upperPart.setAlignment(Pos.CENTER);

		//Here we place the buttons
		HBox buttons = new HBox(10);
		buttons.getChildren().addAll(btn1,btn2);
		buttons.setAlignment(Pos.CENTER);
		
		
		/*
		 * Inside the VBox we load a label and an HBox that
		 * holds out command buttons
		 */
		VBox layout = new VBox(10); //pixels apart
		layout.getChildren().addAll(upperPart,buttons);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(10, 10, 10, 10));
		
		Scene scene = new Scene(layout);
		
		window.setScene(scene);
		window.showAndWait();
		
		return answer;
	}
	

}
