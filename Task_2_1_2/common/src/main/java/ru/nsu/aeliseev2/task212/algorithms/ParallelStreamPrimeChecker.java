package ru.nsu.aeliseev2.task212.algorithms;

import java.util.Arrays;

/**
 * An implementation of {@code PrimeChecker} using Java's parallel streams.
 */
public class ParallelStreamPrimeChecker extends PrimeChecker {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasComposites(long[] numbers, int startIndex, int endIndex) {
        return Arrays.stream(numbers)
            .skip(startIndex)
            .limit(endIndex - startIndex)
            .parallel()
            .anyMatch(value -> !isPrime(value));
    }
}
