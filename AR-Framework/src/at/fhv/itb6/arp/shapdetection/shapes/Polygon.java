package at.fhv.itb6.arp.shapdetection.shapes;

import org.opencv.core.*;
import java.util.Arrays;

/**
 * Created by simon_000 on 27/03/2016.
 */
public class Polygon {
    private Point[] points;

    public Polygon(Point[] points) {
        this.points = points;
    }

    public Point[] getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + Arrays.toString(getPoints());
    }
}




