package controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;


public class LoginController {

	@FXML private Button login;
	@FXML private TextField username;
	@FXML private TextField password;
	@FXML private AnchorPane rootPane;




	@FXML
	public void loginButton(ActionEvent event) throws IOException  {

		// Checks if any field is empty
		if(username.getText().isEmpty() || password.getText().isEmpty() ) {
			userWarning();
			return;
		}

		String name = username.getText();
		String pass = password.getText();

		if(name == "admin" && pass == "admin") {

			// load admin scene

		} else if(checkIfUserExists(name, pass)) {
//			Parent newRoot = FXMLLoader.load(getClass().getResource("/view/UserHomePage.fxml"));
//			Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
//			Scene scene = new Scene (newRoot);
//			s.setScene(scene);
//			s.setTitle("User Login");
//			s.show();
			loadLogin(event);
		} else {
			userWarning();
		}
	}

	public void loadLogin(ActionEvent event) {
		try {
			Parent newRoot;
			newRoot = FXMLLoader.load(getClass().getResource("/view/UserHomePage.fxml"));
			Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene (newRoot);
			s.setScene(scene);
			s.setTitle("User Login");
			s.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void userWarning() {
		Alert alert = new Alert(AlertType.WARNING ,  "User Does not exists", ButtonType.OK);
		alert.showAndWait();
		return;
	}


	public boolean checkIfUserExists(String name, String pass) {
		String line;

		try {
			FileReader file = new FileReader("LoginDetails.txt");
			BufferedReader bufferedReader = new BufferedReader(file);
			while ((line = bufferedReader.readLine()) != null ) {
				if(line.contains(name) && line.contains(pass)) {
					bufferedReader.close();
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

}