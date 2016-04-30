package app;

import Gateway.GameGateway;
import Gateway.GameToken;
import Gateway.PlayerIdentifier;
import Logic.Token;
import at.fhv.itb6.arp.ARFacade;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by simon_000 on 30/04/2016.
 */
public class GameController extends Observable{

    public GameGateway _gameGateway;

    public GameController(Integer cameraID, GameType gameType) {
        ARFacade.getInstance().init(cameraID);

        if(gameType == GameType.vsAI) {
            _gameGateway = new GameGateway(Token.PLAYER_1, Token.NO_PLAYER);
        } else {
            _gameGateway = new GameGateway(Token.PLAYER_1, Token.PLAYER_2);
        }

        _gameGateway.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                gameStateChanged();
            }
        });
    }

    public PlayerIdentifier getCurrentPlayer() {
        return _gameGateway.getCurrentPlayer();
    }

    public List<GameToken> getPlayer1GameTokens() {
        return _gameGateway.getTokensPlayer1();
    }

    public List<GameToken> getPlayer2GameTokens() {
        return _gameGateway.getTokensPlayer2();
    }

    private void gameStateChanged() {
        this.hasChanged();
        this.notifyObservers();
    }


}
