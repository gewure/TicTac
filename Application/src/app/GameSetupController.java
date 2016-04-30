package app;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by simon_000 on 30/04/2016.
 */
public class GameSetupController {

    private IntegerProperty _cameraID;

    public GameSetupController() {
        _cameraID = new SimpleIntegerProperty();
    }

    //Todo return game controller
    public void start(Integer cameraID, GameType gameMode) {

    }
}
