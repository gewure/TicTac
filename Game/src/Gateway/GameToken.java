package Gateway;

/**
 * Created by simon_000 on 30/04/2016.
 */
public class GameToken {
    private PlayerIdentifier _playerIdentifier;
    private GamePosition _gamePosition;

    public GameToken(PlayerIdentifier playerIdentifier, GamePosition gamePosition) {
        _playerIdentifier = playerIdentifier;
        _gamePosition = gamePosition;
    }

    public PlayerIdentifier getPlayerIdentifier(){
        return _playerIdentifier;
    }

    public GamePosition getGamePosition() {
        return _gamePosition;
    }

    public void setGamePosition(GamePosition gamePosition) {
        _gamePosition = gamePosition;
    }
}
