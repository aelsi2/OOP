package ru.nsu.aeliseev2.task113.tests;

import java.text.ParseException;
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

class ExpressionParseTests {
    @Test
    void variable() throws ParseException {
        String string = "  hello_World ";
        var expected = new Variable("hello_World");
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void integerNumber() throws ParseException {
        String string = "256";
        var expected = new Number(256);
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void fractionalNumber() throws ParseException {
        String string = "256.360";
        var expected = new Number(256.360);
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addition() throws ParseException {
        String string = "1 + 2";
        var expected = new Addition(new Number(1), new Number(2));
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void operatorPrecedence1() throws ParseException {
        String string = "1*2+3";
        var expected = new Addition(
            new Multiplication(new Number(1), new Number(2)),
            new Number(3));
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void operatorPrecedence2() throws ParseException {
        String string = "1+2*3";
        var expected = new Addition(
            new Number(1),
            new Multiplication(new Number(2), new Number(3)));
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void negatedMultiplication() throws ParseException {
        String string = "-1 * -1";
        var expected = new Multiplication(
            new Number(-1),
            new Number(-1));
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void negatedSubtraction() throws ParseException {
        String string = "-1----1";
        var expected = new Subtraction(
            new Number(-1),
            new Negation(new Negation(new Number(-1))));
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void parentheses1() throws ParseException {
        String string = "(1 + 2) * 3";
        var expected = new Multiplication(
            new Addition(new Number(1), new Number(2)),
            new Number(3));
        var actual = Expression.parse(string);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void parentheses2() throws ParseException {
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
    void addAssociativity() throws ParseException {
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
    void mulAssociativity() throws ParseException {
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
