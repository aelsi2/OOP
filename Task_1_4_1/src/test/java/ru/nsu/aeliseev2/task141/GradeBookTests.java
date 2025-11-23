package ru.nsu.aeliseev2.task141;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GradeBookTests {
    @Test
    void create() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 0);
        var subject3 = new Subject("Subj3", 0);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM
            ),
            Map.of(
                subject1, GradeType.EXAM
            )
        );
        new GradeBook(242222, grades);
    }

    @Test
    void createInvalidSemester() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 2);
        var subject3 = new Subject("Subj3", 0);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM
            ),
            Map.of(
                subject1, GradeType.EXAM
            )
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new GradeBook(242222, grades);
        });
    }

    @Test
    void createMissingGrade() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 1);
        var subject3 = new Subject("Subj3", 0);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM
            ),
            Map.of(
                subject2, GradeType.EXAM
            )
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new GradeBook(242222, grades);
        });
    }

    @Test
    void gradeBookId() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 0);
        var subject3 = new Subject("Subj3", 0);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM
            ),
            Map.of(
                subject1, GradeType.EXAM
            )
        );
        long gradeBookId = 242228;
        var gradeBook = new GradeBook(gradeBookId, grades);
        Assertions.assertEquals(gradeBookId, gradeBook.getGradeBookId());
    }

    @Test
    void semesterCount() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 0);
        var subject3 = new Subject("Subj3", 0);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM
            ),
            Map.of(
                subject1, GradeType.EXAM
            )
        );
        var gradeBook = new GradeBook(242222, grades);
        Assertions.assertEquals(2, gradeBook.semesterCount());
    }

    private record GradePair(Subject subject, GradeType gradeType) {
    }

    @Test
    void getSemesterGrades() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 0);
        var subject3 = new Subject("Subj3", 0);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM
            ),
            Map.of(
                subject1, GradeType.EXAM
            )
        );
        var gradeBook = new GradeBook(242222, grades);
        var semGrades = new HashSet<>(gradeBook.getSemesterGrades(0));
        Assertions.assertAll(
            () -> Assertions.assertEquals(3, semGrades.size()),
            () -> {
                var expected = Set.of(
                    new GradePair(subject1, GradeType.DIFFERENTIATED_CREDIT),
                    new GradePair(subject2, GradeType.BINARY_CREDIT),
                    new GradePair(subject3, GradeType.EXAM)
                );
                var actual = semGrades.stream().map(grade -> new GradePair(
                    grade.getSubject(), grade.getGradeType()
                )).collect(Collectors.toCollection(HashSet::new));
                Assertions.assertEquals(expected, actual);
            }
        );
    }

    @Test
    void getSemesterGradesInvalid() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 0);
        var subject3 = new Subject("Subj3", 0);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM
            ),
            Map.of(
                subject1, GradeType.EXAM
            )
        );
        var gradeBook = new GradeBook(242222, grades);
        Assertions.assertThrows(NoSuchElementException.class,
            () -> gradeBook.getSemesterGrades(2)
        );
    }

    @Test
    void getGrade() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 0);
        var subject3 = new Subject("Subj3", 0);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM
            ),
            Map.of(
                subject1, GradeType.EXAM
            )
        );
        var gradeBook = new GradeBook(242222, grades);
        var actual = gradeBook.getGrade(0, subject2);
        Assertions.assertAll(
            () -> Assertions.assertEquals(subject2, actual.getSubject()),
            () -> Assertions.assertEquals(GradeType.BINARY_CREDIT, actual.getGradeType())
        );
    }

    @Test
    void getGradeInvalidSemester() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 0);
        var subject3 = new Subject("Subj3", 0);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM
            ),
            Map.of(
                subject1, GradeType.EXAM
            )
        );
        var gradeBook = new GradeBook(242222, grades);
        Assertions.assertThrows(NoSuchElementException.class,
            () -> gradeBook.getGrade(2, subject1)
        );
    }

    @Test
    void getGradeInvalidSubject() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 0);
        var subject3 = new Subject("Subj3", 0);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM
            ),
            Map.of(
                subject1, GradeType.EXAM
            )
        );
        var gradeBook = new GradeBook(242222, grades);
        Assertions.assertThrows(NoSuchElementException.class,
            () -> gradeBook.getGrade(1, subject2)
        );
    }

    @Test
    void getAverageGrade() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 1);
        var subject3 = new Subject("Subj3", 0);
        var subject4 = new Subject("Subj4", 0);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM,
                subject4, GradeType.EXAM
            ),
            Map.of(
                subject1, GradeType.EXAM,
                subject2, GradeType.DIFFERENTIATED_CREDIT
            )
        );
        var gradeBook = new GradeBook(242222, grades);
        gradeBook.getGrade(0, subject2).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(0, subject3).setGradeValue(GradeValue.GOOD);
        gradeBook.getGrade(0, subject4).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(1, subject1).setGradeValue(GradeValue.SATISFACTORY);
        gradeBook.getGrade(1, subject2).setGradeValue(GradeValue.FAIL);
        double expected = 3.5;
        Assertions.assertEquals(expected, gradeBook.getAverageGrade());
    }

    @Test
    void canTransferToBudgetEmpty() {
        var gradeBook = new GradeBook(242222, List.of());
        Assertions.assertFalse(gradeBook.canTransferToBudget());
    }

    @Test
    void canTransferToBudgetFirstSemester() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 1);
        var subject3 = new Subject("Subj3", 0);
        var subject4 = new Subject("Subj4", 0);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM,
                subject4, GradeType.EXAM
            ),
            Map.of(
                subject1, GradeType.EXAM,
                subject2, GradeType.DIFFERENTIATED_CREDIT
            )
        );
        var gradeBook = new GradeBook(242222, grades);
        gradeBook.getGrade(0, subject2).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(0, subject3).setGradeValue(GradeValue.GOOD);
        gradeBook.getGrade(0, subject4).setGradeValue(GradeValue.EXCELLENT);
        Assertions.assertFalse(gradeBook.canTransferToBudget());
    }

    @Test
    void canTransferToBudgetFirstSemesterBad() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 1);
        var subject3 = new Subject("Subj3", 0);
        var subject4 = new Subject("Subj4", 0);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM,
                subject4, GradeType.EXAM
            ),
            Map.of(
                subject1, GradeType.EXAM,
                subject2, GradeType.DIFFERENTIATED_CREDIT
            )
        );
        var gradeBook = new GradeBook(242222, grades);
        gradeBook.getGrade(0, subject1).setGradeValue(GradeValue.FAIL);
        gradeBook.getGrade(0, subject2).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(0, subject3).setGradeValue(GradeValue.GOOD);
        gradeBook.getGrade(0, subject4).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(1, subject1).setGradeValue(GradeValue.GOOD);
        gradeBook.getGrade(1, subject2).setGradeValue(GradeValue.SATISFACTORY);
        Assertions.assertFalse(gradeBook.canTransferToBudget());
    }

    @Test
    void canTransferToBudgetSecondSemesterBad() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 1);
        var subject3 = new Subject("Subj3", 0);
        var subject4 = new Subject("Subj4", 0);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM,
                subject4, GradeType.EXAM
            ),
            Map.of(
                subject1, GradeType.EXAM,
                subject2, GradeType.DIFFERENTIATED_CREDIT
            )
        );
        var gradeBook = new GradeBook(242222, grades);
        gradeBook.getGrade(0, subject1).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(0, subject2).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(0, subject3).setGradeValue(GradeValue.GOOD);
        gradeBook.getGrade(0, subject4).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(1, subject1).setGradeValue(GradeValue.SATISFACTORY);
        gradeBook.getGrade(1, subject2).setGradeValue(GradeValue.SATISFACTORY);
        Assertions.assertFalse(gradeBook.canTransferToBudget());
    }

    @Test
    void canTransferToBudgetGood() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 1);
        var subject3 = new Subject("Subj3", 0);
        var subject4 = new Subject("Subj4", 0);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM,
                subject4, GradeType.EXAM
            ),
            Map.of(
                subject1, GradeType.EXAM,
                subject2, GradeType.DIFFERENTIATED_CREDIT
            )
        );
        var gradeBook = new GradeBook(242222, grades);
        gradeBook.getGrade(0, subject1).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(0, subject2).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(0, subject3).setGradeValue(GradeValue.GOOD);
        gradeBook.getGrade(0, subject4).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(1, subject1).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(1, subject2).setGradeValue(GradeValue.SATISFACTORY);
        Assertions.assertTrue(gradeBook.canTransferToBudget());
    }

    @Test
    void canGetHighScholarshipEmpty() {
        var gradeBook = new GradeBook(242222, List.of());
        Assertions.assertFalse(gradeBook.canGetHighScholarship());
    }

    @Test
    void canGetHighScholarshipBad() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 1);
        var subject3 = new Subject("Subj3", 0);
        var subject4 = new Subject("Subj4", 0);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM,
                subject4, GradeType.EXAM
            ),
            Map.of(
                subject1, GradeType.EXAM,
                subject2, GradeType.DIFFERENTIATED_CREDIT
            )
        );
        var gradeBook = new GradeBook(242222, grades);
        gradeBook.getGrade(0, subject1).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(0, subject2).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(0, subject3).setGradeValue(GradeValue.GOOD);
        gradeBook.getGrade(0, subject4).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(1, subject1).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(1, subject2).setGradeValue(GradeValue.GOOD);
        Assertions.assertFalse(gradeBook.canGetHighScholarship());
    }

    @Test
    void canGetHighScholarshipGood() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 1);
        var subject3 = new Subject("Subj3", 0);
        var subject4 = new Subject("Subj4", 0);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM,
                subject4, GradeType.EXAM
            ),
            Map.of(
                subject1, GradeType.EXAM,
                subject2, GradeType.DIFFERENTIATED_CREDIT
            )
        );
        var gradeBook = new GradeBook(242222, grades);
        gradeBook.getGrade(0, subject1).setGradeValue(GradeValue.SATISFACTORY);
        gradeBook.getGrade(0, subject2).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(0, subject3).setGradeValue(GradeValue.GOOD);
        gradeBook.getGrade(0, subject4).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(1, subject1).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(1, subject2).setGradeValue(GradeValue.EXCELLENT);
        Assertions.assertTrue(gradeBook.canGetHighScholarship());
    }

    @Test
    void canGetDiplomaWithHonorsGood() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 1);
        var subject3 = new Subject("Subj3", 0);
        var subject4 = new Subject("Subj4", 0);
        var subject5 = new Subject("Subj5", 1);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM,
                subject4, GradeType.EXAM
            ),
            Map.of(
                subject1, GradeType.EXAM,
                subject2, GradeType.DIFFERENTIATED_CREDIT,
                subject5, GradeType.THESIS
            )
        );
        var gradeBook = new GradeBook(242222, grades);
        gradeBook.getGrade(0, subject1).setGradeValue(GradeValue.SATISFACTORY);
        gradeBook.getGrade(0, subject2).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(0, subject3).setGradeValue(GradeValue.GOOD);
        gradeBook.getGrade(0, subject4).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(1, subject1).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(1, subject2).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(1, subject5).setGradeValue(GradeValue.EXCELLENT);
        Assertions.assertTrue(gradeBook.canGetDiplomaWithHonors());
    }

    @Test
    void canGetDiplomaWithHonorsFinalSat() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 1);
        var subject3 = new Subject("Subj3", 0);
        var subject4 = new Subject("Subj4", 0);
        var subject5 = new Subject("Subj5", 1);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM,
                subject4, GradeType.EXAM
            ),
            Map.of(
                subject1, GradeType.EXAM,
                subject2, GradeType.DIFFERENTIATED_CREDIT,
                subject5, GradeType.THESIS
            )
        );
        var gradeBook = new GradeBook(242222, grades);
        gradeBook.getGrade(0, subject1).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(0, subject2).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(0, subject3).setGradeValue(GradeValue.GOOD);
        gradeBook.getGrade(0, subject4).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(1, subject1).setGradeValue(GradeValue.SATISFACTORY);
        gradeBook.getGrade(1, subject2).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(1, subject5).setGradeValue(GradeValue.EXCELLENT);
        Assertions.assertFalse(gradeBook.canGetDiplomaWithHonors());
    }

    @Test
    void canGetDiplomaWithHonorsNotEnoughEx() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 1);
        var subject3 = new Subject("Subj3", 0);
        var subject4 = new Subject("Subj4", 0);
        var subject5 = new Subject("Subj5", 1);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM,
                subject4, GradeType.EXAM
            ),
            Map.of(
                subject1, GradeType.EXAM,
                subject2, GradeType.DIFFERENTIATED_CREDIT,
                subject5, GradeType.THESIS
            )
        );
        var gradeBook = new GradeBook(242222, grades);
        gradeBook.getGrade(0, subject1).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(0, subject2).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(0, subject3).setGradeValue(GradeValue.GOOD);
        gradeBook.getGrade(0, subject4).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(1, subject1).setGradeValue(GradeValue.GOOD);
        gradeBook.getGrade(1, subject2).setGradeValue(GradeValue.GOOD);
        gradeBook.getGrade(1, subject5).setGradeValue(GradeValue.EXCELLENT);
        Assertions.assertFalse(gradeBook.canGetDiplomaWithHonors());
    }

    @Test
    void canGetDiplomaWithHonorsBadThesis() {
        var subject1 = new Subject("Subj1", 1);
        var subject2 = new Subject("Subj2", 1);
        var subject3 = new Subject("Subj3", 0);
        var subject4 = new Subject("Subj4", 0);
        var subject5 = new Subject("Subj5", 1);
        var grades = List.of(
            Map.of(
                subject1, GradeType.DIFFERENTIATED_CREDIT,
                subject2, GradeType.BINARY_CREDIT,
                subject3, GradeType.EXAM,
                subject4, GradeType.EXAM
            ),
            Map.of(
                subject1, GradeType.EXAM,
                subject2, GradeType.DIFFERENTIATED_CREDIT,
                subject5, GradeType.THESIS
            )
        );
        var gradeBook = new GradeBook(242222, grades);
        gradeBook.getGrade(0, subject1).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(0, subject2).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(0, subject3).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(0, subject4).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(1, subject1).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(1, subject2).setGradeValue(GradeValue.EXCELLENT);
        gradeBook.getGrade(1, subject5).setGradeValue(GradeValue.GOOD);
        Assertions.assertFalse(gradeBook.canGetDiplomaWithHonors());
    }
}