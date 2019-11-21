package controllers;

import java.io.BufferedReader;
import java.io.File;

import user.*;
import java.io.FileReader;
import java.io.IOException;

import controllers.AdminHomePageController;
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

	public static User currentUser;
	public static int currentUserIndex;
	public static int session = 0;

	@FXML
	public void loginButton(ActionEvent event) throws IOException  {
		
		if(user.User.userList.isEmpty()) {
			User stock = new User("stock","stock");
			user.User.userList.add(stock);
		}

		// Checks if any field is empty
		if(username.getText().isEmpty() || password.getText().isEmpty() ) {
			userWarning();
			return;
		}

		//String home = System.getProperty("user.home");
		//File f = new File(home + File.separator + "Desktop" + File.separator + "Testing" + File.separator + "Java.txt");
		//System.out.println(home + "  HOME");
		
		String name = username.getText();
		String pass = password.getText();

		if(name.equals("admin") && pass.equals("admin")) {
			session++;
			Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/AdminHomePage.fxml"));
			Parent root = loader.load();
			AdminHomePageController controller = loader.getController();
			controller.initialize(s);
			s.setScene(new Scene(root, 640, 480));
			s.setTitle("Admin");
			s.show();

		} else if(checkIfUserExists(name)) {

			int userIndex=0;
			for(int i = userIndex;i<user.User.userList.size();i++) {

				if(user.User.userList.get(i).getName().equals(name)) {
					userIndex=i;
					break;
				}
			}
			currentUserIndex = userIndex;
			currentUser = user.User.userList.get(userIndex);

			/*Parent newRoot = FXMLLoader.load(getClass().getResource("/view/UserHomePage.fxml"));
			Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene (newRoot);
			s.setScene(scene);
			s.setTitle(currentUser.getName());
			s.show();
			UserHomePageController.initializeList();*/

			if(name.equals("stock") && pass.equals("stock")) {
				if(currentUser.albums.isEmpty()) {
					UserAlbum stockAlbum = new UserAlbum("Stock Album");
					File folder = new File("src/stock/");
					File[] files = folder.listFiles();
					
					for(int i=0;i<5;i++) {
						String s = String.valueOf(i);
						UserPhoto p = new UserPhoto(s,files[i].getAbsolutePath());
						stockAlbum.addPhoto(p);
					}
					stockAlbum.numOfPhotos=5;
					currentUser.albums.add(stockAlbum);
					//stockAlbum.addPhoto(photo);
				}
			}

			Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/UserHomePage.fxml"));
			Parent root = loader.load();
			UserHomePageController controller = loader.getController();
			controller.initialize(s);
			s.setScene(new Scene(root, 736, 402));
			s.setTitle("Home");
			s.show();
		} else {
			userWarning();
		}
	}


	public void userWarning() {
		Alert alert = new Alert(AlertType.WARNING ,  "User Does not exists", ButtonType.OK);
		alert.showAndWait();
		return;
	}


	public boolean checkIfUserExists(String name) {
		if(user.User.userList.isEmpty()) {
			return false;
		}
		else {
			for(int i=0;i<user.User.userList.size();i++) {
				if(user.User.userList.get(i).getName().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}


}