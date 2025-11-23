package ru.nsu.aeliseev2.task141;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GradeValueTests {
    @Test
    void order() {
        var expected = List.of(
            GradeValue.FAIL,
            GradeValue.FAIL,
            GradeValue.SATISFACTORY,
            GradeValue.SATISFACTORY,
            GradeValue.GOOD,
            GradeValue.GOOD,
            GradeValue.EXCELLENT,
            GradeValue.EXCELLENT
        );
        var actual = Stream.of(
            GradeValue.EXCELLENT,
            GradeValue.SATISFACTORY,
            GradeValue.GOOD,
            GradeValue.SATISFACTORY,
            GradeValue.FAIL,
            GradeValue.GOOD,
            GradeValue.EXCELLENT,
            GradeValue.FAIL
        ).sorted(GradeValue::compareTo).toList();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void numericValueEx() {
        Assertions.assertEquals(5, GradeValue.EXCELLENT.toInt());
    }

    @Test
    void numericValueGood() {
        Assertions.assertEquals(4, GradeValue.GOOD.toInt());
    }

    @Test
    void numericValueSat() {
        Assertions.assertEquals(3, GradeValue.SATISFACTORY.toInt());
    }

    @Test
    void numericValueFail() {
        Assertions.assertEquals(2, GradeValue.FAIL.toInt());
    }
}
