package at.fhv.itb6.arp.inputInterface;

import at.fhv.itb6.arp.hardwareinterface.CameraInterface;
import at.fhv.itb6.arp.inputInterface.exceptions.GamebordersNotDetectedException;
import at.fhv.itb6.arp.inputInterface.exceptions.NoMarkerDetectedException;
import at.fhv.itb6.arp.shapdetection.ShapeDetection;
import at.fhv.itb6.arp.shapdetection.shapes.Polygon;
import at.fhv.itb6.arp.shapdetection.shapes.Rectangle;
import at.fhv.itb6.arp.shapdetection.shapes.ShapeUtil;
import at.fhv.itb6.arp.shapdetection.shapes.Triangle;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Zopo on 29.03.2016.
 */
public class InputDetection {
    private InputConfiguration _inputConfiguration;
    private List<Mat> _markerFrameBuffer = new LinkedList<>();

    public InputDetection(InputConfiguration inputConfiguration){
        _inputConfiguration = inputConfiguration;
    }

    /**
     * Waits for user input
     * @return The coordinates of the user interaction wrapped into a InputAction
     * @throws GamebordersNotDetectedException
     * @throws NoMarkerDetectedException
     */
    public InputAction getUserInput(CursorStatusListener callback) throws GamebordersNotDetectedException, NoMarkerDetectedException {
        int currentFrame = 0;
        int interruptionCount = 0;
        Point setMarkerPos = new Point(0,0);
        CameraInterface cam = new CameraInterface(_inputConfiguration.getHardwareId());

        while (currentFrame < _inputConfiguration.getConfirmationTime()){
            currentFrame++;

            //Get camera image
            Mat frame = cam.readImage();

            //Detect board borders and correct perspective view
            Map<Class, List<Polygon>> detectedPolygons = ShapeDetection.detect(frame);
            Rectangle borderRect = getBorderRect(detectedPolygons.get(Rectangle.class));
            Mat correctedImage = ShapeUtil.perspectiveCorrection(frame, borderRect, new Size(800, 500));

            //Detect marker position
            Map<Class, List<Polygon>> detectedPolygonsPostCorection = ShapeDetection.detect(correctedImage);
            Point markerPos = getMarkerPos(detectedPolygonsPostCorection.get(Triangle.class), correctedImage);

            //Calculate relative marker position
            Point relativeMarkerPos = calculateRelativeMarkerPos(correctedImage.size(), markerPos);

            //Check if the marker has moved
            if (isMarkerMoved(setMarkerPos, relativeMarkerPos)){
                interruptionCount++;
                if (interruptionCount > _inputConfiguration.getInterruptionTolerance()){
                    currentFrame = 0;
                    setMarkerPos = relativeMarkerPos;
                }
            }
            callback.cursorChangedEvent(setMarkerPos.x, setMarkerPos.y, (double) currentFrame / (double) _inputConfiguration.getConfirmationTime());
        }

        return new InputAction(setMarkerPos);
    }


    protected Rectangle getBorderRect(List<Polygon> polygons) throws GamebordersNotDetectedException {
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
            throw new GamebordersNotDetectedException();
        }
        return biggestRect;
    }

    protected Point getMarkerPos(List<Polygon> polygons, Mat frame) throws NoMarkerDetectedException {
        Scalar minCol = _inputConfiguration.getMinCol();
        Scalar maxCol = _inputConfiguration.getMaxCol();

        Mat colorOnly = new Mat();
        Core.inRange(frame, minCol, maxCol, colorOnly);

        Moments mu = Imgproc.moments(colorOnly);
        Point center = new Point(mu.get_m10() / mu.get_m00(), mu.get_m01() / mu.get_m00());

        return center;
    }

    protected Point calculateRelativeMarkerPos(Size border, Point markerPoint){
        double relativeX = markerPoint.x / border.width;
        double relativeY = markerPoint.y / border.height;

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
