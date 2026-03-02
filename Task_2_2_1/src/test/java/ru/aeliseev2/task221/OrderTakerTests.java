package ru.aeliseev2.task221;

import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task221.Order;
import ru.nsu.aeliseev2.task221.OrderQueue;
import ru.nsu.aeliseev2.task221.OrderTaker;

class OrderTakerTests {
    @Test
    void empty() {
        var strategy = DummyWorkingStrategy.INSTANCE;
        List<Order> orders = List.of();
        var factory = new TestOrderFactory(orders);
        var queue = new OrderQueue(5);
        var logger = new TestOrderLogger();
        var orderTaker = new OrderTaker(factory, queue, logger, strategy);
        orderTaker.run();
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            Assertions.assertEquals(orders, OrderQueueUtils.getOrders(queue));
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
        var orders = List.of(
            new Order(6), new Order(9), new Order(4), new Order(2), new Order(0));
        var factory = new TestOrderFactory(orders);
        var queue = new OrderQueue(5);
        var logger = new TestOrderLogger();
        var orderTaker = new OrderTaker(factory, queue, logger, strategy);
        orderTaker.run();
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            Assertions.assertEquals(orders, OrderQueueUtils.getOrders(queue));
        });
        Assertions.assertEquals(5, logger.created);
        Assertions.assertEquals(0, logger.cooking);
        Assertions.assertEquals(0, logger.cooked);
        Assertions.assertEquals(0, logger.delivering);
        Assertions.assertEquals(0, logger.delivered);
    }

    @Test
    void smallQueueCap() {
        var strategy = DummyWorkingStrategy.INSTANCE;
        var orders = List.of(
            new Order(6), new Order(9), new Order(4), new Order(2), new Order(0));
        var factory = new TestOrderFactory(orders);
        var queue = new OrderQueue(3);
        var logger = new TestOrderLogger();
        var orderTaker = new OrderTaker(factory, queue, logger, strategy);
        new Thread(orderTaker).start();
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            Assertions.assertEquals(orders, OrderQueueUtils.getOrders(queue));
        });
        Assertions.assertEquals(5, logger.created);
        Assertions.assertEquals(0, logger.cooking);
        Assertions.assertEquals(0, logger.cooked);
        Assertions.assertEquals(0, logger.delivering);
        Assertions.assertEquals(0, logger.delivered);
    }
}
