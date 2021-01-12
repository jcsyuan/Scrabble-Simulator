import java.util.ArrayList;

// Play class: represents a possible play for a player
public class Play implements Comparable<Play>{
	
	// stores all words created during a possible turn, points of the play, and words to delete
	private ArrayList<Word> words;
	private Word extendedWord;
	private int points;
	
	// initializer
	public Play(Word firstWord) {
		this.words = new ArrayList<Word>();
		this.words.add(firstWord);
		for(int i = 0; i < words.size(); i++) {
			points += words.get(i).getPoints();
		}
	}
	
	// add a word to the play
	public void addWord(Word word) {
		words.add(word);
	}
	
	// if play extends word, add original word
	public void addExtendedWord(Word word) {
		extendedWord = new Word(word.getTiles(), word.getAlignment(), word.getStartingRow(), word.getStartingCol(), word.getPoints());
		this.toString();
	}
	
	// return first word created from play
	public Word getBaseWord() {
		return words.get(0);
	}
	
	// return original extended word if possible 
	public Word getExtendedWord() {
		return extendedWord;
	}
	
	// return all words created from play
	public ArrayList<Word> getWords() {
		return words;
	}
	
	// return total points of play
	public int getPoints() {
		return points;
	}
	
	// allows plays to be sorted in descending order
	public int compareTo(Play o) {
		return o.points - this.points;
	}
	
	// what variables to print
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < words.size() - 1; i++)
			sb.append(words.get(i).getWord() + ", ");
		sb.append(words.get(words.size()-1).getWord() + "] ");
		sb.append("Delete word: " + extendedWord);
		sb.append("\tPoints: " + points + "\tRow: " + words.get(0).getStartingRow() + "\tCol: " + words.get(0).getStartingCol() + "\n");
		return sb.toString();
	}
}
