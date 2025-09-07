package ru.nsu.aeliseev2.task112.tests;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import ru.nsu.aeliseev2.task112.ConsoleUtils;

class ConsoleUtilsTests {
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @Test
    void pause() {
        var scanner = new Scanner("\n\n\n");
        var stream = new PrintStream(DummyOutputStream.INSTANCE);
        ConsoleUtils.pause(stream, scanner);
    }

    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @Test
    void pauseMultiple() {
        var scanner = new Scanner("\n\n\n");
        var stream = new PrintStream(DummyOutputStream.INSTANCE);
        ConsoleUtils.pause(stream, scanner);
        ConsoleUtils.pause(stream, scanner);
        ConsoleUtils.pause(stream, scanner);
    }

    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @Test
    void inputNumberCorrect() {
        var scanner = new Scanner("20\n");
        var stream = new PrintStream(DummyOutputStream.INSTANCE);
        int expected = 20;
        int actual = ConsoleUtils.inputNumber(scanner, stream, "", 0, 20);
        Assertions.assertEquals(expected, actual);
    }

    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @Test
    void inputNumberOutOfBounds() {
        var scanner = new Scanner("-20\n10\n");
        var stream = new PrintStream(DummyOutputStream.INSTANCE);
        int expected = 10;
        int actual = ConsoleUtils.inputNumber(scanner, stream, "", 0, 20);
        Assertions.assertEquals(expected, actual);
    }

    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @Test
    void inputNumberBadFormat() {
        var scanner = new Scanner("xddd\n3\n");
        var stream = new PrintStream(DummyOutputStream.INSTANCE);
        int expected = 3;
        int actual = ConsoleUtils.inputNumber(scanner, stream, "", 0, 20);
        Assertions.assertEquals(expected, actual);
    }
}
