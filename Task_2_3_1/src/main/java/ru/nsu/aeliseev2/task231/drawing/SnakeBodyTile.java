package ru.nsu.aeliseev2.task231.drawing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public record SnakeBodyTile(Paint primary, Paint secondary, double rotation) implements Tile {
    @Override
    public void draw(GraphicsContext context, double size) {
        context.save();
        context.translate(size / 2, size / 2);
        context.rotate(-rotation);
        context.translate(-size / 2, -size / 2);
        context.setFill(secondary);
        context.fillRect(size / 4, size / 4, size / 2, size / 2);
        context.setFill(primary);
        context.fillRect(3 * size / 4, size / 4, size / 2, size / 2);
        context.restore();
    }
}
