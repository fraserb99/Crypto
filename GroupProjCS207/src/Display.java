import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Display extends JFrame implements ActionListener{

	private ArrayList<JTextField> textArray = new ArrayList<JTextField>();
	private String sentence = "The quick brown fox jumped over the lazy dog";
	private String[] parsedSentence;
	textPane.addKeyListener(new KeyAdapter() {
		@Override
		public void keyTyped(KeyEvent arg0) {
		}
	});
	
	public Display() {
		setTitle("Test GUI");
		setLayout(new FlowLayout());
		
		parseSentence();
		
		for (int i=0;i<parsedSentence.length;i++) {
			for (int c=0;c<parsedSentence[i].length();c++) {
				JTextField temp = new JTextField(parsedSentence[i].substring(c, c+1));
				if (c==0 && i!=0) {
					JPanel separator = new JPanel();
					separator.setSize(new Dimension(5, 15));
					add(separator);
				}
				temp.setMargin(new Insets(5,5,5,5));
				textArray.add(temp);
				add(temp);
			}
			
		}
		
		
		
		setSize(1000, 1200);
		setVisible(true);
	}
	
	public void parseSentence() {
		 parsedSentence = sentence.split(" ");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Display gui = new Display();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		
	}

}
