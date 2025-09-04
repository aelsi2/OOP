package ru.nsu.aeliseev2.task_1_1_1;

/**
 * Integer array utilities.
 */
public final class ArrayUtils {
    private ArrayUtils() {
    }

    private static int leftChildIndex(int index) {
        return index * 2 + 1;
    }

    private static int rightChildIndex(int index) {
        return index * 2 + 2;
    }

    private static void swap(int[] array, int indexA, int indexB) {
        int temp = array[indexA];
        array[indexA] = array[indexB];
        array[indexB] = temp;
    }

    private static void siftDown(int[] array, int length, int index) {
        while (leftChildIndex(index) < length) {
            int childIndex = leftChildIndex(index);
            int rightIndex = rightChildIndex(index);
            if (rightIndex < length && array[rightIndex] > array[childIndex]) {
                childIndex = rightIndex;
            }
            if (array[index] < array[childIndex]) {
                swap(array, index, childIndex);
                index = childIndex;
            } else {
                break;
            }
        }
    }

    private static void makeHeap(int[] array) {
        for (int index = array.length - 1; index >= 0; index--) {
            siftDown(array, array.length, index);
        }
    }

    /**
     * Sorts an integer array in place using the heap sort algorithm.
     *
     * @param array The array to sort.
     * @return The value of {@code array}
     * @throws IllegalArgumentException The value of {@code array} is {@code null}
     */
    public static int[] heapsort(int[] array) throws IllegalArgumentException {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }
        makeHeap(array);
        for (int endIndex = array.length - 1; endIndex > 0; endIndex--) {
            swap(array, 0, endIndex);
            siftDown(array, endIndex, 0);
        }
        return array;
    }
}
