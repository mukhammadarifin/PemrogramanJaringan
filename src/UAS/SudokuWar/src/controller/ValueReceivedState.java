package controller;

import model.CellLocation;

// when a value has been received from the other end of the connection
public class ValueReceivedState {

	public CellLocation loc;
	public int newValue;
	
	
	public ValueReceivedState( CellLocation l, int value ) {
		loc = l;
		newValue = value;
	}
}
