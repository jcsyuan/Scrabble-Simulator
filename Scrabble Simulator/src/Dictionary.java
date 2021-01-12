import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

// Dictionary class: stores all valid words to be used in game
public class Dictionary {
	
	// stores words
	private HashSet<String> validWords = new HashSet<>();
	
	// initializer
	public Dictionary() throws FileNotFoundException {
		// scan words from text file to HashSet
		File file = new File("large_dictionary.txt"); 
	    Scanner sc = new Scanner(file);
	    while (sc.hasNextLine()) {
	    	validWords.add(sc.nextLine());
	    }
	    sc.close();
	}
	
	// checks if given word exists in dictionary
	public boolean checkWordValid(String word) {
		return validWords.contains(word);
	}
	
	// prints all words in dictionary
	public void printDictionary() {
		Iterator<String> it = validWords.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}
	
}
