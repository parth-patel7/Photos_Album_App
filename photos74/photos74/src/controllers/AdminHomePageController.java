package controllers;


import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import user.User;

public class AdminHomePageController {

	@FXML private ListView<User> usersList;
	@FXML private Button logoutButton;
	@FXML private Button deleteButton;
	@FXML private Button addButton;
	@FXML private TextField username;
	@FXML private TextField password;
	public static ObservableList<User> users = FXCollections.observableArrayList();
	
	public void initialize(Stage primaryStage) {
		if(users.isEmpty()) {
			users.addAll(user.User.userList);
		}
		usersList.setItems(users);
		usersList.getSelectionModel().selectFirst();
		
		// Deleting a user
		deleteButton.setOnAction((ActionEvent e) -> {
			final int selectedIdx = usersList.getSelectionModel().getSelectedIndex();
			String name = usersList.getItems().get(selectedIdx).getName();
			
			if(name.equals("admin") || name.equals("stock")) {
				Alert alert = new Alert(AlertType.WARNING ,  "Cannot delete this user.", ButtonType.OK);
				alert.showAndWait();
			}else{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Delete User");
				alert.setHeaderText("You are about to delete a user.");
				alert.setContentText("Continue?");
	
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					usersList.getItems().remove(selectedIdx);
				    usersList.getSelectionModel().selectFirst();
				    user.User.userList.remove(selectedIdx);
				}
			}
		});
		
		primaryStage.setOnCloseRequest(event -> {
			user.saveData.writeUsersToFile();
		});
		
		logoutButton.setOnAction((ActionEvent e) -> {
			userLogout(e);
		});
	}

	
	@FXML
	public void userLogout(ActionEvent event) {
		Parent newRoot;
		try {
			newRoot = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			Stage s = (Stage) ((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene (newRoot);
			s.setScene(scene);
			s.setTitle("Login");
			s.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addUser(ActionEvent event) throws IOException {
		String name = username.getText();
		String pass = password.getText();
		
		if(name.isBlank() || pass.isBlank()) {
			Alert alert = new Alert(AlertType.WARNING ,  "Cannot enter blank username or password.", ButtonType.OK);
			alert.showAndWait();
		}
		else if(checkIfUserExists(username.getText())) {
			Alert alert = new Alert(AlertType.WARNING ,  "User already exists", ButtonType.OK);
			alert.showAndWait();
		}
		else {
			User u = new User(name, pass);
			user.User.userList.add(u);
			
			users.add(u);
		    usersList.setItems(users);
		    usersList.getSelectionModel().selectFirst();
		}
		
		username.clear();
		password.clear();
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
