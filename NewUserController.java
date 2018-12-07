import java.sql.SQLException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * 
 * @author Constantinos Loizou
 *
 */
public class NewUserController {

	private Stage window;
	private boolean isLibrarian = false;

	@FXML
	TextField txtUsername;
	@FXML
	TextField txtFname;
	@FXML
	TextField txtLname;
	@FXML
	TextField txtPhone;
	@FXML
	TextField txtAddress;
	@FXML
	RadioButton rbLib;
	@FXML
	RadioButton rbBor;
	@FXML
	ToggleGroup accType;
	@FXML
	TextField txtEmpDate;
	@FXML
	TextField txtStaffNum;
	@FXML
	Button btnCreate;
	@FXML
	Button btnClose;
	@FXML
	Label lblEmp;
	@FXML
	Label lblStaffNum;

	@FXML
	private void rbLibrarian() {
		txtEmpDate.setDisable(false);
		lblEmp.setDisable(false);
		txtStaffNum.setDisable(false);
		lblStaffNum.setDisable(false);
		isLibrarian = true;
	}

	@FXML
	private void rbBorrower() {
		txtEmpDate.setDisable(true);
		lblEmp.setDisable(true);
		txtStaffNum.setDisable(true);
		lblStaffNum.setDisable(true);
		isLibrarian = false;
	}

	@FXML
	private void close() {
		window.close();
	}

	public void passStageReference(Stage window) {
		this.window = window;

	}

	@FXML
	private void constructUser() throws SQLException {
		String forename = txtFname.getText();
		String surname = txtLname.getText();
		String phone = txtPhone.getText();
		String address = txtAddress.getText();
		String username = txtUsername.getText();
		UserImage profPic = new UserImage("C:/Users/lkonn/Google Drive/University/Year 2/CS230/Assignment 2/tawe-lib/image_files/jeremy.jpeg");

		if (isLibrarian) {
			int staffNum = Integer.parseInt(txtStaffNum.getText());
			String emp = txtEmpDate.getText();
			Scanner read = new Scanner(emp);
					int empDay = read.nextInt();
					int empMonth = read.nextInt();
					int empYear = read.nextInt();
					Date empDate = new Date(empDay, empMonth, empYear);
			read.close();

			Librarian lib1 = new Librarian(username, forename, surname, phone, address, profPic, staffNum, empDate);
			DatabaseRequest db = new DatabaseRequest();
			db.addUser(lib1);
			
			System.out.println("Successfully Created new Librarian with ");
			System.out.print("username: " + lib1.getUsername());

		} else {
			int balance = 0;
			Borrower bor1 = new Borrower(username, forename, surname, phone, address, profPic, balance);
			DatabaseRequest db = new DatabaseRequest();
			db.addUser(bor1);
			System.out.println("Successfully Created new Borrower with ");
			System.out.print("username: " + bor1.getUsername());
		}

	}

}
