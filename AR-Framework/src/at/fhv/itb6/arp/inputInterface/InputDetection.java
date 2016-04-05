package at.fhv.itb6.arp.inputInterface;

import at.fhv.itb6.arp.hardwareinterface.CameraInterface;
import at.fhv.itb6.arp.inputInterface.exceptions.GamebordersNotDetectedException;
import at.fhv.itb6.arp.inputInterface.exceptions.NoMarkerDetectedException;
import at.fhv.itb6.arp.shapdetection.ShapeDetection;
import at.fhv.itb6.arp.shapdetection.shapes.Polygon;
import at.fhv.itb6.arp.shapdetection.shapes.Rectangle;
import at.fhv.itb6.arp.shapdetection.shapes.ShapeUtil;
import at.fhv.itb6.arp.shapdetection.shapes.Triangle;
import org.opencv.core.Mat;
import org.opencv.core.Point;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Zopo on 29.03.2016.
 */
public class InputDetection {
    private InputConfiguration _inputConfiguration;

    public InputDetection(InputConfiguration inputConfiguration){
        _inputConfiguration = inputConfiguration;
    }

    /**
     * Waits for user input
     * @return The coordinates of the user interaction wrapped into a InputAction
     * @throws GamebordersNotDetectedException
     * @throws NoMarkerDetectedException
     */
    public InputAction getUserInput() throws GamebordersNotDetectedException, NoMarkerDetectedException {
        int currentFrame = 0;
        int interruptionCount = 0;
        Point setMarkerPos = new Point(0,0);
        CameraInterface cam = new CameraInterface(_inputConfiguration.getHardwareId());

        while (currentFrame < _inputConfiguration.getConfirmationTime()){
            currentFrame++;

            //Get camera image
            Mat frame = cam.readImage();

            //Detect maker and border positions
            Map<Class, List<Polygon>> detectedPolygons = ShapeDetection.detect(frame);

            Point[] borderPoints = getBorderCoordinates(detectedPolygons.get(Rectangle.class));
            Point markerPos = getMarkerPos(detectedPolygons.get(Triangle.class));

            //Calculate relative marker position
            Point relativeMarkerPos = calculateRelativeMarkerPos(borderPoints, markerPos);

            //Check if the marker has moved
            if (isMarkerMoved(setMarkerPos, relativeMarkerPos)){
                interruptionCount++;
                if (interruptionCount > _inputConfiguration.getInterruptionTolerance()){
                    currentFrame = 0;
                    setMarkerPos = relativeMarkerPos;
                }

            }
        }

        return new InputAction(setMarkerPos);
    }


    protected Point[] getBorderCoordinates(List<Polygon> polygons) throws GamebordersNotDetectedException {
        Rectangle biggestRect = null;
        double biggestRectSize = 0;
        for (Polygon p : polygons){
            if (p instanceof Rectangle){
                double rectSize = ((Rectangle) p).getSurface();
                if (rectSize > biggestRectSize){
                    biggestRect = (Rectangle) p;
                    biggestRectSize = rectSize;
                }
            }
        }
        if (biggestRect == null) {
            throw new GamebordersNotDetectedException(polygons);
        }
        return biggestRect.getPoints();
    }

/*
    public Point[] getBorderCoordinates(List<Polygon> polygons) throws GamebordersNotDetectedException {

        //Get all detected rectangles and their center coordinates
        List<Rectangle> rectangles = new LinkedList<>(); //Rectangle list is saved kept for debugging purposes
        List<Point> points = new LinkedList<>();
        for (Polygon p : polygons){
            if (p instanceof Rectangle){
                rectangles.add((Rectangle) p);
                points.add(ShapeUtil.getCenter(p));
            }
        }

        //Throw exception when there are less than four detected rectangles
        if (rectangles.size() < 4){
            throw new GamebordersNotDetectedException(rectangles);
        }

        //Identify the four most extreme points
        Point bottomLeft = points.get(0);
        Point bottomRight = points.get(0);
        Point topLeft = points.get(0);
        Point topRight = points.get(0);

        for (Point p : points){
            //bottom left detection
            if (p.x < bottomLeft.x || (p.x == bottomLeft.x && p.y < bottomLeft.y)) bottomLeft = p;

            //bottom right detection
            if (p.x > bottomRight.x || (p.x == bottomRight.x && p.y < bottomRight.y)) bottomRight = p;

            //top left detection
            if (p.y >= topLeft.y && p.x < topLeft.x) topLeft = p;

            //top right detection
            if (p.y >= topRight.y && p.x > topRight.x) topRight = p;
        }

        return new Point[]{bottomLeft, bottomRight, topLeft, topRight};
    }*/

    protected Point getMarkerPos(List<Polygon> polygons) throws NoMarkerDetectedException {
        //get a Triangle
        Polygon triangle = null;
        for (Polygon p : polygons){
            if (p.getPoints().length == 3){
                triangle = p;
            }
        }

        if (triangle == null){
            throw new NoMarkerDetectedException();
        }

        return ShapeUtil.getCenter(triangle);
    }

    protected Point calculateRelativeMarkerPos(Point[] borderPoints, Point markerPoint){
        double relativeX = (markerPoint.x - borderPoints[0].x / borderPoints[1].x);
        double relativeY = (markerPoint.y - borderPoints[0].y / borderPoints[2].y);

        return new Point(relativeX, relativeY);
    }

    protected boolean isMarkerMoved(Point oldPos, Point newPos){
        if (oldPos == null | newPos == null){
            return true;
        }

        double differenceX = Math.abs(oldPos.x - newPos.x);
        double differenceY = Math.abs(oldPos.y - newPos.y);

        return (differenceX > _inputConfiguration.getSensivityX() || differenceY > _inputConfiguration.getSensivityY());
    }
}
