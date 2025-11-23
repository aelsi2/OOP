package ru.nsu.aeliseev2.task141;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GradeTypeTests {
    @Test
    void budgetSatExam() {
        Assertions.assertFalse(GradeType.EXAM.isBudgetSatAllowed());
    }
    @Test
    void budgetSatDifCredit() {
        Assertions.assertTrue(GradeType.DIFFERENTIATED_CREDIT.isBudgetSatAllowed());
    }

    @Test
    void differentiatedExam() {
        Assertions.assertTrue(GradeType.DIFFERENTIATED_CREDIT.isDifferentiated());
    }

    @Test
    void differentiatedDifCredit() {
        Assertions.assertTrue(GradeType.DIFFERENTIATED_CREDIT.isDifferentiated());
    }

    @Test
    void differentiatedBinCredit() {
        Assertions.assertFalse(GradeType.BINARY_CREDIT.isDifferentiated());
    }
}
