package ui; /**
 * Created by simon_000 on 06/04/2016.
 */

import app.GameSetupController;
import app.GameType;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class StartPageController implements IPanelClosable{

    private IPanelCloseHandler _panelCloseHandler;

    @FXML
    private ToggleGroup GameMode;

    @FXML
    private Label DetectedNotDetectedFlag;

    @FXML
    private ComboBox<?> camDropDown;

    @FXML
    private ImageView frame;

    private GameSetupController _gameSetupController;
    private GameType _gameType;
    private IntegerProperty _cameraID;

    public StartPageController() {
        _gameSetupController = new GameSetupController();
    }

    @FXML
    public void initialize() {
        _gameType = GameType.vsPlayer;

        GameMode.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                System.out.println(newValue);
            }
        });
    }

    @FXML
    void startGameEventHandler(ActionEvent event) throws IOException {
        System.out.println("Start meeeeeeeeeeeeeeee!");

        _gameSetupController.start(_cameraID.getValue(), _gameType);
        _panelCloseHandler.closeNext(getClass().getResource("ui/GamePage.fxml"), new GamePageController());
    }

    @Override
    public void setPanelCloseHandler(IPanelCloseHandler panelCloseHandler) {
        _panelCloseHandler = panelCloseHandler;
    }
}
