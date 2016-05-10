package ui;

import Gateway.GamePosition;
import Gateway.GameToken;
import Logic.Token;
import app.CursorPositionToGamePositionMapper;
import app.GameController;
import at.fhv.itb6.arp.CursorStatus;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by simon_000 on 06/04/2016.
 */
public class GamePageController {
    private Stage _parent;
    private String _gamephase;
    private int _frame = 0;
    private CursorPositionToGamePositionMapper _mapper;

    @FXML Canvas canvis;

    @FXML
    BorderPane rootPane;

    Canvas buffer1;
    Canvas buffer2;

    private GameController _gameGameController;
    private HashMap<GamePosition, Point> _gamePositionMappings;

    private Point calcRelativePoint(org.opencv.core.Point point) {
        return new Point((int)(point.x * canvis.getWidth()), (int)(point.y * canvis.getHeight()));
    }

    public GamePageController(GameController gameController, Stage parent) {
        CursorStatus.getInstance().addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                CursorStatus cs = (CursorStatus) o;
                _frame++;
                if (_frame % 3 == 0) {
                    redraw();
                }
            }
        });

        buffer1 = new Canvas(1366, 768);
        buffer2 = new Canvas(1366, 768);

        canvis = buffer1;
        _parent = parent;
        _parent.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                buffer1.setWidth(newValue.doubleValue());
                buffer2.setWidth(newValue.doubleValue());
                remap();
                redraw();
            }
        });

        _parent.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                buffer1.setHeight(newValue.doubleValue());
                buffer2.setHeight(newValue.doubleValue());

                remap();
                redraw();
            }
        });

        _gameGameController = gameController;

        _gamePositionMappings = new HashMap<>();

        _gameGameController.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                setGamephase(_gameGameController.getGamephase().toString());
                System.out.println("Move detected - Updating gamefield");
                redraw();
            }
        });
    }

    private void redraw(){
        Canvas toDraw;
        Canvas toDisplay;

        if (canvis == buffer1){
            toDraw = buffer2;
            toDisplay = buffer1;
        } else {
            toDraw = buffer1;
            toDisplay = buffer2;
        }

        GraphicsContext context = toDraw.getGraphicsContext2D();
        CursorStatus cursorStatus = CursorStatus.getInstance();
        drawBackground(context);
        drawGameField(context, _gameGameController.getPlayer1GameTokens(), _gameGameController.getPlayer2GameTokens());
        drawProgress(context);
        drawCircle(context, Color.BEIGE, 3, (int)((cursorStatus.getPosX()) * canvis.getWidth()), (int)((cursorStatus.getPosY()) * canvis.getHeight()));
        drawGamephase(context);

        canvis = toDraw;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                rootPane.setCenter(canvis);
            }
        });
    }

    private void remap(){
        _gamePositionMappings.clear();

        _mapper = new CursorPositionToGamePositionMapper();
        _gamePositionMappings.put(GamePosition.Out0, calcRelativePoint(_mapper.getMapping(GamePosition.Out0).getPosition()));
        _gamePositionMappings.put(GamePosition.Out1, calcRelativePoint(_mapper.getMapping(GamePosition.Out1).getPosition()));
        _gamePositionMappings.put(GamePosition.Out2, calcRelativePoint(_mapper.getMapping(GamePosition.Out2).getPosition()));
        _gamePositionMappings.put(GamePosition.Out3, calcRelativePoint(_mapper.getMapping(GamePosition.Out3).getPosition()));
        _gamePositionMappings.put(GamePosition.Out4, calcRelativePoint(_mapper.getMapping(GamePosition.Out4).getPosition()));
        _gamePositionMappings.put(GamePosition.Out5, calcRelativePoint(_mapper.getMapping(GamePosition.Out5).getPosition()));
        _gamePositionMappings.put(GamePosition.Out6, calcRelativePoint(_mapper.getMapping(GamePosition.Out6).getPosition()));
        _gamePositionMappings.put(GamePosition.Out7, calcRelativePoint(_mapper.getMapping(GamePosition.Out7).getPosition()));


        _gamePositionMappings.put(GamePosition.Middle0, calcRelativePoint(_mapper.getMapping(GamePosition.Middle0).getPosition()));
        _gamePositionMappings.put(GamePosition.Middle1, calcRelativePoint(_mapper.getMapping(GamePosition.Middle1).getPosition()));
        _gamePositionMappings.put(GamePosition.Middle2, calcRelativePoint(_mapper.getMapping(GamePosition.Middle2).getPosition()));
        _gamePositionMappings.put(GamePosition.Middle3, calcRelativePoint(_mapper.getMapping(GamePosition.Middle3).getPosition()));
        _gamePositionMappings.put(GamePosition.Middle4, calcRelativePoint(_mapper.getMapping(GamePosition.Middle4).getPosition()));
        _gamePositionMappings.put(GamePosition.Middle5, calcRelativePoint(_mapper.getMapping(GamePosition.Middle5).getPosition()));
        _gamePositionMappings.put(GamePosition.Middle6, calcRelativePoint(_mapper.getMapping(GamePosition.Middle6).getPosition()));
        _gamePositionMappings.put(GamePosition.Middle7, calcRelativePoint(_mapper.getMapping(GamePosition.Middle7).getPosition()));


        _gamePositionMappings.put(GamePosition.Center0, calcRelativePoint(_mapper.getMapping(GamePosition.Center0).getPosition()));
        _gamePositionMappings.put(GamePosition.Center1, calcRelativePoint(_mapper.getMapping(GamePosition.Center1).getPosition()));
        _gamePositionMappings.put(GamePosition.Center2, calcRelativePoint(_mapper.getMapping(GamePosition.Center2).getPosition()));
        _gamePositionMappings.put(GamePosition.Center3, calcRelativePoint(_mapper.getMapping(GamePosition.Center3).getPosition()));
        _gamePositionMappings.put(GamePosition.Center4, calcRelativePoint(_mapper.getMapping(GamePosition.Center4).getPosition()));
        _gamePositionMappings.put(GamePosition.Center5, calcRelativePoint(_mapper.getMapping(GamePosition.Center5).getPosition()));
        _gamePositionMappings.put(GamePosition.Center6, calcRelativePoint(_mapper.getMapping(GamePosition.Center6).getPosition()));
        _gamePositionMappings.put(GamePosition.Center7, calcRelativePoint(_mapper.getMapping(GamePosition.Center7).getPosition()));

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
        redraw();
    }

    private void drawGameField(GraphicsContext context, List<GameToken> player1Token, List<GameToken> player2Token) {
        player1Token.forEach((gameToken) -> drawGameToken(context, gameToken));
        player2Token.forEach((gameToken) -> drawGameToken(context, gameToken));
        drawActivePosition(context, CursorStatus.getInstance().getActivePosition());

        drawUnusedTokens(context);
    }

    private void drawProgress(GraphicsContext context){
        CursorStatus cs = CursorStatus.getInstance();

        if (cs.getProgress() > 0.25) {

            //GamePosition pos = _mapper.map(new org.opencv.core.Point(cs.getPosX(), cs.getPosY()));
            //Point point = _gamePositionMappings.get(pos);
            Point point = new Point((int) (cs.getPosX() * canvis.getWidth()), (int) (cs.getPosY() * canvis.getHeight()));
            double radius = cs.getProgress() * 50;

            if (point != null) {
                context.setStroke(Color.WHITE);
                context.strokeOval(point.x - radius / 2, point.y - radius / 2, radius, radius);
            }
        }
    }

    private void drawUnusedTokens(GraphicsContext context){
        float radius = 50;

        int p1 = _gameGameController.getUnusedTokensPlayer1();
        int p2 = _gameGameController.getUnusedTokensPlayer2();

        Point startP1 = new Point(45, 60);
        Point startP2 = new Point((int)(canvis.getWidth() - 45), 60);

        for (int i = 0; i < p1; i++){
            drawCircle(context, Color.RED, radius, startP1.x, startP1.y);
            startP1.y += radius + 5;
        }

        for (int i = 0; i < p2; i++){
            drawCircle(context, Color.BLUE, radius, startP2.x, startP2.y);
            startP2.y += radius + 5;
        }
    }

    private void drawActivePosition(GraphicsContext context, GamePosition gamePosition){
        float radius = 25;
        Point point = _gamePositionMappings.get(gamePosition);

        if (point == null){
            return;
        }

        drawCircle(context, Color.BLACK, radius, point.x, point.y);
    }

    private void drawGameToken(GraphicsContext context, GameToken gameToken) {
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

    private void drawGamephase(GraphicsContext context){
        context.rotate(180);
        context.setFill(Color.WHITE);
        context.setFont(new javafx.scene.text.Font("Century Gothic", 42.0));
        context.fillText(_gamephase, -canvis.getWidth() + 10, -canvis.getHeight() + 50, 800);

        context.rotate(180);
    }

    public void setGamephase(String gamephase){
        _gamephase = gamephase;
    }
}
