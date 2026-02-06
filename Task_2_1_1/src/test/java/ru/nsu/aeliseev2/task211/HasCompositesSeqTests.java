package ru.nsu.aeliseev2.task211;

class HasCompositesSeqTests extends HasCompositesTests {
    @Override
    protected PrimeChecker getChecker() {
        return new SequentialPrimeChecker();
    }
}
