import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * in charge of creating a laptop for the database
 * 
 * @author Constantinos Loizou
 *
 */
public class NewLaptopController {

	private Stage window;

	@FXML
	Button btnCreate;
	@FXML
	Button btnCancel;
	@FXML
	TextField txtTitle;
	@FXML
	TextField txtManufacturer;
	@FXML
	TextField txtYear;
	@FXML
	TextField txtModel;
	@FXML
	TextField txtOS;

	/**
	 * Construct laptop and puts it into a database.
	 *
	 */
	@FXML
	private void constructLaptop() {
		String title = txtTitle.getText();
		int year = Integer.parseInt(txtYear.getText());
		String make = txtManufacturer.getText();
		String model = txtModel.getText();
		String os = txtOS.getText();
		Thumbnail thumb = new Thumbnail("laptop.png");

		Laptop laptop1 = new Laptop(title, year, thumb, make, model, os);
		try {
			new DatabaseRequest().addResource(laptop1);
		} catch (SQLException e) {
			System.out.println("CAUGHT SQL EXCEPTION - Unique ID already exists");
			constructLaptop();
		}

		System.out.println(laptop1.toString());

		close();
	}

	/**
	 * Close the window.
	 */
	@FXML
	private void close() {
		window.close();
	}

	/**
	 * Passes current stage onto next class to load new scene on it.
	 * Closes and reverts to previous stage.
	 *
	 * @param window stage window
	 */
	public void passStageReference(Stage window) {
		this.window = window;

	}

}
