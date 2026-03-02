package ru.aeliseev2.task221;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task221.Order;
import ru.nsu.aeliseev2.task221.OrderLogger;
import ru.nsu.aeliseev2.task221.PrintStreamOrderLogger;

class PrintStreamLoggerTests {
    @Test
    void created() {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(os);
        final OrderLogger logger = new PrintStreamOrderLogger(ps);
        final Order order = new Order(69);
        logger.logCreated(order);
        Assertions.assertEquals("[69] created\n", os.toString(StandardCharsets.UTF_8));
    }

    @Test
    void cooking() {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(os);
        final OrderLogger logger = new PrintStreamOrderLogger(ps);
        final Order order = new Order(420);
        logger.logCooking(order);
        Assertions.assertEquals("[420] cooking\n", os.toString(StandardCharsets.UTF_8));
    }

    @Test
    void cooked() {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(os);
        final OrderLogger logger = new PrintStreamOrderLogger(ps);
        final Order order = new Order(1337);
        logger.logCooked(order);
        Assertions.assertEquals("[1337] cooked\n", os.toString(StandardCharsets.UTF_8));
    }

    @Test
    void delivering() {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(os);
        final OrderLogger logger = new PrintStreamOrderLogger(ps);
        final Order order = new Order(67);
        logger.logDelivering(order);
        Assertions.assertEquals("[67] delivering\n", os.toString(StandardCharsets.UTF_8));
    }

    @Test
    void delivered() {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(os);
        final OrderLogger logger = new PrintStreamOrderLogger(ps);
        final Order order = new Order(228);
        logger.logDelivered(order);
        Assertions.assertEquals("[228] delivered\n", os.toString(StandardCharsets.UTF_8));
    }
}
