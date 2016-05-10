package Gateway;

import Logic.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by simon_000 on 30/04/2016.
 */
public class GameFacade extends Observable {

    private List<GameToken> _tokensPlayer1;
    private List<GameToken> _tokensPlayer2;
    private Token _currentPlayer;
    private Phase currentPhase;
    private Game game;
    private BiMap<GamePosition, Integer> positionMapping;
    private boolean _isPvp;
    private AIPlayer _aiPlayer;

    public GameFacade(boolean isPvp) {
        initializePositionMapping();
        _tokensPlayer1 = new ArrayList<>();
        _tokensPlayer2 = new ArrayList<>();

        game = new Game();
        LocalGame localGame = new LocalGame();
        _isPvp = isPvp;


        Player player1 = null;
        Player player2 = null;

        try {
            player1 = new HumanPlayer("Player1", Token.PLAYER_1, 9);
            if (isPvp){
                player2 = new HumanPlayer("Player2", Token.PLAYER_2, 9);
            } else {
                player2 = new MinimaxAIPlayer(Token.PLAYER_2, 9, 7);
                _aiPlayer = (AIPlayer) player2;
            }
        } catch (GameException e) {
            e.printStackTrace();
        }
        _currentPlayer = Token.PLAYER_1;
        setCurrentPhase(Phase.PLACING_PLAYER1);

        localGame.setPlayers(player1, player2);
    }

    public List<GameToken> getTokensPlayer1() {
        return _tokensPlayer1;
    }

    public List<GameToken> getTokensPlayer2() {
        return _tokensPlayer2;
    }

    public void getWonPlayer() throws GameException {
        if(game.getGameBoard().getNumberOfPiecesOfPlayer(_currentPlayer) < 3){
            if (_currentPlayer == Token.PLAYER_1 && game.getGameBoard().getUnplayedPiecesP1() == 0) {
                setCurrentPhase(Phase.WON_PLAYER2);
            } else if (_currentPlayer == Token.PLAYER_2 && game.getGameBoard().getUnplayedPiecesP2() == 0) {
                setCurrentPhase(Phase.WON_PLAYER1);
            }
        }
    }

    private void setCurrentPlayer(Token currentPlayer) {
        _currentPlayer = currentPlayer;
        gameStateChanged();
    }

    public Token getCurrentPlayer() {
        return _currentPlayer;
    }

    public boolean removeGameToken(GamePosition position){
        if (position == GamePosition.None){
            return false;
        }

        int pos = positionMapping.get(position);
        if(getCurrentPlayer().equals(Token.PLAYER_1)){
            try {
                if(game.positionHasPieceOfPlayer(pos, Token.PLAYER_2)){
                    game.removePiece(pos, Token.PLAYER_2);
                    setPartOfNextPlayerPhase(game);
                }else{
                    return false;
                }
            } catch (GameException e) {
                e.printStackTrace();
            }
        }else{
            try {
                if(game.positionHasPieceOfPlayer(pos, Token.PLAYER_1)){
                    game.removePiece(pos, Token.PLAYER_1);
                    setPartOfNextPlayerPhase(game);
                } else{
                    return false;
                }
            } catch (GameException e) {
                e.printStackTrace();
            }
        }
        try {
            getWonPlayer();
        } catch (GameException e) {
            e.printStackTrace();
        }

        gameStateChanged();
        return true;
    }

