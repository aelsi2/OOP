package ru.nsu.aeliseev2.task112.tests;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import ru.nsu.aeliseev2.task112.controllers.ConsoleController;
import ru.nsu.aeliseev2.task112.controllers.GameAction;
import ru.nsu.aeliseev2.task112.model.CardHand;

class ConsoleControllerTests {
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @Test
    void stand() {
        var expected = GameAction.STAND;

        var scanner = new Scanner("0\n");
        var dummyStream = new PrintStream(DummyOutputStream.INSTANCE);
        var hand = new CardHand();

        var controller = new ConsoleController(dummyStream, scanner);
        Assertions.assertEquals(expected, controller.chooseAction(hand));
    }

    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @Test
    void hit() {
        var expected = GameAction.HIT;

        var scanner = new Scanner("1\n");
        var dummyStream = new PrintStream(DummyOutputStream.INSTANCE);
        var hand = new CardHand();

        var controller = new ConsoleController(dummyStream, scanner);
        Assertions.assertEquals(expected, controller.chooseAction(hand));
    }

    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @Test
    void invalid() {
        var expected = GameAction.HIT;

        var scanner = new Scanner("69\n1\n");
        var dummyStream = new PrintStream(DummyOutputStream.INSTANCE);
        var hand = new CardHand();

        var controller = new ConsoleController(dummyStream, scanner);
        Assertions.assertEquals(expected, controller.chooseAction(hand));
    }
}
