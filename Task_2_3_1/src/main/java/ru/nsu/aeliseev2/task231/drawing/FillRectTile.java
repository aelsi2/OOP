package ru.nsu.aeliseev2.task231.drawing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

/**
 * A tile that draws a filled rectangle.
 *
 * @param paint The rectangle fill paint.
 */
public record FillRectTile(Paint paint) implements Tile {
    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(GraphicsContext context, double size) {
        context.setFill(paint);
        context.fillRect(0, 0, size, size);
    }
}
