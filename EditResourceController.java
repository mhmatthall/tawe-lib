import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * Controls resource editing in GUI
 *
 * @author Constantinos Loizou
 *
 */
public class EditResourceController {

	private Stage window;
	private Resource resource;

	@FXML
	Button btnUpdate;
	@FXML
	Button btnDelete;
	@FXML
	Button btnExit;
	// For everything
	@FXML
	Label lblResID;
	@FXML
	TextField txtTitle;
	@FXML
	TextField txtYear;

	// For Books
	@FXML
	Label lblBook;
	@FXML
	Label lblAuthor;
	@FXML
	Label lblPublisher;
	@FXML
	Label lblBookLanguage;
	@FXML
	Label lblGenre;
	@FXML
	Label lblISBN;
	@FXML
	TextField txtISBN;
	@FXML
	TextField txtAuthor;
	@FXML
	TextField txtPublisher;
	@FXML
	TextField txtBookLanguage;
	@FXML
	TextField txtGenre;

	// For DVDs
	@FXML
	Label lblDVD;
	@FXML
	Label lblDirector;
	@FXML
	Label lblDVDLanguage;
	@FXML
	Label lblRuntime;
	@FXML
	TextField txtDirector;
	@FXML
	TextField txtDVDLanguage;
	@FXML
	TextField txtRuntime;

	// For Laptops
	@FXML
	Label lblLaptop;
	@FXML
	Label lblMake;
	@FXML
	Label lblModel;
	@FXML
	Label lblOS;
	@FXML
	TextField txtMake;
	@FXML
	TextField txtModel;
	@FXML
	TextField txtOS;

	/**
	 * Update.
	 */
	@FXML
	private void update() throws SQLException {
		String title = txtTitle.getText();
		int year = Integer.parseInt(txtYear.getText());
		if (resource instanceof Book) {
			String author = txtAuthor.getText();
			String publisher = txtPublisher.getText();
			String language = txtBookLanguage.getText();
			String genre = txtGenre.getText();
			String isbn = txtISBN.getText();
			Book newBook = new Book(title, year, resource.getThumbnail(),
					author, publisher, genre, isbn, language);
			new DatabaseRequest().editResource(newBook);
			AlertBox.display("Resource Updated");
		} else if (resource instanceof DVD) {
			String director = txtDirector.getText();
			String language = txtDVDLanguage.getText();
			int runtime = Integer.parseInt(txtRuntime.getText());
			DVD newDVD = new DVD(title, year, resource.getThumbnail(),
					director, runtime, language);
			new DatabaseRequest().editResource(newDVD);
			AlertBox.display("Resource Updated");
		} else {
			String make = txtMake.getText();
			String model = txtModel.getText();
			String os = txtOS.getText();
			Laptop newLaptop = new Laptop(title, year, resource.getThumbnail(),
					make, model, os);
			new DatabaseRequest().editResource(newLaptop);
			AlertBox.display("Resource Updated");
		}
	}

	/**
	 * Delete.
	 */
	@FXML
	private void delete() throws SQLException {
		boolean flag = ConfirmationBox.display("WARNING", "Are you sure you want to delete this resource?");
		if (flag) {
			new DatabaseRequest().deleteResource(resource.getResourceID());
			AlertBox.display("Resource Deleted");
			close();
		} else {
			return;
		}
	}

	/**
	 * Close.
	 */
	@FXML
	private void close() {
		window.close();
	}

	/**
	 * Pass stage reference.
	 *
	 * @param window the window
	 */
	public void passStageReference(Stage window) {
		this.window = window;
	}

	public void passResourceID(String resID) throws SQLException {
		loadResourceData(resID);
	}

	/**
	 * Pass resource reference.
	 *
	 * @param resource the resource
	 */
	private void loadResourceData(String resID) throws SQLException {
		this.resource = new DatabaseRequest().getResource(resID);
		System.out.println("inside edir ");
		lblResID.setText(resource.getResourceID());
		txtTitle.setText(resource.getTitle());
		txtYear.setText(Integer.toString(resource.getYear()));

		if (resource instanceof Book) {
			disableBookIrrelevantkFeatures();
			txtAuthor.setText(((Book) resource).getAuthor());
			txtPublisher.setText(((Book) resource).getPublisher());
			txtBookLanguage.setText(((Book) resource).getLanguage());
			txtGenre.setText(((Book) resource).getGenre());
			txtISBN.setText(((Book) resource).getISBN());
		} else if (resource instanceof DVD) {
			disableDVDIrrelevantFeatures();
			txtDirector.setText(((DVD) resource).getDirector());
			txtDVDLanguage.setText(((DVD) resource).getLanguage());
			txtRuntime.setText(Integer.toString(((DVD) resource).getRuntime()));
		} else {
			disableLaptopIrrelevantFeatures();
			txtMake.setText(((Laptop) resource).getManufacturer());
			txtModel.setText(((Laptop) resource).getModel());
			txtOS.setText(((Laptop) resource).getOperatingSys());

		}

	}

	/**
	 * Pass resource reference.
	 *
	 * @param resource the resource
	 */
	public void passResourceReference(Resource resource) {
		this.resource = resource;
	}
	
	private void disableBookIrrelevantkFeatures() {
		lblDVD.setDisable(true);
		lblLaptop.setDisable(true);
		lblDirector.setDisable(true);
		lblDVDLanguage.setDisable(true);
		lblRuntime.setDisable(true);
		txtDirector.setDisable(true);
		txtDVDLanguage.setDisable(true);
		txtRuntime.setDisable(true);
		lblMake.setDisable(true);
		lblModel.setDisable(true);
		lblOS.setDisable(true);
		txtMake.setDisable(true);
		txtModel.setDisable(true);
		txtOS.setDisable(true);
	}

	private void disableDVDIrrelevantFeatures() {
		lblBook.setDisable(true);
		lblLaptop.setDisable(true);
		lblAuthor.setDisable(true);
		lblPublisher.setDisable(true);
		lblBookLanguage.setDisable(true);
		lblGenre.setDisable(true);
		lblISBN.setDisable(true);
		txtAuthor.setDisable(true);
		txtPublisher.setDisable(true);
		txtBookLanguage.setDisable(true);
		txtISBN.setDisable(true);
		txtGenre.setDisable(true);
		lblISBN.setDisable(true);
		lblMake.setDisable(true);
		lblModel.setDisable(true);
		lblOS.setDisable(true);
		txtMake.setDisable(true);
		txtModel.setDisable(true);
		txtOS.setDisable(true);
	}

	private void disableLaptopIrrelevantFeatures() {
		lblBook.setDisable(true);
		lblDVD.setDisable(true);
		lblAuthor.setDisable(true);
		lblPublisher.setDisable(true);
		lblBookLanguage.setDisable(true);
		lblGenre.setDisable(true);
		lblISBN.setDisable(true);
		txtGenre.setDisable(true);
		txtAuthor.setDisable(true);
		txtPublisher.setDisable(true);
		txtBookLanguage.setDisable(true);
		txtISBN.setDisable(true);
		lblDirector.setDisable(true);
		lblDVDLanguage.setDisable(true);
		lblRuntime.setDisable(true);
		txtDirector.setDisable(true);
		txtDVDLanguage.setDisable(true);
		txtRuntime.setDisable(true);
	}

}
