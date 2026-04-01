package ru.nsu.aeliseev2.task231.app;

import javafx.fxml.FXML;
import ru.nsu.aeliseev2.task231.app.drawing.TileMap;

public class SnakeController {
    @FXML
    private TileMap background;

    @FXML
    private TileMap foreground;

    @FXML
    private void initialize() {
        background.setData(makeBackground(10, 10));
        background.setMapWidth(10);
        background.setMapHeight(10);
        background.setPalette(Palettes.BACKGROUND_GRASS);
        foreground.setMapWidth(10);
        foreground.setMapHeight(10);
        foreground.setPalette(Palettes.FOREGROUND_BLUE_RED);
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
