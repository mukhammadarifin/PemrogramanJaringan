package model;

import java.awt.Color;

/**
 * This file declares several constants that are useful in
 * the program in different modules.  Any class that 
 * implements this interface can use these constants.
 */
public interface SudokuConstants {
	
	/**
	 * The size of the game board, which is also the 
	 * number of cells in a block. The board should
	 * always be square, with equal height and width.
	 */
	public static final int BOARD_SIZE = 9;
	/**
	 * The height (how many rows) of a single block.
	 * The width of a block should be BOARD_SIZE / BLOCK_HEIGHT
	 */
	public static final int BLOCK_HEIGHT = 3;
	/**
	 * The default port number for both server and client.
	 * It is shown as the default value for the text fields.
	 */
	public static final int DEFAULT_PORT = 31416;
	/**
	 * The default IP address to connect for client.
	 * It is shown as the default value for the text fields.
	 */
	public static final String DEFAULT_IP = "127.0.0.1";
	/**
	 * The default difficulty level. It is shown as the default
	 * value for the difficulty text field. You can change this 
	 * value to fit your own difficulty level system.
	 */
	public static final int DEFAULT_DIFFICULTY = 5;

	
	public static final int BLOCK_SIZE = BOARD_SIZE / BLOCK_HEIGHT;
	public static final String LOGIN_WINDOW_NAME = "Login";
	public static final int LOGIN_WINDOW_HEIGHT = 150;
	public static final int LOGIN_WINDOW_WIDTH = 480;
	
	public static final String LAUNCH_BTN_NAME = "Launch!";
	public static final String JOIN_BTN_NAME = "Join!";
	
	public static final String GAME_WINDOW_NAME = "Sudoku";
	public static final int HOST_WINDOW_HEIGHT = 500;
	public static final int HOST_WINDOW_WIDTH = 500;
	public static final int GUEST_WINDOW_HEIGHT = 460;
	public static final int GUEST_WINDOW_WIDTH = 500;
	
	public static final String NEW_GAME_BTN_NAME = "New Game";
	public static final int MIN_DIFFICULTY = 1;
	public static final int MAX_DIFFICULTY = 8;

	public static final Color USER_COLOR = Color.BLACK;
	public static final Color FOREIGN_COLOR = Color.YELLOW;
	public static final Color BLOCK_COLOR1 = new Color( 0, 100, 255 );
	public static final Color BLOCK_COLOR2 = new Color( 0, 100, 204 );
	public static final Color BLOCK_BORDER_COLOR = Color.WHITE;
	public static final Color CELL_BORDER_COLOR = Color.BLACK;
	public static final Color UNMODIFIABLE_COLOR = Color.WHITE;
	public static final Color FOCUSED_COLOR = Color.YELLOW;
	public static final Color CONFLICT_COLOR = Color.RED;
	
	public static final int BOARD_HEIGHT = 400;
	public static final int BOARD_WIDTH = 400;
	public static final int CELL_HEIGHT = 40;
	public static final int CELL_WIDTH = 40;
	public static final int CELL_NUMBER_SIZE = 20;
	
	public static final String NEW_GAME_MSG = "new_game";
	public static final String NEW_NBR_MSG = "new_nbr";
	public static final String ACK_MSG = "OK";
		
}
