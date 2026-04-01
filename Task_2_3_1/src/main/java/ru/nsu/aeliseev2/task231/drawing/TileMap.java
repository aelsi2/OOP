package ru.nsu.aeliseev2.task231.drawing;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

/**
 * A GUI element drawing a tile map.
 */
public class TileMap extends Pane {
    private final Canvas canvas;

    private final IntegerProperty mapWidthProperty = new SimpleIntegerProperty();
    private final IntegerProperty mapHeightProperty = new SimpleIntegerProperty();
    private final ObjectProperty<Tile[]> paletteProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<int[]> dataProperty = new SimpleObjectProperty<>();

    /**
     * Gets the map width.
     *
     * @return The width of the tile map in tiles.
     */
    public int getMapWidth() {
        return mapWidthProperty.get();
    }

    /**
     * Sets the map width.
     *
     * @param value The width of the tile map in tiles.
     */
    public void setMapWidth(int value) {
        mapWidthProperty.set(value);
    }

    /**
     * Gets the map width property.
     *
     * @return The map width property.
     */
    public IntegerProperty mapWidthProperty() {
        return mapWidthProperty;
    }

    /**
     * Gets the map height.
     *
     * @return The height of the tile map in tiles.
     */
    public int getMapHeight() {
        return mapHeightProperty.get();
    }

    /**
     * Sets the map height.
     *
     * @param value The height of the tile map in tiles.
     */
    public void setMapHeight(int value) {
        mapHeightProperty.set(value);
    }

    /**
     * Gets the map height property.
     *
     * @return The map height property.
     */
    public IntegerProperty mapHeightProperty() {
        return mapHeightProperty;
    }

    /**
     * Gets the currently used tile palette.
     *
     * @return The tile palette.
     */
    public Tile[] getPalette() {
        return paletteProperty.get();
    }

    /**
     * Sets the currently used tile palette.
     *
     * @param value The tile palette.
     */
    public void setPalette(Tile[] value) {
        paletteProperty.set(value);
    }

    /**
     * Gets the tile palette property.
     *
     * @return The tile palette property.
     */
    public ObjectProperty<Tile[]> paletteProperty() {
        return paletteProperty;
    }

    /**
     * Gets the currently displayed map data. Each element is an index in {@code palette}.
     *
     * @return The map data.
     */
    public int[] getData() {
        return dataProperty.get();
    }

    /**
     * Sets the currently displayed map data. Each element is an index in {@code palette}.
     *
     * @param value  The map data.
     */
    public void setData(int[] value) {
        dataProperty.set(value);
    }

    /**
     * Gets the map data property.
     *
     * @return The map data property.
     */
    public ObjectProperty<int[]> dataProperty() {
        return dataProperty;
    }

    /**
     * Initializes a new instance of {@code TileMap}.
     */
    public TileMap() {
        canvas = new Canvas();
        getChildren().add(canvas);

        canvas.widthProperty().bind(widthProperty());
        canvas.heightProperty().bind(heightProperty());
        canvas.widthProperty().addListener(observable -> redraw());
        canvas.heightProperty().addListener(observable -> redraw());
        paletteProperty().addListener(observable -> redraw());
        dataProperty().addListener(observable -> redraw());
        mapWidthProperty().addListener(observable -> redraw());
        mapHeightProperty().addListener(observable -> redraw());
    }

    /**
     * Redraws the tile map with the current data.
     */
    public void redraw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        int columns = getMapWidth();
        int rows = getMapHeight();
        Tile[] palette = getPalette();
        int[] fieldData = getData();

        if (rows == 0 || columns == 0 || palette == null || fieldData == null
            || fieldData.length < rows * columns) {
            return;
        }

        double xOffset, yOffset, tileSize;
        if (width / height > (double) columns / (double) rows) {
            tileSize = height / rows;
            yOffset = 0;
            xOffset = (width - (columns * tileSize)) / 2;
        } else {
            tileSize = width / columns;
            xOffset = 0;
            yOffset = (height - (rows * tileSize)) / 2;
        }
        gc.clearRect(0, 0, width, height);
        drawLayer(gc, palette, fieldData, columns, rows, xOffset, yOffset, tileSize);
    }

    private static void drawLayer(GraphicsContext gc, Tile[] palette, int[] data,
                                  int columns, int rows, double xOffset, double yOffset,
                                  double tileSize) {
        gc.save();
        gc.translate(xOffset, yOffset);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int index = data[row * columns + col];
                if (index < 0 || index >= palette.length) {
                    continue;
                }
                gc.save();
                gc.translate(col * tileSize, row * tileSize);
                palette[index].draw(gc, tileSize);
                gc.restore();
            }
        }
        gc.restore();
    }
}
