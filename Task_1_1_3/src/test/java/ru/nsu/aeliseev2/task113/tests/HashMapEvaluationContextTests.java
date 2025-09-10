package ru.nsu.aeliseev2.task113.tests;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task113.HashMapEvaluationContext;

class HashMapEvaluationContextTests {
    @Test
    void missingKey() {
        var context = new HashMapEvaluationContext();
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            context.getVariable("x");
        });
    }

    @Test
    void oneVariable() {
        var context = new HashMapEvaluationContext();
        context.setVariable("a", 10);
        Assertions.assertEquals(10, context.getVariable("a"));
    }

    @Test
    void threeVariables() {
        var context = new HashMapEvaluationContext();
        context.setVariable("a", 5);
        context.setVariable("b", 17);
        context.setVariable("c", 23);
        Assertions.assertEquals(5, context.getVariable("a"));
        Assertions.assertEquals(17, context.getVariable("b"));
        Assertions.assertEquals(23, context.getVariable("c"));
    }

    @Test
    void overwrite() {
        var context = new HashMapEvaluationContext();
        context.setVariable("a", 5);
        context.setVariable("a", 17);
        Assertions.assertEquals(17, context.getVariable("a"));
    }
}
