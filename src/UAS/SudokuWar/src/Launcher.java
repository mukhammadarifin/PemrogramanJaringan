/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;

import model.SudokuConstants;
import view.*;


@SuppressWarnings("serial")
public class Launcher extends JFrame implements SudokuConstants, ActionListener {

	private JTextField newGamePortTF;
	private JButton launchBtn;
	private JTextField joinGameAddrTF;
	private JButton joinBtn;
	
	
	public static void main (String args[]) {
		JFrame frame = new Launcher();
		frame.setVisible(true);		
	}


	public Launcher() {
		setTitle( LOGIN_WINDOW_NAME );
		setSize( LOGIN_WINDOW_WIDTH, LOGIN_WINDOW_HEIGHT );
		setLayout( new GridLayout( 2, 1 ) );

		JLabel newGameLbl = new JLabel( "Start a new game.  Port: " );
		newGamePortTF = new JTextField( 5 );
		newGamePortTF.setText( Integer.toString( DEFAULT_PORT ) );
		launchBtn = new JButton( LAUNCH_BTN_NAME );
		launchBtn.addActionListener( this );

		JPanel startP = new JPanel();
		startP.add( newGameLbl );
		startP.add( newGamePortTF );
		startP.add( launchBtn );

		JLabel joinGameLbl = new JLabel( "Join a game.  Address: " );
		joinGameAddrTF = new JTextField( 15 );
		joinGameAddrTF.setText( DEFAULT_IP + ":" + DEFAULT_PORT );
		joinBtn = new JButton( JOIN_BTN_NAME );
		joinBtn.addActionListener( this );

		JPanel joinP = new JPanel();
		joinP.add( joinGameLbl );
		joinP.add( joinGameAddrTF );
		joinP.add( joinBtn );

		getContentPane().add( startP );
		getContentPane().add( joinP );

	}


	public void actionPerformed(ActionEvent e) {
		if ( e.getActionCommand().equals( LAUNCH_BTN_NAME )) {
			launchGame();
		} else
			joinGame();
	}

	
	private void launchGame() {
		String portStr = newGamePortTF.getText().trim();
		ServerSocket server = null;
		
		try {
			int port = Integer.parseInt( portStr );
			server = new ServerSocket( port );
			dispose();
			new HostView( server );
		} catch ( NumberFormatException nfe ) {
			showMsg( "Invalid port: " + portStr );
		} catch ( IOException ioe ) {
			showMsg( "Launch server failed: " + ioe.getMessage() );
		}
	}

	
	private void joinGame() {
		String netAddr = joinGameAddrTF.getText();
		String[] netAddrTokens = netAddr.split( ":" );
		
		if ( netAddrTokens == null ) {
			showMsg( "Please enter a proper address and port." );
		} else if ( netAddrTokens.length < 2 ) {
			showMsg( "Please enter an address and port in a form such as '127.0.0.1:1234'" );
		}
		
		String host = netAddrTokens[0];
		String portStr = netAddrTokens[1].trim();
		
		try {
			int port = Integer.parseInt( portStr );
			Socket socket = new Socket( host, port );
			dispose();
			new GuestView( socket );
		} catch (NumberFormatException nfe ) {
			showMsg( "Invalid port: " + portStr );
		} catch ( IOException ioe ) {
			showMsg( "Join game failed: " + ioe.getMessage() );
		}
	}

	
	private void showMsg( String msg ) {
		JOptionPane.showMessageDialog( this, msg, "Message", JOptionPane.WARNING_MESSAGE);
	}
}
