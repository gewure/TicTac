package at.fhv.itb6.arp.shapdetection;

import at.fhv.itb6.arp.shapdetection.shapes.Polygon;
import at.fhv.itb6.arp.shapdetection.shapes.Rectangle;
import at.fhv.itb6.arp.shapdetection.shapes.Triangle;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import java.util.*;

/**
 * Created by simon_000 on 27/03/2016.
 */
public final class ShapeDetection {
    private ShapeDetection() {

    }

    private static Size blurKernel = new Size(3, 3);

    private static double thresholdA = 100;
    private static double thresholdB = 200;
    private static double sensivity = 9;

    public static Map<Class, List<Polygon>> detect(Mat img) {
        //preprocess
        Mat processedImg = new Mat();
        Imgproc.cvtColor(img, processedImg, Imgproc.COLOR_BGR2GRAY); //convert to grayscale image
        Imgproc.blur(processedImg, processedImg, blurKernel); // blur

        //detect contours
        List<MatOfPoint> contours = new ArrayList<>();

        Mat cannyOutput = new Mat();
        Imgproc.Canny(processedImg, cannyOutput, thresholdA, thresholdB); //highlight edges witch canny detection
        processedImg.release();

        Imgproc.findContours(cannyOutput, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        cannyOutput.release();


        Map<Class, List<Polygon>> polygones = new HashMap<>();
        polygones.put(Triangle.class, new LinkedList<>());
        polygones.put(Rectangle.class, new LinkedList<>());
        polygones.put(Polygon.class, new LinkedList<>());

        for(int i = 0; i < contours.size(); ++i) {

            MatOfPoint2f approximation = new MatOfPoint2f();

            //approximate contours
            Imgproc.approxPolyDP(new MatOfPoint2f((contours.get(i).toArray())), approximation, Imgproc.arcLength(new MatOfPoint2f(contours.get(i).toArray()), true) * 0.02, true);

            approximation = removeDuplicatePoints(approximation);

            if(approximation.toList().size() == 4) {

                if(!polygones.containsKey(Rectangle.class)) {
                    polygones.put(Rectangle.class, new ArrayList<>());
                }

                List<Polygon> rectangles = polygones.get(Rectangle.class);
                rectangles.add((new Rectangle(approximation.toArray(), contours.get(i))));

            } else if(approximation.toList().size() == 3) {
                if(!polygones.containsKey(Triangle.class)) {
                    polygones.put(Triangle.class, new ArrayList<>());
                }

                List<Polygon> triangles = polygones.get(Triangle.class);
                triangles.add((new Triangle(approximation.toArray(), contours.get(i))));

            }

            approximation.release();
        }

        return polygones;
    }

    private static MatOfPoint2f removeDuplicatePoints(MatOfPoint2f approximation){
        List<Point> points = new LinkedList<>();

        for (Point point : approximation.toList()){
            boolean isDuplicate = false;
            for (Point lPoint : points){
                if (Math.abs(point.x - lPoint.x) < sensivity && Math.abs(point.y - lPoint.y) < sensivity){
                    isDuplicate = true;
                }
            }
            if (!isDuplicate){
                points.add(point);
            }
        }

        Point[] pointsArray = points.toArray(new Point[points.size()]);
        MatOfPoint2f newApproximation = new MatOfPoint2f(pointsArray);

        return newApproximation;
    }

}
