package ru.nsu.aeliseev2.task113.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task113.expressions.Number;
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
}
