package Gateway;

import Logic.Token;

import java.util.Observable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon_000 on 30/04/2016.
 */
public class GameGateway extends Observable {

    private List<GameToken> _tokensPlayer1;
    private List<GameToken> _tokensPlayer2;
    private PlayerIdentifier _currentPlayer;

    public GameGateway(Token player1, Token player2) {
        _tokensPlayer1 = new ArrayList<>();
        _tokensPlayer2 = new ArrayList<>();

        //Todo: dummy values remove
        _tokensPlayer1.add(new GameToken(PlayerIdentifier.Player1, GamePosition.Out0));
        _tokensPlayer1.add(new GameToken(PlayerIdentifier.Player2, GamePosition.Out1));
    }

    public List<GameToken> getTokensPlayer1() {
        return _tokensPlayer1;
    }

    public List<GameToken> getTokensPlayer2() {
        return _tokensPlayer2;
    }

    public void setCurrentPlayer(PlayerIdentifier currentPlayer) {
        _currentPlayer = currentPlayer;
        gameStateChanged();
    }

    public PlayerIdentifier getCurrentPlayer() {
        return _currentPlayer;
    }

    //returns false if setting of tokens is not legal anymore
    public boolean SetGameToken(GamePosition position) {
        //Todo add logic that communications with the ninemorisfiles

        //Todo: invoke gameStateChanged after the token was set
        return false;
    }

    //return false if the move was not legal
    public boolean MakeMove(GamePosition origion, GamePosition desitination) {
        //Todo add logic that communications with the ninemorisfiles

        //Todo: invoke gameStateChanged after the move was made
        return false;
    }

    public void gameStateChanged() {
        this.hasChanged();
        this.notifyObservers();
    }
}
