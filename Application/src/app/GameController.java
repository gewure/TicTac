package app;

import Gateway.GameFacade;
import Gateway.GamePosition;
import Gateway.GameToken;
import Logic.Phase;
import Logic.Token;
import at.fhv.itb6.arp.ARFacade;
import at.fhv.itb6.arp.inputInterface.InputConfiguration;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by simon_000 on 30/04/2016.
 */
public class GameController extends Observable implements Runnable{

    private  GameFacade _gameFacade;
    private CursorPositionToGamePositionMapper _mapping;
    private boolean _gameOver;
    public GameController(InputConfiguration inputConfiguration, GameType gameType) {
        _gameFacade = new GameFacade();
        _mapping = new CursorPositionToGamePositionMapper();

        _gameFacade.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                gameStateChanged();
            }
        });

        ARFacade.getInstance().init(inputConfiguration);

        start();
    }

    private void start() {
        Thread gameLoopThread = new Thread(this);
        gameLoopThread.start();
    }

    private boolean _running;
    @Override
    public void run() {
        _running = true;
        while(_running) {
            actOnPhase(_gameFacade.getCurrentPhase());

            if(_gameFacade.getWonPlayer() != Token.NO_PLAYER) {
                _gameOver = true;
            }

            gameStateChanged();
        }
    }

    private void actOnPhase(Phase phase) {
        GamePosition src = readeInput();
        if(phase == Phase.MOVING_PLAYER1 || phase == Phase.MOVING_PLAYER2) {
            GamePosition dest = readeInput();
            _gameFacade.moveGameToken(src, dest);

        } else if(phase == Phase.REMOVING_PLAYER1 || phase == Phase.REMOVING_PLAYER2) {
            _gameFacade.removeGameToken(src);

        } else if(phase == Phase.PLACING_PLAYER1 || phase == Phase.PLACING_PLAYER2) {
            _gameFacade.placeGameToken(src);
        }
    }

    private GamePosition readeInput(){
        return _mapping.map(ARFacade.getInstance().getCursorPosition());
    }

    public Token getCurrentPlayer() {
        return _gameFacade.getCurrentPlayer();
    }

    public boolean getGameOver() {
        return _gameOver;
    }

    public List<GameToken> getPlayer1GameTokens() {
        return _gameFacade.getTokensPlayer1();
    }

    public List<GameToken> getPlayer2GameTokens() {
        return _gameFacade.getTokensPlayer2();
    }

    private void gameStateChanged() {
        this.setChanged();
        this.notifyObservers();
    }
}