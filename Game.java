import java.util.*;

/*****************************************************************
 * The base game, that the GUI always checks to see what will be
 * displayed by using MVC.
 * 
 * @author Brody Berson
 * @version 1.0
 *****************************************************************/
public class Game {
	/** 2D Array of Cell type to set up the game */
	private Cell[][] gameBoard;

	/** Integer for rows throughout */
	private int rows;

	/** Integer for columns throughout */
	private int columns;

	/** The total number of bombs in the game */
	private int totalBombs;

	/** Integer of the selected row */
	private int sRow;

	/** Integer of the selected column */
	private int sCol;

	/** Keeps track of wins */
	private int wins;

	/** Keeps track of losses */
	private int losses;

	/*****************************************************************
	 * Method that sets up a new game according to its parameters by
	 * the size of the board and number of bombs for the GUI to display.
	 * 
	 * @param boardSize - integer to determine the size of the board
	 * @param totalBombs - number of bombs
	 *****************************************************************/
	public Game(int boardSize, int totalBombs) {
		rows = boardSize;
		columns = boardSize;
		this.totalBombs = totalBombs;
		gameBoard = new Cell[rows][columns];
		reset();
	}

	/*****************************************************************
	 * Method that checks to see if the given cell is within the
	 * game board and is valid to be selected.
	 * 
	 * @param row - integer to determine correct row
	 * @param col - integer to determine correct column
	 *****************************************************************/
	public boolean isValidCell(int row, int col) {
		if (row>=0 && col >=0 && row <rows && col <columns)
			return true;
		return false;
	}

	/*****************************************************************
	 * Method that counts all the adjacent bombs around the given cell
	 * and sets the mineCount in that cell.
	 * 
	 * @param row - integer to determine correct row
	 * @param col - integer to determine correct column
	 *****************************************************************/
	public void countBombs(int row, int col) {
		int mineCount = 0;
		//counting neighbors
		if (isValidCell(row-1,col))
			if(gameBoard[row-1][col].isMine())
				mineCount++;
		if (isValidCell(row-1,col-1))
			if(gameBoard[row-1][col-1].isMine())
				mineCount++;
		if (isValidCell(row,col-1))
			if(gameBoard[row][col-1].isMine())
				mineCount++;
		if (isValidCell(row+1,col-1))
			if(gameBoard[row+1][col-1].isMine())
				mineCount++;
		if (isValidCell(row+1,col))
			if(gameBoard[row+1][col].isMine())
				mineCount++;
		if (isValidCell(row+1,col+1))
			if(gameBoard[row+1][col+1].isMine())
				mineCount++;
		if (isValidCell(row,col+1))
			if(gameBoard[row][col+1].isMine())
				mineCount++;
		if (isValidCell(row-1,col+1))
			if(gameBoard[row-1][col+1].isMine())
				mineCount++;
		if (isValidCell(row,col))
			if(gameBoard[row][col].isMine())
				mineCount = 0;
		gameBoard[row][col].setMineCount(mineCount);
	}

	/*****************************************************************
	 * Method that will select the given cell and expose it as well as
	 * count the surrounding bombs and check to open up all other ones
	 * around the given cell.
	 * 
	 * @param row - integer to determine correct row
	 * @param col - integer to determine correct column
	 *****************************************************************/
	public void select (int row, int col) {
		sRow = row;
		sCol = col;
		if (!gameBoard[row][col].isFlagged())
			if (!gameBoard[row][col].isExposed()) {
				gameBoard[row][col].setExposed(true);
				countBombs(row, col);
				if (!gameBoard[row][col].isMine()) {
					if (gameBoard[row][col].getMineCount() == 0){
						//checks all surrounding cells to open
						if (isValidCell(row-1,col))
							if(!gameBoard[row-1][col].isMine())
								select(row-1,col);
						if (isValidCell(row-1,col-1))
							if(!gameBoard[row-1][col-1].isMine())
								select(row-1,col-1);
						if (isValidCell(row,col-1))
							if(!gameBoard[row][col-1].isMine())
								select(row,col-1);
						if (isValidCell(row+1,col-1))
							if(!gameBoard[row+1][col-1].isMine())
								select(row+1,col-1);
						if (isValidCell(row+1,col))
							if(!gameBoard[row+1][col].isMine())
								select(row+1,col);
						if (isValidCell(row+1,col+1))
							if(!gameBoard[row+1][col+1].isMine())
								select(row+1,col+1);
						if (isValidCell(row,col+1))
							if(!gameBoard[row][col+1].isMine())
								select(row,col+1);
						if (isValidCell(row-1,col+1))
							if(!gameBoard[row-1][col+1].isMine())
								select(row-1,col+1);
					}
				}
			}
	}

	/*****************************************************************
	 * Returns the eNumerated type of GameStatus if the game is over,
	 * (win, or lose) or needs to continue.
	 * 
	 * @return {@code GameStatus.Won} - if game is won
	 * @return {@code GameStatus.Lost} - if game is over and lost
	 * @return {@code GameStatus.Continue} - if game is not over
	 *****************************************************************/
	public GameStatus getGameStatus () {
		// you can not select a Flagged spot
		if (!gameBoard[sRow][sCol].isFlagged()) 
			if (gameBoard[sRow][sCol].isMine()) 
				return GameStatus.Lost;

		boolean won = true;
		for (int row = 0; row < rows; row++) 
			for (int col = 0; col < columns; col++) {
				if (!gameBoard[row][col].isMine())
					if (!gameBoard[row][col].isExposed())
						won = false;
			}
		if (won)
			return GameStatus.Won;

		return GameStatus.Continue;
	}


	/*****************************************************************
	 * A helper method that will prepare the game to be wiped clean,
	 * bombs set, and game is reset.
	 *****************************************************************/
	public void reset() {
		// Wipes the board clean
		for (int row = 0; row < rows; row++) 
			for (int col = 0; col < columns; col++)
				gameBoard[row][col] = new Cell (0, false, false, false);

		// Creates Bombs
		Random r = new Random();
		for (int i = 0; i < totalBombs; i++) {
			int row = r.nextInt(rows);
			int col = r.nextInt(columns);
			//if there already is a bomb, retries
			if (gameBoard[row][col].isMine())
				i--;
			else
				gameBoard[row][col].setMine(true);
		}
	}

	/*****************************************************************
	 * Returns the game board to be displayed in the GUI.
	 * 
	 * @return {@code gameBoard} - 2D Array of all Cell type.
	 *****************************************************************/
	public Cell[][] getDisplay() {
		return gameBoard;
	}

	/*****************************************************************
	 * Returns the number of consecutive wins in the given session.
	 * 
	 * @return {@code wins} - number of consecutive wins
	 *****************************************************************/
	public int getWins() {
		return wins;
	}

	/*****************************************************************
	 * Returns the number of consecutive losses in the given session.
	 * 
	 * @return {@code losses} - number of consecutive losses
	 *****************************************************************/
	public int getLosses() {
		return losses;
	}

	/*****************************************************************
	 * Adds one win to the win count.
	 *****************************************************************/
	public void addWin() {
		wins++;
	}

	/*****************************************************************
	 * Adds one loss to the loss count.
	 *****************************************************************/
	public void addLoss() {
		losses++;
	}
}




