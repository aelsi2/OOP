package ru.nsu.aeliseev2.task231;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import ru.nsu.aeliseev2.task231.drawing.FillRectTile;
import ru.nsu.aeliseev2.task231.drawing.FoodTile;
import ru.nsu.aeliseev2.task231.drawing.SnakeBodyTile;
import ru.nsu.aeliseev2.task231.drawing.SnakeHeadTile;
import ru.nsu.aeliseev2.task231.drawing.Tile;
import ru.nsu.aeliseev2.task231.drawing.TileLayer;

public class SnakeController {
    private static final Tile[] BG_PALETTE = new Tile[]{
        new FillRectTile(Paint.valueOf("#5fa34f")),
        new FillRectTile(Paint.valueOf("#70b262")),
    };
    private static final Tile[] FG_PALETTE = makeForegroundPalette(
        Paint.valueOf("#53a4e2"),
        Paint.valueOf("#3887c2"),
        Paint.valueOf("#eb5146"));

    @FXML
    private TileLayer background;

    @FXML
    private TileLayer foreground;

    @FXML
    private void initialize() {
        background.setData(makeBackground(10, 10));
        background.setFieldWidth(10);
        background.setFieldHeight(10);
        background.setPalette(BG_PALETTE);
        foreground.setFieldWidth(10);
        foreground.setFieldHeight(10);
        foreground.setPalette(FG_PALETTE);
    }

    private static Tile[] makeForegroundPalette(
        Paint snakePrimary,
        Paint snakeSecondary,
        Paint food) {
        Tile[] palette = new Tile[10];
        palette[0] = new FillRectTile(Color.TRANSPARENT);
        for (int i = 0; i < 4; i++) {
            palette[1 + i] = new SnakeBodyTile(snakePrimary, snakeSecondary, 180 - i * 90);
            palette[1 + i + 4] = new SnakeHeadTile(snakePrimary, snakeSecondary, 180 - i * 90);
        }
        palette[9] = new FoodTile(food);
        return palette;
    }

    private static int[] makeBackground(int width, int height) {
        int[] data = new int[width * height];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                data[row * width + col] = (row + col) % 2;
            }
        }
        return data;
    }
}
