package ui;

import Gateway.GamePosition;
import Gateway.GameToken;
import Gateway.PlayerIdentifier;
import app.GameController;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.*;
import java.util.List;

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
        _gamePositionMappings.put(GamePosition.Out0, new Point(0, 0));
        _gamePositionMappings.put(GamePosition.Out1, new Point(275, 0));
        _gamePositionMappings.put(GamePosition.Out2, new Point(550, 0));
        _gamePositionMappings.put(GamePosition.Out3, new Point(550, 275));
        _gamePositionMappings.put(GamePosition.Out4, new Point(550, 550));
        _gamePositionMappings.put(GamePosition.Out5, new Point(275, 550));
        _gamePositionMappings.put(GamePosition.Out6, new Point(0, 550));
        _gamePositionMappings.put(GamePosition.Out7, new Point(0, 275));

        _gamePositionMappings.put(GamePosition.Middle0, new Point(85, 85));
        _gamePositionMappings.put(GamePosition.Middle1, new Point(275, 85));
        _gamePositionMappings.put(GamePosition.Middle2, new Point(465, 85));
        _gamePositionMappings.put(GamePosition.Middle3, new Point(465, 275));
        _gamePositionMappings.put(GamePosition.Middle4, new Point(465, 465));
        _gamePositionMappings.put(GamePosition.Middle5, new Point(275, 465));
        _gamePositionMappings.put(GamePosition.Middle6, new Point(85, 465));
        _gamePositionMappings.put(GamePosition.Middle7, new Point(85, 275));

        _gamePositionMappings.put(GamePosition.Center0, new Point(170, 170));
        _gamePositionMappings.put(GamePosition.Center1, new Point(275, 170));
        _gamePositionMappings.put(GamePosition.Center2, new Point(380, 170));
        _gamePositionMappings.put(GamePosition.Center3, new Point(380, 275));
        _gamePositionMappings.put(GamePosition.Center4, new Point(380, 380));
        _gamePositionMappings.put(GamePosition.Center5, new Point(275, 380));
        _gamePositionMappings.put(GamePosition.Center6, new Point(170, 380));
        _gamePositionMappings.put(GamePosition.Center7, new Point(170, 275));

        _gameGameController.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                GraphicsContext context = canvis.getGraphicsContext2D();
                drawGameField(context, _gameGameController.getPlayer1GameTokens(), _gameGameController.getPlayer2GameTokens());

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
        drawGameField(context, _gameGameController.getPlayer1GameTokens(), _gameGameController.getPlayer2GameTokens());
    }

    private void drawGameField(GraphicsContext context, List<GameToken> player1Token, List<GameToken> player2Token) {
        drawBackground(context);
        player1Token.forEach((gameToken) -> drawGameToken(context, gameToken, 100));
        player2Token.forEach((gameToken) -> drawGameToken(context, gameToken, 100));
    }

    private void drawGameToken(GraphicsContext context, GameToken gameToken, int xOffset) {
        float radius = 50;
        Point point = _gamePositionMappings.get(gameToken.getGamePosition());

        if(point == null) {
            System.out.println(gameToken.getGamePosition() + " not found!");
            return; //Todo: remove use for debugging so that the applications does not crash
        }

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
