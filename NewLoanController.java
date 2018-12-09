import java.sql.SQLException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * 
 * @author Constantinos Loizou
 *
 */
public class NewLoanController {
	private Stage window;
	private Resource resource;

	private ObservableList<String> usersWaiting;
	private ObservableList<String> copies;

	@FXML
	Button btnIssueLoan;
	@FXML
	Button btnclose;
	@FXML
	Label lblResourceID;
	@FXML
	ChoiceBox<String> userList;

	@FXML
	private void loan() throws SQLException {
		int i = userList.getSelectionModel().getSelectedIndex();
		String username = userList.getSelectionModel().getSelectedItem();
		Loan loan1 = new Loan(copies.get(i), username);
		try {
			new DatabaseRequest().addLoan(loan1);
		} catch (SQLException e) {
			System.out.println("Encountered some problem when issuing the loan");
			e.getMessage();
			e.getClass();
		}

	}

	public void passStageReference(Stage window) {
		this.window = window;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
		// Add users that have reserved the resource to a list
		for (Copy copy : resource.viewCopies()) {
			if (copy.getReservingUser() != null) {
				usersWaiting.add(copy.getReservingUser());
				copies.add(copy.getCopyID());

			}
		}
		userList.setItems(usersWaiting);
		userList.getSelectionModel().selectFirst();
	}

}
