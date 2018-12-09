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



	/**
	 * Update.
	 */
	@FXML
	private void update() {

	}

	/**
	 * Delete.
	 */
	@FXML
	private void delete() {

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

	/**
	 * Pass resource reference.
	 *
	 * @param resource the resource
	 */
	public void passResourceReference(Resource resource) {
		this.resource = resource;
		
	}

}
