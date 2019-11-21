package controllers;

import user.*;
import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserHomePageController {

	@FXML private ListView<UserAlbum> albumsList;
	@FXML private Text userName;
	@FXML private Button openButton;
	@FXML private Button createAlbum;
	@FXML private Button createSave;
	@FXML private TextField createText;
	@FXML private Button renameAlbum;
	@FXML private Button renameSave;
	@FXML private TextField renameField;

	public static ObservableList<UserAlbum> albums = FXCollections.observableArrayList();
	public UserAlbum current;
	
	public void initialize(Stage primaryStage) {
		albums.clear();
		albumsList.getItems().clear();
		if(albums.isEmpty()) {
			for(int i =0; i<controllers.LoginController.currentUser.albums.size(); i++) {
				albums.add(i, controllers.LoginController.currentUser.albums.get(i));
			}
		}
	
		albumsList.setItems(albums);
	}
	
	@FXML
	public void openAlbum(ActionEvent event) throws IOException{
		if(albumsList.getSelectionModel().getSelectedItem()!=null) {
		
			UserAlbum selectedAlbum = albumsList.getSelectionModel().getSelectedItem();
			
			Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/AlbumPage.fxml"));
			Parent root = loader.load();
			AlbumPageController controller = loader.getController();
			controller.initialize(s, selectedAlbum);
			s.setScene(new Scene(root, 1159, 663));
			s.setTitle(controllers.LoginController.currentUser.username);
			s.setResizable(false);
			s.show();
		
		}
	}


	// When Create button is pressed new window pops up
	@FXML
	public void createAlbumPressed(ActionEvent event) {
		try {			
			Stage Stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/createAlbum.fxml"));
			AnchorPane pane = loader.load();
			Scene scene = new Scene(pane);
			Stage.setResizable(false);
			Stage.setScene(scene);
			Stage.show(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML

	public void createSavePressed(ActionEvent event) throws IOException {
		String create = createText.getText();
		Stage s1 = (Stage) createText.getScene().getWindow();
		createAlbum(create);
		s1.close();
	}


	public void createAlbum(String name) {

		if(!controllers.LoginController.currentUser.albums.isEmpty()) {
			for(int i =0; i<controllers.LoginController.currentUser.albums.size(); i++) {
				if(controllers.LoginController.currentUser.albums.get(i).name.equals(name)) {
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Album name already exists", ButtonType.OK);
					alert.showAndWait();
					return;
				} 
			}
		}

		UserAlbum album = new UserAlbum(name);
		controllers.LoginController.currentUser.albums.add(album);
		albums.add(album);
		user.saveData.writeUsersToFile();
		//albumsList.setItems(albums);
		//albumsList.getSelectionModel().selectFirst();
		
		//System.out.println(controllers.LoginController.currentUser.album);
		//clean();
		//initializeList();

	}
	// When Rename button is pressed new window pops up
	@FXML
	public void renameAlbumPressed(ActionEvent event) {
		String newName = renameField.getText();
		String temp = albumsList.getSelectionModel().getSelectedItem().name;
		for(int i =0; i<controllers.LoginController.currentUser.albums.size(); i++) {
			if(controllers.LoginController.currentUser.albums.get(i).name == temp) {
				controllers.LoginController.currentUser.albums.get(i).name = newName;
				albums.get(i).name=newName;
			} 
		}
		renameField.clear();
		albumsList.getSelectionModel().selectFirst();
	}

	@FXML
	public void deleteAlbumPressed(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this album?", ButtonType.YES,  ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.NO) {
			return;
		} else {
			deleteAlbum();
			albumsList.getSelectionModel().selectFirst();
		}
	}

	public void deleteAlbum() {
		String temp = albumsList.getSelectionModel().getSelectedItem().name;
		for(int i =0; i<controllers.LoginController.currentUser.albums.size(); i++) {
			if(controllers.LoginController.currentUser.albums.get(i).name.equals(temp)) {
				controllers.LoginController.currentUser.albums.remove(i);
				albums.remove(i);
				albumsList.setItems(albums);
			}
		}
	}

	@FXML
	public void userLogout(ActionEvent event) {

		Parent newRoot;

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?", ButtonType.YES,  ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.NO) {
			return;
		} else {
			try {
				newRoot = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
				Stage s = (Stage) ((Node)event.getSource()).getScene().getWindow();
				Scene scene = new Scene (newRoot);
				s.setScene(scene);
				s.setTitle("Login");
				s.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void setStock() {
		ArrayList<UserPhoto> stockPhotos = new ArrayList<UserPhoto>();
		File folder = new File("src/stock/");
		File[] files = folder.listFiles();
		
		// set UserPhoto objects for all stock photos.
		for(int i=0;i<files.length;i++) {
			UserPhoto j = new UserPhoto(files[i].getName(),files[i].getAbsolutePath());
			stockPhotos.add(j);
		}
		
		UserAlbum stockAlbum = new UserAlbum("Stock");
		for(int i=0;i<files.length;i++) {
			stockAlbum.addPhoto(stockPhotos.get(i));
			stockAlbum.numOfPhotos++;
		}
		
		user.User.userList.get(1).albums.add(stockAlbum);
	}
}
