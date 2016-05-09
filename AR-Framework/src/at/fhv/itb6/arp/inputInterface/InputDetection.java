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

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Zopo on 29.03.2016.
 */
public class InputDetection {
    private InputConfiguration _inputConfiguration;
    private InputMedianFilter _inputMedianFilter;
    private List<Mat> _markerFrameBuffer = new LinkedList<>();

    public InputDetection(InputConfiguration inputConfiguration){
        _inputConfiguration = inputConfiguration;
        _inputMedianFilter = new InputMedianFilter(_inputConfiguration);
    }

    /**
     * Waits for user input
     * @return The coordinates of the user interaction wrapped into a InputAction
     * @throws NoMarkerDetectedException
     */
    public InputAction getUserInput(CursorStatusListener callback) {
        if (!_inputConfiguration.isGameboardDetected()) {
            detectGameboard();
        }

        int currentFrame = 0;
        int interruptionCount = 0;
        Point setMarkerPos = new Point(0, 0);
        CameraInterface cam = new CameraInterface(_inputConfiguration.getHardwareId());

        while (currentFrame < _inputConfiguration.getConfirmationTime()) {
            currentFrame++;

            //Get camera image
            Mat frame = cam.readImage();
            Mat correctedImage = new Mat();

            try {
                correctedImage = ShapeUtil.perspectiveCorrection(frame, _inputConfiguration.getGameboardRectangle(), new Size(1000, 1000));
                for (int i = 0; i < _inputConfiguration.getCameraPosition(); i++) {
                    correctedImage = rotateRight(correctedImage);
                }

                //Detect marker position
                Point markerPos = getMarkerPos(correctedImage);

                //Calculate relative marker position
                Point relativeMarkerPos = calculateRelativeMarkerPos(correctedImage.size(), markerPos);

                _inputMedianFilter.insertPoint(relativeMarkerPos);
                relativeMarkerPos = _inputMedianFilter.getMedian();

                System.out.println(relativeMarkerPos.x + "|" + relativeMarkerPos.y + "\t" + currentFrame);

                //Check if the marker has moved
                if (isMarkerMoved(setMarkerPos, relativeMarkerPos)) {
                    interruptionCount++;
                    if (interruptionCount > _inputConfiguration.getInterruptionTolerance()) {
                        currentFrame = 0;
                        setMarkerPos = relativeMarkerPos;
                    }
                }
                callback.cursorChangedEvent(setMarkerPos.x, setMarkerPos.y, (double) currentFrame / (double) _inputConfiguration.getConfirmationTime());
            } catch (NoMarkerDetectedException e) {
                interruptionCount++;
                if (interruptionCount > _inputConfiguration.getInterruptionTolerance()) {
                    currentFrame = 0;
                }
            }
            frame.release();
        }
        try {
            cam.close();
        } catch (IOException e) {
            e.printStackTrace();
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

    protected Point getMarkerPos(Mat frame) throws NoMarkerDetectedException {
        Scalar minCol = _inputConfiguration.getMinCol();
        Scalar maxCol = _inputConfiguration.getMaxCol();

        Mat colorOnly = new Mat();
        Core.inRange(frame, minCol, maxCol, colorOnly);
        Imgproc.erode(colorOnly, colorOnly, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(4,4)));

        Moments mu = Imgproc.moments(colorOnly);
        Point center = new Point(mu.get_m10() / mu.get_m00(), mu.get_m01() / mu.get_m00());

        if (Double.isNaN(center.x) || Double.isNaN(center.y)){
            throw new NoMarkerDetectedException();
        }

        colorOnly.release();
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

    protected Mat rotateRight(Mat src){
        Mat dst = new Mat();
        Point midPoint = new Point(src.cols()/2, src.rows()/2);
        Mat rotationM = Imgproc.getRotationMatrix2D(midPoint, 90, 1);
        Imgproc.warpAffine(src, dst, rotationM, new Size(src.rows(), src.cols()));
        return dst;
    }

    public void detectGameboard(){
        CameraInterface cam = new CameraInterface(_inputConfiguration.getHardwareId());
        _inputConfiguration.setGameboardDetected(false);

        Rectangle borderRect = null;

        while (!_inputConfiguration.isGameboardDetected()){
            try {
                Mat frame = cam.readImage();
                ShapeDetection.detect(frame);
                Map<Class, List<Polygon>> detectedPolygons = ShapeDetection.detect(frame);
                frame.release();
                borderRect = getBorderRect(detectedPolygons.get(Rectangle.class));

                _inputConfiguration.setGameboardRectangle(borderRect);
                _inputConfiguration.setGameboardDetected(true);
            } catch (GamebordersNotDetectedException e) {
                e.printStackTrace();
            }
        }

        try {
            cam.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
