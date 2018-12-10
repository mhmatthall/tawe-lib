import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/** To check whether the resources available or not.
 * 
 * @author Caleb Warburton
 *
 */
public class ResourcePageController {

	private User user;
	private DVD dvd;
	private Book book;
	private Laptop laptop;
	private Stage window;
	private Resource resource;

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
	Label lblCopies;
	@FXML
	Button btnExit;
	@FXML
	Button btnRequest;
	@FXML
	Button btnEdit;
	@FXML
	Button btnLoans;
	@FXML
	ImageView resourceImg;
	@FXML
	HBox upperElements;

	/**
	 * To check loan.
	 *
	 * @throws IOException  if file "NewLoan.fxml" cannot be found
	 * @throws SQLException if cannot connect to the Database
	 */
	@FXML
	private void loan() throws IOException, SQLException {
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

	/**
	 * Determines if the user is librarian or borrower.
	 *
	 * @param user the new user
	 */
	public void setUser(User user) {
		this.user = user;
		if (user.isLibrarian()) {
			btnRequest.setDisable(true);
		} else {
			btnEdit.setDisable(true);
			btnLoans.setDisable(true);
		}
	}

	/**
	 * Retrieves information of the book from the database.
	 * Sets the information of book
	 *
	 * @param resource the new book
	 * @throws SQLException if cannot connect to the Database
	 */
	public void setBook(Resource resource) throws SQLException {
		this.book = (Book) new DatabaseRequest().getResource(resource.getResourceID());
		this.resource = book;
		resourceImg.setImage(new Image("image_files/book.png"));
		System.out.println(resource.toString());
		lblTitle.setText(resource.getTitle());
		lbl1.setText("Year: " + Integer.toString(resource.getYear()));
		lbl2.setText("Author: " + ((Book) resource).getAuthor());
		lbl3.setText("Publisher: " + ((Book) resource).getPublisher());
		lbl4.setText("Genre: " + ((Book) resource).getGenre());
		lbl5.setText("ISBN: " + ((Book) resource).getISBN());
		lbl6.setText("Language: " + ((Book) resource).getLanguage());
		lblCopies.setText(Integer.toString(book.viewCopies().size()));
	}

	/**
	 * Retrieves information of the DVD from the database
	 * Sets the information of DVD
	 *
	 * @param resource the new dvd
	 * @throws SQLException if cannot connect to the Database
	 */
	public void setDVD(Resource resource) throws SQLException {
		this.dvd = (DVD) new DatabaseRequest().getResource(resource.getResourceID());
		this.resource = dvd;
		resourceImg.setImage(new Image("image_files/dvd.png"));
		lblTitle.setText(dvd.getTitle());
		lbl1.setText("Year: " + Integer.toString(dvd.getYear()));
		lbl2.setText(dvd.getDirector());
		lbl3.setText(Integer.toString(dvd.getRuntime()));
		lbl4.setText("Language: " + (dvd.getLanguage()));
		lbl5.disableProperty();
		lbl6.disableProperty();
		if (dvd.viewCopies().isEmpty() == true) {
			lblCopies.setText(Integer.toString(0));
		} else {
			lblCopies.setText(Integer.toString(dvd.viewCopies().size()));
		}
	}

	/**
	 * Retrieves information of laptop from the database.
	 * Sets the information of laptop
	 * 
	 * @param resource the new laptop
	 * @throws SQLException if cannot connect to the Database
	 */
	public void setLaptop(Resource resource) throws SQLException {
		this.laptop = (Laptop) new DatabaseRequest().getResource(resource.getResourceID());
		this.resource = laptop;
		resourceImg.setImage(new Image("image_files/laptop.png"));
		lblTitle.setText(laptop.getTitle());
		lbl1.setText(Integer.toString(laptop.getYear()));
		lbl2.setText(laptop.getManufacturer());
		lbl3.setText(laptop.getModel());
		lbl4.setText(laptop.getOperatingSys());
		lbl5.disableProperty();
		lbl6.disableProperty();
		lblCopies.setText(Integer.toString(laptop.viewCopies().size()));
	}

	/**
	 * Retrieve loans from the database.
	 *
	 * @throws IOException  if file "NewLoan.fxml" cannot be found
	 * @throws SQLException if cannot connect to the Database
	 */
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

	/**
	 * Close the window.
	 */
	@FXML
	private void close() {
		window.close();
	}

	@FXML
	private void makeRequest() throws SQLException {
		user.requestResource(resource.getResourceID(),user.getUsername());
	}
	
	/**
	 * Edits the resource from the database.
	 *
	 * @throws IOException if file "EditResource.fxml" cannot be found
	 * @throws SQLException if cannot connect to the Database
	 */
	@FXML
	private void editResource() throws IOException, SQLException {
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/EditResource.fxml"));
		Pane editor = loader.load();
		EditResourceController controller = loader.getController();
		controller.passStageReference(window);
		controller.passResourceID(resource.getResourceID());
		Scene scene = new Scene(editor);
		window.setScene(scene);
		window.show();
	}

	public void newCopies(int newCopiesNo, int copyLength, int currentPosition) throws SQLException {
		DatabaseRequest db = new DatabaseRequest();
		for (int i = currentPosition; i < newCopiesNo; i++) {
			//try{	
				Copy addition = new Copy(resource.getResourceID(),copyLength);
				db.addCopy(addition);
			//}catch (SQLException e) {
			//	newCopies(newCopiesNo, copyLength, i);
			//}
		}
	}
	
	@FXML
	private void makeCopies() throws SQLException {
		int copyLength = Integer.parseInt(InputBox.display("new copies", "How many copies do you wish to add?").get(0));
		int newCopiesNo = Integer.parseInt(InputBox.display("new copies", "How many copies do you wish to add?").get(1));
		newCopies(newCopiesNo,copyLength,0);
	}
	
	/**
	 * Passes current stage onto next class to load new scene on it.
	 * Closes and reverts to previous stage.
	 * 
	 * @param window the window
	 */
	public void passStageReference(Stage window) {
		this.window = window;

	}

	/**
	 * Sets the resource.
	 *
	 * @param resource the new resource
	 */
	public void setResource(Resource resource) {
		this.resource = resource;

	}
}