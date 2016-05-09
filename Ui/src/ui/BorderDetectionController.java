package ui;

import at.fhv.itb6.arp.inputInterface.InputConfiguration;
import at.fhv.itb6.arp.inputInterface.InputDetection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by Zopo on 05.05.2016.
 */
public class BorderDetectionController{

    @FXML
    Canvas borderImage;

    private InputConfiguration _inputConfiguration;
    private Stage _parent;

    public BorderDetectionController(InputConfiguration ic, Stage parent){
        _inputConfiguration = ic;
        _parent = parent;

        _parent.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                borderImage.setWidth(newValue.doubleValue());
                initialize();
            }
        });

        _parent.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                borderImage.setHeight(newValue.doubleValue());
                initialize();
            }
        });
    }

    public void initialize(){
        GraphicsContext graphics = borderImage.getGraphicsContext2D();
        graphics.setFill(Color.DARKGRAY);
        graphics.fillRect(0,0,borderImage.getWidth(),borderImage.getHeight());
/*
        GraphicsContext graphics = borderImage.getGraphicsContext2D();
        graphics.setLineWidth(5);
        graphics.strokeLine(0,0, borderImage.getWidth(), 0);
        graphics.strokeLine(borderImage.getWidth(),0, borderImage.getWidth(), borderImage.getHeight());
        graphics.strokeLine(borderImage.getWidth(), borderImage.getHeight(), 0, borderImage.getHeight());
        graphics.strokeLine(0, borderImage.getHeight(), 0,0);
*/
    }

    public void detectGameboard(){
        InputDetection id = new InputDetection(_inputConfiguration);
        id.detectGameboard();
    }
}
