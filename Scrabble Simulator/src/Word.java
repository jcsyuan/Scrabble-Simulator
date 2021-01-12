
// Word class: represents a word to be possibly played in game
public class Word implements Comparable<Word> {
	
	// stores tiles that make up the word, direction, starting row and column and
	// corresponding points
	private Tile[] tiles;
	private ALIGNMENT alignment;
	private int startR;
	private int startC;
	private int points;
	
	// initializer: creating word through board (optimizer)
	public Word(Board board, ALIGNMENT alignment, int startR, int startC) {
		this.alignment = alignment;
		this.startR = startR;
		this.startC = startC;
		this.points = 0;
		int tempLength = 0;
		int rowInc = alignment == ALIGNMENT.HORIZONTAL ? 0 : 1;
		int colInc = alignment == ALIGNMENT.VERTICAL ? 0 : 1;
		int multiplier = 1;
		// calculate points of the tiles on the board (taking into account special cells)
		while(board.getCell(startR, startC) != null && board.getCell(startR, startC).getTile() != null) {
			CELL_TYPE tempCellType = board.getCell(startR, startC).getType();
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
		// set starting coordinates
		startR = this.startR;
		startC = this.startC;
		// get the tiles on the Board to store in Word
		tiles = new Tile[tempLength];
		for(int i = 0; i < tempLength; i++) {
			tiles[i] = board.getCell(startR, startC).getTile();
			startR += rowInc;
			startC += colInc;
		}
	}
	
	// initializer: creating word manually given an array of Tiles
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
	
	// return tiles used to make up word
	public Tile[] getTiles() {
		return tiles;
	}
	
	// return a certain tile given an index
	public Tile getTile(int index) {
		return tiles[index];
	}
	
	// return string format of the word
	public String getWord() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tiles.length; i++)
			sb.append(tiles[i].getLetter());
		return sb.toString();
	}

	// return starting row of word
	public int getStartingRow() {
		return startR;
	}

	// return starting column of word
	public int getStartingCol() {
		return startC;
	}

	// return the points of word
	public int getPoints() {
		return points;
	}

	// return the length of word
	public int getLength() {
		return tiles.length;
	}

	// return direction of word
	public ALIGNMENT getAlignment() {
		return alignment;
	}

	// what variables of the word to print
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < tiles.length - 1; i++)
			sb.append(tiles[i] + ", ");
		sb.append(tiles[tiles.length - 1] + "]\t");
		sb.append("Alignment: " + alignment + "\t");
		sb.append("Starting Row: " + startR + "\t");
		sb.append("Starting Col: " + startC + "\t");
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
