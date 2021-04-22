package controller;

import java.io.*;
import java.net.*;
import java.util.Observable;
import java.util.Observer;
import model.*;


public class ClientThread extends Thread implements SudokuConstants, Observer {
	Socket socket = null;
	PrintWriter out;
	BufferedReader in;
	GameStatus gameStatus;

	
	public ClientThread( Socket socket, GameStatus gameStatus ) {
		this.socket = socket;
		this.gameStatus = gameStatus;
	}
	
	
	public void run() {
		
		try {
			this.gameStatus.hasConnection();
			this.gameStatus.addObserver( this );
			
			out = new PrintWriter( socket.getOutputStream(), true );
			in = new BufferedReader( new InputStreamReader( socket.getInputStream() ));
			
			// upon connection, receive data for current game
			String fromServer = in.readLine();
			
			if ( fromServer.equals( NEW_GAME_MSG ) ) {
				fromServer = in.readLine();
				parseNewGameData( fromServer );
				out.println( ACK_MSG );
				out.flush();
			}
			
			while ( (fromServer = in.readLine()) != null ) {
				if ( fromServer.equals( NEW_GAME_MSG ) ) {
					fromServer = in.readLine();
					parseNewGameData( fromServer );
				} else if (fromServer.equals( NEW_NBR_MSG ) ) {
					fromServer = in.readLine();
					setNumber( fromServer );
				}
			}
			
			out.close();
			in.close();
			socket.close();
			
			this.gameStatus.deleteObserver( this );
			this.gameStatus.hasNoConnection();
			
		} catch( IOException e ) {
			System.out.println( "Error:" + e.getMessage() );
		}
	}			

	
	private void parseNewGameData( String data ) {
		gameStatus.deserialize( data );
	}
	
	
	private void setNumber( String data ) {
		String[] fields = data.split(",");
		int row = Integer.parseInt( fields[0] );
		int col = Integer.parseInt( fields[1] );
		int nbr = Integer.parseInt( fields[2] );
			
		gameStatus.updateReceivedNbr( row, col, nbr);
	}
	
	
	private void sendNumber( CellLocation loc, int nbr ) {
    	if ( out == null )
    		return;
    	
		out.println( NEW_NBR_MSG );
		out.println( loc.getRow() + "," + loc.getCol() + "," + nbr );
		out.flush();
	}
	
	
	public void update( Observable o, Object arg ) {
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
