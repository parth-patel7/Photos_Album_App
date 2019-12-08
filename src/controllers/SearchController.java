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
import user.UserAlbum;
import javafx.stage.Stage;
import user.*;


/**
 * @author joshherrera
 * @author parthpatel
 */
public class SearchController {
	@FXML private ImageView slideImage=new ImageView();
	@FXML private Button next;
	@FXML private Button prev;
	@FXML private Label caption;
	@FXML private TextField albumName;

	public static int cindex;
	public static UserAlbum current;

	/**
	 * Initializes the slideshow of search results.
	 * @param album
	 */
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

	
	/**
	 * If "next" clicked, cycle to the next photo. Cannot click next if on the last photo.
	 */
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

	/**
	 * If "previous" clicked, cycle to the previous photo. Cannot click next if on the first photo.
	 */
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

	/**
	 * Used to set a given thumbnail image.
	 */
	public void setImage() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(current.photos.get(cindex).path);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}

		slideImage.setImage(new Image(fis));
	}

	
	/**
	 * If "create album from results" clicked, create an album from results in the user's album list.
	 */
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
