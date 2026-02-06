package ru.nsu.aeliseev2.task211;

class HasCompositesThreadTests extends HasCompositesTests {
    @Override
    protected PrimeChecker getChecker() {
        return new ThreadedPrimeChecker(12);
    }
}
