
public class Cell {
	private Tile tile = null;
	private CELL_TYPE type;
	
	public Cell(Tile tile, CELL_TYPE type) {
		this.tile = tile;
		this.type = type;
	}
	
	public boolean setTile(Tile tile) {
		if(tile == null) {
			this.tile = tile;
			return true;
		}
		return false;
	}
	
	public Tile getTile() {
		return tile;
	}
	
	public CELL_TYPE getType() {
		return type;
	}
}
