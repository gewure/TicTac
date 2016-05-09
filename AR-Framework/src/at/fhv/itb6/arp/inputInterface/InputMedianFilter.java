package at.fhv.itb6.arp.inputInterface;

import org.opencv.core.Point;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Zopo on 09.05.2016.
 */
public class InputMedianFilter {
    private LinkedList<Double> _lastX;
    private LinkedList<Double> _lastY;
    private InputConfiguration _inputConfiguration;

    public InputMedianFilter(InputConfiguration ic){
        _inputConfiguration = ic;
        _lastX = new LinkedList<>();
        _lastY = new LinkedList<>();

    }

    public void insertPoint(Point p){
        _lastX.add(p.x);
        _lastY.add(p.y);

        if (_lastX.size() > _inputConfiguration.getMedianRange()){
            _lastX.remove(0);
        }
        if (_lastY.size() > _inputConfiguration.getMedianRange()){
            _lastY.remove(0);
        }
    }

    public Point getMedian(){
        List<Double> sortedX = new LinkedList<>();
        List<Double> sortedY = new LinkedList<>();

        sortedX.addAll(_lastX);
        sortedY.addAll(_lastY);

        Comparator<Double> dComparator = new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return Math.round(Math.round(o1 - o2));
            }
        };

        sortedX.sort(dComparator);
        sortedY.sort(dComparator);

        return new Point(sortedX.get(sortedX.size()/2), sortedY.get(sortedY.size()/2));
    }
}
