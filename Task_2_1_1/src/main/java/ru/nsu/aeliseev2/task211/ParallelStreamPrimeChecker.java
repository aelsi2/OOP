package ru.nsu.aeliseev2.task211;

import java.util.Arrays;

/**
 * An implementation of {@code PrimeChecker} using Java's parallel streams.
 */
public class ParallelStreamPrimeChecker extends PrimeChecker {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasComposites(long[] numbers) {
        return Arrays.stream(numbers).parallel().anyMatch(value -> !isPrime(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ParallelStreamPrimeChecker";
    }
}
