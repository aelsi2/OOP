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

class DifferentiateTests {
    @Test
    void variableWithItself() {
        var diff = new Variable("x").differentiate("x");
        Assertions.assertEquals(Number.ONE, diff);
    }

    @Test
    void variableWithOther() {
        var diff = new Variable("x").differentiate("y");
        Assertions.assertEquals(Number.ZERO, diff);
    }

    @Test
    void constant() {
        var diff = new Number(255).differentiate("x");
        Assertions.assertEquals(Number.ZERO, diff);
    }

    @Test
    void negation() {
        var diff = new Negation(new Variable("x")).differentiate("x");
        Assertions.assertEquals(new Negation(Number.ONE), diff);
    }

    @Test
    void sumOfVariables() {
        var expression = new Addition(
            new Variable("x"),
            new Variable("y"));
        var expected = new Addition(
            Number.ONE,
            Number.ZERO);
        Assertions.assertEquals(expected, expression.differentiate("x"));
    }

    @Test
    void differenceOfVariables() {
        var expression = new Subtraction(
            new Variable("x"),
            new Variable("y"));
        var expected = new Subtraction(
            Number.ONE,
            Number.ZERO);
        Assertions.assertEquals(expected, expression.differentiate("x"));
    }

    @Test
    void multiplicationOfVariables() {
        var expression = new Multiplication(
            new Variable("x"),
            new Variable("y"));
        var expected = new Addition(
            new Multiplication(Number.ONE, new Variable("y")),
            new Multiplication(new Variable("x"), Number.ZERO));
        Assertions.assertEquals(expected, expression.differentiate("x"));
    }

    @Test
    void divisionOfVariables() {
        var expression = new Division(
            new Variable("x"),
            new Variable("y"));
        var expected = new Division(
            new Subtraction(
                new Multiplication(Number.ONE, new Variable("y")),
                new Multiplication(new Variable("x"), Number.ZERO)),
            new Multiplication(
                new Variable("y"),
                new Variable("y")));
        Assertions.assertEquals(expected, expression.differentiate("x"));
    }
}
