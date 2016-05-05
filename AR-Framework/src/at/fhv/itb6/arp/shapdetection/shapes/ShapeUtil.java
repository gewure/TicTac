package at.fhv.itb6.arp.shapdetection.shapes;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Zopo on 29.03.2016.
 */
public class ShapeUtil {
    public static Point getCenter(Polygon polygon){
        double totalX = 0;
        double totalY = 0;

        for (Point p : polygon.getPoints()){
            totalX += p.x;
            totalY += p.y;
        }

        int numberOfPoints = polygon.getPoints().length;

        return new Point(   totalX / numberOfPoints,
                            totalY / numberOfPoints);
    }

    public static Mat perspectiveCorrection(Mat distortedImage, Rectangle distortedRectangle, Size goalSize){
        Point centerPoint = getCenter(distortedRectangle);

        Point bl = distortedRectangle.getPoints()[0];
        Point br = distortedRectangle.getPoints()[1];
        Point tl = distortedRectangle.getPoints()[2];
        Point tr = distortedRectangle.getPoints()[3];

        List<Point> topPoints = new LinkedList<>();
        List<Point> bottomPoints = new LinkedList<>();

        for (Point p : distortedRectangle.getPoints()){
            if (p.y < centerPoint.y){
                topPoints.add(p);
            } else {
                bottomPoints.add(p);
            }
        }

        if (topPoints.size() == 2 && bottomPoints.size() == 2){
            tl = topPoints.get(0).x > topPoints.get(1).x ? topPoints.get(1) : topPoints.get(0);
            tr = topPoints.get(0).x > topPoints.get(1).x ? topPoints.get(0) : topPoints.get(1);
            bl = bottomPoints.get(0).x > bottomPoints.get(1).x ? bottomPoints.get(1) : bottomPoints.get(0);
            br = bottomPoints.get(0).x > bottomPoints.get(1).x ? bottomPoints.get(0) : bottomPoints.get(1);
        }

        List<Point> rectCorners = new LinkedList<>();
        rectCorners.add(tl);
        rectCorners.add(tr);
        rectCorners.add(br);
        rectCorners.add(bl);

        List<Point> goalCoord = new LinkedList<>();
        goalCoord.add(new Point(0, 0));
        goalCoord.add(new Point(goalSize.width, 0));
        goalCoord.add(new Point(goalSize.width, goalSize.height));
        goalCoord.add(new Point(0, goalSize.height));

        Mat rectCornersMat = Converters.vector_Point2f_to_Mat(rectCorners);
        Mat goalCoordMat = Converters.vector_Point2f_to_Mat(goalCoord);

        Mat transformation = Imgproc.getPerspectiveTransform(rectCornersMat, goalCoordMat);

        Mat processedImage = new Mat();
        Imgproc.warpPerspective(distortedImage, processedImage, transformation, goalSize);

        return processedImage;
    }

    public static Scalar getAverageColor(Mat image, Polygon polygon){
        Mat mask = new Mat(image.rows(), image.cols(), 0);
        List<MatOfPoint> points = new LinkedList<>();
        points.add(new MatOfPoint(polygon.getPoints()));
        Imgproc.fillPoly(mask, points, new Scalar(255));

        return Core.mean(image, mask);
    }
}
