import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


/*****************************************************************
 * The Class and panel for MineSweeper to display all the
 * information to the user using a graphic user interface (GUI)
 * using model view control (MVC).
 * 
 * @author Brody Berson
 * @version 1.0
 *****************************************************************/
public class MineSweeper extends JPanel {
    /** AUTO-GENERATED */
    private static final long serialVersionUID = 1L;

    /** GUI frame */
    private JFrame frame;

    /** Creates a new game */
    private Game g;

    /** Label */
    private JLabel label;

    /** Displays Wins */
    private JLabel winCount;

    /** Displays Losses */
    private JLabel loseCount;

    /** Reset Button */
    private JButton clrButton;

    /** Toggle bomb button */
    private JButton toggleButton;

    /** Integer for use of all rows */
    private int rows;

    /** Integer for use of all columns */
    private int columns;

    /** Boolean for use implementing toggling on bombs */
    private boolean toggle;

    /** 2D Array of buttons for GUI board */
    private JButton[][] board;

    /** 2D Array that is ready to call the game board from Game class */
    private Cell[][] gameBoard;

    /** Menu items */
    private JMenuBar menus;
    private JMenu fileMenu;
    private JMenuItem quitItem;
    private JMenuItem newItem;

    /*****************************************************************
     * Main Method to run {@code mainGame}, separate so you can create
     * a new board in game without having to close program.
     * 
     * @param args[]
     ****************************************************************/ 
    public static void main(String args[]){
	mainGame();
    }

    /*****************************************************************
     * Main Method to run the game and ask for the board size and
     * number of bombs.
     ****************************************************************/
    public static void mainGame() {
	// size of board
	String board;
	int size = 0;
	// will loop if user makes board size smaller than 8 or too big
	do{
	    try {
		board = JOptionPane.showInputDialog(
			"Enter size of board:");
		size = Integer.parseInt(board);
	    }

	    catch (Exception e)  {
	    }
	} while ((size < 8) || (size > 80));
	// number of bombs
	String bombs;
	int totalBombs = 0;
	// user cannot have bombs smaller than one or bigger than board
	do{
	    try {
		bombs = JOptionPane.showInputDialog(
			"Enter number of bombs:");
		totalBombs = Integer.parseInt(bombs);
	    }

	    catch (Exception e)  {
	    }
	} while ((totalBombs < 1) || (totalBombs > (size*size - 1)));
	// sends constructor parameters
	new MineSweeper(size, totalBombs);
    }

    /*****************************************************************
     * Constructor installs all of the GUI components and sets up
     * the game board to be called by the main method.
     * 
     * @param boardSize - the number of both rows and columns
     * @param totalBombs - number of total bombs
     ****************************************************************/ 
    public MineSweeper(int boardSize, int totalBombs){
	// establish the frame
	frame = new JFrame ("Minesweeper");

	rows = boardSize;
	columns = boardSize;

	g = new Game (boardSize, totalBombs);
	board = new JButton[rows][columns];

	JPanel boardPanel = new JPanel();
	MouseAdapter listener = new MouseAdapter();
	boardPanel.setLayout(new GridLayout(rows,columns));

	for (int row = 0; row < rows; row++) 
	    for (int col = 0; col < columns; col++) {
		board[row][col] = new JButton("");
		board[row][col].addMouseListener(listener);
		boardPanel.add(board[row][col]);
	    }

	// sets up the top panel
	JPanel topPanel = new JPanel();


	// sets up top row of labels and buttons
	winCount = new JLabel ("Wins: " + g.getWins());
	topPanel.add (winCount);

	clrButton = new JButton("Reset!");
	clrButton.addMouseListener(listener);
	topPanel.add (clrButton);

	label = new JLabel ("ÁÁÁÁÁMINESWEEPER!!!!!");
	topPanel.add (label);

	toggleButton = new JButton("Toggle");
	toggleButton.addMouseListener(listener);
	topPanel.add (toggleButton);
	toggle = false;

	loseCount = new JLabel ("Losses: " + g.getLosses());
	topPanel.add (loseCount);


	// set up File menu
	fileMenu = new JMenu("File");
	quitItem = new JMenuItem("Quit");
	quitItem.addMouseListener(listener);
	newItem = new JMenuItem("New Board");
	newItem.addMouseListener(listener);
	fileMenu.add(newItem);
	fileMenu.add(quitItem);
	menus = new JMenuBar();
	frame.setJMenuBar(menus);
	menus.add(fileMenu);

	// grabs the gameBoard and sets up the layout of the frame
	gameBoard = g.getDisplay();

	//creates a layout and displays it
	frame.add(BorderLayout.CENTER, boardPanel);
	frame.add(BorderLayout.NORTH, topPanel);
	frame.setSize(500,500);
	frame.setVisible(true);
	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    }