    //return false if the move was not legal
    public boolean moveGameToken(GamePosition origion, GamePosition desitination) {
        if (desitination == GamePosition.None || origion == GamePosition.None){
            return false;
        }

        int dest = positionMapping.get(desitination);
        int orig = positionMapping.get(origion);

        try {
            if(game.positionHasPieceOfPlayer(orig, getCurrentPlayer())) {
                if (game.positionIsAvailable(dest)) {
                    int move = game.movePieceFromTo(orig, dest, getCurrentPlayer());
                    if(move == 0) {
                        setNextPlayerPhase(dest, game);
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (GameException e) {
            e.printStackTrace();
        }

        gameStateChanged();
        return true;
    }

    //return false if the move was not legal
    public boolean placeGameToken( GamePosition desitination) {
        try {
            if (desitination == null || desitination == GamePosition.None){
                return false;
            }
            int dest = positionMapping.get(desitination);
            if(game.positionIsAvailable(dest)) {
                game.placePieceOfPlayer(dest, getCurrentPlayer());
                setNextPlayerPhase(dest, game);
            }else{
                return false;
            }
        } catch (GameException e) {
            e.printStackTrace();
        }

        gameStateChanged();
        return true;
    }

    private void switchPlayer() {
        if(getCurrentPlayer() == Token.PLAYER_1) {
            setCurrentPlayer(Token.PLAYER_2);
        } else {
            setCurrentPlayer(Token.PLAYER_1);
        }
    }

    private void setNextPlayerPhase(int dest, Game game){
        try {
            if (game.madeAMill(dest, getCurrentPlayer())){
				if(getCurrentPlayer().equals(Token.PLAYER_1)){
					setCurrentPhase(Phase.REMOVING_PLAYER1);
				}else {
                    setCurrentPhase(Phase.REMOVING_PLAYER2);
				}
			}else{
	            setPartOfNextPlayerPhase(game);
			}
        } catch (GameException e) {
            e.printStackTrace();
        }
    }

    private void setPartOfNextPlayerPhase(Game game) throws GameException{
            if(getCurrentPlayer().equals(Token.PLAYER_1)){
                if(game.getGameBoard().getUnplayedPiecesP1() > 0){
                    if(getCurrentPlayer().equals(Token.PLAYER_1)) {
                        setCurrentPhase(Phase.PLACING_PLAYER2);
                    }
                }else {
                    if (getCurrentPlayer().equals(Token.PLAYER_1) && game.getGameBoard().getUnplayedPiecesP2()==0) {
                        setCurrentPhase(Phase.MOVING_PLAYER2);
                    }else{
                        setCurrentPhase(Phase.PLACING_PLAYER2);
                    }
                }
            }else if(getCurrentPlayer().equals(Token.PLAYER_2)){
                    if(game.getGameBoard().getUnplayedPiecesP2() > 0){
                        if(getCurrentPlayer().equals(Token.PLAYER_2)) {
                            setCurrentPhase(Phase.PLACING_PLAYER1);
                        }
                    }else{
                        if(getCurrentPlayer().equals(Token.PLAYER_2)&& game.getGameBoard().getUnplayedPiecesP1()==0){
                            setCurrentPhase(Phase.MOVING_PLAYER1);
                        }else{
                            setCurrentPhase(Phase.PLACING_PLAYER1);
                        }
                    }
            }

        switchPlayer();
    }

    public void gameStateChanged() {
        _tokensPlayer1.clear();
        _tokensPlayer2.clear();
        for(Position pos : game.getGameBoard().getBoardPositions()) {
            if(pos.isOccupied()) {
                Token token = pos.getPlayerOccupyingIt();
                GamePosition gamePosition = positionMapping.getKey(pos.getPositionIndex());
                GameToken gameToken = new GameToken(token, gamePosition);

                if(token.equals(Token.PLAYER_1)) {
                    _tokensPlayer1.add(gameToken);
                } else {
                    _tokensPlayer2.add(gameToken);
                }
            }
        }

        this.setChanged();
        this.notifyObservers();
    }

    public Phase getCurrentPhase(){
        return currentPhase;
    }

    public void setCurrentPhase(Phase currentPhase){
        this.currentPhase = currentPhase;
    }

    public void initializePositionMapping(){
        positionMapping = new BiMap<>();

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
        positionMapping.put(GamePosition.Center1,11);
        positionMapping.put(GamePosition.Center2,15);
        positionMapping.put(GamePosition.Center3,16);
        positionMapping.put(GamePosition.Center4,17);
        positionMapping.put(GamePosition.Center5,12);
        positionMapping.put(GamePosition.Center6,8);
        positionMapping.put(GamePosition.Center7,7);

    }

    public boolean isPositionOccupiedByCurrentPlayer(GamePosition position){
        try {
            return game.positionHasPieceOfPlayer(positionMapping.get(position), _currentPlayer);
        } catch (GameException e) {
            return false;
        }
    }

    public void makeAiMove() throws GameException {
        if (getCurrentPhase() == Phase.MOVING_PLAYER2){
            Move m = _aiPlayer.getPieceMove(game.getGameBoard(), game.getCurrentGamePhase());
            moveGameToken(positionMapping.getKey(m.srcIndex), positionMapping.getKey(m.destIndex));
        } else if (getCurrentPhase() == Phase.PLACING_PLAYER2){
            int placeIndex = _aiPlayer.getIndexToPlacePiece(game.getGameBoard());
            placeGameToken(positionMapping.getKey(placeIndex));
        } else if (getCurrentPhase() == Phase.REMOVING_PLAYER2){
            int removeIndex = _aiPlayer.getIndexToRemovePieceOfOpponent(game.getGameBoard());
            removeGameToken(positionMapping.getKey(removeIndex));
        }
    }

    public int getUnusedTokensPlayer1(){
        return game.getGameBoard().getUnplayedPiecesP1();
    }

    public int getUnusedTokensPlayer2(){
        return game.getGameBoard().getUnplayedPiecesP2();
    }
}
