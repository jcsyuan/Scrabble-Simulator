import java.util.ArrayList;
import java.util.Random;

// Player class: represents a single player in a simulation
public class Player {
	
	// stores current tiles as well as total accumulated points
	final static int MAX_TILES = 7;
	private Tile[] tiles;
	private int points;

	// initializer
	public Player(ArrayList<Tile> remainingTiles) {
		tiles = new Tile[MAX_TILES];
		// picks seven random tiles to start off the simulation
		for (int i = 0; i < MAX_TILES; i++) {
			Random rand = new Random();
			int randomIndex = rand.nextInt(remainingTiles.size());
			tiles[i] = remainingTiles.get(randomIndex);
			remainingTiles.remove(randomIndex);
		}
		points = 0;
	}

	// return current tiles of player
	public Tile[] getTiles() {
		return tiles;
	}

	// add points to player based on their play
	public void addPoints(int points) {
		this.points += points;
	}

	// play a tile and receive a new random one
	public boolean replaceTile(Tile tile, ArrayList<Tile> remainingTiles) {
		for (int i = 0; i < tiles.length; i++) {
			if (tile.equals(tiles[i])) {
				tiles[i] = null;
				Random rand = new Random();
				int randomIndex = rand.nextInt(remainingTiles.size());
				tiles[i] = remainingTiles.get(randomIndex);
				remainingTiles.remove(randomIndex);
				return true;
			}
		}
		return false;
	}

	// what to print for ArrayList
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(points + " [");
		for (int i = 0; i < tiles.length - 1; i++)
			sb.append(tiles[i] + ", ");
		sb.append(tiles[MAX_TILES - 1] + "] ");
		return sb.toString();
	}
}
