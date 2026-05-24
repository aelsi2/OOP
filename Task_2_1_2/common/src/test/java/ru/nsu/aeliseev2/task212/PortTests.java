package ru.nsu.aeliseev2.task212;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task212.utils.Port;

class PortTests {
    @Test
    void parseNegative() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Port.parse("-1");
        });
    }

    @Test
    void parseAboveLimit() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Port.parse("65600");
        });
    }

    @Test
    void parseGarbage() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Port.parse("lorem ipsum dolor sit amet");
        });
    }

    @Test
    void parseNormal() {
        Assertions.assertEquals(8080, Port.parse("8080"));
    }
}
