package at.fhv.itb6.arp.inputInterface.exceptions;

import at.fhv.itb6.arp.shapdetection.shapes.Rectangle;

import java.util.List;

/**
 * Created by Zopo on 29.03.2016.
 */
public class GamebordersNotDetectedException extends Exception{
    private List<Rectangle> _detectedRectangles;

    public GamebordersNotDetectedException(List<Rectangle> rectangles) {
        _detectedRectangles = rectangles;
    }

    public List<Rectangle> getDetectedRectangles() {
        return _detectedRectangles;
    }
}
