package ru.aeliseev2.task221;

import java.time.Duration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task221.Order;
import ru.nsu.aeliseev2.task221.OrderQueue;

class OrderQueueTests {
    @Test
    void empty() throws InterruptedException {
        var queue = new OrderQueue(0);
        Assertions.assertNull(queue.take());
    }

    @Test
    void beginEnd() {
        var queue = new OrderQueue(1);
        queue.putBegin();
        new Thread(() -> {
            try {
                Thread.sleep(50);
                queue.put(new Order(69));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                queue.putEnd();
            }
        }).start();
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            Assertions.assertEquals(new Order(69), queue.take());
        });
    }

    @Test
    void beginEndMultiple() {
        var queue = new OrderQueue(1);
        queue.putBegin();
        queue.putBegin();
        queue.putBegin();
        new Thread(() -> {
            try {
                Thread.sleep(50);
                queue.put(new Order(69));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                queue.putEnd();
                queue.putEnd();
                queue.putEnd();
            }
        }).start();
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            Assertions.assertEquals(new Order(69), queue.take());
        });
    }

    @Test
    void limitedCap() throws InterruptedException {
        var queue = new OrderQueue(2);
        queue.putBegin();
        queue.put(new Order(10));
        queue.put(new Order(20));
        new Thread(() -> {
            try {
                Thread.sleep(50);
                queue.take();
                queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            queue.put(new Order(20));
        });
    }

    @Test
    void putWithoutBegin() {
        var queue = new OrderQueue(0);
        Assertions.assertThrows(IllegalStateException.class, () -> {
            queue.put(new Order(5));
        });
    }

    @Test
    void putEndWithoutBegin() {
        var queue = new OrderQueue(0);
        Assertions.assertThrows(IllegalStateException.class, queue::putEnd);
    }

    @Test
    void tooManyEnds() {
        var queue = new OrderQueue(0);
        queue.putBegin();
        Assertions.assertThrows(IllegalStateException.class, () -> {
            queue.putEnd();
            queue.putEnd();
        });
    }
}
