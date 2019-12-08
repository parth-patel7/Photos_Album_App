package user;

import java.util.ArrayList;
import java.io.Serializable;
import javafx.scene.image.Image;
/**
 * @author joshherrera
 * @author parthpatel
 */
public class UserAlbum implements Serializable{


	public String name;
	public int numOfPhotos;
	public ArrayList<UserPhoto> photos;


	/**
	 * Initiates Album Object
	 * @param name
	 */
	public UserAlbum(String name){
		this.name = name;
		this.numOfPhotos = 0;
		photos = new ArrayList<UserPhoto>();
		
		/*Image image1 = new Image("/a.png");
		UserPhoto up = new UserPhoto(image1, "test");
		photos.add(up);*/
	}

	
	/**
	 * Adds photo to photo list
	 * @param photo
	 */
	public void addPhoto(UserPhoto photo) {
		this.photos.add(photo);
	}


	
	/**
	 * toStirng method to print object
	 */
	public String toString() {
		return ("Album Name = "+this.name + " \n"
				+ "Latest Photo = " +    UserPhoto.getLatestPhoto(this) +    " \n" 
				+ "Oldest Photo = " +    UserPhoto.getOldestPhoto(this)  +  " \n" +
				"Number of Photos = " + this.numOfPhotos + "\n");
	}
	
	
	
	/**
	 * gets album name
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	
}
