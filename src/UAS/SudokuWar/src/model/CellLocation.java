package model;


public class CellLocation {

	public static final int INVALID = -1;
	private int row;
	private int col;
	
	
	public CellLocation( int row, int col ) {
		this.row = row; 
		this.col = col;
	}
	
	
	public int getRow() {
		return row;
	}
	
	public void setRow( int row ) {
		this.row = row;
	}
	
	
	public int getCol() {
		return col;
	}
	
	public void setCol( int col ) {
		this.col = col;
	}
	
	
	public void setLoc( int row, int col ) {
		this.row = row;
		this.col = col;
	}
	
	
	public void clear() {
		row = INVALID;
		col = INVALID;
	}
	
	public boolean isValid() {
		if ( row == INVALID || col == INVALID )
			return false;
		
		return true;
	}
	
	
	public boolean isEqualTo( CellLocation loc ) {
		if ( row != loc.getRow() )
			return false;
		
		if ( col != loc.getCol() )
			return false;
		
		return true;
	}
	
	
	public void copyFrom( CellLocation loc ) {
		row = loc.getRow();
		col = loc.getCol();
	}
}
