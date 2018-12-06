import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * @author Constantinos Loizou
 *
 */
public class SearchUserController {
	
	private Stage window;
	
	@FXML Button btnSearch;
	@FXML Button btnCancel;
	@FXML TextField txtUserID;

	
	@FXML private void searchUser() {
		System.out.println("Searching for user");
	}
	
	/*
	 * FUNDAMENTAL CHANGE NEEDED. REMOVE LIST VIEW AND HAVE A PLAIN SEARCH TEXTFIELD
	 * IF USER IS NOT FOUND POP OUT NEW ALERT BOX
	 * IF USER IS FOUND SWITCH SCENE TO USER CONTROL SCENE
	 */
	
	
	
	@FXML

	private void close() {
		window.close();
	}
	
	public void passStageReference(Stage window){
		this.window = window;
	}
	
}