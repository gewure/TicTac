package app;

import Gateway.GameFacade;
import Gateway.GamePosition;
import Gateway.GameToken;
import Logic.Token;
import at.fhv.itb6.arp.ARFacade;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by simon_000 on 30/04/2016.
 */
public class GameController extends Observable implements Runnable{

    public GameFacade _gameFacade;

    public GameController(Integer cameraID, GameType gameType) {
        _gameFacade = new GameFacade();
        _gameFacade.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                gameStateChanged();
            }
        });

        ARFacade.getInstance().init(cameraID, _gameFacade);

        start();

        _gameFacade.placeGameToken(GamePosition.Center0);

        _gameFacade.placeGameToken(GamePosition.Center1);

        _gameFacade.placeGameToken(GamePosition.Center2);
    }

    private void start() {
        //start thread that runs the game
        Thread gameLoopThread = new Thread(this);

        gameLoopThread.start();
    }

    private boolean _running;
    @Override
    public void run() {
        _running = false; //TODO: set to true
        while(_running) {
            //read cursor input

            //write it into the gamegateway

            gameStateChanged();

            // if player won stop loop
        }
    }

    public Token getCurrentPlayer() {
        return _gameFacade.getCurrentPlayer();
    }

    public List<GameToken> getPlayer1GameTokens() {
        return _gameFacade.getTokensPlayer1();
    }

    public List<GameToken> getPlayer2GameTokens() {
        return _gameFacade.getTokensPlayer2();
    }

    private void gameStateChanged() {
        this.hasChanged();
        this.notifyObservers();
    }


}
