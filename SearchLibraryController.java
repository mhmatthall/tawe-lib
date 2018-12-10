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


/** Searches resources in the library
 * 
 * @author Constantinos Loizou
 *
 */

public class SearchLibraryController {
	private Stage window;
	private User user;

	@FXML
	Button btnSearch;
	@FXML
	Button btnCancel;
	@FXML
	Button btnSelect;
	@FXML
	Button btnListAll;
	@FXML
	CheckBox cbBooks;
	@FXML
	CheckBox cbDVDs;
	@FXML
	CheckBox cbLaptops;
	@FXML
	TextField txtSearchBox;
	@FXML
	TableView<Resource> resultsTable;
	@FXML
	TableColumn<Resource, String> resultsTitle = new TableColumn<>();
	@FXML
	TableColumn<Resource, String> resultsYear = new TableColumn<>();
	@FXML
	TableColumn<Resource, String> resultsID = new TableColumn<>();

	/**
	 * Closes window.
	 */
	
	@FXML
	private void close() {
		window.close();
	}

	/**
	 * Searches Database with searchTerm.
	 * Displays list of resources matching searchTerm in table form. 
	 *
	 * @throws SQLException fails to connect to Database
	 */
	
	@FXML
	private void search() throws SQLException {

		String searchTerm = txtSearchBox.getText();
		ArrayList<String> type = new ArrayList<>();
		
		if ( !((cbBooks.isSelected()) || (cbDVDs.isSelected()) || (cbLaptops.isSelected())) ) {
			AlertBox.display("Please select at least one resource type");
			return;
		}
		
		if (cbBooks.isSelected()) {
			type.add("BOOK");
		}
		if (cbDVDs.isSelected()){
			type.add("DVD");
		}
		if (cbLaptops.isSelected()) {
			type.add("LAPTOP");
		}
		
		ArrayList<Resource> results = new DatabaseRequest().searchResources(searchTerm, type);
		if (results.isEmpty()) {
			AlertBox.display("No results matching search term");
			return;
		}
		ObservableList<Resource> resourceObList = FXCollections.observableArrayList(results);

		// String has to match EXACTLY the attribute of resource constructor
		resultsTitle.setCellValueFactory(new PropertyValueFactory<Resource, String>("title")); 
		resultsYear.setCellValueFactory(new PropertyValueFactory<Resource, String>("year")); 
		resultsID.setCellValueFactory(new PropertyValueFactory<Resource, String>("resourceID"));
		resultsTable.setItems(resourceObList);
	}

	
	/**
	 * Select item.
	 *
	 * @throws IOException file ResourcePage.fxml is missing
	 * @throws SQLException fails to connect to Database
	 */


	@FXML 
	private void listAll() throws SQLException {
		ArrayList<Resource> allRes = new DatabaseRequest().browseResources();
		
		ObservableList<Resource> resourceObList = FXCollections.observableArrayList(allRes);
		resultsTitle.setCellValueFactory(new PropertyValueFactory<Resource, String>("title")); // ONLY THESE TWO
		resultsYear.setCellValueFactory(new PropertyValueFactory<Resource, String>("year")); // ROWS WORK, WTF?
		resultsID.setCellValueFactory(new PropertyValueFactory<Resource, String>("resourceID"));
		resultsTable.setItems(resourceObList);
	}

	
	@FXML
	private void selectItem() throws IOException, SQLException {
		System.out.println(resultsTable.getSelectionModel().getSelectedItem().getTitle() + " "
				+ resultsTable.getSelectionModel().getSelectedItem().getResourceID());
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/ResourcePage.fxml"));
		Pane details = loader.load();
		ResourcePageController controller = loader.getController();
		if (resultsTable.getSelectionModel().getSelectedItem() instanceof Book) {
			controller.setBook(resultsTable.getSelectionModel().getSelectedItem());
		} else if (resultsTable.getSelectionModel().getSelectedItem() instanceof DVD) {
			controller.setDVD(resultsTable.getSelectionModel().getSelectedItem());
		} else if (resultsTable.getSelectionModel().getSelectedItem() instanceof Laptop){
			controller.setLaptop(resultsTable.getSelectionModel().getSelectedItem());
		} else {
			throw new IllegalArgumentException("UNKNOWN RESOURCE TYPE");
		}
		controller.passStageReference(window);
		controller.setUser(user);
		Scene scene = new Scene(details);
		window.setScene(scene);
		window.show();
	}

	
	/**
	 * Sets the user.
	 *
	 * @param user set user
	 */
	
	public void setUser(User user) {
		this.user = user;
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

}
