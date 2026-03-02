package ru.aeliseev2.task221;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task221.Order;
import ru.nsu.aeliseev2.task221.SequentialOrderFactory;

class SequentialOrderFactoryTests {
    @Test
    void createZero() {
        var factory = new SequentialOrderFactory(0);
        Assertions.assertNull(factory.makeOrder());
        Assertions.assertNull(factory.makeOrder());
        Assertions.assertNull(factory.makeOrder());
    }

    @Test
    void createThree() {
        var factory = new SequentialOrderFactory(3);
        Assertions.assertEquals(new Order(0), factory.makeOrder());
        Assertions.assertEquals(new Order(1), factory.makeOrder());
        Assertions.assertEquals(new Order(2), factory.makeOrder());
        Assertions.assertNull(factory.makeOrder());
        Assertions.assertNull(factory.makeOrder());
        Assertions.assertNull(factory.makeOrder());
    }

    @Test
    void createFive() {
        var factory = new SequentialOrderFactory(5);
        Assertions.assertEquals(new Order(0), factory.makeOrder());
        Assertions.assertEquals(new Order(1), factory.makeOrder());
        Assertions.assertEquals(new Order(2), factory.makeOrder());
        Assertions.assertEquals(new Order(3), factory.makeOrder());
        Assertions.assertEquals(new Order(4), factory.makeOrder());
        Assertions.assertNull(factory.makeOrder());
        Assertions.assertNull(factory.makeOrder());
        Assertions.assertNull(factory.makeOrder());
    }
}
