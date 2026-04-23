package ru.nsu.aeliseev2.task241.model;

/**
 * A student's grade.
 *
 * @param name     The name of the grade.
 * @param strategy The strategy used to calculate the value of the grade.
 */
public record Grade(String name, GradeStrategy strategy) {
    /**
     * Calculates the value of the grade for the specified student.
     *
     * @param student      The student to calculate the grade for.
     * @param taskDatabase The task database.
     * @return The value of the grade.
     */
    public double calculate(Student student, TaskDatabase taskDatabase) {
        return strategy.calculate(student, taskDatabase);
    }
}
