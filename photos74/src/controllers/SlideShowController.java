package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import user.*;
/**
 * @author joshherrera
 * @author parthpatel
 */
public class SlideShowController {
	@FXML private ImageView slideImage=new ImageView();
	@FXML private Button next;
	@FXML private Button prev;
	@FXML private Label caption;
	@FXML private Label tags;
	
	public static int cindex;
	public static UserAlbum current;
	
	
	/**
	 * Initializes a slideshow of all the photos in the current album.
	 */
	public void initialize() {
		cindex=0;
		prev.setDisable(true);
		current = AlbumPageController.currentA;
		setImage();
		
		if(current.numOfPhotos==1) {
			next.setDisable(true);
		}
		
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
		caption.setText(current.photos.get(cindex).caption);
		tags.setText(" "+current.photos.get(cindex).tagList.toString().replace("[", "").replace("]", "").replace(",", ""));
		
	}
}
