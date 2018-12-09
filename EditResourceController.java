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
public class EditResourceController{

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
	TextField txtAuthor;
	@FXML
	TextField txtPublisher;
	@FXML
	TextField txtBookLanguage;
	// For DVDs
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

	public void passResourceReference(Resource resource) {
		this.resource = resource;
		
	}

}
