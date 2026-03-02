package ru.aeliseev2.task221;

import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task221.Baker;
import ru.nsu.aeliseev2.task221.Order;
import ru.nsu.aeliseev2.task221.OrderQueue;

class BakerTests {
    @Test
    void empty() {
        var strategy = DummyWorkingStrategy.INSTANCE;
        List<Order> orders = List.of();
        var inQueue = OrderQueueUtils.createQueue(orders);
        var outQueue = new OrderQueue(5);
        var logger = new TestOrderLogger();
        var baker = new Baker(inQueue, outQueue, logger, strategy);
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            baker.run();
            Assertions.assertEquals(orders, OrderQueueUtils.getOrders(outQueue));
        });
        Assertions.assertEquals(0, logger.created);
        Assertions.assertEquals(0, logger.cooking);
        Assertions.assertEquals(0, logger.cooked);
        Assertions.assertEquals(0, logger.delivering);
        Assertions.assertEquals(0, logger.delivered);
    }

    @Test
    void putNormal() {
        var strategy = DummyWorkingStrategy.INSTANCE;
        List<Order> orders = List.of(new Order(100), new Order(200), new Order(123));
        var inQueue = OrderQueueUtils.createQueue(orders);
        var outQueue = new OrderQueue(5);
        var logger = new TestOrderLogger();
        var baker = new Baker(inQueue, outQueue, logger, strategy);
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            baker.run();
            Assertions.assertEquals(orders, OrderQueueUtils.getOrders(outQueue));
        });
        Assertions.assertEquals(0, logger.created);
        Assertions.assertEquals(3, logger.cooking);
        Assertions.assertEquals(3, logger.cooked);
        Assertions.assertEquals(0, logger.delivering);
        Assertions.assertEquals(0, logger.delivered);
    }

    @Test
    void smallOutCap() {
        var strategy = DummyWorkingStrategy.INSTANCE;
        List<Order> orders = List.of(new Order(100), new Order(200), new Order(123));
        var inQueue = OrderQueueUtils.createQueue(orders);
        var outQueue = new OrderQueue(1);
        var logger = new TestOrderLogger();
        var baker = new Baker(inQueue, outQueue, logger, strategy);
        new Thread(baker).start();
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            Assertions.assertEquals(orders, OrderQueueUtils.getOrders(outQueue));
        });
        Assertions.assertEquals(0, logger.created);
        Assertions.assertEquals(3, logger.cooking);
        Assertions.assertEquals(3, logger.cooked);
        Assertions.assertEquals(0, logger.delivering);
        Assertions.assertEquals(0, logger.delivered);
    }

    @Test
    void slowInput() {
        var strategy = DummyWorkingStrategy.INSTANCE;
        List<Order> orders = List.of(new Order(100), new Order(200), new Order(123));
        var outQueue = new OrderQueue(5);
        var logger = new TestOrderLogger();
        var inQueue = OrderQueueUtils.createDelayedQueue(50, orders);
        var baker = new Baker(inQueue, outQueue, logger, strategy);
        new Thread(baker).start();
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            Assertions.assertEquals(orders, OrderQueueUtils.getOrders(outQueue));
        });
        Assertions.assertEquals(0, logger.created);
        Assertions.assertEquals(3, logger.cooking);
        Assertions.assertEquals(3, logger.cooked);
        Assertions.assertEquals(0, logger.delivering);
        Assertions.assertEquals(0, logger.delivered);
    }
}
