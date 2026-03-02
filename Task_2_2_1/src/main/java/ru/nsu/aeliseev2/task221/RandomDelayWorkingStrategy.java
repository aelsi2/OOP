package ru.nsu.aeliseev2.task221;

import java.util.Random;

/**
 * An implementation of {@code WorkingStrategy} that sleeps for a random amount of time within the
 * specified bounds.
 */
public class RandomDelayWorkingStrategy implements WorkingStrategy {
    private final Random random;
    private final int minDelayMillis;
    private final int maxDelayMillis;

    /**
     * Creates a new instance of {@code RandomDelayWorkingStrartegy}.
     *
     * @param random         The random number generator to use.
     * @param minDelayMillis The minimum delay in milliseconds.
     * @param maxDelayMillis The maximum delay in milliseconds.
     */
    public RandomDelayWorkingStrategy(Random random, int minDelayMillis, int maxDelayMillis) {
        this.random = random;
        this.minDelayMillis = minDelayMillis;
        this.maxDelayMillis = maxDelayMillis;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doWork() throws InterruptedException {
        Thread.sleep(random.nextInt(minDelayMillis, maxDelayMillis + 1));
    }
}
