package ru.aeliseev2.task221;

import java.time.Duration;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task221.RandomDelayWorkingStrategy;

class RandomDelayWorkingStrategyTests {
    @Test
    void cancel() {
        var strategy = new RandomDelayWorkingStrategy(
            new Random(0), 1000, 1000);
        var mainThread = Thread.currentThread();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(500);
                mainThread.interrupt();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        Assertions.assertThrows(InterruptedException.class, strategy::doWork);
    }

    @Test
    void wait1000ms() {
        var strategy = new RandomDelayWorkingStrategy(
            new Random(0), 1000, 1000);
        Assertions.assertTimeout(Duration.ofSeconds(1010), strategy::doWork);
    }
}
