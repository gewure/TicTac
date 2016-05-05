package ui;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public abstract class ViewFactory {

    private URL _viewPath;
    private Object _controller;

    public ViewFactory(URL viewPath, Object controller) {
        _viewPath = viewPath;
        _controller = controller;
    }

    public void create(Stage paneToPlaceIn) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(_viewPath);

        if(_controller instanceof IPanelClosable) {
            ((IPanelClosable)_controller).setPanelCloseHandler(new IPanelCloseHandler() {

                @Override
                public void closeNext(URL location, Object Controlle ) throws IOException {
                    if(Controlle != null) {
                        loader.setController(Controlle);
                    }
                    loader.setLocation(location);
                }
            });
        }

        paneToPlaceIn.setScene(loader.load());
    }

}