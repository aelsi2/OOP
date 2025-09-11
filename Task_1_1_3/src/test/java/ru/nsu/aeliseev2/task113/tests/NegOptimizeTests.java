package ru.nsu.aeliseev2.task113.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task113.expressions.Addition;
import ru.nsu.aeliseev2.task113.expressions.Negation;
import ru.nsu.aeliseev2.task113.expressions.Number;
import ru.nsu.aeliseev2.task113.expressions.Subtraction;
import ru.nsu.aeliseev2.task113.expressions.Variable;

class NegOptimizeTests {
    @Test
    void negNum() {
        var expression = new Negation(new Number(10));
        var expected = new Number(-10);
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void negNeg() {
        var expression = new Negation(new Negation(new Variable("x")));
        var expected = new Variable("x");
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void negSub() {
        var expression = new Negation(
            new Subtraction(new Variable("x"), new Variable("y")));
        var expected = new Subtraction(new Variable("y"), new Variable("x"));
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void subNeg() {
        var expression = new Subtraction(
            new Variable("x"),
            new Negation(new Variable("y")));
        var expected = new Addition(new Variable("x"), new Variable("y"));
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void subNegNum() {
        var expression = new Subtraction(
            new Variable("x"),
            new Number(-10));
        var expected = new Addition(new Variable("x"), new Number(10));
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void addNeg() {
        var expression = new Addition(
            new Variable("x"),
            new Negation(new Variable("y")));
        var expected = new Subtraction(new Variable("x"), new Variable("y"));
        Assertions.assertEquals(expected, expression.optimize());
    }

    @Test
    void addNegNum() {
        var expression = new Addition(
            new Variable("x"),
            new Number(-10));
        var expected = new Subtraction(new Variable("x"), new Number(10));
        Assertions.assertEquals(expected, expression.optimize());
    }
}
