
public class Word implements Comparable<Word> {
	private Tile[] tiles;
	private ALIGNMENT alignment;
	private int startR;
	private int startC;
	private int points;
	
	// creating word through board (optimizer)
	public Word(Board board, ALIGNMENT alignment, int startR, int startC) {
		this.alignment = alignment;
		this.startR = startR;
		this.startC = startC;
		this.points = 0;
		int tempLength = 0;
		int rowInc = alignment == ALIGNMENT.HORIZONTAL ? 0 : 1;
		int colInc = alignment == ALIGNMENT.VERTICAL ? 0 : 1;
		int multiplier = 1;
		while(board.getCell(startR, startC) != null && board.getCell(startR, startC).getTile() != null) {
//			System.out.println("LETTER: "+board.getCell(startR, startC).getTile().getLetter());
//			System.out.println("POINT: "+board.getCell(startR, startC).getTile().getPoints());
			CELL_TYPE tempCellType = board.getCell(startR, startC).getType();
//			System.out.println("CELL TYPE: "+tempCellType);
			if(tempCellType == CELL_TYPE.DOUBLE_LETTER) {
				this.points += 2 * board.getCell(startR, startC).getTile().getPoints();
			} else if(tempCellType == CELL_TYPE.TRIPLE_LETTER) {
				this.points += 3 * board.getCell(startR, startC).getTile().getPoints();
			} else if(tempCellType == CELL_TYPE.DOUBLE_WORD) {
				this.points += board.getCell(startR, startC).getTile().getPoints();
				multiplier *= 2;
			} else if(tempCellType == CELL_TYPE.TRIPLE_WORD) {
				this.points += board.getCell(startR, startC).getTile().getPoints();
				multiplier *= 3;
			} else {
				this.points += board.getCell(startR, startC).getTile().getPoints();
			}
			tempLength++;
			startR += rowInc;
			startC += colInc;
		}
		this.points *= multiplier;
//		System.out.println("TOTALPOINTS1: "+this.points);
		startR = this.startR;
		startC = this.startC;
		tiles = new Tile[tempLength];
		for(int i = 0; i < tempLength; i++) {
			tiles[i] = board.getCell(startR, startC).getTile();
			startR += rowInc;
			startC += colInc;
		}
	}
	
	// creating word to manually
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
	
	public Tile[] getTiles() {
		return tiles;
	}
	
	public Tile getTile(int index) {
		return tiles[index];
	}

	public String getWord() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tiles.length; i++)
			sb.append(tiles[i].getLetter());
		return sb.toString();
	}

	public int getStartingRow() {
		return startR;
	}

	public int getStartingCol() {
		return startC;
	}

	public int getPoints() {
		return points;
	}

	public int getLength() {
		return tiles.length;
	}

	public ALIGNMENT getAlignment() {
		return alignment;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < tiles.length - 1; i++)
			sb.append(tiles[i] + ", ");
		sb.append(tiles[tiles.length - 1] + "]\n");
		sb.append("Alignment: " + alignment + "\n");
		sb.append("Starting Row: " + startR + "\n");
		sb.append("Starting Column: " + startC + "\n");
		sb.append("Points: " + points + "\n");
		return sb.toString();
	}

	// allows ArrayList of Words to be sorted
	public int compareTo(Word o) {
		return o.points - this.points;
	}

	// basis for removing from ArrayList<Word>
	public boolean equals(Object obj) {
		if (obj instanceof Word) {
			Word other = (Word) obj;
			return this.startR == other.startR && this.startC == other.startC && this.getWord().equals(other.getWord()) && this.alignment == other.alignment;
		}
		return false;
	}
}
