
// Tile class: stores data for one tile
public class Tile {
	
	// stores letter, points, and whether tile is played or not
	private char letter;
	private int points;
	private boolean played = false;
	
	// initializer
	public Tile(char letter, int points) {
		this.letter = letter;
		this.points = points;
	}
	
	// initializer
	public Tile(char letter, int points, boolean played) {
		this.letter = letter;
		this.points = points;
		this.played = played;
	}
	
	// returns letter of tile
	public char getLetter() {
		return letter;
	}
	
	// returns points of tile
	public int getPoints() {
		return points;
	}
	
	// returns whether or not tile is played
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
		return "" + this.getLetter() + this.getPoints();
	}
}
