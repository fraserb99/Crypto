import java.io.Serializable;

public class Action implements Serializable{
	private String charEntered;
	private String previousChar;
	private char encryptedChar;
	
	public Action(String charEntered, char encryptedChar, String previousChar) {
		this.charEntered = charEntered;
		this.encryptedChar = encryptedChar;
		this.previousChar = previousChar;
	}
	public char getEncryptedChar() {
		return encryptedChar;
	}
	public void setEncryptedChar(char encryptedChar) {
		this.encryptedChar = encryptedChar;
	}
	public String getPreviousChar() {
		return previousChar;
	}
	public void setPreviousChar(String previousChar) {
		this.previousChar = previousChar;
	}
	public String getCharEntered() {
		return charEntered;
	}
	public void setCharEntered(String charEntered) {
		this.charEntered = charEntered;
	}
	
	
}
