import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ResourcePageController {
	private User user;
	private Resource resource;
	private Stage window;
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
	@FXML
	ImageView profImg;
	@FXML
	HBox upperElements;
	
	@FXML private void loan() throws IOException {
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("NewLoan.fxml"));
		Pane pane = loader.load();
		NewLoanController controller = loader.getController();
		controller.setResource(resource);
		controller.passStageReference(window);
		Scene scene = new Scene(pane);
		window.setScene(scene);
		
		window.show();
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
	@FXML
	public void initialize() {
		if (resource.getResourceID().charAt(0) == 'B') {
			Book book = (Book) resource ;
			lbl1.setText(Integer.toString(book.getYear()));
			lbl2.setText(book.getAuthor());
			lbl3.setText(book.getPublisher());
			lbl4.setText(book.getGenre());
			lbl5.setText(book.getISBN());
			lbl6.setText(book.getLanguage());
		} else if (resource.getResourceID().charAt(0) == 'D') {
			DVD dvd = (DVD) resource ;
			lbl1.setText(Integer.toString(dvd.getYear()));
			lbl2.setText(dvd.getDirector());
			lbl3.setText(Integer.toString(dvd.getRuntime()));
			lbl4.setText(dvd.getLanguage());
			lbl5.disableProperty();
			lbl6.disableProperty();
		} else if (resource.getResourceID().charAt(0) == 'L') {
			Laptop laptop = (Laptop) resource;
			lbl1.setText(Integer.toString(laptop.getYear()));
			lbl2.setText(laptop.getManufacturer());
			lbl3.setText(laptop.getModel());
			lbl4.setText(laptop.getOperatingSys());
			lbl5.disableProperty();
			lbl6.disableProperty();
		}
		if (user.isLibrarian()) {
			btnRequest.disableProperty();
		} else {
			btnEdit.disableProperty();
			btnLoans.disableProperty();
		}
	}
	@FXML
	private void btnLoans() throws IOException {
		window.close();
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("NewLoan.fxml"));
		Pane dashboard = loader.load();
		NewLoanController controller = loader.getController();
		controller.setResource(resource);
		controller.passStageReference(window);
		Scene scene = new Scene(dashboard);
		window.setScene(scene);
		window.show();
	}
	public void passStageReference(Stage window) {
		this.window = window;
		
	}
}