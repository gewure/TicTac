package ui; /**
 * Created by simon_000 on 06/04/2016.
 */

import app.GameController;
import app.GameSetupController;
import app.GameType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class StartPageController implements IPanelClosable{

    private IPanelCloseHandler _panelCloseHandler;

    @FXML
    private ToggleGroup GameMode;

    @FXML
    private ComboBox<Integer> camDropDown;


    private GameSetupController _gameSetupController;
    private GameType _gameType;
    private Integer _cameraID;

    public StartPageController() {
        _gameSetupController = new GameSetupController();
    }

    @FXML
    public void initialize() {
        _gameType = GameType.vsPlayer;

        GameMode.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton radioButton = (RadioButton) newValue;

                if(radioButton.getText().equals("PVP")) {
                    _gameType = GameType.vsPlayer;
                } else  {
                    _gameType = GameType.vsAI;
                }
            }
        });

        camDropDown.getItems().addAll(_gameSetupController.getCameraIDs());
        camDropDown.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                _cameraID = newValue;
            }
        });

        camDropDown.valueProperty().set(_gameSetupController.getCameraIDs().get(0));
    }

    @FXML
    void startGameEventHandler(ActionEvent event) throws IOException {
        System.out.println("Start meeeeeeeeeeeeeeee!");

        GameController gameController = _gameSetupController.start(_cameraID, _gameType);
        _panelCloseHandler.closeNext(getClass().getResource("GamePage.fxml"), new GamePageController(gameController));
    }

    @Override
    public void setPanelCloseHandler(IPanelCloseHandler panelCloseHandler) {
        _panelCloseHandler = panelCloseHandler;
    }
}
