import java.io.FileNotFoundException;
import java.util.ArrayList;

// Runner class: runs and tests optimizer
public class Runner {

	public static void main(String[] args) throws FileNotFoundException {
		testThree();
	}
	
	// Test one: create dictionary and create game
	public static void testOne() throws FileNotFoundException {
		
		// create dictionary
		System.out.println("TEST 1.0 - DICTIONARY CREATION");
		Dictionary dictionary = new Dictionary();
		System.out.println(dictionary.checkWordValid("DOG"));
		dictionary.printDictionary();
		System.out.println();
		
		// create game
		System.out.println("TEST 1.1 - GAME CREATION");
		Game game = new Game(1);
		game.getBoard().printBoard();
		System.out.println(game.getRemainingTiles());
		Tile[] tiles = game.getPlayer(0).getTiles();
		printArray(tiles);
		
		// place word on board
		System.out.println("TEST 1.2 - WORD TOSTRING");
		Word testWord = new Word(tiles, ALIGNMENT.HORIZONTAL, 1, 3, 20);
		System.out.println(testWord);
	}
	
	// Test two: create optimizer and find most optimal play twice
	public static void testTwo() throws FileNotFoundException {
		
		// create optimizer
		System.out.println("TEST 2.0 - OPTIMIZER CREATION");
		Dictionary dictionary = new Dictionary();
		Game game = new Game(1);
		game.getBoard().addWord("SUPER", ALIGNMENT.HORIZONTAL, 4, 4);
		Word jo = new Word(game.getBoard(), ALIGNMENT.HORIZONTAL, 4, 4);
		game.getPlayedWords().add(jo);
		
		// first play
		System.out.println("FIRST PLAY:");
		Optimizer optimizer = new Optimizer(dictionary, game.getBoard(), game.getPlayer(0), game.getPlayedWords());
		Play bestPlay = optimizer.getOptimalPlay();
		ArrayList<Play> possiblePlays = optimizer.getPossiblePlays();
		System.out.println(possiblePlays.get(0));
		System.out.println(possiblePlays.get(1));
		System.out.println(possiblePlays.get(2));
		System.out.println(bestPlay);
		game.playPlay(game.getPlayer(0), bestPlay);
		game.getBoard().printBoard();
		
		// second play
		System.out.println("SECOND PLAY:");
		optimizer = new Optimizer(dictionary, game.getBoard(), game.getPlayer(0), game.getPlayedWords());
		bestPlay = optimizer.getOptimalPlay();
		possiblePlays = optimizer.getPossiblePlays();
		System.out.println(possiblePlays.get(0));
		System.out.println(possiblePlays.get(1));
		System.out.println(possiblePlays.get(2));
		game.playPlay(game.getPlayer(0), bestPlay);
		game.getBoard().printBoard();
		
	}
	
	// Test three: test game with two players
	public static void testThree() throws FileNotFoundException {
		
		// create dictionary and game
		System.out.println("TEST 3.0 - SIMPLE SIMULATION");
		Dictionary dictionary = new Dictionary();
		Game game = new Game(2);
		game.getBoard().addWord("START", ALIGNMENT.HORIZONTAL, 7, 5);
		Word start = new Word(game.getBoard(), ALIGNMENT.HORIZONTAL, 7, 5);
		game.getPlayedWords().add(start);
		
		// play game until all tiles used
		boolean playerOne = true;
		boolean played = true;
		while(played && game.getRemainingTiles().size() > Player.MAX_TILES) {
			Optimizer optimizer = playerOne ? 
					new Optimizer(dictionary, game.getBoard(), game.getPlayer(0), game.getPlayedWords()) :
					new Optimizer(dictionary, game.getBoard(), game.getPlayer(1), game.getPlayedWords());
			Play bestPlay = optimizer.getOptimalPlay();
			played = playerOne ? game.playPlay(game.getPlayer(0), bestPlay) : game.playPlay(game.getPlayer(1), bestPlay);
			game.getBoard().printBoard();
			game.printScore();
			playerOne = !playerOne;
		}
		
	}
	
	// print any object array
	private static void printArray(Object[] arr) {
		System.out.print("[");
		for(int i = 0; i < arr.length - 1; i++)
			System.out.print(arr[i] + ", ");
		System.out.println(arr[arr.length - 1] + "]");
		System.out.println();
	}
}
