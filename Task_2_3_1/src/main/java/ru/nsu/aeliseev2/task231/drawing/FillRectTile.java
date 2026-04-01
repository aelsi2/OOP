package ru.nsu.aeliseev2.task231.drawing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public record FillRectTile(Paint paint) implements Tile {
    @Override
    public void draw(GraphicsContext context, double size) {
        context.setFill(paint);
        context.fillRect(0, 0, size, size);
    }
}
