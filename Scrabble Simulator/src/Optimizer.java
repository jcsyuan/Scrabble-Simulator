import java.util.ArrayList;
import java.util.Collections;

public class Optimizer {
	private Dictionary dictionary;
	private Board board;
	private Player player;
	private ArrayList<Word> playedWords;
	private ArrayList<Play> possiblePlays;

	public Optimizer(Dictionary dictionary, Board board, Player player, ArrayList<Word> playedWords) {
		this.dictionary = dictionary;
		this.board = board;
		this.player = player;
		this.playedWords = playedWords;
		this.possiblePlays = new ArrayList<Play>();
	}
	
	public void printPossiblePlays() {
		for(int i = 0; i < possiblePlays.size(); i++) {
			System.out.println(possiblePlays.get(i));
		}
	}
	
	public ArrayList<Play> getPossiblePlays() {
		return possiblePlays;
	}

	// naive logic use max points, doesn't account for special cells, placement,
	// etc. implement stronger logic later
	public Play getOptimalPlay() {
		for (int i = 0; i < playedWords.size(); i++) {
			checkDirect(i);
			checkBorder(i);
		}
		Collections.sort(possiblePlays);
		return possiblePlays.size() > 0 ? possiblePlays.get(0) : null;
	}

	// check 7 up, 7 down, 7 left, 7 right
	private void checkDirect(int playedWordIndex) {
		Word currWord = playedWords.get(playedWordIndex);
		int startR = currWord.getStartingRow();
		int startC = currWord.getStartingCol();

		if (currWord.getAlignment() == ALIGNMENT.HORIZONTAL) {
			for (int letterIndex = 0; letterIndex < currWord.getLength(); letterIndex++) {
				if (letterIndex == 0) {
					helperCheckDirectModel(currWord, startR, startC, true, false, true, true);
				} else if (letterIndex == currWord.getLength() - 1) {
					helperCheckDirectModel(currWord, startR, startC, false, false, true, true);
				} else {
					helperCheckDirectModel(currWord, startR, startC, false, true, true, true);
				}
				startC++;
			}
		} else {
			for (int letterIndex = 0; letterIndex < currWord.getLength(); letterIndex++) {
				if (letterIndex == 0) {
					helperCheckDirectModel(currWord, startR, startC, true, true, true, false);
				} else if (letterIndex == currWord.getLength() - 1) {
					helperCheckDirectModel(currWord, startR, startC, true, true, false, false);
				} else {
					helperCheckDirectModel(currWord, startR, startC, true, true, true, false);
				}
				startR++;
			}
		}

		// horizontal
		// first: check left, up, down
		// normal: check up, down
		// last: check right, up, down

		// vertical
		// first: check left, right, up
		// normal: check left, right
		// last: check left, right, down

		// each tile added: if horizontally added check up and down, if vertically added
		// check left and right

	}

	// checkDirect helper to eliminate repetition
	private void helperCheckDirectModel(Word rootWord, int startR, int startC, boolean left, boolean right, boolean up, boolean down) {
		if (left)
			helperRecursiveCheck(rootWord, startR, startC, 0, -1, ALIGNMENT.HORIZONTAL, player.getTiles());
		if (right)
			helperRecursiveCheck(rootWord, startR, startC, 0, 1, ALIGNMENT.HORIZONTAL, player.getTiles());
		if (up)
			helperRecursiveCheck(rootWord, startR, startC, -1, 0, ALIGNMENT.VERTICAL, player.getTiles());
		if (down)
			helperRecursiveCheck(rootWord, startR, startC, 1, 0, ALIGNMENT.VERTICAL, player.getTiles());
	}

