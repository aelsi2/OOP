package ru.aeliseev2.task221;

import java.time.Duration;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task221.Restaurant;

class RestaurantTests {
    @Test
    void runMany() {
        var employees = IntStream.range(0, 10).mapToObj(idx -> (Runnable) () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).toList();
        var restaurant = new Restaurant(employees);
        Assertions.assertTimeout(Duration.ofMillis(1010), restaurant::run);
    }
}
