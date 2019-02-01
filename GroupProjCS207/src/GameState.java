import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {
	private Cipher cipher;
	private ArrayList<String> userGuess;
	//private <> user;
	
	public GameState(Cipher c, ArrayList<String> a) {
		cipher = c;
		userGuess = a;
	}
	
	public Cipher getCipher() {
		return cipher;
	}
	
	public ArrayList<String> getGuess(){
		return userGuess;
	}
	
	public void save(String filename) {
		try {
	         FileOutputStream fOut = new FileOutputStream(filename + ".ser");
	         ObjectOutputStream objOut = new ObjectOutputStream(fOut);
	         objOut.writeObject(this);
	         objOut.close();
	         fOut.close();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	}
}
