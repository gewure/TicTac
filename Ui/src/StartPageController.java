/**
 * Created by simon_000 on 06/04/2016.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

    @FXML
    void startGameEventHandler(ActionEvent event) throws IOException {
        System.out.println("Start meeeeeeeeeeeeeeee!");
        _panelCloseHandler.closeNext(getClass().getResource("GamePage.fxml"), new GamePageController());
    }

    @Override
    public void setPanelCloseHandler(IPanelCloseHandler panelCloseHandler) {
        _panelCloseHandler = panelCloseHandler;
    }
}
