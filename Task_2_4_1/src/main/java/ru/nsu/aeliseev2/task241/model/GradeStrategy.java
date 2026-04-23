package ru.nsu.aeliseev2.task241.model;

/**
 * A strategy for calculating a student's grade.
 */
public interface GradeStrategy {
    /**
     * Calculates the value of the grade for the specified student.
     *
     * @param student      The student to calculate the grade for.
     * @param taskDatabase The task database.
     * @return The value of the grade.
     */
    double calculate(Student student, TaskDatabase taskDatabase);
}
