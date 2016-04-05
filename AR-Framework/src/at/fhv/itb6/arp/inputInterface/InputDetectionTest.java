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
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.List;
import java.util.Map;

/**
 * Created by Zopo on 29.03.2016.
 */
public class InputDetectionTest {

    private static Scalar edgeColor = new Scalar(0,255,0);
    private static Size displaySize = new Size(800, 600);

    public static void main(String[] args) {
        InputDetection id = new InputDetection(new InputConfiguration());
        Imshow imshow = new Imshow("Current Frame");
        CameraInterface ci = new CameraInterface(0);

        while (true){
            Mat frame = ci.readImage();

            //Detect board borders and correct perspective view
            Map<Class, List<Polygon>> detectedPolygons = ShapeDetection.detect(frame);
            Rectangle borderRect = null;
            try {
                borderRect = id.getBorderRect(detectedPolygons.get(Rectangle.class));
                frame = ShapeUtil.perspectiveCorrection(frame, borderRect, new Size(800, 500));

                //Detect marker position
                Map<Class, List<Polygon>> detectedPolygonsPostCorection = ShapeDetection.detect(frame);
                Point markerPos = id.getMarkerPos(detectedPolygonsPostCorection.get(Triangle.class));

                //Calculate relative marker position
                Point relativeMarkerPos = id.calculateRelativeMarkerPos(frame.size(), markerPos);
                Imgproc.circle(frame, markerPos, 4, edgeColor);
                Imgproc.putText(frame, String.format("%3f | %3f", relativeMarkerPos.x, relativeMarkerPos.y), new Point(5,15), 0, 0.5, edgeColor);

            } catch (GamebordersNotDetectedException e) {
            } catch (NoMarkerDetectedException e) {
            }


            imshow.showImage(frame);
        }
    }
}
