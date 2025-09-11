package ru.nsu.aeliseev2.task113.tests;

import java.text.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task113.EvaluationContext;

class EvaluationContextParseTests {
    @Test
    void invalid() {
        Assertions.assertThrows(ParseException.class, () -> {
            EvaluationContext.parse("x = ; y = 5");
        });
    }

    @Test
    void parseNone() throws ParseException {
        EvaluationContext.parse("");
    }

    @Test
    void parseOne() throws ParseException {
        var actual = EvaluationContext.parse("x = 0.75");
        Assertions.assertEquals(0.75, actual.getVariable("x"));
    }

    @Test
    void parseTwo() throws ParseException {
        var context = EvaluationContext.parse("x = 0.5; y = 7");
        Assertions.assertEquals(0.5, context.getVariable("x"));
        Assertions.assertEquals(7, context.getVariable("y"));
    }

    @Test
    void overwrite() throws ParseException {
        var context = EvaluationContext.parse("x = 0.5; x = 25");
        Assertions.assertEquals(25, context.getVariable("x"));
    }
}
