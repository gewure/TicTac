package at.fhv.itb6.arp;

import Gateway.GameFacade;
import at.fhv.itb6.arp.hardwareinterface.CameraInterface;

import java.util.List;

/**
 * Created by simon_000 on 30/04/2016.
 */
public class ARFacade {

    private static ARFacade _instance;
    private  ARFacade() {

    }

    public static ARFacade getInstance() {
        if(_instance == null) {
            _instance = new ARFacade();
        }

        return _instance;
    }

    public static List<Integer> getCameraIDs() {
        return CameraInterface.getAvailableDeviceIds(10);
    }

    private GameFacade _gameFacade;

    public void init(int cameraID, GameFacade _gameFacade) {


    }


}
