import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * @author Constantinos Loizou
 *
 */
public class SearchLibraryController {
	private Stage window;
	private String type;

	@FXML
	Button btnSearch;
	@FXML
	Button btnCancel;
	@FXML
	RadioButton rbBooks;
	@FXML
	RadioButton rbDVDs;
	@FXML
	RadioButton rbLaptops;
	@FXML
	ToggleGroup radioGroup;
	@FXML
	TextField txtSearchBox;
	@FXML
	TableView<Resource> resultsTable;
	@FXML
	TableColumn<Resource, String> resultsTitle = new TableColumn<>("bloody name");
	@FXML
	TableColumn<Resource, String> resultsYear = new TableColumn<>("Year");
	@FXML
	TableColumn<Resource, String> resultsID = new TableColumn<>("Unique ID");
	
	@FXML
	private void close() {
		window.close();
	}

	@FXML
	private void search() throws SQLException {
		System.out.println("Supposed to search now\n");
		
		if (radioGroup.getSelectedToggle() == null) {
			AlertBox.display("Please select resource type");
			return;
		}
		
		if (radioGroup.getSelectedToggle().equals(rbBooks)) {
			type = "Book"; 
		} else if (radioGroup.getSelectedToggle().equals(rbDVDs)) {
			type = "DVD";
		} else if (radioGroup.getSelectedToggle().equals(rbLaptops)) {
			type = "Laptop";
		}
		
		
		
		ArrayList<Resource> resourceArList = new DatabaseRequest().browseResources();
		ObservableList<Resource> resourceObList = FXCollections.observableArrayList(resourceArList);
		for (Resource resource : resourceArList) {
			resourceObList.add(resource);
		}
		// String has to match EXACTLY the attribute of resource
		resultsTitle.setCellValueFactory(new PropertyValueFactory<Resource, String>("title")); // ONLY THESE TWO
		resultsYear.setCellValueFactory(new PropertyValueFactory<Resource, String>("year")); // ROWS WORK, WTF?
		resultsID.setCellValueFactory(new PropertyValueFactory<Resource, String>("resourceID"));
		resultsTable.setItems(resourceObList);
	}

	public void passStageReference(Stage window) {
		this.window = window;
	}

}
