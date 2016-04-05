package at.fhv.itb6.arp.inputInterface.exceptions;

import at.fhv.itb6.arp.shapdetection.shapes.Polygon;
import at.fhv.itb6.arp.shapdetection.shapes.Rectangle;

import java.util.List;

/**
 * Created by Zopo on 29.03.2016.
 */
public class GamebordersNotDetectedException extends Exception{
    private List<Polygon> _detectedPolygons;

    public GamebordersNotDetectedException(List<Polygon> polygons) {
        _detectedPolygons = polygons;
    }

    public List<Polygon> getDetectedPolygons() {
        return _detectedPolygons;
    }
}
