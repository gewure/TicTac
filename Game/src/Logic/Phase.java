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
			case PLACING_PLAYER1: return "<b>Player1(RED)</b> has to <b>PLACE</b> a token";
			case PLACING_PLAYER2: return "<b>Player2(BLUE)</b> has to <b>PLACE</b> a token";
			case MOVING_PLAYER1: return "<b>Player1(RED)</b> has to <b>MOVE</b> a token";
			case MOVING_PLAYER2: return "<b>Player2(BLUE)</b> has to <b>MOVE</b> a token";
			case REMOVING_PLAYER1: return "<b>Player1(RED)</b> has to <b>REMOVE</b> a token";
			case REMOVING_PLAYER2: return "<b>Player2(BLUE)</b> has to <b>REMOVE</b> a token";
			case WON_PLAYER1: return "<b>Player1(RED)</b> has <b>WON</b> the game";
			case WON_PLAYER2: return "<b>Player2(BLUE)</b> has <b>WON</b> the game";
			default: throw new IllegalArgumentException();
		}
	}
}
