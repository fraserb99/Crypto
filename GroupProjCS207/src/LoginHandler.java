import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;


public class LoginHandler {

	private String user;
	private String pword;

	public LoginHandler(String user, String pword) {

		this.user = user;
		this.pword = pword;

	}

	public boolean authenticate() {

		File f = new File("Data/Users/" + user + "/account.txt");
		String pass = null;
		if (f.exists() && !f.isDirectory()) {
			try {
				FileReader fr = new FileReader("Data/Users/" + user + "/account.txt");
				BufferedReader br = new BufferedReader(fr);
				pass = br.readLine();
				br.close();
			} catch (Exception e) {
				System.out.println("IO error: " + e.getStackTrace());
			}

			if (pass.equals(pword)) {
				return true;
			} else { // password incorrect
				return false;
			}
		} else { // user does not exist therefore cannot authorise
			return false;
		}
	}

	public boolean createAccount() {
		File f = new File("Data/Users/" + user + "/account.txt");

		if (f.exists() && !f.isDirectory()) {
			return false;
		} else {

			try {
				File dir = new File("Data/Users/" + user);
				dir.mkdirs();
				File account = new File(dir, "account.txt");
				account.createNewFile();
				PrintWriter pr = new PrintWriter(
						new BufferedWriter(new FileWriter("Data/Users/" + user + "/account.txt", false)));
				pr.println(pword);
				pr.flush();
				pr.close();
			} catch (Exception e) {
				System.out.println("IO error: " + e.getStackTrace());
			}

			return true;
		}
	}
}
