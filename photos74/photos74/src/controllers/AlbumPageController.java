package controllers;

import user.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;

public class AlbumPageController {
	@FXML private ImageView image1=new ImageView();
	@FXML private ImageView image2=new ImageView();
	@FXML private ImageView image3=new ImageView();
	@FXML private ImageView image4=new ImageView();
	@FXML private ImageView bigImage=new ImageView();
	@FXML private Label albumName;
	@FXML private Label latestPhoto, oldestPhoto;
	@FXML private Label numPhotos;
	@FXML private Label captionLabel;
	@FXML private Button next;
	@FXML private Button prev;
	@FXML private TextField captionField;
	@FXML private Button captionSave;
	@FXML private Button deleteBtn, slideshow;
	@FXML private Button addBtn;
	@FXML private Button moveBtn;
	@FXML private Button copyBtn;
	@FXML private TextField addPathField;
	@FXML private TextField addNameField;
	@FXML private TextField moveField;


	// Search buttons
	@FXML private ToggleGroup option, group;
	@FXML private Button searchCancel;//, addTagSave, deleteTag;
	@FXML private RadioButton tagButton, dateButton, singleTag, andTag, orTag;
	@FXML private DatePicker fromDate, toDate;
	@FXML private TextField tag1, tag2, value1, value2;// tag, value, delTag, delValue;
	//@FXML private ComboBox<String> tag1, tag2;

	@FXML private TextField addTag, addValue, delTag, delVal;
	@FXML private Button addTagPressed, delTagPressed;


	public int[] indicies = new int[4];
	public static UserPhoto current;
	public int currentIndex=0;
	public static UserAlbum currentA;

