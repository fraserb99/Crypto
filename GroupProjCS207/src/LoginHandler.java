import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;


public class LoginHandler {

	private String user;
	private User userObj;

	public LoginHandler(String user) {

		this.user = user;

	}
	
	public User getUser() {
		return userObj;
	}

	public boolean authenticate() {

		File f = new File("Data/Users/" + user + "/account.txt");
		if (f.exists() && !f.isDirectory()) {
			userObj = readUser("Data/Users/" + user + "/account.txt");
			if (userObj == null) {
				return false;
			}
			if (user.equals(userObj.getUsername())) {
				return true;
			} else { // password incorrect
				return false;
			}
		} else { // user does not exist therefore cannot authorise
			return false;
		}
	}

	public boolean createAccount() {
		File f = new File("Data/Users/" + user + "/account.txt");

		if (f.exists() && !f.isDirectory()) {
			return false;
		} else {

			try {
				File dir = new File("Data/Users/" + user);
				dir.mkdirs();
				File account = new File(dir, "account.txt");
				account.createNewFile();
				User newUser = new User(user);
				writeObject(newUser, "Data/Users/" + user + "/account.txt");
				userObj = newUser;
			} catch (Exception e) {
				System.out.println("IO error: " + e.getStackTrace());
			}

			return true;
		}
	}
	
	public void writeObject(Object obj, String filepath) {
		try {
	         FileOutputStream fOut = new FileOutputStream(filepath);
	         ObjectOutputStream objOut = new ObjectOutputStream(fOut);
	         objOut.writeObject(obj);
	         objOut.close();
	         fOut.close();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	}
	
	public User readUser(String filepath) {
		try {
			User usr;
			FileInputStream fIn = new FileInputStream(filepath);
			ObjectInputStream objIn = new ObjectInputStream(fIn);
			usr = (User) objIn.readObject();
			fIn.close();
			objIn.close();
			return usr;
		} catch (IOException e){
			JOptionPane.showMessageDialog(null, "The user data is corrupt");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		return null;
	}
}
