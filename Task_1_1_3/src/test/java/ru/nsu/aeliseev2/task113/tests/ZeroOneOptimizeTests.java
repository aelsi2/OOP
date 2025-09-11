package ru.nsu.aeliseev2.task113.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task113.expressions.Addition;
import ru.nsu.aeliseev2.task113.expressions.Division;
import ru.nsu.aeliseev2.task113.expressions.Multiplication;
import ru.nsu.aeliseev2.task113.expressions.Negation;
import ru.nsu.aeliseev2.task113.expressions.Number;
import ru.nsu.aeliseev2.task113.expressions.Subtraction;
import ru.nsu.aeliseev2.task113.expressions.Variable;

class ZeroOneOptimizeTests {
    @Test
    void addZeroRight() {
        var expression = new Addition(
            new Variable("x"),
            Number.ZERO);
        var expected = new Variable("x");
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void addZeroLeft() {
        var expression = new Addition(
            Number.ZERO,
            new Variable("x"));
        var expected = new Variable("x");
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void subZeroRight() {
        var expression = new Subtraction(
            new Variable("x"),
            Number.ZERO);
        var expected = new Variable("x");
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void subZeroLeft() {
        var expression = new Subtraction(
            Number.ZERO,
            new Variable("x"));
        var expected = new Negation(new Variable("x"));
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void mulZeroLeft() {
        var expression = new Multiplication(
            Number.ZERO,
            new Variable("x"));
        var expected = Number.ZERO;
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void mulZeroRight() {
        var expression = new Multiplication(
            new Variable("x"),
            Number.ZERO);
        var expected = Number.ZERO;
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void mulOneLeft() {
        var expression = new Multiplication(
            Number.ONE,
            new Variable("x"));
        var expected = new Variable("x");
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void mulOneRight() {
        var expression = new Multiplication(
            new Variable("x"),
            Number.ONE);
        var expected = new Variable("x");
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void divZero() {
        var expression = new Division(
            Number.ZERO,
            new Variable("x"));
        var expected = Number.ZERO;
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void divByOne() {
        var expression = new Division(
            new Variable("x"),
            Number.ONE);
        var expected = new Variable("x");
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void subSame() {
        var expression = new Subtraction(
            new Variable("x"),
            new Variable("x"));
        var expected = Number.ZERO;
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void divSame() {
        var expression = new Division(
            new Variable("x"),
            new Variable("x"));
        var expected = Number.ONE;
        Assertions.assertEquals(expected, expression.optimize());
    }
}
