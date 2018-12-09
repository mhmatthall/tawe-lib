import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ResorcePageController {
	private User user;
	private Resource resource;
 
	
	@FXML
	Label lbl1;
	@FXML
	Label lbl2;
	@FXML
	Label lbl3;
	@FXML
	Label lbl4;
	@FXML
	Label lbl5;
	@FXML
	Label lbl6;
	@FXML
	Button btnExit;
	@FXML
	Button btnRequest;
	@FXML
	Button btnEdit;
	@FXML
	Button btnLoans;
	@FXML ImageView profImg;
	@FXML HBox upperElements;


	public void setUser(User user) {
		this.user = user;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
}