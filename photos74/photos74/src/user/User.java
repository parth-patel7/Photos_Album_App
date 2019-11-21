package user;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{


	public String username;
	public String password;
	public ArrayList<UserAlbum> albums;

	// added
	public static ArrayList<User> userList = new ArrayList<User>();

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		albums = new ArrayList<UserAlbum>();
	}

	
	public void addAlbum(UserAlbum Album) {
		albums.add(Album);
	}
	
	
	public void addUser(User user) {
		userList.add(user);
	}
	
	


	public String toString() {
		return (this.username + "\t\t" + this.password);
	}

	public String getName() {
		return this.username;
	}

	public String getPass() {
		return this.password;
	}
}
