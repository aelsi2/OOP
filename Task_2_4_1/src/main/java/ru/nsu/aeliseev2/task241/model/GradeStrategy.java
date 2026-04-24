package ru.nsu.aeliseev2.task241.model;

import java.util.List;

/**
 * A strategy for calculating a student's grade.
 */
public interface GradeStrategy {
    /**
     * Gets the list of tasks considered when calculating this grade.
     *
     * @param taskDatabase The task database.
     * @return The list of tasks.
     */
    List<Task> getTasks(TaskDatabase taskDatabase);

    /**
     * Gets the number of points given to a student for a task.
     *
     * @param student      The student to get the sum of points for.
     * @param taskDatabase The task database.
     * @return The sum of points.
     */
    double getTaskPoints(Task task, Student student, TaskDatabase taskDatabase);

    /**
     * Gets the sum of points considered when calculating this grade.
     *
     * @param student      The student to get the sum of points for.
     * @param taskDatabase The task database.
     * @return The sum of points.
     */
    double getTotalPoints(Student student, TaskDatabase taskDatabase);

    /**
     * Calculates the value of the grade for the specified student.
     *
     * @param student      The student to calculate the grade for.
     * @param taskDatabase The task database.
     * @return The value of the grade.
     */
    double calculate(Student student, TaskDatabase taskDatabase);
}
