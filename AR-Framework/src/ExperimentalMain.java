import at.fhv.itb6.arp.hardwareinterface.CameraInterface;
import at.fhv.itb6.arp.shapdetection.ShapeDetection;
import at.fhv.itb6.arp.shapdetection.shapes.Polygon;
import at.fhv.itb6.arp.shapdetection.shapes.Rectangle;
import com.atul.JavaOpenCV.Imshow.Imshow;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by simon_000 on 27/03/2016.
 */
public class ExperimentalMain {

    private static Scalar edgeColor = new Scalar(0,0,255);

    public static void main(String[] args) throws IOException {

        CameraInterface cam = new CameraInterface(0);
        Imshow imshow = new Imshow("Current Frame");

        while(true) {
            Mat frame = cam.readImage();

            Map<Class, List<Polygon>> polygons = ShapeDetection.detect(frame);
            List<Polygon> rectangles = polygons.get(Rectangle.class);
            for(int polygonCount = 0; polygonCount < rectangles.size(); ++polygonCount) {

                Point[] points = rectangles.get(polygonCount).getPoints();
                System.out.println(rectangles.get(polygonCount).toString());
                for(int contourCount = 0; contourCount < points.length - 1; ++contourCount) {
                    Imgproc.line(frame, points[contourCount], points[contourCount + 1], edgeColor);
                }
            }

            imshow.showImage(frame);
        }
    }
}
