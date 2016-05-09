package at.fhv.itb6.arp;

import Gateway.GameFacade;
import at.fhv.itb6.arp.hardwareinterface.CameraInterface;
import at.fhv.itb6.arp.inputInterface.*;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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

    public void init(InputConfiguration inputConfiguration) {
        if (inputConfiguration.isUseMouseSimulation()){
            _inputDetection = new MouseInputSimulation(inputConfiguration);
            inputConfiguration.setConfirmationTime(30000);
        } else {
            _inputDetection = new InputDetection(inputConfiguration);

        }
        inputActions = new LinkedBlockingQueue<>();
    }

    public InputAction getCursorPosition() {
        InputAction inputAction = null;

        Thread readInputThread = new Thread(() -> {
            try {
                inputActions.put(_inputDetection.getUserInput(new CursorStatusListener() {
                   @Override
                   public void cursorChangedEvent(double posX, double posY, double progress) {
                       CursorStatus cursorStatus = CursorStatus.getInstance();
                       cursorStatus.cursorChangedEvent(posX, posY, progress);
                       //ignore ask zopo
                   }
               }));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        readInputThread.start();

        try {
             inputAction = inputActions.take(); // will block until the readInputThread has detected the cursor
            inputActions.clear();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Input Action found");
        return inputAction;
    }
}
