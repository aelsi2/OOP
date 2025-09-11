package ru.nsu.aeliseev2.task113.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task113.expressions.Addition;
import ru.nsu.aeliseev2.task113.expressions.Division;
import ru.nsu.aeliseev2.task113.expressions.Multiplication;
import ru.nsu.aeliseev2.task113.expressions.Number;
import ru.nsu.aeliseev2.task113.expressions.Variable;

class HashCodeTests {
    @Test
    void variables() {
        var variable1 = new Variable("x");
        var variable2 = new Variable("x");
        Assertions.assertEquals(variable1.hashCode(), variable2.hashCode());
    }

    @Test
    void numbers() {
        var number1 = new Number(10);
        var number2 = new Number(10);
        Assertions.assertEquals(number1.hashCode(), number2.hashCode());
    }

    @Test
    void addition() {
        var expression1 = new Addition(Number.ZERO, Number.ONE);
        var expression2 = new Addition(Number.ZERO, Number.ONE);
        Assertions.assertEquals(expression1.hashCode(), expression2.hashCode());
    }

    @Test
    void nested() {
        var expression1 = new Addition(
            new Multiplication(
                new Variable("x"),
                new Number(10)),
            new Division(
                new Variable("y"),
                new Number(20)));
        var expression2 = new Addition(
            new Multiplication(
                new Variable("x"),
                new Number(10)),
            new Division(
                new Variable("y"),
                new Number(20)));
        Assertions.assertEquals(expression1.hashCode(), expression2.hashCode());
    }
}
