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
public class NewBookController {

	private Stage window;

	@FXML
	Button btnCreate;
	@FXML
	Button btnCancel;
	@FXML
	TextField txtTitle;
	@FXML
	TextField txtAuthor;
	@FXML
	TextField txtYear;
	@FXML
	TextField txtPublisher;
	@FXML
	TextField txtLanguage;
	@FXML
	TextField txtISBN;
	@FXML
	TextField txtGenre;

	@FXML
	private void constructBook() {
		String title = txtTitle.getText();
		int year = Integer.parseInt(txtYear.getText());
		String author = txtAuthor.getText();
		String publisher = (txtPublisher.getText());
		String language = txtLanguage.getText();
		Thumbnail thumb = new Thumbnail("book.png");
		String isbn = txtISBN.getText();
		String genre = txtGenre.getText();

		Book book1 = new Book(title, year, thumb, author, publisher, genre, isbn, language);
		
		DatabaseRequest db;
		try {
			db = new DatabaseRequest();
			db.addResource(book1);
		} catch (SQLException e) {
			System.out.println("CAUGHT SQL EXCEPTION - Unique ID already exists");
			constructBook();
		}
		

		System.out.println(book1.toString());
		close();
	}

	@FXML
	private void close() {
		window.close();
	}

	public void passStageReference(Stage window) {
		this.window = window;

	}

}
