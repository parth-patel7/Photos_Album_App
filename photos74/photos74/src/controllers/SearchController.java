package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import user.*;


public class SearchController {
	@FXML private ImageView slideImage=new ImageView();
	@FXML private Button next;
	@FXML private Button prev;
	@FXML private Label caption;
	@FXML private TextField albumName;

	public static int cindex;
	public static UserAlbum current;

	public void initialize(UserAlbum album) {
		cindex=0;
		prev.setDisable(true);

		current = album;
		setImage();

		next.setOnAction((ActionEvent e) -> {
			next();
		});
		prev.setOnAction((ActionEvent e) -> {
			prev();
		});
	}

	@FXML
	public void next() {
		cindex++;
		if(cindex>0) {
			prev.setDisable(false);
		}
		if(cindex==current.numOfPhotos-1) {
			next.setDisable(true);
		}
		setImage();
	}

	@FXML
	public void prev() {
		cindex--;
		if(cindex<current.numOfPhotos-1) {
			next.setDisable(false);
		}
		if(cindex==0) {
			prev.setDisable(true);
		}
		setImage();
	}

	public void setImage() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(current.photos.get(cindex).path);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}

		slideImage.setImage(new Image(fis));
	}

	public void createAlbumFromResults(){
		if( albumName.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Name Required", ButtonType.OK);
			alert.showAndWait();
		} else {
			Stage s = (Stage) albumName.getScene().getWindow();
			s.close();
			current.name = albumName.getText();
			LoginController.currentUser.addAlbum(current);
		}
	}
}