	// recursive method to check all possibilities in one direction
	private void helperRecursiveCheck(Word rootWord, int startR, int startC, int rowInc, int colInc, ALIGNMENT newWordAlignment,
			Tile[] tiles) {
		for (int i = 0; i < tiles.length; i++) {
			// check if tile used already
			if (tiles[i] != null) {
				// if blank tile check all 26 possibilities
				if (tiles[i].getLetter() == '0') {
					for (char tempChar = 'A'; tempChar <= 'Z'; tempChar++) {
						// check if can add tile to board, save in variable, remove from tiles
						Tile tempNewTile = new Tile(tempChar, 0);
						if (board.addTile(tempNewTile, startR + rowInc, startC + colInc)) {
							tiles[i] = null;
							// check if word is made -> if made check additionalWord then add to list, if
							// not valid don't add to list
							int wordR = startR;
							int wordC = startC;
							if (newWordAlignment == ALIGNMENT.HORIZONTAL) {
								wordC = helperGetStartingRowCol(startR, startC, newWordAlignment);
							} else {
								wordR = helperGetStartingRowCol(startR, startC, newWordAlignment);
							}
							String tempWordString = helperGetWord(wordR, wordC, newWordAlignment);
							
							Word tempWord = new Word(board, newWordAlignment, wordR, wordC);
							Play tempPlay = new Play(tempWord);
							ALIGNMENT additionalAlignment = newWordAlignment == ALIGNMENT.HORIZONTAL ? ALIGNMENT.VERTICAL
									: ALIGNMENT.HORIZONTAL;
							if (helperCheckAdditionalValidWord(tempPlay, startR + rowInc, startC + colInc,
									additionalAlignment)) {
								if (dictionary.checkWordValid(tempWordString)) {
									if(newWordAlignment == rootWord.getAlignment()) {
										tempPlay.addExtendedWord(rootWord);
										System.out.println(tempPlay);
									}
									possiblePlays.add(tempPlay);
								}
								helperRecursiveCheck(rootWord, startR + rowInc, startC + colInc, rowInc, colInc, newWordAlignment, tiles);
							}
							tiles[i] = new Tile('0', 0);
							board.getCell(startR + rowInc, startC + colInc).resetCell();
						}
					}
				} else {
					// check if can add tile to board, save in variable, remove from tiles
					if (board.addTile(new Tile(tiles[i].getLetter(), tiles[i].getPoints()), startR + rowInc, startC + colInc)) {
						tiles[i] = null;
						// check if word is made -> if made check additionalWord then add to list, if
						// not valid don't add to list
						int wordR = startR;
						int wordC = startC;
						if (newWordAlignment == ALIGNMENT.HORIZONTAL) {
							wordC = helperGetStartingRowCol(startR, startC, newWordAlignment);
						} else {
							wordR = helperGetStartingRowCol(startR, startC, newWordAlignment);
						}
						String tempWordString = helperGetWord(wordR, wordC, newWordAlignment);
						
						Word tempWord = new Word(board, newWordAlignment, wordR, wordC);
						Play tempPlay = new Play(tempWord);
						ALIGNMENT additionalAlignment = newWordAlignment == ALIGNMENT.HORIZONTAL ? ALIGNMENT.VERTICAL
								: ALIGNMENT.HORIZONTAL;
						if (helperCheckAdditionalValidWord(tempPlay, startR + rowInc, startC + colInc,
								additionalAlignment)) {
							if (dictionary.checkWordValid(tempWordString)) {
								if(newWordAlignment == rootWord.getAlignment()) {
									tempPlay.addExtendedWord(rootWord);
									System.out.println(tempPlay);
								}
								possiblePlays.add(tempPlay);
							}
							helperRecursiveCheck(rootWord, startR + rowInc, startC + colInc, rowInc, colInc, newWordAlignment, tiles);
						}
						tiles[i] = board.getCell(startR + rowInc, startC + colInc).getTile();
						board.getCell(startR + rowInc, startC + colInc).resetCell();
					}
				}
			}
		}
	}

	// check if added letter creates another perpendicular word, return false if not
	// allowed (then don't recurse),
	// return true if no word created or word created (also add to play if created)
	private boolean helperCheckAdditionalValidWord(Play currPlay, int row, int col, ALIGNMENT checkDirection) {
		// iterate to the farthest left/up connected tile to find start and check if word is valid
		if ((inBounds(row - 1) && inBounds(row + 1) && inBounds(col - 1) && inBounds(col + 1)) && ((checkDirection == ALIGNMENT.HORIZONTAL && board.getCell(row, col - 1).getTile() == null && board.getCell(row, col + 1).getTile() == null) ||
			(checkDirection == ALIGNMENT.VERTICAL && board.getCell(row - 1, col).getTile() == null && board.getCell(row + 1, col).getTile() == null)))
			return true;
		col = checkDirection == ALIGNMENT.HORIZONTAL ? helperGetStartingRowCol(row, col, checkDirection) : col;
		row = checkDirection == ALIGNMENT.VERTICAL ? helperGetStartingRowCol(row, col, checkDirection) : row;
		String tempWord = helperGetWord(row, col, checkDirection);
		if (dictionary.checkWordValid(tempWord)) {
			Word additionalWord = new Word(board, checkDirection, row, col);
			currPlay.addWord(additionalWord);
			return true;
		} else {
			return false;
		}
	}

	// returns starting position of word given a random position
	private int helperGetStartingRowCol(int row, int col, ALIGNMENT checkDirection) {
		int answer = 0;
		while (0 <= row && 0 <= col && board.getCell(row, col).getTile() != null) {
			if (checkDirection == ALIGNMENT.HORIZONTAL) {
				answer = col;
				col--;
			} else {
				answer = row;
				row--;
			}
		}
		return answer;
	}

	// returns String of word given a starting position
	private String helperGetWord(int startR, int startC, ALIGNMENT alignment) {
		StringBuilder sb = new StringBuilder();
		while (board.getCell(startR, startC) != null && board.getCell(startR, startC).getTile() != null) {
			sb.append(board.getCell(startR, startC).getTile().getLetter());
			if (alignment == ALIGNMENT.HORIZONTAL) {
				startC++;
			} else {
				startR++;
			}
		}
		return sb.toString();
	}
	
	// check if row/col are in bounds
	private boolean inBounds(int rowCol) {
		return rowCol >=0 && rowCol <Board.BOARD_LENGTH;
	}

	// check around borders of word
	private void checkBorder(int index) {
		// add to tempExtendedWords if added to top or bottom of vertical and left or
		// right of horizontal
	}
}

// FIRST MOVE METHOD
