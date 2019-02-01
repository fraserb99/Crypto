import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Display extends JFrame implements ActionListener{
	
	private Game game;
	private JPanel textPanel;
	private JPanel scorePanel;
	private ArrayList<JTextField> textArray = new ArrayList<JTextField>();
	private ArrayList<JPanel> panelArray = new ArrayList<JPanel>();
	private String unencrypted = "YOU'RE A QUICK BROWN FOX";
	private Cipher cipher;
	private String sentence;
	private String[] parsedSentence;
	private String concatSentence;
	private ArrayList<String> guessArray = new ArrayList<String>();
	
	
	public Display(Game gameClass) {
		setTitle("Cryptogram Game");
		setLayout(new BorderLayout());
		setFocusable(true);
		game = gameClass;
		
		cipher = new Cipher();
		sentence = cipher.encrypt(unencrypted);
		cipher.setOriginalText(unencrypted);
		concatSentence = sentence.replaceAll("[^A-Za-z0-9]", "");
		parseSentence();
		
		textPanel = new JPanel(new FlowLayout());
		add(textPanel);
		
		generateTextFields();
		
		scorePanel = new JPanel(new GridLayout());
		
		for (int i=0;i<textArray.size();i++) {
			guessArray.add("");
		}
		
		JPanel btnPanel = new JPanel(new FlowLayout());
		add(btnPanel, BorderLayout.SOUTH);
		
		JButton hintButton = new JButton("Hint");
		hintButton.addActionListener(this);
		hintButton.setActionCommand("Hint");
		btnPanel.add(hintButton);
		
		JButton checkButton = new JButton("Check");
		checkButton.addActionListener(this);
		checkButton.setActionCommand("Check");
		btnPanel.add(checkButton);
		
		JButton saveBtn = new JButton("Save");
		saveBtn.addActionListener(this);
		saveBtn.setActionCommand("Save");
		btnPanel.add(saveBtn);
		
		JButton loadBtn = new JButton("Load");
		loadBtn.addActionListener(this);
		loadBtn.setActionCommand("Load");
		btnPanel.add(loadBtn);
		
		setSize(600, 400);
		setVisible(true);
	}
	
	public void parseSentence() {
		 parsedSentence = sentence.split(" ");
	}

	
	public void updateText(KeyEvent ke) {
		int i = textArray.indexOf(ke.getSource());
		char c = concatSentence.charAt(i);
		for (int x=0;x<concatSentence.length();x++) {
			if (concatSentence.charAt(x) == c) {
				textArray.get(x).setText(textArray.get(i).getText().toUpperCase());
				guessArray.set(x, textArray.get(i).getText().toUpperCase());
			}
		}
	}
	
	public boolean checkSolution() {
		String userGuess = String.join("", guessArray);
		if (cipher.encrypt(userGuess).equals(concatSentence)) {
			return true;
		} else return false;
	}
	
	public void saveGame() {
		GameState state = new GameState(cipher, guessArray);
		String filename = JOptionPane.showInputDialog("Please enter a name for your save");
		state.save("Users/" + filename);
	}
	
	public void loadGame() {
		String filepath;
		File folder = new File("Users/");
		String[] fileList = folder.list();
		String filename = (String) JOptionPane.showInputDialog(null, "Choose a save", "Load Game", JOptionPane.QUESTION_MESSAGE, null, fileList, fileList[0]);
		if (filename == null) return;
		filepath = "Users/" + filename;
		GameState saveGame = readObject(filepath);
		cipher = saveGame.getCipher();
		guessArray = saveGame.getGuess();
		unencrypted = cipher.getOriginalText();
		sentence = cipher.encrypt(unencrypted);
		concatSentence = sentence.replaceAll("[^A-Za-z0-9]", "");
		parseSentence();
		
		for (int i=0;i<panelArray.size();i++) {
			textPanel.remove(panelArray.get(i));
		}
		
		panelArray.clear();
		textArray.clear();
		
		generateTextFields();
		
		for (int i=0;i<guessArray.size();i++) {
			textArray.get(i).setText(guessArray.get(i));
		}
	}
	
	public GameState readObject(String filepath) {
		try {
			GameState saveGame;
			FileInputStream fIn = new FileInputStream(filepath);
			ObjectInputStream objIn = new ObjectInputStream(fIn);
			saveGame = (GameState) objIn.readObject();
			fIn.close();
			objIn.close();
			return saveGame;
		} catch (IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void generateTextFields() {
		
		for (int i=0;i<parsedSentence.length;i++) {
			for (int c=0;c<parsedSentence[i].length();c++) {
				JPanel panel = new JPanel(new BorderLayout());
				panelArray.add(panel);
				panel.setSize(new Dimension(40,80));
				JLabel label = new JLabel();
				
				if (Character.toString(parsedSentence[i].charAt(c)).matches("^[a-zA-Z0-9]*$")) {
					JTextField temp = new JTextField(1);
					label.setText(parsedSentence[i].substring(c, c+1));
					temp.setVisible(true);
					label.setVisible(true);
					temp.addKeyListener(new KeyAdapter() {
						@Override
						public void keyTyped(KeyEvent e) {
							updateText(e);
						}
					});
					temp.addFocusListener(new FocusListener() {
	
				        @Override
				        public void focusGained(FocusEvent e) {
				        	int i = textArray.indexOf(e.getSource());
				    		char c = concatSentence.charAt(i);
				    		for (int x=0;x<concatSentence.length();x++) {
				    			if (concatSentence.charAt(x) == c) {
				    				textArray.get(x).setBackground(Color.yellow);
				    			}
				    		}
				        }
	
				        @Override
				        public void focusLost(FocusEvent e) {
				        	int i = textArray.indexOf(e.getSource());
				    		char c = concatSentence.charAt(i);
				    		for (int x=0;x<concatSentence.length();x++) {
				    			if (concatSentence.charAt(x) == c) {
				    				textArray.get(x).setBackground(Color.white);
				    			}
				    		}
				        }
				    });
					temp.setMargin(new Insets(5,5,5,5));
					textArray.add(temp);
					panel.add(temp, BorderLayout.NORTH);
					panel.add(label, BorderLayout.CENTER);
				} else {
					label.setText(parsedSentence[i].substring(c, c+1));
					panel.add(label, BorderLayout.NORTH);
				}
				
				if (c==0 && i!=0) {
					JPanel separator = new JPanel();
					separator.setSize(new Dimension(5, 25));
					textPanel.add(separator);
					panelArray.add(separator);
				}
				
				textPanel.add(panel);
				setVisible(true);
				repaint();
			}	
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Check")) {
			JOptionPane.showMessageDialog(null, checkSolution());
		} else if (e.getActionCommand().equals("Save")) {
			saveGame();
		} else if (e.getActionCommand().equals("Load")) {
			loadGame();
		} else if (e.getActionCommand().equals("Hint")) {
			
		}
	}
	
		

}
