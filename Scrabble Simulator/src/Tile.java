
public class Tile {
	private char letter;
	private int points;
	private boolean played = false;
	
	public Tile(char letter, int points) {
		this.letter = letter;
		this.points = points;
	}
	
	public Tile(char letter, int points, boolean played) {
		this.letter = letter;
		this.points = points;
		this.played = played;
	}
	
	public char getLetter() {
		return letter;
	}
	
	public int getPoints() {
		return points;
	}
	
	public boolean isPlayed() {
		return played;
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
		return "Letter: " + this.getLetter() + " Points: " + this.getPoints();
	}
}
