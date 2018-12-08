import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Constantinos Loizou
 *
 */
public class SearchLibraryController {
	private Stage window;
	
	@FXML Button btnSearch;
	@FXML Button btnCancel;
	@FXML CheckBox cbBooks;
	@FXML CheckBox cbDVDs;
	@FXML CheckBox cbLaptops;
	
	@FXML TextField txtSearchBox;
	@FXML TableView<Resource> resultsTable;
	@FXML TableColumn<Resource,String> resultsType = new TableColumn<>("Type");
	@FXML TableColumn<Resource,String> resultsTitle = new TableColumn<>("Title");
	@FXML TableColumn<Resource,String> resultsYear = new TableColumn<>("Year");
	@FXML TableColumn<Resource,String> resultsID = new TableColumn<>("Unique ID");


	
	@FXML private void close() {
		window.close();
	}
	
	@FXML private void search() throws SQLException {
		System.out.println("Supposed to search now");
		
		ArrayList<Resource> allRes = new DatabaseRequest().browseResources();
		
		ObservableList<Resource> resList = FXCollections.observableArrayList(allRes);
		for (Resource res: resList) {
			System.out.println(res.getResourceID() + " " + res.getTitle());
		}

		
	}
	
	public void passStageReference(Stage window) {
		this.window = window;
	}
	
	
	
}
