import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;



import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;

import com.sun.xml.internal.ws.util.StringUtils;

import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import java.awt.SystemColor;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField userTxt;
	private JPasswordField passwdTxt;
	public LoginFrame logframe;

	
	public LoginFrame() {
		
		logframe = this;
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		requestFocus();
		setSize(400, 400);
		
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new SoftBevelBorder(BevelBorder.RAISED, SystemColor.menuText, null, null, null));
		this.setContentPane(contentPane);
		
		contentPane.setLayout(null);
		
		

		

		userTxt = new JTextField();
		userTxt.setVisible(true);
		userTxt.setBounds(105, 100, 212, 26);
		contentPane.add(userTxt);
		userTxt.setColumns(10);

		passwdTxt = new JPasswordField();
		passwdTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent key) {
				if (key.getKeyCode() == key.VK_ENTER) { //if enter is pressed it will atempt to login
					
						if (userTxt.getText().length() > 0 && passwdTxt.getText().length() > 0) {
							authenticate(userTxt.getText(),
									passwdTxt.getText());
						} else {
							MsgBox.error("Invalid username or password!!", "Input Error");
						}

				}
			}
		});
		passwdTxt.setToolTipText("");
		passwdTxt.setBounds(105, 160, 212, 26);
		contentPane.add(passwdTxt);
		passwdTxt.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 200, 300, this.getWidth());
		contentPane.add(separator);

		

		

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(24, 100, 82, 20);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(24, 160, 82, 20);
		contentPane.add(lblPassword);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //runs when the login button is pressed - RB
				
						if (userTxt.getText().length() > 0 && passwdTxt.getText().length() > 0) {
							authenticate(userTxt.getText(),
									passwdTxt.getText());
						} else {
							MsgBox.error("Invalid username or password!!", "Input Error");
						}
					

			}
		});
		btnLogin.setBounds(37, 220, 157, 29);
		contentPane.add(btnLogin);

		JButton btnNewButton = new JButton("Create Account");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //runs when the Create Account button is pressed
				
					
						if (userTxt.getText().length() > 0 && passwdTxt.getText().length() > 0) {
							Pattern p = Pattern.compile("[^a-zA-Z0-9]"); //validates the username as alphanumeric
							if (!p.matcher(userTxt.getText()).find() && !p.matcher(passwdTxt.getText()).find()) {
								
									//create account
							} else {
								MsgBox.error("Invalid characters in username or password! Alphanumeric only.",
										"Input Error");
							}
						} else {
							MsgBox.error("Invalid username or password!", "Input Error");
						}
					

			}
		});
		btnNewButton.setBounds(223, 220, 156, 29);
		contentPane.add(btnNewButton);
		setVisible(true);
		contentPane.setVisible(true);
	}

	private void authenticate(String user, String password) {
		
	}

}
