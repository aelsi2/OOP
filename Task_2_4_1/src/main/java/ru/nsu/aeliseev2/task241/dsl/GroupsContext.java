package ru.nsu.aeliseev2.task241.dsl;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.aeliseev2.task241.model.Student;

/**
 * Configuration DSL {@code groups} context.
 */
public class GroupsContext {
    /**
     * Configuration DSL {@code groups.student} context.
     */
    public class StudentContext {
        private final String name;

        /**
         * Initializes a new instance of {@code StudentContext}.
         *
         * @param name The full name of the student.
         */
        public StudentContext(String name) {
            this.name = name;
        }

        /**
         * Specifies the GitHub username of the student and adds them to the database.
         *
         * @param githubUsername The GitHub username of the student.
         */
        public void username(String githubUsername) {
            students.add(new Student(name, githubUsername));
        }
    }

    final List<Student> students = new ArrayList<>();

    /**
     * Begins constructing a new student.
     *
     * @param name The full name of the student.
     * @return The {@code groups.student} context.
     */
    public StudentContext student(String name) {
        return new StudentContext(name);
    }
}
