import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;

public class Game implements Serializable, Cloneable{
	
	private String saveName;
	public static boolean running;
	private User user;
	private String unencrypted;
	private Cipher cipher;
	private String sentence;
	private String[] parsedSentence;
	private String concatSentence;
	private HashMap<Character, String> guessMap = new HashMap<Character, String>();
	private Stack<Action> undoStack = new Stack<Action>();
	private boolean correct;
	private Display display;
	
	public Game(Display display, User user){
		this.user = user;
		this.display = display;
		cipher = new Cipher();
		correct = false;
//		int answer = JOptionPane.showConfirmDialog(null, "Would you like to load an existing game?", "Load Game?", JOptionPane.YES_NO_OPTION);
//		if (answer == JOptionPane.YES_OPTION) {
//				loadGame(display);
//				loadGame(display);
//				return;
//		} else {
			unencrypted = generatePhrase();
			sentence = cipher.encrypt(unencrypted);
			cipher.setOriginalText(unencrypted);
			parsedSentence = sentence.split(" ");
			concatSentence = String.join("", parsedSentence);
			user.printDetails();
		
	}
	
	public String generatePhrase() {
		String phrase = "";
		FileInputStream fIn;
		Random rnd = new Random();
		Integer index = rnd.nextInt(4);
		try {
			fIn = new FileInputStream("Data/phrases.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fIn));
			for (int i=0;i<index;i++) {
				br.readLine();
			}
			phrase = br.readLine();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The phrases file can't be found");
		}
		
		return phrase;
	}
	
	public String getSaveName() {
		return saveName;
	}
	
	public User getUser() {
		return user;
	}
	
	public String getSentence() {
		return sentence;
	}
	
	public String[] getParsedSentence() {
		return parsedSentence;
	}
	
	public String getConcatSentence() {
		return concatSentence;
	}
	
	public HashMap<Character, String> getGuessMap() {
		return guessMap;
	}
	
	public void setGuessMap(HashMap<Character, String> guessMap) {
		this.guessMap = guessMap;
	}
	public void setUser(User username) {
		user = username;
	}
	
	public void setUndoStack(Stack<Action> undoStack) {
		this.undoStack = undoStack;
	}
	
	public void loadGame(Display display) {
		Game game = user.getSaves();
		
		if (game == null) {
			JOptionPane.showMessageDialog(null, "No save games found");
		}else display.loadGame(game);
	}
	
	public void newGuess(Action a) {
		if (undoStack.isEmpty()) {
			undoStack.push(a);
			user.userGuess(cipher.encrypt(a.getCharEntered()).equals(Character.toString(a.getEncryptedChar()))); 
			return;
		}
		Action previous = undoStack.peek();
		//checks that the new action is different from the last one on the stack
		if (previous.getEncryptedChar() != a.getEncryptedChar() || !previous.getCharEntered().equals(a.getCharEntered())) {
			undoStack.push(a);
			user.userGuess(cipher.encrypt(a.getCharEntered()).equals(Character.toString(a.getEncryptedChar())));
		}
	}
	
	public Action undoPop() {
		if (undoStack.isEmpty()) {
			return null;
		} else return undoStack.pop();
	}
	
	public String getPreviousChar(char c) {
		return guessMap.get(c);
	}
	
	public void setGuess(char key, String value) {
		guessMap.put(key, value);
	}
	
	public void saveGame() {
		user.saveGame(this);
	}
	
	public boolean checkSolution() {
		for (int i=0;i<concatSentence.length();i++) {
			if (!concatSentence.substring(i, i+1).equals(cipher.encrypt(guessMap.get(concatSentence.charAt(i))))) {
				user.gameComplete(false);
				user.save();
				return false;
			}
		}
		user.gameComplete(true);
		return true;
	}
	
	public void hint(){
		String usrGuess;
		Character encrypted;
		for (Character letter = 'A'; letter <= 'Z'; letter++) {
			encrypted = cipher.encrypt(letter.toString()).charAt(0);
			usrGuess = guessMap.get(encrypted);
		    if (!usrGuess.equals(letter.toString())) {
		    	guessMap.put(encrypted, letter.toString());
		    	display.updateText();
		    	return;
		    }
		}
		
	}
	
	public void printDetails() {
		user.printDetails();
	}
	
	@Override
	public Game clone() throws CloneNotSupportedException { 
		Game temp = (Game)super.clone();
		temp.setGuessMap((HashMap<Character, String>)this.guessMap.clone());
		temp.setUndoStack((Stack<Action>) this.undoStack.clone());
		return temp;
	}
	
}
