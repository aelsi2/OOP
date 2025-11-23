package ru.nsu.aeliseev2.task141;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GradeTests {
    @Test
    void setGetValueNonDifFail() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 0, GradeType.BINARY_CREDIT);
        grade.setGradeValue(GradeValue.FAIL);
        Assertions.assertEquals(GradeValue.FAIL, grade.getGradeValue());
    }

    @Test
    void setGetValueNonDifGood() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 0, GradeType.BINARY_CREDIT);
        grade.setGradeValue(GradeValue.SATISFACTORY);
        Assertions.assertEquals(GradeValue.EXCELLENT, grade.getGradeValue());
    }

    @Test
    void setGetValueDifGood() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 0, GradeType.EXAM);
        grade.setGradeValue(GradeValue.GOOD);
        Assertions.assertEquals(GradeValue.GOOD, grade.getGradeValue());
    }

    @Test
    void countsTowardsAverageNoMark() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 0, GradeType.EXAM);
        Assertions.assertFalse(grade.countsTowardsAverage());
    }

    @Test
    void countsTowardsAverageExam() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 0, GradeType.EXAM);
        grade.setGradeValue(GradeValue.EXCELLENT);
        Assertions.assertTrue(grade.countsTowardsAverage());
    }

    @Test
    void countsTowardsAverageBinCredit() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 0, GradeType.BINARY_CREDIT);
        grade.setGradeValue(GradeValue.EXCELLENT);
        Assertions.assertFalse(grade.countsTowardsAverage());
    }

    @Test
    void getNumericValueNoMark() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 0, GradeType.EXAM);
        Assertions.assertEquals(0, grade.getNumericValue());
    }

    @Test
    void getNumericValueSat() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 0, GradeType.EXAM);
        grade.setGradeValue(GradeValue.SATISFACTORY);
        Assertions.assertEquals(3, grade.getNumericValue());
    }

    @Test
    void isGoodForBudgetNoMark() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 0, GradeType.EXAM);
        Assertions.assertTrue(grade.isGoodForBudget());
    }

    @Test
    void isGoodForBudgetExamSat() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 0, GradeType.EXAM);
        grade.setGradeValue(GradeValue.SATISFACTORY);
        Assertions.assertFalse(grade.isGoodForBudget());
    }

    @Test
    void isGoodForBudgetExamGood() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 0, GradeType.EXAM);
        grade.setGradeValue(GradeValue.GOOD);
        Assertions.assertTrue(grade.isGoodForBudget());
    }

    @Test
    void isGoodForBudgetDifCreditSat() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 0, GradeType.DIFFERENTIATED_CREDIT);
        grade.setGradeValue(GradeValue.SATISFACTORY);
        Assertions.assertTrue(grade.isGoodForBudget());
    }

    @Test
    void isGoodForBudgetDifCreditFail() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 0, GradeType.DIFFERENTIATED_CREDIT);
        grade.setGradeValue(GradeValue.FAIL);
        Assertions.assertFalse(grade.isGoodForBudget());
    }

    @Test
    void isGoodForDiplomaWithHonorsNoMark() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 0, GradeType.DIFFERENTIATED_CREDIT);
        Assertions.assertTrue(grade.isGoodForDiplomaWithHonors());
    }

    @Test
    void isGoodForDiplomaWithHonorsGood() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 0, GradeType.DIFFERENTIATED_CREDIT);
        grade.setGradeValue(GradeValue.GOOD);
        Assertions.assertTrue(grade.isGoodForDiplomaWithHonors());
    }

    @Test
    void isGoodForDiplomaWithHonorsSat() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 0, GradeType.DIFFERENTIATED_CREDIT);
        grade.setGradeValue(GradeValue.SATISFACTORY);
        Assertions.assertFalse(grade.isGoodForDiplomaWithHonors());
    }

    @Test
    void canBeExcellentNoMark() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 0, GradeType.EXAM);
        Assertions.assertTrue(grade.canBeExcellent());
    }

    @Test
    void canBeExcellentGood() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 0, GradeType.EXAM);
        grade.setGradeValue(GradeValue.GOOD);
        Assertions.assertFalse(grade.canBeExcellent());
    }

    @Test
    void canBeExcellentEx() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 0, GradeType.EXAM);
        grade.setGradeValue(GradeValue.EXCELLENT);
        Assertions.assertTrue(grade.canBeExcellent());
    }

    @Test
    void getSubject() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 0, GradeType.EXAM);
        Assertions.assertEquals(subject, grade.getSubject());
    }

    @Test
    void getSemesterIndex() {
        var subject = new Subject("Subject", 0);
        var grade = new Grade(subject, 5, GradeType.EXAM);
        Assertions.assertEquals(5, grade.getSemesterIndex());
    }
}
