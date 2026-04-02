package ru.nsu.aeliseev2.task231.app;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import ru.nsu.aeliseev2.task231.app.drawing.TileMap;
import ru.nsu.aeliseev2.task231.model.Direction;
import ru.nsu.aeliseev2.task231.model.Game;
import ru.nsu.aeliseev2.task231.model.RandomFoodPlacer;
import ru.nsu.aeliseev2.task231.model.ScoreWinCondition;

/**
 * The controller for the main game window.
 */
public class SnakeController {
    @FXML
    private TileMap background;

    @FXML
    private TileMap foreground;

    @FXML
    private Label score;

    private Game game;
    private Direction direction;

    /**
     * Handler method key presses. Changes the snake's movement direction when an arrow key is
     * pressed.
     *
     * @param code The key code.
     */
    public void handleKeyPress(KeyCode code) {
        switch (code) {
            case LEFT:
                direction = Direction.LEFT;
                break;
            case UP:
                direction = Direction.UP;
                break;
            case RIGHT:
                direction = Direction.RIGHT;
                break;
            case DOWN:
                direction = Direction.DOWN;
                break;
            default:
                break;
        }
    }

    @FXML
    private void initialize() {
        game = new Game(15, 15,
            new ScoreWinCondition(100),
            new RandomFoodPlacer(3));
        direction = Direction.RIGHT;
        initializeLayers(game.getData(), game.getMapWidth(), game.getMapHeight());
        updateScreen();

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0.2), event -> {
                game.tick(direction);
                updateScreen();
            })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void initializeLayers(int[] fgData, int width, int height) {
        background.setMapWidth(width);
        background.setMapHeight(height);
        foreground.setMapWidth(width);
        foreground.setMapHeight(height);
        int[] bgData = new int[width * height];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                bgData[row * width + col] = (row + col) % 2;
            }
        }
        foreground.setData(fgData);
        background.setData(bgData);
    }

    private void updateScreen() {
        foreground.redraw();
        score.setText(String.format("%d", game.getScore()));
    }
}
