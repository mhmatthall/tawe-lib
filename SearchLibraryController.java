import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
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
	Button btnSelect;
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
	TableView<Book> resultsTable;
	@FXML
	TableColumn<Book, String> resultsTitle = new TableColumn<>();
	@FXML
	TableColumn<Book, String> resultsYear = new TableColumn<>();
	@FXML
	TableColumn<Book, String> resultsID = new TableColumn<>();

	@FXML
	private void close() {
		window.close();
	}

	@FXML
	private void search() throws SQLException {
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
		// String has to match EXACTLY the attribute of resource constructor
		resultsTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title")); // ONLY THESE TWO
		resultsYear.setCellValueFactory(new PropertyValueFactory<Book, String>("year")); // ROWS WORK, WTF?
		resultsID.setCellValueFactory(new PropertyValueFactory<Book, String>("resourceID"));
		resultsTable.setItems(resourceObList);
	}
	@FXML
	private void selectItem() throws IOException {
		Book resource = (Book) resultsTable.getSelectionModel().getSelectedItem();
		System.out.println(resource.getTitle() + " " + resource.getResourceID());
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/ResourcePage.fxml"));
		Pane details = loader.load();
		ResourcePageController controller = loader.getController();
		controller.setResource(resource);
		controller.passStageReference(window);
		Scene scene = new Scene(details);
		window.setScene(scene);
		window.show();
	}
	
	public void passStageReference(Stage window) {
		this.window = window;
	}

}
