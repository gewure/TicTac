package at.fhv.itb6.arp.hardwareinterface;

import com.atul.JavaOpenCV.Imshow.Imshow;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by simon_000 on 27/03/2016.
 */
public class CameraInterface implements Closeable {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private VideoCapture videoCapture;
    private int deviceId;

    /**
     *
     * @param range maximum number of devices
     * @return list of possible devices
     */
    public static List<Integer> getAvailableDeviceIds(int range) {
        List<Integer> devices = new ArrayList<>();

        for(int i = 0; i < range; ++i) {
            VideoCapture cap = new VideoCapture(i);

            if(cap.isOpened()) {
                devices.add(i);
                cap.release();
            }
        }

        return devices;
    }

    public CameraInterface(int deviceId) {
        this.deviceId = deviceId;
        videoCapture = new VideoCapture(deviceId);
    }

    public Mat readImage() {
        if(!videoCapture.isOpened()) {
            System.err.println("No CameraInterface on device id => " + deviceId);
        }

        Mat readImg = new Mat();

        videoCapture.read(readImg);

        return readImg;
    }

    private boolean isClosed;

    @Override
    public void close() throws IOException {
        if(!isClosed) {
            videoCapture.release();
            isClosed = true;
        }
    }
}
