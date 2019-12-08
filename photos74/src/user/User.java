package user;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * @author joshherrera
 * @author parthpatel
 */
public class User implements Serializable{


	public String username;
	public String password;
	public ArrayList<UserAlbum> albums;

	public static ArrayList<User> userList = new ArrayList<User>();

	
	/**
	 * Initiates User Object
	 * @param username
	 * @param password
	 */
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		albums = new ArrayList<UserAlbum>();
	}

	
	
	/**
	 * Adds new album to album list
	 * @param Album
	 */
	public void addAlbum(UserAlbum Album) {
		albums.add(Album);
	}
	
	
	
	/**
	 * Adds User to user list
	 * @param user
	 */
	public void addUser(User user) {
		userList.add(user);
	}
	
	


	/**
	 * toStirng method to print object
	 */
	public String toString() {
		return (this.username + "\t\t" + this.password);
	}

	
	/**
	 * gets user name
	 * @return Username
	 */
	public String getName() {
		return this.username;
	}

	/**
	 * gets user password
	 * @return Password
	 */
	public String getPass() {
		return this.password;
	}
}
