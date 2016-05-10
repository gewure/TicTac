package at.fhv.itb6.arp.inputInterface;

import at.fhv.itb6.arp.hardwareinterface.CameraInterface;
import at.fhv.itb6.arp.inputInterface.CursorStatusListener;
import at.fhv.itb6.arp.inputInterface.InputAction;
import at.fhv.itb6.arp.inputInterface.InputConfiguration;
import at.fhv.itb6.arp.inputInterface.InputDetection;
import at.fhv.itb6.arp.inputInterface.exceptions.NoMarkerDetectedException;
import at.fhv.itb6.arp.shapdetection.shapes.ShapeUtil;
import org.opencv.core.*;

import java.awt.*;
import java.awt.Point;
import java.io.IOException;

/**
 * Created by Zopo on 09.05.2016.
 */
public class MouseInputSimulation extends InputDetection {
    public MouseInputSimulation(InputConfiguration inputConfiguration) {
        super(inputConfiguration);
    }

    @Override
    public void detectGameboard(){

    }

    @Override
    public InputAction getUserInput(CursorStatusListener callback) {
        int currentFrame = 0;
        int interruptionCount = 0;
        org.opencv.core.Point setMarkerPos = new org.opencv.core.Point(0, 0);

        while (currentFrame < getInputConfiguration().getConfirmationTime()) {
            currentFrame++;

            //Get pointer info
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            PointerInfo pi = MouseInfo.getPointerInfo();


            Point markerPos = pi.getLocation();
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

            org.opencv.core.Point relativeMarkerPos = new org.opencv.core.Point(markerPos.x / d.getWidth(), markerPos.y / d.getHeight());

            System.out.println(relativeMarkerPos.x + "|" + relativeMarkerPos.y + "\t" + currentFrame);

            //Check if the marker has moved
            if (isMarkerMoved(setMarkerPos, relativeMarkerPos)) {
                interruptionCount++;
                if (interruptionCount > getInputConfiguration().getInterruptionTolerance()) {
                    currentFrame = 0;
                    setMarkerPos = relativeMarkerPos;
                }
            }
            callback.cursorChangedEvent(setMarkerPos.x, setMarkerPos.y, (double) currentFrame / (double) getInputConfiguration().getConfirmationTime());
        }
        return new InputAction(setMarkerPos);
    }
}
