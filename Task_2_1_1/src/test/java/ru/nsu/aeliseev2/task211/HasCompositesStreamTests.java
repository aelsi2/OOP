package ru.nsu.aeliseev2.task211;

public class HasCompositesStreamTests extends HasCompositesTests {
    @Override
    protected PrimeChecker getChecker() {
        return new ParallelStreamPrimeChecker();
    }
}
