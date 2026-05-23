package ru.nsu.aeliseev2.task212.algorithms;

/**
 * An abstract strategy for checking if an array contains any non-prime (composite) numbers.
 */
public abstract class PrimeChecker {
    /**
     * Checks if the specified number is prime.
     *
     * @param number The number to check.
     * @return {@code true} if {@code number} is prime, {@code false} otherwise.
     */
    public static boolean isPrime(long number) {
        if (number < 2) {
            return false;
        }
        for (long i = 2; i <= number / 2; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the specified array contains any non-prime (composite numbers).
     *
     * @param numbers    The array to search for composite numbers.
     * @param startIndex The start index in the number array.
     * @param endIndex   The start index in the number array.
     * @return {@code true} if {@code numbers} contains at least one composite number, {@code false}
     *     otherwise.
     * @throws InterruptedException The operation was interrupted.
     */
    public abstract boolean hasComposites(
        long[] numbers, int startIndex, int endIndex
    ) throws InterruptedException;
}
