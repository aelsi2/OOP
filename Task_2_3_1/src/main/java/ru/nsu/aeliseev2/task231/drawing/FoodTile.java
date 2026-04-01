package ru.nsu.aeliseev2.task231.drawing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public record FoodTile(Paint paint) implements Tile {
    @Override
    public void draw(GraphicsContext context, double size) {
        context.setFill(paint);
        context.fillOval(size / 4, size / 4, size / 2, size / 2);
    }
}
