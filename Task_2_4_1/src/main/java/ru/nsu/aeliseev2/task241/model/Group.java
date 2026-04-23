package ru.nsu.aeliseev2.task241.model;

import java.util.List;

/**
 * A group of students.
 *
 * @param name     The name of the group.
 * @param students The members of the group.
 */
public record Group(String name, List<Student> students) {
}
