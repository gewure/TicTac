package app;

import Gateway.GamePosition;
import at.fhv.itb6.arp.inputInterface.InputAction;
import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by simon_000 on 06/05/2016.
 */
public class CursorPositionToGamePositionMapper {

    public List<Region> regions;

    public CursorPositionToGamePositionMapper() {
        regions = new ArrayList<>();

        //add mapping for stuff with function addMapping
        addMapping(0.275, 0.097, 0.03, GamePosition.Out0);
    }

    public GamePosition map(InputAction inputAction){

        GamePosition gamePosition = GamePosition.None;
        int i = 0;
        while ((gamePosition.equals(GamePosition.None)) && (i < regions.size())) {
            Region current = regions.get(i);

            if(current.inRange(inputAction.getPoint())) {
                gamePosition = current.gamePosition;
            }

            ++i;
        }

        if(gamePosition == GamePosition.None) {
            System.out.println("No appropiate mapping found!");
        }

        return gamePosition;
    }

    public void addMapping(double xPos, double yPos, double distance, GamePosition gamePosition) {
        Point p = new Point();
        p.x = xPos;
        p.y = yPos;

        regions.add(new Region(p, distance, gamePosition));
    }

    private class Region {
        private Point position;
        private double distance;
        private GamePosition gamePosition;

        public Region(Point position, double distance, GamePosition gamePosition) {
            this.position = position;
            this.distance = distance;
            this.gamePosition = gamePosition;
        }

        public boolean inRange(Point point2) {
            return calcDistance(point2) >= distance;
        }

        private double calcDistance(Point point2) {
            return Math.sqrt(Math.pow(point2.x - position.x, 2) + Math.pow(point2.y - position.y, 2));
        }
    }

}
