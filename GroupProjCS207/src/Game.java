
public class Game implements Runnable {
	
	private Thread thread;
	public static boolean running;
	Display display;
	Cipher cipher;
	String TITLE = "CRYPTOGRAM";
	int WIDTH = 800;
	int HEIGHT = 600;
	
	public Game(){
		start();
	}
	
	private void init(){
		display = new Display(TITLE, WIDTH, HEIGHT);
	}
	
	@Override
	public void run() {
		
		init();
		
		cipher = new Cipher();
		String encryptedMsg = cipher.encrypt("Fraser is so berted");
		System.out.println(encryptedMsg);
		System.out.println(cipher.decrypt(encryptedMsg));
		
		/*while(running){
			
		}
		*/
	}
	
	public synchronized void start(){
		if(running)return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if(!running)return;
		try {
			running = false;
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
