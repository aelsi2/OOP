package ru.nsu.aeliseev2.task221;

/**
 * A strategy for simulating work that takes some time.
 */
public interface WorkingStrategy {
    /**
     * Simulates work.
     *
     * @throws InterruptedException The current thread was interrupted.
     */
    void doWork() throws InterruptedException;
}
