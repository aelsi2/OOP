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

class StringTests {
    @Test
    void numberInteger() {
        var number = new Number(65536);
        Assertions.assertEquals("65536", number.toString());
    }

    @Test
    void numberSmallFraction() {
        var number = new Number(0.25);
        Assertions.assertEquals("0.25", number.toString());
    }

    @Test
    void numberLargeFraction() {
        var number = new Number(0.000025);
        Assertions.assertEquals("0.000025", number.toString());
    }

    @Test
    void numberNegative() {
        var number = new Number(-100000);
        Assertions.assertEquals("-100000", number.toString());
    }

    @Test
    void numberNan() {
        var number = new Number(Double.NaN);
        Assertions.assertEquals("NaN", number.toString());
    }

    @Test
    void numberInf() {
        var number = new Number(Double.POSITIVE_INFINITY);
        Assertions.assertEquals("Inf", number.toString());
    }

    @Test
    void numberNegInf() {
        var number = new Number(Double.NEGATIVE_INFINITY);
        Assertions.assertEquals("-Inf", number.toString());
    }

    @Test
    void variable() {
        var variable = new Variable("xyz");
        Assertions.assertEquals("xyz", variable.toString());
    }

    @Test
    void negateVariable() {
        var expression = new Negation(new Variable("x"));
        Assertions.assertEquals("-x", expression.toString());
    }

    @Test
    void negateSum() {
        var expression = new Negation(
            new Addition(
                new Variable("x"),
                new Variable("y")));
        Assertions.assertEquals("-(x + y)", expression.toString());
    }

    @Test
    void negateNegativeNumber() {
        var expression = new Negation(new Number(-1));
        Assertions.assertEquals("-(-1)", expression.toString());
    }

    @Test
    void additionNestedRight() {
        var expression = new Addition(
            new Variable("x"),
            new Addition(
                new Variable("y"),
                new Variable("z")));
        Assertions.assertEquals("x + (y + z)", expression.toString());
    }

    @Test
    void additionNestedLeft() {
        var expression = new Addition(
            new Addition(
                new Variable("x"),
                new Variable("y")),
            new Variable("z"));
        Assertions.assertEquals("x + y + z", expression.toString());
    }

    @Test
    void subtractionAdditionNesting() {
        var expression = new Subtraction(
            new Addition(
                new Subtraction(
                    new Variable("x"),
                    new Variable("y")),
                new Variable("z")),
            new Variable("w"));
        Assertions.assertEquals("x - y + z - w", expression.toString());
    }

    @Test
    void subtractionNegative() {
        var expression = new Subtraction(
            new Negation(new Variable("x")),
            new Negation(new Variable("y")));
        Assertions.assertEquals("-x - (-y)", expression.toString());
    }

    @Test
    void multiplicationNegative() {
        var expression = new Multiplication(
            new Number(-1),
            new Number(-1));
        Assertions.assertEquals("-1 * (-1)", expression.toString());
    }

    @Test
    void multiplicationDivisionNesting() {
        var expression = new Division(
            new Multiplication(
                new Division(
                    new Variable("x"),
                    new Variable("y")),
                new Variable("z")),
            new Variable("w"));
        Assertions.assertEquals("x / y * z / w", expression.toString());
    }

    @Test
    void multiplySum() {
        var expression =
            new Multiplication(
                new Addition(
                    new Variable("x"),
                    new Variable("y")),
                new Variable("z"));
        Assertions.assertEquals("(x + y) * z", expression.toString());
    }

    @Test
    void divideDifference() {
        var expression =
            new Division(
                new Subtraction(
                    new Variable("x"),
                    new Variable("y")),
                new Variable("z"));
        Assertions.assertEquals("(x - y) / z", expression.toString());
    }

    @Test
    void divideBySum() {
        var expression =
            new Division(
                new Variable("x"),
                new Addition(
                    new Variable("y"),
                    new Variable("z")));
        Assertions.assertEquals("x / (y + z)", expression.toString());
    }
}