    /*****************************************************************
     * Private helper method to just restart the game class and the
     * GUI, and updates the win and loss count.
     * 
     * @param boardSize - the number of both rows and columns
     * @param totalBombs - number of total bombs
     ****************************************************************/ 
    private void restartGame() {
	g.reset();
	winCount.setText("Wins: " + g.getWins());
	loseCount.setText("Losses: " + g.getLosses());
	// wipes GUI board clean
	for (int row = 0; row < rows; row++) 
	    for (int col = 0; col < columns; col++)
		board[row][col].setText("");
    }

    /*****************************************************************
     * Private helper method to just update all the buttons to be
     * turned on or off, set the mine count and Flagged.
     ****************************************************************/ 
    private void update() {
	for (int row = 0; row < rows; row++) 
	    for (int col = 0; col < columns; col++) {
		if (!gameBoard[row][col].isExposed()) {
		    board[row][col].setEnabled(true);
		    if (gameBoard[row][col].isFlagged())
			board[row][col].setText("F");
		    if (!gameBoard[row][col].isFlagged())
			board[row][col].setText("");
		    // toggle takes precedence of flags
		    if (toggle)
			if (gameBoard[row][col].isMine()) { 
			    board[row][col].setText(".");
			    // toggled mines that are flagged
			    if (gameBoard[row][col].isFlagged())
				board[row][col].setText("!");
			}
		}
		if (gameBoard[row][col].isExposed()) {
		    board[row][col].setEnabled(false);
		    // only display mineCount greater than zero
		    if (gameBoard[row][col].getMineCount() != 0)
			board[row][col].setText("" + 
				gameBoard[row][col].getMineCount());
		}
	    }
    }

    //*****************************************************************
    //  Represents an mouse listener for the temperature input field.
    //*****************************************************************
    private class MouseAdapter implements MouseListener{

	public void mouseClicked(MouseEvent event) {
	    // TODO Auto-generated method stub
	}

	public void mouseEntered(MouseEvent event) {
	    // TODO Auto-generated method stub
	}

	public void mouseExited(MouseEvent event) {
	    // TODO Auto-generated method stub
	}

	public void mousePressed(MouseEvent event) {
	    // TODO Auto-generated method stub
	}

	public void mouseReleased(MouseEvent event) {
	    // extract the component that was clicked
	    JComponent click = (JComponent) event.getSource();
	    // quits the game
	    if (click == quitItem)
		System.exit(0);
	    
	    // creates a new game with new board size
	    if (click == newItem){
		frame.dispose();
		mainGame();
	    }
	    // creates new game
	    if (click == clrButton)
		restartGame();
	    
	    // toggles the mines on or not
	    if (click == toggleButton) {
		if (toggle)
		    toggle = false;
		else
		    toggle = true;
	    }
	    // selects the correct button pressed
	    for (int row = 0; row < rows; row++) 
		for (int col = 0; col < columns; col++) {
		    if (event.getButton() == 1) {
			if (click == board[row][col]) {
			    g.select(row,col);
			    // checks gameStatus
			    if (g.getGameStatus() == GameStatus.Lost) {
				for (row = 0; row < rows; row++) 
				    for (col = 0; col < columns; col++) {
					board[row][col].setEnabled(false);
					board[row][col].setText("*");
				    }
				JOptionPane.showMessageDialog(null,
					"You have blown up...sorry!");
				g.addLoss();
				restartGame();
			    }
			    // checks gameStatus
			    if (g.getGameStatus() == GameStatus.Won) {
				for (row = 0; row < rows; row++) 
				    for (col = 0; col < columns; col++) {
					board[row][col].setEnabled(false);
					update();
					if (gameBoard[row][col].isMine())
					    board[row][col].setText("*");
				    }
				JOptionPane.showMessageDialog(null,
					"Discovered all the mines!");
				g.addWin();
				restartGame();
			    }
			}
		    }
		}
	    // selects the correct button right clicked for flagging
	    for (int row = 0; row < rows; row++) 
		for (int col = 0; col < columns; col++) {
		    if (event.getButton() == 3) {
			if (click == board[row][col]) {
			    if (gameBoard[row][col].isFlagged())
				gameBoard[row][col].setFlagged(false);
			    else
				gameBoard[row][col].setFlagged(true);
			}
		    }
		}
	    // Updates buttons to be turned on or off.
	    update();
	}   
    }
}
