package app;

import at.fhv.itb6.arp.ARFacade;

import java.util.List;

/**
 * Created by simon_000 on 30/04/2016.
 */
public class GameSetupController {

    public GameSetupController() {
    }

    public List<Integer> getCameraIDs() {

        return ARFacade.getCameraIDs();
    }

    //Todo return game controller
    public GameController start(Integer cameraID, GameType gameMode) {

        return new GameController(cameraID, gameMode);
    }
}
