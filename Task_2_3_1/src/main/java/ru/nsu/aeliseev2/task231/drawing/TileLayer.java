package ru.nsu.aeliseev2.task231.drawing;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class TileLayer extends Pane {
    private final Canvas canvas;

    private final IntegerProperty fieldWidthProperty = new SimpleIntegerProperty();
    private final IntegerProperty fieldHeightProperty = new SimpleIntegerProperty();
    private final ObjectProperty<Tile[]> paletteProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<int[]> dataProperty = new SimpleObjectProperty<>();

    public int getFieldWidth() {
        return fieldWidthProperty.get();
    }

    public void setFieldWidth(int value) {
        fieldWidthProperty.set(value);
    }

    public IntegerProperty fieldWidthProperty() {
        return fieldWidthProperty;
    }

    public int getFieldHeight() {
        return fieldHeightProperty.get();
    }

    public void setFieldHeight(int value) {
        fieldHeightProperty.set(value);
    }

    public IntegerProperty fieldHeightProperty() {
        return fieldHeightProperty;
    }

    public Tile[] getPalette() {
        return paletteProperty.get();
    }

    public void setPalette(Tile[] value) {
        paletteProperty.set(value);
    }

    public ObjectProperty<Tile[]> paletteProperty() {
        return paletteProperty;
    }

    public int[] getData() {
        return dataProperty.get();
    }

    public void setData(int[] value) {
        dataProperty.set(value);
    }

    public ObjectProperty<int[]> dataProperty() {
        return dataProperty;
    }

    public TileLayer() {
        canvas = new Canvas();
        getChildren().add(canvas);

        canvas.widthProperty().bind(widthProperty());
        canvas.heightProperty().bind(heightProperty());
        canvas.widthProperty().addListener(observable -> redraw());
        canvas.heightProperty().addListener(observable -> redraw());
        paletteProperty().addListener(observable -> redraw());
        dataProperty().addListener(observable -> redraw());
        fieldWidthProperty().addListener(observable -> redraw());
        fieldHeightProperty().addListener(observable -> redraw());
    }

    public void redraw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        int columns = getFieldWidth();
        int rows = getFieldHeight();
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
