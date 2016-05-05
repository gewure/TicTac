package ui;

import at.fhv.itb6.arp.inputInterface.InputConfiguration;
import at.fhv.itb6.arp.inputInterface.InputDetection;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by Zopo on 05.05.2016.
 */
public class BorderDetectionController{

    @FXML
    Canvas borderImage;

    private InputConfiguration _inputConfiguration;

    public BorderDetectionController(InputConfiguration ic){
        _inputConfiguration = ic;
    }

    public void initialize(){
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
