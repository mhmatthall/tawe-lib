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
	private DVD dvd;
	private Book book;
	private Laptop laptop;
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
	public void setBook(Resource resource) throws SQLException {
		this.book = (Book) new DatabaseRequest().getResource(resource.getResourceID());
		System.out.println(resource.toString());
		lblTitle.setText(resource.getTitle());
		lbl1.setText("Year: " + Integer.toString(resource.getYear()));
		lbl2.setText("Author: " + ((Book)resource).getAuthor());
		lbl3.setText("Publisher: " + ((Book)resource).getPublisher());
		lbl4.setText("Genre: " + ((Book)resource).getGenre());
		lbl5.setText("ISBN: " + ((Book)resource).getISBN());
		lbl6.setText("Language: " + ((Book)resource).getLanguage());
	}
	public void setDVD(Resource resource) throws SQLException { 
		this.dvd = (DVD) new DatabaseRequest().getResource(resource.getResourceID());
		lblTitle.setText(dvd.getTitle());
		lbl1.setText("Year: " + Integer.toString(dvd.getYear()));
		lbl2.setText(((DVD)dvd).getDirector());
		lbl3.setText(Integer.toString(((DVD)dvd).getRuntime()));
		lbl4.setText("Language: " + ((DVD)dvd).getLanguage());
		lbl5.disableProperty();
		lbl6.disableProperty();
	}

	public void setLaptop(Resource resource) throws SQLException {
		this.laptop = (Laptop) new DatabaseRequest().getResource(resource.getResourceID());
		lblTitle.setText(resource.getTitle());
		lbl1.setText(Integer.toString(resource.getYear()));
		lbl2.setText(((Laptop)resource).getManufacturer());
		lbl3.setText(((Laptop)resource).getModel());
		lbl4.setText(((Laptop)resource).getOperatingSys());
		lbl5.disableProperty();
		lbl6.disableProperty();
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
	
	@FXML
	private void close() {
		window.close();
	}
	
	@FXML
	private void editResource() throws IOException {
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/EditResource.fxml"));
		Pane editor = loader.load();
		EditResourceController controller = loader.getController();
		controller.passStageReference(window);
		controller.passResourceReference(resource);
		Scene scene = new Scene(editor);
		window.setScene(scene);
		window.show();
	}
	
	public void passStageReference(Stage window) {
		this.window = window;
		
	}
}