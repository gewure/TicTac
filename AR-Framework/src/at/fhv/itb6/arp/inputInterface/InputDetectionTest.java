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
public class InputDetectionTest{

    static Scalar textColor = new Scalar(0,100,0);
    static Scalar pointColor = new Scalar(150,255,150);
    static Size displaySize = new Size(800, 600);

    public static void main(String[] args) {
        InputDetectionTest idt = new InputDetectionTest();

        InputConfiguration ic = new InputConfiguration();
        ic.setHardwareId(0);
        InputDetection id = new InputDetection(ic);
        CameraInterface ci = new CameraInterface(0);
        Imshow imshow = new Imshow("Current Frame");

        while (true) {
            Mat frame = ci.readImage();

            //Detect board borders and correct perspective view
            Map<Class, List<Polygon>> detectedPolygons = ShapeDetection.detect(frame);
            Rectangle borderRect = null;
            try {
                borderRect = id.getBorderRect(detectedPolygons.get(Rectangle.class));
                frame = ShapeUtil.perspectiveCorrection(frame, borderRect, new Size(800, 500));


                //Detect marker position
                Map<Class, List<Polygon>> detectedPolygonsPostCorection = ShapeDetection.detect(frame);
                Point markerPos = id.getMarkerPos(detectedPolygonsPostCorection.get(Triangle.class), frame);

                //Calculate relative marker position
                Point relativeMarkerPos = id.calculateRelativeMarkerPos(frame.size(), markerPos);

                Imgproc.circle(frame, markerPos, 2, pointColor);
            } catch (GamebordersNotDetectedException e) {

            } catch (NoMarkerDetectedException e) {

            }

            imshow.showImage(frame);
        }
    }
}
