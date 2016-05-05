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
        initializePositionMapping();
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
    public boolean moveGameToken(GamePosition origion, GamePosition desitination) {



        //Todo add logic that communications with the ninemorisfiles

        //Todo: invoke gameStateChanged after the move was made
        gameStateChanged();
        return false;
    }

    //return false if the move was not legal
    public boolean placeGameToken( GamePosition desitination) {
        try {
            int dest = positionMapping.get(desitination);
            if(game.positionIsAvailable(dest)) {
                game.placePieceOfPlayer(dest, getCurrentPlayer());
                if(game.madeAMill(dest, getCurrentPlayer())){
                    if(getCurrentPlayer().equals(Token.PLAYER_1)){
                        setCurrentPhase(Phase.REMOVING_PLAYER1);
                    }else{
                        setCurrentPhase(Phase.REMOVING_PLAYER2);
                    }
                }else{
                    //TODO getNumberOfPiecesOfPlayer
                    if(game.getGameBoard().getNumberOfPiecesOfPlayer(getCurrentPlayer())< 9){
                        if(getCurrentPlayer().equals(Token.PLAYER_1)){
                            setCurrentPhase(Phase.PLACING_PLAYER2);
                        }else{
                            setCurrentPhase(Phase.PLACING_PLAYER1);
                        }
                    }else{
                        if(getCurrentPlayer().equals(Token.PLAYER_1)){
                            setCurrentPhase(Phase.MOVING_PLAYER2);
                        }else{
                            setCurrentPhase(Phase.MOVING_PLAYER1);
                        }
                    }
                }
            }else{
                return false;
            }
        } catch (GameException e) {
            e.printStackTrace();
        }

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

    public void initializePositionMapping(){
        positionMapping = new HashMap<>();

        positionMapping.put(GamePosition.Out0, 0);
        positionMapping.put(GamePosition.Out1, 9);
        positionMapping.put(GamePosition.Out2, 21);
        positionMapping.put(GamePosition.Out3, 22);
        positionMapping.put(GamePosition.Out4, 23);
        positionMapping.put(GamePosition.Out5, 14);
        positionMapping.put(GamePosition.Out6, 2);
        positionMapping.put(GamePosition.Out7, 1);

        positionMapping.put(GamePosition.Middle0,3);
        positionMapping.put(GamePosition.Middle1,10);
        positionMapping.put(GamePosition.Middle2,18);
        positionMapping.put(GamePosition.Middle3,19);
        positionMapping.put(GamePosition.Middle4,20);
        positionMapping.put(GamePosition.Middle5,13);
        positionMapping.put(GamePosition.Middle6,5);
        positionMapping.put(GamePosition.Middle7,4);

        positionMapping.put(GamePosition.Center0,6);
        positionMapping.put(GamePosition.Center0,11);
        positionMapping.put(GamePosition.Center0,15);
        positionMapping.put(GamePosition.Center0,16);
        positionMapping.put(GamePosition.Center0,17);
        positionMapping.put(GamePosition.Center0,12);
        positionMapping.put(GamePosition.Center0,8);
        positionMapping.put(GamePosition.Center0,7);

    }

}
