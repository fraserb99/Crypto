import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javafx.scene.layout.Border;

public class Display extends JFrame implements ActionListener{

	private ArrayList<JTextField> textArray = new ArrayList<JTextField>();
	private String sentence = "The quick brown fox jumped over the lazy dog";
	private String[] parsedSentence;
	private String concatSentence = "THEQUICKBROWNFOXJUMPEDOVERTHELAZYDOG";

	
	public Display() {
		setTitle("Test GUI");
		setLayout(new FlowLayout());
		setFocusable(true);
		
		parseSentence();
		
		for (int i=0;i<parsedSentence.length;i++) {
			for (int c=0;c<parsedSentence[i].length();c++) {
				JPanel panel = new JPanel(new BorderLayout());
				panel.setSize(new Dimension(40,80));
				JTextField temp = new JTextField(1);
				JLabel label = new JLabel();
				
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
			if (concatSentence.charAt(x) == c && x != i) {
				textArray.get(x).setText(textArray.get(i).getText());
			}
		}
	}
	
	public String concat(String[] s) {
		String sOut = "";
		for (int i=0;i<s.length;i++) {
			sOut.concat(s[i]);
		}
		return sOut;
	}


	public static void main(String[] args) {
		Display gui = new Display();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
	
		

}
