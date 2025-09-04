package ru.nsu.aeliseev2.task_1_1_1;

import java.util.Arrays;

/**
 * @hidden
 */
public final class App {
    private App() {
    }

    /**
     * @hidden
     */
    public static void main(String[] args) {
        int[] result = ArrayUtils.heapsort(new int[]{5, 4, 3, 2, 1});
        System.out.println(Arrays.toString(result));
    }
}