	public void initialize(Stage primaryStage, UserAlbum album) {
		currentA=album;

		for(int i=0;i<4;i++) {
			indicies[i]=i;
		}

		albumName.setText(album.name);
		latestPhoto.setText(user.UserPhoto.getLatestPhoto(album));
		oldestPhoto.setText(user.UserPhoto.getOldestPhoto(album));
		numPhotos.setText(Integer.toString(album.numOfPhotos));

		if(!album.photos.isEmpty()) {
			setImages();
			setImage(bigImage,0);
		}

		image1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(indicies[0]<album.numOfPhotos) {
					setImage(bigImage,indicies[0]);
					currentIndex = indicies[0];
				}
			}
		});
		image2.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(indicies[1]<album.numOfPhotos) {
					setImage(bigImage,indicies[1]);
					currentIndex = indicies[1];
				}
			}
		});
		image3.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(indicies[2]<album.numOfPhotos) {
					setImage(bigImage,indicies[2]);
					currentIndex = indicies[2];
				}
			}
		});
		image4.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(indicies[3]<album.numOfPhotos) {
					setImage(bigImage,indicies[3]);
					currentIndex = indicies[3];
				}
			}
		});


		next.setOnAction((ActionEvent e) -> {
			nextSet(album);
		});
		prev.setOnAction((ActionEvent e) -> {
			prevSet(album);
		});
		captionSave.setOnAction((ActionEvent e) -> {
			addCaption();
		});
		deleteBtn.setOnAction((ActionEvent e) -> {
			deletePhoto(currentIndex, album);
		});	
		addBtn.setOnAction((ActionEvent e) -> {
			addPhoto(album);
		});
		moveBtn.setOnAction((ActionEvent e) -> {
			movePhoto(currentIndex,album);
		});
		copyBtn.setOnAction((ActionEvent e) -> {
			copyPhoto(currentIndex,album);
		});

		slideshow.setOnAction((ActionEvent e) -> {
			slideshowPressed(album);
		});

		addTagPressed.setOnAction((ActionEvent e) -> {
			addTag();
		});

		delTagPressed.setOnAction((ActionEvent e) -> {
			delTag();
		});

	}

	public void addTag() {
		if(addTag.getText().isEmpty() || addValue.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Empty field not allowed", ButtonType.OK);
			alert.showAndWait();
			return;
		}

		current=currentA.photos.get(currentIndex);
		for(int i=0;i<current.tagList.size();i++) {
			UserTags t = current.tagList.get(i);
			if(t.getTag().equals(addTag.getText()) && t.getValue().equals(addValue.getText())) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Duplicate tag not allowed", ButtonType.OK);
				alert.showAndWait();
				return;
			}
		}


		String tagName = addTag.getText();
		String tagValue = addValue.getText();
		UserTags tag = new UserTags(tagName, tagValue);
		current.tagList.add(tag);
		addTag.clear();
		addValue.clear();
	}

	public void delTag() {
		if(delTag.getText().isEmpty() || delVal.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Empty field not allowed", ButtonType.OK);
			alert.showAndWait();
			return;
		} else {
			String tagName = delTag.getText();
			String tagValue = delVal.getText();
			// Find tag and delete it 
			for(int i = 0; i < currentA.photos.get(currentIndex).tagList.size(); i++) {
				if( currentA.photos.get(currentIndex).tagList.get(i).getTag().equals(tagName) &&
						currentA.photos.get(currentIndex).tagList.get(i).getValue().equals(tagValue)) {
					currentA.photos.get(currentIndex).tagList.remove(i);
					delTag.clear();
					delVal.clear();
					return;
				}
			}

		}
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

	@FXML
	public void nextSet(UserAlbum album){
		if(indicies[3]<album.photos.size()) {
			for(int i=0;i<4;i++) {
				indicies[i]=indicies[i]+4;
			}
		}
		setImages();
	}

	@FXML
	public void prevSet(UserAlbum album){
		if(indicies[0]!=0) {
			for(int i=0;i<4;i++) {
				indicies[i]=indicies[i]-4;
			}
		}
		setImages();
	}

	public void setImages() {
		if(indicies[0]<currentA.photos.size()) {
			setImage(image1,indicies[0]);
		}
		else {
			image1.setImage(null); 
		}
		if(indicies[1]<currentA.photos.size()) {
			setImage(image2,indicies[1]);
		}else { 
			image2.setImage(null); 
		}
		if(indicies[2]<currentA.photos.size()) {
			setImage(image3,indicies[2]);
		}else { 
			image3.setImage(null); 
		}
		if(indicies[3]<currentA.photos.size()) {
			setImage(image4,indicies[3]);
		}else { 
			image4.setImage(null); 
		}
	}

	@FXML
	public void addCaption() {
		currentA.photos.get(currentIndex).addCaption(captionField.getText());
		captionField.clear();
	}

	public void deletePhoto(int index, UserAlbum album) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete User");
		alert.setHeaderText("You are about to delete a photo.");
		alert.setContentText("Continue?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			album.photos.remove(index);
			album.numOfPhotos--;
			setImages();
			if(currentA.numOfPhotos!=0) {
				setImage(bigImage,0);
			}
		}
		albumName.setText(album.name);
		latestPhoto.setText(user.UserPhoto.getLatestPhoto(album));
		oldestPhoto.setText(user.UserPhoto.getOldestPhoto(album));
		numPhotos.setText(Integer.toString(album.numOfPhotos));

	}

	public void addPhoto(UserAlbum album) {

		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(addBtn.getScene().getWindow());
		if(selectedFile!=null) {
			String path = selectedFile.getAbsolutePath();
			if(addNameField.getText().isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Empty field not allowed", ButtonType.OK);
				alert.showAndWait();
				return;
			}
			String name = addNameField.getText();
			UserPhoto newPhoto = new UserPhoto(name,path);
			Date date = new Date();
			newPhoto.cal.set(1900+date.getYear(), date.getMonth(), date.getDate());
			album.addPhoto(newPhoto);
			album.numOfPhotos++;
			addNameField.clear();
			albumName.setText(album.name);
			latestPhoto.setText(newPhoto.cal.getTime().toString());
			oldestPhoto.setText(user.UserPhoto.getOldestPhoto(album));
			numPhotos.setText(Integer.toString(album.numOfPhotos));
			setImages();
			setImage(bigImage,0);
		}
	}

	public void slideshowPressed(UserAlbum album){
		try {			

			Stage s = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/SlideShow.fxml"));
			Parent root = loader.load();
			SlideShowController controller = loader.getController();
			controller.initialize();
			s.setScene(new Scene(root, 600, 400));
			s.setResizable(false);
			s.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void goHome(ActionEvent event) throws IOException{
		Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/UserHomePage.fxml"));
		Parent root = loader.load();
		UserHomePageController controller = loader.getController();
		controller.initialize(s);
		s.setScene(new Scene(root, 736, 402));
		s.setTitle("Home");
		s.show();
	}

	// Search button pressed
	@FXML
	public void searchButtonPressed(ActionEvent event) {
		try {			
			Stage Stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/searchMain.fxml"));
			AnchorPane pane = loader.load();
			Scene scene = new Scene(pane);
			Stage.setResizable(false);
			Stage.setScene(scene);
			Stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// If cancel is pressed during search selection
	@FXML public void searchCancelPressed(ActionEvent event) {
		Stage s = (Stage) searchCancel.getScene().getWindow();
		s.close();
	}

	@FXML
	public void searchByDatePressed(ActionEvent event){
		if(fromDate.getValue() == null || toDate.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Select Date", ButtonType.OK);
			alert.showAndWait();
		} else	if(fromDate.getValue().isAfter(toDate.getValue())) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Incorrect Slection", ButtonType.OK);
			alert.showAndWait();

		}	else {
			Stage s = (Stage) toDate.getScene().getWindow();
			s.close();
			// Search photos

			UserAlbum searchResult = new UserAlbum("searchResult");

			LocalDate localDate = toDate.getValue();
			Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			date.setSeconds(0);
			Calendar ToDate = Calendar.getInstance();
			ToDate.setTime(date);


			LocalDate localDate2 = fromDate.getValue();
			Date date2 = Date.from(localDate2.atStartOfDay(ZoneId.systemDefault()).toInstant());
			date2.setSeconds(0);
			Calendar FromDate = Calendar.getInstance();
			FromDate.setTime(date2);

			for(int i =0 ; i < LoginController.currentUser.albums.size(); i++){
				for(int j = 0; j < LoginController.currentUser.albums.get(i).photos.size(); j++) {
					if( (LoginController.currentUser.albums.get(i).photos.get(j).cal.after(FromDate)) &&
							(LoginController.currentUser.albums.get(i).photos.get(j).cal.before(ToDate))	) {
						//System.out.println("YES");
						searchResult.addPhoto(LoginController.currentUser.albums.get(i).photos.get(j));
						searchResult.numOfPhotos++;
					}
				}
			}
			if(searchResult.photos.size() == 0) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No Result", ButtonType.OK);
				alert.showAndWait();
			} else {
				searchResultPressed(searchResult);
			}
		}
	}


	@FXML
	public void searchByTagSavePressed(ActionEvent event){
		Toggle selectedTogger = group.getSelectedToggle();

		if(selectedTogger == null) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Seletct a toggle", ButtonType.OK);
			alert.showAndWait();

		}else if(selectedTogger == orTag) {
			if(tag1.getText().isEmpty() || tag2.getText().isEmpty() || value1.getText().isEmpty() || value2.getText().isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Missing tag or value", ButtonType.OK);
				alert.showAndWait();
			}
			searchViaOrTag(tag1.getText(), value1.getText(), tag2.getText(), value2.getText());
			Stage s = (Stage) tag1.getScene().getWindow();
			s.close();

		} else if(selectedTogger == andTag) {
			if(tag1.getText().isEmpty() || tag2.getText().isEmpty() || value1.getText().isEmpty() || value2.getText().isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Missing tag or value", ButtonType.OK);
				alert.showAndWait();
			}
			searchViaAndTag(tag1.getText(), value1.getText(), tag2.getText(), value2.getText());
			Stage s = (Stage) tag1.getScene().getWindow();
			s.close();

		} else if(selectedTogger == singleTag) {
			if( (!tag1.getText().isEmpty() && !tag2.getText().isEmpty()) || 
					(!value1.getText().isEmpty() && !value2.getText().isEmpty())) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Only one tag allowed", ButtonType.OK);
				alert.showAndWait();
			}
			searchViaSingleTag(tag1.getText(), value1.getText());
			Stage s = (Stage) tag1.getScene().getWindow();
			s.close();
		}
	}

	public void searchResultPressed(UserAlbum album){
		try {			
			Stage s = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/SearchResult.fxml"));
			Parent root = loader.load();
			SearchController controller = loader.getController();
			controller.initialize(album);
			s.setScene(new Scene(root, 600, 400));
			s.setResizable(false);
			s.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void searchViaOrTag(String firstTag,  String firstValue, String secondTag, String secondValue){

		UserAlbum searchResult = new UserAlbum("searchResult");

		for(int i =0 ; i < LoginController.currentUser.albums.size(); i++){
			for(int j = 0; j < LoginController.currentUser.albums.get(i).photos.size(); j++) {
				for(int k = 0; k < LoginController.currentUser.albums.get(i).photos.get(j).tagList.size(); k++) {
					if( (LoginController.currentUser.albums.get(i).photos.get(j).tagList.get(k).getTag().equals(firstTag) &&
							LoginController.currentUser.albums.get(i).photos.get(j).tagList.get(k).getValue().equals(firstValue)) ||
							(LoginController.currentUser.albums.get(i).photos.get(j).tagList.get(k).getTag().equals(secondTag) &&
									LoginController.currentUser.albums.get(i).photos.get(j).tagList.get(k).getValue().equals(secondValue))) {
						//System.out.println("FOUND (OR) \n");
						searchResult.photos.add(LoginController.currentUser.albums.get(i).photos.get(j));
						searchResult.numOfPhotos++;
					}
				}
			}
		}
		if(searchResult.photos.size() == 0) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No Result", ButtonType.OK);
			alert.showAndWait();
		} else {
			searchResultPressed(searchResult);
		}
	}

	public void searchViaAndTag(String firstTag,  String firstValue, String secondTag, String secondValue){

		UserAlbum searchResult = new UserAlbum("searchResult");

		for(int i =0 ; i < LoginController.currentUser.albums.size(); i++){
			for(int j = 0; j < LoginController.currentUser.albums.get(i).photos.size(); j++) {
				for(int k = 0; k < LoginController.currentUser.albums.get(i).photos.get(j).tagList.size(); k++) {
					
					if( (LoginController.currentUser.albums.get(i).photos.get(j).tagList.get(k).getTag().equals(firstTag) &&
							LoginController.currentUser.albums.get(i).photos.get(j).tagList.get(k).getValue().equals(firstValue)) &&
							(LoginController.currentUser.albums.get(i).photos.get(j).tagList.get(k).getTag().equals(secondTag) &&
									LoginController.currentUser.albums.get(i).photos.get(j).tagList.get(k).getValue().equals(secondValue))) {
						//System.out.println("FOUND (OR) \n");
						searchResult.photos.add(LoginController.currentUser.albums.get(i).photos.get(j));
						searchResult.numOfPhotos++;
					}
				}
			}
		}
		if(searchResult.photos.size() == 0) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No Result", ButtonType.OK);
			alert.showAndWait();
		} else {
			searchResultPressed(searchResult);
		}
	}

	public void searchViaSingleTag(String firstTag,  String firstValue) {

		UserAlbum searchResult = new UserAlbum("searchResult");

		for(int i =0 ; i < LoginController.currentUser.albums.size(); i++){
			for(int j = 0; j < LoginController.currentUser.albums.get(i).photos.size(); j++) {
				for(int k = 0; k < LoginController.currentUser.albums.get(i).photos.get(j).tagList.size(); k++) {
					if( (LoginController.currentUser.albums.get(i).photos.get(j).tagList.get(k).getTag().equals(firstTag) &&
							LoginController.currentUser.albums.get(i).photos.get(j).tagList.get(k).getValue().equals(firstValue)) ) {
						searchResult.photos.add(LoginController.currentUser.albums.get(i).photos.get(j));
						searchResult.numOfPhotos++;
					}
				}
			}
		}
		if(searchResult.photos.size() == 0) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No Result", ButtonType.OK);
			alert.showAndWait();
		} else {
			searchResultPressed(searchResult);
		}
	}

	// If save is pressed during search selection
	@FXML
	public void searchSelection(ActionEvent event) {
		Toggle selectedTogger = option.getSelectedToggle();

		if(selectedTogger == dateButton) {
			Stage s = (Stage) searchCancel.getScene().getWindow();
			s.close();
			try {
				Stage Stage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/searchByDate.fxml"));
				AnchorPane pane = loader.load();
				Scene scene = new Scene(pane);
				Stage.setResizable(false);
				Stage.setScene(scene);
				Stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(selectedTogger == tagButton) {
			Stage s = (Stage) searchCancel.getScene().getWindow();
			s.close();
			try {
				//				tag1.getItems().addAll("one", "two");
				//				tag1.setEditable(true);
				Stage Stage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/searchByTag.fxml"));
				AnchorPane pane = loader.load();
				Scene scene = new Scene(pane);
				Stage.setResizable(false);
				Stage.setScene(scene);
				Stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please select an option", ButtonType.OK);
			alert.showAndWait();
		}
	}

	public void movePhoto(int index, UserAlbum album) {
		try {			
			Stage Stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/movePhoto.fxml"));
			AnchorPane pane = loader.load();
			Scene scene = new Scene(pane);

			Stage.setResizable(false);
			Stage.setScene(scene);
			Stage.show(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void moveConfirm() {
		String albumName = moveField.getText();
		if(albumName.equals(currentA.getName())) {
			Alert alert = new Alert(AlertType.WARNING ,  "Cannot move to this album.", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		for(int i =0; i<controllers.LoginController.currentUser.albums.size(); i++) {
			if(controllers.LoginController.currentUser.albums.get(i).getName().equals(albumName)) {
				controllers.LoginController.currentUser.albums.get(i).addPhoto(currentA.photos.get(currentIndex));
				controllers.LoginController.currentUser.albums.get(i).numOfPhotos++;

				currentA.numOfPhotos--;
				currentA.photos.remove(currentIndex);
				//setImages(currentA);

				Stage s1 = (Stage) moveField.getScene().getWindow();
				s1.close();
				return;
			}
		}
		Alert alert = new Alert(AlertType.WARNING ,  "Album does not exist.", ButtonType.OK);
		alert.showAndWait();

	}

	public void copyPhoto(int index, UserAlbum album) {
		try {			
			Stage Stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/copyPhoto.fxml"));
			AnchorPane pane = loader.load();
			Scene scene = new Scene(pane);

			Stage.setResizable(false);
			Stage.setScene(scene);
			Stage.show(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copyConfirm() {
		String albumName = moveField.getText();
		if(albumName.equals(currentA.getName())) {
			Alert alert = new Alert(AlertType.WARNING ,  "Cannot copy to this album.", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		for(int i =0; i<controllers.LoginController.currentUser.albums.size(); i++) {
			if(controllers.LoginController.currentUser.albums.get(i).getName().equals(albumName)) {
				controllers.LoginController.currentUser.albums.get(i).addPhoto(currentA.photos.get(currentIndex));
				controllers.LoginController.currentUser.albums.get(i).numOfPhotos++;

				Stage s1 = (Stage) moveField.getScene().getWindow();
				s1.close();
				return;
			}
		}
		Alert alert = new Alert(AlertType.WARNING ,  "Album does not exist.", ButtonType.OK);
		alert.showAndWait();

	}

	public void setImage(ImageView image, int index) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(currentA.photos.get(index).path);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		image.setImage(new Image(fis));
	}
}

