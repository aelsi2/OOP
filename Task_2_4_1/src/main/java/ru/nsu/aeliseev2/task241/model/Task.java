package ru.nsu.aeliseev2.task241.model;

import java.time.Instant;

/**
 * A task to be done by the students.
 *
 * @param dirName      The name of the directory inside the repo.
 * @param name         The human-readable name of the task.
 * @param softDeadline The date of the soft deadline for this task.
 * @param hardDeadline The date of the hard deadline for this task.
 */
public record Task(String dirName, String name, Instant softDeadline, Instant hardDeadline) {
}
