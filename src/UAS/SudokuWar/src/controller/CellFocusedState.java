package controller;

import model.CellLocation;


// when a user clicks on a cell
public class CellFocusedState {
	
	public CellLocation currCell;
	public CellLocation prevCell;
	
	public CellFocusedState( CellLocation cCell, CellLocation pCell ) {
		currCell = cCell;
		prevCell = pCell;
	}
}
