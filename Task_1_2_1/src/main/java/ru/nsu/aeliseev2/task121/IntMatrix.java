package ru.nsu.aeliseev2.task121;

import java.util.Arrays;

class IntMatrix {
    private static final int MIN_CAPACITY = 16;
    private int[] array;
    private int width;
    private int height;

    public IntMatrix(int width, int height) {
        this.width = width;
        this.height = height;
        this.array = new int[Math.max(width * height, MIN_CAPACITY)];
    }

    public IntMatrix() {
        this(0, 0);
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public void set(int row, int column, int value) {
        array[width * row + column] = value;
    }

    public int get(int row, int column) {
        return array[width * row + column];
    }

    public void addRow() {
        int newWidth = width;
        int newHeight = height + 1;
        int[] newArray = array;
        if (newArray.length < newWidth * newHeight) {
            newArray = new int[Math.max(newArray.length, MIN_CAPACITY) * 2];
            System.arraycopy(array, 0, newArray, 0, width * height);
        }
        Arrays.fill(newArray, width * height, newWidth * newHeight, 0);
        array = newArray;
        height = newHeight;
    }

    public void addColumn() {
        int newWidth = width + 1;
        int newHeight = height;
        int[] newArray = array;
        if (newArray.length < newWidth * newHeight) {
            newArray = new int[Math.max(newArray.length, MIN_CAPACITY) * 2];
        }
        for (int i = height - 1; i >= 0; i--) {
            System.arraycopy(array, i * width, newArray, i * newWidth, width);
            newArray[i * newWidth + newWidth - 1] = 0;
        }
        array = newArray;
        width = newWidth;
    }

    public void removeRow(int index) {
        System.arraycopy(
            array,
            (index + 1) * width,
            array,
            index * width,
            (height - 1 - index) * width);
        height--;
    }

    public void removeColumn(int index) {
        for (int i = 0; i < height; i++) {
            System.arraycopy(
                array,
                i * width,
                array,
                i * (width - 1),
                index
            );
            System.arraycopy(
                array,
                i * width + index + 1,
                array,
                i * (width - 1) + index,
                width - index - 1
            );
        }
        width--;
    }

    public void removeAll() {
        width = 0;
        height = 0;
    }

    public void removeRows() {
        height = 0;
    }
}
