package ru.nsu.aeliseev2.task231.app;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import ru.nsu.aeliseev2.task231.app.drawing.FillRectTile;
import ru.nsu.aeliseev2.task231.app.drawing.FoodTile;
import ru.nsu.aeliseev2.task231.app.drawing.SnakeBodyTile;
import ru.nsu.aeliseev2.task231.app.drawing.SnakeHeadTile;
import ru.nsu.aeliseev2.task231.app.drawing.Tile;

/**
 * Default {@code TileMap} palettes for the game.
 */
public class Palettes {
    /**
     * Green checkered grass background palette.
     */
    public static final Tile[] BACKGROUND_GRASS = new Tile[]{
        new FillRectTile(Paint.valueOf("#5fa34f")),
        new FillRectTile(Paint.valueOf("#70b262")),
    };

    /**
     * Blue striped snake and red circular food foreground palette.
     */
    public static final Tile[] FOREGROUND_BLUE_RED = makeForegroundPalette(
        Paint.valueOf("#65b5f2"),
        Paint.valueOf("#3887c2"),
        Paint.valueOf("#f2665c"));

    private Palettes() {
    }

    private static Tile[] makeForegroundPalette(
        Paint snakePrimary,
        Paint snakeSecondary,
        Paint food) {
        Tile[] palette = new Tile[10];
        palette[0] = new FillRectTile(Color.TRANSPARENT);
        for (int i = 0; i < 4; i++) {
            palette[1 + i] = new SnakeBodyTile(
                snakePrimary, snakeSecondary, 180 - i * 90);
            palette[1 + i + 4] = new SnakeHeadTile(
                snakePrimary, snakeSecondary, 180 - i * 90);
        }
        palette[9] = new FoodTile(food);
        return palette;
    }

}
