import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * @author Constantinos Loizou
 *
 */
public class NewLaptopController {
	
	private Stage window;
	
	@FXML Button btnCreate;
	@FXML Button btnCancel;
	@FXML TextField txtTitle;
	@FXML TextField txtManufacturer;
	@FXML TextField txtYear;
	@FXML TextField txtModel;
	@FXML TextField txtOS;
	
	@FXML
	private void constructLaptop() throws SQLException{
		String title = txtTitle.getText();
		int year = Integer.parseInt(txtYear.getText());
		String make = txtManufacturer.getText(); 
		String model = txtModel.getText();
		String os = txtOS.getText();
		Thumbnail thumb = new Thumbnail(null);
		
		Laptop laptop1 = new Laptop(title, year, thumb, make, model, os);
		new DatabaseRequest().addResource(laptop1);
		
		System.out.println(laptop1.toString());
		
		window.close();
	}

	@FXML
	private void close() {
		window.close();
	}
	
	public void passStageReference(Stage window) {
		this.window = window;
		
	}
	
	
	
}
