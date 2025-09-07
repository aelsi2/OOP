package ru.nsu.aeliseev2.task112.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task112.controllers.GameAction;

class GameActionTests {
    @Test
    void standFromOrdinal() {
        var expected = GameAction.STAND;
        var actual = GameAction.fromOrdinal(0);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void hitFromOrdinal() {
        var expected = GameAction.HIT;
        var actual = GameAction.fromOrdinal(1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void invalidNegativeOrdinal() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            GameAction.fromOrdinal(-1);
        });
    }

    @Test
    void invalidLargeOrdinal() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            GameAction.fromOrdinal(5);
        });
    }
}
