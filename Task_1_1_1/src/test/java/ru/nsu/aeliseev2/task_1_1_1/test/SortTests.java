package ru.nsu.aeliseev2.task_1_1_1.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task_1_1_1.ArrayUtils;

import java.util.Random;

public class SortTests {
    private SortTests() {
    }

    @Test
    void nullArray() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ArrayUtils.heapsort(null));
    }

    @Test
    void empty() {
        var input = new int[0];
        var expected = new int[0];
        var result = ArrayUtils.heapsort(input);
        Assertions.assertEquals(input, result);
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void singleElement() {
        var input = new int[]{1};
        var expected = new int[]{1};
        var result = ArrayUtils.heapsort(input);
        Assertions.assertEquals(input, result);
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void smallArrayEven() {
        var input = new int[]{-7, 8, 800, 555, 35, 35};
        var expected = new int[]{-7, 8, 35, 35, 555, 800};
        var result = ArrayUtils.heapsort(input);
        Assertions.assertEquals(input, result);
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void smallArrayOdd() {
        var input = new int[]{4, 1, 54, 2, 5, 6, 0, -10, 20};
        var expected = new int[]{-10, 0, 1, 2, 4, 5, 6, 20, 54};
        var result = ArrayUtils.heapsort(input);
        Assertions.assertEquals(input, result);
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void duplicates() {
        var input = new int[]{6, 6, 6, -3, -3, 9, 9, 9, 9, 4, 4, 4};
        var expected = new int[]{-3, -3, 4, 4, 4, 6, 6, 6, 9, 9, 9, 9};
        var result = ArrayUtils.heapsort(input);
        Assertions.assertEquals(input, result);
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void largeArray() {
        var input = new int[]{
                581, 715, -706, -121, 251, 170, -221, 551, -481, 584, -858, -384, 64, -579, -122,
                470, -579, -324, -349, 613, -235, 132, 119, 277, -610, 217, 691, 535, 972, -965,
                -607, -372, -726, 170, 581, 407, -128, 538, -685, 139, 697, -620, -670, 886, 811,
                -538, -741, 634, 436, -595, -57, 505, -918, -417, -692, 394, 626, 380, -568, -588,
                508, -961, 534, 289, 177, 47, -182, -66, 374, 189, -863, 137, -508, 173, 166, -377,
                -672, -901, -739, 830, 702, -847, -869, 411, 843, -992, 694, -230, 959, 34, -364,
                174, -357, 316, -905, -348, -785, 289, -654, 501
        };
        var expected = new int[]{
                -992, -965, -961, -918, -905, -901, -869, -863, -858, -847, -785, -741, -739, -726,
                -706, -692, -685, -672, -670, -654, -620, -610, -607, -595, -588, -579, -579, -568,
                -538, -508, -481, -417, -384, -377, -372, -364, -357, -349, -348, -324, -235, -230,
                -221, -182, -128, -122, -121, -66, -57, 34, 47, 64, 119, 132, 137, 139, 166, 170,
                170, 173, 174, 177, 189, 217, 251, 277, 289, 289, 316, 374, 380, 394, 407, 411,
                436, 470, 501, 505, 508, 534, 535, 538, 551, 581, 581, 584, 613, 626, 634, 691,
                694, 697, 702, 715, 811, 830, 843, 886, 959, 972
        };
        var result = ArrayUtils.heapsort(input);
        Assertions.assertEquals(input, result);
        Assertions.assertArrayEquals(expected, result);
    }
}
