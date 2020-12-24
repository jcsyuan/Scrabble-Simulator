import java.util.ArrayList;

public class Game {
	private Board board;
	private ArrayList<Word> playedWords;
	private ArrayList<Player> players;
	private ArrayList<Tile> remainingTiles;

	public Game(int numPlayers) {
		board = new Board();
		playedWords = new ArrayList<Word>();
		remainingTiles = new ArrayList<Tile>();
		helperCreateTiles();
		players = new ArrayList<Player>();
		for (int i = 0; i < numPlayers; i++) {
			players.add(new Player(remainingTiles));
		}
	}

	public Board getBoard() {
		return board;
	}

	public ArrayList<Word> getPlayedWords() {
		return playedWords;
	}

	public Player getPlayer(int i) {
		return players.get(i);
	}
	
	public ArrayList<Tile> getRemainingTiles() {
		return remainingTiles;
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
