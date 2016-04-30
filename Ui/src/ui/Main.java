package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class Main extends Application implements IPanelCloseHandler {

	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		try {

			this.primaryStage = primaryStage;


			FXMLLoader loader = new FXMLLoader();

			StartPageController controller = new StartPageController();
			controller.setPanelCloseHandler(this);

			loader.setController(controller);
			loader.setLocation(getClass().getResource("StartPage.fxml"));

			Scene scene = new Scene(loader.load(), 800,600);

			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setFullScreen(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void closeNext(URL location, Object Controller) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		if(Controller != null) {
			loader.setController(Controller);
		}

		loader.setLocation(location);

		Scene scene = new Scene(loader.load(), 800,600);



		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setFullScreen(true);

	}
}
