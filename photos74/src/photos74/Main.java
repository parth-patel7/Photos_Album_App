package photos74;


import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application{


	@Override
	public void start(Stage primaryStage) {

		try {

			Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			Scene scene = new Scene (root);
			primaryStage.setScene(scene);
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
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}


}
