package app;

import Gateway.GamePosition;
import at.fhv.itb6.arp.inputInterface.InputAction;
import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by simon_000 on 06/05/2016.
 */
public class CursorPositionToGamePositionMapper {

    public List<Region> regions;

    public CursorPositionToGamePositionMapper() {
        regions = new ArrayList<>();

        //add mapping for stuff with function addMapping
        addMapping(0.29, 0.125, 0.05, GamePosition.Out0);
        addMapping(0.5, 0.125, 0.05, GamePosition.Out1);
        addMapping(0.71, 0.125, 0.05, GamePosition.Out2);
        addMapping(0.71, 0.5, 0.05, GamePosition.Out3);
        addMapping(0.71, 0.875, 0.05, GamePosition.Out4);
        addMapping(0.5, 0.875, 0.05, GamePosition.Out5);
        addMapping(0.29, 0.875, 0.05, GamePosition.Out6);
        addMapping(0.29, 0.5, 0.05, GamePosition.Out7);

        addMapping(0.35, 0.25, 0.05, GamePosition.Middle0);
        addMapping(0.5, 0.25, 0.05, GamePosition.Middle1);
        addMapping(0.65, 0.25, 0.05, GamePosition.Middle2);
        addMapping(0.65, 0.5, 0.05, GamePosition.Middle3);
        addMapping(0.65, 0.75, 0.05, GamePosition.Middle4);
        addMapping(0.5, 0.75, 0.05, GamePosition.Middle5);
        addMapping(0.35, 0.75, 0.05, GamePosition.Middle6);
        addMapping(0.35, 0.5, 0.05, GamePosition.Middle7);

        addMapping(0.42, 0.35, 0.05, GamePosition.Center0);
        addMapping(0.5, 0.35, 0.05, GamePosition.Center1);
        addMapping(0.58, 0.35, 0.05, GamePosition.Center2);
        addMapping(0.58, 0.5, 0.05, GamePosition.Center3);
        addMapping(0.58, 0.65, 0.05, GamePosition.Center4);
        addMapping(0.5, 0.65, 0.05, GamePosition.Center5);
        addMapping(0.42, 0.65, 0.05, GamePosition.Center6);
        addMapping(0.42, 0.5, 0.05, GamePosition.Center7);




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

        System.out.println(gamePosition.toString());
        return gamePosition;
    }

    private HashMap<GamePosition, Region> _regionMappings;

    public Region getMapping(GamePosition gamePosition) {
        return _regionMappings.get(gamePosition);
    }

    public void addMapping(double xPos, double yPos, double distance, GamePosition gamePosition) {
        Point p = new Point();
        p.x = xPos;
        p.y = yPos;

        Region newRegion = new Region(p, distance, gamePosition);
        if(_regionMappings == null) {
            _regionMappings = new HashMap<>();
        }
        _regionMappings.put(gamePosition, newRegion);

        regions.add(newRegion);
    }



    public class Region {
        private Point position;
        private double distance;
        private GamePosition gamePosition;

        public Region(Point position, double distance, GamePosition gamePosition) {
            this.position = position;
            this.distance = distance;
            this.gamePosition = gamePosition;
        }

        public boolean inRange(Point point2) {
            return calcDistance(point2) <= distance;
        }

        private double calcDistance(Point point2) {
            return Math.sqrt(Math.pow(point2.x - position.x, 2) + Math.pow(point2.y - position.y, 2));
        }

        public Point getPosition() {
            return position;
        }
    }

}
