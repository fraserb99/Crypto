import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Display extends JFrame implements ActionListener{

	private ArrayList<JTextField> textArray = new ArrayList<JTextField>();
	private String unencrypted = "THE QUICK BROWN FOX JUMPED OVER THE LAZY DOG";
	private Cipher cipher;
	private String sentence;
	private String[] parsedSentence;
	private String concatSentence;
	private ArrayList<String> guessArray = new ArrayList<String>();
	
	
	public Display() {
		setTitle("Cryptogram Game");
		setLayout(new FlowLayout());
		setFocusable(true);
		
		cipher = new Cipher();
		sentence = cipher.encrypt(unencrypted);
		concatSentence = sentence.replaceAll("\\s", "");
		parseSentence();
		
		for (int i=0;i<parsedSentence.length;i++) {
			for (int c=0;c<parsedSentence[i].length();c++) {
				JPanel panel = new JPanel(new BorderLayout());
				panel.setSize(new Dimension(40,80));
				JTextField temp = new JTextField(1);
				JLabel label = new JLabel();
				guessArray.add("");
				
				//label.setLabelFor(temp);
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
				
				
				if (c==0 && i!=0) {
					JPanel separator = new JPanel();
					separator.setSize(new Dimension(5, 25));
					add(separator);
				}
				temp.setMargin(new Insets(5,5,5,5));
				textArray.add(temp);
				panel.add(temp, BorderLayout.NORTH);
				panel.add(label, BorderLayout.CENTER);
				add(panel);
				setVisible(true);
			}	
		}
		
		JButton checkButton = new JButton("Check");
		checkButton.addActionListener(this);
		checkButton.setActionCommand("Check");
		add(checkButton);
		
		JButton saveBtn = new JButton("Save");
		saveBtn.addActionListener(this);
		saveBtn.setActionCommand("Save");
		add(saveBtn);
		
		setSize(800, 800);
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
		state.save();
	}

	public static void main(String[] args) {
		Display gui = new Display();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Check")) {
			JOptionPane.showMessageDialog(null, checkSolution());
		} else if (e.getActionCommand().equals("Save")) {
			saveGame();
		}
	}
	
		

}
