package at.fhv.itb6.arp.inputInterface;

import at.fhv.itb6.arp.shapdetection.shapes.Polygon;
import at.fhv.itb6.arp.shapdetection.shapes.Triangle;
import org.opencv.core.Scalar;

/**
 * Created by Zopo on 29.03.2016.
 */
public class InputConfiguration {
    private int _hardwareId = 1;
    private int _confirmationTime = 120;
    private int _interruptionTolerance = 3;
    private double _sensivityX = 0.015;
    private double _sensivityY = 0.015;
    private Scalar _minCol = new Scalar(0, 180, 0);
    private Scalar _maxCol = new Scalar(125, 255, 125);
    private int _makerBufferSize = 30;

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
}
