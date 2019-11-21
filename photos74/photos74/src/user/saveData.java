package user;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class saveData implements Serializable{

	private static final long serialVersionUID = 3727994457398773678L;


	public static void writeUsersToFile() {

		try {
			File f = new File("DataBase.txt");
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(user.User.userList);
			oos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void getUsers() {
		try {
			
			File f = new File("DataBase.txt");
			if (f.length() != 0) {
				FileInputStream fis = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fis);
	
				try {
						//while(ois.available() > 0) {
					User.userList = (ArrayList<User>) ois.readObject();
	
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (EOFException e) {
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
