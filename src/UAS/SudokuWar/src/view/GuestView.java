package view;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import model.*;
import controller.*;


public class GuestView extends JFrame implements SudokuConstants, ActionListener, Observer {

	private GameStatus gameStatus;
	private Board board;
	private GameController gameCtrl;
	private Socket conn;
	
	
	public GuestView( Socket socket ) {
		conn = socket;
		
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setTitle( GAME_WINDOW_NAME );
		setSize( GUEST_WINDOW_WIDTH, GUEST_WINDOW_HEIGHT );

		gameStatus = new GameStatus();
		
		// set up model-view-controller relationships
		gameCtrl = new GameController( gameStatus );
		board = new Board( gameCtrl );
		gameStatus.addObserver( board );
		gameStatus.addObserver( this );
		
		getContentPane().add( board, BorderLayout.CENTER );
		setLocationRelativeTo( null );
		setVisible( true );
		setResizable( false );
		
		startConnecting();
	}
	
	
	// starts connecting to the host
	private void startConnecting() {
		new ClientThread( conn, gameStatus ).start();
	}
	
	
	public void update( Observable o, Object arg ) {
		if ( arg instanceof LostConnectionState ) {
			showMsg( "Host has disconnected." );
			return;
		}
	}


    
	private void showMsg( String msg ) {
		JOptionPane.showMessageDialog( this, msg, "Message", JOptionPane.WARNING_MESSAGE);
	}
	
    
	public void actionPerformed(ActionEvent e) {}
	public void update() {}
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
