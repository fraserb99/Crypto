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
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class Display extends JFrame implements ActionListener{
	
	private Game game;
	private JPanel textPanel;
	private JPanel scorePanel;
	private DefaultTableModel tableModel;
	private JTable scoreboard;
	private ArrayList<JTextField> textArray = new ArrayList<JTextField>();
	private ArrayList<JPanel> panelArray = new ArrayList<JPanel>();
	private Cipher cipher;
	private String sentence;
	private String[] parsedSentence;
	private String concatSentence;
	private ArrayList<User> userList = new ArrayList<User>();
	private User user;
	
	public Display( User user) {
		
		setTitle("Cryptogram Game");
		setLayout(new BorderLayout());
		setFocusable(true);
		game = new Game(this, user);
		this.user = user;
		setSentence();
		
		textPanel = new JPanel(new FlowLayout());
		add(textPanel);
		
		generateTextFields();
		
		
		
		scorePanel = new JPanel(new BorderLayout());
		add(scorePanel, BorderLayout.EAST);
		scorePanel.setSize(600, 600);
		generateScoreboard();
		
		JPanel btnPanel = new JPanel(new FlowLayout());
		add(btnPanel, BorderLayout.SOUTH);
		
		JButton undoBtn = new JButton("Undo");
		undoBtn.addActionListener(this);
		undoBtn.setActionCommand("Undo");
		btnPanel.add(undoBtn);
		
		JButton hintButton = new JButton("Print Details");
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
		
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	
		        save();
		        System.exit(0);
		    }
		});
		
		setSize(1200, 600);
		setVisible(true);
	}
	
	public void save(){
		this.user.save();
	}
	public void setSentence() {
		sentence = game.getSentence();
		parsedSentence = game.getParsedSentence();
		concatSentence = game.getConcatSentence();
	}

	public void updateText(KeyEvent ke) {
		int i = textArray.indexOf(ke.getSource());
		char c = concatSentence.charAt(i);
		String guess = textArray.get(i).getText().toUpperCase();
		guess = Character.toString(ke.getKeyChar()).toUpperCase();
		String previous = game.getPreviousChar(c);
		if (!guess.matches("[A-Z]")) {
			guess = "";
		} else {
			Action action = new Action(guess, c, previous);
			game.newGuess(action);
		}
		textArray.get(i).setText("");
		game.setGuess(c, guess);
		
		
		for (int x=0;x<concatSentence.length();x++) {
			if (concatSentence.charAt(x) == c) {
				textArray.get(x).setText(guess);
			}
		}
	}
	
	public void updateText() {
		char encryptedChar;
		HashMap<Character, String> guessMap = game.getGuessMap();
		for (int i=0;i<concatSentence.length();i++) {
			encryptedChar = concatSentence.charAt(i);
			textArray.get(i).setText(guessMap.get(encryptedChar));
			System.out.println(i + " " + encryptedChar + " " + guessMap.get(encryptedChar));
		}
	}
	
	public void loadUsers() {
		userList.clear();
		File[] folders = new File("Data/Users/").listFiles();
		if (folders != null) {
			for(File folder : folders) {
				if(folder.isDirectory()) {
					try {
						User usr;
						FileInputStream fIn = new FileInputStream(folder.getPath() + "/account.txt");
						ObjectInputStream objIn = new ObjectInputStream(fIn);
						usr = (User) objIn.readObject();
						userList.add(usr);
						fIn.close();
						objIn.close();
					} catch (IOException e){
						System.out.println("The user data is corrupt: " + folder.getPath());
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						System.out.println("The user data is corrupt: " + folder.getPath());
						e.printStackTrace();
					} catch (ClassCastException e) {
						System.out.println("The user data is corrupt: " + folder.getPath());
						e.printStackTrace();
					}
				} else {
					System.out.println("Unexpected file: " + folder.getPath());
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Unable to load users");
		}
	}
	
	public void generateScoreboard(){
		loadUsers();
		if (!userList.isEmpty()) {
			String[] colNames = {"Pos.", "Username", "Finished", "Guess %"};
			tableModel = new DefaultTableModel(colNames,0) {
				@Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
			};
			scoreboard = new JTable(tableModel);
			scoreboard.setBackground(textPanel.getBackground());
			scoreboard.setShowGrid(false);
			scoreboard.setSize(600, 600);
			Collections.sort(userList, Collections.reverseOrder());
			User userObj;
			for (int i=0;i<10 && i<userList.size();i++) {
				userObj = userList.get(i);
				String[] data = {
						((Integer) (i+1)).toString(),
						userObj.getUsername(),
						userObj.getGamesComplete().toString(),
						userObj.getGuessPercent().toString() + "%"};
				tableModel.addRow(data);
			}
			scorePanel.add(scoreboard);
			scorePanel.add(scoreboard.getTableHeader(), BorderLayout.NORTH);
		} else {
			scorePanel.add(new JTextField("No users found"));
		}
		for(User usr : userList) {
			System.out.println(usr.getGamesComplete());
		}
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
						public void keyPressed(KeyEvent e) {
							//invoked later to stop text being entered twice
							final KeyEvent eLocal = e;
							SwingUtilities.invokeLater(new Runnable() {
						        public void run() {
									updateText(eLocal);
						        }
						    });
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
	
	public void loadGame(Game newGame) {
		game = newGame;
		setSentence();
		generateTextFields();
		for (int i=0;i<panelArray.size();i++) {
			textPanel.remove(panelArray.get(i));
		}
		
		panelArray.clear();
		textArray.clear();
		
		generateTextFields();
		
		HashMap<Character, String> guessMap = game.getGuessMap();
		char encryptedChar;
		for (int i=0;i<concatSentence.length();i++) {
			encryptedChar = concatSentence.charAt(i);
			textArray.get(i).setText(guessMap.get(encryptedChar));
			System.out.println(i + " " + encryptedChar + " " + guessMap.get(encryptedChar));
		}
		System.out.println("Game loaded");
	}
	
	public void undo() {
		try {
			Action action = game.undoPop();
			for (int x=0;x<concatSentence.length();x++) {
				if (concatSentence.charAt(x) == action.getEncryptedChar()) {
					textArray.get(x).setText(action.getPreviousChar());
				}
			}
			game.setGuess(action.getEncryptedChar(), action.getPreviousChar());
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Nothing left to undo!");
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Check")) {
			JOptionPane.showMessageDialog(null, game.checkSolution());
		} else if (e.getActionCommand().equals("Save")) {
			game.saveGame();
		} else if (e.getActionCommand().equals("Load")) {
			game.loadGame(this);
		} else if (e.getActionCommand().equals("Hint")) {
			game.printDetails();
		} else if (e.getActionCommand().equals("Undo")) {
			undo();
		}
	}
	
	

}
