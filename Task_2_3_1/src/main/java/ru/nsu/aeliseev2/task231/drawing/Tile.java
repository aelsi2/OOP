package ru.nsu.aeliseev2.task231.drawing;

import javafx.scene.canvas.GraphicsContext;

/**
 * A tile drawn in a {@code TileMap}.
 */
public interface Tile {
    /**
     * Draws the tile at position (0, 0).
     *
     * @param context The graphics context to draw the tile in.
     * @param size The size of the tile.
     */
    void draw(GraphicsContext context, double size);
}
