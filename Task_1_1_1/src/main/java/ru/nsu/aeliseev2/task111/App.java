package ru.nsu.aeliseev2.task111;

import java.util.Arrays;

/**
 * Main application class.
 */
public final class App {
    private App() {
    }

    /**
     * Application entry point.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        var array = new int[]{5, 4, 3, 2, 1};
        ArrayUtils.heapsort(array);
        System.out.println(Arrays.toString(array));
    }
}
