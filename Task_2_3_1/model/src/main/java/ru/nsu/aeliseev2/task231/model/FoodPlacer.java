package ru.nsu.aeliseev2.task231.model;

/**
 * A strategy for placing bits of food on each game tick.
 */
public interface FoodPlacer {
    /**
     * Places zero or more bits of food.
     *
     * @param data   The game map data.
     * @param width  The width of the game map in tiles.
     * @param height The height of the game map in tiles.
     * @param score  The current score of the game.
     */
    void placeFood(int[] data, int width, int height, int score);
}
