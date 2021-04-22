package controller;

// when there are conflicting values in the game
public class ConflictingValuesState {
	
	public int[][] boardStatus;
	
	
	public ConflictingValuesState( int[][] locs ) {
		boardStatus = locs;
	}
}
