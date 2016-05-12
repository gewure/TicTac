package ui; /**
 * Created by simon_000 on 06/04/2016.
 */

import app.GameController;
import app.GameSetupController;
import app.GameType;
import at.fhv.itb6.arp.hardwareinterface.CameraInterface;
import at.fhv.itb6.arp.inputInterface.InputConfiguration;
import at.fhv.itb6.arp.shapdetection.shapes.Rectangle;
import at.fhv.itb6.arp.shapdetection.shapes.ShapeUtil;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class StartPageController implements IPanelClosable{

    private IPanelCloseHandler _panelCloseHandler;
    private Stage _parent;

    @FXML
    private CheckBox chkMouse;

    @FXML
    private ToggleGroup GameMode;

    @FXML
    private ComboBox<Integer> camDropDown;

    @FXML
    private Canvas imageCanvas;

    @FXML
    private Canvas rawImageCanvas;


    private GameSetupController _gameSetupController;
    private GameType _gameType;
    private Integer _cameraID;
    private InputConfiguration _inputConfiguration;

    public StartPageController(Stage parent) {
        _parent = parent;
        _gameSetupController = new GameSetupController();
    }

    @FXML
    public void initialize() {
        _inputConfiguration = new InputConfiguration();
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
                _inputConfiguration.setHardwareId(_cameraID);
            }
        });

        camDropDown.valueProperty().set(_gameSetupController.getCameraIDs().get(0));
    }

    @FXML
    void startGameEventHandler(ActionEvent event) throws IOException {
        System.out.println("Start meeeeeeeeeeeeeeee!");

        _inputConfiguration.setUseMouseSimulation(chkMouse.isSelected());

        GameController gameController = _gameSetupController.start(_inputConfiguration, _gameType);
        GamePageController pageController = new GamePageController(gameController, _parent);
        _panelCloseHandler.closeNext(getClass().getResource("GamePage.fxml"), pageController);

    }

    @FXML
    void detectBorder(ActionEvent event) throws IOException {

        /*BorderDetectionController bdc = new BorderDetectionController(_inputConfiguration);
        _panelCloseHandler.closeNext(getClass().getResource("BorderDetection.fxml"), bdc);
        bdc.getBorder();*/
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                final Stage popup = new Stage();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("BorderDetection.fxml"));
                BorderDetectionController bdc = new BorderDetectionController(_inputConfiguration, popup);
                loader.setController(bdc);
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bdc.initialize();
                popup.setFullScreen(true);
                Scene borderDetectionScene = new Scene(root);
                popup.setScene(borderDetectionScene);

                popup.show();
                bdc.detectGameboard();
                popup.close();

                CameraInterface ic = new CameraInterface(_cameraID);
                Mat image = ic.readImage();
                Mat gameboard = ShapeUtil.perspectiveCorrection(image, _inputConfiguration.getGameboardRectangle(), new Size(imageCanvas.getWidth(),imageCanvas.getHeight()));

                Imgproc.resize(image, image, new Size(rawImageCanvas.getWidth(), rawImageCanvas.getHeight()));

                try {
                    ic.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MatOfByte byteMat = new MatOfByte();
                Imgcodecs.imencode(".bmp", gameboard, byteMat);
                Image asImage = new Image(new ByteArrayInputStream(byteMat.toArray()));

                MatOfByte byteMatRaw = new MatOfByte();
                Imgcodecs.imencode(".bmp", image, byteMat);
                Image rawAsImage = new Image(new ByteArrayInputStream(byteMat.toArray()));

                image.release();
                gameboard.release();

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        imageCanvas.getGraphicsContext2D().drawImage(asImage, 0, 0);
                        rawImageCanvas.getGraphicsContext2D().drawImage(rawAsImage, 0, 0);
                    }
                });
            }
        });
    }

    @Override
    public void setPanelCloseHandler(IPanelCloseHandler panelCloseHandler) {
        _panelCloseHandler = panelCloseHandler;
    }
}
