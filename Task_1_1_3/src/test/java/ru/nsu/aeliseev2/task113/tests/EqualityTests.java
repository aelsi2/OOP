package ru.nsu.aeliseev2.task113.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task113.expressions.Addition;
import ru.nsu.aeliseev2.task113.expressions.Division;
import ru.nsu.aeliseev2.task113.expressions.Multiplication;
import ru.nsu.aeliseev2.task113.expressions.Number;
import ru.nsu.aeliseev2.task113.expressions.Subtraction;
import ru.nsu.aeliseev2.task113.expressions.Variable;

class EqualityTests {
    @Test
    void variablesEqual() {
        var variable1 = new Variable("x");
        var variable2 = new Variable("x");
        Assertions.assertEquals(variable1, variable2);
    }

    @Test
    void variablesNotEqual() {
        var variable1 = new Variable("x");
        var variable2 = new Variable("z");
        Assertions.assertNotEquals(variable1, variable2);
    }

    @Test
    void numbersEqual() {
        var number1 = new Number(10);
        var number2 = new Number(10);
        Assertions.assertEquals(number1, number2);
    }

    @Test
    void numbersNotEqual() {
        var number1 = new Number(10);
        var number2 = new Number(20);
        Assertions.assertNotEquals(number1, number2);
    }

    @Test
    void addSub() {
        var number1 = new Number(10);
        var number2 = new Number(20);
        var expr1 = new Addition(number1, number2);
        var expr2 = new Subtraction(number1, number2);
        Assertions.assertNotEquals(expr1, expr2);
    }

    @Test
    void addNull() {
        var number1 = new Number(10);
        var number2 = new Number(20);
        var expression = new Addition(number1, number2);
        Assertions.assertNotEquals(expression, null);
    }

    @Test
    void nestedEqual() {
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
        Assertions.assertEquals(expression1, expression2);
    }

    @Test
    void nestedNotEqual() {
        var expression1 = new Addition(
            new Multiplication(
                new Variable("x"),
                new Number(10)),
            new Division(
                new Variable("y"),
                new Number(0)));
        var expression2 = new Addition(
            new Multiplication(
                new Variable("x"),
                new Number(10)),
            new Division(
                new Variable("y"),
                new Number(20)));
        Assertions.assertNotEquals(expression1, expression2);
    }
}
