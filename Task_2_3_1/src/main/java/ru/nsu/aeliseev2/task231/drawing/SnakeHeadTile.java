package ru.nsu.aeliseev2.task231.drawing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

/**
 * A tile that draws the snake's head.
 *
 * @param primary   The primary paint.
 * @param secondary The secondary paint.
 * @param rotation  The rotation of this tile in degrees (counter-clockwise, starting from pointing
 *                  to the right).
 */
public record SnakeHeadTile(Paint primary, Paint secondary, double rotation) implements Tile {
    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(GraphicsContext context, double size) {
        context.save();
        context.translate(size / 2, size / 2);
        context.rotate(-rotation);
        context.translate(-size / 2, -size / 2);
        context.setFill(primary);
        context.fillRect(size / 8, size / 8, 3 * size / 4, 3 * size / 4);
        context.setFill(secondary);
        context.fillOval(0.6 * size, 0.6 * size, size / 8, size / 8);
        context.fillOval(0.6 * size, 0.3 * size, size / 8, size / 8);
        context.restore();
    }
}
