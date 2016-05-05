package at.fhv.itb6.arp.shapdetection.shapes;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.List;

/**
 * Created by simon_000 on 27/03/2016.
 */
public class Rectangle extends Polygon {

    public Rectangle(Point[] contour, MatOfPoint approximatio) {
        super(contour, approximatio);
    }

    public double getSurface() {
        Size rotatedRect = getApproximation().size();
        return rotatedRect.height * rotatedRect.width;
    }

    @Override
    public String toString() {
         return super.toString() + " Size: " + getSurface();
    }
}
