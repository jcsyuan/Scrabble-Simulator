
// Cell class: acts as a single placeholder on the board
public class Cell {
	
	// stores the tile on the cell as well as the special cell type
	private Tile tile = null;
	private CELL_TYPE type;
	
	// initializer
	public Cell(Tile tile, CELL_TYPE type) {
		this.tile = tile;
		this.type = type;
	}
	
	// places a tile on the cell
	public boolean setTile(Tile tile) {
		if(this.tile == null) {
			this.tile = tile;
			return true;
		}
		return false;
	}
	
	// clear tile from cell
	public void resetCell() {
		tile = null;
	}
	
	// return tile on cell
	public Tile getTile() {
		return tile;
	}
	
	// return type of cell
	public CELL_TYPE getType() {
		return type;
	}
}
