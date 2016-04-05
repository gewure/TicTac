package at.fhv.itb6.arp.inputInterface;

/**
 * Created by Zopo on 29.03.2016.
 */
public class InputConfiguration {
    private int _hardwareId = 0;
    private int _confirmationTime = 60;
    private int _interruptionTolerance = 2;
    private double _sensivityX = 0.01;
    private double _sensivityY = 0.01;

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
}
