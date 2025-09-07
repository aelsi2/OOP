package ru.nsu.aeliseev2.task113.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task113.EvaluationContext;

class EvaluationContextTests {
    @Test
    void fromString() {
        var context = EvaluationContext.fromString("x = 5 ; y = 10");
        double xValue = context.getVariable("x");
        double yValue = context.getVariable("y");
        Assertions.assertEquals(5, xValue);
        Assertions.assertEquals(10, yValue);
    }
}
