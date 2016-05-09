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

    static Scalar textColor = new Scalar(0, 100, 0);
    static Scalar pointColor = new Scalar(255, 150, 150);

    public static void main(String[] args) {
        InputConfiguration ic = new InputConfiguration();
        ic.setHardwareId(0);
        ic.setCameraPosition(InputConfiguration.CameraPosition.BOTTOM);
        InputDetection id = new InputDetection(ic);
        id.detectGameboard();

        CameraInterface ci = new CameraInterface(ic.getHardwareId());
        Imshow imshow = new Imshow("Current Frame");

        while (true) {

            //Get camera image
            Mat frame = ci.readImage();
            Mat correctedImage = new Mat(1, 1, 0);
            Mat colorOnly = new Mat(1,1,0);

            //Detect board borders and correct perspective view
            Map<Class, List<Polygon>> detectedPolygons = ShapeDetection.detect(frame);
            Rectangle borderRect = null;
            try {
                correctedImage = ShapeUtil.perspectiveCorrection(frame, ic.getGameboardRectangle(), new Size(1000, 1000));
                for (int i = 0; i < ic.getCameraPosition(); i++) {
                    correctedImage = id.rotateRight(correctedImage);
                }

                Scalar minCol = ic.getMinCol();
                Scalar maxCol = ic.getMaxCol();

                colorOnly = new Mat();
                Core.inRange(frame, minCol, maxCol, colorOnly);
                Imgproc.erode(colorOnly, colorOnly, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(4,4)));
                //Imgproc.erode(colorOnly, colorOnly, Imgproc.getGaussianKernel(10, 1));


                //Imgproc.filter2D(colorOnly, colorOnly, 0, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(100,100)));

                //Detect marker position
                Point markerPos = id.getMarkerPos(correctedImage);

                //Calculate relative marker position
                Point relativeMarkerPos = id.calculateRelativeMarkerPos(correctedImage.size(), markerPos);

                Point correctedMarkerPos = new Point(relativeMarkerPos.x * correctedImage.width(), relativeMarkerPos.y * correctedImage.height());
                Imgproc.circle(correctedImage, correctedMarkerPos, 3, pointColor);
            } catch (NoMarkerDetectedException e) {
            }

            imshow.showImage(correctedImage);
        }

    }
}
