package ru.nsu.aeliseev2.task_1_1_1;

import java.util.Arrays;

/**
 * Main application class.
 */
public final class App {
    private App() {
    }

    /**
     * Application entry point.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        int[] result = ArrayUtils.heapsort(new int[]{5, 4, 3, 2, 1});
        System.out.println(Arrays.toString(result));
    }
}
