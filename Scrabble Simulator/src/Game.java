import java.util.ArrayList;

// Game class: represents a single simulation game
public class Game {
	
	// stores game board, total previous played words, total previous plays,
	// tiles still remaining, total players in the game
	private Board board;
	private ArrayList<Word> playedWords;
	private ArrayList<Play> previousPlays;
	private ArrayList<Tile> remainingTiles;
	private ArrayList<Player> players;

	// create game based on given number of players
	public Game(int numPlayers) {
		board = new Board();
		playedWords = new ArrayList<Word>();
		previousPlays = new ArrayList<Play>();
		remainingTiles = new ArrayList<Tile>();
		helperCreateTiles();
		players = new ArrayList<Player>();
		for (int i = 0; i < numPlayers; i++) {
			players.add(new Player(remainingTiles));
		}
	}
	
	// change the variables based on the play that is being played
	public boolean playPlay(Player player, Play play) {
		// check if player and play is valid
		if(player == null || play == null) {
			return false;
		}
		Word word = play.getBaseWord();
		int startR = word.getStartingRow();
		int startC = word.getStartingCol();
		int rowInc = word.getAlignment() == ALIGNMENT.HORIZONTAL ? 0 : 1;
		int colInc = word.getAlignment() == ALIGNMENT.VERTICAL ? 0 : 1;
		// place tiles on board
		for(int i = 0; i < word.getLength(); i++) {
			// place tile on board
			board.getCell(startR, startC).setTile(word.getTile(i));
			// remove tile from player and replace with new tile
			if(!word.getTile(i).isPlayed()) {
				player.replaceTile(word.getTile(i), remainingTiles);
			}
			startR += rowInc;
			startC += colInc;
		}
		// add points to player
		player.addPoints(play.getPoints());
		// removed extended word if possible
		if(play.getExtendedWord() != null)
			playedWords.remove(play.getExtendedWord());
		// add play to previous plays
		previousPlays.add(play);
		// add words from play to previous played words
		ArrayList<Word> words = play.getWords();
		for(int i = 0; i < words.size(); i++) {
			playedWords.add(words.get(i));
		}
		return true;
	}

	// return board of game
	public Board getBoard() {
		return board;
	}

	// return previous played words
	public ArrayList<Word> getPlayedWords() {
		return playedWords;
	}
	
	// return previous played plays
	public ArrayList<Play> getPreviousPlays() {
		return previousPlays;
	}
	
	// return the remaining tiles in the game
	public ArrayList<Tile> getRemainingTiles() {
		return remainingTiles;
	}

	// return player at index i
	public Player getPlayer(int i) {
		return players.get(i);
	}
	
	// print scores of all players and their tiles
	public void printScore() {
		for(int player = 0; player < players.size(); player++) {
			System.out.println("PLAYER " + (player + 1) + ": " + players.get(player));
		}
		System.out.println();
	}

	// helper method to create tiles in the game
	private void helperCreateTiles() {
		for (char letter = 'A'; letter <= 'Z'; letter++) {
			if (letter == 'Z' || letter == 'Q' || letter == 'X' || letter == 'J' || letter == 'K')
				helperDuplicateTile(letter, 1);
			else if (letter == 'Y' || letter == 'W' || letter == 'V' || letter == 'H' || letter == 'F' || letter == 'P'
					|| letter == 'M' || letter == 'C' || letter == 'B')
				helperDuplicateTile(letter, 2);
			else if (letter == 'G')
				helperDuplicateTile(letter, 3);
			else if (letter == 'D' || letter == 'U' || letter == 'S' || letter == 'L')
				helperDuplicateTile(letter, 4);
			else if (letter == 'T' || letter == 'R' || letter == 'N')
				helperDuplicateTile(letter, 6);
			else if (letter == 'O')
				helperDuplicateTile(letter, 8);
			else if (letter == 'I' || letter == 'A')
				helperDuplicateTile(letter, 9);
			else if (letter == 'E')
				helperDuplicateTile(letter, 12);
		}
		helperDuplicateTile('0', 2);
	}

	// helper method to duplicate same tiles
	private void helperDuplicateTile(char letter, int num) {
		for(int i = 0; i < num; i++) {
			remainingTiles.add(new Tile(letter, helperLetterToPoints(letter)));
		}
	}

	// helper method to simplify letter to corresponding point value
	private int helperLetterToPoints(char letter) {
		if (letter == 'A' || letter == 'E' || letter == 'I' || letter == 'L' || letter == 'N' || letter == 'O' || letter == 'R'
				|| letter == 'S' || letter == 'T' || letter == 'U')
			return 1;
		else if (letter == 'D' || letter == 'G')
			return 2;
		else if (letter == 'B' || letter == 'C' || letter == 'M' || letter == 'P')
			return 3;
		else if (letter == 'F' || letter == 'H' || letter == 'V' || letter == 'W' || letter == 'Y')
			return 4;
		else if (letter == 'K')
			return 5;
		else if (letter == 'J' || letter == 'X')
			return 8;
		else if (letter == 'Q' || letter == 'Z')
			return 10;
		else
			return 0;
	}
}
