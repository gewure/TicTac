package at.fhv.itb6.arp;

import Gateway.GamePosition;
import at.fhv.itb6.arp.inputInterface.CursorStatusListener;

import java.util.Observable;

/**
 * Created by Zopo on 05.05.2016.
 */
public class CursorStatus extends Observable implements CursorStatusListener {
    private static CursorStatus _instance;

    private double posX = 0;
    private double posY = 0;
    private double progress = 0;
    private GamePosition activePosition = GamePosition.None;


    private CursorStatus(){
    }

    public static CursorStatus getInstance(){
        if (_instance == null){
            _instance = new CursorStatus();
        }
        return _instance;
    }

    @Override
    public void cursorChangedEvent(double posX, double posY, double progress) {
        this.posX = posX;
        this.posY = posY;
        this.progress = progress;

        this.setChanged();
        this.notifyObservers();
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getProgress() {
        return progress;
    }

    public GamePosition getActivePosition() {
        return activePosition;
    }

    public void setGamePosition(GamePosition position){
        activePosition = position;
        setChanged();
        notifyObservers();
    }
}
