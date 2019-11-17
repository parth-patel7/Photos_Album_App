package controllers;


import java.io.File;
import java.io.IOException;

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

	@FXML private  Text userName;
	@FXML private  Button createAlbum;


	@FXML
	public void createAlbumPressed(ActionEvent event) {
		try {			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/createAlbum.fxml"));
			Parent root = (Parent) loader.load();
			Stage Stage = new Stage();
			Scene scene = new Scene(root);
			Stage.setResizable(false);
			Stage.setScene(scene);
			Stage.setTitle("Create new album");
			Stage.show(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private String findDir(File root, String name) {
		if (root.getName().equals(name)){
			return root.getAbsolutePath();
		}

		File[] files = root.listFiles();

		if(files != null){
			for (File f : files)  {
				if(f.isDirectory()){   
					String myResult = findDir(f, name);
					//this just means this branch of the
					//recursion reached the end of the
					//directory tree without results, but
					//we don't want to cut it short here,
					//we still need to check the other
					//directories, so continue the for loop
					if (myResult != null) {
						return myResult;
					}
				}
			}
		}

		//we don't actually need to change this. It just means we reached
		//the end of the directory tree (there are no more sub-directories
		//in this directory) and didn't find the result
		return null;
	}


	//	@FXML
	//	public void renameAlbumPressed(ActionEvent event) {
	//		try {			
	//			Stage primaryStage = new Stage();
	//			FXMLLoader loader = new FXMLLoader();
	//			loader.setLocation(getClass().getResource("/view/createAlbum.fxml"));
	//			AnchorPane pane = loader.load();
	//			Scene scene = new Scene(pane);
	//			primaryStage.setResizable(false);
	//			primaryStage.setScene(scene);
	//			primaryStage.setTitle("Create new album");
	//			primaryStage.show(); 
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//		}
	//
	//		if(renameCancel.isPressed()) {
	//			return;
	//		} else if(renameSave.isPressed()){
	//			// Create new Album
	//		}
	//	}




	@FXML
	public void userLogout(ActionEvent event) {
		Parent newRoot;
		try {
			newRoot = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			Stage s = (Stage) ((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene (newRoot);
			s.setScene(scene);
			s.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
