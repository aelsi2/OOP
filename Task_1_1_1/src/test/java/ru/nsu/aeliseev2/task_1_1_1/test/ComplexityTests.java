package ru.nsu.aeliseev2.task_1_1_1.test;

import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task_1_1_1.ArrayUtils;

import java.util.Random;

public class ComplexityTests {
    @Test
    void benchmark() {
        int repeatCount = 10000;
        var sizes = new int[] {100, 200, 300, 400, 500, 1000, 2000};
        var random = new Random();
        for (int size : sizes) {
            var arrays = new int[repeatCount][size];
            for (int repeatIndex = 0; repeatIndex < repeatCount; repeatIndex++) {
                for (int i = 0; i < size; i++) {
                    arrays[repeatIndex][i] = random.nextInt();
                }
            }
            long startTime = System.nanoTime();
            for (int repeatIndex = 0; repeatIndex < repeatCount; repeatIndex++) {
                ArrayUtils.heapsort(arrays[repeatIndex]);
            }
            long endTime = System.nanoTime();
            long execTime = endTime - startTime;
            System.out.printf("%d elements: %d ms\n", size, execTime / 1_000_000);
        }
    }
}
