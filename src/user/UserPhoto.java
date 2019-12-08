package user;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.Calendar;

import javafx.scene.image.Image;
/**
 * @author joshherrera
 * @author parthpatel
 */
public class UserPhoto implements Serializable{
	public String name;
	public String path;
	public String caption;
	public ArrayList <UserTags> tagList;
	public Calendar cal;
	public Image image;


	
	/**
	 * Initiates Photo Object
	 * @param name
	 * @param path
	 */
	public UserPhoto(String name, String path){
		this.name = name;
		this.path = path;
		//image = new Image("file:\\"+path, true);
		caption = "";
		tagList = new ArrayList <UserTags>();
		cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);
	}

	
	
	/**
	 * Return latest photo time
	 * @param album
	 * @return
	 */
	public static String getLatestPhoto(UserAlbum album) {

		if(album.photos.size() == 0) {
			return "";
		}

		if(album.photos.size() == 1) {
			return album.photos.get(0).cal.getTime().toString();
		}

		Calendar cal = album.photos.get(0).cal;
		for(int i = 1; i < album.photos.size(); i++) {
			if(cal.compareTo(album.photos.get(i).cal) < 0) {
				cal = album.photos.get(i).cal;
			}
		}		
		return cal.getTime().toString();
	}



	/**
	 * Return latest oldest time
	 * @param album
	 * @return
	 */
	public static String getOldestPhoto(UserAlbum album) {

		if(album.photos.size() == 0) {
			return "";
		}

		if(album.photos.size() == 1) {
			return album.photos.get(0).cal.getTime().toString();
		}

		Calendar temp = album.photos.get(0).cal;
		for(int i = 1; i < album.photos.size(); i++) {
			if(temp.compareTo(album.photos.get(i).cal) > 0) {
				temp = album.photos.get(i).cal;
			}
		}		
		return temp.getTime().toString();
	}

	
	
	/**
	 * Adds caption to photo
	 * @param s
	 */
	public void addCaption(String s) {
		caption = s;
	}

	
	
	/**
	 * returns photo path (URL)
	 * @return
	 */
	public String getPath() {
		return this.path;
	}
}
