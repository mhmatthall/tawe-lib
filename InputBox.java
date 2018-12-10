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
public class InputBox {



	public static ArrayList<String> display(String title, String msg) {
		ArrayList<String> answers = new ArrayList<>();
		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinHeight(150);
		window.setMinWidth(250);

		Label lbl1 = new Label(msg);
		ChoiceBox<String> loanLength = new ChoiceBox<>();
		
		ObservableList<String> value = FXCollections.observableArrayList("1", "7", "14", "28");
		loanLength.setItems(value);
		loanLength.getSelectionModel().selectFirst();
		TextField txtBox = new TextField("Insert value here");
		
		
		Button btn1 = new Button("Done");
		btn1.setOnAction(e -> {
			System.out.println(loanLength.getValue());
			answers.add(loanLength.getValue());
			answers.add(txtBox.getText());
			window.close();
		});

		

		btn1.setMinWidth(50);

		/*
		 * Inside the VBox we load a label and an HBox that holds out command buttons
		 */
		VBox layout = new VBox(10); // pixels apart
		layout.getChildren().addAll(lbl1, txtBox, loanLength, btn1);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(10, 10, 10, 10));

		Scene scene = new Scene(layout);

		window.setScene(scene);
		window.showAndWait();

		return answers;
	}

}
