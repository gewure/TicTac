package ui;

import Gateway.GamePosition;
import Gateway.GameToken;
import Gateway.PlayerIdentifier;
import app.GameController;
import com.sun.javafx.scene.paint.GradientUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by simon_000 on 06/04/2016.
 */
public class GamePageController {

    @FXML Canvas canvis;

    private GameController _gameGameController;
    private HashMap<GamePosition, Point> _gamePositionMappings;

    public GamePageController(GameController gameController) {
        _gameGameController = gameController;

        _gamePositionMappings = new HashMap<>();
        _gamePositionMappings.put(GamePosition.Out0, new Point(0,0));
        _gamePositionMappings.put(GamePosition.Out1, new Point(275,0));

        _gameGameController.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                //TODO: add logic that draws the game
                GraphicsContext context=  canvis.getGraphicsContext2D();

                drawBackground(context);

            }
        });
    }

    private void drawBackground(GraphicsContext context) {
        context.clearRect(0, 0, canvis.getWidth(), canvis.getHeight());

        Image image = new Image("/ui/gameboard.png");

        context.drawImage(image, 100, 0);
        context.save();
    }

    @FXML
    public void initialize() {
        GraphicsContext context=  canvis.getGraphicsContext2D();

        drawBackground(context);
        _gameGameController.getPlayer1GameTokens().forEach((gameToken) -> drawGameToken(context, gameToken, 100));
    }

    private void drawGameToken(GraphicsContext context, GameToken gameToken, int xOffset) {
        float radius = 50;
        Point point = _gamePositionMappings.get(gameToken.getGamePosition());

        if(gameToken.getPlayerIdentifier() == PlayerIdentifier.Player1) {
            drawCircle(context, Color.RED, radius, point.x + xOffset, point.y);
        }
        else {
            drawCircle(context, Color.BLUE, radius, point.x + xOffset, point.y);
        }
    }

    private void drawCircle(GraphicsContext context, Color color, float radius, int x, int y) {
        context.setFill(color);
        context.fillOval(x, y, radius, radius);
    }
}
