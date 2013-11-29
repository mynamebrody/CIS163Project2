/*****************************************************************
 * Class that creates a type cell that will be invoked into a 2D
 * array list to set up the MineSweeper Game.
 *
 * @author Brody Berson
 * @version 1.0
 *****************************************************************/
public class Cell {
	/** Integer to contain a count of neighboring mines */
	private int mineCount;

	/** If the cell is a mine or not */
	private boolean mine;

	/** If the cell has been flagged or not */
	private boolean flagged;

	/** If the cell has been exposed or not */
	private boolean exposed;

	/*****************************************************************
	 * Constructor to create each individual cell that contains a count
	 * of adjacent mines, if the cell is a mine, if the cell has been
	 * flagged, and/or if the cell has been exposed. 
	 *
	 * @param mineCount - number of adjacent mines around the cell
	 * @param mine - if the cell is a mine or not
	 * @param flagged - if the cell has been flagged
	 * @param exposed - if the cell has been exposed or not
	 *****************************************************************/
	public Cell(int mineCount, boolean mine, boolean flagged,
			boolean exposed) {
		this.mineCount = mineCount;
		this.mine = mine;
		this.flagged = flagged;
		this.exposed = exposed;
	}

	/*****************************************************************
	 * Method to return the number of adjacent mines.
	 *
	 * @return {@code mineCount} - number of adjacent mines around
	 *****************************************************************/
	public int getMineCount() {
		return mineCount;
	}

	/*****************************************************************
	 * Method to set the number of mines adjacent to the current cell.
	 * 
	 * @param mineCount - the {@code mineCount} to set
	 *****************************************************************/
	public void setMineCount(int mineCount) {
		this.mineCount = mineCount;
	}

	/*****************************************************************
	 * Method to return if the cell is a mine or not.
	 * 
	 * @return {@code mine} - if the cell is a mine or not
	 *****************************************************************/
	public boolean isMine() {
		return mine;
	}

	/*****************************************************************
	 * Method to set the current status if the cell is a mine or not.
	 * 
	 * @param mine - the {@code mine} status to set
	 *****************************************************************/
	public void setMine(boolean mine) {
		this.mine = mine;
	}

	/*****************************************************************
	 * Method to return if the cell has been flagged or not.
	 * 
	 * @return {@code flagged} - if the cell has been flagged or not
	 *****************************************************************/
	public boolean isFlagged() {
		return flagged;
	}

	/*****************************************************************
	 * Method to set the current status if the cell is flagged or not.
	 * 
	 * @param flagged - the {@code flagged} status to set
	 *****************************************************************/
	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}

	/*****************************************************************
	 * Method to return if the cell has been exposed or not.
	 * 
	 * @return {@code exposed} - if the cell has been exposed or not
	 *****************************************************************/
	public boolean isExposed() {
		return exposed;
	}

	/*****************************************************************
	 * Method to set the current status if the cell is exposed or not.
	 * 
	 * @param exposed - the {@code exposed} status to set
	 *****************************************************************/
	public void setExposed(boolean exposed) {
		this.exposed = exposed;
	}


}
