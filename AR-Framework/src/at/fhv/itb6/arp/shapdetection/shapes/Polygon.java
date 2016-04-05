package at.fhv.itb6.arp.shapdetection.shapes;

import org.opencv.core.*;
import java.util.Arrays;

/**
 * Created by simon_000 on 27/03/2016.
 */
public class Polygon {
    private Point[] points;
    private MatOfPoint approximation;

    public Polygon(Point[] points, MatOfPoint approximation) {
        this.points = points;
        this.approximation = approximation;
    }

    public Point[] getPoints() {
        return points;
    }

    protected MatOfPoint getApproximation() {
        return approximation;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + Arrays.toString(getPoints());
    }
}




