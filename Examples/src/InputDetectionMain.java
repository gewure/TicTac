import at.fhv.itb6.arp.hardwareinterface.CameraInterface;
import at.fhv.itb6.arp.inputInterface.InputAction;
import at.fhv.itb6.arp.inputInterface.InputConfiguration;
import at.fhv.itb6.arp.inputInterface.InputDetection;
import at.fhv.itb6.arp.inputInterface.exceptions.GamebordersNotDetectedException;
import at.fhv.itb6.arp.inputInterface.exceptions.NoMarkerDetectedException;
import at.fhv.itb6.arp.shapdetection.ShapeDetection;
import at.fhv.itb6.arp.shapdetection.shapes.Polygon;
import com.atul.JavaOpenCV.Imshow.Imshow;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.List;

/**
 * Created by Zopo on 29.03.2016.
 */
public class InputDetectionMain {

    private static Scalar edgeColor = new Scalar(255,0,0);

    public static void main(String[] args) {
        InputDetection id = new InputDetection(new InputConfiguration());
        Imshow imshow = new Imshow("Current Frame");
        CameraInterface ci = new CameraInterface(0);

        while (true){
            Mat frame = ci.readImage();

            try {
                //Detect maker and border positions
                List<Polygon> detectedPolygons = ShapeDetection.detect(frame);

                Point[] borderPoints = id.getBorderCoordinates(detectedPolygons);

                Imgproc.line(frame, borderPoints[0], borderPoints[1], edgeColor);
                Imgproc.line(frame, borderPoints[0], borderPoints[2], edgeColor);
                Imgproc.line(frame, borderPoints[2], borderPoints[3], edgeColor);
                Imgproc.line(frame, borderPoints[1], borderPoints[3], edgeColor);

                Imgproc.putText(frame, "BL", borderPoints[0], 0, 1, edgeColor);
                Imgproc.putText(frame, "BR", borderPoints[1], 0, 1, edgeColor);
                Imgproc.putText(frame, "TL", borderPoints[2], 0, 1, edgeColor);
                Imgproc.putText(frame, "TR", borderPoints[3], 0, 1, edgeColor);

                Point markerPos = id.getMarkerPos(detectedPolygons);
                Imgproc.circle(frame, markerPos, 5, edgeColor);

                //Calculate relative marker position
                Point relativeMarkerPos = id.calculateRelativeMarkerPos(borderPoints, markerPos);

            } catch (GamebordersNotDetectedException e) {
                System.out.println("Gameborders not detected (" + e.getDetectedRectangles().size() +"/4)");
            } catch (NoMarkerDetectedException e) {
                System.out.println("Marker not detected");
            }

            imshow.showImage(frame);
        }
    }
}
