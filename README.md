# Scrabble-Simulator

Using Java to simulate a classic game of Scrabble. The simulator generates the optimal move for a player given a distinct Scrabble board using a recurisve algorithm implemented in the Optimizer class.

Game
- Board
- ArrayList of Words (used for reference by Optimizer to find optimal play)
- ArrayList of Plays (previously played)
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
- Played variable (used by Optimizer to differentiate a temporary play vs. a permanent play)

Play
- ArrayList of Words that are created by the Play
- Extended Word to denote an extention of a permanent Word (delete original word from Game if played. reduce repetition)
- Total points of the play

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
- uses a recursive algorithm on each Word already played

Dictionary
- HashSet of valid words
