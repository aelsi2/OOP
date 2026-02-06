package ru.nsu.aeliseev2.task211;

/**
 * A basic sequential implementation of {@code PrimeChecker}.
 */
public class SequentialPrimeChecker extends PrimeChecker {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasComposites(long[] numbers) {
        for (long number : numbers) {
            if (!isPrime(number)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "SequentialPrimeChecker";
    }
}
