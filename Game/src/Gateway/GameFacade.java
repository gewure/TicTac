package Gateway;

import Logic.*;

import java.util.HashMap;
import java.util.Observable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon_000 on 30/04/2016.
 */
public class GameFacade extends Observable {

    private List<GameToken> _tokensPlayer1;
    private List<GameToken> _tokensPlayer2;
    private Token _currentPlayer;
    private Phase currentPhase;
    private Game game;
    private HashMap<GamePosition, Integer> positionMapping;

    public GameFacade(Token player1, Token player2) {
        _tokensPlayer1 = new ArrayList<>();
        _tokensPlayer2 = new ArrayList<>();

        game = new Game();
        LocalGame localGame = new LocalGame();

        HumanPlayer humanPlayer1 = null;
        HumanPlayer humanPlayer2 = null;

        try {
            humanPlayer1 = new HumanPlayer("Player1", Token.PLAYER_1, 9);
            humanPlayer2 = new HumanPlayer("Player2", Token.PLAYER_2, 9);
        } catch (GameException e) {
            e.printStackTrace();
        }

        localGame.setPlayers(humanPlayer1, humanPlayer2);

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

    public Token getWonPlayer() {
        //TODO: returns the player that hase won if the game ends
        return Token.NO_PLAYER;
    }

    private void setCurrentPlayer(Token currentPlayer) {
        _currentPlayer = currentPlayer;
        gameStateChanged();
    }

    public Token getCurrentPlayer() {
        return _currentPlayer;
    }

    //returns false if setting of tokens is not legal anymore
    public boolean setGameToken(GamePosition position) {
        //Todo add logic that communications with the ninemorisfiles

        //Todo: invoke gameStateChanged after the token was set
        gameStateChanged();
        return false;
    }


    public boolean removeGameToken(GamePosition position){

        gameStateChanged();
        return false;
    }

    //return false if the move was not legal
    public boolean moveGameToken(GamePosition desitination) {



        //Todo add logic that communications with the ninemorisfiles

        //Todo: invoke gameStateChanged after the move was made
        gameStateChanged();
        return false;
    }

    //return false if the move was not legal
    public boolean placeGameToken(GamePosition origion, GamePosition desitination) {
        try {
            int dest = positionMapping.get(desitination);
            if(game.positionIsAvailable(dest)) {
                game.placePieceOfPlayer(dest, getCurrentPlayer());
                game.madeAMill(dest, getCurrentPlayer());
            }else{
                return false;
            }
        } catch (GameException e) {
            e.printStackTrace();
        }

        //Todo add logic that communications with the ninemorisfiles

        //Todo: invoke gameStateChanged after the move was made
        gameStateChanged();
        return true;
    }

    public void gameStateChanged() {
        this.hasChanged();
        this.notifyObservers();
    }

    public Phase getCurrentPhase(){
        return currentPhase;
    }

    public void setCurrentPhase(Phase currentPhase){
        this.currentPhase = currentPhase;
    }
}
