package at.fhv.itb6.arp.inputInterface;

import at.fhv.itb6.arp.hardwareinterface.CameraInterface;
import at.fhv.itb6.arp.inputInterface.exceptions.GamebordersNotDetectedException;
import at.fhv.itb6.arp.inputInterface.exceptions.NoMarkerDetectedException;
import at.fhv.itb6.arp.shapdetection.ShapeDetection;
import at.fhv.itb6.arp.shapdetection.shapes.Polygon;
import at.fhv.itb6.arp.shapdetection.shapes.Rectangle;
import at.fhv.itb6.arp.shapdetection.shapes.ShapeUtil;
import at.fhv.itb6.arp.shapdetection.shapes.Triangle;
import com.atul.JavaOpenCV.Imshow.Imshow;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Zopo on 29.03.2016.
 */
public class InputDetectionTest {

    private static Scalar textColor = new Scalar(0,100,0);
    private static Scalar pointColor = new Scalar(150,255,150);
    private static Size displaySize = new Size(800, 600);

    public static void main(String[] args) {
        InputConfiguration ic = new InputConfiguration();
        InputDetection id = new InputDetection(ic);
        Imshow imshow = new Imshow("Current Frame");
        CameraInterface ci = new CameraInterface(1);
    }

}
