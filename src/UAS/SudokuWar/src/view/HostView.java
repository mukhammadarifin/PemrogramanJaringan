package view;

import java.awt.*;
import java.awt.event.*;
import java.net.ServerSocket;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import model.*;
import controller.*;



public class HostView extends JFrame implements SudokuConstants, ActionListener, Observer {

	private GameStatus gameStatus;
	private JTextField levelTF;
	private JButton newGameBtn;
	private Board board;
	private GameController gameCtrl;
	private ServerSocket server;
	private ServerThread netCtrl;
	
	
	public HostView( ServerSocket socket ) {
		server = socket;
		
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setTitle( GAME_WINDOW_NAME );
		setSize( HOST_WINDOW_WIDTH, HOST_WINDOW_HEIGHT );

		JLabel levelLbl = new JLabel( "Level(1 hardest, 8 simplest): " );
		levelTF = new JTextField( 5 );
		levelTF.setText( Integer.toString( DEFAULT_DIFFICULTY ) );
		newGameBtn = new JButton( NEW_GAME_BTN_NAME );
		newGameBtn.addActionListener( this );
		
		JPanel levelP = new JPanel();
		levelP.add( levelLbl );
		levelP.add( levelTF );
		levelP.add( newGameBtn );
		getContentPane().add( levelP, BorderLayout.PAGE_START );
		
		gameStatus = new GameStatus();
		
		// set up model-view-controller relationships
		gameCtrl = new GameController( gameStatus );
		board = new Board( gameCtrl );
		gameStatus.addObserver( board );
		gameStatus.addObserver( this );
		
		startNewGame( DEFAULT_DIFFICULTY );
		
		getContentPane().add( board, BorderLayout.CENTER );
		
		//pack();
		setLocationRelativeTo(null);
		setVisible( true );
		setResizable( false );
		
		startListening();
	}
	
	
	private void startListening() {
		netCtrl = new ServerThread( server, gameStatus );
		netCtrl.start();
	}
	
	
	private void startNewGame( int level ) {
		gameStatus.startNewGame( level );
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		if ( e.getActionCommand().equals( NEW_GAME_BTN_NAME )) {
			String lvlStr = levelTF.getText().trim();
			try {
				int lvl = Integer.parseInt( lvlStr );
				if ( lvl < MIN_DIFFICULTY || lvl > MAX_DIFFICULTY ) {
					showMsg( "Please enter a difficulty between " + MIN_DIFFICULTY + " and " + MAX_DIFFICULTY );
					return;
				}
				startNewGame( lvl );
			} catch ( NumberFormatException nfe ) {
				showMsg( "Start new game failed. Invalid level: " + lvlStr );
			} 
		}
	}


	public void update( Observable o, Object arg ) {
		if ( arg instanceof ConnectedState ) {
			showMsg( "Guest has connected." );
			return;
		}
		
		if ( arg instanceof LostConnectionState ) {
			showMsg( "Guest has disconnected." );
			startListening();
			return;
		}
	}

    
	private void showMsg( String msg ) {
		JOptionPane.showMessageDialog( this, msg, "Message", JOptionPane.WARNING_MESSAGE);
	}

	
	public void update() {}
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
