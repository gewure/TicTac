package at.fhv.itb6.arp.shapdetection.shapes;

import org.opencv.core.Point;

/**
 * Created by Zopo on 29.03.2016.
 */
public class ShapeUtil {
    public static Point getCenter(Polygon polygon){
        double totalX = 0;
        double totalY = 0;

        for (Point p : polygon.getPoints()){
            totalX += p.x;
            totalY += p.y;
        }

        int numberOfPoints = polygon.getPoints().length;

        return new Point(   totalX / numberOfPoints,
                            totalY / numberOfPoints);
    }
}
