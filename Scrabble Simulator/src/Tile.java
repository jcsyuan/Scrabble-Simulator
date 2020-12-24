
public class Tile {
	private char letter;
	private int points;
	
	public Tile(char letter, int points) {
		this.letter = letter;
		this.points = points;
	}
	
	public char getLetter() {
		return letter;
	}
	
	public int getPoints() {
		return points;
	}
	
	// basis for removing from ArrayList<Tile>
	public boolean equals(Object obj) {
		if(obj instanceof Tile) {
			Tile other = (Tile)obj;
			return this.letter == other.letter && this.points == other.points;
		}
		return false;
	}
	
	// what to print for ArrayList
	public String toString() {
		return this.getLetter() + " " + this.getPoints();
	}
}
