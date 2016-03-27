package at.fhv.itb6.arp.shapdetection.shapes;

import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;

import java.util.List;

/**
 * Created by simon_000 on 27/03/2016.
 */
public class Rectangle extends Polygon {
    public Rectangle(Point[] contour) {
        super(contour);
    }
}
