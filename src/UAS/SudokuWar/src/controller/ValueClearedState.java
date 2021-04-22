package controller;

import model.CellLocation;

// when a value in the game has been cleared
public class ValueClearedState {
	
	public CellLocation loc;
	
	public ValueClearedState( CellLocation l ) {
		loc = l;
	}
}
