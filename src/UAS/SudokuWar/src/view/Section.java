package view;

import java.awt.*;
import javax.swing.*;

import model.*;


public class Section extends JPanel implements SudokuConstants {

	private Cell[][] cells;

	
	public Section( int row, int col, Color defaultBkgd ) {
		setLayout( new GridLayout( BLOCK_SIZE, BLOCK_SIZE ) ) ;
		cells = new Cell[BLOCK_SIZE][BLOCK_SIZE];
		
		for ( int i=0; i<BLOCK_SIZE; i++ ) {
			for ( int j=0; j<BLOCK_SIZE; j++ ) {
				Cell cell = new Cell( row*BLOCK_SIZE+i, col*BLOCK_SIZE+j, defaultBkgd );
				cells[i][j] = cell;
				add( cell );
			}
		}
	}
	
	
	public void populate( int sectRow, int sectCol, int[][] numbers ) {
		for ( int row=0; row<BLOCK_SIZE; row++ ) {
			for ( int col=0; col<BLOCK_SIZE; col++ ) {				
				int gameRow = sectRow * BLOCK_SIZE + row;
				int gameCol = sectCol * BLOCK_SIZE + col;
				int value = numbers[ gameRow ][ gameCol ];
				cells[row][col].initNumber( value );
			}
		}
	}
	
	
	public void populateFromOutside( int sectRow, int sectCol, int[][] numbers, int[][] boardStatus ) {
		for ( int row=0; row<BLOCK_SIZE; row++ ) {
			for ( int col=0; col<BLOCK_SIZE; col++ ) {
				
				int gameRow = sectRow * BLOCK_SIZE + row;
				int gameCol = sectCol * BLOCK_SIZE + col;
				int value = numbers[ gameRow ][ gameCol ];
				int status = boardStatus[ gameRow ][ gameCol ];
				
				if ( status == GameStatus.UNMODIFIABLE )
					cells[row][col].initNumber( value );
				else
					cells[row][col].setNormalNumber( value, FOREIGN_COLOR );
			}
		}
	}
	
	
	public void setNumber( CellLocation loc, int nbr, Color color ) {
		Cell cell = getCorrectCell( loc );
		cell.setNumber( nbr, color );
	}
	
	public void setFocus( CellLocation loc ) {
		Cell cell = getCorrectCell( loc );
		cell.showAsFocused();
		requestFocusInWindow();
	}
	
	public void removeFocus( CellLocation loc ) {
		Cell cell = getCorrectCell( loc );
		cell.removeFocus();
	}
	
	public void showStatus( CellLocation loc, int status ) {
		Cell cell = getCorrectCell( loc );
		
		if ( status == GameStatus.HAS_CONFLICT )
			cell.showConflict();
		else if ( status == GameStatus.HAS_NO_CONFLICT )
			cell.showNoConflict();
	}
	
	
	// retrieves the correct cell based on the row and column in the game
	private Cell getCorrectCell( CellLocation loc ) {
		int row = loc.getRow() % BLOCK_SIZE;
		int col = loc.getCol() % BLOCK_SIZE;
		return cells[row][col];
	}
}
