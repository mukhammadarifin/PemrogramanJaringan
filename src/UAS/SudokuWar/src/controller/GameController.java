package controller;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.GameStatus;
import view.Cell;


public class GameController implements MouseListener, KeyListener {

	private GameStatus game;
	
	
	public GameController( GameStatus game ) {
		this.game = game;
	}

	
	public void mousePressed(MouseEvent e) {
		JPanel panel = (JPanel) e.getSource();
		Component component = panel.getComponentAt( e.getPoint() );
		
		if ( !(component instanceof Cell) )
			return;
		
		Cell cell = (Cell) component;
		int row = cell.getRow();
		int col = cell.getCol();
		
		if (e.getButton() == MouseEvent.BUTTON1 ) {
			game.setFocus( row, col );
		} else if (e.getButton() == MouseEvent.BUTTON3 ) {
			game.clearNumber( row, col );
		}
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
	
	public void keyPressed( KeyEvent e){
		int keyCode = e.getKeyCode();
		String key = e.getKeyText( keyCode );
	    
		// check for a number entered on the number pad
		if ( key.startsWith( "NumPad-" ) ) {
			// the number in the 8th position
			key = key.substring( 7, 8 );
		}
		
		try {
			int nbr = Integer.parseInt( key );
			game.setNumberInGame( game.getCurrentCell(), nbr );
		} catch ( NumberFormatException nfe ) {
		}
	}
	
	public void keyTyped ( KeyEvent e ) {}
	public void keyReleased ( KeyEvent e ) {}
}
