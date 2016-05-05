package ui;

import java.io.IOException;
import java.net.URL;

public interface IPanelCloseHandler {
    void closeNext(URL location, Object Controller) throws IOException;
}