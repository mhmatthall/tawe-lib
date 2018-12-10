import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * in charge of creating a loan for the database
 * @author Constantinos Loizou
 *
 */
public class NewLoanController {
	private Stage window;
	private Resource resource;
	int i = 0;
	public ObservableList<String> usersWaiting;
	public ObservableList<String> copies = FXCollections.observableArrayList("");

	@FXML
	Button btnIssueLoan;
	@FXML
	Button btnclose;
	@FXML
	Label lblResourceID;
	@FXML
	ChoiceBox<String> userList;

	/**
	 * constructs a loan and adds it to the database.
	 *
	 * @throws SQLException the SQL exception
	 */
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

	/**
	 * Pass stage reference.
	 *
	 * @param window stage window
	 */
	public void passStageReference(Stage window) {
		this.window = window;
	}

	/**
	 * reserves a copy for a user if its not reserved by others
	 *
	 * @param resource the new resource
	 * @throws SQLException the SQL exception
	 */
	public void setResource(Resource resource) throws SQLException {
		this.resource = resource;
		ObservableList<String> usersWaiting = FXCollections.observableArrayList("");;
		for (Copy copy : new DatabaseRequest().getCopies(resource.getResourceID())) {
			try {
				if (copy.getReservingUser() != null){
					usersWaiting.add(copy.getReservingUser());
					copies.add(copy.getCopyID());
				}
				} catch( NullPointerException e){
					
				}

			}
		userList.setItems(usersWaiting);
		userList.getSelectionModel().selectFirst();
	}

}
