package Gateway;

import Logic.Token;

/**
 * Created by simon_000 on 30/04/2016.
 */
public class GameToken {
    private Token _playerIdentifier;
    private GamePosition _gamePosition;

    public GameToken(Token playerIdentifier, GamePosition gamePosition) {
        _playerIdentifier = playerIdentifier;
        _gamePosition = gamePosition;
    }

    public Token getPlayerIdentifier(){
        return _playerIdentifier;
    }

    public GamePosition getGamePosition() {
        return _gamePosition;
    }

    public void setGamePosition(GamePosition gamePosition) {
        _gamePosition = gamePosition;
    }
}