

import javax.swing.JOptionPane;

public class MsgBox {
	
	//displays different kind of message boxes - RB
	
	 public static void info(String msg, String title)
	    {
	        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
	    }
	 public static void error(String msg, String title){
		 JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
	 }
	 
	 public static String input(String msg){
		 String input =  JOptionPane.showInputDialog(msg);
		 return input;
	 }

}
