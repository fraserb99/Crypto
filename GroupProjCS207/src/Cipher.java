import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Cipher {
	
	char[] mappingCharacterSet = new char[]{ 'a','b','c','d','e','f','g','h','i','j','k',
			'l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2'
			,'3','4','5','6','7','8','9'};
	char[] alphabet = new char[]{ 'a','b','c','d','e','f','g','h','i','j','k',
			'l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	
	char[] mappedChars = new char[26];
	
	ArrayList<Integer> indexes = new ArrayList<Integer>();
	
	public Cipher(){
		fillIndexes();
		map();
		printMappedChars();
	}
	
	private void fillIndexes(){
		for(int i=0;i<36;i++){
			indexes.add(i);
		
		}
	}
	
	private void map(){
		int j  =0;
		for(int i=35;i>=0;i--){
			if(j<alphabet.length){
				int randomIndex = ThreadLocalRandom.current().nextInt(0, i + 1);
				int index = indexes.remove(randomIndex);
				mappedChars[j] = mappingCharacterSet[index];
				j++;
			}else {
				break;
			}
			
		}
		
	}
	
	private void printMappedChars(){
		for(int i=0;i<mappedChars.length;i++){
			System.out.print(mappedChars[i] + ",");
			
		}
		System.out.println("");
		for(int i=0;i<alphabet.length;i++){
			System.out.print(alphabet[i] + ",");

		}
	}
	
	public String encrypt(String text){
		String newString = "";
		for(int i=0;i<text.length();i++){
			char mappedChar = ' ';
			for(int j=0;j<alphabet.length;j++){
				if(alphabet[j] == text.charAt(i)) mappedChar = mappedChars[j];
			}
			newString = newString + mappedChar;
		}
		return newString;
	}
	
	public String decrypt(String text){
		String newString = "";
		for(int i=0;i<text.length();i++){
			char mappedChar = ' ';
			for(int j=0;j<alphabet.length;j++){
				if(mappedChars[j] == text.charAt(i)) mappedChar = alphabet[j];
			}
			newString = newString + mappedChar;
		}
		return newString;
	}
}
