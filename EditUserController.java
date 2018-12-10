import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * Handles user editing.
 * 
 * @author Constantinos Loizou
 *
 */
public class EditUserController {

	private User user;
	private User editor;

	@FXML
	Label lblUsername;
	@FXML
	Label lblAccType;
	@FXML
	Label lblEmp;
	@FXML
	Label lblStaffNum;
	@FXML
	Label lblStaff;
	@FXML
	Label lblEmpDate;
	@FXML
	TextField txtFname;
	@FXML
	TextField txtLname;
	@FXML
	TextField txtPhone;
	@FXML
	TextField txtAddress;
	@FXML
	Button btnUpdate;
	@FXML
	Button btnClose;
	@FXML
	Button btnDelete;

	private Stage window;

	/**
	 * Closes the window
	 */
	@FXML
	private void close() {
		window.close();
	}

	/**
	 * Deletes user
	 *
	 * @throws SQLException if cannot connect to Database
	 */
	@FXML
	private void deleteUser() throws SQLException {
		if (!editor.isLibrarian()) {
			AlertBox.display("If you want to delete your account,\nplease talk"
					+ " to a Librarian");
			return;
		}
		boolean choice = ConfirmationBox.display("WARNING", "Are you sure you"
				+ " delete this user?");
		if (choice) {
			new DatabaseRequest().deleteUser(lblUsername.getText());
			AlertBox.display("User deleted");
			window.close();
			return;
		} else {
			return;
		}
	}

	/**
	 * Passes current stage onto next class to load new scene on it.
	 * Closes and reverts to previous stage.
	 * @param window the window
	 */
	public void passStageReference(Stage window) {
		this.window = window;

	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(User user) {
		System.out.println(user.toString());
		this.user = user;
		lblUsername.setText(user.getUsername());
		txtFname.setText(user.getForename());
		txtLname.setText(user.getSurname());
		txtPhone.setText(user.getPhoneNumber());
		txtAddress.setText(user.getAddress());
		if (user.isLibrarian()) {
			lblAccType.setText("Librarian");
			lblEmp.setDisable(false);
			lblStaffNum.setDisable(false);
			String empDate = ((Librarian) user).getEmploymentDate().toString();
			lblEmpDate.setText(empDate);
			Integer stuffNum = (((Librarian) user).getStaffNumber());
			lblStaff.setText(stuffNum.toString());
		} else {
			lblAccType.setText("User");

		}

	}

	/**
	 * Update.
	 *
	 * @throws SQLException if connection to database fails
	 */
	@FXML
	public void update() throws SQLException {
		String username = lblUsername.getText();
		String firstName = txtFname.getText();
		String lastName = txtLname.getText();
		String phone = txtPhone.getText();
		String address = txtAddress.getText();

		if (user.isLibrarian()) {
			Librarian updatedUser = new Librarian(user.getUsername(),
					firstName, lastName, phone, address, user.getProfileImage(),
					((Librarian) user).getStaffNumber(),
					((Librarian) user).getEmploymentDate());
			new DatabaseRequest().editUser(updatedUser);
			AlertBox.display("User Updated");
		} else {
			Borrower updatedUser = new Borrower(user.getUsername(), firstName,
					lastName, phone, address,user.getProfileImage(),
					((Borrower) user).getBalance());
			new DatabaseRequest().editUser(updatedUser);
			AlertBox.display("User Updated");
		}
		window.close();
	}

	/**
	 * Disables delete button.
	 */
	public void disableDeleteButton() {
		btnDelete.setDisable(true);
	}

	/**
	 * Checks the Editor. If user is Borrower, delete button gets disabled
	 *
	 * @param editor user who is editing
	 */
	public void setEditor(User editor) {
		this.editor = editor;
		if (!editor.isLibrarian()) {
			btnDelete.setDisable(true);
		}
	}

}
