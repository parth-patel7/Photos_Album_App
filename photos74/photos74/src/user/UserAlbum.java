package user;

import java.util.ArrayList;
import java.io.Serializable;
import javafx.scene.image.Image;

public class UserAlbum implements Serializable{


	public String name;
	public int numOfPhotos;
	public ArrayList<UserPhoto> photos;


	public UserAlbum(String name){
		this.name = name;
		this.numOfPhotos = 0;
		photos = new ArrayList<UserPhoto>();
		
		/*Image image1 = new Image("/a.png");
		UserPhoto up = new UserPhoto(image1, "test");
		photos.add(up);*/
	}

	public void addPhoto(UserPhoto photo) {
		this.photos.add(photo);
	}


	public String toString() {
		return ("Album Name = "+this.name + " \n"
				+ "Latest Photo = " +    UserPhoto.getLatestPhoto(this) +    " \n" 
				+ "Oldest Photo = " +    UserPhoto.getOldestPhoto(this)  +  " \n" +
				"Number of Photos = " + this.numOfPhotos + "\n");
	}
	
	public String getName() {
		return this.name;
	}

	
}
