package ru.nsu.aeliseev2.task113.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task113.EvaluationContext;
import ru.nsu.aeliseev2.task113.HashMapEvaluationContext;
import ru.nsu.aeliseev2.task113.expressions.Addition;
import ru.nsu.aeliseev2.task113.expressions.Division;
import ru.nsu.aeliseev2.task113.expressions.Multiplication;
import ru.nsu.aeliseev2.task113.expressions.Negation;
import ru.nsu.aeliseev2.task113.expressions.Number;
import ru.nsu.aeliseev2.task113.expressions.Subtraction;
import ru.nsu.aeliseev2.task113.expressions.Variable;

class EvaluateTests {
    @Test
    void number() {
        var expression = new Number(128);
        Assertions.assertEquals(128, expression.evaluate(EvaluationContext.EMPTY));
    }

    @Test
    void variable() {
        var expression = new Variable("x");
        var context = new HashMapEvaluationContext();
        context.setVariable("x", 30);
        Assertions.assertEquals(30, expression.evaluate(context));
    }

    @Test
    void addition() {
        var expression = new Addition(new Number(128), new Number(256));
        Assertions.assertEquals(384, expression.evaluate(EvaluationContext.EMPTY));
    }

    @Test
    void subtraction() {
        var expression = new Subtraction(new Number(128), new Number(256));
        Assertions.assertEquals(-128, expression.evaluate(EvaluationContext.EMPTY));
    }

    @Test
    void negation() {
        var expression = new Negation(new Number(1024));
        Assertions.assertEquals(-1024, expression.evaluate(EvaluationContext.EMPTY));
    }

    @Test
    void multiplication() {
        var expression = new Multiplication(new Number(17), new Number(23));
        Assertions.assertEquals(391, expression.evaluate(EvaluationContext.EMPTY));
    }

    @Test
    void division() {
        var expression = new Division(new Number(17), new Number(23));
        Assertions.assertEquals(17.0 / 23, expression.evaluate(EvaluationContext.EMPTY));
    }

    @Test
    void divByZero() {
        var expression = new Division(new Number(1), new Number(0));
        Assertions.assertEquals(Double.POSITIVE_INFINITY,
            expression.evaluate(EvaluationContext.EMPTY));
    }

    @Test
    void divZeroByZero() {
        var expression = new Division(new Number(0), new Number(0));
        Assertions.assertEquals(Double.NaN, expression.evaluate(EvaluationContext.EMPTY));
    }

    @Test
    void manyVariables() {
        var expression = new Addition(new Variable("x"), new Variable("y"));
        var context = new HashMapEvaluationContext();
        context.setVariable("x", 91);
        context.setVariable("y", 8);
        Assertions.assertEquals(99, expression.evaluate(context));
    }
}
