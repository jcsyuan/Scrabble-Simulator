
public class Board {
	final static int BOARD_LENGTH = 15;
	private Cell[][] cells;
	
	public Board() {
		cells = new Cell[BOARD_LENGTH][BOARD_LENGTH];
		helperGenerateBoard();
	}
	
	public boolean addTile(Tile tile, int row, int col) {
		if(row < 0 || col < 0 || row >= BOARD_LENGTH ||col >= BOARD_LENGTH)
			return false;
		Cell cell = getCell(row, col);
		if(cell.setTile(tile))
			return true;
		return false;
	}
	
	public void addWord(String word, ALIGNMENT alignment, int startR, int startC) {
		int rowInc = alignment == ALIGNMENT.HORIZONTAL ? 0 : 1;
		int colInc = alignment == ALIGNMENT.VERTICAL ? 0 : 1;
		for(int i = 0; i < word.length(); i++) {
			cells[startR][startC].setTile(new Tile(word.charAt(i), 1, true));
			startR += rowInc;
			startC += colInc;
		}
	}
	
	public Cell getCell(int row, int col) {
		if(0 <= row && row < BOARD_LENGTH && 0 <= col && col < BOARD_LENGTH)
			return cells[row][col];
		return null;
	}
	
	public void printBoard() {
		System.out.println("CURRENT BOARD");
		for(int r = 0; r < BOARD_LENGTH; r++) {
			for(int c = 0; c < BOARD_LENGTH; c++) {
				Cell tempCell = cells[r][c];
				if (tempCell.getTile() == null) {
					System.out.print("* ");
				} else {
					System.out.print(tempCell.getTile().getLetter() + " ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void printBoardTypes() {
		System.out.println("BOARD TYPES");
		for(int r = 0; r < BOARD_LENGTH; r++) {
			for(int c = 0; c < BOARD_LENGTH; c++) {
				Cell tempCell = cells[r][c];
				if (tempCell.getType() == CELL_TYPE.DOUBLE_LETTER) {
					System.out.print("d ");
				} else if(tempCell.getType() == CELL_TYPE.TRIPLE_LETTER){
					System.out.print("t ");
				} else if(tempCell.getType() == CELL_TYPE.DOUBLE_WORD){
					System.out.print("D ");
				} else if(tempCell.getType() == CELL_TYPE.TRIPLE_WORD){
					System.out.print("T ");
				} else {
					System.out.print("* ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private void helperGenerateBoard() {
		for (int r = 0; r < BOARD_LENGTH; r++) {
			for (int c = 0; c < BOARD_LENGTH; c++) {
				// double letter
				if ((r == 3 && c == 0) || (r == 3 && c == 14) || (r == 11 && c == 0) || (r == 11 && c == 14)
						|| (r == 0 && c == 3) || (r == 14 && c == 3) || (r == 0 && c == 11) || (r == 14 && c == 11))
					cells[r][c] = new Cell(null, CELL_TYPE.DOUBLE_LETTER);
				else if ((r == 2 && c == 6) || (r == 2 && c == 8) || (r == 12 && c == 6) || (r == 12 && c == 8)
						|| (r == 6 && c == 2) || (r == 6 && c == 6) || (r == 6 && c == 8) || (r == 6 && c == 12)
						|| (r == 8 && c == 2) || (r == 8 && c == 6) || (r == 8 && c == 8) || (r == 8 && c == 12))
					cells[r][c] = new Cell(null, CELL_TYPE.DOUBLE_LETTER);
				else if ((r == 3 && c == 7) || (r == 7 && c == 3) || (r == 11 && c == 7) || (r == 7 && c == 11))
					cells[r][c] = new Cell(null, CELL_TYPE.DOUBLE_LETTER);
				// triple letter
				else if ((r == 1 && c == 5) || (r == 1 && c == 9) || (r == 13 && c == 5) || (r == 13 && c == 9)
						|| (r == 5 && c == 1) || (r == 5 && c == 5) || (r == 5 && c == 9) || (r == 5 && c == 13)
						|| (r == 9 && c == 1) || (r == 9 && c == 5) || (r == 9 && c == 9) || (r == 9 && c == 13))
					cells[r][c] = new Cell(null, CELL_TYPE.TRIPLE_LETTER);
				// double word
				else if (r == c && ((r > 0 && r < 5) || (r > 9 && r < 14)))
					cells[r][c] = new Cell(null, CELL_TYPE.DOUBLE_WORD);
				else if (((r + c) == 14) && ((c > 0 && c < 5) || (c > 9 && c < 14)))
					cells[r][c] = new Cell(null, CELL_TYPE.DOUBLE_WORD);
				// triple word
				else if ((r == 0 && c == 0) || (r == 14 && c == 14) || (r == 0 && c == 14) || (r == 14 && c == 0)
						|| (r == 7 && c == 0) || (r == 0 && c == 7) || (r == 7 && c == 14) || (r == 14 && c == 7))
					cells[r][c] = new Cell(null, CELL_TYPE.TRIPLE_WORD);
				// normal
				else
					cells[r][c] = new Cell(null, CELL_TYPE.NORMAL);
			}
		}
	}
}
