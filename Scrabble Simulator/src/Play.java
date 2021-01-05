import java.util.ArrayList;

public class Play implements Comparable<Play>{
	private ArrayList<Word> words;
	private Word extendedWord;
	private int points;
	
	
	public Play(Word firstWord) {
		this.words = new ArrayList<Word>();
		this.words.add(firstWord);
		for(int i = 0; i < words.size(); i++) {
			points += words.get(i).getPoints();
		}
	}
	
	public void addWord(Word word) {
		words.add(word);
	}
	
	public void addExtendedWord(Word word) {
		extendedWord = new Word(word.getTiles(), word.getAlignment(), word.getStartingRow(), word.getStartingCol(), word.getPoints());
		System.out.println("ADDED");
//		extendedWord = word;
		System.out.println(extendedWord.getWord());
		this.toString();
	}
	
	public Word getBaseWord() {
		return words.get(0);
	}
	
	public Word getExtendedWord() {
		return extendedWord;
	}
	
	public ArrayList<Word> getWords() {
		return words;
	}
	
	public int getPoints() {
		return points;
	}
	
	// allows plays to be sorted in descending order
	public int compareTo(Play o) {
		return o.points - this.points;
	}
	
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
