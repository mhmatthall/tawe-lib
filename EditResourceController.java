import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
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
	Label lblDirector;
	@FXML
	Label lblDVDLanguage;
	@FXML
	Label lblRuntime;
	@FXML
	TextField txtAuthor;
	@FXML
	TextField txtPublisher;
	@FXML
	TextField txtBookLanguage;

	// For DVDs
	@FXML
	Label lblMake;
	@FXML
	Label lblModel;
	@FXML
	Label lblOS;
	@FXML
	TextField txtISBN;
	@FXML
	TextField txtDirector;
	@FXML
	TextField txtDVDLanguage;
	@FXML
	TextField txtRuntime;
	// For Laptops
	@FXML
	TextField txtMake;
	@FXML
	TextField txtModel;
	@FXML
	TextField txtOS;

	@FXML
	private void update() {

	}

	@FXML
	private void delete() {

	}

	@FXML
	private void close() {
		window.close();
	}

	public void passStageReference(Stage window) {
		this.window = window;

	}

	public void passResourceID(String resID) throws SQLException {
		loadResourceData(resID);

	}

	private void loadResourceData(String resID) throws SQLException {
		this.resource = new DatabaseRequest().getResource(resID);

		lblResID.setText(resource.getResourceID());
		txtTitle.setText(resource.getTitle());
		txtYear.setText(Integer.toString(resource.getYear()));

		if (resource instanceof Book) {
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
	}
	public void passResourceReference(Resource resource) {
		this.resource = resource;

	}
}

