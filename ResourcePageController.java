import java.io.IOException;
import java.sql.SQLException;

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
	Label lblTitle;
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
	
	@FXML private void loan() throws IOException, SQLException {
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
		if (user.isLibrarian()) {
			btnRequest.setDisable(true);
		} else {
			btnEdit.setDisable(true);
			btnLoans.setDisable(true);
		}
	}

	public void setResource(Resource resource) {
		this.resource = resource;
		System.out.println(resource.toString());
		if (resource.getResourceID().charAt(0) == 'B') {
			Book book = (Book) resource ;
			System.out.println(resource.toString());
			lblTitle.setText(book.getTitle());
			lbl1.setText("Year: " + Integer.toString(book.getYear()));
			lbl2.setText("Author: " + book.getAuthor());
			lbl3.setText("Publisher: " + book.getPublisher());
			lbl4.setText("Genre: " + book.getGenre());
			lbl5.setText("ISBN: " + book.getISBN());
			lbl6.setText("Language: " + book.getLanguage());
		} else if (resource.getResourceID().charAt(0) == 'D') {
			DVD dvd = (DVD) resource ;
			lblTitle.setText(dvd.getTitle());
			lbl1.setText("Year: " + Integer.toString(dvd.getYear()));
			lbl2.setText(dvd.getDirector());
			lbl3.setText(Integer.toString(dvd.getRuntime()));
			lbl4.setText("Language: " + dvd.getLanguage());
			lbl5.disableProperty();
			lbl6.disableProperty();
		} else if (resource.getResourceID().charAt(0) == 'L') {
			Laptop laptop = (Laptop) resource;
			lblTitle.setText(laptop.getTitle());
			lbl1.setText(Integer.toString(laptop.getYear()));
			lbl2.setText(laptop.getManufacturer());
			lbl3.setText(laptop.getModel());
			lbl4.setText(laptop.getOperatingSys());
			lbl5.disableProperty();
			lbl6.disableProperty();
		}
	}
	@FXML
	public void initialize() {
	}

	@FXML
	private void btnLoans() throws IOException, SQLException {
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/NewLoan.fxml"));
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