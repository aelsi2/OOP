package ru.nsu.aeliseev2.task231.model;

/**
 * The game of snake.
 */
public class Game {
    private final int mapWidth;
    private final int mapHeight;
    private final int[] data;
    private final WinCondition winCondition;
    private final FoodPlacer foodPlacer;

    private int headX, headY;
    private int tailX, tailY;
    private int score;
    private int growCount;

    /**
     * Initializes a new instance {@code Game}.
     *
     * @param mapWidth     The width of the game map in tiles.
     * @param mapHeight    The height of the game map in tiles.
     * @param winCondition The win condition of the game.
     * @param foodPlacer   The food placing strategy.
     */
    public Game(int mapWidth, int mapHeight, WinCondition winCondition, FoodPlacer foodPlacer) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.data = new int[mapWidth * mapHeight];
        this.winCondition = winCondition;
        this.foodPlacer = foodPlacer;
        this.score = 0;

        this.headX = this.tailX = Math.max(0, (mapWidth - 1) / 2);
        this.headY = this.tailY = Math.max(0, (mapHeight - 1) / 2);
        this.data[getIndex(headX, headY)] = GameTile.HEAD_R.toInt();

        foodPlacer.placeFood(data, mapWidth, mapHeight, score);
    }

    /**
     * Gets the width of the map in tiles.
     *
     * @return Map width.
     */
    public int getMapWidth() {
        return mapWidth;
    }

    /**
     * Gets the height of the map in tiles.
     *
     * @return Map height.
     */
    public int getMapHeight() {
        return mapHeight;
    }

    /**
     * Gets the game map data.
     *
     * @return The game map data.
     */
    public int[] getData() {
        return data;
    }

    /**
     * Gets the current score of the game.
     *
     * @return The score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Advances the game by one step, moving the snake by one tile.
     *
     * @param direction The movement direction.
     * @return The state of the game after this update.
     */
    public GameState tick(Direction direction) {
        int newHeadX = headX + direction.deltaX;
        int newHeadY = headY + direction.deltaY;

        if (newHeadX >= mapWidth || newHeadX < 0) {
            return GameState.LOSE;
        }
        if (newHeadY >= mapHeight || newHeadY < 0) {
            return GameState.LOSE;
        }

        GameTile nextTile = GameTile.fromInt(data[getIndex(newHeadX, newHeadY)]);
        growCount += nextTile.growLength;
        score += nextTile.scoreIncrease;
        if (nextTile.hasCollision) {
            return GameState.LOSE;
        }

        data[getIndex(headX, headY)] = GameTile.BODY_L.rotate(direction).toInt();
        data[getIndex(newHeadX, newHeadY)] = GameTile.HEAD_L.rotate(direction).toInt();
        headX = newHeadX;
        headY = newHeadY;

        if (growCount > 0) {
            growCount -= 1;
        } else {
            Direction tailDir = GameTile.fromInt(data[getIndex(tailX, tailY)]).direction;
            data[getIndex(tailX, tailY)] = GameTile.EMPTY.toInt();
            tailX += tailDir.deltaX;
            tailY += tailDir.deltaY;
        }

        foodPlacer.placeFood(data, mapWidth, mapHeight, score);
        if (winCondition.isWin(data, mapWidth, mapHeight, score)) {
            return GameState.WIN;
        } else {
            return GameState.RUNNING;
        }
    }

    private int getIndex(int x, int y) {
        return y * mapWidth + x;
    }
}
