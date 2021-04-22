package controller;

import model.CellLocation;

// when a value in the game has changed
public class ValueChangedState {

	public CellLocation loc;
	public int newValue;
	
	
	public ValueChangedState( CellLocation l, int value ) {
		loc = l;
		newValue = value;
	}
}
