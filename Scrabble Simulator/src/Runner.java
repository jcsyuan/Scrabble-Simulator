import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Runner {

	public static void main(String[] args) throws FileNotFoundException {
		testTwo();
	}
	
	public static void testOne() throws FileNotFoundException {
		
		System.out.println("TEST 1.0 - DICTIONARY CREATION");
		Dictionary dictionary = new Dictionary();
		System.out.println(dictionary.checkWordValid("DOG"));
		dictionary.printDictionary();
		System.out.println();
		
		System.out.println("TEST 1.1 - GAME CREATION");
		Game game = new Game(1);
		game.getBoard().printBoard();
		System.out.println(game.getRemainingTiles());
		Tile[] tiles = game.getPlayer(0).getTiles();
		printArray(tiles);
		
		System.out.println("TEST 1.2 - WORD TOSTRING");
		Word testWord = new Word(tiles, ALIGNMENT.HORIZONTAL, 1, 3, 20);
		System.out.println(testWord);
	}
	
	public static void testTwo() throws FileNotFoundException {
		
		System.out.println("TEST 2.0 - OPTIMIZER CREATION");
		Dictionary dictionary = new Dictionary();
		Game game = new Game(1);
		game.getBoard().addWord("JONATHAN", ALIGNMENT.HORIZONTAL, 4, 4);
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
//		System.out.println(game.getPlayedWords());
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
//		System.out.println(game.getPlayedWords());
		game.getBoard().printBoard();
		

	}
	
	private static void printArray(Object[] arr) {
		System.out.print("[");
		for(int i = 0; i < arr.length - 1; i++)
			System.out.print(arr[i] + ", ");
		System.out.println(arr[arr.length - 1] + "]");
		System.out.println();
	}
}
