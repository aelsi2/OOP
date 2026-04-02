package ru.nsu.aeliseev2.task231.app;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import ru.nsu.aeliseev2.task231.app.drawing.TileMap;
import ru.nsu.aeliseev2.task231.model.Direction;
import ru.nsu.aeliseev2.task231.model.Game;
import ru.nsu.aeliseev2.task231.model.GameState;
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
    @FXML
    private Parent menu;
    @FXML
    private Spinner<Integer> widthSpinner;
    @FXML
    private Spinner<Integer> heightSpinner;
    @FXML
    private Spinner<Integer> foodSpinner;
    @FXML
    private Spinner<Integer> speedSpinner;

    private Game game;
    private Direction direction;
    private Timeline timeline;

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
    private void onStartClick(ActionEvent ignored) {
        menu.setVisible(false);
        int width = widthSpinner.getValue();
        int height = heightSpinner.getValue();
        int food = foodSpinner.getValue();
        int speed = speedSpinner.getValue();
        startNewGame(width, height, width * height - 1, food, 1.0 / (speed + 2));
    }

    @FXML
    private void initialize() {
        initializeLayers(null, 10, 10);
    }

    private void startNewGame(int width, int height, int winScore, int foodCount, double delay) {
        game = new Game(width, height,
            new ScoreWinCondition(winScore),
            new RandomFoodPlacer(foodCount));
        initializeLayers(game.getData(), game.getMapWidth(), game.getMapHeight());
        direction = Direction.RIGHT;
        updateScreen(GameState.RUNNING);

        timeline = new Timeline(
            new KeyFrame(Duration.seconds(delay), event -> {
                var state = game.tick(direction);
                if (state != GameState.RUNNING) {
                    endGame();
                }
                updateScreen(state);
            })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void endGame() {
        timeline.stop();
        menu.setVisible(true);
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

    private void updateScreen(GameState state) {
        foreground.redraw();
        String format;
        switch (state) {
            case WIN:
                format = "You won! Score: %d";
                break;
            case LOSE:
                format = "You lost! Score: %d";
                break;
            default:
                format = "%d";
                break;
        }
        score.setText(String.format(format, game.getScore()));
    }
}
