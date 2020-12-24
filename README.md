# Scrabble-Simulator

Simulates a classic game of Scrabble. The simulator generates the optimal move for a player given a distinct Scrabble board.

Game
- Board
- ArrayList of Words
- ArrayList of Players
- ArrayList of Tiles left

Board
- 2D Array of Cells

Cell
- Tile occupying the cell (null if empty)
- Special variable denoting a 'Premium Square'

Tile
- Letter (assign letter regardless if blank tile)
- Point (0 if blank tile)

Word (play)
- Array of Tiles
- Alignment variable
- Starting row
- Starting column
- Total points of play

Player
- Array of Tiles
- Total points

Optimizer
- returns optimal Word (play)

Dictionary
- HashSet of valid words
- HashMap of letter to point values
