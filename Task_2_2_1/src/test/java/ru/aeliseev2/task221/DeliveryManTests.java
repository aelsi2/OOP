package ru.aeliseev2.task221;

import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task221.DeliveryMan;
import ru.nsu.aeliseev2.task221.Order;

class DeliveryManTests {
    @Test
    void empty() {
        var strategy = DummyWorkingStrategy.INSTANCE;
        List<Order> orders = List.of();
        var inQueue = OrderQueueUtils.createQueue(orders);
        var logger = new TestOrderLogger();
        var deliveryMan = new DeliveryMan(inQueue, 5, logger, strategy);
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(100), deliveryMan::run);
        Assertions.assertEquals(0, logger.created);
        Assertions.assertEquals(0, logger.cooking);
        Assertions.assertEquals(0, logger.cooked);
        Assertions.assertEquals(0, logger.delivering);
        Assertions.assertEquals(0, logger.delivered);
    }

    @Test
    void oneGo() {
        var strategy = DummyWorkingStrategy.INSTANCE;
        List<Order> orders = List.of(new Order(1), new Order(2), new Order(3));
        var inQueue = OrderQueueUtils.createQueue(orders);
        var logger = new TestOrderLogger();
        var deliveryMan = new DeliveryMan(inQueue, 3, logger, strategy);
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(100), deliveryMan::run);
        Assertions.assertEquals(0, logger.created);
        Assertions.assertEquals(0, logger.cooking);
        Assertions.assertEquals(0, logger.cooked);
        Assertions.assertEquals(3, logger.delivering);
        Assertions.assertEquals(3, logger.delivered);
    }

    @Test
    void oneGoLessThanCap() {
        var strategy = DummyWorkingStrategy.INSTANCE;
        List<Order> orders = List.of(new Order(1), new Order(2), new Order(3));
        var inQueue = OrderQueueUtils.createQueue(orders);
        var logger = new TestOrderLogger();
        var deliveryMan = new DeliveryMan(inQueue, 5, logger, strategy);
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(100), deliveryMan::run);
        Assertions.assertEquals(0, logger.created);
        Assertions.assertEquals(0, logger.cooking);
        Assertions.assertEquals(0, logger.cooked);
        Assertions.assertEquals(3, logger.delivering);
        Assertions.assertEquals(3, logger.delivered);
    }

    @Test
    void multiGo() {
        var strategy = DummyWorkingStrategy.INSTANCE;
        List<Order> orders = List.of(new Order(1), new Order(2), new Order(3));
        var inQueue = OrderQueueUtils.createQueue(orders);
        var logger = new TestOrderLogger();
        var deliveryMan = new DeliveryMan(inQueue, 1, logger, strategy);
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(100), deliveryMan::run);
        Assertions.assertEquals(0, logger.created);
        Assertions.assertEquals(0, logger.cooking);
        Assertions.assertEquals(0, logger.cooked);
        Assertions.assertEquals(3, logger.delivering);
        Assertions.assertEquals(3, logger.delivered);
    }

    @Test
    void slowInput() {
        var strategy = DummyWorkingStrategy.INSTANCE;
        List<Order> orders = List.of(new Order(1), new Order(2), new Order(3));
        var inQueue = OrderQueueUtils.createDelayedQueue(50, orders);
        var logger = new TestOrderLogger();
        var deliveryMan = new DeliveryMan(inQueue, 3, logger, strategy);
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(100), deliveryMan::run);
        Assertions.assertEquals(0, logger.created);
        Assertions.assertEquals(0, logger.cooking);
        Assertions.assertEquals(0, logger.cooked);
        Assertions.assertEquals(3, logger.delivering);
        Assertions.assertEquals(3, logger.delivered);
    }
}
