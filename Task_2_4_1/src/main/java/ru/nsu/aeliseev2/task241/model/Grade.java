package ru.nsu.aeliseev2.task241.model;

/**
 * A student's grade.
 *
 * @param name     The name of the grade.
 * @param strategy The strategy used to calculate the value of the grade.
 */
public record Grade(String name, GradeStrategy strategy) {
}
