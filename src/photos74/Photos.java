package photos74;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import user.*;
/**
 * @author joshherrera
 * @author parthpatel
 */
public class Photos extends Application{


	/**
	 * Starts the main stage (Login page)
	 */
	@Override
	public void start(Stage primaryStage) {
		saveData.getUsers();
		String row;
		try {

			Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			Scene scene = new Scene (root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Login");
			primaryStage.show(); 
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}


		/**
		 * After the user has closed the GUI the data base is being updated here 
		 */
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				try {
					// store all changes
					saveData.writeUsersToFile();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * main method 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}


}
