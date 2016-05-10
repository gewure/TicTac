package Logic;

/**
 * Created by Florin on 05.05.2016.
 */
public enum Phase {
	PLACING_PLAYER1, MOVING_PLAYER1, REMOVING_PLAYER1,PLACING_PLAYER2,
	MOVING_PLAYER2, REMOVING_PLAYER2, WON_PLAYER1, WON_PLAYER2;

	@Override
	public String toString() {
		switch(this) {
			case PLACING_PLAYER1: return "Player1(RED) has to PLACE a token";
			case PLACING_PLAYER2: return "Player2(BLUE) has to PLACE a token";
			case MOVING_PLAYER1: return "Player1(RED) has to MOVE a token";
			case MOVING_PLAYER2: return "Player2(BLUE) has to MOVE a token";
			case REMOVING_PLAYER1: return "Player1(RED) has to REMOVE a token";
			case REMOVING_PLAYER2: return "Player2(BLUE) has to REMOVE a token";
			case WON_PLAYER1: return "Player1(RED) has WON the game";
			case WON_PLAYER2: return "Player2(BLUE) has WON the game";
			default: throw new IllegalArgumentException();
		}
	}
}
