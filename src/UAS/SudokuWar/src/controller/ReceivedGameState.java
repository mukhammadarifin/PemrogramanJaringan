package controller;

// when a new game is sent from the host
public class ReceivedGameState {
	
	public int[][] game;
	public int[][] boardStatus;
	
	
	public ReceivedGameState(int[][] game, int[][] boardStatus ) {
		this.game = game;
		this.boardStatus = boardStatus;
	}
}
