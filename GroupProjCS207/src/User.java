import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JOptionPane;

public class User implements Serializable, Comparable<User>{
	private String username;
	private Integer gamesComplete;
	private Integer gamesCorrect;
	private Integer numGuesses;
	private Game saveGame;
	private String filepath;
	private Integer guessCorrect;
	
	public User(String name) {
		username = name;
		gamesComplete = 0;
		gamesCorrect = 0;
		numGuesses = 0;
		guessCorrect = 0;
		saveGame = null;
		filepath = "Data/Users/" + username + "/account.txt";
	}
	
	public String getUsername(){
		return username;
	}
	
	public Integer getGamesComplete() {
		return gamesComplete;
	}
	
	public Integer getGamesCorrect() {
		return gamesCorrect;
	}
	
	public Integer getNumGuesses() {
		return numGuesses;
	}
	
	public Game getSaves(){
		return saveGame;
	}
	
	public void save() {
		try {
		    FileOutputStream fOut = new FileOutputStream(filepath);
		    ObjectOutputStream objOut = new ObjectOutputStream(fOut);
		    objOut.writeObject(this);
		    objOut.close();
		    fOut.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "There was a problem saving the user file");
		    e.printStackTrace();
		}
	}
	
	public void saveGame(Game game) {
		
		if (saveGame != null) {
			int answer = JOptionPane.showConfirmDialog(null, "A saved game already exists, would you like to overwrite it?", "Save Game", JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.NO_OPTION) {
				return;
			}
		}
		try {
			saveGame = (Game) game.clone();
		} catch (CloneNotSupportedException e) {
			JOptionPane.showMessageDialog(null, "There was a problem saving the game");
		}
		
		try {
		    FileOutputStream fOut = new FileOutputStream(filepath);
		    ObjectOutputStream objOut = new ObjectOutputStream(fOut);
		    objOut.writeObject(this);
		    objOut.close();
		    fOut.close();
		    JOptionPane.showMessageDialog(null, "Game Saved");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "There was a problem saving the game");
		    e.printStackTrace();
		}
		System.out.println("Saved");
	}
	
	public void userGuess(boolean guess) {
		if (guess == true) {
			numGuesses++;
			guessCorrect++;
		}
		else numGuesses++;
		save();
	}
	
	public void gameComplete(boolean correct) {
		if (correct == true) {
			gamesComplete++;
		}
		save();
	}
	
	public Double getGuessPercent() {
		if (numGuesses != 0) {
			return (double) (100 * guessCorrect/numGuesses);
		} else {
			return 0.0;
		}
	}
	
	public void printDetails() {
		System.out.println("Username: " + username);
		System.out.println("Games Completed: " + gamesComplete);
		System.out.println("Games Won: " + gamesCorrect);
		System.out.println("Number of guesses: " + numGuesses);
		System.out.println("Correct Guesses: " + guessCorrect);
	}

	@Override
	public int compareTo(User user) {
		int n;
		n = this.getGamesComplete().compareTo(user.getGamesComplete());
		if (n==0) {
			return this.getGuessPercent().compareTo(user.getGuessPercent());
		} else return n;
	}
	
}
