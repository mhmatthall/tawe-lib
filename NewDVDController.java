import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * in charge of creating a DVD for the database
 * @author Constantinos Loizou
 *
 */
public class NewDVDController {

	private Stage window;

	@FXML
	Button btnCreate;
	@FXML
	Button btnCancel;
	@FXML
	TextField txtTitle;
	@FXML
	TextField txtDirector;
	@FXML
	TextField txtYear;
	@FXML
	TextField txtRuntime;
	@FXML
	TextField txtLanguage;

	/**
	 * Construct DVD and puts it into a database
	 *
	 * @throws SQLException if connection to database fails
	 */
	@FXML
	private void constructDVD() throws SQLException {
		String title = txtTitle.getText();
		int year = Integer.parseInt(txtYear.getText());
		String director = txtDirector.getText();
		int runtime = Integer.parseInt(txtRuntime.getText());
		String language = txtLanguage.getText();
		Thumbnail thumb = new Thumbnail("dvd.png");

		DVD dvd1 = new DVD(title, year, thumb, director, runtime, language);
		new DatabaseRequest().addResource(dvd1);

		System.out.println(dvd1.toString());
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
	 * Pass stage reference.
	 *
	 * @param window the staged to be passed
	 */
	public void passStageReference(Stage window) {
		this.window = window;

	}

}
