import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class Dictionary {
	private HashSet<String> validWords = new HashSet<>();
	
	public Dictionary() throws FileNotFoundException {
		helperScanFile();
	}
	
	public boolean checkWordValid(String word) {
		return validWords.contains(word);
	}
	
	public void printDictionary() {
		Iterator<String> it = validWords.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}
	
	private void helperScanFile() throws FileNotFoundException {
		File file = new File("small_dictionary.txt"); 
	    Scanner sc = new Scanner(file);
	    while (sc.hasNextLine()) {
	    	validWords.add(sc.nextLine());
	    }
	    sc.close();
	}
}
