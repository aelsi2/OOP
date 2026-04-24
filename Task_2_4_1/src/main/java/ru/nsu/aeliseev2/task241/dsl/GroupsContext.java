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
        private final String username;

        /**
         * Initializes a new instance of {@code StudentContext}.
         *
         * @param username The GitHub username of the student.
         */
        public StudentContext(String username) {
            this.username = username;
        }

        /**
         * Specifies the full name of the student and adds them to the database.
         *
         * @param fullName The full name of the student.
         */
        public void name(String fullName) {
            students.add(new Student(fullName, username));
        }
    }

    final List<Student> students = new ArrayList<>();

    /**
     * Begins constructing a new student.
     *
     * @param username The GitHub username of the student.
     * @return The {@code groups.student} context.
     */
    public StudentContext student(String username) {
        return new StudentContext(username);
    }
}
