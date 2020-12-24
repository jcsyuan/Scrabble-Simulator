import java.io.FileNotFoundException;

public class Runner {

	public static void main(String[] args) throws FileNotFoundException {
		testOne();
	}
	
	public static void testOne() throws FileNotFoundException {
		
		System.out.println("test 1.0 - dictionary creation");
		Dictionary dictionary = new Dictionary();
		System.out.println(dictionary.checkWordValid("DOG"));
		dictionary.printDictionary();
		System.out.println();
		
		System.out.println("test 1.1 - game creation");
		Game game = new Game(1);
		game.getBoard().printBoard();
		System.out.println(game.getRemainingTiles());
		Tile[] tiles = game.getPlayer(0).getTiles();
		System.out.print("[");
		for(int i = 0; i < 6; i++)
			System.out.print(tiles[i] + ", ");
		System.out.println(tiles[6] + "]");
		System.out.println();
		
		System.out.println("test 1.2 - optimizer creation");
		Optimizer optimizer = new Optimizer(dictionary, game.getBoard(), game.getPlayedWords(), game.getPlayer(0));
		
		
	}
}
