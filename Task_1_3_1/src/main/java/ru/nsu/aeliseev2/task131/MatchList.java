package ru.nsu.aeliseev2.task131;

class MatchList {
    private static final int DEFAULT_CAPACITY = 32;

    private int[] array;
    private int size;

    public MatchList() {
        size = 0;
        array = new int[DEFAULT_CAPACITY];
    }

    public int increment(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index was outside of the list's bounds.");
        }
        array[index]++;
        return array[index];
    }

    public void remove(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index was outside of the list's bounds.");
        }
        System.arraycopy(array, index + 1, array, index, size - index);
        size--;
    }

    public int get(int index) {
        return array[index];
    }

    public void add() {
        if (size + 1 >= array.length) {
            var newArray = new int[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
        array[size++] = 0;
    }

    public int size() {
        return size;
    }
}
