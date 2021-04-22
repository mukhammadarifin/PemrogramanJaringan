package view;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import model.*;
import controller.*;


public class Board extends JPanel implements SudokuConstants, Observer {

	private GameController gameCtrl;
	Section[][] sections = new Section[BLOCK_SIZE][BLOCK_SIZE];

	
	public Board( GameController ctrl ) {
		
		gameCtrl = ctrl;
		setSize( BOARD_WIDTH, BOARD_HEIGHT );
		Color bkgdColor = BLOCK_COLOR1;
                

		setLayout( new GridLayout(BLOCK_SIZE,BLOCK_SIZE) );
		for ( int i=0; i<BLOCK_SIZE; i++ ) {
			for ( int j=0; j<BLOCK_SIZE; j++ ) {
				
				if ( (i+j)%2 == 0 ) {
					bkgdColor = BLOCK_COLOR1;
				} else {
					bkgdColor = BLOCK_COLOR2;
				}
				
				Section section = new Section(i, j, bkgdColor );
				sections[i][j] = section;
				section.setBorder( BorderFactory.createLineBorder( BLOCK_BORDER_COLOR ) );
				section.addMouseListener( gameCtrl );
				section.addKeyListener( gameCtrl );
				add( section );
			}
		}
		
	}
	
	
	// populates the game board from the host side
	public void populate( int[][] game ) {
		for ( int row=0; row<BLOCK_SIZE; row++ ) {
			for ( int col=0; col<BLOCK_SIZE; col++ ) {
				sections[row][col].populate( row, col, game );
			}
		}
	}
	
	
	// populates the game board from the guest side
	public void populateFromOutside( int[][] game, int[][] boardStatus ) {
		for ( int row=0; row<BLOCK_SIZE; row++ ) {
			for ( int col=0; col<BLOCK_SIZE; col++ ) {
				sections[row][col].populateFromOutside( row, col, game, boardStatus );
			}
		}
		
		showStatus( boardStatus );
	}
	
	
	public void setNumber( CellLocation loc, int nbr, Color color ) {
		Section sect = getCorrectSect( loc );
		sect.setNumber( loc, nbr, color );
	}
	
	
	// sets focus to a cell so that the user may enter a number in it
	public void setFocus( CellLocation loc ) {
		if ( !loc.isValid() )
			return;
		
		Section sect = getCorrectSect( loc );
		sect.setFocus( loc );
	}

	
	public void removeFocus( CellLocation loc ) {
		if ( !loc.isValid() )
			return;
		
		Section sect = getCorrectSect( loc );
		sect.removeFocus( loc );
	}
	
	
	// updates the display of the board to show and remove conflicts
	public void showStatus( int[][] boardStatus ) {
		for ( int row=0; row<BOARD_SIZE; row++ ) {
			for ( int col=0; col<BOARD_SIZE; col++ ) {
				int status = boardStatus[row][col];
				if ( status == GameStatus.UNMODIFIABLE )
					continue;
				
				CellLocation loc = new CellLocation( row, col );
				Section sect = getCorrectSect( loc );
				sect.showStatus( loc, status );
			}
		}
	}
	
	
	public void showGameSolved() {
		JOptionPane.showMessageDialog( this, "Game solved!");
	}
	
	// handles notification from the model
	public void update( Observable o, Object arg ) {
		
		if ( arg instanceof NewGameState ) {
			NewGameState state = (NewGameState) arg;
			populate( state.game );
			return;
		}
		
		if ( arg instanceof CellFocusedState ) {
			CellFocusedState state = (CellFocusedState) arg;
			setFocus( state.currCell );
			removeFocus( state.prevCell );
			return;
		}
		
		if ( arg instanceof ValueChangedState ) {
			ValueChangedState state = (ValueChangedState) arg;
			setNumber( state.loc, state.newValue, USER_COLOR );
			return;
		}
		
		if ( arg instanceof ValueClearedState ) {
			ValueClearedState state = (ValueClearedState) arg;
			setNumber( state.loc, 0, USER_COLOR );
			return;
		}
		
		if ( arg instanceof ValueReceivedState ) {
			ValueReceivedState state = (ValueReceivedState) arg;
			setNumber( state.loc, state.newValue, FOREIGN_COLOR );
			return;
		}
		
		if ( arg instanceof ConflictingValuesState ) {
			ConflictingValuesState state = (ConflictingValuesState) arg;
			int[][] boardStatus = state.boardStatus;
			showStatus( boardStatus );
			return;
		}
		
		if ( arg instanceof ReceivedGameState ) {
			ReceivedGameState state = (ReceivedGameState) arg;
			populateFromOutside( state.game, state.boardStatus );
			return;
		}
		
		if ( arg instanceof GameSolvedState ) {
			showGameSolved();
			return;
		}
		
	}
	

    // retrieve the correct section based on the row and column of the game
	private Section getCorrectSect( CellLocation loc ) {
		int row = loc.getRow() / BLOCK_SIZE;
		int col = loc.getCol() / BLOCK_SIZE;
		return sections[row][col];
	}
}
