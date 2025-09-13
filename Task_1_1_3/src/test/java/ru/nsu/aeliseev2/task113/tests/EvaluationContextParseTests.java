package ru.nsu.aeliseev2.task113.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task113.EvaluationContext;
import ru.nsu.aeliseev2.task113.parsers.ExpressionParseException;
import ru.nsu.aeliseev2.task113.parsers.UnexpectedCharException;

class EvaluationContextParseTests {
    @Test
    void invalid() {
        Assertions.assertThrows(ExpressionParseException.class, () -> {
            EvaluationContext.parse("x = ; y = 5");
        });
    }

    @Test
    void invalidChar() {
        Assertions.assertThrows(UnexpectedCharException.class, () -> {
            EvaluationContext.parse("@");
        });
    }

    @Test
    void parseNone() {
        EvaluationContext.parse("");
    }

    @Test
    void parseOne() {
        var actual = EvaluationContext.parse("x = 0.75");
        Assertions.assertEquals(0.75, actual.getVariable("x"));
    }

    @Test
    void parseTwo() {
        var context = EvaluationContext.parse("x = 0.5; y = 7");
        Assertions.assertEquals(0.5, context.getVariable("x"));
        Assertions.assertEquals(7, context.getVariable("y"));
    }

    @Test
    void overwrite() {
        var context = EvaluationContext.parse("x = 0.5; x = 25");
        Assertions.assertEquals(25, context.getVariable("x"));
    }

    @Test
    void parseNegative() {
        var actual = EvaluationContext.parse("x = -0.3");
        Assertions.assertEquals(-0.3, actual.getVariable("x"));
    }

    @Test
    void parseInfinity() {
        var actual = EvaluationContext.parse("x = Inf");
        Assertions.assertEquals(Double.POSITIVE_INFINITY, actual.getVariable("x"));
    }

    @Test
    void parseNegativeInfinity() {
        var actual = EvaluationContext.parse("x = -Inf");
        Assertions.assertEquals(Double.NEGATIVE_INFINITY, actual.getVariable("x"));
    }

    @Test
    void parseNaN() {
        var actual = EvaluationContext.parse("x = NaN");
        Assertions.assertEquals(Double.NaN, actual.getVariable("x"));
    }
}
