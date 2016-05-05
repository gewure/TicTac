package at.fhv.itb6.arp.inputInterface;

/**
 * Created by Zopo on 29.03.2016.
 */

import org.opencv.core.Point;

/**
 * Contains the position of a input action
 * point.x and point.y are values between 0 and 1
 *
 * Example Coordinates:
 * [0|0]        => Bottom left corner of the gameboard
 * [1|1]        => Top right corner of the gameboard
 * [0.5|0.5]    => Middle of the gameboard
 *
 */
public class InputAction {
    private Point _point;

    public InputAction(Point point){
        _point = point;
    }

    public Point getPoint() {
        return _point;
    }

    protected void setPoint(Point point) {
        _point = point;
    }
}
