package ru.nsu.aeliseev2.task241.model;

/**
 * A student.
 *
 * @param name           The full name of the student.
 * @param githubUsername The GitHub username of the student.
 */
public record Student(String name, String githubUsername) {
}
