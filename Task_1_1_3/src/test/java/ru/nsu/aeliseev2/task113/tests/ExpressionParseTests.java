package ru.nsu.aeliseev2.task113.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task113.expressions.Addition;
import ru.nsu.aeliseev2.task113.expressions.Division;
import ru.nsu.aeliseev2.task113.expressions.Expression;
import ru.nsu.aeliseev2.task113.expressions.Multiplication;
import ru.nsu.aeliseev2.task113.expressions.Negation;
import ru.nsu.aeliseev2.task113.expressions.Number;
import ru.nsu.aeliseev2.task113.expressions.Subtraction;
import ru.nsu.aeliseev2.task113.expressions.Variable;
import ru.nsu.aeliseev2.task113.parsing.ExpressionParseException;

class ExpressionParseTests {
    @Test
    void invalid() {
        String string = "1 +";
        Assertions.assertThrows(ExpressionParseException.class, () -> {
            Expression.parse(string);
        });
    }

    @Test
    void variable() {
        String string = "  hello_World ";
        var expected = new Variable("hello_World");
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void variableNaN() {
        String string = "nana";
        var expected = new Variable("nana");
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void nanNumber() {
        String string = "nan";
        var expected = new Number(Double.NaN);
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void infinityNumber() {
        String string = "inf";
        var expected = new Number(Double.POSITIVE_INFINITY);
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void negativeInfinityNumber() {
        String string = "-inf";
        var expected = new Number(Double.NEGATIVE_INFINITY);
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void integerNumber() {
        String string = "256";
        var expected = new Number(256);
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void fractionalNumber() {
        String string = "256.360";
        var expected = new Number(256.360);
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addition() {
        String string = "1 + 2";
        var expected = new Addition(new Number(1), new Number(2));
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void operatorPrecedence1() {
        String string = "1*2+3";
        var expected = new Addition(
            new Multiplication(new Number(1), new Number(2)),
            new Number(3));
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void operatorPrecedence2() {
        String string = "1+2*3";
        var expected = new Addition(
            new Number(1),
            new Multiplication(new Number(2), new Number(3)));
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void negatedMultiplication() {
        String string = "-1 * -1";
        var expected = new Multiplication(
            new Number(-1),
            new Number(-1));
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void negatedSubtraction() {
        String string = "-1----1";
        var expected = new Subtraction(
            new Number(-1),
            new Negation(new Negation(new Number(-1))));
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void parentheses1() {
        String string = "(1 + 2) * 3";
        var expected = new Multiplication(
            new Addition(new Number(1), new Number(2)),
            new Number(3));
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void parentheses2() {
        String string = "5 * (1 + 2)";
        var expected = new Multiplication(
            new Number(5),
            new Addition(
                new Number(1),
                new Number(2)));
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addAssociativity() {
        String string = "1 + 2 - 3 + 4";
        var expected = new Addition(
            new Subtraction(
                new Addition(
                    new Number(1),
                    new Number(2)),
                new Number(3)),
            new Number(4));
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void mulAssociativity() {
        String string = "1 * 2 / 3 * 4";
        var expected = new Multiplication(
            new Division(
                new Multiplication(
                    new Number(1),
                    new Number(2)),
                new Number(3)),
            new Number(4));
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }
}
