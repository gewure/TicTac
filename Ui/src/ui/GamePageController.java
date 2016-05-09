package ui;

import Gateway.GamePosition;
import Gateway.GameToken;
import Logic.Token;
import app.CursorPositionToGamePositionMapper;
import app.GameController;
import at.fhv.itb6.arp.CursorStatus;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by simon_000 on 06/04/2016.
 */
public class GamePageController {
    private Stage _parent;

    @FXML Canvas canvis;

    private GameController _gameGameController;
    private HashMap<GamePosition, Point> _gamePositionMappings;

    private Point calcRelativePoint(org.opencv.core.Point point) {
        return new Point((int)(point.x * canvis.getWidth()), (int)(point.y * canvis.getHeight()));
    }

    public GamePageController(GameController gameController, Stage parent) {
        _parent = parent;
        _parent.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                canvis.setWidth(newValue.doubleValue());
                remap();
                //drawBackground(canvis.getGraphicsContext2D());
                redraw(canvis.getGraphicsContext2D());
            }
        });

        _parent.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                canvis.setHeight(newValue.doubleValue());
                remap();
                //drawBackground(canvis.getGraphicsContext2D());
                redraw(canvis.getGraphicsContext2D());
            }
        });

        _gameGameController = gameController;

        _gamePositionMappings = new HashMap<>();

        _gameGameController.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println("Move detected - Updating gamefield");
                GraphicsContext context = canvis.getGraphicsContext2D();
                //drawGameField(context, _gameGameController.getPlayer1GameTokens(), _gameGameController.getPlayer2GameTokens());
                redraw(context);
            }
        });
    }

    private void redraw(GraphicsContext context){
        drawBackground(context);
        drawGameField(context, _gameGameController.getPlayer1GameTokens(), _gameGameController.getPlayer2GameTokens());
        CursorStatus cursorStatus = CursorStatus.getInstance();
        drawCircle(context, Color.BEIGE, 3, (int)((cursorStatus.getPosX()) * canvis.getWidth()), (int)((cursorStatus.getPosY()) * canvis.getHeight()));
    }

    private void remap(){
        _gamePositionMappings.clear();

        CursorPositionToGamePositionMapper mapper = new CursorPositionToGamePositionMapper();
        _gamePositionMappings.put(GamePosition.Out0, calcRelativePoint(mapper.getMapping(GamePosition.Out0).getPosition()));
        _gamePositionMappings.put(GamePosition.Out1, calcRelativePoint(mapper.getMapping(GamePosition.Out1).getPosition()));
        _gamePositionMappings.put(GamePosition.Out2, calcRelativePoint(mapper.getMapping(GamePosition.Out2).getPosition()));
        _gamePositionMappings.put(GamePosition.Out3, calcRelativePoint(mapper.getMapping(GamePosition.Out3).getPosition()));
        _gamePositionMappings.put(GamePosition.Out4, calcRelativePoint(mapper.getMapping(GamePosition.Out4).getPosition()));
        _gamePositionMappings.put(GamePosition.Out5, calcRelativePoint(mapper.getMapping(GamePosition.Out5).getPosition()));
        _gamePositionMappings.put(GamePosition.Out6, calcRelativePoint(mapper.getMapping(GamePosition.Out6).getPosition()));
        _gamePositionMappings.put(GamePosition.Out7, calcRelativePoint(mapper.getMapping(GamePosition.Out7).getPosition()));


        _gamePositionMappings.put(GamePosition.Middle0, calcRelativePoint(mapper.getMapping(GamePosition.Middle0).getPosition()));
        _gamePositionMappings.put(GamePosition.Middle1, calcRelativePoint(mapper.getMapping(GamePosition.Middle1).getPosition()));
        _gamePositionMappings.put(GamePosition.Middle2, calcRelativePoint(mapper.getMapping(GamePosition.Middle2).getPosition()));
        _gamePositionMappings.put(GamePosition.Middle3, calcRelativePoint(mapper.getMapping(GamePosition.Middle3).getPosition()));
        _gamePositionMappings.put(GamePosition.Middle4, calcRelativePoint(mapper.getMapping(GamePosition.Middle4).getPosition()));
        _gamePositionMappings.put(GamePosition.Middle5, calcRelativePoint(mapper.getMapping(GamePosition.Middle5).getPosition()));
        _gamePositionMappings.put(GamePosition.Middle6, calcRelativePoint(mapper.getMapping(GamePosition.Middle6).getPosition()));
        _gamePositionMappings.put(GamePosition.Middle7, calcRelativePoint(mapper.getMapping(GamePosition.Middle7).getPosition()));


        _gamePositionMappings.put(GamePosition.Center0, calcRelativePoint(mapper.getMapping(GamePosition.Center0).getPosition()));
        _gamePositionMappings.put(GamePosition.Center1, calcRelativePoint(mapper.getMapping(GamePosition.Center1).getPosition()));
        _gamePositionMappings.put(GamePosition.Center2, calcRelativePoint(mapper.getMapping(GamePosition.Center2).getPosition()));
        _gamePositionMappings.put(GamePosition.Center3, calcRelativePoint(mapper.getMapping(GamePosition.Center3).getPosition()));
        _gamePositionMappings.put(GamePosition.Center4, calcRelativePoint(mapper.getMapping(GamePosition.Center4).getPosition()));
        _gamePositionMappings.put(GamePosition.Center5, calcRelativePoint(mapper.getMapping(GamePosition.Center5).getPosition()));
        _gamePositionMappings.put(GamePosition.Center6, calcRelativePoint(mapper.getMapping(GamePosition.Center6).getPosition()));
        _gamePositionMappings.put(GamePosition.Center7, calcRelativePoint(mapper.getMapping(GamePosition.Center7).getPosition()));

    }

    private void drawBackground(GraphicsContext context) {
        context.clearRect(0, 0, canvis.getWidth(), canvis.getHeight());

        context.setFill(Color.BLACK);
        context.fillRect(0,0, canvis.getWidth(), canvis.getHeight());
        Image image = new Image("/ui/gameboard.png");

        context.drawImage(image, canvis.getWidth()/2 - image.getWidth()/2, canvis.getHeight()/2 - image.getHeight()/2);

        /*context.setFill(Color.BLUE);
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                context.strokeLine(i/10, 0, i/10, canvis.getHeight());
            }
        }
        */

        context.save();
    }

    @FXML
    public void initialize() {
        remap();

        GraphicsContext context=  canvis.getGraphicsContext2D();
        drawGameField(context, _gameGameController.getPlayer1GameTokens(), _gameGameController.getPlayer2GameTokens());
    }

    private void drawGameField(GraphicsContext context, List<GameToken> player1Token, List<GameToken> player2Token) {
        player1Token.forEach((gameToken) -> drawGameToken(context, gameToken));
        player2Token.forEach((gameToken) -> drawGameToken(context, gameToken));
    }

    private void drawGameToken(GraphicsContext context, GameToken gameToken) {
        System.out.println("Drawing game token...");
        float radius = 50;
        Point point = _gamePositionMappings.get(gameToken.getGamePosition());

        if(point == null) {
            System.out.println(gameToken.getGamePosition() + " not found!");
            return; //Todo: remove use for debugging so that the applications does not crash
        }

        if(gameToken.getPlayerIdentifier() == Token.PLAYER_1) {
            drawCircle(context, Color.RED, radius, point.x, point.y);
        }
        else {
            drawCircle(context, Color.BLUE, radius, point.x, point.y);
        }
    }

    private void drawCircle(GraphicsContext context, Color color, float radius, int x, int y) {
        int fixedX = (int)(x - radius/2);
        int fixedY = (int)(y - radius/2);
        context.setFill(color);
        context.fillOval(fixedX, fixedY, radius, radius);
    }
}
