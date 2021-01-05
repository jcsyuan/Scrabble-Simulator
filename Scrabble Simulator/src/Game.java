import java.util.ArrayList;

public class Game {
	private Board board;
	private ArrayList<Word> playedWords;
	private ArrayList<Play> previousPlays;
	private ArrayList<Tile> remainingTiles;
	private ArrayList<Player> players;

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
	
	public boolean playPlay(Player player, Play play) {
		if(player == null || play == null) {
			return false;
		}
		Word word = play.getBaseWord();
		int startR = word.getStartingRow();
		int startC = word.getStartingCol();
		int rowInc = word.getAlignment() == ALIGNMENT.HORIZONTAL ? 0 : 1;
		int colInc = word.getAlignment() == ALIGNMENT.VERTICAL ? 0 : 1;
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
		player.addPoints(play.getPoints());
		if(play.getExtendedWord() != null)
			playedWords.remove(play.getExtendedWord());
		previousPlays.add(play);
		ArrayList<Word> words = play.getWords();
		for(int i = 0; i < words.size(); i++) {
			playedWords.add(words.get(i));
		}
		return true;
	}

	public Board getBoard() {
		return board;
	}

	public ArrayList<Word> getPlayedWords() {
		return playedWords;
	}
	
	public ArrayList<Play> getPreviousPlays() {
		return previousPlays;
	}
	
	public ArrayList<Tile> getRemainingTiles() {
		return remainingTiles;
	}

	public Player getPlayer(int i) {
		return players.get(i);
	}

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

	private void helperDuplicateTile(char letter, int num) {
		for(int i = 0; i < num; i++) {
			remainingTiles.add(new Tile(letter, helperLetterToPoints(letter)));
		}
	}

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
