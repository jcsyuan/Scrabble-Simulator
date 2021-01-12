import java.util.ArrayList;
import java.util.Collections;

// Optimizer class: finds the optimal play given a board
public class Optimizer {
	
	// stores dictionary, board, words, and plays to use when determining the best play
	private Dictionary dictionary;
	private Board board;
	private Player player;
	private ArrayList<Word> playedWords;
	private ArrayList<Play> possiblePlays;

	// initializer
	public Optimizer(Dictionary dictionary, Board board, Player player, ArrayList<Word> playedWords) {
		this.dictionary = dictionary;
		this.board = board;
		this.player = player;
		this.playedWords = playedWords;
		this.possiblePlays = new ArrayList<Play>();
	}
	
	// return all plays that optimizer finds
	public ArrayList<Play> getPossiblePlays() {
		return possiblePlays;
	}
	
	// print plays that optimizer finds
	public void printPossiblePlays() {
		for(int i = 0; i < possiblePlays.size(); i++) {
			System.out.println(possiblePlays.get(i));
		}
	}

	// naive logic use max points, doesn't account for special cells, placement,
	// etc. implement stronger logic later
	public Play getOptimalPlay() {
		for (int i = 0; i < playedWords.size(); i++) {
			checkDirect(i);
			// checkBorder(i);
		}
		Collections.sort(possiblePlays);
		return possiblePlays.size() > 0 ? possiblePlays.get(0) : null;
	}
	
	// check 7 up, 7 down, 7 left, 7 right
	private void checkDirect(int playedWordIndex) {
		Word currWord = playedWords.get(playedWordIndex);
		int startR = currWord.getStartingRow();
		int startC = currWord.getStartingCol();
		// horizontal word
		if (currWord.getAlignment() == ALIGNMENT.HORIZONTAL) {
			for (int letterIndex = 0; letterIndex < currWord.getLength(); letterIndex++) {
				// first: check left, up, down
				if (letterIndex == 0) {
					helperCheckDirectModel(currWord, startR, startC, true, false, true, true);
				// normal: check up, down
				} else if (letterIndex == currWord.getLength() - 1) {
					helperCheckDirectModel(currWord, startR, startC, false, false, true, true);
				// last: check right, up, down
				} else {
					helperCheckDirectModel(currWord, startR, startC, false, true, true, true);
				}
				startC++;
			}
		// vertical word
		} else {
			for (int letterIndex = 0; letterIndex < currWord.getLength(); letterIndex++) {
				// first: check left, right, up
				if (letterIndex == 0) {
					helperCheckDirectModel(currWord, startR, startC, true, true, true, false);
				// normal: check left, right
				} else if (letterIndex == currWord.getLength() - 1) {
					helperCheckDirectModel(currWord, startR, startC, true, true, false, false);
				// last: check left, right, down
				} else {
					helperCheckDirectModel(currWord, startR, startC, true, true, true, false);
				}
				startR++;
			}
		}
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
						Tile tempNewTile = new Tile(tempChar, 0);
						// check if tile can be added to board
						if (board.addTile(tempNewTile, startR + rowInc, startC + colInc)) {
							// remove tile from player's hand
							tiles[i] = null;
							// get related variables
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
							// check if added tile creates conflict with other tiles
							if (helperCheckAdditionalValidWord(tempPlay, startR + rowInc, startC + colInc,
									additionalAlignment)) {
								// check if word is valid
								if (dictionary.checkWordValid(tempWordString)) {
									if(newWordAlignment == rootWord.getAlignment()) {
										tempPlay.addExtendedWord(rootWord);
									}
									possiblePlays.add(tempPlay);
								}
								// regardless of valid word, recurse onto next tile
								helperRecursiveCheck(rootWord, startR + rowInc, startC + colInc, rowInc, colInc, newWordAlignment, tiles);
							}
							// add tile back to player's hand
							tiles[i] = new Tile('0', 0);
							// remove tile from board
							board.getCell(startR + rowInc, startC + colInc).resetCell();
						}
					}
				} else {
					// check if tile can be added to board
					if (board.addTile(new Tile(tiles[i].getLetter(), tiles[i].getPoints()), startR + rowInc, startC + colInc)) {
						// remove tile from player's hand
						tiles[i] = null;
						// get related variables
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
						// check if added tile creates conflict with other tiles
						if (helperCheckAdditionalValidWord(tempPlay, startR + rowInc, startC + colInc,
								additionalAlignment)) {
							// check if word is valid
							if (dictionary.checkWordValid(tempWordString)) {
								if(newWordAlignment == rootWord.getAlignment()) {
									tempPlay.addExtendedWord(rootWord);
								}
								possiblePlays.add(tempPlay);
							}
							// regardless of valid word, recurse onto next tile
							helperRecursiveCheck(rootWord, startR + rowInc, startC + colInc, rowInc, colInc, newWordAlignment, tiles);
						}
						// add tile back to player's hand
						tiles[i] = board.getCell(startR + rowInc, startC + colInc).getTile();
						// remove tile from board
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
	
	// check if row/column are in bounds
	private boolean inBounds(int rowCol) {
		return rowCol >=0 && rowCol <Board.BOARD_LENGTH;
	}

}
