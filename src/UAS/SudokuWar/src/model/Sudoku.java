package model;

import java.util.*;


public class Sudoku implements SudokuConstants {


	// generate a new solution
	public static int[][] generateGame() {
		int[][] game = new int[BOARD_SIZE][BOARD_SIZE];
		
		// randomly generate the first row
		game[0] = genRandomRow();
		
		if ( !canBeFilled( 0, game ) )
			return null;
		
		return game;
	}

	
	// uses backtracking to come up with a solution
	public static boolean canBeFilled( int index, int[][] cells ) {

		if ( index > (BOARD_SIZE * BOARD_SIZE - 1) )
			return true;
		
		int col = index % BOARD_SIZE;
		int row = index / BOARD_SIZE;
		
		if ( cells[row][col] != 0 )
			return canBeFilled( index+1, cells);
		
		int[] randomRow = genRandomRow();
		for ( int i=0; i<BOARD_SIZE; i++ ) {
			if ( isValid( row, col, randomRow[i], cells ) ) {
				cells[row][col] = randomRow[i];
				if ( canBeFilled( index+1, cells) )
					return true;
			}
		}
	
		// no possible solution -> backtrack
		cells[row][col] = 0;
		return false;
	}
	

	// checks whether a value place at cells[row][col] is valid
	public static boolean isValid( int row, int col, int value, int[][] cells ) {
		// check whether there is a duplicate value in the row
		for ( int i=0; i<BOARD_SIZE; i++ ) { 
			if ( i == col )
				continue;

			if ( value == cells[row][i] ) {
				return false;
			}
		}

		// check whether there is a duplicate value in the column
	    for ( int i=0; i<BOARD_SIZE; i++ ) {
	    	if ( i == row )
	    		continue;
	    	
	    	if ( value == cells[i][col]) {
	    		return false;
	    	}
	    }

	    int sectRow = (row / BLOCK_SIZE) * BLOCK_SIZE;
	    int sectCol = (col / BLOCK_SIZE) * BLOCK_SIZE;
	    
	    // check whether there is a duplicate value in the block
	    for ( int i=0; i<BLOCK_SIZE; i++ ) { 
	    	for ( int j=0; j<BLOCK_SIZE; j++ ) {
	          	int rowOffset = sectRow + i;
	           	int colOffset = sectCol + j;
	           	
	           	if ( rowOffset == row && colOffset == col )
	           		continue;
	           	
	            if ( value == cells[rowOffset][colOffset]) {
	            	return false;
	            }
	        }
	    }

	    return true;
	}

	
	// generates an array with the integers 1 to 9 randomly placed
	public static int[] genRandomRow() {
		int[] row = new int[BOARD_SIZE];
		for ( int i=0; i<row.length; i++ ) {
			row[ i ] = i+1;
		}
		
		Random rand = new Random();
		for ( int i=row.length-1; i>0; i-- ) {
			int j = rand.nextInt(i+1);

			if ( i == j )
				continue;
			
			int temp = row[i];
			row[i] = row[j];
			row[j] = temp;
		}
		
		return row;
	}
}
