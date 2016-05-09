package app;

import at.fhv.itb6.arp.ARFacade;
import at.fhv.itb6.arp.inputInterface.InputConfiguration;
import jdk.internal.util.xml.impl.Input;

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
    public GameController start(InputConfiguration inputConfiguration, GameType gameMode) {

        return new GameController(inputConfiguration, gameMode);
    }
}
