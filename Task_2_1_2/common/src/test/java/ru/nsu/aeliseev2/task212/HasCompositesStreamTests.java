package ru.nsu.aeliseev2.task212;

import ru.nsu.aeliseev2.task212.algorithms.ParallelStreamPrimeChecker;
import ru.nsu.aeliseev2.task212.algorithms.PrimeChecker;

class HasCompositesStreamTests extends HasCompositesTests {
    @Override
    protected PrimeChecker getChecker() {
        return new ParallelStreamPrimeChecker();
    }
}
