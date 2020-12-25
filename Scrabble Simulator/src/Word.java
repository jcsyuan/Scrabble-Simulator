
public class Word {
	private Tile[] tiles;
	private ALIGNMENT alignment;
	private int startR;
	private int startC;
	private int points;

	public Word() {
		points = 0;
	}

	public Word(Tile[] tiles, ALIGNMENT alignment, int startR, int startC, int points) {
		this.tiles = new Tile[tiles.length];
		for (int i = 0; i < tiles.length; i++) {
			this.tiles[i] = tiles[i];
		}
		this.alignment = alignment;
		this.startR = startR;
		this.startC = startC;
		this.points = points;
	}

	public int getPoints() {
		return points;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i = 0; i < 6; i++)
			sb.append(tiles[i] + ", ");
		sb.append(tiles[6] + "]\n");
		sb.append("Alignment: " + alignment + "\n");
		sb.append("Starting Row: " + startR + "\n");
		sb.append("Starting Column: " + startC + "\n");
		sb.append("Points: " + points + "\n");
		return sb.toString();
	}
}
