package ru.nsu.aeliseev2.task113.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task113.expressions.Addition;
import ru.nsu.aeliseev2.task113.expressions.Division;
import ru.nsu.aeliseev2.task113.expressions.Multiplication;
import ru.nsu.aeliseev2.task113.expressions.Number;
import ru.nsu.aeliseev2.task113.expressions.Subtraction;

class ConstantOptimizeTests {
    @Test
    void add() {
        var expression = new Addition(
            new Number(5),
            new Number(6));
        var expected = new Number(11);
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void sub() {
        var expression = new Subtraction(
            new Number(5),
            new Number(6));
        var expected = new Number(-1);
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void mul() {
        var expression = new Multiplication(
            new Number(5),
            new Number(6));
        var expected = new Number(30);
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void div() {
        var expression = new Division(
            new Number(5),
            new Number(6));
        var expected = new Number(5.0 / 6);
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void nested() {
        var expression = new Addition(
            new Multiplication(
                new Number(5),
                new Number(6)),
            new Number(100));
        var expected = new Number(5 * 6 + 100);
        Assertions.assertEquals(expected, expression.optimize());
    }
}
