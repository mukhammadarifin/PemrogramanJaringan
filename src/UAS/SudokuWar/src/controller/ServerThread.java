package controller;

import java.io.*;
import java.net.*;
import java.util.Observable;
import java.util.Observer;
import model.*;


public class ServerThread extends Thread implements SudokuConstants, Observer {
	ServerSocket server = null;
	PrintWriter out;
	BufferedReader in;
	GameStatus gameStatus;

	
	public ServerThread( ServerSocket server, GameStatus gameStatus ) {
		this.server = server;
		this.gameStatus = gameStatus;
	}
	
	
	public void run() {
		
		try {
			Socket socket = server.accept();
			
			this.gameStatus.hasConnection();
			this.gameStatus.addObserver( this );
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
			// upon connection, send over current game 
			sendEntireGame();
			
			String fromClient = in.readLine();
			
			while ( (fromClient = in.readLine()) != null ) {
				if (fromClient.equals( NEW_NBR_MSG ) ) {
					fromClient = in.readLine();
					setNumber( fromClient );
				}
			}
			
			out.close();
			in.close();
			socket.close();
			
			this.gameStatus.deleteObserver( this );
			this.gameStatus.hasNoConnection();
			
		} catch( Exception e ) {
			System.out.println( "Error: " + e.getMessage() );
		}
	}			
	
	
	private void sendEntireGame() {
		if ( out == null )
			return;
		
		out.println( NEW_GAME_MSG );
		out.println( gameStatus.serialize() );
		out.flush();
	}
	
	
	private void sendNumber( CellLocation loc, int nbr ) {
		if ( out == null )
			return;
    	
		out.println( NEW_NBR_MSG );
		out.println( loc.getRow() + "," + loc.getCol() + "," + nbr );
		out.flush();

	}
	
	
	private void setNumber( String data ) {
		String[] fields = data.split(",");
		int row = Integer.parseInt( fields[0] );
		int col = Integer.parseInt( fields[1] );
		int nbr = Integer.parseInt( fields[2] );
		gameStatus.updateReceivedNbr( row, col, nbr);
	}
	
	
	public void update( Observable o, Object arg ) {
		if ( arg instanceof NewGameState ) {
			NewGameState state = (NewGameState) arg;
			sendEntireGame();
			return;
		}
		
		if ( arg instanceof ValueChangedState ) {
			ValueChangedState state = (ValueChangedState) arg;
			sendNumber( state.loc, state.newValue );
			return;
		}
		
		if ( arg instanceof ValueClearedState ) {
			ValueClearedState state = (ValueClearedState) arg;
			sendNumber( state.loc, 0 );
			return;
		}
	}
}
