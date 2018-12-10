import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;

/**
 * The Class ConfirmationBox. A pop up in GUI for confirmation (e.g. exit)
 * 
 * @author Constantinos Loizou
 * 
 */

public class ValueBox {

	static double answer;

	/**
	 * Display a pop-out window prompting user for a text input. Then returns a string us the user's input
	 * Used in cases where input is needed but no appropriate TextField exist in the GUI 
	 *
	 * @param title the title of the  pop-out window
	 * @param msg the message to be displayed
	 * @return string the user entered.
	 */
	
	public static double display(String title, String msg) {
		 
		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinHeight(150);
		window.setMinWidth(250);

		Label lbl1 = new Label(msg);
		TextField txtBox = new TextField("Insert value here");
		
		Button btn1 = new Button("Done");
		btn1.setOnAction(e -> {
			answer = Double.parseDouble(txtBox.getText());
			window.close();
		});

		

		btn1.setMinWidth(50);

		/*
		 * Inside the VBox we load a label and an HBox that holds out command buttons
		 */
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(lbl1, txtBox, btn1);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(10, 10, 10, 10));

		Scene scene = new Scene(layout);

		window.setScene(scene);
		window.showAndWait();

		return answer;
	}

}
