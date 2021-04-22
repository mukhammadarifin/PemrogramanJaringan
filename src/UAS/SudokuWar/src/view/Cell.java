package view;

import java.awt.*;
import javax.swing.*;
import model.SudokuConstants;


public class Cell extends JLabel implements SudokuConstants {

	// row in the game
	private int row;
	// column in the game
	private int col;
	// default background color
	private Color bkgdColor;
	
	
	public Cell( int x, int y, Color defaultBkgd ) {
		super( "", CENTER ) ;
		setSize( CELL_WIDTH, CELL_HEIGHT );
		row = x; 
		col = y;
		bkgdColor = defaultBkgd;
		
		setFont( new Font( Font.SERIF, Font.PLAIN, CELL_NUMBER_SIZE ) );
		setBackground( bkgdColor );
		setBorder( BorderFactory.createLineBorder( CELL_BORDER_COLOR ) );
		setOpaque( true );
	}

	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	
	// shows an unmodifiable value
	public void initNumber( int number ) {
		setNumber( number, UNMODIFIABLE_COLOR );
		showAsNormal();
	}
	
	// shows a normal value
	public void setNormalNumber( int number, Color color ) {
		setNumber( number, color );
		showAsNormal();
	}
	
	// changes the value
	public void setNumber( int number, Color color ) {
		setForeground( color );
		setText( number > 0 ? number + "" : "" );
	}

	 
	public void showAsFocused() {
		setBorder( BorderFactory.createLineBorder( FOCUSED_COLOR ) );
	}
	 
	public void removeFocus() {
		setBorder(BorderFactory.createLineBorder( CELL_BORDER_COLOR ));
	}
	 

	public void showConflict() {
		setBackground( CONFLICT_COLOR );
	}

	public void showNoConflict() {
		setBackground( bkgdColor );
	}

	public void showAsNormal() {
		setBackground( bkgdColor );
		setBorder(BorderFactory.createLineBorder( CELL_BORDER_COLOR ));
	}

}
