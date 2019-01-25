import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;

public class Display {

	JFrame frame;
	JPanel panel;
	String title;
	int width;
	int height;

	JTextPane cryptedText;

	public Display(String title, int width, int height) {
		frame = new JFrame();
		this.title = title;
		this.width = width;
		this.height = height;

		createDisplay();
	}

	private void createDisplay() {
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		panel = new JPanel();
		panel.setLayout(null);
		panel = new JPanel();
		panel.setBounds(0, (int) (frame.getHeight() * 0.01), (int) (frame.getWidth() * 0.6),
				(int) (frame.getHeight() * 0.8));
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		cryptedText = new JTextPane();
		cryptedText.setForeground(new Color(0, 0, 0));
		cryptedText.setFont(new Font("Tahoma", Font.PLAIN, 18));

		cryptedText.setEditable(false);
		panel.add(cryptedText, BorderLayout.CENTER);
		TitledBorder title;
		title = BorderFactory.createTitledBorder("Encrypted Text");
		panel.setBorder(title);

	}

}
