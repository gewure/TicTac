package at.fhv.itb6.arp.shapdetection.shapes;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;

/**
 * Created by simon_000 on 30/03/2016.
 */
public class Triangle extends Polygon {
    public Triangle(Point[] points, MatOfPoint approximatio) {
        super(points, approximatio);
    }
}
