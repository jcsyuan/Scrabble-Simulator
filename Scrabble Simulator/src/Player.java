import java.util.ArrayList;
import java.util.Random;

public class Player {
	final static int MAX_TILES = 7;
	private Tile[] tiles;
	private int points;
	
	public Player(ArrayList<Tile> remainingTiles) {
		tiles = new Tile[MAX_TILES];
		for(int i = 0; i < MAX_TILES; i++) {
			Random rand = new Random();
			int randomIndex = rand.nextInt(remainingTiles.size());
			tiles[i] = remainingTiles.get(randomIndex);
			remainingTiles.remove(randomIndex);
		}
		points = 0;
	}
	
	public Tile[] getTiles() {
		return tiles;
	}
}
