package ru.nsu.aeliseev2.task231.model;

/**
 * A strategy for determining if the player has won on a given game tick.
 */
public interface WinCondition {
    /**
     * Determines if the player has won.
     *
     * @param data   The game map data.
     * @param width  The width of the game map in tiles.
     * @param height The height of the game map in tiles.
     * @param score  The current score of the game.
     * @return Whether the player has lost.
     */
    boolean isWin(int[] data, int width, int height, int score);
}
