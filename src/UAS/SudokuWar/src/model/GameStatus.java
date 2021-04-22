package model;

import java.util.*;
import controller.*;


public class GameStatus extends Observable implements SudokuConstants {

	public static final int UNMODIFIABLE = -1;
	public static final int HAS_NO_CONFLICT = 0;
	public static final int HAS_CONFLICT = 1;
	
	private int[][] solution;
	private int[][] game;
	private int[][] boardStatus;
	
	private CellLocation currCell;
	private CellLocation prevCell;
	
	private boolean isConnected;
	
	
	public GameStatus() {
		solution = new int[ BOARD_SIZE ][BOARD_SIZE];
		game = new int[BOARD_SIZE][BOARD_SIZE];
		boardStatus = new int[BOARD_SIZE][BOARD_SIZE];
		currCell = new CellLocation( CellLocation.INVALID, CellLocation.INVALID );
		prevCell = new CellLocation( CellLocation.INVALID, CellLocation.INVALID );
		isConnected = false;
	}

	
	public void startNewGame( int level ) {
		do {
			solution = Sudoku.generateGame();
		} while ( solution == null );
		
		game = new int[BOARD_SIZE][BOARD_SIZE];
		boardStatus = new int[BOARD_SIZE][BOARD_SIZE];
		currCell.clear();
		prevCell.clear();
		
		setDifficulty( level );
		
		NewGameState state = new NewGameState( game );
		setChanged();
		notifyObservers( state );
	}
	

	public void startReceivedGame( int[][] newGame, int[][] newBoardStatus ) {
		game = newGame;
		boardStatus = newBoardStatus;
		currCell.clear();
		prevCell.clear();
		
		ReceivedGameState state = new ReceivedGameState( game, boardStatus );
		setChanged();
		notifyObservers( state );
	}
	
	
	public int[][] getGame() {
		return game;
	}
	
	
	private void setDifficulty( int level ) {
		int[] randomRow;
		
		for ( int sect=0; sect<BOARD_SIZE; sect++ ) {
			randomRow = Sudoku.genRandomRow();
			int startRow = (sect / BLOCK_SIZE) * BLOCK_SIZE;
			int startCol = (sect % BLOCK_SIZE) * BLOCK_SIZE;
			
			for ( int i=0; i<level; i++ ) {
				int offset = randomRow[i] - 1;
				int rowOffset = offset / BLOCK_SIZE;
				int colOffset = offset % BLOCK_SIZE;
				game[startRow+rowOffset][startCol+colOffset] = solution[startRow+rowOffset][startCol+colOffset];
				boardStatus[startRow+rowOffset][startCol+colOffset] = UNMODIFIABLE;
			}
		}
	}
	
	
	public void setFocus( int row, int col ) {
		if ( boardStatus[row][col] == UNMODIFIABLE ) {
			return;
		}
			
		if ( currCell.isEqualTo( new CellLocation( row, col ) ) )
			return;
		
		prevCell.copyFrom( currCell );
		currCell.setLoc( row, col );
		
		CellFocusedState state = new CellFocusedState( currCell, prevCell );
		setChanged();
		notifyObservers( state );
	}
	
	
	public CellLocation getCurrentCell() {
		return currCell;
	}
	
	public CellLocation getPreviousCell() {
		return prevCell;
	}
	
	
	public void clearNumber( int row, int col ) {
		if ( boardStatus[row][col] == UNMODIFIABLE )
			return;
		
		setFocus( row, col );
		game[row][col] = 0;
		
		ValueClearedState state = new ValueClearedState( new CellLocation( row, col) );
		setChanged();
		notifyObservers( state );
		
		checkGame();
	}
	
	
	public void setNumberInGame( CellLocation loc, int nbr ) {
		setNumberInGame( loc.getRow(), loc.getCol(), nbr );
	}
	
	public void setNumberInGame( int row, int col, int nbr ){
		game[row][col] = nbr;
		
		ValueChangedState state = new ValueChangedState( new CellLocation( row, col), nbr );
		setChanged();
		notifyObservers( state );
		
		checkGame();
	}
	
	public void updateReceivedNbr( int row, int col, int nbr ){
		game[row][col] = nbr;
		
		ValueReceivedState state = new ValueReceivedState( new CellLocation( row, col), nbr );
		setChanged();
		notifyObservers( state );
		
		checkGame();
	}
	
	public int getNumberInGame( int row, int col ){
		return game[row][col];
	}
	
	
	private synchronized void checkGame() {
		boolean isSolved = true;
		
		for ( int row=0; row<BOARD_SIZE; row++ ) {
			for (int col=0; col<BOARD_SIZE; col++ ) {
				
				int value = game[row][col];				
				if ( value == 0 ) {
					isSolved = false;
					boardStatus[row][col] = HAS_NO_CONFLICT;
					continue;
				}
				
				int status = boardStatus[row][col];
				if ( status == UNMODIFIABLE ) {
					continue;
				}
				
				if ( !Sudoku.isValid( row, col, value, game )) {
					isSolved = false;
					
					boardStatus[row][col] = HAS_CONFLICT;
				} else {
					boardStatus[row][col] = HAS_NO_CONFLICT;
				}
			}
		}
		
		if ( isSolved ) {
			lockBoard();
			GameSolvedState solved = new GameSolvedState();
			setChanged();
			notifyObservers( solved );
			return;
		}
			
		ConflictingValuesState conflict = new ConflictingValuesState( boardStatus );
		setChanged();
		notifyObservers( conflict );
		
	}
	

	private void lockBoard() {
		// set all cells to unmodifiable
		for ( int row=0; row<BOARD_SIZE; row++ ) {
			for ( int col=0; col<BOARD_SIZE; col++ ) {
				boardStatus[row][col] = UNMODIFIABLE;
			}
		}
	}
	
	
	public void hasConnection() {
		isConnected = true;
		ConnectedState state = new ConnectedState();
		setChanged();
		notifyObservers( state );
	}

	
	public void hasNoConnection() {
		isConnected = false;
		LostConnectionState state = new LostConnectionState();
		setChanged();
		notifyObservers( state );
	}
	
	
	public String serialize() {
		StringBuffer buffer = new StringBuffer();
		for ( int row=0; row<BOARD_SIZE; row++ ) {
			for ( int col=0; col<BOARD_SIZE; col++ ) {
				buffer.append( row + "," + col + "," + game[row][col] + "," + boardStatus[row][col] + ";" );
			}
		}
		return buffer.toString();
	}
	
	public void deserialize( String data ) {
		int[][] newGame = new int[BOARD_SIZE][BOARD_SIZE];
		int[][] newBoardStatus = new int[BOARD_SIZE][BOARD_SIZE];
		
		String[] tokens = data.split( ";" );
		for ( String token : tokens ) {
			String[] fields = token.split(",");
			int row = Integer.parseInt( fields[0] );
			int col = Integer.parseInt( fields[1] );
			int nbr = Integer.parseInt( fields[2] );
			int status = Integer.parseInt( fields[3] );
			
			newGame[row][col] = nbr;
			newBoardStatus[row][col] = status;
		}
		
		startReceivedGame( newGame, newBoardStatus );
	}
}
