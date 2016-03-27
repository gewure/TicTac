package at.fhv.itb6.arp.shapdetection;

import at.fhv.itb6.arp.shapdetection.shapes.Polygon;
import at.fhv.itb6.arp.shapdetection.shapes.Rectangle;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by simon_000 on 27/03/2016.
 */
public final class ShapeDetection {
    private ShapeDetection() {

    }

    private static Size blurKernel = new Size(3, 3);

    private static double thresholdA = 100;
    private static double thresholdB = 200;

    public static List<Polygon> detect(Mat img) {
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

        List<Polygon> polygons = new ArrayList<>();
        for(int i = 0; i < contours.size(); ++i) {

            MatOfPoint2f approximation = new MatOfPoint2f();

            //approximate contours
            Imgproc.approxPolyDP(new MatOfPoint2f((contours.get(i).toArray())), approximation, Imgproc.arcLength(new MatOfPoint2f(contours.get(i).toArray()), true) * 0.02, true);

            //todo abstract
            if(approximation.toList().size() == 4) {
                System.out.println(Arrays.toString(approximation.toArray()));
                Polygon polygon = new Rectangle(approximation.toArray());
                polygons.add(polygon);
            } else {
                //System.out.println("No polygon factory for " + approximation.toList().size() + " found!");
            }

            approximation.release();
        }

        return polygons;
    }

}
