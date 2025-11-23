package ru.nsu.aeliseev2.task141;

/**
 * Represents a subject.
 *
 * @param name          The name of the subject.
 * @param finalSemester The index of the final semester of this subject.
 */
public record Subject(String name, int finalSemester) {
}
