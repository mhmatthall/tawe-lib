import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditUserController {

	private User user;

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

	private Stage window;

	@FXML
	private void updateUser() {

	}

	@FXML
	private void close() {
		window.close();
	}

	public void passStageReference(Stage window) {
		this.window = window;

	}

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

	public void initialize() {
		System.out.println("I have been initialized and i'm so proud of it");
	}

}
