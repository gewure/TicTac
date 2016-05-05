package Gateway;

import Logic.Token;

import java.util.Observable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon_000 on 30/04/2016.
 */
public class GameFacade extends Observable {

    private List<GameToken> _tokensPlayer1;
    private List<GameToken> _tokensPlayer2;
    private PlayerIdentifier _currentPlayer;

    public GameFacade(Token player1, Token player2) {
        _tokensPlayer1 = new ArrayList<>();
        _tokensPlayer2 = new ArrayList<>();

        //Todo: dummy values remove
       /* _tokensPlayer1.add(new GameToken(PlayerIdentifier.Player1, GamePosition.Out0));
        _tokensPlayer1.add(new GameToken(PlayerIdentifier.Player1, GamePosition.Out1));
        _tokensPlayer1.add(new GameToken(PlayerIdentifier.Player1, GamePosition.Out2));
        _tokensPlayer1.add(new GameToken(PlayerIdentifier.Player1, GamePosition.Out3));
        _tokensPlayer1.add(new GameToken(PlayerIdentifier.Player1, GamePosition.Out4));
        _tokensPlayer1.add(new GameToken(PlayerIdentifier.Player1, GamePosition.Out5));
        _tokensPlayer1.add(new GameToken(PlayerIdentifier.Player1, GamePosition.Out6));
        _tokensPlayer1.add(new GameToken(PlayerIdentifier.Player1, GamePosition.Out7));

        _tokensPlayer2.add(new GameToken(PlayerIdentifier.Player2, GamePosition.Middle0));
        _tokensPlayer2.add(new GameToken(PlayerIdentifier.Player2, GamePosition.Middle1));
        _tokensPlayer2.add(new GameToken(PlayerIdentifier.Player2, GamePosition.Middle2));
        _tokensPlayer2.add(new GameToken(PlayerIdentifier.Player2, GamePosition.Middle3));
        _tokensPlayer2.add(new GameToken(PlayerIdentifier.Player2, GamePosition.Middle4));
        _tokensPlayer2.add(new GameToken(PlayerIdentifier.Player2, GamePosition.Middle5));
        _tokensPlayer2.add(new GameToken(PlayerIdentifier.Player2, GamePosition.Middle6));
        _tokensPlayer2.add(new GameToken(PlayerIdentifier.Player2, GamePosition.Middle7));

        _tokensPlayer1.add(new GameToken(PlayerIdentifier.Player1, GamePosition.Center0));
        _tokensPlayer1.add(new GameToken(PlayerIdentifier.Player1, GamePosition.Center1));
        _tokensPlayer1.add(new GameToken(PlayerIdentifier.Player1, GamePosition.Center2));
        _tokensPlayer1.add(new GameToken(PlayerIdentifier.Player1, GamePosition.Center2));
        _tokensPlayer1.add(new GameToken(PlayerIdentifier.Player1, GamePosition.Center3));
        _tokensPlayer1.add(new GameToken(PlayerIdentifier.Player1, GamePosition.Center4));
        _tokensPlayer1.add(new GameToken(PlayerIdentifier.Player1, GamePosition.Center5));
        _tokensPlayer1.add(new GameToken(PlayerIdentifier.Player1, GamePosition.Center6));
        _tokensPlayer1.add(new GameToken(PlayerIdentifier.Player1, GamePosition.Center7));*/
    }

    public List<GameToken> getTokensPlayer1() {
        return _tokensPlayer1;
    }

    public List<GameToken> getTokensPlayer2() {
        return _tokensPlayer2;
    }

    public PlayerIdentifier getWonPlayer() {
        //TODO: returns the player that hase won if the game ends
        return PlayerIdentifier.Non;
    }

    private void setCurrentPlayer(PlayerIdentifier currentPlayer) {
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
        gameStateChanged();
        return false;
    }

    //return false if the move was not legal
    public boolean MakeMove(GamePosition origion, GamePosition desitination) {
        //Todo add logic that communications with the ninemorisfiles

        //Todo: invoke gameStateChanged after the move was made
        gameStateChanged();
        return false;
    }

    public void gameStateChanged() {
        this.hasChanged();
        this.notifyObservers();
    }
}
