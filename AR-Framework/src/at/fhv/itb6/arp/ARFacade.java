package at.fhv.itb6.arp;

import Gateway.GameFacade;
import at.fhv.itb6.arp.hardwareinterface.CameraInterface;
import at.fhv.itb6.arp.inputInterface.CursorStatusListener;
import at.fhv.itb6.arp.inputInterface.InputAction;
import at.fhv.itb6.arp.inputInterface.InputConfiguration;
import at.fhv.itb6.arp.inputInterface.InputDetection;

import java.util.List;
import java.util.concurrent.BlockingQueue;

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

    private InputDetection _inputDetection;
    private BlockingQueue<InputAction> inputActions;

    public static List<Integer> getCameraIDs() {
        return CameraInterface.getAvailableDeviceIds(10);
    }

    private GameFacade _gameFacade;

    public void init(int cameraID) {
        InputConfiguration config = new InputConfiguration();
        config.setHardwareId(cameraID);
        _inputDetection = new InputDetection(config);
    }

    public InputAction getCursorPosition() {
        Thread readInputThread = new Thread(() -> {
            boolean cursorNotFound = true;
            while(cursorNotFound) {
                    InputAction inputAction = _inputDetection.getUserInput(new CursorStatusListener() {
                        @Override
                        public void cursorChangedEvent(double posX, double posY, double progress) {
                            //ignore ask zopo
                        }
                    });
                    cursorNotFound = false;
            }
        });
        readInputThread.start();

        InputAction inputAction = null;
        try {
             inputAction = inputActions.take(); // will block until the readInputThread has detected the cursor
            inputActions.clear();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return inputAction;
    }
}
