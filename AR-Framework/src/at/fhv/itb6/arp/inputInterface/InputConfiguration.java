package at.fhv.itb6.arp.inputInterface;

import at.fhv.itb6.arp.shapdetection.shapes.Polygon;
import at.fhv.itb6.arp.shapdetection.shapes.Rectangle;
import at.fhv.itb6.arp.shapdetection.shapes.Triangle;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

/**
 * Created by Zopo on 29.03.2016.
 */
public class InputConfiguration {
    private int _hardwareId = 1;
    private int _confirmationTime = 4;
    private int _interruptionTolerance = 2;
    private int _medianRange = 6;
    private double _sensivityX = 0.03;
    private double _sensivityY = 0.03;
    private Scalar _minCol = new Scalar(0, 50, 0);
    private Scalar _maxCol = new Scalar(55, 255, 55);
    private int _makerBufferSize = 30;
    private int _cameraPosition = CameraPosition.BOTTOM;
    private boolean _gameboardDetected = false;
    private Rectangle _gameboardRectangle;
    private boolean _useMouseSimulation = false;



    public int getHardwareId() {
        return _hardwareId;
    }

    public void setHardwareId(int hardwareId) {
        _hardwareId = hardwareId;
    }

    public int getConfirmationTime() {
        return _confirmationTime;
    }

    public void setConfirmationTime(int confirmationTime) {
        _confirmationTime = confirmationTime;
    }

    public int getInterruptionTolerance() {
        return _interruptionTolerance;
    }

    public void setInterruptionTolerance(int interruptionTolerance) {
        _interruptionTolerance = interruptionTolerance;
    }

    public double getSensivityX() {
        return _sensivityX;
    }

    public void setSensivityX(double sensivityX) {
        _sensivityX = sensivityX;
    }

    public double getSensivityY() {
        return _sensivityY;
    }

    public void setSensivityY(double sensivityY) {
        _sensivityY = sensivityY;
    }

    public Scalar getMinCol() {
        return _minCol;
    }

    public void setMinCol(Scalar minCol) {
        _minCol = minCol;
    }

    public Scalar getMaxCol() {
        return _maxCol;
    }

    public void setMaxCol(Scalar maxCol) {
        _maxCol = maxCol;
    }

    public int getMakerBufferSize() {
        return _makerBufferSize;
    }

    public void setMakerBufferSize(int makerBufferSize) {
        _makerBufferSize = makerBufferSize;
    }

    public int getCameraPosition() {
        return _cameraPosition;
    }

    public void setCameraPosition(int cameraPosition) {
        _cameraPosition = cameraPosition;
    }

    public boolean isGameboardDetected() {
        return _gameboardDetected;
    }

    public void setGameboardDetected(boolean gameboardDetected) {
        _gameboardDetected = gameboardDetected;
    }

    public Rectangle getGameboardRectangle() {
        return _gameboardRectangle;
    }

    public void setGameboardRectangle(Rectangle gameboardRectangle) {
        _gameboardRectangle = gameboardRectangle;
    }

    public int getMedianRange() {
        return _medianRange;
    }

    public void setMedianRange(int medianRange) {
        _medianRange = medianRange;
    }

    public enum CameraPosition{
        ;
        public static int BOTTOM = 0;
        public static int LEFT = 1;
        public static int TOP = 2;
        public static int RIGHT = 3;
    }

    public boolean isUseMouseSimulation() {
        return _useMouseSimulation;
    }

    public void setUseMouseSimulation(boolean useMouseSimulation) {
        _useMouseSimulation = useMouseSimulation;
    }
}
